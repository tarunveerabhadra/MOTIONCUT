import java.util.*;
import java.io.*;

class Habit {
    String name;
    String description;
    String frequency; // Daily or Weekly
    List<Boolean> completions; // true for completed, false for missed

    public Habit(String name, String description, String frequency) {
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.completions = new ArrayList<>();
    }

    public void markCompletion(boolean completed) {
        completions.add(completed);
    }

    public double getStrength() {
        int completedDays = 0;
        for (boolean b : completions) {
            if (b) completedDays++;
        }
        if (completions.size() == 0) return 0.0;
        return (completedDays * 100.0) / completions.size();
    }

    public String getFeedback() {
        double strength = getStrength();
        if (strength >= 80) return "Great job! You're consistently keeping up with your habit.";
        else if (strength >= 50) return "Good job! You're making progress, but there's room for improvement.";
        else return "Keep going! Try to be more consistent in completing your habit.";
    }

    public int getStreak() {
        int streak = 0;
        for (int i = completions.size() - 1; i >= 0; i--) {
            if (completions.get(i)) streak++;
            else break;
        }
        return streak;
    }

    public String toFileString() {
        return name + ";" + description + ";" + frequency + ";" + completions.toString();
    }
}

public class HabitStrengthCalculator {
    static Scanner scanner = new Scanner(System.in);
    static List<Habit> habits = new ArrayList<>();
    static final String FILE_NAME = "habits.txt";

    public static void main(String[] args) {
        loadHabits();
        while (true) {
            System.out.println("\n--- Habit Strength Calculator ---");
            System.out.println("1. Create New Habit");
            System.out.println("2. Mark Habit Completion");
            System.out.println("3. View Habit Strength");
            System.out.println("4. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: createHabit(); break;
                case 2: markCompletion(); break;
                case 3: viewStrength(); break;
                case 4: saveHabits(); System.out.println("Progress saved. Goodbye!"); return;
                default: System.out.println("Invalid choice.");
            }
        }
    }

    static void createHabit() {
        System.out.print("Enter habit name: ");
        String name = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter frequency (Daily/Weekly): ");
        String frequency = scanner.nextLine();
        habits.add(new Habit(name, description, frequency));
        System.out.println("Habit created!");
    }

    static void markCompletion() {
        if (habits.isEmpty()) {
            System.out.println("No habits found.");
            return;
        }
        for (int i = 0; i < habits.size(); i++) {
            System.out.println((i + 1) + ". " + habits.get(i).name);
        }
        System.out.print("Select habit number to mark completion: ");
        int index = scanner.nextInt() - 1;
        if (index < 0 || index >= habits.size()) {
            System.out.println("Invalid selection.");
            return;
        }
        System.out.print("Did you complete this habit today? (yes/no): ");
        String input = scanner.next().toLowerCase();
        boolean completed = input.equals("yes");
        habits.get(index).markCompletion(completed);
        System.out.println("Habit marked as " + (completed ? "completed." : "missed."));
    }

    static void viewStrength() {
        if (habits.isEmpty()) {
            System.out.println("No habits found.");
            return;
        }
        for (Habit habit : habits) {
            System.out.println("\nHabit: " + habit.name);
            System.out.printf("Strength: %.2f%%\n", habit.getStrength());
            System.out.println("Feedback: " + habit.getFeedback());
            System.out.println("Streak: " + habit.getStreak() + " day(s)");
        }
    }

    static void saveHabits() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Habit h : habits) {
                writer.write(h.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving habits.");
        }
    }

    static void loadHabits() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";", 4);
                if (parts.length < 4) continue;
                Habit habit = new Habit(parts[0], parts[1], parts[2]);

                String compString = parts[3].replaceAll("[\\[\\] ]", "");
                if (!compString.isEmpty()) {
                    String[] compArray = compString.split(",");
                    for (String s : compArray) {
                        habit.completions.add(Boolean.parseBoolean(s));
                    }
                }
                habits.add(habit);
            }
        } catch (IOException e) {
            System.out.println("Error loading habits.");
        }
    }
}
