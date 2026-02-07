# Agent Instructions: GPT Investor Android

GPT Investor is a modern, AI-powered investment insights application. You must follow these patterns
strictly to maintain architectural integrity and code quality.

## 1. Verification & Quality Gate

Before finishing any task, you **MUST** run these verification commands. If they fail, fix the code
and re-run them.

- **Formatting**: Run `./gradlew ktfmtFormat` to apply formatting.
- **Lint Check**: Run `./gradlew ktfmtCheck` to verify compliance.
- **Build**: Run `./gradlew :module-name:assembleDebug` (replace `module-name` with the edited
  module, e.g., `:home:homeimpl`).
- **Unit Tests**: Run `./gradlew :module-name:testDebugUnitTest`.

## 2. Dependency Management (`libs.versions.toml`)

- **Never hardcode versions** in `build.gradle.kts`.
- Use `libs.versions.[name].get().toInt()` for SDK versions.
- Use `libs.[alias]` for libraries.
- If a dependency is missing, add it to `gradle/libs.versions.toml` first.

## 3. Directory & Module Structure

- **Feature Isolation**: Logic belongs in `*impl` modules. Database entities belong in `*-db`
  modules.
- **Inward Dependency**: Implementation modules (`impl`) can depend on `:theme`, `:navigation`, and
  their specific data modules. They should NOT depend on other feature `impl` modules.
- **Namespace**: Format must be `com.thejawnpaul.gptinvestor.[modulename]`.

## 4. Coding Patterns

- **Kotlin**:
    - Prefer `val` over `var`.
    - Use Kotlin's trailing lambda syntax.
    - Use `Dagger Hilt` for DI. Always use `@Inject constructor` for ViewModels and Repositories.
- **Compose**:
    - Use Material 3 (`libs.androidx.material3`).
    - Follow the State Hoisting pattern.
    - Preview functions must use `@Preview` and `Theme` wrappers.
- **Naming**:
    - Classes: `PascalCase`.
    - Functions/Variables: `camelCase`.
    - Compose UI: `PascalCase`.

## 5. Ambiguity & Clarification

- If you are unsure which module a new class belongs in, **ask for clarification**.
- If a dependency exists in `libs.versions.toml` but you aren't sure which alias to use, **ask**.
- If you encounter a build error that seems unrelated to your changes, **report it before proceeding
  **.

## 6. Testing

- Use `com.google.common.truth.Truth.assertThat` for assertions.
- Use `Robolectric` for tests requiring Android framework components.
- Use `kotlinx.coroutines.test` and `Turbine` for coroutines and Flow testing.
- Use `PageTestRule` and `PageTestSpec` for feature tests (declared with
  `com.thejawnpaul.gptinvestor.navigation.Page`) or `ComposeTestRule` for Compose UI tests.
- Always include `kspTest(libs.dagger.hilt.compiler)` if the test requires Hilt.
