package com.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestCreateObject {
    //Object to JSON (Serialize Model)
/*{
 "name": "Lenovo K14",
 "data": {
    "year": 2024,
    "price": 1849.99,
    "cpu_model": "Intel Core i9",
    "hard_disk_size": "1 TB",
    "capacity": "2 cpu",
    "screen_size": "14 Inch",
    "color": "Gray"
 }
}
*/
    @JsonProperty("name")
    public String name;

    @JsonProperty("data")
    public Data addData;
   
    public RequestCreateObject() {}
   
    public RequestCreateObject(String name, Data addData){
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

        @JsonProperty("capacity")
        public String capacity;

        @JsonProperty("screen_size")
        public String screenSize;

        @JsonProperty("color")
        public String color;

        public Data() {}
        
        public Data(int year, double price, String CPUModel, String HDSize, String capacity, String screenSize, String color){
            this.year = year;
            this.price = price;
            this.CPUModel = CPUModel;
            this.HDSize = HDSize;
            this.capacity = capacity;
            this.screenSize = screenSize;
            this.color = color;
        }

    }

    
};
    

