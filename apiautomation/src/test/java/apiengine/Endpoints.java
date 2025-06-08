package apiengine;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Endpoints {

    String token, tokenLogin;
    int idObject;

    public Endpoints(){
        RestAssured.baseURI="https://whitesmokehouse.com";
    }

    public Response registerEmployee(String bodyRequest){
        Response responseRegister = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(bodyRequest)
            .log().all()
            .when()
            .post("/webhook/api/register");
        
        return responseRegister;    
    }

    public Response loginEmployee(String bodyRequest){
        Response responseLogin = RestAssured.given()
            .header("Content-Type", "application/json")
            .body(bodyRequest)
            .log().all()
            .when()
            .post("/webhook/api/login");
        
        return responseLogin;    
    }

    public Response getSingleObject(String bodyRequest){
        Response responseSingleObj = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .pathParam("webhookId", "8749129e-f5f7-4ae6-9b03-93be7252443c")
            .pathParam("id", idObject)
            .log().all()
            .when()
            .get("/webhook/{webhookId}/api/objects/" + idObject);
    
        return responseSingleObj;    
    }

    public Response createObject(String bodyRequest){
        Response responseCreateObj = RestAssured.given()
            .header("Content-Type","application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .body(bodyRequest)
            .log().all()
            .when()
            .post("/webhook/api/objects");
        return responseCreateObj;  
    }

    public Response updateObject(String bodyRequest){
        Response responseUpdateObj = RestAssured.given()
            .header("Content-Type","application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .pathParam("webhookId", "37777abe-a5ef-4570-a383-c99b5f5f7906")
            .pathParam("id", idObject)
            .body(bodyRequest)
            .log().all()
            .when()
            .put("/webhook/{webhookId}/api/objects/{id}");
        return responseUpdateObj;  
    }

    public Response partiallyUpdateObject(String bodyRequest){
        Response responsePartiallyUpdateObj = RestAssured.given()
            .header("Content-Type","application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .pathParam("webhookId", "39a0f904-b0f2-4428-80a3-391cea5d7d04")
            .pathParam("id", idObject)
            .body(bodyRequest)
            .log().all()
            .when()
            .patch("/webhook/{webhookId}/api/object/{id}");
        return responsePartiallyUpdateObj;  
    }

    public Response deleteObject(String bodyRequest){
        Response responseDeleteObj = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .pathParam("webhookId", "d79a30ed-1066-48b6-83f5-556120afc46f")
            .pathParam("id", idObject)
            .log().all()
            .when()
            .delete("/webhook/{webhookId}/api/objects/{id}");
        return responseDeleteObj;  
    }

    public Response listObjectById(String bodyRequest){
        Response responseListObjById = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .queryParam("id", idObject)
            .log().all()
            .when()
            .get("/webhook/api/objectslistId");    
        return responseListObjById;    
    }

    public Response getAllDepartment(){
        Response responseListObjById = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .queryParam("id", idObject)
            .log().all()
            .when()
            .get("/webhook/api/objectslistId");    
        return responseListObjById;    
    }

    public Response getAllObject(){
         Response responseGetAllObj = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + tokenLogin)
            .log().all()
            .when()
            .get("webhook/api/objects");
        return responseGetAllObj;
    }
}
