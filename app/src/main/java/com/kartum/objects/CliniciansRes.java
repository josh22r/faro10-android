package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 8/28/2017.
 */

public class CliniciansRes {

    @SerializedName("memberships")
    @Expose
    public List<Membership> memberships = new ArrayList<Membership>();

    public class Membership {

        @SerializedName("clinician_id")
        @Expose
        public int clinicianId;
        @SerializedName("clinic_name")
        @Expose
        public String clinicName;
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("user_name")
        @Expose
        public String userName;
        @SerializedName("id")
        @Expose
        public int id;

    }
}
