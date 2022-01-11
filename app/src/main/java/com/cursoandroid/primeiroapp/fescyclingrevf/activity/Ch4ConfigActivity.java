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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cursoandroid.primeiroapp.fescyclingrevf.R;
import com.cursoandroid.primeiroapp.fescyclingrevf.classes.Channel;

public class Ch4ConfigActivity extends AppCompatActivity {

    private Button btUpdateCh4;
    private Switch swStateCH4R, swStateCH4L;
    public static final String CHANNELR_MESSAGE="channel4parametersR";
    public static final String CHANNELL_MESSAGE="channel4parametersL";
    private Channel channel4parametersR, channel4parametersL;
    private EditText textStartAngleCH4right, textStartAngleCH4left;
    private EditText textFinishAngleCH4right, textFinishAngleCH4left;
    private TextView textAmplitudeCH4right, textAmplitudeCH4left;
    private TextView textWidthCH4right, textWidthCH4left;
    private TextView textFrequencyCH4right, textFrequencyCH4left;
    private SeekBar seekBarAmplitudeCH4right, seekBarAmplitudeCH4left;
    private SeekBar seekBarWidthCH4right, seekBarWidthCH4left;
    private SeekBar seekBarFrequencyCH4right, seekBarFrequencyCH4left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch4_config);
        //populate view
        swStateCH4R = findViewById(R.id.switchStateCH4right);
        swStateCH4L = findViewById(R.id.switchStateCH4left);
        textStartAngleCH4right = findViewById(R.id.textStartAngleCH4right);
        textStartAngleCH4left = findViewById(R.id.textStartAngleCH4left);
        textFinishAngleCH4right = findViewById(R.id.textFinishAngleCH4right);
        textFinishAngleCH4left = findViewById(R.id.textFinishAngleCH4left);
        textAmplitudeCH4right = findViewById(R.id.textAmplitudeCH4right);
        textAmplitudeCH4left = findViewById(R.id.textAmplitudeCH4left);
        textWidthCH4right = findViewById(R.id.textWidthCH4right);
        textWidthCH4left = findViewById(R.id.textWidthCH4left);
        textFrequencyCH4right = findViewById(R.id.textFrequencyCH4right);
        textFrequencyCH4left = findViewById(R.id.textFrequencyCH4left);
        seekBarAmplitudeCH4right = findViewById(R.id.seekBarAmplitudeCH4right);
        seekBarAmplitudeCH4left = findViewById(R.id.seekBarAmplitudeCH4left);
        seekBarWidthCH4right = findViewById(R.id.seekBarWidthCH4right);
        seekBarWidthCH4left = findViewById(R.id.seekBarWidthCH4left);
        seekBarFrequencyCH4right = findViewById(R.id.seekBarFrequencyCH4right);
        seekBarFrequencyCH4left = findViewById(R.id.seekBarFrequencyCH4left);

        //retrieve data
        Bundle data = getIntent().getExtras();
        //verify if channels are ON or OFF
        channel4parametersR = (Channel) data.getSerializable(CHANNELR_MESSAGE);
        if(channel4parametersR.isChannelState()==true)swStateCH4R.setChecked(true);
        else swStateCH4R.setChecked(false);
        channel4parametersL = (Channel) data.getSerializable(CHANNELL_MESSAGE);
        if(channel4parametersL.isChannelState()==true)swStateCH4L.setChecked(true);
        else swStateCH4L.setChecked(false);
        //Show configuration parameters on screen
        textStartAngleCH4right.setText(String.valueOf(channel4parametersR.getChannelStartAngle()));
        textStartAngleCH4left.setText(String.valueOf(channel4parametersL.getChannelStartAngle()));
        textFinishAngleCH4right.setText(String.valueOf(channel4parametersR.getChannelFinishAngle()));
        textFinishAngleCH4left.setText(String.valueOf(channel4parametersL.getChannelFinishAngle()));
        textAmplitudeCH4right.setText(String.valueOf(channel4parametersR.getChannelAmplitude())+"mA");
        textAmplitudeCH4left.setText(String.valueOf(channel4parametersL.getChannelAmplitude())+"mA");
        textWidthCH4right.setText(String.valueOf(channel4parametersR.getChannelWidth())+"us");
        textWidthCH4left.setText(String.valueOf(channel4parametersL.getChannelWidth())+"us");
        textFrequencyCH4right.setText(String.valueOf(channel4parametersR.getChannelFrequency())+"Hz");
        textFrequencyCH4left.setText(String.valueOf(channel4parametersL.getChannelFrequency())+"Hz");

        seekBarAmplitudeCH4right.setProgress(channel4parametersR.getChannelAmplitude());
        seekBarAmplitudeCH4right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAmplitudeCH4right.setText(progress+"mA");
                channel4parametersR.setChannelAmplitude(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH4right.setText(String.valueOf(channel4parametersR.getChannelAmplitude())+"mA");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH4right.setText(String.valueOf(channel4parametersR.getChannelAmplitude())+"mA");
            }
        });

        seekBarWidthCH4right.setProgress(channel4parametersR.getChannelWidth());
        seekBarWidthCH4right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textWidthCH4right.setText(progress+"us");
                channel4parametersR.setChannelWidth(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textWidthCH4right.setText(String.valueOf(channel4parametersR.getChannelWidth())+"us");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textWidthCH4right.setText(String.valueOf(channel4parametersR.getChannelWidth())+"us");
            }
        });

        seekBarAmplitudeCH4left.setProgress(channel4parametersL.getChannelAmplitude());
        seekBarAmplitudeCH4left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAmplitudeCH4left.setText(progress+"mA");
                channel4parametersL.setChannelAmplitude(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH4left.setText(String.valueOf(channel4parametersL.getChannelAmplitude())+"mA");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH4left.setText(String.valueOf(channel4parametersL.getChannelAmplitude())+"mA");
            }
        });

        seekBarWidthCH4left.setProgress(channel4parametersL.getChannelWidth());
        seekBarWidthCH4left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textWidthCH4left.setText(progress+"us");
                channel4parametersL.setChannelWidth(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textWidthCH4left.setText(String.valueOf(channel4parametersL.getChannelWidth())+"us");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textWidthCH4left.setText(String.valueOf(channel4parametersL.getChannelWidth())+"us");
            }
        });

        seekBarFrequencyCH4right.setProgress(channel4parametersR.getChannelFrequency());
        seekBarFrequencyCH4right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFrequencyCH4right.setText(progress+"Hz");
                textFrequencyCH4left.setText(progress+"Hz");
                channel4parametersR.setChannelFrequency(progress);
                channel4parametersL.setChannelFrequency(progress);
                seekBarFrequencyCH4right.setProgress(channel4parametersR.getChannelFrequency());
                seekBarFrequencyCH4left.setProgress(channel4parametersL.getChannelFrequency());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textFrequencyCH4right.setText(String.valueOf(channel4parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH4left.setText(String.valueOf(channel4parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH4right.setProgress(channel4parametersR.getChannelFrequency());
                seekBarFrequencyCH4left.setProgress(channel4parametersL.getChannelFrequency());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textFrequencyCH4right.setText(String.valueOf(channel4parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH4left.setText(String.valueOf(channel4parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH4right.setProgress(channel4parametersR.getChannelFrequency());
                seekBarFrequencyCH4left.setProgress(channel4parametersL.getChannelFrequency());
            }
        });

        seekBarFrequencyCH4left.setProgress(channel4parametersL.getChannelFrequency());
        seekBarFrequencyCH4left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFrequencyCH4right.setText(progress+"Hz");
                textFrequencyCH4left.setText(progress+"Hz");
                channel4parametersR.setChannelFrequency(progress);
                channel4parametersL.setChannelFrequency(progress);
                seekBarFrequencyCH4right.setProgress(channel4parametersR.getChannelFrequency());
                seekBarFrequencyCH4left.setProgress(channel4parametersL.getChannelFrequency());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textFrequencyCH4right.setText(String.valueOf(channel4parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH4left.setText(String.valueOf(channel4parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH4right.setProgress(channel4parametersR.getChannelFrequency());
                seekBarFrequencyCH4left.setProgress(channel4parametersL.getChannelFrequency());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textFrequencyCH4right.setText(String.valueOf(channel4parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH4left.setText(String.valueOf(channel4parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH4right.setProgress(channel4parametersR.getChannelFrequency());
                seekBarFrequencyCH4left.setProgress(channel4parametersL.getChannelFrequency());
            }
        });

        btUpdateCh4 = findViewById(R.id.buttonUpdateCh4);
        btUpdateCh4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passar dados
                Intent returnIntent = new Intent();

                if(swStateCH4R.isChecked())channel4parametersR.setChannelState(true);
                else channel4parametersR.setChannelState(false);
                if(swStateCH4L.isChecked())channel4parametersL.setChannelState(true);
                else channel4parametersL.setChannelState(false);

                String teste = textStartAngleCH4right.getText().toString();
                channel4parametersR.setChannelStartAngle(Integer.parseInt(teste));
                teste = textStartAngleCH4left.getText().toString();
                channel4parametersL.setChannelStartAngle(Integer.parseInt(teste));
                teste = textFinishAngleCH4right.getText().toString();
                channel4parametersR.setChannelFinishAngle(Integer.parseInt(teste));
                teste = textFinishAngleCH4left.getText().toString();
                channel4parametersL.setChannelFinishAngle(Integer.parseInt(teste));

                returnIntent.putExtra(CHANNELR_MESSAGE, channel4parametersR);
                returnIntent.putExtra(CHANNELL_MESSAGE, channel4parametersL);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}



