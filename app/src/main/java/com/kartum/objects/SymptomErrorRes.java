package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 9/7/2017.
 */

public class SymptomErrorRes {

    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("errors")
    @Expose
    public Errors errors;

    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("message")
    @Expose
    public String message;

    public class Errors {

        @SerializedName("symptom")
        @Expose
        public List<String> symptom = new ArrayList<String>();

    }
}
