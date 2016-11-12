/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.adp;

/**
 * Defines several constants used between {@link ConnectionService} and the UI.
 */
public interface Constants {

    // Message types sent from the ConnectionService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the ConnectionService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    public static final String TYPE = "TYPE";
    public static final String CONTENT = "CONTENT";
    public static final String VALUE = "VALUE";

    public static final String INFO ="INFO";
    public static final String STEER ="STEER";
    //public static final String BRAKE ="BRAKE";
    public static final String LIGHT ="LIGHT";
    public static final String DISTANCE ="DISTANCE";
    public static final String ALARM ="ALARM";
    public static final String TEMPERATURE ="TEMPERATURE";
    public static final String ARDUINO_MEMORY="STATE";

    public static final String ALARM_INFO ="-PAY ATTENTION-";
    public static final String SAFETY_INFO ="Enjoy your trip";
    public static final String TURN_LEFT ="TURN_LEFT";
    public static final String TURN_RIGHT ="TURN_RIGHT";
    public static final String BRAKE ="BRAKE gForce";
    public static final int GFORCE = 1;
    public static final int ANGLE = 30;
}
