package com.thecoderscrew.shop.Classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.thecoderscrew.shop.Interface.SmsListener;

public class ReadSMS extends BroadcastReceiver {

    private static SmsListener mListener;

        @Override
        public void onReceive(Context context, Intent intent) {

            Bundle data  = intent.getExtras();

            Object[] pdus = (Object[]) data.get("pdus");

            for(int i=0;i<pdus.length;i++)
            {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

                String messageBody = smsMessage.getMessageBody();

                mListener.messageReceived(messageBody);

            }
        }

        public static void bindListener(SmsListener listener) {
            mListener = listener;
        }
    }