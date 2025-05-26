package com.demo.responsemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAddObject {
//JSON to Object (Deserialize Model)

@JsonProperty("id")
public int id;

@JsonProperty("name")
public String name;

@JsonProperty("data")
public DataDetail data;


public static class DataDetail {

        @JsonProperty("year")
        public int year;     

        @JsonProperty("price")
        public double price;

        @JsonProperty("cpu_model")
        public String CPUModel; 

        @JsonProperty("hard_disk_size")
        public String HDSize;

        @JsonProperty("color")
        public String color; 

        @JsonProperty("capacity")
        public String capacity;

        @JsonProperty("screen_size")
        public String screenSize; 

    }
}
