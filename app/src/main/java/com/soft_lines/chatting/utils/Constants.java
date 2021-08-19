package com.soft_lines.chatting.utils;

import java.util.HashMap;

public class Constants {

    public static final String KEY_COLLECTION_USERS = "users";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PREFERENCES_NAME = "chatAppPreference";
    public static final String KEY_IS_SIGNED_IN = "isSignedIn";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FCM_TOKEN = "fcmToken";
    public static final String KEY_USER = "user";
    public static final String KEY_COLLECTION_CHAT = "chat";
    public static final String KEY_SENDER_ID = "senderId";
    public static final String KEY_RECEIVER_ID = "receiverId";
    public static final String KEY_MESSAGE_TEXT = "messageText";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_MESSAGE_IMAGE = "messageImage";
    public static final String KEY_COLLECTION_CONVERSATIONS = "conversions";
    public static final String KEY_SENDER_NAME = "senderName";
    public static final String KEY_RECEIVER_NAME = "receiverName";
    public static final String KEY_SENDER_IMAGE = "senderImage";
    public static final String KEY_RECEIVER_IMAGE = "receiverImage";
    public static final String KEY_LAST_MESSAGE = "lastMessage";
    public static final String KEY_AVAILABILITY = "availability";
    public static final String REMOTE_MSG_AUTHORIZATION = "authorization";
    public static final String REMOTE_MSG_CONTENT_TYPE = "Content-Type";
    public static final String REMOTE_MSG_DATA = "data";
    public static final String REMOTE_MSG_REGISTRATION_IDS = "registration_ids";
    public static final String KEY_OTHER_USER = "other_user";

    public static HashMap<String, String> remoteMsgHeaders = null;
    public static HashMap<String, String> getRemoteMsgHeaders() {
        if (remoteMsgHeaders == null) {
            remoteMsgHeaders = new HashMap<>();
            remoteMsgHeaders.put(REMOTE_MSG_AUTHORIZATION,
                    "Key=AAAAYUp5ffc:APA91bGCoSGyIkXUON3zoNPJgn5A6PF87qloEjH57BAhc9MDcL_1sWnYkh13iOjCRUc5gkAkzqH4DFf_Ey8hta60HlYtfFrBItpN5G-SlESO_tLerNWDwiamjMzruDXpBHuRiKrudBwg");
        }
        remoteMsgHeaders.put(REMOTE_MSG_CONTENT_TYPE, "application/json");
        return remoteMsgHeaders;
    }

}
