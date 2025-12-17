# 📚 StudyMate – Android Development Project

StudyMate is een Android-applicatie ontwikkeld met **Jetpack Compose** die studenten helpt om hun studietaken te beheren op een overzichtelijke en slimme manier.  
De app focust niet alleen op takenlijsten, maar biedt ook een **unieke visuele ondersteuning** om deadlines beter in te schatten.

---

## 🎯 Doel van de applicatie

Het doel van StudyMate is om studenten te helpen:
- hun taken per vak te organiseren
- deadlines niet te vergeten
- prioriteiten te stellen op basis van urgentie
- overzicht te bewaren tijdens drukke periodes zoals examens en projecten

---

## 🚀 Belangrijkste functionaliteiten

- Taken aanmaken, bewerken en verwijderen
- Taken koppelen aan een course
- Deadline instellen per taak
- Taken aanduiden als voltooid
- Lokale opslag via **Room Database**
- Moderne UI met **Jetpack Compose (Material 3)**
- State management via **MVVM + ViewModel**

---

## 🌟 Unique Selling Point (USP)

### Smart Deadline Risk System

Elke taak krijgt **automatisch een risiconiveau** op basis van de ingestelde deadline.  
Dit helpt studenten om snel te zien welke taken dringend zijn.

### Risicoregels
- **HIGH** → deadline binnen 0–2 dagen of in het verleden
- **MEDIUM** → deadline binnen 3–7 dagen
- **LOW** → deadline over meer dan 7 dagen of geen deadline

### Visualisatie
- 🔴 **High risk** → rood
- 🟠 **Medium risk** → oranje
- 🟢 **Low risk** → groen

Het risiconiveau wordt weergegeven via een **kleurgecodeerde chip** bij elke taak.

---

## 🔍 Filter & Sort

De gebruiker kan:
- Taken **filteren op risico**:
    - All
    - High
    - Medium
    - Low
- Taken **sorteren**:
    - op deadline (oplopend)
    - op risico (High → Low)

Deze opties zijn beschikbaar via het menu rechtsboven in de applicatie.

---

## 🧱 Technische architectuur

- **Programmeertaal:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Architectuur:** MVVM
- **State management:** StateFlow
- **Database:** Room (local storage)

### Projectstructuur
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

De risicoberekening gebeurt centraal via `RiskCalculator`.

- Deadlines worden opgeslagen als `epochDay`
- Het verschil met de huidige dag bepaalt het risiconiveau
- Deze logica wordt gebruikt voor:
    - kleur van de RiskChip
    - filteren op risico
    - sorteren op risico

---

## 🧪 Wat kan getest worden in de applicatie

- Een taak aanmaken met verschillende deadlines
- Controleren of het risiconiveau automatisch verandert
- Filteren op risico (High / Medium / Low)
- Sorteren op deadline
- Sorteren op risico (High eerst)
- Taken afronden via checkbox
- Taken verwijderen

---

## 👨‍🎓 Auteur

- **Naam:** Adrien Goksel
- **Opleiding:** IT – Android Development
- **School:** Erasmushogeschool Brussel

---

## ✅ Conclusie

StudyMate gaat verder dan een klassieke to-do applicatie door:
- automatisch risico’s te berekenen
- duidelijke visuele feedback te geven via kleuren
- studenten te helpen prioriteiten te stellen

De applicatie combineert **gebruiksvriendelijkheid**, **moderne Android-technologie** en een **duidelijke USP**.

