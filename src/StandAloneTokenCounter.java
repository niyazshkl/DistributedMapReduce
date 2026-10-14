import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StandAloneTokenCounter {
    public static void main (){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter quit or a line of tokens to be processed by spaces");
            String inputString = scanner.nextLine();
            if (inputString.toLowerCase().equals("quit")) break;
            String[] inputArray = inputString.split("\\s+");
            // do the counting manualy per item
            Map<String, Integer> finalMap = new HashMap<>();
            Arrays.stream(inputArray).forEach((item) -> {
                if (!finalMap.containsKey(item)) finalMap.put(item, 1);
                else finalMap.put(item, finalMap.get(item) + 1);
            });
            System.out.println("Output: \n" + finalMap);
        }
        System.out.println("Thank you for your time");
    }
}
