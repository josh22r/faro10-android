/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kartum.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.kartum.R;
import com.kartum.utils.Debug;

import java.util.Date;


/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends IntentService {

    public GCMIntentService() {
        super("GCMIntentService");
    }

    @Override
    protected void onHandleIntent(Intent i) {
        Bundle extras = i.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(i);

        if (!extras.isEmpty()) { // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that
			 * GCM will be extended in the future with new message types, just
			 * ignore any message types you're not interested in, or that you
			 * don't recognize.
			 */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                // generateNotification(this, "Send error", extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                // generateNotification(this, "Deleted messages on server",
                // extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                Debug.e("", "" + extras.getString("message"));

                try {
//                    GCMres notificationResponse = new Gson().fromJson(
//                            extras.getString("message"), new TypeToken<GCMres>() {
//                            }.getType());
//
//                    if (notificationResponse != null) {
//                        Intent intent = new Intent(this, NotificationActivity.class);
//                        int notyType = notificationResponse.ntype;
//
//                        if (notyType == 1 && notificationResponse.handleType == 1) {
//
//                            MessageUser.Datum datum = new MessageUser.Datum();
//                            datum.FullName = "";
//                            datum.MemberCode = notificationResponse.MemberCode;
//                            datum.profileurl = "";
//                            datum.FromUserID = notificationResponse.fromUserId;
//
//                            intent = new Intent(this, MessageActivity.class);
//                            intent.putExtra("data", new Gson().toJson(
//                                    datum, new TypeToken<MessageUser.Datum>() {
//                                    }.getType()));
//                        }
//                        if (notyType == 1 && notificationResponse.handleType == 2) {
//
//                            intent = new Intent(this, OfferDetailsActivity.class);
//                            intent.putExtra("offerid", notificationResponse.redirect_id);
//                            if (notificationResponse.actiontype.equalsIgnoreCase("0")) {
//                                intent.putExtra("offertype", 2);
//
//                            } else if (notificationResponse.actiontype.equalsIgnoreCase("1")) {
//
//                                intent.putExtra("offertype", 1);
//                            }
//                        }
//                        if (notyType == 1 && notificationResponse.handleType == 3) {
//
//                            intent = new Intent(this, SentInquiryActivity.class);
//                            if (notificationResponse.actiontype.equalsIgnoreCase("0")) {
//                                intent.putExtra("type", 2);
//
//                            } else if (notificationResponse.actiontype.equalsIgnoreCase("1")) {
//
//                                intent.putExtra("type", 1);
//                            }
//                        }
//                        if (notyType == 2 && notificationResponse.handleType == 5) {
//
//                            intent = new Intent(this, OfferDetailsActivity.class);
//                            intent.putExtra("offerid", notificationResponse.redirect_id);
//                            if (notificationResponse.actiontype.equalsIgnoreCase("0")) {
//                                intent.putExtra("offertype", 2);
//
//                            } else if (notificationResponse.actiontype.equalsIgnoreCase("1")) {
//
//                                intent.putExtra("offertype", 1);
//                            }
//                        }
//                        if (notyType == 2 && notificationResponse.handleType == 6) {
//
//                            intent = new Intent(this, SentInquiryActivity.class);
//                            if (notificationResponse.actiontype.equalsIgnoreCase("0")) {
//                                intent.putExtra("type", 2);
//
//                            } else if (notificationResponse.actiontype.equalsIgnoreCase("1")) {
//
//                                intent.putExtra("type", 1);
//                            }
//                        }
//                        if (notyType == 2 && notificationResponse.handleType == 7) {
//
//                            intent = new Intent(this, ReceivedHoldReqActivity.class);
//                        }
//                        if (notyType == 2 && notificationResponse.handleType == 9) {
//
//                            intent = new Intent(this, MyConnectionsActivity.class);
//                        }
//                        if (notyType == 2 && notificationResponse.handleType == 10) {
//
//                            intent = new Intent(this, SentHoldReqActivity.class);
//                        }
//                        if (notyType == 2 && notificationResponse.handleType == 11) {
//
//                            intent = new Intent(this, OfferDetailsActivity.class);
//                            intent.putExtra("offerid", notificationResponse.redirect_id);
//                            if (notificationResponse.actiontype.equalsIgnoreCase("0")) {
//                                intent.putExtra("offertype", 2);
//
//                            } else if (notificationResponse.actiontype.equalsIgnoreCase("1")) {
//
//                                intent.putExtra("offertype", 1);
//
//                            }
//                        }

//                        generateNotification(this, intent, notificationResponse.ntitle, notificationResponse.ndesc);
//                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GCMBroadcastReceiver.completeWakefulIntent(i);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.

    /**
     * Issues a notification to inform the user that server has sent a message.
     */
    private void generateNotification(Context context, Intent notificationIntent, String title,
                                      String message) {

        int icon = R.mipmap.ic_launcher;
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        int id = Math.abs(new Date().hashCode());

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("" + title)
                .setContentText(message)
                .setSmallIcon(icon)
                .setTicker("" + title)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .setStyle(new NotificationCompat.BigTextStyle().setBigContentTitle("" + title).bigText(message))
                .build();
        notificationManager.notify(id, notification);

    }
}
