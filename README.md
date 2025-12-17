# 📚 StudyMate – Android Development Project

StudyMate is an Android application developed with **Jetpack Compose** that helps students manage their study tasks in a clear, structured, and intelligent way.  
Instead of being a simple to-do list, the app provides **visual and contextual feedback** to help students better understand deadlines, urgency, and focus.

---

## 🎯 Goal of the Application

The goal of StudyMate is to help students:
- organize tasks per course
- avoid missing deadlines
- set priorities based on urgency
- keep an overview during busy periods such as exams and projects
- focus on what truly matters at a given moment

---

## 🚀 Main Features

- Create, edit, and delete tasks
- Link tasks to a course
- Set a deadline per task
- Mark tasks as completed
- Local data storage using **Room Database**
- Modern UI built with **Jetpack Compose (Material 3)**
- State management using **MVVM + ViewModel**

---

## 🌟 Unique Selling Points (USP)

### 1. Smart Deadline Risk System

Each task automatically receives a **risk level** based on its deadline.  
This allows students to immediately see which tasks require attention and which deadlines have already been missed.

#### Risk Rules
- **OVERDUE** → deadline is in the past
- **HIGH** → deadline within 0–2 days
- **MEDIUM** → deadline within 3–7 days
- **LOW** → deadline more than 7 days away

This clear separation ensures that missed deadlines are not treated the same as urgent upcoming tasks.

#### Visual Feedback
- 🔴 **Overdue** → dark red
- 🔴 **High risk** → red
- 🟠 **Medium risk** → orange
- 🟢 **Low risk** → green

Each risk level is displayed using a **color-coded RiskChip** next to the task.

---

### 2. Smart Focus Mode

StudyMate includes a **Smart Focus Mode** that helps students reduce cognitive overload and distractions.

When Focus Mode is enabled:
- only tasks with **OVERDUE**, **HIGH**, or **MEDIUM** risk levels are shown
- low-priority tasks are temporarily hidden
- the user can fully concentrate on urgent and important work

This feature allows students to focus on what truly matters **right now**, without removing or deleting any tasks.

Focus Mode can be enabled or disabled at any time via the menu in the top-right corner of the application.

---

## 🔍 Filter & Sort Options

Users can:

### Filter tasks by risk:
- All
- Overdue
- High
- Medium
- Low

### Sort tasks by:
- Deadline (ascending)
- Risk level (Overdue → High → Medium → Low)

These options are accessible via the menu in the top-right corner of the application.

---

## 🧱 Technical Architecture

- **Programming language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Architecture pattern:** MVVM
- **State management:** StateFlow
- **Database:** Room (local storage)

### Project Structure

data/
├─ local/
│ ├─ dao/
│ ├─ entity/
│ └─ db/
domain/
├─ RiskCalculator
├─ RiskLevel
ui/
├─ screens/
│ ├─ tasks
│ ├─ courses
│ └─ settings
├─ components/
│ └─ RiskChip
├─ events
└─ state
viewmodel/
└─ TaskViewModel


---

## 🧮 Risk Calculation Logic

The risk calculation is handled centrally by the `RiskCalculator`.

- Deadlines are stored as `epochDay`
- The difference between the current day and the deadline determines the risk level
- The same logic is reused for:
  - displaying the RiskChip color
  - filtering tasks by risk
  - sorting tasks by risk

This ensures consistent behavior across the entire application.

---

## 🧪 What Can Be Tested in the App

- Creating tasks with different deadlines
- Verifying that the risk level updates automatically
- Filtering tasks by risk (Overdue / High / Medium / Low)
- Sorting tasks by deadline
- Sorting tasks by risk (Overdue first)
- Enabling and disabling Smart Focus Mode
- Marking tasks as completed
- Deleting tasks

---

## 📦 APK

The built APK of the application can be found in the `/apk` folder of this repository.

---

## 👨‍🎓 Author

- **Name:** Adrien Göksel
- **Program:** IT – Android Development
- **Institution:** Erasmushogeschool Brussel

---

## ✅ Conclusion

StudyMate goes beyond a traditional to-do application by:
- automatically calculating task urgency
- clearly separating overdue tasks from urgent ones
- providing immediate visual feedback using colors
- offering a Smart Focus Mode to reduce distractions
- helping students reflect on planning and prioritization

The application combines **usability**, **modern Android technologies**, and **multiple clear functional USPs**.
