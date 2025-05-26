package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestLogin {
//Object to JSON

@JsonProperty("email")
public String email;

@JsonProperty("password")
public  String password;

public RequestLogin() {}

public RequestLogin(String email, String password){
    this.email = email;
    this.password = password;
}
}
