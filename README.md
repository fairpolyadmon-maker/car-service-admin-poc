# Car Service Center Admin POC - Daily Routine Task & Enforcement App

An Android application built with **Kotlin** and **Jetpack Compose** tailored specifically for the **Car Service Center Admin POC (Sr. Executive / Assistant Manager - Car Service Center)** to enforce standard operating procedures (SOPs), track inspections, forcefully alert pending tasks, and automate daily reporting to the Head of Administration.

---

## 🎯 Alignment with Core Responsibilities

The app systematically covers the 8 core responsibilities outlined in the Job Description:

1. **Service Center Facility Management**: Daily opening & closing inspections, service bays, customer lounge, pantry, and washroom maintenance.
2. **Workshop Support**: Air compressor, lighting, ventilation, drainage, and technician facility support.
3. **Safety & Security**: Fire extinguishers, emergency exit clearance, biometric attendance, and security guard oversight.
4. **Utilities & Engineering Coordination**: Main power supply, generator diesel/battery checks, UPS backups, water pumps, and equipment breakdown logs.
5. **Waste & Housekeeping Coordination**: Clean bays, scrap metal and used engine oil storage, drainage cleaning, and sweeper supervision.
6. **Administrative Support**: Stationery stock, consumable requisitions, and facility expense tracking.
7. **Vendor & Contractor Management**: On-site contractor supervision, work permits, and service quality verification.
8. **Daily Reporting**: Complete facility inspection tracking, critical issue escalation, and automatic WhatsApp/SMS report generation for the Head of Administration.

---

## ⏰ Daily Routine & Enforcement Schedule

| Time Slot | Routine Work | Alarm & Enforcement Mechanism |
| :--- | :--- | :--- |
| **09:00 AM - 09:45 AM** | **Morning Opening Inspection**: Gate, Power, Generator, UPS, Compressor, Lounge AC/Pantry, Sweepers, Guard. | High-priority alarm upon office entry. Repeated every 15 mins if tasks remain unfinished. |
| **01:00 PM - 02:00 PM** | **Mid-Day & Workshop Operations**: Workshop drainage, lighting, fire safety, vendor permits, waste disposal. | Mid-day inspection alarm and persistent tracking. |
| **05:15 PM - 05:45 PM** | **Pre-Closing Enforcement (30-45 mins before 6 PM departure)**: Review all pending morning/midday items, stationery requisitions, incident logs. | **Maximum Urgency Alarm!** Alerts the officer of any pending tasks before leaving office. Repeats every 10 mins. |
| **05:45 PM - 06:00 PM** | **Evening Closing & Management Reporting**: Generate & send report to Head of Admin, escalate critical issues, security lockup handover. | Final closure reminder & one-click report dispatch. |

---

## 🚀 Key Features

- **Persistent Sticky Progress Notification**: Cannot be dismissed until all tasks are checked.
- **Automated Alarms via Exact AlarmManager**: Functions even if the app is closed or backgrounded.
- **Repeat Follow-up System**: Repeatedly rings and vibrates at 10-15 minute intervals if items are pending.
- **One-Click Daily Reporting**: Formats a complete Bengali & English report ready for WhatsApp and SMS dispatch to the Head of Administration.
- **Emergency SOS / Escalation**: Quick button to immediately notify management of critical facility failures.
- **In-App Alarm & Notification Tester**: Instant buttons in Settings to test morning alarms, 5:15 PM pre-closing alerts, and sticky notification tray.

---

## 🛠️ Tech Stack & Architecture

- **Platform**: Android Native (API 24 to 36)
- **UI Framework**: Jetpack Compose with Material Design 3
- **Language**: Kotlin 2.3+
- **Architecture**: MVVM with Repository Pattern
- **Scheduling**: Android `AlarmManager` (`RTC_WAKEUP`), `BroadcastReceiver`, `BootReceiver`
- **Notifications**: `NotificationCompat`, Notification Channels (High Priority, Alarms, Sticky Ongoing)
- **Local Persistence**: `SharedPreferences` with structured JSON serialization

---

## 📱 Build & Installation

### Option 1: Direct APK Installation
The pre-compiled APK is available in the repository root:
```
CarServiceAdminPOC.apk
```
Transfer to an Android phone, enable *Install Unknown Apps*, and allow notification permissions upon launch.

### Option 2: Build from Source
```bash
# Clone the repository
git clone <repository_url>
cd admin_poc_app

# Build debug APK
./gradlew assembleDebug

# Output APK location
app/build/outputs/apk/debug/app-debug.apk
```
