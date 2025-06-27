import java.util.HashMap;
import java.util.Scanner;

public class EmojiMoodResponder {

    public static void main(String[] args) {

        HashMap<String, String> moodMap = new HashMap<>();

        moodMap.put("happy", "😊 Stay smiling! Happiness is contagious.");
        moodMap.put("sad", "😢 It's okay to feel down. Brighter days are ahead.");
        moodMap.put("tired", "😴 Rest is important. Take a break if you need it.");
        moodMap.put("angry", "😠 Deep breaths help. You’ve got this.");
        moodMap.put("excited", "🤩 Love the energy! Keep it up!");
        moodMap.put("nervous", "😬 You're stronger than your anxiety.");
        moodMap.put("bored", "😐 Try something new today—maybe learn a fun fact!");
        moodMap.put("confused", "😕 It’s okay not to have all the answers.");

        Scanner scanner = new Scanner(System.in);

        System.out.print("How are you feeling today? (e.g., happy, sad, tired): ");
        String userMood = scanner.nextLine().toLowerCase().trim();

               if (moodMap.containsKey(userMood)) {
            System.out.println("Your Mood Response: " + moodMap.get(userMood));
        } else {
                        System.out.println("Hmm, I don't recognize that mood. Try something like: happy, sad, tired, excited...");
        }

        scanner.close();
    }
}
