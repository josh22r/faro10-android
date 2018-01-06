package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 8/19/2017.
 */

public class ObservUserRes {

    @SerializedName("observations_of_others")
    @Expose
    public List<ObservationsOfOther> observationsOfOthers = new ArrayList<ObservationsOfOther>();

    public class ObservationsOfOther {

        @SerializedName("id")
        @Expose
        public int id;
        @SerializedName("observer_id")
        @Expose
        public int observerId;
        @SerializedName("relationship")
        @Expose
        public String relationship;
        @SerializedName("guardian")
        @Expose
        public boolean guardian;
        @SerializedName("meds")
        @Expose
        public int meds;
        @SerializedName("status")
        @Expose
        public String status;

    }
}