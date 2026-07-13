# 🎙️ Shabdha Sethu

Shabdha Sethu is an AI-powered Speech Translation Web Application that enables users to convert speech to text, translate text into multiple languages, listen to translated text, and maintain translation history. The application is built using React.js for the frontend and Spring Boot for the backend, with Google Gemini AI handling intelligent translations.


---

## 🚀 Live Demo

### 🌍 Frontend
https://shabdha-sethu.vercel.app

### ⚙️ Backend API
https://shabdha-sethu-backend.onrender.com

---

---

## 🚀 Features

- 🔐 User Registration & Login with JWT Authentication
- 🎤 Speech-to-Text using Web Speech API
- 🤖 AI-powered Translation using Google Gemini API
- 🌍 Multi-language Translation
  - English
  - Telugu
  - Hindi
- 🔊 Text-to-Speech
- 📁 Audio File Upload
- 📜 Translation History
- 🗑️ Clear Translation History
- 📋 Copy Translated Text
- ⬇️ Download Translation as Text File
- 🔄 Language Swap
- ⏳ Loading Animation while Translating
- 📱 Responsive User Interface

---

# 🛠 Tech Stack

## Frontend

- React.js
- JavaScript (ES6)
- HTML5
- CSS3
- Fetch API
- Web Speech API

## Backend

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- JWT Authentication

## Database

- MySQL

## AI Service

- Google Gemini API

## Speech Recognition

- Web Speech API
- VOSK (Offline Speech Recognition)

---

# 📂 Project Structure

```
Shabdha-Sethu
│
├── Frontend
│   ├── src
│   ├── public
│   └── package.json
│
├── Backend
│   ├── src
│   ├── model
│   ├── pom.xml
│   └── application.properties
```

---

# ⚙️ Installation

## Clone Repository

```bash
git clone https://github.com/ChinthaNarendra/Shabdha-Sethu.git
```

---

## Backend

```bash
cd Backend
```

Configure:

```
application.properties
```

Add your

- MySQL Configuration
- Gemini API Key

Run

```bash
mvn spring-boot:run
```

---

## Frontend

```bash
cd Frontend
npm install
npm run dev
```

---

# 💡 Challenges Faced

- Integrating Google Gemini API for accurate translations.
- Handling Roman Telugu input and translating it into meaningful English/Telugu/Hindi.
- Managing asynchronous API calls with loading states.
- Implementing JWT Authentication between React and Spring Boot.
- Maintaining user-specific translation history.
- Integrating Speech Recognition and Text-to-Speech seamlessly.

---

# 📈 Future Enhancements

- Dark / Light Theme
- Delete Individual History Item
- More Language Support
- Voice Selection
- Real-time Conversation Translation
- OCR Image Translation
- Export Translation History as PDF
- Deploy with Docker

---

# 👨‍💻 Author

**Chintha Narendra**

GitHub:
https://github.com/ChinthaNarendra

LinkedIn:
(www.linkedin.com/in/narendra-chintha-912a8a303)

Email:
chinthanarendracn@gmail.com

---

# ⭐ If you like this project

Give this repository a ⭐ on GitHub.
