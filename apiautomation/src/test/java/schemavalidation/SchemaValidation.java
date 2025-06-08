package schemavalidation;

import org.testng.annotations.Test;

import com.demo.model.RequestRegister;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.response.Response;

public class SchemaValidation {


    @Test
    public void registerEmployee() throws JsonProcessingException{
    RestAssured.baseURI="https://whitesmokehouse.com";
    //1. Hit the endpoint Register with valid data  
    RequestRegister smRegister = new RequestRegister("testqa.wrc1181@gmail.com","Test", "@dmin123", "Technology", "085611789000");
    ObjectMapper objectMapper = new ObjectMapper();
    String reqBodyRegister = objectMapper.writeValueAsString(smRegister);
    
    Response responseRegister = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(reqBodyRegister)
            .log().all()
            .when()
            .post("/webhook/api/register");
        
        try{
            responseRegister.then().assertThat().body(matchesJsonSchemaInClasspath("register-schema.json"));
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Test
    public void loginAccount(){
    RestAssured.baseURI="https://whitesmokehouse.com";
    
    //Try using Serialize
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

    try{
            responseLogin.then().assertThat().body(matchesJsonSchemaInClasspath("login-schema.json"));
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }
}
