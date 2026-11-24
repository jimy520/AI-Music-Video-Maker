AI MUSIC VIDEO MAKER — NATIVE ANDROID PROTOTYPE 0.1.0

Target: Android 8.0+ (API 26), including Samsung Galaxy A55 5G.

WHAT THIS BUILD DOES:
- Native Android app shell (Kotlin).
- Selects local audio using Android system file picker.
- Input for song title and user-supplied lyrics.
- Shows target production settings: 720p, AI 3D visual, word karaoke, opening title.
- No laptop server required to open this prototype.

WHAT IS NOT IMPLEMENTED YET:
- On-device Whisper transcription and word-level alignment.
- Karaoke subtitle timing/render pipeline.
- On-device AI 3D video generation.
- MP4 export/render.
- YouTube URL import/download. Use media files you own or are authorized to use.

WHY NOT A READY APK HERE:
This package contains Android Studio source. The build environment used to prepare it does not include Android SDK/Gradle installation, so a signed APK could not be compiled or tested here.

BUILD:
1. Install Android Studio on a computer.
2. Open this folder as an existing project.
3. Let Gradle sync dependencies.
4. Build > Build Bundle(s) / APK(s) > Build APK(s).
5. Install the generated app-debug.apk on the Galaxy A55.

Hybrid local design:
- Core UI and eventual audio/video pipeline run on-device.
- Optional external AI providers can be added later for higher-quality 3D video generation.
- Keep all external AI calls opt-in; no API key is included in this project.

Before production:
- Implement word-level forced alignment for user-provided lyrics.
- Add local speech model (e.g. sherpa-onnx/whisper.cpp Android integration) and test Indonesian song recognition.
- Add native video compositor / Media3 Transformer pipeline.
- Add local or optional cloud 3D generation provider adapter.
- Add thermal, storage, battery, and long-render tests on target device.
