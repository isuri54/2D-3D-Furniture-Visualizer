# 2D-3D Furniture Visualizer

Project folder structure for HCI coursework (Java Swing).

## How to run

**Requirements:** Java 11+ and Maven.

### From terminal (project root)

```bash
mvn compile exec:java
```

### From IntelliJ IDEA

1. **File → Open** → select the `FurnitureVisualizer` folder (the one containing `pom.xml`).
2. Wait for Maven to import.
3. Open `Main.java` and click the green **Run** button next to `main`, or use **Run → Run 'Main.main()'**.

The app opens a window with the Login page.

## Structure

```
src/main/java/com/mycompany/furniturevisualizer/
├── ui/
│   └── pages/
│       ├── LoginPage.java
│       ├── DashboardPage.java
│       ├── CatalogPage.java
│       └── VisualizationPage.java
src/main/resources/
src/test/java/
```
