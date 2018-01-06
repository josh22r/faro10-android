package com.kartum.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DrugsRes {

    @SerializedName("drugs")
    @Expose
    public List<Drug> drugs = new ArrayList<Drug>();

    public class Drug {

        @SerializedName("scientific_name")
        @Expose
        public String scientificName;
        @SerializedName("friendly_name")
        @Expose
        public String friendlyName;
        @SerializedName("pharma_comp")
        @Expose
        public Object pharmaComp;
        @SerializedName("id")
        @Expose
        public int id;

    }

}