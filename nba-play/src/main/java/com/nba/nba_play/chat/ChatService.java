package com.nba.nba_play.chat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nba.nba_play.player.Player;
import com.nba.nba_play.player.PlayerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {

    private final PlayerService playerService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${anthropic.api.key}")
    private String apiKey;

    public ChatService(PlayerService playerService) {
        this.playerService = playerService;
    }

    public List<Player> processQuery(String userQuery) throws Exception {
        String prompt = """
                You are an NBA stats search assistant. Convert the user query into a JSON search parameter.
                Return ONLY a valid JSON object, no explanation, no markdown. Use one of these formats:
                - {"type": "name", "value": "player name"}
                - {"type": "team", "value": "TEAM_CODE"} (3-letter NBA team code like LAL, GSW, BOS, MIA)
                - {"type": "filter", "field": "pts|reb|ast|stl|blk|eff|min|gp", "operator": "gt|lt", "value": 25.0}
                - {"type": "all"}

                User query: "%s"
                """.formatted(userQuery);

        String requestBody = objectMapper.writeValueAsString(Map.of(
                "model", "claude-haiku-4-5-20251001",
                "max_tokens", 100,
                "messages", List.of(Map.of("role", "user", "content", prompt))
        ));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.anthropic.com/v1/messages"))
                .header("x-api-key", apiKey)
                .header("anthropic-version", "2023-06-01")
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode responseJson = objectMapper.readTree(response.body());

        if (responseJson.has("error")) {
            String errorMsg = responseJson.get("error").get("message").asText();
            throw new RuntimeException("Claude API error: " + errorMsg);
        }

        String content = responseJson.get("content").get(0).get("text").asText().trim();
        // strip markdown code blocks if Claude wraps the response
        content = content.replaceAll("^```[a-z]*\\n?", "").replaceAll("```$", "").trim();

        JsonNode params = objectMapper.readTree(content);
        String type = params.get("type").asText();

        return switch (type) {
            case "name" -> playerService.getPlayersByName(params.get("value").asText());
            case "team" -> playerService.getPlayersFromTeam(params.get("value").asText());
            case "filter" -> {
                String field = params.get("field").asText();
                String operator = params.get("operator").asText();
                double value = params.get("value").asDouble();
                yield playerService.getPlayers().stream()
                        .filter(p -> {
                            double stat = getStatValue(p, field);
                            return operator.equals("gt") ? stat > value : stat < value;
                        })
                        .collect(Collectors.toList());
            }
            default -> playerService.getPlayers();
        };
    }

    private double getStatValue(Player p, String field) {
        return switch (field) {
            case "pts" -> p.getPts();
            case "reb" -> p.getReb();
            case "ast" -> p.getAst();
            case "stl" -> p.getStl();
            case "blk" -> p.getBlk();
            case "eff" -> p.getEff();
            case "min" -> p.getMin();
            case "gp" -> (double) p.getGp();
            default -> 0;
        };
    }
}
