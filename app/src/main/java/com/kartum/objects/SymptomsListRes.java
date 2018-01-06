package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 9/1/2017.
 */

public class SymptomsListRes {

    @SerializedName("symptoms")
    @Expose
    public List<Symptom> symptoms = new ArrayList<Symptom>();

    public class Symptom {

        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("patient_id")
        @Expose
        public Object patientId;

        public boolean isSelected;
    }
}
