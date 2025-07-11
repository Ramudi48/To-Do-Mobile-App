

## 📱 To-Do Android App

A lightweight Android mobile application built with Java for managing personal to-do lists. This app supports offline functionality, secure local authentication, and CRUD operations using Room Database.

---

### ✅ Features

* **User Authentication**

  * Register with a unique username and password
  * Secure local login
  * Auto-login with SharedPreferences
* **To-Do List Management**

  * Create tasks with title, description, due date, and tag
  * Edit or delete existing tasks
  * Mark tasks as completed
  * Tasks are sorted by due date
* **Data Persistence**

  * Local storage using Room Database
  * Credentials and session stored securely using SharedPreferences
* **User Interface**

  * Material Design UI
  * Responsive layout compatible with various Android screen sizes
  * Dark mode compatible (optional)

---

### 🏗️ Tech Stack

| Layer            | Technology            |
| ---------------- | --------------------- |
| Language         | Java                  |
| UI Framework     | Android SDK + XML     |
| Local DB         | Room (SQLite wrapper) |
| State Management | LiveData              |
| Auth Storage     | SharedPreferences     |

---

### 📸 Screenshots

*(You can add screenshots of login screen, to-do list, and add/edit page here)*

---

### 🚀 Getting Started

#### 1. Clone the repository

```bash
git clone https://github.com/your-username/todo-android-app.git
```

#### 2. Open in Android Studio

* File > Open > Select the project folder
* Let Gradle sync complete

#### 3. Run the app

* Connect an Android device or use the emulator
* Click ▶️ Run

---

### 🔒 Security

* Passwords are stored securely using `SharedPreferences` (can be extended with hashing)
* App works fully offline — no remote storage involved

---

### 📂 Project Structure

```
com.example.myapplication
│
├── activities/
│   ├── LoginActivity.java
│   ├── RegisterActivity.java
│   ├── MainActivity.java
│   └── AddEditTodoActivity.java
│
├── adapter/
│   └── TodoAdapter.java
│
├── database/
│   ├── Todo.java
│   ├── TodoDao.java
│   └── TodoDatabase.java
│
├── utils/
│   └── SharedPrefManager.java
│
└── res/layout/
    ├── activity_login.xml
    ├── activity_register.xml
    ├── activity_main.xml
    ├── activity_add_edit_todo.xml
    └── item_todo.xml
```

---

### 📌 Requirements

* Android Studio (latest version recommended)
* Android SDK 31+
* Java 8 or later

---

### 📖 Requirements Covered (from the PDF)

✅ Register & Login
✅ Local authentication
✅ CRUD for to-do tasks
✅ Local Room storage
✅ Offline support
✅ Material UI
✅ Responsive design
✅ Validation for duplicate usernames

---

### 📃 License

This project is for educational purposes. You can modify it for personal or academic use.

---

Let me know if you'd like me to export this as a downloadable `README.md` file or include screenshots too.
