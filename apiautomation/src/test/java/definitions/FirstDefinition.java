  /*package definitions;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.testng.Assert;

import com.demo.responsemodel.ResponseAddObject;
import com.demo.responsemodel.ResponsePartiallyUpdateObject;
import com.demo.responsemodel.ResponseSingleObject;
import com.demo.responsemodel.ResponseUpdateObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.And;
  import io.cucumber.java.en.Given;
  import io.cucumber.java.en.Then;
  import io.cucumber.java.en.When;
  import io.restassured.RestAssured;
  import io.restassured.module.jsv.JsonSchemaValidator;
  import io.restassured.response.Response;

  public class FirstDefinition{
      private String baseUrl;
      private Response response;
      private static String token;
      private static Integer idObject;
      
      @Given("The base url in this feature is {string}")
      public void set_baseurl(String baseUrl){
          this.baseUrl = baseUrl;
          System.out.println("Base URL set: " + baseUrl);

      }

     @When("Send an http {string} request to {string} with body:")    
      public void send_request_http(String method, String url, String body){
          switch (method.toUpperCase()) {
          case "GET":
                  response = RestAssured.given()
                          .header("Content-Type", "application/json")
                          .header("Authorization", "Bearer " + token)
                          .pathParam("webhookId", "8749129e-f5f7-4ae6-9b03-93be7252443c")
                          .pathParam("id", idObject)
                          .log().all()
                          .when()
                          .get(baseUrl + url);
                  break;

              case "DELETE":
                  response = RestAssured.given()
                          .header("Content-Type", "application/json")
                          .header("Authorization", "Bearer " + token)
                          .pathParam("webhookId", "d79a30ed-1066-48b6-83f5-556120afc46f")
                          .pathParam("id", idObject)
                          .log().all()
                          .when()
                          .delete(baseUrl + url);
                  break;

              case "POST":
                  response = RestAssured.given()
                          .header("Content-Type", "application/json")
                          .header("Authorization", this.token != null ? "Bearer " + this.token : "")
                          .body(body)
                          .log().all()
                          .when()
                          .post(baseUrl + url);
                  break;

              case "PUT":
                  response = RestAssured.given()
                          .header("Content-Type", "application/json")
                          .header("Authorization", this.token != null ? "Bearer " + this.token : "")
                          .pathParam("webhookId", "37777abe-a5ef-4570-a383-c99b5f5f7906")
                          .pathParam("id", idObject)
                          .body(body)
                          .log().all()
                          .when()
                          .put(baseUrl + url);
                  break;
              
              case "PATCH":
                  response = RestAssured.given()
                          .header("Content-Type", "application/json")
                          .header("Authorization", "Bearer " + token)
                          .pathParam("webhookId", "39a0f904-b0f2-4428-80a3-391cea5d7d04")
                          .pathParam("id", idObject)
                          .body(body)
                          .log().all()
                          .when()
                          .patch(baseUrl + url);
                  break;
              default:
                  throw new IllegalArgumentException("Metode HTTP tidak didukung: " + method);
          }
          System.out.println("Base URL set: " + baseUrl);

      }


      @Then("The response code must be {int}")
      public void validate_status_code(int statusCode){
          assert response.statusCode() == statusCode: "Error, due to actual status code is " + response.statusCode();
        }

      @And("The response schema should be match with schema {string}")
      public void schema_validation(String schemaPath){
          response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));         
        }

      @And("The Id is not null")
      public void validate_id_is_not_null(){
          String id = response.jsonPath().getString("id");
        Assert.assertNotNull(id, "Expected id but got null");
      }

      @And("The email should equal {string}")
      public void validate_email(String expectedEmail){
        String actualEmail = response.jsonPath().getString("email");
        Assert.assertEquals(actualEmail, expectedEmail);
      }

      @And("The full_name should equal {string}") 
      public void validate_full_name(String expectedFullName){
        String actualFullName = response.jsonPath().getString("full_name");
        Assert.assertEquals(actualFullName, expectedFullName);
      }

      @And("The department should equal {string}")
      public void validate_department(String expectedDepartment){
        String actualDepartment = response.jsonPath().getString("department");
        Assert.assertEquals(actualDepartment, expectedDepartment);
      }

      @And("The phone_number should equal {string}")
      public void validate_phone_number(String expectedPhoneNumber){
        String actualPhoneNumber = response.jsonPath().getString("phone_number");
        Assert.assertEquals(actualPhoneNumber, expectedPhoneNumber);
      }
        
      @And("Save the token from the response to the local storage")
      public void save_token(){
        token = response.jsonPath().getString("token");
        assertNotNull(token);
        System.out.println("Token: " + token);

      }

      @Given("Have token on the local storage")
      public void assert_token_in_variable(){
        assertNotNull("Token is not null", token);
      }

      @And("The idObject is not null")
      public void validate_idObject(){
          if (idObject == null) {
            try {
                FirstDefinition.idObject = response.jsonPath().getInt("id"); // jika response adalah object
            } catch (Exception e) {
                FirstDefinition.idObject = response.jsonPath().getInt("[0].id"); // jika response adalah array
            }
          }

          Assert.assertNotNull(idObject, "idObject is null");
          System.out.println("idObject saved: " + idObject);
      }

      @And("Save the idObject from the response to the local storage")
      public void save_idObject(){
          Assert.assertNotNull(idObject, "idObject must already be saved from previous step");
          System.out.println("idObject saved: " + idObject);
      }

      @And("The name from the create response should be {string}")
      public void validate_name(String expectedName) throws JsonProcessingException{         
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(response.getBody().asString(), new TypeReference<List<ResponseAddObject>>() {});
        String actualName = rspAddObj.get(0).getName();
        Assert.assertEquals(expectedName, actualName);
      }

      @And("The year from the create response should be {int}")
      public void validate_year(int expectedYear) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseAddObject>>(){});
        int actualYear = rspAddObj.get(0).getData().getYear();
        Assert.assertEquals(expectedYear, actualYear);

      }

      @And("The price from the create response should be {double}")
      public void validate_price(double expectedPrice) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseAddObject>>(){});
        double actualPrice = rspAddObj.get(0).getData().getPrice();
        Assert.assertEquals(expectedPrice, actualPrice);
      }
      
      @And("The cpu_model from the create response should be {string}")
      public void validate_CPUModel(String expectedCPUModel) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseAddObject>>(){});
        String actualCPUModel = rspAddObj.get(0).getData().getCPUModel();
        Assert.assertEquals(expectedCPUModel, actualCPUModel);
      }
      
      @And("The hard_disk_size from the create response should be {string}")
      public void validate_HDSize(String expectedHDSize) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseAddObject>>(){});
        String actualHDSize = rspAddObj.get(0).getData().getHDSize();
        Assert.assertEquals(expectedHDSize, actualHDSize);
      }
      
      @And("The capacity from the create response should be {string}")
      public void validate_capacity(String expectedCapacity) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseAddObject>>(){});
        String actualCapacity = rspAddObj.get(0).getData().getCapacity();
        Assert.assertEquals(expectedCapacity, actualCapacity);
      }
      
      @And("The screen_size from the create response should be {string}")
      public void validate_screenSize(String expectedScreenSize) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseAddObject>>(){});
        String actualScreenSize = rspAddObj.get(0).getData().getScreenSize();
        Assert.assertEquals(expectedScreenSize, actualScreenSize);
      }
      
      @And("The color from the create response should be {string}")
      public void validate_color(String expectedColor) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseAddObject> rspAddObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseAddObject>>(){});
        String actualColor = rspAddObj.get(0).getData().getColor();
        Assert.assertEquals(expectedColor, actualColor);
      }

      ////////////////////////////////////////////////////////////////////////
      @And("The name from the single-object response should be {string}")
      public void validate_singleName(String expectedName){      
        ResponseSingleObject rspSingleObj = response.as(ResponseSingleObject.class);
        String actualName = rspSingleObj.getName();
        Assert.assertEquals(expectedName, actualName);
      }

      @And("The year from the single-object response should be {string}")
      public void validate_singleYear(String expectedYear){
        ResponseSingleObject rspSingleObj = response.as(ResponseSingleObject.class);
        String actualYear = rspSingleObj.getData().getYear();
        Assert.assertEquals(expectedYear, actualYear);

      }

      @And("The price from the single-object response should be {string}")
      public void validate_singlePrice(String expectedPrice){
        ResponseSingleObject rspSingleObj = response.as(ResponseSingleObject.class);
        String actualPrice = rspSingleObj.getData().getPrice();
        Assert.assertEquals(expectedPrice, actualPrice);
      }
      
      @And("The cpu_model from the single-object response should be {string}")
      public void validate_singleCPUModel(String expectedCPUModel){
        ResponseSingleObject rspSingleObj = response.as(ResponseSingleObject.class);
        String actualCPUModel = rspSingleObj.getData().getCPUModel();
        Assert.assertEquals(expectedCPUModel, actualCPUModel);
      }
      
      @And("The hard_disk_size from the single-object response should be {string}")
      public void validate_singleHDSize(String expectedHDSize){
        ResponseSingleObject rspSingleObj = response.as(ResponseSingleObject.class);
        String actualHDSize = rspSingleObj.getData().getHDSize();
        Assert.assertEquals(expectedHDSize, actualHDSize);
      }
      
      @And("The capacity from the single-object response should be {int}")
      public void validate_singleCapacity(int expectedCapacity){
        ResponseSingleObject rspSingleObj = response.as(ResponseSingleObject.class);
        int actualCapacity = rspSingleObj.getData().getCapacity();
        Assert.assertEquals(expectedCapacity, actualCapacity);
      }
      
      @And("The screen_size from the single-object response should be {int}")
      public void validate_singleScreenSize(int expectedScreenSize){
        ResponseSingleObject rspSingleObj = response.as(ResponseSingleObject.class);
        int actualScreenSize = rspSingleObj.getData().getScreenSize();
        Assert.assertEquals(expectedScreenSize, actualScreenSize);
      }
      
      @And("The color from the single-object response should be {string}")
      public void validate_singleColor(String expectedColor){
        ResponseSingleObject rspSingleObj = response.as(ResponseSingleObject.class);
        String actualColor = rspSingleObj.getData().getColor();
        Assert.assertEquals(expectedColor, actualColor);
      }

      /////////////////////////////////////////////////////////////////////////// 

      @And("The name from the update response should be {string}")
      public void validate_updateName(String expectedName) throws JsonProcessingException{         
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(response.getBody().asString(), 
        new TypeReference<List<ResponseUpdateObject>>() {});
        String actualName = rspUpdateObj.get(0).getName();
        Assert.assertEquals(expectedName, actualName);
      }

      @And("The year from the update response should be {int}")
      public void validate_updateYear(int expectedYear) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseUpdateObject>>(){});
        int actualYear = rspUpdateObj.get(0).getData().getYear();
        Assert.assertEquals(expectedYear, actualYear);

      }

      @And("The price from the update response should be {double}")
      public void validate_updatePrice(double expectedPrice) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseUpdateObject>>(){});
        double actualPrice = rspUpdateObj.get(0).getData().getPrice();
        Assert.assertEquals(expectedPrice, actualPrice);
      }
      
      @And("The cpu_model from the update response should be {string}")
      public void validate_updateCPUModel(String expectedCPUModel) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseUpdateObject>>(){});
        String actualCPUModel = rspUpdateObj.get(0).getData().getCPUModel();
        Assert.assertEquals(expectedCPUModel, actualCPUModel);
      }
      
      @And("The hard_disk_size from the update response should be {string}")
      public void validate_updateHDSize(String expectedHDSize) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseUpdateObject>>(){});
        String actualHDSize = rspUpdateObj.get(0).getData().getHDSize();
        Assert.assertEquals(expectedHDSize, actualHDSize);
      }
      
      @And("The capacity from the update response should be {string}")
      public void validate_updateCapacity(String expectedCapacity) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseUpdateObject>>(){});
        String actualCapacity = rspUpdateObj.get(0).getData().getCapacity();
        Assert.assertEquals(expectedCapacity, actualCapacity);
      }
      
      @And("The screen_size from the update response should be {string}")
      public void validate_updateScreenSize(String expectedScreenSize) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseUpdateObject>>(){});
        String actualScreenSize = rspUpdateObj.get(0).getData().getScreenSize();
        Assert.assertEquals(expectedScreenSize, actualScreenSize);
      }
      
      @And("The color from the update response should be {string}")
      public void validate_updateColor(String expectedColor) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        List<ResponseUpdateObject> rspUpdateObj = objectMapper.readValue(response.body().asString(), 
        new TypeReference <List<ResponseUpdateObject>>(){});
        String actualColor = rspUpdateObj.get(0).getData().getColor();
        Assert.assertEquals(expectedColor, actualColor);
      }

      /////////////////////////////////////////////////////////////////////////

      @And("The name from the partially update response should be {string}")
      public void validate_partiallyUpdateName(String expectedPartName){      
        ResponsePartiallyUpdateObject rspPartiallyUpdateObj = response.as(ResponsePartiallyUpdateObject.class);
        String actualPartName = rspPartiallyUpdateObj.getName();
        Assert.assertEquals(expectedPartName, actualPartName);
        System.out.println("Actual name from response: " + actualPartName);
      }

      @And("The year from the partially update response should be {string}")
      public void validate_partiallyUpdateYear(String expectedYear){
        ResponsePartiallyUpdateObject rspPartiallyUpdateObj = response.as(ResponsePartiallyUpdateObject.class);
        String actualYear = rspPartiallyUpdateObj.getData().getYear();
        Assert.assertEquals(expectedYear, actualYear);

      }
   
      @And("The color from the partially update response should be {string}")
      public void validate_partiallyUpdateColor(String expectedColor){
        ResponsePartiallyUpdateObject rspPartiallyUpdateObj = response.as(ResponsePartiallyUpdateObject.class);
        String actualColor = rspPartiallyUpdateObj.getData().getColor();
        Assert.assertEquals(expectedColor, actualColor);
      }

      ////////////////////////////////////////////////////////////////////////////

  }
*/