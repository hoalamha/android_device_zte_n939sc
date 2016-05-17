/*
 * Copyright (C) 2006 The Android Open Source Project
 * Copyright (C) 2015, 2016 The CyanogenMod Project
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
 
package com.android.internal.telephony;

import static com.android.internal.telephony.RILConstants.*;
import android.content.Context;
import android.media.AudioManager;
import android.os.AsyncResult;
import android.os.Message;
import android.os.Parcel;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.SignalStrength;
import com.android.internal.telephony.cdma.CdmaInformationRecords;
import com.android.internal.telephony.cdma.SignalToneUtil;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NubiaUiccRIL extends RIL implements CommandsInterface {
    protected boolean isGSM = false;
    
    public NubiaUiccRIL(Context context, int preferredNetworkType, int cdmaSubscription, Integer instanceId) {
        super(context, preferredNetworkType, cdmaSubscription, instanceId);
    }
    
    public NubiaUiccRIL(Context context, int networkMode, int cdmaSubscription) {
        super(context, networkMode, cdmaSubscription);
    }
    
    protected Object responseSignalStrength(Parcel p) {
        int numInts = 12, localSignalStrength1 = 12;
        int[] response = new int[numInts];
		for (int i = 0; i < numInts; i++) {
            response[i] = p.readInt();
        }
        response[1] = (response[0] & 0xff);
        response[2] = (response[2] % 256);
        response[4] = (response[4] % 256);
        if(!isGSM) {
            response[8] = response[2];
            response[10] = response[2];
        } else if(((response[7] & 0xff) == 0xff) || (response[7] == 99)) {
            response[7] = 99;
            response[8] = 0x7fffffff;
            response[9] = 0x7fffffff;
            response[10] = 0x7fffffff;
            response[11] = 0x7fffffff;
        } else {
            response[7] = (response[7] & 0xff);
        }
        new SignalStrength(response[0], response[1], response[2], response[3], response[4], response[5], response[6], response[7], response[8], response[9], response[10], response[11], true);
        return new SignalStrength(response[0], response[1], response[2], response[3], response[4], response[5], response[6], response[7], response[8], response[9], response[10], response[11], true);
    }
    
    @Override
    public void setPhoneType(int phoneType) {
        super.setPhoneType(phoneType);
        isGSM = (phoneType != 2);
    }
}
