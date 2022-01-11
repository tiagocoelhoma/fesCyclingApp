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

public class Ch2ConfigActivity extends AppCompatActivity {

    private Button btUpdateCh2;
    private Switch swStateCH2R, swStateCH2L;
    public static final String CHANNELR_MESSAGE="channel2parametersR";
    public static final String CHANNELL_MESSAGE="channel2parametersL";
    private Channel channel2parametersR, channel2parametersL;
    private EditText textStartAngleCH2right, textStartAngleCH2left;
    private EditText textFinishAngleCH2right, textFinishAngleCH2left;
    private TextView textAmplitudeCH2right, textAmplitudeCH2left;
    private TextView textWidthCH2right, textWidthCH2left;
    private TextView textFrequencyCH2right, textFrequencyCH2left;
    private SeekBar seekBarAmplitudeCH2right, seekBarAmplitudeCH2left;
    private SeekBar seekBarWidthCH2right, seekBarWidthCH2left;
    private SeekBar seekBarFrequencyCH2right, seekBarFrequencyCH2left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch2_config);
        //populate view
        swStateCH2R = findViewById(R.id.switchStateCH2right);
        swStateCH2L = findViewById(R.id.switchStateCH2left);
        textStartAngleCH2right = findViewById(R.id.textStartAngleCH2right);
        textStartAngleCH2left = findViewById(R.id.textStartAngleCH2left);
        textFinishAngleCH2right = findViewById(R.id.textFinishAngleCH2right);
        textFinishAngleCH2left = findViewById(R.id.textFinishAngleCH2left);
        textAmplitudeCH2right = findViewById(R.id.textAmplitudeCH2right);
        textAmplitudeCH2left = findViewById(R.id.textAmplitudeCH2left);
        textWidthCH2right = findViewById(R.id.textWidthCH2right);
        textWidthCH2left = findViewById(R.id.textWidthCH2left);
        textFrequencyCH2right = findViewById(R.id.textFrequencyCH2right);
        textFrequencyCH2left = findViewById(R.id.textFrequencyCH2left);
        seekBarAmplitudeCH2right = findViewById(R.id.seekBarAmplitudeCH2right);
        seekBarAmplitudeCH2left = findViewById(R.id.seekBarAmplitudeCH2left);
        seekBarWidthCH2right = findViewById(R.id.seekBarWidthCH2right);
        seekBarWidthCH2left = findViewById(R.id.seekBarWidthCH2left);
        seekBarFrequencyCH2right = findViewById(R.id.seekBarFrequencyCH2right);
        seekBarFrequencyCH2left = findViewById(R.id.seekBarFrequencyCH2left);

        //retrieve data
        Bundle data = getIntent().getExtras();
        //verify if channels are ON or OFF
        channel2parametersR = (Channel) data.getSerializable(CHANNELR_MESSAGE);
        if(channel2parametersR.isChannelState()==true)swStateCH2R.setChecked(true);
        else swStateCH2R.setChecked(false);
        channel2parametersL = (Channel) data.getSerializable(CHANNELL_MESSAGE);
        if(channel2parametersL.isChannelState()==true)swStateCH2L.setChecked(true);
        else swStateCH2L.setChecked(false);
        //Show configuration parameters on screen
        textStartAngleCH2right.setText(String.valueOf(channel2parametersR.getChannelStartAngle()));
        textStartAngleCH2left.setText(String.valueOf(channel2parametersL.getChannelStartAngle()));
        textFinishAngleCH2right.setText(String.valueOf(channel2parametersR.getChannelFinishAngle()));
        textFinishAngleCH2left.setText(String.valueOf(channel2parametersL.getChannelFinishAngle()));
        textAmplitudeCH2right.setText(String.valueOf(channel2parametersR.getChannelAmplitude())+"mA");
        textAmplitudeCH2left.setText(String.valueOf(channel2parametersL.getChannelAmplitude())+"mA");
        textWidthCH2right.setText(String.valueOf(channel2parametersR.getChannelWidth())+"us");
        textWidthCH2left.setText(String.valueOf(channel2parametersL.getChannelWidth())+"us");
        textFrequencyCH2right.setText(String.valueOf(channel2parametersR.getChannelFrequency())+"Hz");
        textFrequencyCH2left.setText(String.valueOf(channel2parametersL.getChannelFrequency())+"Hz");

        seekBarAmplitudeCH2right.setProgress(channel2parametersR.getChannelAmplitude());
        seekBarAmplitudeCH2right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAmplitudeCH2right.setText(progress+"mA");
                channel2parametersR.setChannelAmplitude(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH2right.setText(String.valueOf(channel2parametersR.getChannelAmplitude())+"mA");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH2right.setText(String.valueOf(channel2parametersR.getChannelAmplitude())+"mA");
            }
        });

        seekBarWidthCH2right.setProgress(channel2parametersR.getChannelWidth());
        seekBarWidthCH2right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textWidthCH2right.setText(progress+"us");
                channel2parametersR.setChannelWidth(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textWidthCH2right.setText(String.valueOf(channel2parametersR.getChannelWidth())+"us");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textWidthCH2right.setText(String.valueOf(channel2parametersR.getChannelWidth())+"us");
            }
        });

        seekBarAmplitudeCH2left.setProgress(channel2parametersL.getChannelAmplitude());
        seekBarAmplitudeCH2left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textAmplitudeCH2left.setText(progress+"mA");
                channel2parametersL.setChannelAmplitude(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH2left.setText(String.valueOf(channel2parametersL.getChannelAmplitude())+"mA");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textAmplitudeCH2left.setText(String.valueOf(channel2parametersL.getChannelAmplitude())+"mA");
            }
        });

        seekBarWidthCH2left.setProgress(channel2parametersL.getChannelWidth());
        seekBarWidthCH2left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textWidthCH2left.setText(progress+"us");
                channel2parametersL.setChannelWidth(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textWidthCH2left.setText(String.valueOf(channel2parametersL.getChannelWidth())+"us");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textWidthCH2left.setText(String.valueOf(channel2parametersL.getChannelWidth())+"us");
            }
        });

        seekBarFrequencyCH2right.setProgress(channel2parametersR.getChannelFrequency());
        seekBarFrequencyCH2right.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFrequencyCH2right.setText(progress+"Hz");
                textFrequencyCH2left.setText(progress+"Hz");
                channel2parametersR.setChannelFrequency(progress);
                channel2parametersL.setChannelFrequency(progress);
                seekBarFrequencyCH2right.setProgress(channel2parametersR.getChannelFrequency());
                seekBarFrequencyCH2left.setProgress(channel2parametersL.getChannelFrequency());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textFrequencyCH2right.setText(String.valueOf(channel2parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH2left.setText(String.valueOf(channel2parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH2right.setProgress(channel2parametersR.getChannelFrequency());
                seekBarFrequencyCH2left.setProgress(channel2parametersL.getChannelFrequency());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textFrequencyCH2right.setText(String.valueOf(channel2parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH2left.setText(String.valueOf(channel2parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH2right.setProgress(channel2parametersR.getChannelFrequency());
                seekBarFrequencyCH2left.setProgress(channel2parametersL.getChannelFrequency());
            }
        });

        seekBarFrequencyCH2left.setProgress(channel2parametersL.getChannelFrequency());
        seekBarFrequencyCH2left.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textFrequencyCH2right.setText(progress+"Hz");
                textFrequencyCH2left.setText(progress+"Hz");
                channel2parametersR.setChannelFrequency(progress);
                channel2parametersL.setChannelFrequency(progress);
                seekBarFrequencyCH2right.setProgress(channel2parametersR.getChannelFrequency());
                seekBarFrequencyCH2left.setProgress(channel2parametersL.getChannelFrequency());
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                textFrequencyCH2right.setText(String.valueOf(channel2parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH2left.setText(String.valueOf(channel2parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH2right.setProgress(channel2parametersR.getChannelFrequency());
                seekBarFrequencyCH2left.setProgress(channel2parametersL.getChannelFrequency());
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textFrequencyCH2right.setText(String.valueOf(channel2parametersR.getChannelFrequency())+"Hz");
                textFrequencyCH2left.setText(String.valueOf(channel2parametersL.getChannelFrequency())+"Hz");
                seekBarFrequencyCH2right.setProgress(channel2parametersR.getChannelFrequency());
                seekBarFrequencyCH2left.setProgress(channel2parametersL.getChannelFrequency());
            }
        });

        btUpdateCh2 = findViewById(R.id.buttonUpdateCh2);
        btUpdateCh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //passar dados
                Intent returnIntent = new Intent();

                if(swStateCH2R.isChecked())channel2parametersR.setChannelState(true);
                else channel2parametersR.setChannelState(false);
                if(swStateCH2L.isChecked())channel2parametersL.setChannelState(true);
                else channel2parametersL.setChannelState(false);

                String teste = textStartAngleCH2right.getText().toString();
                channel2parametersR.setChannelStartAngle(Integer.parseInt(teste));
                teste = textStartAngleCH2left.getText().toString();
                channel2parametersL.setChannelStartAngle(Integer.parseInt(teste));
                teste = textFinishAngleCH2right.getText().toString();
                channel2parametersR.setChannelFinishAngle(Integer.parseInt(teste));
                teste = textFinishAngleCH2left.getText().toString();
                channel2parametersL.setChannelFinishAngle(Integer.parseInt(teste));

                returnIntent.putExtra(CHANNELR_MESSAGE, channel2parametersR);
                returnIntent.putExtra(CHANNELL_MESSAGE, channel2parametersL);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}



