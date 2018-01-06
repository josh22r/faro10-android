package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by om on 8/19/2017.
 */

public class JournalEntryRes {

    @SerializedName("journals")
    @Expose
    public List<Journal> journals = new ArrayList<Journal>();

    public class Journal {

        @SerializedName("entry_id")
        @Expose
        public int entryId;
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("text")
        @Expose
        public String text;

    }
}