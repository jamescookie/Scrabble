# Scrabble (Micronaut)

[![Play Live](https://img.shields.io/badge/Play-Live-brightgreen)](https://scrabble.jamescookie.com/)
[![Java CI with Gradle](https://github.com/jamescookie/Scrabble/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/jamescookie/Scrabble/actions/workflows/gradle.yml)
[![Last Commit](https://img.shields.io/github/last-commit/jamescookie/Scrabble)](https://github.com/jamescookie/Scrabble/commits)
[![Issues](https://img.shields.io/github/issues-raw/jamescookie/Scrabble)](https://github.com/jamescookie/Scrabble/issues)
[![License](https://img.shields.io/github/license/jamescookie/Scrabble)](https://github.com/jamescookie/Scrabble/blob/master/LICENSE)

> A lightweight, single-player Scrabble-style web game built with Micronaut and Thymeleaf.

Play the game live: **https://scrabble.jamescookie.com/**

---

## About this project
- ✅ Playable in your browser — no install required.
- 🧩 Small, focused codebase demonstrating Micronaut + Thymeleaf server-side rendering.
- 🔤 Implements Scrabble basics: letter bag, tray, scoring, and word validation.

## Quick start (developer)
1. Install JDK 21 (or newer) and ensure Gradle is available (or use the bundled wrapper).
2. From the project root run:

```bash
./gradlew run
```

3. Open http://localhost:8080 in your browser and start playing.

### Tests & build
- Run tests: `./gradlew test`
- Build a runnable shadow JAR: `./gradlew shadowJar` (artifact appears under `build/libs`)

## Play
Open the live site and try a few plays — it's the easiest way to see the app in action:

https://scrabble.jamescookie.com/

## Features
- 🧾 Web-based Scrabble board and simple UI
- ⚖️ Letter bag and tray simulation
- 🧮 Scoring rules and basic word validation
- 🎯 Simple single-player gameplay for experimenting with words

## Project layout (high level)
- `src/main/java` — server code (Micronaut controllers, services)
- `src/main/resources/views` — Thymeleaf templates
- `src/main/resources/public` — static assets

## License
See `LICENSE` if present. If no license file is included, contact the maintainer for permission to reuse.

## Maintainer
James — https://jamescookie.com
