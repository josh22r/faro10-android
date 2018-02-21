package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by om on 7/31/2017.
 */

public class    ProfileRes {

    @SerializedName("user")
    @Expose
    public User user;

    public class User {

        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("race")
        @Expose
        public String race;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("weight")
        @Expose
        public String weight;
        @SerializedName("zip_code")
        @Expose
        public String zipCode;
        @SerializedName("nationality")
        @Expose
        public String nationality;
        @SerializedName("country")
        @Expose
        public String country;
        @SerializedName("time_zone")
        @Expose
        public String time_zone;
        @SerializedName("occupation")
        @Expose
        public String occupation;
        @SerializedName("emergency_contact_name")
        @Expose
        public String emergencyContactName;
        @SerializedName("emergency_contact_num")
        @Expose
        public String emergencyContactNum;
        @SerializedName("diagnosis")
        @Expose
        public String diagnosis;
        @SerializedName("clinician_id")
        @Expose
        public String clinicianId;
        @SerializedName("trial_interested")
        @Expose
        public boolean trialInterested;

    }
}