package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginRes {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("message")
    @Expose
    public String message;

}
