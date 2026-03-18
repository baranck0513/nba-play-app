# NBA Play

NBA Play is a full-stack application that scrapes, stores, and dynamically presents season statistics for 200+ NBA players. It features a natural language AI search powered by Claude, allowing users to query player data in plain English.

## Tech Stack

- **Backend**: Java 21, Spring Boot, Spring Data JPA, Hibernate
- **Database**: PostgreSQL
- **Frontend**: React, JavaScript
- **AI**: Claude API (Anthropic) — natural language query processing
- **Data Scraping**: Python, pandas

## Features

- **Natural Language Search** - Ask questions like *"Who are the best scorers on the Lakers?"* or *"Show me players averaging over 25 points"* and Claude interprets the query and returns matching players
- **Full CRUD API** - Add, update, delete, and retrieve player statistics via REST endpoints
- **Player Filtering** - Filter players by team or search by name
- **Data Scraping** - Scraped season statistics for 200+ players using Python and pandas
- **React Frontend** - UI to browse and interact with player data

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/player` | Get all players |
| GET | `/api/v1/player?team={team}` | Filter players by team |
| GET | `/api/v1/player?name={name}` | Search players by name |
| POST | `/api/v1/player` | Add a new player |
| PUT | `/api/v1/player` | Update a player |
| DELETE | `/api/v1/player/{playerName}` | Delete a player |
| POST | `/api/v1/chat` | Natural language AI search |

## Setup

### Prerequisites
- Java 21
- PostgreSQL
- Node.js
- Anthropic API Key

### Backend
1. Create a PostgreSQL database named `nba-database`
2. Copy `application.properties.example` to `application.properties` and fill in your values:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nba-database
spring.datasource.username=your_username
spring.datasource.password=your_password
anthropic.api.key=your_api_key
```
3. Run the Spring Boot application

### Frontend
```bash
cd nba-play-ui
npm install
npm start
```
