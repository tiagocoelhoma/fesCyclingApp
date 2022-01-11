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

public class Ch1ConfigActivity extends AppCompatActivity {

    private Button btUpdateCh1;
    private Switch swStateCH1R, swStateCH1L;
    public static final String CHANNELR_MESSAGE="channel1parametersR";
    public static final String CHANNELL_MESSAGE="channel1parametersL";
    private Channel channel1parametersR, channel1parametersL;
    private EditText textStartAngleCH1right, textStartAngleCH1left;
    private EditText textFinishAngleCH1right, textFinishAngleCH1left;
    private TextView textAmplitudeCH1right, textAmplitudeCH1left;
    private TextView textWidthCH1right, textWidthCH1left;
    private TextView textFrequencyCH1right, textFrequencyCH1left;
    private SeekBar seekBarAmplitudeCH1right, seekBarAmplitudeCH1left;
    private SeekBar seekBarWidthCH1right, seekBarWidthCH1left;
    private SeekBar seekBarFrequencyCH1right, seekBarFrequencyCH1left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch1_config);
        //populate view
        swStateCH1R = findViewById(R.id.switchStateCH1right);
        swStateCH1L = findViewById(R.id.switchStateCH1left);
        textStartAngleCH1right = findViewById(R.id.textStartAngleCH1right);
        textStartAngleCH1left = findViewById(R.id.textStartAngleCH1left);
        textFinishAngleCH1right = findViewById(R.id.textFinishAngleCH1right);
        textFinishAngleCH1left = findViewById(R.id.textFinishAngleCH1left);
        textAmplitudeCH1right = findViewById(R.id.textAmplitudeCH1right);
        textAmplitudeCH1left = findViewById(R.id.textAmplitudeCH1left);
        textWidthCH1right = findViewById(R.id.textWidthCH1right);
        textWidthCH1left = findViewById(R.id.textWidthCH1left);
        textFrequencyCH1right = findViewById(R.id.textFrequencyCH1right);
        textFrequencyCH1left = findViewById(R.id.textFrequencyCH1left);
        seekBarAmplitudeCH1right = findViewById(R.id.seekBarAmplitudeCH1right);
        seekBarAmplitudeCH1left = findViewById(R.id.seekBarAmplitudeCH1left);
        seekBarWidthCH1right = findViewById(R.id.seekBarWidthCH1right);
        seekBarWidthCH1left = findViewById(R.id.seekBarWidthCH1left);
        seekBarFrequencyCH1right = findViewById(R.id.seekBarFrequencyCH1right);
        seekBarFrequencyCH1left = findViewById(R.id.seekBarFrequencyCH1left);

        //retrieve data
        Bundle data = getIntent().getExtras();
        //verify if channels are ON or OFF
        channel1parametersR = (Channel) data.getSerializable(CHANNELR_MESSAGE);
        if(channel1parametersR.isChannelState()==true)swStateCH1R.setChecked(true);
        else swStateCH1R.setChecked(false);
        channel1parametersL = (Channel) data.getSerializable(CHANNELL_MESSAGE);
        if(channel1parametersL.isChannelState()==true)swStateCH1L.setChecked(true);
        else swStateCH1L.setChecked(false);
        //Show configuration parameters on screen
        textStartAngleCH1right.setText(String.valueOf(channel1parametersR.getChannelStartAngle()));
        textStartAngleCH1left.setText(String.valueOf(channel1parametersL.getChannelStartAngle()));
        textFinishAngleCH1right.setText(String.valueOf(channel1parametersR.getChannelFinishAngle()));
        textFinishAngleCH1left.setText(String.valueOf(channel1parametersL.getChannelFinishAngle()));
        textAmplitudeCH1right.setText(String.valueOf(channel1parametersR.getChannelAmplitude())+"mA");
        textAmplitudeCH1left.setText(String.valueOf(channel1parametersL.getChannelAmplitude())+"mA");
        textWidthCH1right.setText(String.valueOf(channel1parametersR.getChannelWidth())+"us");
        textWidthCH1left.setText(String.valueOf(channel1parametersL.getChannelWidth())+"us");
        textFrequencyCH1right.setText(String.valueOf(channel1parametersR.getChannelFrequency())+"Hz");
        textFrequencyCH1left.setText(String.valueOf(channel1parametersL.getChannelFrequency())+"Hz");

        seekBarAmplitudeCH1right.setProgress(channel1parametersR.getChannelAmplitude());
        seekBarAmplitudeCH1right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAmplitudeCH1right.setText(progress+"mA");
                channel1parametersR.setChannelAmplitude(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH1right.setText(String.valueOf(channel1parametersR.getChannelAmplitude())+"mA");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH1right.setText(String.valueOf(channel1parametersR.getChannelAmplitude())+"mA");
            }
        });

        seekBarWidthCH1right.setProgress(channel1parametersR.getChannelWidth());
        seekBarWidthCH1right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textWidthCH1right.setText(progress+"us");
                channel1parametersR.setChannelWidth(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textWidthCH1right.setText(String.valueOf(channel1parametersR.getChannelWidth())+"us");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textWidthCH1right.setText(String.valueOf(channel1parametersR.getChannelWidth())+"us");
            }
        });

        seekBarAmplitudeCH1left.setProgress(channel1parametersL.getChannelAmplitude());
        seekBarAmplitudeCH1left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAmplitudeCH1left.setText(progress+"mA");
                channel1parametersL.setChannelAmplitude(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH1left.setText(String.valueOf(channel1parametersL.getChannelAmplitude())+"mA");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH1left.setText(String.valueOf(channel1parametersL.getChannelAmplitude())+"mA");
            }
        });

        seekBarWidthCH1left.setProgress(channel1parametersL.getChannelWidth());
        seekBarWidthCH1left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textWidthCH1left.setText(progress+"us");
                channel1parametersL.setChannelWidth(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textWidthCH1left.setText(String.valueOf(channel1parametersL.getChannelWidth())+"us");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textWidthCH1left.setText(String.valueOf(channel1parametersL.getChannelWidth())+"us");
            }
        });

        seekBarFrequencyCH1right.setProgress(channel1parametersR.getChannelFrequency());
        seekBarFrequencyCH1right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFrequencyCH1right.setText(progress+"Hz");
                textFrequencyCH1left.setText(progress+"Hz");
                channel1parametersR.setChannelFrequency(progress);
                channel1parametersL.setChannelFrequency(progress);
                seekBarFrequencyCH1right.setProgress(channel1parametersR.getChannelFrequency());
                seekBarFrequencyCH1left.setProgress(channel1parametersL.getChannelFrequency());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textFrequencyCH1right.setText(String.valueOf(channel1parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH1left.setText(String.valueOf(channel1parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH1right.setProgress(channel1parametersR.getChannelFrequency());
                seekBarFrequencyCH1left.setProgress(channel1parametersL.getChannelFrequency());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textFrequencyCH1right.setText(String.valueOf(channel1parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH1left.setText(String.valueOf(channel1parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH1right.setProgress(channel1parametersR.getChannelFrequency());
                seekBarFrequencyCH1left.setProgress(channel1parametersL.getChannelFrequency());
            }
        });

        seekBarFrequencyCH1left.setProgress(channel1parametersL.getChannelFrequency());
        seekBarFrequencyCH1left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFrequencyCH1right.setText(progress+"Hz");
                textFrequencyCH1left.setText(progress+"Hz");
                channel1parametersR.setChannelFrequency(progress);
                channel1parametersL.setChannelFrequency(progress);
                seekBarFrequencyCH1right.setProgress(channel1parametersR.getChannelFrequency());
                seekBarFrequencyCH1left.setProgress(channel1parametersL.getChannelFrequency());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textFrequencyCH1right.setText(String.valueOf(channel1parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH1left.setText(String.valueOf(channel1parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH1right.setProgress(channel1parametersR.getChannelFrequency());
                seekBarFrequencyCH1left.setProgress(channel1parametersL.getChannelFrequency());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textFrequencyCH1right.setText(String.valueOf(channel1parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH1left.setText(String.valueOf(channel1parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH1right.setProgress(channel1parametersR.getChannelFrequency());
                seekBarFrequencyCH1left.setProgress(channel1parametersL.getChannelFrequency());
            }
        });

        btUpdateCh1 = findViewById(R.id.buttonUpdateCh1);
        btUpdateCh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passar dados
                Intent returnIntent = new Intent();

                if(swStateCH1R.isChecked())channel1parametersR.setChannelState(true);
                else channel1parametersR.setChannelState(false);
                if(swStateCH1L.isChecked())channel1parametersL.setChannelState(true);
                else channel1parametersL.setChannelState(false);

                String teste = textStartAngleCH1right.getText().toString();
                channel1parametersR.setChannelStartAngle(Integer.parseInt(teste));
                teste = textStartAngleCH1left.getText().toString();
                channel1parametersL.setChannelStartAngle(Integer.parseInt(teste));
                teste = textFinishAngleCH1right.getText().toString();
                channel1parametersR.setChannelFinishAngle(Integer.parseInt(teste));
                teste = textFinishAngleCH1left.getText().toString();
                channel1parametersL.setChannelFinishAngle(Integer.parseInt(teste));

                returnIntent.putExtra(CHANNELR_MESSAGE, channel1parametersR);
                returnIntent.putExtra(CHANNELL_MESSAGE, channel1parametersL);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
