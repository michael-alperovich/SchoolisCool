package com.example.michaelalperovich.schooliscool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView promptTextView;
    TextView statsTextView;
    Button leftButton;
    Button rightButton;
    private int promptNum = 0;
    private ArrayList<Prompt> prompts = new ArrayList<>();
    private int stress = 50;
    private int energy = 50;
    private int friends = 50;
    private int grades = 50;
    private int hour = 6;
    private int minute = 0;
    private int choice;
    private boolean initialised = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        promptTextView = findViewById(R.id.promptTextView);
        statsTextView = findViewById(R.id.statsTextView);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        initialise();

    }


    private void timeIncrease() {
        minute += 30;
        if (minute >= 60) {
            hour += minute / 60;
            minute -= 60;
        }
    }

    private void printStats() {
        String tmp = new String();
        tmp += "Stress: " + stress;
        tmp += " Energy: " + energy;
        tmp += " Friends: " + friends;
        tmp += " Grades: " + grades;
        statsTextView.setText(tmp);
    }

    private void displayPrompt() {
        if(promptNum >= prompts.size()){
            promptNum = 0;
        }
        promptTextView.setText(prompts.get(promptNum).toString());
        leftButton.setText(prompts.get(promptNum).getOption(0));
        rightButton.setText(prompts.get(promptNum).getOption(1));
    }

    private void initializePrompts() {
        prompts.add(new Prompt("You did your homework until 1 am. When do you wake up?", "6:00 A.M.", "10:00 A.M.", -5, 10, -15, 5, 0, -5, 10, -10));
        prompts.add(new Prompt("You've been invited to a party! Do you go?", "YES", "NO", 5, 0, 5, -5, 20, -10, -10, 15));
        prompts.add(new Prompt("Spring Season is starting! Do you sign up for a sport?", "YES", "NO", 10, -5, 10, -5, 10, -5, -5, 15));
        prompts.add(new Prompt("Your crush tells you that they really like you. Do you ask them out for a date?", "YES", "NO", 15, 0, 10, -5, 5, -5, -10, 15));
        prompts.add(new Prompt("Your neighbors are going out for the weekend. They ask you if you could babysit their son. Do you?", "YES", "NO", 5, 0, -5, 0, -5, 5, 0, 5));
        prompts.add(new Prompt("There's a lecture in a university in your area that is open to the public. Do you go?", "YES", "NO", 0, 0, -5, 0, -5, 5, 15, -5));
        prompts.add(new Prompt("There's a scholarship available for college. Do you try to get it?", "YES", "NO", 15, -5, -15, 5, -5, 5, -5, 5));
        prompts.add(new Prompt("There's a job opening in your area and you fit the criteria that would be needed to fill it. Do you apply for the job?", "YES", "NO", 15, -5, -10, 10, -10, 10, 15, -5));
    }

    public void choice(int option){

        Prompt currentPrompt = prompts.get(promptNum);
        stress += Math.max(currentPrompt.getStressChange()[option], 0);
        energy += Math.min(currentPrompt.getEnergyChange()[option], 100);
        friends += Math.min(currentPrompt.getFriendsChange()[option], 100);
        grades += Math.min(currentPrompt.getGradesChange()[option], 100);

    }

    public void getButtonPressed(int option){
        choice = option;
        initialised = false;
        if (option != -1) {
            //System.out.println("time passes");
            printStats();
        }
        else {
            System.out.println("You failed");
        }
        main2();
    }

    public void initialise() {
        promptNum = 0;
        ArrayList<Prompt> prompts = new ArrayList<>();
        stress = 50;
        energy = 50;
        friends = 50;
        grades = 50;
        hour = 6;
        minute = 0;
        initialised = false;
        initializePrompts();
        printStats();
        main();
    }

    public void main() {
        initialised = true;

        //System.out.println("Time: " + hour + ": " + (minute / 10) + (minute % 10));
        displayPrompt();
    }

    private void main2() {
        choice(choice);
        promptNum++;
        if (grades > 0 && friends > 0 && energy > 0 && stress < 100) {

            //System.out.println("time passes");
            printStats();
            timeIncrease();
            main();
        }
        else {
            printStats();
            promptTextView.setText("You Failed!");
            return;
        }
    }


    public void onYesClick(View View) {
        if (initialised){
            getButtonPressed(0);
        }
    }

    public void onNoClick(View View) {
        if (initialised) {
            getButtonPressed(1);
        }
    }

    public void onRetryClick(View view) {
        initialise();
    }
}


