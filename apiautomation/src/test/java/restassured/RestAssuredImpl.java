package restassured;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredImpl {

    String token;

    @BeforeClass
    public void setup(){
        //Define base_url
        RestAssured.baseURI="https://whitesmokehouse.com";

    }

    @Test
    public void Register(){
        //Create Register Request
        String requestBody = "{\n" + //
                        "  \"email\": \"testqa.wrc002@gmail.com\",\n" + //
                        "  \"full_name\": \"Test\",\n" + //
                        "  \"password\": \"@dmin123\",\n" + //
                        "  \"department\": \"Technology\",\n" + //
                        "  \"phone_number\": \"085611789000\"\n" + //
                        "}";
        
        //Send POST request to Register endpoint
        Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .log().all()
            .when()
            .post("/webhook/api/register");
        
        //Print the response
        System.out.println("Response: " + response.asPrettyString());

        //Validate the response
        assert response.jsonPath().getString("email").equals("testqa.wrc002@gmail.com"): "Expected email testqa.wrc5@gmail.com, but got "+ response.jsonPath().getString("email");
        }

   @Test (priority = 1)
    public void Login(){
        
        //Create Login Request
        String requestBody = "{\n" + //
                        "  \"email\": \"trisniy097@gmail.com\",\n" + //
                        "  \"password\": \"@dmin123\"\n" + //
                        "}";
        
        //Send POST request to Login endpoint
        Response response = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .log().all()
            .when()
            .post("/webhook/api/login");
        
        //Print the response
        System.out.println("Response: " + response.asPrettyString());
        token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
    }

    @Test (dependsOnMethods="Login")
    public void AddObject(){
        //Create Add Object Request
        String requestBody ="{\n" +
                            "  \"name\": \"Lenovo K14\",\n" +
                            "  \"data\": {\n" +
                            "    \"year\": 2024,\n" +
                            "    \"price\": 1849.99,\n" +
                            "    \"cpu_model\": \"Intel Core i9\",\n" +
                            "    \"hard_disk_size\": \"1 TB\",\n" +
                            "    \"capacity\": \"2 cpu\",\n" +
                            "    \"screen_size\": \"14 Inch\",\n" +
                            "    \"color\": \"black\"\n" +
                            "  }\n" +
                            "}";
        
        //Send POST request to Add Object endpoint
        Response response = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + token)
        .body(requestBody)
        .log().all()
        .when()
        .post("/webhook/api/objects");

        //Print the response
        System.out.println("Response: " + response.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        String productName = response.jsonPath().getString("[0].name");
        Assert.assertEquals(productName,"Lenovo K14", "Product Name mismatch");
        String productYear = response.jsonPath().getString("[0].data.year");
        Assert.assertEquals(productYear,"2024", "Product Year mismatch");
}
    
    @Test (dependsOnMethods="AddObject")
    public void GetAllObjects(){

        //Send GET request to Get All Objects endpoint
        Response response = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + token)
        .log().all()
        .when()
        .get("webhook/api/objects");

    //Print the response
    System.out.println("Response: " + response.asPrettyString());

    //Status Code Validation
    Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

    }

    @Test  (dependsOnMethods= "AddObject")
    public void GetObjectById(){

        //Send GET request to Get All Objects endpoint
        Response response = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + token)
        .queryParam("id", 371)
        .queryParam("active", "true")
        .log().all()
        .when()
        .get("/webhook/api/objectslistId");

        //Print the response
        System.out.println("Response: " + response.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        int productId = response.jsonPath().getInt("[0].id");
        Assert.assertEquals(productId,371, "Product Id mismatch");
        String productName = response.jsonPath().getString("[0].name");
        Assert.assertEquals(productName,"Lenovo K14", "Product Name mismatch");

    }

    @Test (dependsOnMethods = "Login")
    public void GetSingleObject(){
        
        Response response = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + token)
        .pathParam("webhookId", "8749129e-f5f7-4ae6-9b03-93be7252443c")
        .pathParam("id", 371)
        .log().all()
        .when()
        .get("/webhook/{webhookId}/api/objects/{id}");
        
        //Print the response
        System.out.println("Response: " + response.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        int productId = response.jsonPath().getInt("id");
        Assert.assertEquals(productId,371, "Product Id mismatch");
        String productName = response.jsonPath().getString("name");
        Assert.assertEquals(productName,"Lenovo K14", "Product Name mismatch");

    }

    @Test (dependsOnMethods="Login")
    public void UpdateObject(){
                
        //Create Update Object Request
        String requestBody ="{\n" +
                            "  \"name\": \"Lenovo K14 - Update\",\n" +
                            "  \"data\": {\n" +
                            "    \"year\": 2024,\n" +
                            "    \"price\": 1849.99,\n" +
                            "    \"cpu_model\": \"Intel Core i9\",\n" +
                            "    \"hard_disk_size\": \"1 TB\",\n" +
                            "    \"capacity\": \"2 cpu\",\n" +
                            "    \"screen_size\": \"14 Inch\",\n" +
                            "    \"color\": \"black\"\n" +
                            "  }\n" +
                            "}";
        
        //Send PUT request to Update Endpoint
        Response response = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + token)
        .pathParam("webhookId", "37777abe-a5ef-4570-a383-c99b5f5f7906")
        .pathParam("id", 372)
        .body(requestBody)
        .log().all()
        .when()
        .put("/webhook/{webhookId}/api/objects/{id}");

        //Print the response
        System.out.println("Response: " + response.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");
        
        //Response Validation
        assert response.jsonPath().getString("[0].name").equals("Lenovo K14 - Update"): "Expected name Lenovo K14 - Update, but got "+ response.jsonPath().getString("[0].name");
        
    }

    @Test (dependsOnMethods="UpdateObject")
    public void PartiallyUpdatedObject(){

        //Create Partially Update Object Request
        String requestBody ="{\n" +
                            "  \"name\": \"Lenovo K14 - Partially Update\",\n" +
                            "    \"year\": 2026,\n" +
                            "    \"color\": \"yellow\"\n" +
                            "}";

        //Send PATCH request to Update Endpoint
        Response response = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + token)
        .pathParam("webhookId", "39a0f904-b0f2-4428-80a3-391cea5d7d04")
        .pathParam("id", 372)
        .body(requestBody)
        .log().all()
        .when()
        .patch("/webhook/{webhookId}/api/object/{id}");

        //Print the response
        System.out.println("Response: " + response.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        assert response.jsonPath().getString("name").equals("Lenovo K14 - Partially Update"): "Expected name Lenovo K14 - Update, but got "+ response.jsonPath().getString("name");
        assert response.jsonPath().getString("data.year").equals("2026"): "Expected year 2026, but got "+ response.jsonPath().getString("data.year");
        assert response.jsonPath().getString("data.color").equals("yellow"): "Expected color yellow, but got "+ response.jsonPath().getString("data.color");
    }

    @Test (dependsOnMethods= "PartiallyUpdatedObject")
    public void DeleteObject(){

        //Send DELETE request to Delete Objects endpoint
        Response response = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + token)
        .pathParam("webhookId", "d79a30ed-1066-48b6-83f5-556120afc46f")
        .pathParam("id", 406)
        .log().all()
        .when()
        .delete("/webhook/{webhookId}/api/objects/{id}");

        ////Print the response
        System.out.println("Response: " + response.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");

    }
    
    @Test (dependsOnMethods= "Login")
    public void GetAllDepartment(){

        //Send GET request to Get All Objects endpoint
        Response response = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + token)
        .log().all()
        .when()
        .get("/webhook/api/department");

        //Print the response
        System.out.println("Response: " + response.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200");


    }

    
}
