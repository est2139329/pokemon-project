# Pokemon TCG Collection Manager

A Spring Boot web application for browsing and managing a Pokemon Trading Card Game collection. Search for cards, view detailed stats, and build your collection — all powered by the [Pokemon TCG API](https://pokemontcg.io/).

## Features

- Search cards by name
- View card details: HP, attacks, abilities, weaknesses, resistances, and market value
- Add/remove cards from a personal session-based collection
- REST API endpoints for programmatic access
- Dark Pokemon-themed responsive UI

## Tech Stack

- **Backend:** Java 17, Spring Boot 3, Spring WebFlux (WebClient)
- **Frontend:** Thymeleaf, HTML/CSS
- **API:** [Pokemon TCG API v2](https://api.pokemontcg.io/v2)
- **Session:** In-memory (HttpSession) — no database required

## Getting Started

### Prerequisites

- Java 17+
- Maven (or use the included `mvnw` wrapper)

### Run the app

```bash
./mvnw spring-boot:run
```

Then open [http://localhost:8080](http://localhost:8080) in your browser.

### Optional: API Key

The Pokemon TCG API works without a key but has rate limits. To use your own key, add it to `src/main/resources/application.properties`:

```properties
pokemontcg.api.key=your_api_key_here
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cards/search?name=` | Search cards by name |
| GET | `/api/cards/{id}` | Get a card by ID |
| GET | `/api/cards/filter/type?type=` | Filter by type |
| GET | `/api/cards/filter/set?set=` | Filter by set |
| GET | `/api/cards/filter/rarity?rarity=` | Filter by rarity |

## Project Structure

```
src/main/java/com/example/demo/
├── HomeController.java     # Web UI routes
├── CardController.java     # REST API routes
├── CardService.java        # Pokemon TCG API integration
├── Card.java               # Card model
├── Attack.java
├── Ability.java
├── Weakness.java
└── Resistance.java

src/main/resources/
├── templates/
│   ├── fragments/navbar.html
│   ├── index.html          # My Collection page
│   ├── search.html         # Search page
│   └── card-detail.html    # Card detail view
└── static/css/style.css
```
