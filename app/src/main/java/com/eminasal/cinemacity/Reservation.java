package com.eminasal.cinemacity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.telephony.SmsManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import static android.widget.Toast.makeText;

public class Reservation extends Recommendation {

    final int SEND_SMS_PERMISSION_REQUEST_CODE = 111;
    private String  phoneNo = "00387611100";
    private String message = "Postovani, zelim rezervisati kartu za film ";
    private Button sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras= new Bundle();

        String movie = extras.getString("MovieName");

        message = message+movie+". Hvala.";




        Button sendBtn = (Button) findViewById(R.id.button_reservation);
        sendBtn.setEnabled(false);
        if(checkPermission(Manifest.permission.SEND_SMS)) {
            sendBtn.setEnabled(true);
        }else {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},
                    SEND_SMS_PERMISSION_REQUEST_CODE);
        }
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(phoneNo)) {

                    if(checkPermission(Manifest.permission.SEND_SMS)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNo, null, message, null, null);
                        Toast.makeText(Reservation.this, "Succesfully made reservation", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Reservation.this, "Permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }


    private boolean checkPermission(String permission) {
        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
        return (checkPermission == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQUEST_CODE: {
                if(grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    sendBtn.setEnabled(true);
                }
                return;
            }
        }
    }



}
