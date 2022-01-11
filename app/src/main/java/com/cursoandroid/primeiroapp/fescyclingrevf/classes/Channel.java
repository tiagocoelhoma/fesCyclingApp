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

package com.cursoandroid.primeiroapp.fescyclingrevf.classes;

import java.io.Serializable;

public class Channel implements Serializable {
    private int ChannelNumber;
    private int ChannelFrequency;
    private int ChannelWidth;
    private int ChannelAmplitude;
    private int ChannelStartAngle;
    private int ChannelFinishAngle;
    private boolean ChannelState;

    public Channel(int channelNumber,
                   int channelFrequency,
                   int channelWidth,
                   int channelAmplitude,
                   int channelStartAngle,
                   int channelFinishAngle,
                   boolean channelState) {

        ChannelNumber = channelNumber;
        ChannelFrequency = channelFrequency;
        ChannelWidth = channelWidth;
        ChannelAmplitude = channelAmplitude;
        ChannelStartAngle = channelStartAngle;
        ChannelFinishAngle = channelFinishAngle;
        ChannelState = channelState;
    }

    public int getChannelNumber() {
        return ChannelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        ChannelNumber = channelNumber;
    }

    public int getChannelFrequency() {
        return ChannelFrequency;
    }

    public void setChannelFrequency(int channelFrequency) {
        ChannelFrequency = channelFrequency;
    }

    public int getChannelWidth() {
        return ChannelWidth;
    }

    public void setChannelWidth(int channelWidth) {
        ChannelWidth = channelWidth;
    }

    public int getChannelAmplitude() {
        return ChannelAmplitude;
    }

    public void setChannelAmplitude(int channelAmplitude) {
        ChannelAmplitude = channelAmplitude;
    }

    public int getChannelStartAngle() {
        return ChannelStartAngle;
    }

    public void setChannelStartAngle(int channelStartAngle) {
        ChannelStartAngle = channelStartAngle;
    }

    public int getChannelFinishAngle() {
        return ChannelFinishAngle;
    }

    public void setChannelFinishAngle(int channelFinishAngle) {
        ChannelFinishAngle = channelFinishAngle;
    }

    public boolean isChannelState() {
        return ChannelState;
    }

    public void setChannelState(boolean channelState) {
        ChannelState = channelState;
    }
}

