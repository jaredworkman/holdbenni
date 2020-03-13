package com.example.benniminni.Personality;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.util.Log;
import android.widget.Toast;


import com.example.benniminni.Helper.RobotInfoSingleton;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class Movement{

        private final String ACTION_USB_PERMISSION = "com.hariharan.arduinousb.USB_PERMISSION";
        private final String TAG = "debug_main4";
        private boolean permissionGranted;

        private UsbManager usbManager;
        private UsbDevice device;
       // private UsbSerialDevice serialPort;
        private UsbDeviceConnection connection;
        private Context context;
        private RobotInfoSingleton robotInfoSingleton;
        private boolean keep = false;




        public void sendArduino(String s) {
//        int duration = Toast.LENGTH_SHORT;
//        Toast toast = Toast.makeText(context, "sending arduno: " + s, duration);
//        toast.show();

            Log.d(TAG, "Sending Arduino: " + s);


        }

    public Boolean voiceDrive(String movement){

        if (movement.toLowerCase().contains("forward")) {
            return true;

        } else if (movement.toLowerCase().contains("backward")){
            return true;

        } else if (movement.toLowerCase().contains("left")) {
            return true;

        } else if (movement.toLowerCase().contains("right")) {
            return true;

        } else if (movement.toLowerCase().contains("stop")) {
            return true;

        } else{
            // respond with an "I don't know that one"
            return true;
        }

    }
}
