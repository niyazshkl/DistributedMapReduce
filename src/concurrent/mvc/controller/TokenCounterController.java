package concurrent.mvc.controller;

import concurrent.mvc.model.TokenCounterModelInterface;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

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
        traceThreadPrompt();
        int numThreads = scanner.nextInt();
        scanner.nextLine();
        this.tokenCounterModelInterface.setNumThreads(numThreads);
        while (true)
        {
            traceNumbersPrompt();
            String inputString = scanner.nextLine();
            if (inputString.toLowerCase().equals("quit")) break;
            handle(inputString);
        }
        this.tokenCounterModelInterface.terminate();
        traceQuit();
    }

    @Override
    public String toString() {
        return AMapReduceTracer.CONTROLLER;
    }
}
