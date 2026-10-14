package standalone;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StandAloneTokenCounter extends AMapReduceTracer {
    public void demo (){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            traceNumbersPrompt();
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
