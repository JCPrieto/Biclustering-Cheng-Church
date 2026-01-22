# Repository Guidelines

## Project Structure & Module Organization

- `src/main/java/bicl_CC/` contains the Java sources for the Swing GUI and biclustering logic.
- `pom.xml` defines the Maven build and Java 21 compiler settings.
- `target/` is Maven build output and should not be edited by hand.

## Build, Test, and Development Commands

- `mvn package` compiles the code and builds the jar into `target/`.
- `mvn test` runs tests if/when they are added (no tests are present yet).
- Run the app from your IDE using the main class `bicl_CC.Bicluster`.

## Coding Style & Naming Conventions

- Indentation follows tabs as seen in existing source files; keep consistency within modified files.
- Package naming uses `bicl_CC` and classes use `PascalCase` (e.g., `Bicluster`, `Resultados`).
- Keep UI text and strings in English/Spanish consistent with nearby code.

## Testing Guidelines

- There is currently no `src/test/java` directory or test framework configured.
- If you add tests, follow Maven defaults (`src/test/java`) and name them `*Test.java`.
- Keep tests focused on algorithmic components (e.g., biclustering logic) rather than UI.

## Commit & Pull Request Guidelines

- This repository has no commit history, so no established commit message conventions exist.
- Prefer short, imperative commit messages (e.g., “Fix bicluster score calculation”).
- Pull requests should include a clear description of changes, reproduction steps for UI fixes, and any relevant
  screenshots.

## Configuration Notes

- Java version is set to 21 in `pom.xml`; ensure your toolchain matches to avoid compile errors.
