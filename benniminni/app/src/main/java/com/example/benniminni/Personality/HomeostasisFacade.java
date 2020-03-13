package com.example.benniminni.Personality;

import android.content.Context;
import android.hardware.usb.UsbManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;


public class HomeostasisFacade {

    public String TAG = "debug_main4";

    Context context;
    Movement movement;
    Speech speech;
    private UsbManager usbManager;


    private static HomeostasisFacade instance = null;

    private HomeostasisFacade() {
        // Exists only to defeat instantiation.
    }

    public static HomeostasisFacade getInstance() {
        if(instance == null) {
            instance = new HomeostasisFacade();
        }
        return instance;
    }

    public void init(Context context, UsbManager usbManager){
        this.usbManager = usbManager;
        Log.d(TAG, "2");
        this.context = context;
       // movement = new Movement(context, usbManager);
        speech = new Speech(context);

        Log.d(TAG, "4");
        //movement.init();
    }


    public TextToSpeech say(String homeostatisFacadeMessage){
        return speech.say(homeostatisFacadeMessage);
    }

    public boolean getPermission(){
        //return movement.getPermission();
        return false;
    }

    public void forward(){
        movement.sendArduino("w");
    }
    public void backward(){
        movement.sendArduino("s");
    }
    public void left(){
        movement.sendArduino("a");
    }
    public void right(){
        movement.sendArduino("d");
    }
    public void stop(){
        movement.sendArduino("z");
    }
    public void getBattery() { movement.sendArduino("v"); }


}