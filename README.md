# kChat

<img src="app/src/main/res/drawable/ic_launcher_foreground.xml" width="72" align="left" alt="kChat icon" />

An open-source, OpenAI-compatible LLM chat client for the **Mudita Kompakt** e-ink
phone. A fork of [MiniiChat](https://github.com/Minis233/miniichat) restyled with
the [Mudita Mindful Design](https://github.com/mudita/MMD) system for a strictly
black-and-white e-ink display. Native Kotlin + Jetpack Compose, no backend, bring
your own keys.

<br clear="left" />

## What it does

- **Multi-provider** — point at any OpenAI-compatible endpoint (OpenAI, OpenRouter,
  DeepSeek, Groq, Mistral, Together, Gemini OpenAI shim, SiliconFlow, Ollama,
  LM Studio, custom). 11 built-in presets.
- **Auto-fetch models** — `GET /models` per provider; manual add/remove too.
- **Bottom-sheet model picker** — search across all providers.
- **Custom assistants** — name, emoji avatar, system prompt, optional temperature
  override. 4 built-in presets (Default / Coder / Translator / Writer).
- **Prompt variables** — `{model} {provider} {assistant} {date} {time} {datetime}
  {weekday} {locale}` rendered into the system prompt.
- **Per-provider customization** — custom HTTP headers and extra body params.
- **Media attachments** — image or file from the `+` menu; images sent as base64
  data URLs (vision-capable models).
- **Streaming** — Server-Sent-Events with a stop button.
- **Markdown** — headings, fenced code, lists, quotes, bold / italic / inline code.
- **e-ink theme** — monochrome MMD styling: pure black/white, no dynamic color, no
  ripple, sharp 1px borders instead of shadows.
- **Bilingual** — English and 简体中文 (Settings → Language).
- **Pure local storage** — DataStore preferences, no analytics, no backend.

## Build & install

```bash
# Debug build (needs JDK 17 + Android SDK)
./gradlew :app:assembleDebug
# APK at app/build/outputs/apk/debug/

# Sideload onto the Kompakt over ADB
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

For a signed, minified release build, configure a keystore in `local.properties`
(copy `local.properties.example`) and run:

```bash
scripts/build-release.sh   # produces kchat-<versionName>.apk in the project root
```

## Cutting a release

CI (`.github/workflows/release.yml`) builds a signed release APK and attaches it to
a GitHub Release on any `v*` tag:

```bash
git tag v1.0.0
git push origin v1.0.0
```

Repository secrets required (Settings → Secrets and variables → Actions):
`KEYSTORE_BASE64` (`base64 -w0 keystore/kchat.jks`), `KEYSTORE_PASSWORD`,
`KEY_ALIAS`, `KEY_PASSWORD`.

## Architecture

```
app/src/main/kotlin/org/ok1cdj/kchat
├── MainActivity.kt                  # Compose entry, locale, KChatTheme
├── ChatViewModel.kt                 # State + send/stream/persist
├── api/LlmClient.kt                 # ktor + SSE, multipart content
├── data/                            # DataStore (settings/conversations/providers/assistants)
├── ui/AppRoot.kt                    # Routing, drawer, back-stack
├── ui/ChatScreen.kt                 # Top bar, message stream, bubbles
├── ui/InputBar.kt                   # Text input + attachment picker
├── ui/ModelPicker.kt                # Bottom-sheet model selector
├── ui/Drawer.kt                     # Date-grouped chat list, search
├── ui/SettingsScreen.kt             # Settings hub
├── ui/AboutDialog.kt                # About dialog (MMD components)
├── ui/ProvidersScreen.kt            # Provider editor + fetch models
├── ui/AssistantsScreen.kt           # Assistant editor
├── ui/MarkdownText.kt               # Inline markdown renderer
└── ui/theme/Theme.kt                # KChatTheme: strict B/W e-ink scheme, ripple off
```

The e-ink look is a hand-built strictly black/white `lightColorScheme` applied via
`MaterialTheme` (`ui/theme/Theme.kt`), with ripple disabled globally — the same
pattern as the sibling Kompakt apps [kRadar](https://github.com/ok1cdj/kRadar) and
[kSread](https://github.com/ok1cdj/kSread). Streaming, provider handling, attachments
and markdown are unchanged from upstream MiniiChat.

## License

[MIT](LICENSE). Uses [Mudita Mindful Design (MMD)](https://github.com/mudita/MMD),
Apache-2.0.

## Acknowledgements

kChat is a fork of **[Minis233/miniichat](https://github.com/Minis233/miniichat)**
(MIT). Upstream's feature scope was inspired by two excellent open-source LLM clients
— **no source code from either is copied**:

- **[rikkahub/rikkahub](https://github.com/rikkahub/rikkahub)** — native Android LLM
  chat client, Apache-2.0. Inspired the multi-provider switching, prompt-variables
  format, and custom-headers idea.
- **[Chevey339/kelivo](https://github.com/Chevey339/kelivo)** — Flutter LLM chat
  client, AGPL-3.0. Inspired the assistant-presets concept, multi-language support,
  and per-provider extra-body customization.

If you build on kChat, please go star those projects as well.
