package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestUpdateObject {
    //Object to JSON (Serialize Model)

    @JsonProperty("name")
    public String name;

    @JsonProperty("data")
    public Data addData;
   
    public RequestUpdateObject() {}
   
    public RequestUpdateObject(String name, Data addData){
        this.name = name;
        this.addData = addData;
    }

    public static class Data {
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

        public Data() {}
        
        public Data(int year, double price, String CPUModel, String HDSize, String color, String capacity, String screenSize){
            this.year = year;
            this.price = price;
            this.CPUModel = CPUModel;
            this.HDSize = HDSize;
            this.color = color;
            this.capacity = capacity;
            this.screenSize = screenSize;
        }

    }

    
};
    

