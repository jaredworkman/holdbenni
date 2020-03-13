package com.example.benniminni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.example.benniminni.Personality.SpeechRecognition;
import com.example.benniminni.Personality.Movement;
import com.example.benniminni.Helper.RobotInfoSingleton;
import com.example.benniminni.Homeostasis.Levels;
import com.example.benniminni.Personality.HomeostasisFacade;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;


import android.speech.tts.TextToSpeech;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;


public class MainControl extends SpeechRecognition {

    private RobotInfoSingleton robotInfoSingleton;
    private ImageView displayFace;
    private Levels levels;
    private ConversationService myConversationService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set window to full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get personality
        robotInfoSingleton = RobotInfoSingleton.getInstance();

        // Wake up
        setContentView(R.layout.face_activity);
        displayFace = findViewById(R.id.displayFace);


        // Check Homeostasis
        int currentLevels = levels.checkCurrentLevels();

        // Check Auditory (Set up IBM Watson)
        myConversationService =
                new ConversationService(
                        "2018-06-14",
                        "26d62983-4d84-4313-9529-0743be961c3f",
                        "IxWNwTsdl1ZZ"
                );

    }

    public boolean processListenCommand(String command){
        if(command.matches("listen")){
            startListening();
            return true;
        }
        else if(command.matches("stop listening")){
            HomeostasisFacade.getInstance().say("Ok I will stop listening");
            onPause();
            return true;
        }
        else if(command.matches("start chat")){
            HomeostasisFacade.getInstance().say("hey, Jared");
            return true;
        }
        return false;
    }

    @Override
    public void processSpeech(String command) {

        if(robotInfoSingleton.isVoiceControlled()) {
            //Movement.voiceDrive(command);
        }
        else {
            String learnedSpeech = robotInfoSingleton.getLearnedResponse(command);
            if(learnedSpeech == null){
                //IBMProcessSpeech(command);
            }
            else{
                processListenCommand(learnedSpeech);
            }
        }
    }

    public boolean processLevels(String levels){
        if(levels.matches("endorphinesHigh")){
            Log.d(TAG, "process Emotions: " + levels);
            displayFace.setImageResource(R.drawable.happy);
            HomeostasisFacade.getInstance().say("I am happy");
            return true;
        }
        else if(levels.matches("stimulusLow")){
            Log.d(TAG, "process Emotions: " + levels);
            displayFace.setImageResource(R.drawable.bored);
            HomeostasisFacade.getInstance().say("I am bored");
            return true;
        }
        else if(levels.matches("endorphinesHigh")){
            Log.d(TAG, "process Emotions: " + levels);
            displayFace.setImageResource(R.drawable.sad);
            HomeostasisFacade.getInstance().say("I am sad");
            return true;
        }
        else if(levels.matches("stressHigh")){
            Log.d(TAG, "process Emotions: " + levels);
            displayFace.setImageResource(R.drawable.mad);
            HomeostasisFacade.getInstance().say("I am mad");
            return true;
        }
        else if(levels.matches("stressZero")){
            Log.d(TAG, "process Emotions: " + levels);
            displayFace.setImageResource(R.drawable.broken);
            HomeostasisFacade.getInstance().say("I am broken");
            return true;
        }
        return false;
    }

    public boolean processMovement(String movement){
        ArrayList<String> MovementSayings = robotInfoSingleton.getMovmentSayings();

        if(movement.toLowerCase().contains("forward")){
            HomeostasisFacade.getInstance().forward();
            return true;
        }
        else if(movement.toLowerCase().contains("backward") || movement.toLowerCase().contains("back")){
            HomeostasisFacade.getInstance().backward();
            return true;
        }
        else if(movement.toLowerCase().contains("right") ){
            HomeostasisFacade.getInstance().right();
            return true;
        }
        else if(movement.toLowerCase().contains("left") ){
            HomeostasisFacade.getInstance().left();
            return true;
        }
        else if(movement.toLowerCase().matches("stop") ){
            HomeostasisFacade.getInstance().stop();
            return true;
        }
        else if(movement.matches("start drive listening")){
            robotInfoSingleton.setVoiceControlled(true);
            TextToSpeech mTTs = HomeostasisFacade.getInstance().say("Now you can tell me how to drive! Tell me to move forward, backward, left or right!");
            while (mTTs.isSpeaking()){
                continue;
            }
            startListening();
            return true;
        }
        else if(movement.matches("stop drive listening")){
            robotInfoSingleton.setVoiceControlled(false);
            onPause();
            HomeostasisFacade.getInstance().say("Thanks for helping me drive around!");
            return true;
        }
        return false;
    }

    public void IBMProcessSpeech(String message){
        MessageRequest request;
        Log.d(TAG, "IBM Process Speech" + message);
        Map<String, Object> context = robotInfoSingleton.getContext();
        if(context == null) {
            request = new MessageRequest.Builder()
                    .inputText(message)
                    .build();
        }
        else {
            request = new MessageRequest.Builder()
                    .inputText(message)
                    .context(context)
                    .build();
//            int duration = Toast.LENGTH_SHORT;
//            Toast toast = Toast.makeText(this, "this is the context: " + context.toString(), duration);
//            toast.show();
        }



        myConversationService
                .message(getString(R.string.workspace), request)
                .enqueue(new ServiceCallback<MessageResponse>() {
                    @Override
                    public void onResponse(final MessageResponse response) {
                        // More code here
                        final String outputText = response.getText().get(0);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, outputText);
                                TextToSpeech mTTs = HomeostasisFacade.getInstance().say(outputText);

                                robotInfoSingleton.setContext(response.getContext());
                                startListening();
//                                final Handler handler = new Handler();
//                                        handler.postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//
//                                            }
//                                        }, 4500);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {}
                });
    }


}