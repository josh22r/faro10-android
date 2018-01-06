package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 8/3/2017.
 */

public class PriscriptionRes {

    @SerializedName("prescriptions")
    @Expose
    public List<Prescription> prescriptions = new ArrayList<Prescription>();
    @SerializedName("entries_prescriptions_chart")
    @Expose
    public EntriesPrescriptionsChart entriesPrescriptionsChart;

    public class Prescription {

        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("reminder")
        @Expose
        public String reminder;
        @SerializedName("dosage")
        @Expose
        public int dosage;
        @SerializedName("started")
        @Expose
        public String started;
        @SerializedName("ended")
        @Expose
        public String ended;
        @SerializedName("total_times_taken")
        @Expose
        public String totalTimesTaken;
        @SerializedName("duration")
        @Expose
        public int duration;
        @SerializedName("consistency")
        @Expose
        public int consistency;
        @SerializedName("as_needed")
        @Expose
        public boolean asNeeded;
        @SerializedName("drug")
        @Expose
        public Drug drug;

        public String time;
    }

    public class EntriesPrescriptionsChart {

        @SerializedName("meds_taken")
        @Expose
        public List<List<String>> medsTaken = new ArrayList<List<String>>();
    }

    public class Drug {

        @SerializedName("scientific_name")
        @Expose
        public String scientificName;
        @SerializedName("friendly_name")
        @Expose
        public String friendlyName;
        @SerializedName("pharma_comp")
        @Expose
        public String pharmaComp;
        @SerializedName("id")
        @Expose
        public int id;
    }
}
