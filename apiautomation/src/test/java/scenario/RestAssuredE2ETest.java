package scenario;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAssuredE2ETest {

    /* List APIs:
     * 1. Register
     * 2. Login
     * 3. AddObject
     * 4. Get Object
     * 5. UpdateObject
     * 6. PartiallyUpdateObject
     * 7. DeleteObject
     */

     /*
      * Scenario: Rest Assured E2E Test
      * Test Case - 001: Register Employee
      * 1. Hit the endpoint register with valid data
      * 2. Hit the endpoint Login with valid data
      *
      * Test Case - 002: Create New Object
      * 1. Hit the endpoint Login with valid data
      * 2. Hit the endpoint AddObject with valid data
      * 3. Hit the endpoint SingleObject with valid data
      *
      * Test Case - 003: Update Existing Object
      * 1. Hit the endpoint Login with valid data
      * 2. Hit the endpoint UpdateObject with valid data
      * 3. Hit the endpoint SingleObject with valid data
      *
      * Test Case - 004: Partially Update Existing Object
      * 1. Hit the endpoint Login with valid data
      * 2. Hit the endpoint PartiallyUpdateObject with valid data
      * 3. Hit the endpoint SingleObject with valid data
      *
      * Test Case - 005: Delete Existing Object
      * 1. Hit the endpoint Login with valid data
      * 2. Hit the endpoint DeleteObject with valid data
      * 3. Hit the endpoint SingleObject with valid data
      */

     String token, tokenLogin;
     int idObject;

    @BeforeClass
    public void setup(){
        //Define Base URL
        RestAssured.baseURI="https://whitesmokehouse.com";

        //Login 
         String reqBodyLogin = "{\n" + //
                        "  \"email\": \"trisniy097@gmail.com\",\n" + //
                        "  \"password\": \"@dmin123\"\n" + //
                        "}";
        
        Response responseLogin = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(reqBodyLogin)
            .log().all()
            .when()
            .post("/webhook/api/login");
        
        //Print the response
        System.out.println("Response: " + responseLogin.asPrettyString());
        tokenLogin = responseLogin.jsonPath().getString("token");
        System.out.println("Token: " + tokenLogin);

    }

    @Test
    public void registerEmployee(){
        
         //1. Hit the endpoint Register with valid data
         String reqBodyRegister = "{\n" + //
                        "  \"email\": \"testqa.wrc8@gmail.com\",\n" + //
                        "  \"full_name\": \"Test\",\n" + //
                        "  \"password\": \"@dmin123\",\n" + //
                        "  \"department\": \"Technology\",\n" + //
                        "  \"phone_number\": \"085611789000\"\n" + //
                        "}";
        
        Response responseRegister = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(reqBodyRegister)
            .log().all()
            .when()
            .post("/webhook/api/register");
        
        System.out.println("Response: " + responseRegister.asPrettyString());

        //Validation Register
        Assert.assertEquals(responseRegister.getStatusCode(), 200, 
        "Status code is not 200");
        Assert.assertNotNull(responseRegister.jsonPath().get("id"), 
        "Expected id but got null");
        Assert.assertEquals(responseRegister.jsonPath().get("email"), "testqa.wrc8@gmail.com",
        "Expected email testqa.wrc8@gmail.com, but got "+ responseRegister.jsonPath().getString("email"));
        
        //2. Hit the endpoint Login with valid data
        String reqBodyLogin = "{\n" + //
                        "  \"email\": \"testqa.wrc8@gmail.com\",\n" + //
                        "  \"password\": \"@dmin123\"\n" + //
                        "}";
        
        Response responseLogin = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(reqBodyLogin)
            .log().all()
            .when()
            .post("/webhook/api/login");
        
        //Print the response
        System.out.println("Response: " + responseLogin.asPrettyString());
        token = responseLogin.jsonPath().getString("token");
        System.out.println("Token: " + token);

    }
    
    /*@Test (dependsOnMethods="registerEmployee")
    public void deleteEmployee(){
        Response responseDeleteEmployee = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + token)
        .pathParam("id", id)
        .log().all()
        .when()
        .delete("/webhook/employee/delete/{id}");

        ///Print the response
        System.out.println("Response: " + responseDeleteEmployee.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(responseDeleteEmployee.getStatusCode(), 200, "Status code is not 200");

    }*/

    @Test (priority = 1)
    public void createNewObject(){

        //Hit the endpoint AddObject with valid data
        String reqBodyCreateObject ="{\n" +
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
        Response responseCreateObject = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .body(reqBodyCreateObject)
        .log().all()
        .when()
        .post("/webhook/api/objects");

        //Print the response
        System.out.println("Response: " + responseCreateObject.asPrettyString());
        idObject = responseCreateObject.jsonPath().getInt("[0].id");
        System.out.println("Id: " + idObject);

        //Status Code Validation
        Assert.assertEquals(responseCreateObject.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        Assert.assertEquals(responseCreateObject.jsonPath().getString("[0].name"),"Lenovo K14");
        Assert.assertEquals(responseCreateObject.jsonPath().getString("[0].data.year"), "2024");
        Assert.assertEquals(responseCreateObject.jsonPath().getDouble("[0].data.price"), 1849.99);
        Assert.assertEquals(responseCreateObject.jsonPath().getString("[0].data.cpu_model"), "Intel Core i9");
        Assert.assertEquals(responseCreateObject.jsonPath().getString("[0].data.hard_disk_size"), "1 TB");
        Assert.assertEquals(responseCreateObject.jsonPath().getString("[0].data.capacity"), "2 cpu");
        Assert.assertEquals(responseCreateObject.jsonPath().getString("[0].data.screen_size"), "14 Inch");
        Assert.assertEquals(responseCreateObject.jsonPath().getString("[0].data.color"), "black");

        //Hit the endpoint GetSingleObject with valid data
        Response rspValidateCreateObj = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "8749129e-f5f7-4ae6-9b03-93be7252443c")
        .pathParam("id", idObject)
        .log().all()
        .when()
        .get("/webhook/{webhookId}/api/objects/{id}");
        
        //Print the response
        System.out.println("Response: " + rspValidateCreateObj.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(rspValidateCreateObj.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        Assert.assertEquals(rspValidateCreateObj.jsonPath().getString("name"), "Lenovo K14");
        Assert.assertEquals(rspValidateCreateObj.jsonPath().getString("data.year"), "2024");
        Assert.assertEquals(rspValidateCreateObj.jsonPath().getDouble("data.price"), 1849.99);
        Assert.assertEquals(rspValidateCreateObj.jsonPath().getString("data.cpu_model"), "Intel Core i9");
        Assert.assertEquals(rspValidateCreateObj.jsonPath().getString("data.hard_disk_size"), "1 TB");
        Assert.assertEquals(rspValidateCreateObj.jsonPath().getInt("data.capacity"), 2);
        Assert.assertEquals(rspValidateCreateObj.jsonPath().getInt("data.screen_size"), 14);
        Assert.assertEquals(rspValidateCreateObj.jsonPath().getString("data.color"), "black");

        
    }

    @Test(dependsOnMethods="createNewObject", priority = 2)
    public  void updateExistingObject(){

        //Hit the endpoint UpdateObject with valid data
        String reqBodyUpdateObject ="{\n" +
                            "  \"name\": \"Lenovo Yoga\",\n" +
                            "  \"data\": {\n" +
                            "    \"year\": 2025,\n" +
                            "    \"price\": 1999.99,\n" +
                            "    \"cpu_model\": \"Intel Core i8\",\n" +
                            "    \"hard_disk_size\": \"2 TB\",\n" +
                            "    \"capacity\": \"4 cpu\",\n" +
                            "    \"screen_size\": \"12 Inch\",\n" +
                            "    \"color\": \"gray\"\n" +
                            "  }\n" +
                            "}";
        
        //Send PUT request to Update Endpoint
        Response responseUpdateObject = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "37777abe-a5ef-4570-a383-c99b5f5f7906")
        .pathParam("id", idObject)
        .body(reqBodyUpdateObject)
        .log().all()
        .when()
        .put("/webhook/{webhookId}/api/objects/{id}");

        //Print the response
        System.out.println("Response: " + responseUpdateObject.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(responseUpdateObject.getStatusCode(), 200, "Status code is not 200");
        
        //Response Validation
        Assert.assertEquals(responseUpdateObject.jsonPath().getString("[0].name"), "Lenovo Yoga");
        Assert.assertEquals(responseUpdateObject.jsonPath().getString("[0].data.year"), "2025");
        Assert.assertEquals(responseUpdateObject.jsonPath().getDouble("[0].data.price"), 1999.99);
        Assert.assertEquals(responseUpdateObject.jsonPath().getString("[0].data.'CPU model'"), "Intel Core i8");
        Assert.assertEquals(responseUpdateObject.jsonPath().getString("[0].data.'Hard disk size'"), "2 TB");
        Assert.assertEquals(responseUpdateObject.jsonPath().getString("[0].data.capacity"), "4 cpu");
        Assert.assertEquals(responseUpdateObject.jsonPath().getString("[0].data.screen_size"), "12 Inch");
        Assert.assertEquals(responseUpdateObject.jsonPath().getString("[0].data.color"), "gray");

        //Hit the endpoint GetSingleObject with valid data
        Response rspValidateUpdateObj = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "8749129e-f5f7-4ae6-9b03-93be7252443c")
        .pathParam("id", idObject)
        .log().all()
        .when()
        .get("/webhook/{webhookId}/api/objects/{id}");
        
        //Print the response
        System.out.println("Response: " + rspValidateUpdateObj.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(rspValidateUpdateObj.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        Assert.assertEquals(rspValidateUpdateObj.jsonPath().getString("name"), "Lenovo Yoga");
        Assert.assertEquals(rspValidateUpdateObj.jsonPath().getString("data.year"), "2025");
        Assert.assertEquals(rspValidateUpdateObj.jsonPath().getDouble("data.price"), 1999.99);
        Assert.assertEquals(rspValidateUpdateObj.jsonPath().getString("data.cpu_model"), "Intel Core i8");
        Assert.assertEquals(rspValidateUpdateObj.jsonPath().getString("data.hard_disk_size"), "2 TB");
        Assert.assertEquals(rspValidateUpdateObj.jsonPath().getInt("data.capacity"), 4);
        Assert.assertEquals(rspValidateUpdateObj.jsonPath().getInt("data.screen_size"), 12);
        Assert.assertEquals(rspValidateUpdateObj.jsonPath().getString("data.color"), "gray");

    }

    @Test (dependsOnMethods="createNewObject", priority = 3)
    public void partiallyUpdatedExistingObject(){
       
        //Hit the endpoint PartiallyUpdateObject with valid data
        String reqBodyPartiallyUpdateObj ="{\n" +
                                        "  \"name\": \"Lenovo Ideapad\",\n" +
                                        "    \"year\": 2027,\n" +
                                        "    \"color\": \"white\"\n" +
                                        "}";

        //Send PATCH request to Update Endpoint
        Response rspPartiallyUpdateObj = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "39a0f904-b0f2-4428-80a3-391cea5d7d04")
        .pathParam("id", idObject)
        .body(reqBodyPartiallyUpdateObj)
        .log().all()
        .when()
        .patch("/webhook/{webhookId}/api/object/{id}");

        //Print the response
        System.out.println("Response: " + rspPartiallyUpdateObj.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(rspPartiallyUpdateObj.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        Assert.assertEquals(rspPartiallyUpdateObj.jsonPath().getString("name"), "Lenovo Ideapad");
        Assert.assertEquals(rspPartiallyUpdateObj.jsonPath().getString("data.year"), "2027");
        Assert.assertEquals(rspPartiallyUpdateObj.jsonPath().getDouble("data.price"), 1999.99);
        Assert.assertEquals(rspPartiallyUpdateObj.jsonPath().getString("data.cpu_model"), "Intel Core i8");
        Assert.assertEquals(rspPartiallyUpdateObj.jsonPath().getString("data.hard_disk_size"), "2 TB");
        Assert.assertEquals(rspPartiallyUpdateObj.jsonPath().getString("data.capacity"), "4 cpu");
        Assert.assertEquals(rspPartiallyUpdateObj.jsonPath().getString("data.screen_size"), "12 Inch");
        Assert.assertEquals(rspPartiallyUpdateObj.jsonPath().getString("data.color"), "white");

        //Hit the endpoint GetSingleObject with valid data
        Response rspValidatePartiallyUpdateObj = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "8749129e-f5f7-4ae6-9b03-93be7252443c")
        .pathParam("id", idObject)
        .log().all()
        .when()
        .get("/webhook/{webhookId}/api/objects/{id}");
        
        //Print the response
        System.out.println("Response: " + rspValidatePartiallyUpdateObj.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(rspValidatePartiallyUpdateObj.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        Assert.assertEquals(rspValidatePartiallyUpdateObj.jsonPath().getString("name"), "Lenovo Ideapad");
        Assert.assertEquals(rspValidatePartiallyUpdateObj.jsonPath().getString("data.year"), "2027");
        Assert.assertEquals(rspValidatePartiallyUpdateObj.jsonPath().getDouble("data.price"), 1999.99);
        Assert.assertEquals(rspValidatePartiallyUpdateObj.jsonPath().getString("data.cpu_model"), "Intel Core i8");
        Assert.assertEquals(rspValidatePartiallyUpdateObj.jsonPath().getString("data.hard_disk_size"), "2 TB");
        Assert.assertEquals(rspValidatePartiallyUpdateObj.jsonPath().getInt("data.capacity"), 4);
        Assert.assertEquals(rspValidatePartiallyUpdateObj.jsonPath().getInt("data.screen_size"), 12);
        Assert.assertEquals(rspValidatePartiallyUpdateObj.jsonPath().getString("data.color"), "white");

    }

    @Test (dependsOnMethods= "createNewObject", priority=4)
    public void deleteExistingObject(){

        //Hit the endpoint DeleteObject with valid data
        Response rspDeleteObject = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .pathParam("webhookId", "d79a30ed-1066-48b6-83f5-556120afc46f")
            .pathParam("id", idObject)
            .log().all()
            .when()
            .delete("/webhook/{webhookId}/api/objects/{id}");

        ////Print the response
        System.out.println("Response: " + rspDeleteObject.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(rspDeleteObject.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        Assert.assertEquals(rspDeleteObject.jsonPath().getString("status"), "deleted");
        Assert.assertEquals(rspDeleteObject.jsonPath().getString("message"), "Object with id = "+ idObject + ", has been deleted.");
        
        //Hit the endpoint GetSingleObject with valid data
        Response rspValidateDeleteObj = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "8749129e-f5f7-4ae6-9b03-93be7252443c")
        .pathParam("id", idObject)
        .log().all()
        .when()
        .get("/webhook/{webhookId}/api/objects/{id}");
        
        //Print the response
        System.out.println("Response: " + rspValidateDeleteObj.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(rspValidateDeleteObj.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        Assert.assertEquals(rspValidateDeleteObj.getBody().asString().trim(), "{}", "Expected empty JSON object");
    }
}
