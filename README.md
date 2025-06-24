# 🇺🇳 Flags Challenge - Android Quiz App

An Android app built using Kotlin, Jetpack Compose, and MVVM architecture that presents a timed "Flags Challenge" game. Users are asked to identify countries based on their flags, with features including scheduled start time, timed questions, feedback on answers, and session state recovery.

---

## 📱 Features

- ⏰ Schedule the challenge to start at a specific time.
- 🧠 Countdown message: "CHALLENGE WILL START IN 00:20" before challenge begins.
- 🏁 15 flag-based multiple-choice questions.
- ⌛ Each question is shown for 30 seconds with a 10-second interval.
- 🎯 Instant feedback: "Correct" or "Wrong" after each answer.
- 🧾 Resume support: user gets the current question on re-entry after backgrounding or killing the app.
- 🧮 Final score display: `SCORE: XX/15`.

---

## 📦 JSON Question Format

```json
{
  "questions": [
    {
      "answer_id": 160,
      "countries": [
        { "country_name": "Bosnia and Herzegovina", "id": 29 },
        { "country_name": "Mauritania", "id": 142 },
        { "country_name": "Chile", "id": 45 },
        { "country_name": "New Zealand", "id": 160 }
      ],
      "country_code": "NZ"
    }
  ]
}
