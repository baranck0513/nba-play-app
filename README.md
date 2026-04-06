# <p align="center"> NbaPlayApp

[![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![React](https://img.shields.io/badge/React-19-61dafb?logo=react&logoColor=black)](https://react.dev/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Claude](https://img.shields.io/badge/AI-Claude%20Haiku-8a2be2?logo=anthropic&logoColor=white)](https://www.anthropic.com/)
[![License](https://img.shields.io/badge/license-MIT-green)](LICENSE)

A full-stack NBA player statistics explorer powered by AI. Ask questions in plain English — *"Who are the best scorers on the Lakers?"* — and Claude interprets your query to return matching players from a database of 200+ NBA player season stats.

## Features

- **Natural Language Search** — AI-powered queries via Claude Haiku; describe what you're looking for and get results instantly
- **Full CRUD API** — Add, update, delete, and retrieve player records via REST endpoints
- **Advanced Filtering** — Filter by team (e.g. `LAL`, `GSW`, `BOS`) or search by player name
- **Comprehensive Stats** — PTS, REB, AST, STL, BLK, FG%, 3P%, FT%, EFF, and more per player
- **React Frontend** — Dark, NBA-themed UI with an interactive stats table
- **Scraped Dataset** — Season statistics for 200+ players collected via Python and pandas

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Backend | Java 21, Spring Boot 4.0.3, Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Frontend | React 19, React Router DOM |
| AI | Claude API (`claude-haiku-4-5`) |
| Data Pipeline | Python, pandas |
| DevOps | Docker, Docker Compose, Maven |

## Getting Started

### Prerequisites

- Java 21
- Node.js
- Docker (for PostgreSQL) _or_ a local PostgreSQL installation
- [Anthropic API Key](https://console.anthropic.com/)

### 1. Start the Database

```bash
docker-compose up -d
```

This spins up a PostgreSQL instance on port `5432`.

### 2. Configure the Backend

Copy the example config and fill in your values:

```bash
cp nba-play/src/main/resources/application.properties.example \
   nba-play/src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nba-database
spring.datasource.username=your_username
spring.datasource.password=your_password
anthropic.api.key=your_api_key
allowed.origins=http://localhost:3000
```

### 3. Run the Backend

```bash
cd nba-play
./mvnw spring-boot:run
```

The API starts on `http://localhost:8080`.

### 4. Run the Frontend

```bash
cd nba-play-ui
npm install
npm start
```

The UI starts on `http://localhost:3000`.

## Sample Usage

### Natural Language Search

```bash
curl -X POST http://localhost:8080/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Who are the best scorers on the Lakers?"}'
```

### Get All Players

```bash
curl http://localhost:8080/api/v1/player
```

### Filter by Team

```bash
curl "http://localhost:8080/api/v1/player?team=GSW"
```

### Search by Name

```bash
curl "http://localhost:8080/api/v1/player?name=LeBron"
```

### Add a Player

```bash
curl -X POST http://localhost:8080/api/v1/player \
  -H "Content-Type: application/json" \
  -d '{"playerName": "Example Player", "team": "LAL", "pts": 22.5, ...}'
```

### Delete a Player

```bash
curl -X DELETE http://localhost:8080/api/v1/player/ExamplePlayer
```

## API Reference

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/player` | Get all players |
| GET | `/api/v1/player?team={team}` | Filter players by team code (e.g. `LAL`) |
| GET | `/api/v1/player?name={name}` | Search players by name |
| POST | `/api/v1/player` | Add a new player |
| PUT | `/api/v1/player` | Update a player |
| DELETE | `/api/v1/player/{playerName}` | Delete a player by name |
| POST | `/api/v1/chat` | Natural language AI search |

## Project Structure

```
nba-play-app-main/
├── docker-compose.yml              # PostgreSQL container
│
├── nba-play/                       # Backend (Spring Boot)
│   ├── pom.xml
│   └── src/main/java/com/nba/nba_play/
│       ├── NbaPlayApplication.java
│       ├── player/
│       │   ├── Player.java         # JPA entity (player_stats table)
│       │   ├── PlayerController.java
│       │   ├── PlayerService.java
│       │   └── PlayerRepository.java
│       └── chat/
│           ├── ChatController.java
│           └── ChatService.java    # Claude API integration
│
└── nba-play-ui/                    # Frontend (React)
    ├── package.json
    └── src/
        ├── App.js                  # Main component
        └── App.css                 # NBA dark theme styling
```

## Contributing

Contributions are welcome. Please open an issue first to discuss what you'd like to change.

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.
