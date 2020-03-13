package com.example.benniminni.Helper;

import java.util.ArrayList;
import java.util.Map;

public class RobotInfoSingleton {

    private static RobotInfoSingleton instance = null;
    private Boolean isChatting = false;
    private Map<String, Object> context;
    Boolean voiceControlled = false;

    private ArrayList<String> MovmentSayings;
    private ArrayList<String> ChatSayings;

    private ArrayList<String> FaceSayings;


    private RobotInfoSingleton() {
        // No Instantiation
    }

    public static RobotInfoSingleton getInstance() {
        if(instance == null) {
            instance = new RobotInfoSingleton();
        }
        return instance;
    }

    public boolean isVoiceControlled(){
        return false;
    }
    public String getLearnedResponse(String request) {
        String response = "Nope";
        if(request.matches("hey")){
            response = "Hi Jared, I'm Benni";
            return response;
        }
        return response;

    }


    public void setSayings(){
        MovmentSayings = new ArrayList<String>();
        MovmentSayings.add("Weeeee!");
        MovmentSayings.add("This is fun!");
        MovmentSayings.add("I love driving around!");
        MovmentSayings.add("Faster, Faster, Faster!");
        MovmentSayings.add("Don't crash me! You'll break me!");
        MovmentSayings.add("You're pretty good at this!");
        MovmentSayings.add("Thanks for helping me move around!");

        ChatSayings = new ArrayList<String>();
        ChatSayings.add("Lets Chat! Push the Off button to start chatting and ask me a question");
        ChatSayings.add("Thanks for chatting with me, I get so bored! Push the Off button to start chatting with me");
        ChatSayings.add("I love talking! Push the button to start and stop talking with me!");
        ChatSayings.add("Oh boy! A friend to talk with! Push the button to start talking to me!");

        FaceSayings = new ArrayList<String>();
        FaceSayings.add("Hey! That tickled!");
        FaceSayings.add("You touched me!");
        FaceSayings.add("Hey! Thats my face!");
        FaceSayings.add("Don't make me touch your face!");
        FaceSayings.add("STOP IT!!");
        FaceSayings.add("Can you ask me next time before you touch me?");
        FaceSayings.add("Hahahaha!");
        FaceSayings.add("Ugh");
        FaceSayings.add("Pppttbbbb");

    }

    public void setChatting(Boolean chatting) {
        isChatting = chatting;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }

    public ArrayList<String> getMovmentSayings() {
        return MovmentSayings;
    }

    public void setVoiceControlled(Boolean voiceControlled) {
        this.voiceControlled = voiceControlled;
    }

}
