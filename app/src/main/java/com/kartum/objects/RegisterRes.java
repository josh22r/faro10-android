package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 9/12/2017.
 */

public class RegisterRes {

//    @SerializedName("success")
//    @Expose
//    public boolean success;
//    @SerializedName("errors")
//    @Expose
//    public Errors errors;
//
//    @SerializedName("status")
//    @Expose
//    public String status;
//    @SerializedName("message")
//    @Expose
//    public String message;
//
//    public class Errors {
//
//        @SerializedName("email")
//        @Expose
//        public List<String> email = new ArrayList<String>();
//
//    }


    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("errors")
    @Expose
    public Errors errors;

    public class Errors {

        @SerializedName("email")
        @Expose
        public List<String> email = new ArrayList<String>();
        @SerializedName("password")
        @Expose
        public List<String> password = new ArrayList<String>();

    }

}
