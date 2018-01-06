package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by om on 8/10/2017.
 */

public class GoalCenterRes {

    @SerializedName("entries")
    @Expose
    public Entries entries;
    @SerializedName("exercise")
    @Expose
    public Exercise exercise;
    @SerializedName("medication")
    @Expose
    public Medication medication;
    @SerializedName("sponsored")
    @Expose
    public Sponsored sponsored;

    public class Entries {

        @SerializedName("goal_progression")
        @Expose
        public float goalProgression;
        @SerializedName("target")
        @Expose
        public int target;
        @SerializedName("medal")
        @Expose
        public String medal;
    }

    public class Exercise {

        @SerializedName("goal_progression")
        @Expose
        public float goalProgression;
        @SerializedName("target")
        @Expose
        public int target;
        @SerializedName("medal")
        @Expose
        public String medal;

    }

    public class Medication {

        @SerializedName("avg_consistency")
        @Expose
        public float avgConsistency;
        @SerializedName("target")
        @Expose
        public int target;
        @SerializedName("medal")
        @Expose
        public String medal;

    }

    public class Sponsored {

        @SerializedName("goal_progression")
        @Expose
        public float goalProgression;
        @SerializedName("target")
        @Expose
        public int target;
        @SerializedName("medal")
        @Expose
        public String medal;

    }
}