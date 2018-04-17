package com.capstone.jmt.data;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by macbookpro on 4/11/18.
 */
public class RefSection {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("section")
    private String section;
    @JsonProperty("refGradeLvlId")
    private int refGradeLvlId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getRefGradeLvlId() {
        return refGradeLvlId;
    }

    public void setRefGradeLvlId(int refGradeLvlId) {
        this.refGradeLvlId = refGradeLvlId;
    }
}
