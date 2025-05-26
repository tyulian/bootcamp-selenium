package com.demo.responsemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseRegister {
//JSON to Object

@JsonProperty("id")
public int id;

@JsonProperty("email")
public String email;

@JsonProperty("full_name")
public String fullName;

@JsonProperty("department")
public String department;

@JsonProperty("phone_number")
public String phoneNumber;

@JsonProperty("create_at")
public String createAt;

@JsonProperty("update_at")
public String updateAt; 



public ResponseRegister() {};


}
