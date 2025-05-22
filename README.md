# 💧 Hydration Reminder App (WorkManager + Notifications)

This Android app demonstrates how to use **WorkManager** to show recurring notifications reminding users to drink water. It includes support for **Android 13+ notification permissions**, **notification channels**, and self-rescheduling workers for custom repeat intervals.

---

## 🚀 Features

- 📅 Periodic hydration reminders using `WorkManager`
- 🔔 Notifications with custom channel support
- ✅ Android 13+ (`Tiramisu`) notification permission handling
- ⚙️ Detects if notification channel is disabled and prompts user
- 🔁 Self-scheduling every 10 seconds (for testing; customizable)

---

## 🛠 Tech Stack

- Kotlin
- AndroidX
- WorkManager `2.9.0`
- NotificationManagerCompat
- ViewBinding (optional)
- Minimum SDK: 21 (Lollipop)

---

## 📂 Project Structure

| File/Class                  | Purpose                                                                 |
|----------------------------|-------------------------------------------------------------------------|
| `MainActivity.kt`          | Initializes notification permissions and starts the first work request |
| `ReminderWorker.kt`        | A `Worker` that shows a notification and schedules itself again         |
| `NotificationHelper.kt`    | Utility class for managing channels, permissions, and settings redirects|
| `activity_main.xml`        | Basic UI layout                                                         |
| `AndroidManifest.xml`      | Declares required notification permissions                             |

---

## 🔧 How It Works

1. `MainActivity` runs at app launch:
   - Creates the notification channel
   - Requests `POST_NOTIFICATIONS` permission (Android 13+)
   - Starts a one-time worker immediately

2. `ReminderWorker`:
   - Shows a “Time to drink water!” notification
   - Schedules the next reminder after 10 seconds (can be changed)

3. `NotificationHelper`:
   - Creates the notification channel
   - Checks if the channel is disabled
   - Guides user to enable it via system settings

---

## 📝 Note

- WorkManager’s official minimum interval for periodic work is **15 minutes**.
- This project uses **chained one-time requests** every 10 seconds to demonstrate short interval reminders (not recommended for production).
- For real apps, increase interval to preserve battery and follow Android best practices.

---

## 📸 Screenshots

![WhatsApp Image 2025-05-23 at 12 39 29 AM](https://github.com/user-attachments/assets/ab9a1590-9733-4f4b-b8ac-58d0057c5469)


---

## 🙌 Contributions

PRs and suggestions are welcome. This is a demo project for learning purposes.

---

## 📄 License

This project is open-source and licensed under the MIT License.
