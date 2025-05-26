package pojo;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.demo.model.RequestCreateObject;
import com.demo.model.RequestLogin;
import com.demo.model.RequestPartiallyUpdateObject;
import com.demo.model.RequestRegister;
import com.demo.model.RequestUpdateObject;
import com.demo.responsemodel.ResponseAddObject;
import com.demo.responsemodel.ResponsePartiallyUpdateObject;
import com.demo.responsemodel.ResponseRegister;
import com.demo.responsemodel.ResponseSingleObject;
import com.demo.responsemodel.ResponseUpdateObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class PojoE2EImpl {
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
public void setup() throws JsonProcessingException{
        //Define Base URL
        RestAssured.baseURI="https://whitesmokehouse.com";

        //Login 
        RequestLogin smLogin = new RequestLogin("trisniy097@gmail.com","@dmin123");
        ObjectMapper objectMapper = new ObjectMapper();
        String reqBodyLogin = objectMapper.writeValueAsString(smLogin);
    
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
public void registerEmployee() throws JsonProcessingException{
        
    //1. Hit the endpoint Register with valid data  
    RequestRegister smRegister = new RequestRegister("testqa.wrc117@gmail.com","Test", "@dmin123", "Technology", "085611789000");
    ObjectMapper objectMapper = new ObjectMapper();
    String reqBodyRegister = objectMapper.writeValueAsString(smRegister);
    
    Response responseRegister = RestAssured.given()
        .header("Content-Type", "application/json")
        .body(reqBodyRegister)
        .log().all()
        .when()
        .post("/webhook/api/register");
        
    System.out.println("Response: " + responseRegister.asPrettyString());

    ResponseRegister rspRegister = responseRegister.as(ResponseRegister.class);

    //Validation Register
    Assert.assertEquals(responseRegister.getStatusCode(), 200, 
    "Expected Status Code 200 but got " + responseRegister.getStatusCode());
    Assert.assertNotNull(rspRegister.id, "Expected id but got null");
    Assert.assertEquals(rspRegister.email, "testqa.wrc117@gmail.com");
    Assert.assertEquals(rspRegister.fullName, "Test");
    Assert.assertEquals(rspRegister.department, "Technology");
    Assert.assertEquals(rspRegister.phoneNumber, "085611789000");

    //2. Hit the endpoint Login with valid data
    //Try using Serialize
    RequestLogin smLogin = new RequestLogin("testqa.wrc117@gmail.com","@dmin123");
    //ObjectMapper objectMapper = new ObjectMapper();
    String reqBodyLogin = objectMapper.writeValueAsString(smLogin);
    
    System.out.println("Json String: " + reqBodyLogin);

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
    
    @Test (priority = 1)
    public void createNewObject() throws JsonProcessingException{
        RequestCreateObject.Data addData = new RequestCreateObject.Data(
            2024, 1849.99, "Intel Core i9","1 TB", "2 cpu", "14 Inch","black");
        RequestCreateObject smCreateObj = new RequestCreateObject("Lenovo K14", addData);

        ObjectMapper objectMapper = new ObjectMapper();

        String reqBodyCreateObject = objectMapper.writeValueAsString(smCreateObj);

        System.out.println("Json String: " + reqBodyCreateObject);
              
        //Send POST request to Add Object endpoint
        Response createObject = given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .body(reqBodyCreateObject)
        .log().all()
        .when()
        .post("/webhook/api/objects");

        //Status Code Validation
        Assert.assertEquals(createObject.getStatusCode(), 200, "Status code is not 200");
        idObject = createObject.jsonPath().getInt("[0].id");
        System.out.println("Id: " + idObject);

        //Deserialize response to List<AddObjectModel>
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(createObject.body().asString(), 
        new TypeReference <List<ResponseAddObject>>(){});
                
        //Response Validation
        
        Assert.assertNotNull(rspAddObj.get(0).getId(), "Expected id but got null");
        Assert.assertEquals(rspAddObj.get(0).getName(), "Lenovo K14");
        Assert.assertEquals(rspAddObj.get(0).getData().year, 2024);
        Assert.assertEquals(rspAddObj.get(0).getData().price, 1849.99);
        Assert.assertEquals(rspAddObj.get(0).getData().CPUModel, "Intel Core i9");
        Assert.assertEquals(rspAddObj.get(0).getData().HDSize, "1 TB");
        Assert.assertEquals(rspAddObj.get(0).getData().capacity, "2 cpu");
        Assert.assertEquals(rspAddObj.get(0).getData().screenSize, "14 Inch");
        Assert.assertEquals(rspAddObj.get(0).getData().color, "black");

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

        ResponseSingleObject rspCreateObj = rspValidateCreateObj.as(ResponseSingleObject.class);

        //Response Validation
        Assert.assertNotNull(rspCreateObj.getId(), "Expected id but got null");
        Assert.assertEquals(rspCreateObj.getName(), "Lenovo K14");
        Assert.assertEquals(rspCreateObj.getData().year, "2024");
        Assert.assertEquals(rspCreateObj.getData().price, "1849.99");
        Assert.assertEquals(rspCreateObj.getData().CPUModel, "Intel Core i9");
        Assert.assertEquals(rspCreateObj.getData().HDSize, "1 TB");
        Assert.assertEquals(rspCreateObj.getData().capacity, 2);
        Assert.assertEquals(rspCreateObj.getData().screenSize, 14);
        Assert.assertEquals(rspCreateObj.getData().color, "black");

        
    }

    @Test(dependsOnMethods = "createNewObject", priority = 2)
    public void updateExistObject() throws JsonProcessingException{

        RequestUpdateObject.Data updateData = new RequestUpdateObject.Data(
            2025, 1999.99, "Intel Core i8","2 TB", "gray", "4 cpu" , "12 inch");
        RequestUpdateObject smUpdateObj = new RequestUpdateObject("Lenovo Yoga", updateData);

        ObjectMapper objectMapper = new ObjectMapper();

        String reqBodyUpdateObject = objectMapper.writeValueAsString(smUpdateObj);

        System.out.println("Json String: " + reqBodyUpdateObject);

        //Send PUT request to Update Endpoint
        Response updateObject = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "37777abe-a5ef-4570-a383-c99b5f5f7906")
        .pathParam("id", idObject)
        .body(reqBodyUpdateObject)
        .log().all()
        .when()
        .put("/webhook/{webhookId}/api/objects/{id}");

        //Print the response
        System.out.println("Response: " + updateObject.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(updateObject.getStatusCode(), 200, "Status code is not 200");
        
        //Deserialize response to List<ResponseUpdateObject>
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(updateObject.body().asString(), 
        new TypeReference <List<ResponseUpdateObject>>(){});
                
        //Response Validation
        Assert.assertNotNull(rspUpdateObj.get(0).getId(), "Expected id but got null");
        Assert.assertEquals(rspUpdateObj.get(0).getName(), "Lenovo Yoga");
        Assert.assertEquals(rspUpdateObj.get(0).getData().year, 2025);
        Assert.assertEquals(rspUpdateObj.get(0).getData().price, 1999.99);
        Assert.assertEquals(rspUpdateObj.get(0).getData().CPUModel, "Intel Core i8");
        Assert.assertEquals(rspUpdateObj.get(0).getData().HDSize, "2 TB");
        Assert.assertEquals(rspUpdateObj.get(0).getData().color, "gray");
        Assert.assertEquals(rspUpdateObj.get(0).getData().capacity, "4 cpu");
        Assert.assertEquals(rspUpdateObj.get(0).getData().screenSize, "12 inch");

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

        ResponseSingleObject rspValUpdateObj = rspValidateUpdateObj.as(ResponseSingleObject.class);

        //Response Validation
        Assert.assertNotNull(rspValUpdateObj.getId(), "Expected id but got null");
        Assert.assertEquals(rspValUpdateObj.getName(), "Lenovo Yoga");
        Assert.assertEquals(rspValUpdateObj.getData().year, "2025");
        Assert.assertEquals(rspValUpdateObj.getData().price, "1999.99");
        Assert.assertEquals(rspValUpdateObj.getData().CPUModel, "Intel Core i8");
        Assert.assertEquals(rspValUpdateObj.getData().HDSize, "2 TB");
        Assert.assertEquals(rspValUpdateObj.getData().capacity, 4);
        Assert.assertEquals(rspValUpdateObj.getData().screenSize, 12);
        Assert.assertEquals(rspValUpdateObj.getData().color, "gray");

    }

    @Test(dependsOnMethods="createNewObject", priority = 3)
    public void partiallyUpdatedExisObject() throws JsonProcessingException{

        RequestPartiallyUpdateObject partiallyUpdateObject = new RequestPartiallyUpdateObject("Lenovo Ideapad", 2027, "white");
        ObjectMapper objectMapper = new ObjectMapper();
        String reqBodyPartiallyUpdateObj = objectMapper.writeValueAsString(partiallyUpdateObject);

        System.out.println("Json String Partially Update: " + reqBodyPartiallyUpdateObj);

        //Send PATCH request to Update Endpoint
        Response partiallyUpdateObj = RestAssured.given()
        .header("Content-Type","application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "39a0f904-b0f2-4428-80a3-391cea5d7d04")
        .pathParam("id", idObject)
        .body(reqBodyPartiallyUpdateObj)
        .log().all()
        .when()
        .patch("/webhook/{webhookId}/api/object/{id}");

        //Print the response
        System.out.println("Response: " + partiallyUpdateObj.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(partiallyUpdateObj.getStatusCode(), 200, "Status code is not 200");
        
        
        //Deserialize response to List<ResponsePartiallyUpdateObject>
        ResponsePartiallyUpdateObject rspPartiallyUpdateObj = objectMapper.readValue(
            partiallyUpdateObj.getBody().asString(),ResponsePartiallyUpdateObject.class);
                  
        
        //Response Validation       
        Assert.assertNotNull(rspPartiallyUpdateObj.getId(), "Expected id but got null");
        Assert.assertEquals(rspPartiallyUpdateObj.getName(), "Lenovo Ideapad");
        Assert.assertEquals(rspPartiallyUpdateObj.getData().year, "2027");
        Assert.assertEquals(rspPartiallyUpdateObj.getData().color, "white");


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

        ResponseSingleObject rspValPartiallyUpdateObj = rspValidatePartiallyUpdateObj.as(ResponseSingleObject.class);

        //Response Validation
        Assert.assertNotNull(rspValPartiallyUpdateObj.getId(), "Expected id but got null");
        Assert.assertEquals(rspValPartiallyUpdateObj.getName(), "Lenovo Ideapad");
        Assert.assertEquals(rspValPartiallyUpdateObj.getData().year, "2027");
        Assert.assertEquals(rspValPartiallyUpdateObj.getData().price, "1999.99");
        Assert.assertEquals(rspValPartiallyUpdateObj.getData().CPUModel, "Intel Core i8");
        Assert.assertEquals(rspValPartiallyUpdateObj.getData().HDSize, "2 TB");
        Assert.assertEquals(rspValPartiallyUpdateObj.getData().capacity, 4);
        Assert.assertEquals(rspValPartiallyUpdateObj.getData().screenSize, 12);
        Assert.assertEquals(rspValPartiallyUpdateObj.getData().color, "white");

    }

    @Test(dependsOnMethods="createNewObject", priority = 4)
    public void DeleteExistObject(){
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
        Response rspValDeleteObj = RestAssured.given()
        .header("Content-Type", "application/json")
        .header("Authorization", "Bearer " + tokenLogin)
        .pathParam("webhookId", "8749129e-f5f7-4ae6-9b03-93be7252443c")
        .pathParam("id", idObject)
        .log().all()
        .when()
        .get("/webhook/{webhookId}/api/objects/{id}");
        
        //Print the response
        System.out.println("Response: " + rspValDeleteObj.asPrettyString());

        //Status Code Validation
        Assert.assertEquals(rspValDeleteObj.getStatusCode(), 200, "Status code is not 200");

        //Response Validation
        Assert.assertEquals(rspValDeleteObj.getBody().asString().trim(), "{}", "Expected empty JSON object");

    }
}
