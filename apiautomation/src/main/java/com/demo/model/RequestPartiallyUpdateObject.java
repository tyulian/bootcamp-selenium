package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestPartiallyUpdateObject {

    @JsonProperty("name")
    private String name;

    @JsonProperty("year")
        private int year;

    @JsonProperty("color")
    private String color;

    public RequestPartiallyUpdateObject(String name, int year, String color) {
        this.name = name;
        this.year = year;
        this.color = color;
    }

}
