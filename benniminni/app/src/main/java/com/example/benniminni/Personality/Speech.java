package com.example.benniminni.Personality;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

public class Speech {

    public final String TAG = "debug";
    private TextToSpeech mTTS;

    public Speech(Context context) {
        mTTS = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        //mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }

    public TextToSpeech say(String message){
        Log.d(TAG, "talking: ");
        float pitch = (float) .85;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) .85;
        if (speed < 0.1) speed = 0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(message, TextToSpeech.QUEUE_FLUSH, null);
        return mTTS;
    }
}
