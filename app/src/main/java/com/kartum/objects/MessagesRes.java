package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 8/28/2017.
 */

public class MessagesRes {

    @SerializedName("messages")
    @Expose
    public List<Message> messages = new ArrayList<Message>();

    public static class Message {

        @SerializedName("body")
        @Expose
        public String body;
        @SerializedName("read")
        @Expose
        public boolean read;
        @SerializedName("archived")
        @Expose
        public boolean archived;
        @SerializedName("member_id")
        @Expose
        public int memberId;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("clinician")
        @Expose
        public Clinician clinician;
    }

    public class Clinician {

        @SerializedName("user_id")
        @Expose
        public String userId;

    }
}
