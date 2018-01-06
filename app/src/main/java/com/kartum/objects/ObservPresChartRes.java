package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kartum Infotech (Bhavesh Hirpara) on 9/21/2017.
 */
public class ObservPresChartRes {

    @SerializedName("observees")
    @Expose
    public List<Observee> observees = new ArrayList<Observee>();

    public class Observee {

        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("meds_taken")
        @Expose
        public List<List<String>> medsTaken = new ArrayList<List<String>>();

        public boolean isSelected;
        public int color;
    }
}
