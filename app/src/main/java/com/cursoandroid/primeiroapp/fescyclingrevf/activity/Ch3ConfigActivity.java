
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

public class Ch3ConfigActivity extends AppCompatActivity {

    private Button btUpdateCh3;
    private Switch swStateCH3R, swStateCH3L;
    public static final String CHANNELR_MESSAGE="channel3parametersR";
    public static final String CHANNELL_MESSAGE="channel3parametersL";
    private Channel channel3parametersR, channel3parametersL;
    private EditText textStartAngleCH3right, textStartAngleCH3left;
    private EditText textFinishAngleCH3right, textFinishAngleCH3left;
    private TextView textAmplitudeCH3right, textAmplitudeCH3left;
    private TextView textWidthCH3right, textWidthCH3left;
    private TextView textFrequencyCH3right, textFrequencyCH3left;
    private SeekBar seekBarAmplitudeCH3right, seekBarAmplitudeCH3left;
    private SeekBar seekBarWidthCH3right, seekBarWidthCH3left;
    private SeekBar seekBarFrequencyCH3right, seekBarFrequencyCH3left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch3_config);
        //populate view
        swStateCH3R = findViewById(R.id.switchStateCH3right);
        swStateCH3L = findViewById(R.id.switchStateCH3left);
        textStartAngleCH3right = findViewById(R.id.textStartAngleCH3right);
        textStartAngleCH3left = findViewById(R.id.textStartAngleCH3left);
        textFinishAngleCH3right = findViewById(R.id.textFinishAngleCH3right);
        textFinishAngleCH3left = findViewById(R.id.textFinishAngleCH3left);
        textAmplitudeCH3right = findViewById(R.id.textAmplitudeCH3right);
        textAmplitudeCH3left = findViewById(R.id.textAmplitudeCH3left);
        textWidthCH3right = findViewById(R.id.textWidthCH3right);
        textWidthCH3left = findViewById(R.id.textWidthCH3left);
        textFrequencyCH3right = findViewById(R.id.textFrequencyCH3right);
        textFrequencyCH3left = findViewById(R.id.textFrequencyCH3left);
        seekBarAmplitudeCH3right = findViewById(R.id.seekBarAmplitudeCH3right);
        seekBarAmplitudeCH3left = findViewById(R.id.seekBarAmplitudeCH3left);
        seekBarWidthCH3right = findViewById(R.id.seekBarWidthCH3right);
        seekBarWidthCH3left = findViewById(R.id.seekBarWidthCH3left);
        seekBarFrequencyCH3right = findViewById(R.id.seekBarFrequencyCH3right);
        seekBarFrequencyCH3left = findViewById(R.id.seekBarFrequencyCH3left);

        //retrieve data
        Bundle data = getIntent().getExtras();
        //verify if channels are ON or OFF
        channel3parametersR = (Channel) data.getSerializable(CHANNELR_MESSAGE);
        if(channel3parametersR.isChannelState()==true)swStateCH3R.setChecked(true);
        else swStateCH3R.setChecked(false);
        channel3parametersL = (Channel) data.getSerializable(CHANNELL_MESSAGE);
        if(channel3parametersL.isChannelState()==true)swStateCH3L.setChecked(true);
        else swStateCH3L.setChecked(false);
        //Show configuration parameters on screen
        textStartAngleCH3right.setText(String.valueOf(channel3parametersR.getChannelStartAngle()));
        textStartAngleCH3left.setText(String.valueOf(channel3parametersL.getChannelStartAngle()));
        textFinishAngleCH3right.setText(String.valueOf(channel3parametersR.getChannelFinishAngle()));
        textFinishAngleCH3left.setText(String.valueOf(channel3parametersL.getChannelFinishAngle()));
        textAmplitudeCH3right.setText(String.valueOf(channel3parametersR.getChannelAmplitude())+"mA");
        textAmplitudeCH3left.setText(String.valueOf(channel3parametersL.getChannelAmplitude())+"mA");
        textWidthCH3right.setText(String.valueOf(channel3parametersR.getChannelWidth())+"us");
        textWidthCH3left.setText(String.valueOf(channel3parametersL.getChannelWidth())+"us");
        textFrequencyCH3right.setText(String.valueOf(channel3parametersR.getChannelFrequency())+"Hz");
        textFrequencyCH3left.setText(String.valueOf(channel3parametersL.getChannelFrequency())+"Hz");

        seekBarAmplitudeCH3right.setProgress(channel3parametersR.getChannelAmplitude());
        seekBarAmplitudeCH3right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAmplitudeCH3right.setText(progress+"mA");
                channel3parametersR.setChannelAmplitude(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH3right.setText(String.valueOf(channel3parametersR.getChannelAmplitude())+"mA");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH3right.setText(String.valueOf(channel3parametersR.getChannelAmplitude())+"mA");
            }
        });

        seekBarWidthCH3right.setProgress(channel3parametersR.getChannelWidth());
        seekBarWidthCH3right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textWidthCH3right.setText(progress+"us");
                channel3parametersR.setChannelWidth(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textWidthCH3right.setText(String.valueOf(channel3parametersR.getChannelWidth())+"us");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textWidthCH3right.setText(String.valueOf(channel3parametersR.getChannelWidth())+"us");
            }
        });

        seekBarAmplitudeCH3left.setProgress(channel3parametersL.getChannelAmplitude());
        seekBarAmplitudeCH3left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAmplitudeCH3left.setText(progress+"mA");
                channel3parametersL.setChannelAmplitude(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH3left.setText(String.valueOf(channel3parametersL.getChannelAmplitude())+"mA");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH3left.setText(String.valueOf(channel3parametersL.getChannelAmplitude())+"mA");
            }
        });

        seekBarWidthCH3left.setProgress(channel3parametersL.getChannelWidth());
        seekBarWidthCH3left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textWidthCH3left.setText(progress+"us");
                channel3parametersL.setChannelWidth(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textWidthCH3left.setText(String.valueOf(channel3parametersL.getChannelWidth())+"us");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textWidthCH3left.setText(String.valueOf(channel3parametersL.getChannelWidth())+"us");
            }
        });

        seekBarFrequencyCH3right.setProgress(channel3parametersR.getChannelFrequency());
        seekBarFrequencyCH3right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFrequencyCH3right.setText(progress+"Hz");
                textFrequencyCH3left.setText(progress+"Hz");
                channel3parametersR.setChannelFrequency(progress);
                channel3parametersL.setChannelFrequency(progress);
                seekBarFrequencyCH3right.setProgress(channel3parametersR.getChannelFrequency());
                seekBarFrequencyCH3left.setProgress(channel3parametersL.getChannelFrequency());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textFrequencyCH3right.setText(String.valueOf(channel3parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH3left.setText(String.valueOf(channel3parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH3right.setProgress(channel3parametersR.getChannelFrequency());
                seekBarFrequencyCH3left.setProgress(channel3parametersL.getChannelFrequency());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textFrequencyCH3right.setText(String.valueOf(channel3parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH3left.setText(String.valueOf(channel3parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH3right.setProgress(channel3parametersR.getChannelFrequency());
                seekBarFrequencyCH3left.setProgress(channel3parametersL.getChannelFrequency());
            }
        });

        seekBarFrequencyCH3left.setProgress(channel3parametersL.getChannelFrequency());
        seekBarFrequencyCH3left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFrequencyCH3right.setText(progress+"Hz");
                textFrequencyCH3left.setText(progress+"Hz");
                channel3parametersR.setChannelFrequency(progress);
                channel3parametersL.setChannelFrequency(progress);
                seekBarFrequencyCH3right.setProgress(channel3parametersR.getChannelFrequency());
                seekBarFrequencyCH3left.setProgress(channel3parametersL.getChannelFrequency());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textFrequencyCH3right.setText(String.valueOf(channel3parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH3left.setText(String.valueOf(channel3parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH3right.setProgress(channel3parametersR.getChannelFrequency());
                seekBarFrequencyCH3left.setProgress(channel3parametersL.getChannelFrequency());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textFrequencyCH3right.setText(String.valueOf(channel3parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH3left.setText(String.valueOf(channel3parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH3right.setProgress(channel3parametersR.getChannelFrequency());
                seekBarFrequencyCH3left.setProgress(channel3parametersL.getChannelFrequency());
            }
        });

        btUpdateCh3 = findViewById(R.id.buttonUpdateCh3);
        btUpdateCh3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passar dados
                Intent returnIntent = new Intent();

                if(swStateCH3R.isChecked())channel3parametersR.setChannelState(true);
                else channel3parametersR.setChannelState(false);
                if(swStateCH3L.isChecked())channel3parametersL.setChannelState(true);
                else channel3parametersL.setChannelState(false);

                String teste = textStartAngleCH3right.getText().toString();
                channel3parametersR.setChannelStartAngle(Integer.parseInt(teste));
                teste = textStartAngleCH3left.getText().toString();
                channel3parametersL.setChannelStartAngle(Integer.parseInt(teste));
                teste = textFinishAngleCH3right.getText().toString();
                channel3parametersR.setChannelFinishAngle(Integer.parseInt(teste));
                teste = textFinishAngleCH3left.getText().toString();
                channel3parametersL.setChannelFinishAngle(Integer.parseInt(teste));

                returnIntent.putExtra(CHANNELR_MESSAGE, channel3parametersR);
                returnIntent.putExtra(CHANNELL_MESSAGE, channel3parametersL);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}



