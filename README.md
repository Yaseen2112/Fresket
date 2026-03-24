# Grocery List Manager

A modern Java desktop application for managing your groceries with a powerful, responsive UI and MySQL database.

---

## Features

- Add, update, and delete grocery items
- Track **Name**, **Unit** (e.g., kg, litre), **Quantity**, **Price**, and **Category**
- Search and real-time table filtering
- Undo, redo, and save data actions
- User authentication system (if enabled)
- Modern, gradient Java Swing UI (no external images/icons required)
- Works on Windows, Mac, Linux (Java+MySQL required)

---

## Screenshots

*(Add a screenshot here – e.g., use the image from your welcome screen or main app window)*

---

## Prerequisites

- Java 8 or higher (JDK)
- MySQL server
- `mysql-connector-java` JAR (put in `lib/`)
- Your database user/password (edit `DatabaseConnection.java` if needed)

---

## Setup & Run

### 1. Clone the repo:

git clone https://github.com/Yaseen2112/Fresket.git
cd Fresket


### 2. Database Setup

- Import the schema:

mysql -u root -p < grocery_schema.sql

(Edit the user/password/host as needed.)

### 3. Build/Compile:

javac -cp lib/mysql-connector-j-9.4.0.jar -d bin src/com/groceryapp/*.java

### 4. Run the App

**Windows:**
java -cp "bin;lib/mysql-connector-j-9.4.0.jar" com.groceryapp.Main
**Mac/Linux:**
java -cp "bin:lib/mysql-connector-j-9.4.0.jar" com.groceryapp.Main

---

## About Me

**Developer:** Yaseen2112
*I love building meaningful, real-world desktop apps in Java/Swing and helping users manage everyday challenges with stylish, performant software.*

---

## License

MIT License (or choose your own!)

---

## Contributions

PRs and suggestions welcome! Open an issue for any question or feature request.
