package com.example.michaelalperovich.schooliscool;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView promptTextView;
    TextView statsTextView;
    TextView timeTextView;
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
    private int day = 0;
    private Random rgen = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        promptTextView = findViewById(R.id.promptTextView);
        statsTextView = findViewById(R.id.statsTextView);
        timeTextView = findViewById(R.id.timeTextView);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        initialize();

    }


    private void timeIncrease() {
        minute += 30;
        if (minute >= 60) {
            hour += minute / 60;
            minute -= 60;
        }
    }

    private void printStats() {
        String tmp = "";
        tmp += "Stress: " + stress;
        tmp += " Energy: " + energy;
        tmp += " Friends: " + friends;
        tmp += " Grades: " + grades;
        statsTextView.setText(tmp);
    }

    private void displayPrompt() {
        promptNum = rgen.nextInt(prompts.size());
        Prompt currentPrompt = prompts.get(promptNum);

        int[] possibleDays = currentPrompt.getPossibleDays();
        int[] possibleTimes = currentPrompt.getPossibleTimes();

        if(!(possibleDays[0] <= day && possibleDays[1] >= day && possibleTimes[0] <= hour && possibleTimes[1] >= hour)){
            displayPrompt();
            return;
        }
        promptTextView.setText(prompts.get(promptNum).toString());
        leftButton.setText(prompts.get(promptNum).getOption(0));
        rightButton.setText(prompts.get(promptNum).getOption(1));
        if (hour == 24) {
            hour = 0;
            day = (day + 1) % 7;
        }
        timeTextView.setText("Time: " + hour + ": " + (minute / 10) + (minute % 10) + "; Day: " + getDayString(day));
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
    	
        stress = Math.max(stress + currentPrompt.getStressChange()[option], 0);
        energy = Math.min(energy + currentPrompt.getEnergyChange()[option], 100);
        friends = Math.min(friends + currentPrompt.getFriendsChange()[option], 100);
        grades = Math.min(grades + currentPrompt.getGradesChange()[option], 100);

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

    public void initialize() {
        promptNum = 0;
        ArrayList<Prompt> prompts = new ArrayList<>();
        stress = 50;
        energy = 50;
        friends = 50;
        grades = 50;
        hour = 6;
        minute = 0;
        initialised = false;
        day = 0;
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
        if (grades > 0 && friends > 0 && energy > 0 && stress < 100) {

            //System.out.println("time passes");
            printStats();
            timeIncrease();
            main();
        }
        else {
            printStats();
            if(grades <= 0){
            	promptTextView.setText("You're grades are suffering so heavily, you're parents have decided to homeschool you...");
            }
            else if(friends <= 0){
            	promptTextView.setText("Being a good friend is important. You didn't do that. You have 0 friends, even on facebook...");
            }
            else if(energy <=  0){
            	promptTextView.setText("You find yourself to tired, to wake up, or move, or go out, or breathe...");
            }
            else if(stress >= 100){
            	promptTextView.setText("Wow this is a lot to handle, you are so stressed out you cannot bring yourself to go to school, do your homework, or see your friends...");
            }
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
        initialize();
    }

    public boolean isWeekday(){

        if(day >= 5){ //0 == monday

            return false;

        }

        return true;
    }

    public String getDayString(int day){

        switch(day){

            case 0:
                return "Monday";
            case 1:
                return "Tuesday";
            case 2:
                return "Wednesday";
            case 3:
                return "Thursday";
            case 4:
                return "Friday";
            case 5:
                return "Saturday";
            case 6:
                return "Sunday";
            default:
                return "Invalid Day";
        }


    }
    
}


