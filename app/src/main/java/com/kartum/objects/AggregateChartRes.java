package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 8/29/2017.
 */

public class AggregateChartRes {

    @SerializedName("user")
    @Expose
    public User user;
    @SerializedName("all")
    @Expose
    public All all;

    public class User {

        @SerializedName("chart")
        @Expose
        public Chart chart;
        @SerializedName("feeling")
        @Expose
        public Object feeling;
        @SerializedName("activity")
        @Expose
        public Object activity;
        @SerializedName("emotion")
        @Expose
        public Object emotion;
        @SerializedName("energy")
        @Expose
        public String energy;
        @SerializedName("anxiety")
        @Expose
        public String anxiety;
        @SerializedName("headache")
        @Expose
        public int headache;
        @SerializedName("hrs_slept")
        @Expose
        public String hrsSlept;
        @SerializedName("took_meds")
        @Expose
        public Object tookMeds;
        @SerializedName("suicide_thought")
        @Expose
        public String suicideThought;
        @SerializedName("zest")
        @Expose
        public String zest;
        @SerializedName("pessimism")
        @Expose
        public String pessimism;
        @SerializedName("panic_attack")
        @Expose
        public int panicAttack;
        @SerializedName("initiative")
        @Expose
        public String initiative;
        @SerializedName("concentration")
        @Expose
        public String concentration;
        @SerializedName("appetite")
        @Expose
        public String appetite;
        @SerializedName("reported_mood")
        @Expose
        public String reportedMood;
        @SerializedName("restlessness")
        @Expose
        public String restlessness;
        @SerializedName("dry_mouth")
        @Expose
        public String dryMouth;
        @SerializedName("nausea")
        @Expose
        public String nausea;
        @SerializedName("nightmare")
        @Expose
        public String nightmare;
        @SerializedName("weight")
        @Expose
        public String weight;
    }

    public class All {

        @SerializedName("chart")
        @Expose
        public Chart_ chart;
        @SerializedName("feeling")
        @Expose
        public String feeling;
        @SerializedName("activity")
        @Expose
        public int activity;
        @SerializedName("emotion")
        @Expose
        public Object emotion;
        @SerializedName("energy")
        @Expose
        public String energy;
        @SerializedName("anxiety")
        @Expose
        public String anxiety;
        @SerializedName("headache")
        @Expose
        public String headache;
        @SerializedName("hrs_slept")
        @Expose
        public String hrsSlept;
        @SerializedName("took_meds")
        @Expose
        public Object tookMeds;
        @SerializedName("suicide_thought")
        @Expose
        public int suicideThought;
        @SerializedName("zest")
        @Expose
        public String zest;
        @SerializedName("pessimism")
        @Expose
        public String pessimism;
        @SerializedName("panic_attack")
        @Expose
        public int panicAttack;
        @SerializedName("initiative")
        @Expose
        public String initiative;
        @SerializedName("concentration")
        @Expose
        public String concentration;
        @SerializedName("appetite")
        @Expose
        public String appetite;
        @SerializedName("reported_mood")
        @Expose
        public String reportedMood;
        @SerializedName("restlessness")
        @Expose
        public int restlessness;
        @SerializedName("dry_mouth")
        @Expose
        public int dryMouth;
        @SerializedName("nausea")
        @Expose
        public int nausea;
        @SerializedName("nightmare")
        @Expose
        public int nightmare;
        @SerializedName("weight")
        @Expose
        public String weight;
    }

    public class Chart_ {

        @SerializedName("feeling")
        @Expose
        public List<List<String>> feeling = new ArrayList<List<String>>();

    }

    public class Chart {

        @SerializedName("feeling")
        @Expose
        public List<List<String>> feeling = new ArrayList<List<String>>();
        @SerializedName("appetite")
        @Expose
        public List<List<String>> appetite = new ArrayList<List<String>>();
        @SerializedName("initiative")
        @Expose
        public List<List<String>> initiative = new ArrayList<List<String>>();
        @SerializedName("pessimism")
        @Expose
        public List<List<String>> pessimism = new ArrayList<List<String>>();
        @SerializedName("zest")
        @Expose
        public List<List<String>> zest = new ArrayList<List<String>>();
        @SerializedName("anxiety")
        @Expose
        public List<List<String>> anxiety = new ArrayList<List<String>>();
        @SerializedName("sleep")
        @Expose
        public List<List<String>> sleep = new ArrayList<List<String>>();
        @SerializedName("energy")
        @Expose
        public List<List<String>> energy = new ArrayList<List<String>>();
        @SerializedName("concentration")
        @Expose
        public List<List<String>> concentration = new ArrayList<List<String>>();
        @SerializedName("panic_attack")
        @Expose
        public List<List<String>> panicAttack = new ArrayList<List<String>>();
        @SerializedName("suicide_thought")
        @Expose
        public List<List<String>> suicideThought = new ArrayList<List<String>>();
        @SerializedName("headache")
        @Expose
        public List<List<String>> headache = new ArrayList<List<String>>();
        @SerializedName("nightmare")
        @Expose
        public List<List<String>> nightmare = new ArrayList<List<String>>();
        @SerializedName("work_life")
        @Expose
        public List<List<String>> workLife = new ArrayList<List<String>>();
        @SerializedName("social_life")
        @Expose
        public List<List<String>> socialLife = new ArrayList<List<String>>();
        @SerializedName("family_life")
        @Expose
        public List<List<String>> familyLife = new ArrayList<List<String>>();
        @SerializedName("weight")
        @Expose
        public List<List<String>> weight = new ArrayList<List<String>>();

    }
}