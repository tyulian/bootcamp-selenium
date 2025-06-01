package com.demo.responsemodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseSingleObject {
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
        private String year;     

        @JsonProperty("price")
        private String price;

        @JsonProperty("cpu_model")
        private String CPUModel; 

        @JsonProperty("hard_disk_size")
        private String HDSize;

        @JsonProperty("color")
        private String color; 

        @JsonProperty("capacity")
        private int capacity;

        @JsonProperty("screen_size")
        private int screenSize; 

    }
}
