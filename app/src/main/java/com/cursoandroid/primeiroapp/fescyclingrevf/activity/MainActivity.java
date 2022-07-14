/*

        The MIT License (MIT)

        Copyright (c) 2021 FES-CYCLING PROJECT LEB-UFMG

        Permission is hereby granted, free of charge, to any person obtaining a copy
        of this software and associated documentation files (the "Software"), to deal
        in the Software without restriction, including without limitation the rights
        to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
        copies of the Software, and to permit persons to whom the Software is
        furnished to do so, subject to the following conditions:

        The above copyright notice and this permission notice shall be included in
        all copies or substantial portions of the Software.

        THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
        IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
        FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
        AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
        LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
        OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
        THE SOFTWARE.

        =============================================================================

        FES-CYCLING PROJECT LEB-UFMG
        Laboratório de Engenharia Biomédia da Universidade Federal de Minas Gerais
        Author: Tiago Coelho Magalhães
        date: April 26, 2021

        Please cite our work as reference.

        */

package com.cursoandroid.primeiroapp.fescyclingrevf.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cursoandroid.primeiroapp.fescyclingrevf.R;
import com.cursoandroid.primeiroapp.fescyclingrevf.classes.Channel;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public Button buttonCH1, buttonCH2, buttonCH3, buttonCH4;
    public Button buttonPlus5mA, buttonPlus2mA, buttonPlus1mA;
    public Button buttonMinus5mA, buttonMinus2mA, buttonMinus1mA;
    public Button buttonPlus50us, buttonMinus50us;
    public Button buttonPlus1Rpm, buttonMinus1Rpm;
    public TextView textChannel1rParameters, textChannel1lParameters;
    public TextView textChannel2rParameters, textChannel2lParameters;
    public TextView textChannel3rParameters, textChannel3lParameters;
    public TextView textChannel4rParameters, textChannel4lParameters;
    public TextView textChannel1rParamAngle, textChannel1lParamAngle;
    public TextView textChannel2rParamAngle, textChannel2lParamAngle;
    public TextView textChannel3rParamAngle, textChannel3lParamAngle;
    public TextView textChannel4rParamAngle, textChannel4lParamAngle;
    public TextView textChannel1rParamAmpWdt, textChannel1lParamAmpWdt;
    public TextView textChannel2rParamAmpWdt, textChannel2lParamAmpWdt;
    public TextView textChannel3rParamAmpWdt, textChannel3lParamAmpWdt;
    public TextView textChannel4rParamAmpWdt, textChannel4lParamAmpWdt;
    public TextView textActualAngle, textActualVelocity;
    ToggleButton buttonConn, buttonSetUpParam;
    RadioButton radioButtonAutomatic, radioButtonManual, radioButtonControl;
    RadioGroup radioButtonGroup;
    private boolean fesStartedFlag;
    private InputStream inputStream;
    private Chronometer fesChronometer;
    private EditText textSetPoint;

    //Bluetooth
    private OutputStream outputStream;
    BluetoothSocket btSocket;
    private BluetoothDevice hC05;
    private BluetoothAdapter btAdapter;
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static boolean isModuleConnected = false;

    //FES-cycling variables
    Channel channelParameters[] = new Channel[8];
    static int setPointValue = 40;
    private int mode = 0;
    public SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCH1 = findViewById(R.id.buttonCH1);
        buttonCH2 = findViewById(R.id.buttonCH2);
        buttonCH3 = findViewById(R.id.buttonCH3);
        buttonCH4 = findViewById(R.id.buttonCH4);
        textChannel1rParameters = findViewById(R.id.textChannel1rParameters);
        textChannel1lParameters = findViewById(R.id.textChannel1lParameters);
        textChannel2rParameters = findViewById(R.id.textChannel2rParameters);
        textChannel2lParameters = findViewById(R.id.textChannel2lParameters);
        textChannel3rParameters = findViewById(R.id.textChannel3rParameters);
        textChannel3lParameters = findViewById(R.id.textChannel3lParameters);
        textChannel4rParameters = findViewById(R.id.textChannel4rParameters);
        textChannel4lParameters = findViewById(R.id.textChannel4lParameters);
        textChannel1rParamAngle = findViewById(R.id.textChannel1rParamAngle);
        textChannel1lParamAngle = findViewById(R.id.textChannel1lParamAngle);
        textChannel2rParamAngle = findViewById(R.id.textChannel2rParamAngle);
        textChannel2lParamAngle = findViewById(R.id.textChannel2lParamAngle);
        textChannel3rParamAngle = findViewById(R.id.textChannel3rParamAngle);
        textChannel3lParamAngle = findViewById(R.id.textChannel3lParamAngle);
        textChannel4rParamAngle = findViewById(R.id.textChannel4rParamAngle);
        textChannel4lParamAngle = findViewById(R.id.textChannel4lParamAngle);
        textChannel1rParamAmpWdt = findViewById(R.id.textChannel1rParamAmpWdt);
        textChannel1lParamAmpWdt = findViewById(R.id.textChannel1lParamAmpWdt);
        textChannel2rParamAmpWdt = findViewById(R.id.textChannel2rParamAmpWdt);
        textChannel2lParamAmpWdt = findViewById(R.id.textChannel2lParamAmpWdt);
        textChannel3rParamAmpWdt = findViewById(R.id.textChannel3rParamAmpWdt);
        textChannel3lParamAmpWdt = findViewById(R.id.textChannel3lParamAmpWdt);
        textChannel4rParamAmpWdt = findViewById(R.id.textChannel4rParamAmpWdt);
        textChannel4lParamAmpWdt = findViewById(R.id.textChannel4lParamAmpWdt);
        radioButtonAutomatic = findViewById(R.id.radioButtonAutomatic);
        radioButtonManual = findViewById(R.id.radioButtonManual);
        radioButtonControl = findViewById(R.id.radioButtonControl);
        radioButtonGroup = findViewById(R.id.radioButtonGroup);
        fesStartedFlag = false;
        buttonPlus50us = findViewById(R.id.buttonPlus50us);
        buttonMinus50us = findViewById(R.id.buttonMinus50us);
        buttonPlus1mA = findViewById(R.id.buttonPlus1mA);
        buttonPlus2mA = findViewById(R.id.buttonPlus2mA);
        buttonPlus5mA = findViewById(R.id.buttonPlus5mA);
        buttonMinus1mA = findViewById(R.id.buttonMinus1mA);
        buttonMinus2mA = findViewById(R.id.buttonMinus2mA);
        buttonMinus5mA = findViewById(R.id.buttonMinus5mA);
        buttonPlus1Rpm = findViewById(R.id.buttonPlus1Rpm);
        buttonMinus1Rpm = findViewById(R.id.buttonMinus1Rpm);
        textActualVelocity = findViewById(R.id.textActualVelocity);
        textActualAngle = findViewById(R.id.textActualAngle);
        fesChronometer = (Chronometer) findViewById(R.id.chronometer1);
        textSetPoint = findViewById(R.id.textSetPoint);

        getSupportActionBar().setElevation(0);

        //DEFAULT CONFIGURATIONS FOR CHANNEL 1 RIGHT
        channelParameters[0] = new Channel(
                0,
                35,             //frequency
                500,                //pulse width
                40,//45,             //amplitude
                160,//215,            //start
                260,//315,            //end
                false               //state
        );
        //DEFAULT CONFIGURATIONS FOR CHANNEL 1 LEFT
        channelParameters[1] = new Channel(
                1,
                35,
                500,
                45,//50,
                340,//35,
                80,//135,
                false
        );
        //DEFAULT CONFIGURATIONS FOR CHANNEL 2 RIGHT
        channelParameters[2] = new Channel(
                2,
                35,
                500,
                40,//25,
                240,//295,
                300,//355,
                false
        );
        //DEFAULT CONFIGURATIONS FOR CHANNEL 2 LEFT
        channelParameters[3] = new Channel(
                3,
                35,
                500,
                45,//30,
                60,//115,
                120,//175,
                false
        );
        //DEFAULT CONFIGURATIONS FOR CHANNEL 3 RIGHT
        channelParameters[4] = new Channel(
                4,
                35,
                500,
                40,//50,
                330,
                70,
                false
        );
        //DEFAULT CONFIGURATIONS FOR CHANNEL 3 LEFT
        channelParameters[5] = new Channel(
                5,
                35,
                500,
                45,//50,
                150,
                250,
                false
        );
        //DEFAULT CONFIGURATIONS FOR CHANNEL 4 RIGHT
        channelParameters[6] = new Channel(
                6,
                35,
                500,
                40,//35,
                280,//335,
                40,//95,
                false
        );
        //DEFAULT CONFIGURATIONS FOR CHANNEL 4 LEFT
        channelParameters[7] = new Channel(
                7,
                35,
                500,
                45,//40,
                100,//155,
                220,//275,
                false
        );

        displayUpdateThread();    //update parameters on Screen

        //SCREEN TRANSITIONS
        buttonCH1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ch1ConfigActivity.class);
                intent.putExtra("channel1parametersR", channelParameters[0]);
                intent.putExtra("channel1parametersL", channelParameters[1]);
                startActivityForResult(intent, 1);
            }
        });
        buttonCH2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ch2ConfigActivity.class);
                intent.putExtra("channel2parametersR", channelParameters[2]);
                intent.putExtra("channel2parametersL", channelParameters[3]);
                startActivityForResult(intent, 2);
            }
        });
        buttonCH3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ch3ConfigActivity.class);
                intent.putExtra("channel3parametersR", channelParameters[4]);
                intent.putExtra("channel3parametersL", channelParameters[5]);
                startActivityForResult(intent, 3);
            }
        });
        buttonCH4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Ch4ConfigActivity.class);
                intent.putExtra("channel4parametersR", channelParameters[6]);
                intent.putExtra("channel4parametersL", channelParameters[7]);
                startActivityForResult(intent, 4);
            }
        });

        //GLOBAL PARAMETERS (PULSE ADJUSTMENT)
        buttonPlus50us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, x;
                for (i = 0; i < 8; i++) {
                    x = channelParameters[i].getChannelWidth();
                    if (x <= 550) channelParameters[i].setChannelWidth(x + 50);
                    else channelParameters[i].setChannelWidth(600);
                }
                if (fesStartedFlag) sendParameters(true);
                displayUpdateThread();
            }
        });
        buttonMinus50us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, x;
                for (i = 0; i < 8; i++) {
                    x = channelParameters[i].getChannelWidth();
                    if (x >= 300) channelParameters[i].setChannelWidth(x - 50);
                    else channelParameters[i].setChannelWidth(250);
                }
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(true);
            }
        });
        //buttonPlus5mA = findViewById(R.id.buttonPlus5mA);
        buttonPlus5mA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, x;
                for (i = 0; i < 8; i++) {
                    x = channelParameters[i].getChannelAmplitude();
                    if (x > 95) channelParameters[i].setChannelAmplitude(100);
                    else channelParameters[i].setChannelAmplitude(x + 5);
                }
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(true);
            }
        });
        //buttonPlus2mA = findViewById(R.id.buttonPlus2mA);
        buttonPlus2mA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, x;
                for (i = 0; i < 8; i++) {
                    x = channelParameters[i].getChannelAmplitude();
                    if (x > 98) channelParameters[i].setChannelAmplitude(100);
                    else channelParameters[i].setChannelAmplitude(x + 2);
                }
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(true);
            }
        });
        //buttonPlus1mA = findViewById(R.id.buttonPlus1mA);
        buttonPlus1mA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, x;
                for (i = 0; i < 8; i++) {
                    x = channelParameters[i].getChannelAmplitude();
                    if (x < 100) channelParameters[i].setChannelAmplitude(x + 1);
                }
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(true);
            }
        });
        //buttonMinus5mA = findViewById(R.id.buttonMinus5mA);
        buttonMinus5mA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, x;
                for (i = 0; i < 8; i++) {
                    x = channelParameters[i].getChannelAmplitude();
                    if (x > 5) channelParameters[i].setChannelAmplitude(x - 5);
                    else channelParameters[i].setChannelAmplitude(0);
                }
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(true);
            }
        });
        //buttonMinus2mA = findViewById(R.id.buttonMinus2mA);
        buttonMinus2mA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, x;
                for (i = 0; i < 8; i++) {
                    x = channelParameters[i].getChannelAmplitude();
                    if (x > 2) channelParameters[i].setChannelAmplitude(x - 2);
                    else channelParameters[i].setChannelAmplitude(0);
                }
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(true);
            }
        });
        //buttonMinus1mA = findViewById(R.id.buttonMinus1mA);
        buttonMinus1mA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i, x;
                for (i = 0; i < 8; i++) {
                    x = channelParameters[i].getChannelAmplitude();
                    if (x >= 1) channelParameters[i].setChannelAmplitude(x - 1);
                }
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(true);
            }
        });

        //GLOBAL PARAMETERS (CADENCE TRANCKING)
        //buttonPlus1Rpm = findViewById(R.id.buttonPlus1Rpm);
        buttonPlus1Rpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setPointValue<60)setPointValue++;
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(false);
            }
        });
        //buttonMinus1Rpm = findViewById(R.id.buttonMinus1Rpm);
        buttonMinus1Rpm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(setPointValue>20)setPointValue--;
                displayUpdateThread();
                if (fesStartedFlag) sendParameters(false);
            }
        });

        //START FES
        buttonSetUpParam = findViewById(R.id.buttonSetUpParam);
        buttonSetUpParam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textMessages = findViewById(R.id.textMessages);
                if (isModuleConnected) {
                    //if it is already connected, check if it is a stop or start FES-condition
                    if (buttonSetUpParam.isChecked() == true) {
                        //start FES
                        fesStartedFlag = true;
                        textMessages.setText("FES started...");
                        textMessages.setTextColor(Color.BLUE);
                        fesChronometer.setBase(SystemClock.elapsedRealtime());
                        fesChronometer.start();
                        sendParameters(true);
                    } else {
                        //stop FES
                        fesStartedFlag = false;
                        fesChronometer.stop();
                        textMessages.setText("FES stoped...");
                        textMessages.setTextColor(Color.RED);
                        try {
                            for (int i = 0; i < 78; i++) outputStream.write((byte) 0);
                            outputStream.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    buttonSetUpParam.toggle();
                    textMessages.setTextColor(Color.RED);
                    textMessages.setText("Set up a connection first");
                }
            }
        });
        //CONNECTION TO BLUETOOTH MODULE
        buttonConn = findViewById(R.id.buttonConnect);
        buttonConn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textMessages = findViewById(R.id.textMessages);
                textMessages.setText("Trying to connect...");
                textMessages.setTextColor(Color.YELLOW);

                btAdapter = BluetoothAdapter.getDefaultAdapter();
                if (btAdapter == null) {
                    // Device does not support Bluetooth
                } else {
                    if (btAdapter.isEnabled()) {
                        // Device Bluetooth is enabled
                        // Here you will have to update the MAC adddress of your module
                        // or implement a connection screen to facilitate this proccess.
                        //System.out.println("FesMsg: Result = " + btAdapter.getBondedDevices());
                        //hC05 = btAdapter.getRemoteDevice("20:16:10:31:92:63"); //EstimuladorTiago
                        hC05 = btAdapter.getRemoteDevice("20:13:09:26:01:02"); //EstimuladorTRIKE
                        //hC05 = btAdapter.getRemoteDevice("FC:A8:9A:00:2A:1E");//PLACA NOVA TiTi-FEScyc
                        //System.out.println("FesMsg: Name = " + hC05.getName());

                        if (!isModuleConnected) {
                            btSocket = null;
                            int counterTries;
                            counterTries = 0;

                            do {
                                try {
                                    btSocket = hC05.createRfcommSocketToServiceRecord(mUUID);
                                    //System.out.println("FesMsg: Socket = " + btSocket);
                                    btSocket.connect();
                                    if (btSocket.isConnected()) {
                                        isModuleConnected = true;
                                        outputStream = btSocket.getOutputStream();
                                    } else isModuleConnected = false;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                counterTries++;
                                //System.out.println("FesMsg: trying...#" + counterTries);
                            } while (counterTries < 2 && isModuleConnected == false);

                            if (isModuleConnected == true) {
                                buttonConn.setTextOn("Connected");
                                //System.out.println("FesMsg: Connected to " + hC05.getName());
                                textMessages.setText("Connected to " + hC05.getName());
                                textMessages.setTextColor(getResources().getColor(R.color.greenON));
                            } else {
                                buttonConn.toggle();
                                //setTextOn("Click to Connect");
                                //System.out.println("FesMsg: Connection Failed");
                                textMessages.setText("Connection Failed");
                                textMessages.setTextColor(Color.RED);
                                try {
                                    btSocket.close();
                                    //System.out.println("FesMsg: disconnecting from module");
                                    textMessages.setText("Disconnected");
                                    textMessages.setTextColor(Color.BLACK);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {  //if it is already connected, disconnect...
                            //stop FES
                            fesStartedFlag = false;
                            textMessages.setText("FES stoped...");
                            textMessages.setTextColor(Color.RED);
                            if(buttonSetUpParam.isChecked())buttonSetUpParam.toggle();
                            try {
                                //for (int i = 0; i < 78; i++) outputStream.write(0);
                                outputStream.write((byte) 0);
                                outputStream.write((byte) 0);       //off
                                outputStream.write((byte) 0);
                                outputStream.write((byte) setPointValue);   //rpm cadence

                                int j, aux=0;
                                for(j=0;j<8;j++)
                                {
                                    if(channelParameters[j].isChannelState())outputStream.write((byte) 1);  //CH ON
                                    else outputStream.write((byte) 0);                                      //CH OFF
                                    outputStream.write((byte) channelParameters[j].getChannelAmplitude());     //CH AMP
                                    outputStream.write((byte) channelParameters[j].getChannelFrequency());     //CH FREQ
                                    aux = channelParameters[j].getChannelWidth();
                                    outputStream.write((byte) (aux>>8));                                     //CH Width(+)
                                    outputStream.write((byte) aux);                                           //CH Width(-)
                                    aux = channelParameters[j].getChannelStartAngle();
                                    aux*=2;
                                    outputStream.write((byte) (aux>>8));                                     //CH Start(+)°
                                    outputStream.write((byte) aux);                                           //CH Start(-)°
                                    aux = channelParameters[j].getChannelFinishAngle();
                                    aux*=2;
                                    outputStream.write((byte) (aux>>8));                                     //CH Finish(+)°
                                    outputStream.write((byte) aux);                                           //CH Finish(-)°
                                }
                                outputStream.write((byte) 0);
                                outputStream.write((byte) 1);
                                outputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            isModuleConnected = false;
                            try {
                                btSocket.close();
                                //System.out.println("FesMsg: disconnecting from module");
                                textMessages.setText("Disconnected");
                                textMessages.setTextColor(Color.BLACK);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        // Bluetooth is not enabled
                        textMessages.setText("Turn ON the Bluetooth on your device");
                        textMessages.setTextColor(Color.RED);
                        buttonConn.setChecked(false);
                    }
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // Keep listening to the Bluetooth InputStream until an exception occurs
                while (true) {
                    //System.out.println("\n\rFesMsg: ");
                    if(isModuleConnected)
                    {
                        byte[] receivedData = new byte[13];     //same size as firmware
                        int angle = 0;
                        float cadence;
                        inputStream = null;
                        try {
                            inputStream = btSocket.getInputStream();
                            inputStream.skip(inputStream.available());
                            for (int i = 0; i<13; i++) {        //same size as firmware
                                receivedData[i] = (byte) inputStream.read();
                                if(i==0)System.out.println("\n\rFesMsg:");
                                System.out.println(" "+String.valueOf(receivedData[i]));
                            }
                            //System.out.println("FesMsg: end");
                            if(receivedData[0]==2){
                                cadence = receivedData[2];
                                cadence /= 100;
                                cadence += receivedData[1];
                                textActualVelocity.setText("Cadence: "+String.valueOf(receivedData[1])+"."+String.valueOf(receivedData[2])+" [rpm]");
                                angle = receivedData[3];
                                angle = angle<<8;
                                //angle *=2;
                                angle +=(receivedData[4]);//*2;
                                if(angle<0)angle+=256;
                                textActualAngle.setText("Actual angle: "+String.valueOf(angle)+"°");
                                if(((mode == 3)||(mode == 2))&&(fesStartedFlag))
                                {
                                    for (int i = 0; i < 8; i++) {
                                        channelParameters[i].setChannelAmplitude(receivedData[i + 5]);
                                    }
                                    //create datase and save data
                                    try{
                                        //create database
                                        bancoDados = openOrCreateDatabase("FesCyclingRevF.db", MODE_PRIVATE, null);
                                        //create table
                                        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cycling (time TEXT, setpoint INT(3), angle INT(3), anglegroup INT(1), speed REAL, mode INT(1), " +
                                                "ch1rstate INT(1), ch1rstartangle INT(3), ch1rfinishangle INT(3), ch1rfreq INT(3), ch1rpw INT(3), ch1ramp INT(3), " +
                                                "ch1lstate INT(1), ch1lstartangle INT(3), ch1lfinishangle INT(3), ch1lfreq INT(3), ch1lpw INT(3), ch1lamp INT(3), " +
                                                "ch2rstate INT(1), ch2rstartangle INT(3), ch2rfinishangle INT(3), ch2rfreq INT(3), ch2rpw INT(3), ch2ramp INT(3), " +
                                                "ch2lstate INT(1), ch2lstartangle INT(3), ch2lfinishangle INT(3), ch2lfreq INT(3), ch2lpw INT(3), ch2lamp INT(3), " +
                                                "ch3rstate INT(1), ch3rstartangle INT(3), ch3rfinishangle INT(3), ch3rfreq INT(3), ch3rpw INT(3), ch3ramp INT(3), " +
                                                "ch3lstate INT(1), ch3lstartangle INT(3), ch3lfinishangle INT(3), ch3lfreq INT(3), ch3lpw INT(3), ch3lamp INT(3), " +
                                                "ch4rstate INT(1), ch4rstartangle INT(3), ch4rfinishangle INT(3), ch4rfreq INT(3), ch4rpw INT(3), ch4ramp INT(3), " +
                                                "ch4lstate INT(1), ch4lstartangle INT(3), ch4lfinishangle INT(3), ch4lfreq INT(3), ch4lpw INT(3), ch4lamp INT(3))");

                                        Date currentTime = Calendar.getInstance().getTime();
                                        //data: channel 1
                                        int c1rStA = channelParameters[0].getChannelStartAngle();
                                        int c1rFnA = channelParameters[0].getChannelFinishAngle();
                                        int c1rSte = 0;
                                        if(channelParameters[0].isChannelState())c1rSte=1;
                                        int c1rFrq = channelParameters[0].getChannelFrequency();
                                        int c1rPwd = channelParameters[0].getChannelWidth();
                                        int c1rAmp = channelParameters[0].getChannelAmplitude();

                                        int c1lStA = channelParameters[1].getChannelStartAngle();
                                        int c1lFnA = channelParameters[1].getChannelFinishAngle();
                                        int c1lSte = 0;
                                        if(channelParameters[1].isChannelState())c1lSte=1;
                                        int c1lFrq = channelParameters[1].getChannelFrequency();
                                        int c1lPwd = channelParameters[1].getChannelWidth();
                                        int c1lAmp = channelParameters[1].getChannelAmplitude();

                                        //data: channel 2
                                        int c2rStA = channelParameters[2].getChannelStartAngle();
                                        int c2rFnA = channelParameters[2].getChannelFinishAngle();
                                        int c2rSte = 0;
                                        if(channelParameters[2].isChannelState())c2rSte=1;
                                        int c2rFrq = channelParameters[2].getChannelFrequency();
                                        int c2rPwd = channelParameters[2].getChannelWidth();
                                        int c2rAmp = channelParameters[2].getChannelAmplitude();

                                        int c2lStA = channelParameters[3].getChannelStartAngle();
                                        int c2lFnA = channelParameters[3].getChannelFinishAngle();
                                        int c2lSte = 0;
                                        if(channelParameters[3].isChannelState())c2lSte=1;
                                        int c2lFrq = channelParameters[3].getChannelFrequency();
                                        int c2lPwd = channelParameters[3].getChannelWidth();
                                        int c2lAmp = channelParameters[3].getChannelAmplitude();

                                        //data: channel 3
                                        int c3rStA = channelParameters[4].getChannelStartAngle();
                                        int c3rFnA = channelParameters[4].getChannelFinishAngle();
                                        int c3rSte = 0;
                                        if(channelParameters[4].isChannelState())c3rSte=1;
                                        int c3rFrq = channelParameters[4].getChannelFrequency();
                                        int c3rPwd = channelParameters[4].getChannelWidth();
                                        int c3rAmp = channelParameters[4].getChannelAmplitude();

                                        int c3lStA = channelParameters[5].getChannelStartAngle();
                                        int c3lFnA = channelParameters[5].getChannelFinishAngle();
                                        int c3lSte = 0;
                                        if(channelParameters[5].isChannelState())c3lSte=1;
                                        int c3lFrq = channelParameters[5].getChannelFrequency();
                                        int c3lPwd = channelParameters[5].getChannelWidth();
                                        int c3lAmp = channelParameters[5].getChannelAmplitude();

                                        //data: channel 4
                                        int c4rStA = channelParameters[6].getChannelStartAngle();
                                        int c4rFnA = channelParameters[6].getChannelFinishAngle();
                                        int c4rSte = 0;
                                        if(channelParameters[6].isChannelState())c4rSte=1;
                                        int c4rFrq = channelParameters[6].getChannelFrequency();
                                        int c4rPwd = channelParameters[6].getChannelWidth();
                                        int c4rAmp = channelParameters[6].getChannelAmplitude();

                                        int c4lStA = channelParameters[7].getChannelStartAngle();
                                        int c4lFnA = channelParameters[7].getChannelFinishAngle();
                                        int c4lSte = 0;
                                        if(channelParameters[7].isChannelState())c4lSte=1;
                                        int c4lFrq = channelParameters[7].getChannelFrequency();
                                        int c4lPwd = channelParameters[7].getChannelWidth();
                                        int c4lAmp = channelParameters[7].getChannelAmplitude();

                                        int actualMode = mode;
                                        int spValue = setPointValue;

                                        int angleSectionGroup = 0;
                                        if((angle>=0)&&(angle<45))          angleSectionGroup = 0;
                                        else if((angle>=45)&&(angle<90))    angleSectionGroup = 1;
                                        else if((angle>=90)&&(angle<135))   angleSectionGroup = 2;
                                        else if((angle>=135)&&(angle<180))  angleSectionGroup = 3;
                                        else if((angle>=180)&&(angle<225))  angleSectionGroup = 4;
                                        else if((angle>=225)&&(angle<270))  angleSectionGroup = 5;
                                        else if((angle>=270)&&(angle<315))  angleSectionGroup = 6;
                                        else if((angle>=315)&&(angle<360))  angleSectionGroup = 7;
                                        else                                angleSectionGroup = 8;

                                        bancoDados.execSQL("INSERT INTO cycling(time,setpoint,angle,anglegroup,speed,mode," +
                                                "ch1rstate,ch1rstartangle,ch1rfinishangle,ch1rfreq,ch1rpw,ch1ramp," +
                                                "ch1lstate,ch1lstartangle,ch1lfinishangle,ch1lfreq,ch1lpw,ch1lamp," +
                                                "ch2rstate,ch2rstartangle,ch2rfinishangle,ch2rfreq,ch2rpw,ch2ramp," +
                                                "ch2lstate,ch2lstartangle,ch2lfinishangle,ch2lfreq,ch2lpw,ch2lamp," +
                                                "ch3rstate,ch3rstartangle,ch3rfinishangle,ch3rfreq,ch3rpw,ch3ramp," +
                                                "ch3lstate,ch3lstartangle,ch3lfinishangle,ch3lfreq,ch3lpw,ch3lamp," +
                                                "ch4rstate,ch4rstartangle,ch4rfinishangle,ch4rfreq,ch4rpw,ch4ramp," +
                                                "ch4lstate,ch4lstartangle,ch4lfinishangle,ch4lfreq,ch4lpw,ch4lamp) " +
                                                "VALUES ('"+currentTime+"','"+spValue+"','"+angle+"','"+angleSectionGroup+"','"+cadence+"','"+actualMode+"'," +
                                                "'"+c1rSte+"','"+c1rStA+"','"+c1rFnA+"','"+c1rFrq+"','"+c1rPwd+"','"+c1rAmp+"'," +
                                                "'"+c1lSte+"','"+c1lStA+"','"+c1lFnA+"','"+c1lFrq+"','"+c1lPwd+"','"+c1lAmp+"'," +
                                                "'"+c2rSte+"','"+c2rStA+"','"+c2rFnA+"','"+c2rFrq+"','"+c2rPwd+"','"+c2rAmp+"'," +
                                                "'"+c2lSte+"','"+c2lStA+"','"+c2lFnA+"','"+c2lFrq+"','"+c2lPwd+"','"+c2lAmp+"'," +
                                                "'"+c3rSte+"','"+c3rStA+"','"+c3rFnA+"','"+c3rFrq+"','"+c3rPwd+"','"+c3rAmp+"'," +
                                                "'"+c3lSte+"','"+c3lStA+"','"+c3lFnA+"','"+c3lFrq+"','"+c3lPwd+"','"+c3lAmp+"'," +
                                                "'"+c4rSte+"','"+c4rStA+"','"+c4rFnA+"','"+c4rFrq+"','"+c4rPwd+"','"+c4rAmp+"'," +
                                                "'"+c4lSte+"','"+c4lStA+"','"+c4lFnA+"','"+c4lFrq+"','"+c4lPwd+"','"+c4lAmp+"') ");
                                        //"VALUES ('"+currentTime+"','"+angle+"','"+cadence+"','"+c1rSte+"','"+c1rStA+"','"+c1rFnA+"','"+c1rFrq+"','"+c1rPwd+"','"+c1rAmp+"') ");
                                    }catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                    displayUpdateThread();
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            //break;
                        }
                    }
                }
            }
        }).start();
    }

    //public void displayUpdate(){
    private void displayUpdateThread(){
        new Thread()
        {
            public void run() {
                try{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //CH1
                            if (!channelParameters[0].isChannelState()) {
                                textChannel1rParameters.setText("CH1 RIGHT OFF");
                                textChannel1rParameters.setTextColor(Color.RED);
                            } else {
                                textChannel1rParameters.setText("CH1 RIGHT ON");
                                textChannel1rParameters.setTextColor(getResources().getColor(R.color.greenON));
                            }
                            if (!channelParameters[1].isChannelState()) {
                                textChannel1lParameters.setText("CH1 LEFT OFF");
                                textChannel1lParameters.setTextColor(Color.RED);
                            } else {
                                textChannel1lParameters.setText("CH1 LEFT ON");
                                textChannel1lParameters.setTextColor(getResources().getColor(R.color.greenON));
                            }
                            textChannel1rParamAmpWdt.setText(""+String.valueOf(channelParameters[0].getChannelAmplitude())+
                                    "mA "+String.valueOf(channelParameters[0].getChannelWidth())+"us "+String.valueOf(channelParameters[0].getChannelFrequency())+"Hz");
                            textChannel1lParamAmpWdt.setText(""+String.valueOf(channelParameters[1].getChannelAmplitude())+
                                    "mA "+String.valueOf(channelParameters[1].getChannelWidth())+"us "+String.valueOf(channelParameters[1].getChannelFrequency())+"Hz");
                            textChannel1rParamAngle.setText(String.valueOf(channelParameters[0].getChannelStartAngle())+
                                    "° to "+String.valueOf(channelParameters[0].getChannelFinishAngle())+"°");
                            textChannel1lParamAngle.setText(String.valueOf(channelParameters[1].getChannelStartAngle())+
                                    "° to "+String.valueOf(channelParameters[1].getChannelFinishAngle())+"°");
                            //CH2
                            if (!channelParameters[2].isChannelState()) {
                                textChannel2rParameters.setText("CH2 RIGHT OFF");
                                textChannel2rParameters.setTextColor(Color.RED);
                            } else {
                                textChannel2rParameters.setText("CH2 RIGHT ON");
                                textChannel2rParameters.setTextColor(getResources().getColor(R.color.greenON));
                            }
                            if (!channelParameters[3].isChannelState()) {
                                textChannel2lParameters.setText("CH2 LEFT OFF");
                                textChannel2lParameters.setTextColor(Color.RED);
                            } else {
                                textChannel2lParameters.setText("CH2 LEFT ON");
                                textChannel2lParameters.setTextColor(getResources().getColor(R.color.greenON));
                            }
                            textChannel2rParamAmpWdt.setText(""+String.valueOf(channelParameters[2].getChannelAmplitude())+
                                    "mA "+String.valueOf(channelParameters[2].getChannelWidth())+"us "+String.valueOf(channelParameters[2].getChannelFrequency())+"Hz");
                            textChannel2lParamAmpWdt.setText(""+String.valueOf(channelParameters[3].getChannelAmplitude())+
                                    "mA "+String.valueOf(channelParameters[3].getChannelWidth())+"us "+String.valueOf(channelParameters[3].getChannelFrequency())+"Hz");
                            textChannel2rParamAngle.setText(String.valueOf(channelParameters[2].getChannelStartAngle())+
                                    "° to "+String.valueOf(channelParameters[2].getChannelFinishAngle())+"°");
                            textChannel2lParamAngle.setText(String.valueOf(channelParameters[3].getChannelStartAngle())+
                                    "° to "+String.valueOf(channelParameters[3].getChannelFinishAngle())+"°");
                            //CH3
                            if (!channelParameters[4].isChannelState()) {
                                textChannel3rParameters.setText("CH3 RIGHT OFF");
                                textChannel3rParameters.setTextColor(Color.RED);
                            } else {
                                textChannel3rParameters.setText("CH3 RIGHT ON");
                                textChannel3rParameters.setTextColor(getResources().getColor(R.color.greenON));
                            }
                            if (!channelParameters[5].isChannelState()) {
                                textChannel3lParameters.setText("CH3 LEFT OFF");
                                textChannel3lParameters.setTextColor(Color.RED);
                            } else {
                                textChannel3lParameters.setText("CH3 LEFT ON");
                                textChannel3lParameters.setTextColor(getResources().getColor(R.color.greenON));
                            }
                            textChannel3rParamAmpWdt.setText(""+String.valueOf(channelParameters[4].getChannelAmplitude())+
                                    "mA "+String.valueOf(channelParameters[4].getChannelWidth())+"us "+String.valueOf(channelParameters[4].getChannelFrequency())+"Hz");
                            textChannel3lParamAmpWdt.setText(""+String.valueOf(channelParameters[5].getChannelAmplitude())+
                                    "mA "+String.valueOf(channelParameters[5].getChannelWidth())+"us "+String.valueOf(channelParameters[5].getChannelFrequency())+"Hz");
                            textChannel3rParamAngle.setText(String.valueOf(channelParameters[4].getChannelStartAngle())+
                                    "° to "+String.valueOf(channelParameters[4].getChannelFinishAngle())+"°");
                            textChannel3lParamAngle.setText(String.valueOf(channelParameters[5].getChannelStartAngle())+
                                    "° to "+String.valueOf(channelParameters[5].getChannelFinishAngle())+"°");
                            //CH4
                            if (!channelParameters[6].isChannelState()) {
                                textChannel4rParameters.setText("CH4 RIGHT OFF");
                                textChannel4rParameters.setTextColor(Color.RED);
                            } else {
                                textChannel4rParameters.setText("CH4 RIGHT ON");
                                textChannel4rParameters.setTextColor(getResources().getColor(R.color.greenON));
                            }
                            if (!channelParameters[7].isChannelState()) {
                                textChannel4lParameters.setText("CH4 LEFT OFF");
                                textChannel4lParameters.setTextColor(Color.RED);
                            } else {
                                textChannel4lParameters.setText("CH4 LEFT ON");
                                textChannel4lParameters.setTextColor(getResources().getColor(R.color.greenON));
                            }
                            textChannel4rParamAmpWdt.setText(""+String.valueOf(channelParameters[6].getChannelAmplitude())+
                                    "mA "+String.valueOf(channelParameters[6].getChannelWidth())+"us "+String.valueOf(channelParameters[6].getChannelFrequency())+"Hz");
                            textChannel4lParamAmpWdt.setText(""+String.valueOf(channelParameters[7].getChannelAmplitude())+
                                    "mA "+String.valueOf(channelParameters[7].getChannelWidth())+"us "+String.valueOf(channelParameters[7].getChannelFrequency())+"Hz");
                            textChannel4rParamAngle.setText(String.valueOf(channelParameters[6].getChannelStartAngle())+
                                    "° to "+String.valueOf(channelParameters[6].getChannelFinishAngle())+"°");
                            textChannel4lParamAngle.setText(String.valueOf(channelParameters[7].getChannelStartAngle())+
                                    "° to "+String.valueOf(channelParameters[7].getChannelFinishAngle())+"°");

                            textSetPoint.setText(String.valueOf(setPointValue)+"rpm");
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView textMessages = findViewById(R.id.textMessages);
        textMessages.setText("Parameters Updated..");
        textMessages.setTextColor(getResources().getColor(R.color.greenON));

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                channelParameters[0] = (Channel) data.getSerializableExtra("channel1parametersR");
                channelParameters[1] = (Channel) data.getSerializableExtra("channel1parametersL");
            }
        }else if (requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                channelParameters[2] = (Channel) data.getSerializableExtra("channel2parametersR");
                channelParameters[3] = (Channel) data.getSerializableExtra("channel2parametersL");
            }
        }else if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                channelParameters[4] = (Channel) data.getSerializableExtra("channel3parametersR");
                channelParameters[5] = (Channel) data.getSerializableExtra("channel3parametersL");
            }
        }else if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                channelParameters[6] = (Channel) data.getSerializableExtra("channel4parametersR");
                channelParameters[7] = (Channel) data.getSerializableExtra("channel4parametersL");
            }
        }
        displayUpdateThread();
        if(fesStartedFlag)sendParameters(true);
    }//onActivityResult

    public void sendParameters(boolean updateStimParameters){
        int aux=0, j;

        if(isModuleConnected){
            try {
                //Protocol
                //outputStream.flush();
                outputStream.write((byte) 0);
                //test if operating mode is automatic or manual
                if(radioButtonManual.isChecked()){
                    outputStream.write((byte) 1);             //manual
                    mode =1;
                    //System.out.println("FesMsg: manual");
                }
                else if(radioButtonAutomatic.isChecked()) {
                    outputStream.write((byte) 2);    //automatic
                    mode =2;
                    //System.out.println("FesMsg: automatico");
                }
                else if(radioButtonControl.isChecked()) {
                    outputStream.write((byte) 3);    //controle
                    mode = 3;
                    //System.out.println("FesMsg: controle");
                }
                else {
                    outputStream.write((byte) 0);       //off
                    mode = 0;
                }

                outputStream.write((byte) 0);
                outputStream.write((byte) setPointValue);   //rpm cadence

                for(j=0;j<8;j++)
                {
                    if(channelParameters[j].isChannelState())outputStream.write((byte) 1);  //CH ON
                    else outputStream.write((byte) 0);                                      //CH OFF
                    outputStream.write((byte) channelParameters[j].getChannelAmplitude());     //CH AMP
                    outputStream.write((byte) channelParameters[j].getChannelFrequency());     //CH FREQ
                    aux = channelParameters[j].getChannelWidth();
                    outputStream.write((byte) (aux>>8));                                     //CH Width(+)
                    outputStream.write((byte) aux);                                           //CH Width(-)
                    aux = channelParameters[j].getChannelStartAngle();
                    aux*=2;
                    outputStream.write((byte) (aux>>8));                                     //CH Start(+)°
                    outputStream.write((byte) aux);                                           //CH Start(-)°
                    aux = channelParameters[j].getChannelFinishAngle();
                    aux*=2;
                    outputStream.write((byte) (aux>>8));                                     //CH Finish(+)°
                    outputStream.write((byte) aux);                                           //CH Finish(-)°
                }
                outputStream.write((byte) 0);
                if(updateStimParameters==true)outputStream.write((byte) 1);
                else outputStream.write((byte) 0);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
