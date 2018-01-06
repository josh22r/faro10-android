package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by om on 8/29/2017.
 */

public class MessageReadRes {

    @SerializedName("message")
    @Expose
    public Message message;

    public class Message {

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
