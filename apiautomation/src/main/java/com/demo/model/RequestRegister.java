package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestRegister {

    @JsonProperty("email")
    private String email;

    @JsonProperty("full_name")
        private String fullName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("department")
    public String department;

    @JsonProperty("phone_number")
    public String phoneNumber;

    public RequestRegister(String email, String fullName, String password, String department, String phoneNumber) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.department = department;
        this.phoneNumber = phoneNumber;
    }

}
