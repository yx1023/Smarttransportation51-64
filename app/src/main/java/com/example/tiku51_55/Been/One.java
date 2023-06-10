package com.example.tiku51_55.Been;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class One {
    private List<Integer> id;
    @SerializedName("RESULT")
    private String rESULT;

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    public String getRESULT() {
        return rESULT;
    }

    public void setRESULT(String rESULT) {
        this.rESULT = rESULT;
    }
}
