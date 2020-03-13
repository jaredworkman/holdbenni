package com.example.benniminni.Personality;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.benniminni.Helper.RobotInfoSingleton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Map;

public class SpeechRecognition extends Activity implements RecognitionListener {
    public final static String TAG = "SpeechRecognition";
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private static final int REQUEST_RECORD_PERMISSION = 100;
    private RobotInfoSingleton robotInfoSingleton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(TAG, "Is Speech Rec Ready?" + SpeechRecognizer.isRecognitionAvailable(this));
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


    }

    public void startListening(){
        Map<String, Object> context = robotInfoSingleton.getContext();
        Log.d(TAG, "startListening");
        robotInfoSingleton.setChatting(true);
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_PERMISSION);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speech.startListening(recognizerIntent);
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        robotInfoSingleton.setChatting(false);
        super.onStop();
        if(speech != null) {
            speech.destroy();
            robotInfoSingleton.setContext(null);
            Log.i(TAG, "destroy)");
        }
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.i(TAG, "onBeginningOfSpeech");
    }
    @Override
    public void onBufferReceived(byte[] buffer) { Log.i(TAG, "onBufferReceived: " + buffer);}

    @Override
    public void onEndOfSpeech() { Log.i(TAG, "onEndOfSpeech");}

    @Override
    public void onError(int errorCode){
        String errorMessage = getErrorText(errorCode);
        Log.d(TAG, "FAILED " + errorMessage);
        robotInfoSingleton.setChatting(false);

    }



    @Override
    public void onEvent(int arg0, Bundle arg1) { Log.i(TAG, "onEvent");}

    @Override
    public void onPartialResults(Bundle arg0) { Log.i(TAG, "onPartialResults");}

    @Override
    public void onReadyForSpeech(Bundle arg0) { Log.i(TAG, "onReadyForSpeech");}

    @Override
    public void onResults(Bundle results){
        Log.i(TAG, "onResults)");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Log.i(TAG, matches.get(0));
        String message = matches.get(0);
        processSpeech(message);
    }

    public void processSpeech(String message){}

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i("rms", "onRmsChanged: " + rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}
