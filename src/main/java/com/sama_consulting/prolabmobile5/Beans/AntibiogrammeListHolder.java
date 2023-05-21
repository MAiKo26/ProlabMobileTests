package com.sama_consulting.prolabmobile5.Beans;

import java.io.Serializable;
import java.util.List;

public class AntibiogrammeListHolder implements Serializable {
    List<Antibiogramme> antibiogrammeList;

    public AntibiogrammeListHolder(List<Antibiogramme> antibiogrammeList) {
        this.antibiogrammeList = antibiogrammeList;
    }

    public List<Antibiogramme> getAntibiogrammeList() {
        return antibiogrammeList;
    }

    public void setAntibiogrammeList(List<Antibiogramme> antibiogrammeList) {
        this.antibiogrammeList = antibiogrammeList;
    }
}