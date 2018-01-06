package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kartum Infotech (Bhavesh Hirpara) on 9/29/2017.
 */
public class CareTeamRes {

    @SerializedName("clinicians")
    @Expose
    public List<Clinician> clinicians = new ArrayList<Clinician>();

    public class Clinician {

        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("clinic_city")
        @Expose
        public String clinicCity;
        @SerializedName("clinic_name")
        @Expose
        public String clinicName;
        @SerializedName("clinic_phone")
        @Expose
        public Object clinicPhone;
        @SerializedName("clinic_state")
        @Expose
        public String clinicState;
        @SerializedName("clinic_street")
        @Expose
        public String clinicStreet;
        @SerializedName("clinic_zip")
        @Expose
        public int clinicZip;

    }
}
