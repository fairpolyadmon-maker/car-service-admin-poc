# Car Service Center Admin POC - Daily Routine Task & Enforcement App

[![Direct APK Download](https://img.shields.io/badge/Download-CarServiceAdminPOC.apk-brightgreen?style=for-the-badge&logo=android)](https://github.com/fairpolyadmon-maker/car-service-admin-poc/releases/download/v1.0.0/CarServiceAdminPOC.apk)
[![GitHub Release](https://img.shields.io/badge/Release-v1.0.0-blue?style=for-the-badge&logo=github)](https://github.com/fairpolyadmon-maker/car-service-admin-poc/releases/tag/v1.0.0)

> 📱 **সরাসরি APK ডাউনলোড লিংক**: [CarServiceAdminPOC.apk ডাউনলোড করুন (v1.0.0)](https://github.com/fairpolyadmon-maker/car-service-admin-poc/releases/download/v1.0.0/CarServiceAdminPOC.apk)

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

## 💻 Enterprise Desktop Application (Windows)

In addition to the Android APK, the project includes a high-performance **Desktop Application** built on **Electron.js** and **HTML5/CSS3 Enterprise Dashboard**:

- **🚀 1-Click Desktop Launcher**: Just double-click `LaunchDesktopApp.bat` in the project root!
- **🚨 Advanced Problem & Incident Tracking**:
  - Log new breakdowns/issues on sight with Category, Location/Bay, and Severity (Critical, High, Medium, Low).
  - Set **Target Resolution Deadlines (SLA)** with real-time countdown.
  - Formulate detailed step-by-step **Work Instructions & SOP Guidance** for technicians.
  - Tag personnel (Foreman, Electrician, Technician, AC Vendor, Security, Admin).
  - Tag involved departments (Workshop, Admin, Stores, Accounts, Safety).
  - Full lifecycle tracking: `OPEN` ➔ `IN_PROGRESS` ➔ `PENDING_SPARE` ➔ `RESOLVED` ➔ `CLOSED`.
- **🔔 Windows Native Toast Notifications & System Tray**:
  - Toast alerts with audio chimes for 09:00 AM Morning Inspection, 01:00 PM Midday, 05:15 PM Pre-closing, and SLA Overdues.
  - Minimizes to Windows System Tray and keeps monitoring in background.

---

## 🛠️ Tech Stack & Multi-Platform Architecture

- **Mobile (Android)**: Kotlin, Jetpack Compose, Material 3, Android Exact AlarmManager, BroadcastReceiver.
- **Desktop (Windows)**: Electron.js, Node.js v24, HTML5/CSS3 Glassmorphic UI, Windows Native Notification API, System Tray.
- **Data Persistence**: Offline-first local JSON & SharedPreferences storage with WhatsApp/SMS/PDF reporting.

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
