package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by om on 8/19/2017.
 */

public class DashboardStatsRes {

    @SerializedName("my_entry_total")
    @Expose
    public String myEntryTotal;
    @SerializedName("my_self_harm_total")
    @Expose
    public String mySelfHarmTotal;
    @SerializedName("my_attended_sessions_total")
    @Expose
    public String myAttendedSessionsTotal;
    @SerializedName("last_entry")
    @Expose
    public String lastEntry;

}
