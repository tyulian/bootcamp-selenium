package com.demo.responsemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseUpdateObject {
//JSON to Object (Deserialize Model)

@JsonProperty("id")
private int id;

@JsonProperty("name")
private String name;

@JsonProperty("data")
private DataDetail data;

@Data
public static class DataDetail {

        @JsonProperty("year")
        private int year;     

        @JsonProperty("price")
        private double price;

        @JsonProperty("CPU model")
        private String CPUModel; 

        @JsonProperty("Hard disk size")
        private String HDSize;

        @JsonProperty("color")
        private String color; 

        @JsonProperty("capacity")
        private String capacity;

        @JsonProperty("screen_size")
        private String screenSize; 

    }
}
