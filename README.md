# **Reversi Game**
### _An OOP-Based Strategy Game_
#### _Part of OOP course by Ariel University_
###### By: Baruh Ifraimov & Dor Cohen

Reversi is a strategy-based board game implemented using **Object-Oriented Programming (OOP)** principles in Java. This project showcases clean code design, modular development, and problem-solving capabilities, making it an excellent demonstration of my programming skills and understanding of OOP concepts.

---

## **🎮 What is Reversi?**

Reversi (also known as Othello) is a two-player strategy game played on an 8x8 board. Players take turns placing their discs, aiming to flip the opponent's discs to their color by trapping them between their own. The player with the most discs of their color on the board at the end wins.

This project extends the classic Reversi game with unique features:
- **Special Discs**: Includes custom mechanics such as Bomb Discs and Unflippable Discs, adding a strategic twist.
- **AI Integration**: Features AI opponents with varying strategies like Greedy AI and Random AI.
- **User-Friendly GUI**: Designed for an intuitive and enjoyable gameplay experience.

---

## **💡 Purpose of the Project**

This project was developed as part of an Object-Oriented Programming (OOP) course to:
- **Demonstrate mastery of OOP principles** like inheritance, polymorphism, encapsulation, and abstraction.
- **Implement advanced AI strategies** for gameplay automation.
- **Build a complete software application**, including both backend game logic and a graphical user interface.

---

## **🛠️ How It’s Built**

### **1. Core Technologies**
- **Language**: Java
- **GUI Framework**: Java Swing
- **Build Tool**: Compiled and executed using `javac` and `java`

### **2. Architecture**
The project is structured using an OOP-driven approach:
- **Game Logic**: Encapsulates board state, move validation, and game rules.
- **Player Classes**: Abstract player class with concrete implementations for Human and AI players.
- **Disc Variants**: Special discs like Bomb Disc and Unflippable Disc showcase polymorphism.
- **AI Algorithms**: Greedy and Random AI demonstrate AI implementation in games.
- **GUI**: A visually appealing interface designed with Java Swing for intuitive gameplay.

### **3. Features**
- **Player Modes**:
    - Human vs. Human
    - Human vs. AI
    - AI vs. AI (observe how AI strategies perform)
- **Dynamic Gameplay**:
    - Track score in real-time.
    - Experiment with new strategies using special discs.
- **Extensible Design**:
    - Easily add new disc types or AI strategies due to modularity.

---

## **🚀 How to Run**

### **Pre-requisites**
1. **Java Development Kit (JDK)** (version 8 or later) installed.
2. (Optional) A Java IDE like IntelliJ IDEA or Eclipse for exploring the source code.

### **Running the Game**
#### Using the Precompiled JAR
1. Locate the `reversi.jar` file in the project directory.
2. Open a terminal, navigate to the directory, and run:
   ```bash
   java -jar reversi.jar
