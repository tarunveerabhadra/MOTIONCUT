import java.util.*;
import java.time.*;
import java.time.format.DateTimeParseException;

public class DailyGoalReminder {

        static class Goal {
        String name;
        LocalTime reminderTime;

        Goal(String name, LocalTime reminderTime) {
            this.name = name;
            this.reminderTime = reminderTime;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Goal> goals = new ArrayList<>();

        System.out.println("📋 Welcome to the Daily Goal Reminder!");
        System.out.print("How many goals would you like to set for today? ");

        int numGoals;
        try {
            numGoals = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Exiting program.");
            return;
        }

        for (int i = 0; i < numGoals; i++) {
            System.out.print("\nEnter goal " + (i + 1) + ": ");
            String goalText = scanner.nextLine().trim();

            if (goalText.isEmpty()) {
                System.out.println("⚠️ Goal cannot be empty. Skipping.");
                continue;
            }

            LocalTime reminderTime = null;
            while (reminderTime == null) {
                System.out.print("Enter reminder time for \"" + goalText + "\" (HH:mm, 24-hour): ");
                String timeInput = scanner.nextLine().trim();
                try {
                    reminderTime = LocalTime.parse(timeInput);
                } catch (DateTimeParseException e) {
                    System.out.println("⚠️ Invalid time format. Please use HH:mm (e.g., 08:30 or 14:45).");
                }
            }

            goals.add(new Goal(goalText, reminderTime));
        }

        System.out.println("\n✅ All goals saved! Reminders will pop up at the set times.");
        scheduleReminders(goals);
    }

        private static void scheduleReminders(List<Goal> goals) {
        Timer timer = new Timer();

        for (Goal goal : goals) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime reminderDateTime = LocalDateTime.of(LocalDate.now(), goal.reminderTime);

            if (reminderDateTime.isBefore(now)) {
                System.out.println("⏰ Skipping \"" + goal.name + "\" — time has already passed for today.");
                continue;
            }

            long delay = Duration.between(now, reminderDateTime).toMillis();

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("\n🔔 Reminder: " + goal.name + " (at " + goal.reminderTime + ")");
                }
            }, delay);
        }
    }
}
