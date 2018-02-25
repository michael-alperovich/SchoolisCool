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
    TextView scoreTextView;
    Button leftButton;
    Button rightButton;
    ImageView stressImageView;
    ImageView energyImageView;
    ImageView gradesImageView;
    ImageView friendsImageView;
    private int promptNum = 0;
    private ArrayList<Prompt> prompts = new ArrayList<>();
    private int stress = 30;
    private int energy = 50;
    private int friends = 50;
    private int grades = 50;
    private int hour = 6;
    private int minute = 0;
    private int choice;
    private boolean initialised = false;
    private int day = 0;
    private Random rgen = new Random();
    private int score;
    private Prompt lastPrompt;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        promptTextView = findViewById(R.id.promptTextView);
        statsTextView = findViewById(R.id.statsTextView);
        timeTextView = findViewById(R.id.timeTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        stressImageView = findViewById(R.id.stressImageView);
        energyImageView = findViewById(R.id.energyImageView);
        gradesImageView = findViewById(R.id.gradesImageView);
        friendsImageView = findViewById(R.id.friendsImageView);
        initialize();

    }


    private void timeIncrease() {
        minute += 60;
        if (minute >= 60) {
            hour += minute / 60;
            minute -= 60;
        }
    }

    private void imagePicker(int x, ImageView view) {
        if (view == energyImageView) {
            if (x < 13) energyImageView.setImageResource(R.drawable.energy0);
            else if (x < 38) energyImageView.setImageResource(R.drawable.energy1);
            else if (x < 63) energyImageView.setImageResource(R.drawable.energy2);
            else if (x < 88) energyImageView.setImageResource(R.drawable.energy3);
            else energyImageView.setImageResource(R.drawable.energy4);
        }

        if (view == friendsImageView) {
            if (x < 13) friendsImageView.setImageResource(R.drawable.friends0);
            else if (x < 38) friendsImageView.setImageResource(R.drawable.friends1);
            else if (x < 63) friendsImageView.setImageResource(R.drawable.friends2);
            else if (x < 88) friendsImageView.setImageResource(R.drawable.friends3);
            else friendsImageView.setImageResource(R.drawable.friends4);
        }

        if (view == stressImageView) {
            if (x < 13) stressImageView.setImageResource(R.drawable.stress0);
            else if (x < 38) stressImageView.setImageResource(R.drawable.stress1);
            else if (x < 63) stressImageView.setImageResource(R.drawable.stress2);
            else if (x < 88) stressImageView.setImageResource(R.drawable.stress3);
            else stressImageView.setImageResource(R.drawable.stress4);
        }

        if (view == gradesImageView) {
            if (x < 13) gradesImageView.setImageResource(R.drawable.grade0);
            else if (x < 38) gradesImageView.setImageResource(R.drawable.grade1);
            else if (x < 63) gradesImageView.setImageResource(R.drawable.grade2);
            else if (x < 88) gradesImageView.setImageResource(R.drawable.grade3);
            else gradesImageView.setImageResource(R.drawable.grade4);
        }
    }

    private void printStats() {
        String tmp = "";
        tmp += "Stress: " + stress;
        tmp += " Energy: " + energy;
        tmp += " Grades: " + grades;
        tmp += " Friends: " + friends;
        statsTextView.setText(tmp);
        stressImageView.setImageResource(R.drawable.energy0);
        imagePicker(stress, stressImageView);
        imagePicker(energy, energyImageView);
        imagePicker(grades, gradesImageView);
        imagePicker(friends, friendsImageView);

    }

    private void displayPrompt() {
    	
        promptNum = rgen.nextInt(prompts.size());
        Prompt currentPrompt = prompts.get(promptNum);

        int[] possibleDays = currentPrompt.getPossibleDays();
        int[] possibleTimes = currentPrompt.getPossibleTimes();

        if(!(currentPrompt != lastPrompt && possibleDays[0] <= day && possibleDays[1] >= day && possibleTimes[0] <= hour && possibleTimes[1] >= hour)){
            displayPrompt();
            return;
        }
        lastPrompt = currentPrompt;
        
        promptTextView.setText(prompts.get(promptNum).toString());
        leftButton.setText(prompts.get(promptNum).getOption(0));
        rightButton.setText(prompts.get(promptNum).getOption(1));
        if (hour == 24) {
            hour = 0;
            day = (day + 1) % 7;
        }
        timeTextView.setText("Time: " + hour + ": " + (minute / 10) + (minute % 10) + "; Day: " + getDayString(day));
        scoreTextView.setText("Score: " + score);
    }

    private void initializePrompts() {
  	  prompts.add(new Prompt("You're friend is skipping English class to hit up Chipotle, do you go with him?", "YES", "NO", 5, -5, 5, 0, 10, -5, -5, 10, 8, 15, 0, 4));
  	  prompts.add(new Prompt("You did your homework until 1 am. When do you wake up?", "6:00 A.M.", "10:00 A.M.", -5, 10, -15, 5, 0, -5, 10, -10, 21, 24, 0, 4));
  	  prompts.add(new Prompt("You've been invited to a party! Do you go?", "YES", "NO", 5, 0, 5, -5, 20, -10, -10, 15, 5, 24, 5, 6));
	  prompts.add(new Prompt("You had a nightmare where you dreamed you had a suprise test and failed the test.", "OK", "OK", 5, 5, 5, 5, 0, 0, 0, 0, 6, 24, 0, 6));
	  prompts.add(new Prompt("You walk into your Math class to find you are taking a pop quiz you haven't studied for. ", "OK", "OK", 5, 5, -5, -5, 0, 0, -10, -10, 8, 15, 0, 4));
  	  prompts.add(new Prompt("Spring Season is starting! Do you sign up for a sport?", "YES", "NO", 5, -5, 10, -5, 10, -5, -5, 15, 0, 24, 0, 6));
  	  prompts.add(new Prompt("Your crush tells you that they really like you. Do you ask them out for a date?", "YES", "NO", 10, 0, 10, -5, 5, -5, -10, 15, 8, 24, 0, 6));
  	  prompts.add(new Prompt("Your neighbors are going out for the weekend. They ask you if you could babysit their son. Do you?", "YES", "NO", 5, 0, -5, 0, -5, 5, 0, 5, 8, 24, 4, 6));
  	  prompts.add(new Prompt("There's a lecture in a university in your area that is open to the public. Do you go?", "YES", "NO", 0, 0, -5, 0, -5, 5, 15, -5, 3, 20, 0, 6));
  	  prompts.add(new Prompt("There's a scholarship available for college. Do you try to get it?", "YES", "NO", 10, -5, -15, 5, -5, 5, -5, 5, 3, 8, 0, 6));
	  prompts.add(new Prompt("Your bus is late. Yeah nothing you can do about it...", "OK", "OK", 5, 5, 0, 0, 0, 0, -5, -5, 8, 9, 0, 4));
	  prompts.add(new Prompt("A random dog ran up to you and ate your homework. Your teacher doesn't believe you", "OK", "OK", 5, 10, 0, 0, 0, 0, -5, -5, 8, 9, 0, 4));  	  
  	  prompts.add(new Prompt("There's a job opening in your area and you fit the criteria that would be needed to fill it. Do you apply for the job?", "YES", "NO", 10, -5, -10, 10, -10, 10, 15, -5, 3, 8, 0, 6));
  	  prompts.add(new Prompt("Do you sign up for an educational summer program?","YES", "SOUNDS LAME", 5, -5, -5, -5, -5, 15, 15, -5, 8, 24, 0, 4));
  	  prompts.add(new Prompt("Do you join the school's Science Team?", "YES", "NO", 10, -5, -5, 0, -5, 0, 5, 0, 8, 24, 0, 4));
  	  prompts.add(new Prompt("Do you join the school's Debate Team?", "YES", "NO", 5, -5, -5, 0, -5, 0, 5, 0, 8, 24, 0, 4));
  	  prompts.add(new Prompt("Your nerdy friend invites you to go to MAHacks", "HELL YEAH", "EW NERDS", 5, -10, -15, 5, 15, -15, 20, 0, 8, 15, 0, 4));
	  prompts.add(new Prompt("Teacher asks you to stay after school", "OK", "OK", 10, 10, -5, -5, -5, -5, 10, 10, 8, 15, 0, 6));
	  prompts.add(new Prompt("You drop your laptop and the screen cracks", "OK", "OK", 0, 0, 0, 0, 0, 0, -10, -10, 8, 17, 0, 6));
	  prompts.add(new Prompt("You lose your phone", "OK", "OK", 15, 15, -5, -5, -10, -10, -5, -5, 6, 24, 0, 6));
	  prompts.add(new Prompt("Fire drill during your studyblock while you were finishing a last minute essay", "OK", "OK", 5, 5, -5, -5, 0, 0, -5, -5, 8, 15, 0, 4));
  	  prompts.add(new Prompt("Its a holiday and family are coming over, do you join?", "YES", "NO", -5, 10, -15, 10, -5, 5, 0, 5, 8, 24, 4, 6));
  	  prompts.add(new Prompt("Online friends invite you to a World of Warcraft raid, do you join?", "HELL YEAH", "NO", -5, 5, -10, 5, 15, -15, -20, 5, 8, 24, 4, 6));
  	  prompts.add(new Prompt("Your friends parents aren't home and they invite you to drink, do you go now?", "YES", "NO", -15, 5, 15, -5, 5, -15, -20, 15, 20, 24, 0, 6));
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
        prompts = new ArrayList<>();
        stress = 30;
        energy = 50;
        friends = 50;
        grades = 50;
        hour = 6;
        minute = 0;
        initialised = false;
        day = 0;
        score = 0;
        initializePrompts();
        lastPrompt = prompts.get(0);
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
            score++;
            main();
        }
        else {
            printStats();
            if(grades <= 0){
            	promptTextView.setText("You're grades are suffering so heavily, you're parents have decided to homeschool you... you lost. \nMake sure to study and work hard, it will pay off!");
            }
            else if(friends <= 0){
            	promptTextView.setText("Being a good friend is important. You didn't do that. You have 0 friends, even on facebook... You lost :(");
            }
            else if(energy <=  0){
            	promptTextView.setText("You find yourself too tired, to wake up, or move, or go out, or breathe... You lost :(");
            }
            else if(stress >= 100){
            	promptTextView.setText("Wow this is a lot to handle, you are so stressed out you cannot bring yourself to go to school, do your homework, or see your friends... You lost :(");
            }
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

        return day >= 4;
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


