package mapreduce.controller;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import mapreduce.model.TokenCounterModelInterface;

import java.util.Scanner;

public class TokenCounterController extends AMapReduceTracer implements TokenCounterControllerInterface {

    private TokenCounterModelInterface tokenCounterModelInterface;

    public TokenCounterController(TokenCounterModelInterface tokenCounterModelInterface) {
        this.tokenCounterModelInterface = tokenCounterModelInterface;
    }

    public void handle(String givenString){
        this.tokenCounterModelInterface.setInputString(givenString);
    }

    @Override
    public void control(){
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            traceNumbersPrompt();
            String inputString = scanner.nextLine();
            if (inputString.toLowerCase().equals("quit")) break;
            handle(inputString);
        }
        System.out.println("Thank you for your time");
    }

    @Override
    public String toString() {
        return AMapReduceTracer.CONTROLLER;
    }
}
