package com.cognizant.crudapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class UserAuthenticationResponse {

    @JsonProperty
    private boolean authenticated;

    @JsonProperty ("user")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserResponse userResponse;


    public boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public UserResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserResponse userResponse) {
        this.userResponse = userResponse;
    }
}
