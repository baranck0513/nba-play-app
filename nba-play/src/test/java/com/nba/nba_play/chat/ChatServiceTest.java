package com.nba.nba_play.chat;

import com.nba.nba_play.player.Player;
import com.nba.nba_play.player.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * A test subclass overrides callClaudeApi() to return controlled JSON
 * without making real network calls.
 */
@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private PlayerService playerService;

    private Player lebron;
    private Player curry;

    @BeforeEach
    void setUp() {
        lebron = buildPlayer(1L, "LeBron James", "LAL", 30.0, 8.0, 7.0, 1.5, 0.5, 20.0, 35.5, 60);
        curry  = buildPlayer(2L, "Stephen Curry", "GSW", 25.0, 5.0, 6.0, 1.2, 0.2, 18.0, 32.0, 55);
    }

    // --- type: name ---

    @Test
    void processQuery_typeNameDelegatesToGetPlayersByName() throws Exception {
        when(playerService.getPlayersByName("LeBron James")).thenReturn(List.of(lebron));
        ChatService service = stubService("{\"type\": \"name\", \"value\": \"LeBron James\"}");

        List<Player> result = service.processQuery("Who is LeBron?");

        assertThat(result).containsExactly(lebron);
        verify(playerService).getPlayersByName("LeBron James");
    }

    // --- type: team ---

    @Test
    void processQuery_typeTeamDelegatesToGetPlayersFromTeam() throws Exception {
        when(playerService.getPlayersFromTeam("LAL")).thenReturn(List.of(lebron));
        ChatService service = stubService("{\"type\": \"team\", \"value\": \"LAL\"}");

        List<Player> result = service.processQuery("Show me Lakers players");

        assertThat(result).containsExactly(lebron);
        verify(playerService).getPlayersFromTeam("LAL");
    }

    // --- type: all ---

    @Test
    void processQuery_typeAllReturnsAllPlayers() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"all\"}");

        List<Player> result = service.processQuery("Show all players");

        assertThat(result).containsExactly(lebron, curry);
    }

    // --- type: filter with gt ---

    @Test
    void processQuery_filterPtsGt_returnsPlayersAboveThreshold() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"pts\", \"operator\": \"gt\", \"value\": 27.0}");

        List<Player> result = service.processQuery("players scoring more than 27");

        // lebron=30 > 27, curry=25 not > 27
        assertThat(result).containsExactly(lebron);
    }

    @Test
    void processQuery_filterPtsLt_returnsPlayersBelowThreshold() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"pts\", \"operator\": \"lt\", \"value\": 28.0}");

        List<Player> result = service.processQuery("players scoring less than 28");

        // curry=25 < 28, lebron=30 not < 28
        assertThat(result).containsExactly(curry);
    }

    // --- filter: all stat fields ---

    @Test
    void processQuery_filterReb() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"reb\", \"operator\": \"gt\", \"value\": 6.0}");

        List<Player> result = service.processQuery("players with more than 6 rebounds");

        // lebron.reb=8 > 6, curry.reb=5 not > 6
        assertThat(result).containsExactly(lebron);
    }

    @Test
    void processQuery_filterAst() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"ast\", \"operator\": \"gt\", \"value\": 6.5}");

        List<Player> result = service.processQuery("players with more than 6.5 assists");

        // lebron.ast=7 > 6.5, curry.ast=6 not > 6.5
        assertThat(result).containsExactly(lebron);
    }

    @Test
    void processQuery_filterStl() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"stl\", \"operator\": \"gt\", \"value\": 1.3}");

        List<Player> result = service.processQuery("players with more than 1.3 steals");

        // lebron.stl=1.5 > 1.3, curry.stl=1.2 not > 1.3
        assertThat(result).containsExactly(lebron);
    }

    @Test
    void processQuery_filterBlk() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"blk\", \"operator\": \"gt\", \"value\": 0.3}");

        List<Player> result = service.processQuery("players with more than 0.3 blocks");

        // lebron.blk=0.5 > 0.3, curry.blk=0.2 not > 0.3
        assertThat(result).containsExactly(lebron);
    }

    @Test
    void processQuery_filterEff() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"eff\", \"operator\": \"gt\", \"value\": 19.0}");

        List<Player> result = service.processQuery("players with efficiency above 19");

        // lebron.eff=20 > 19, curry.eff=18 not > 19
        assertThat(result).containsExactly(lebron);
    }

    @Test
    void processQuery_filterMin() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"min\", \"operator\": \"gt\", \"value\": 33.0}");

        List<Player> result = service.processQuery("players playing more than 33 minutes");

        // lebron.min=35 > 33, curry.min=32 not > 33
        assertThat(result).containsExactly(lebron);
    }

    @Test
    void processQuery_filterGp() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"filter\", \"field\": \"gp\", \"operator\": \"gt\", \"value\": 57.0}");

        List<Player> result = service.processQuery("players with more than 57 games");

        // lebron.gp=60 > 57, curry.gp=55 not > 57
        assertThat(result).containsExactly(lebron);
    }

    // --- unknown type falls back to all ---

    @Test
    void processQuery_unknownType_returnsAllPlayers() throws Exception {
        when(playerService.getPlayers()).thenReturn(List.of(lebron, curry));
        ChatService service = stubService("{\"type\": \"unknown\"}");

        List<Player> result = service.processQuery("something weird");

        assertThat(result).containsExactly(lebron, curry);
    }

    // --- helpers ---

    /** Creates a ChatService whose callClaudeApi() returns the given JSON string. */
    private ChatService stubService(String claudeResponse) {
        return new ChatService(playerService) {
            @Override
            protected String callClaudeApi(String prompt) {
                return claudeResponse;
            }
        };
    }

    private Player buildPlayer(long id, String name, String team,
                               double pts, double reb, double ast,
                               double stl, double blk, double eff,
                               double min, int gp) {
        return new Player(id, 1, name, id * 10, team, gp, min,
                8.0, 16.0, 0.500,
                3.0, 7.0, 0.400,
                5.0, 6.0, 0.850,
                1.5, reb - 1.5, reb, ast,
                stl, blk, 3.0,
                pts, eff);
    }
}
