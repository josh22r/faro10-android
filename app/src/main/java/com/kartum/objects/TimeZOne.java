package com.kartum.objects;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TimeZOne {

        @SerializedName("time_zones")
        @Expose
        public List<String> timeZones = new ArrayList<String>();

}
