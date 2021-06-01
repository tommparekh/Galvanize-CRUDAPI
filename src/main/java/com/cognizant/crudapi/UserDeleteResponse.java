package com.cognizant.crudapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDeleteResponse {

    @JsonProperty
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
