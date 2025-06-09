package apiengine;

import helper.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Endpoints {

    //private final TestContext context;

    public Endpoints(){
        String baseUrl = ConfigManager.getBaseUrl();
        RestAssured.baseURI=baseUrl;
    }

   /* public Endpoints(TestContext context){
        this.context = context;
    } */

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

    public Response getSingleObject(String token, int idObject){
        Response responseSingleObj = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + idObject);
    
        return responseSingleObj;    
    }

    public Response createObject(String bodyRequest, String token){
        Response responseCreateObj = RestAssured.given()
            .header("Content-Type","application/json")
            .header("Authorization", "Bearer " + token)
            .body(bodyRequest)
            .log().all()
            .when()
            .post("/webhook/api/objects");
        return responseCreateObj;  
    }

    public Response updateObject(String bodyRequest, String token, int idObject){
        Response responseUpdateObj = RestAssured.given()
            .header("Content-Type","application/json")
            .header("Authorization", "Bearer " + token)
            .body(bodyRequest)
            .log().all()
            .when()
            .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + idObject);
        return responseUpdateObj;  
    }

    public Response partiallyUpdateObject(String bodyRequest,String token, int idObject){
        Response responsePartiallyUpdateObj = RestAssured.given()
            .header("Content-Type","application/json")
            .header("Authorization", "Bearer " + token)
            .body(bodyRequest)
            .log().all()
            .when()
            .patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/"+idObject);
        return responsePartiallyUpdateObj;  
    }

    public Response deleteObject(String token, int idObject){
        Response responseDeleteObj = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + idObject);
        return responseDeleteObj;  
    }

    public Response listObjectById(String token, int idObject){
        Response responseListObjById = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .get("/webhook/api/objectslistId?id=" + idObject);    
        return responseListObjById;    
    }

    public Response getAllDepartment(String token){
        Response responsegetAllDepartment = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .get("/webhook/api/department");  
        return responsegetAllDepartment;    
    }

    public Response getAllObject(String token){
         Response responseGetAllObj = RestAssured.given()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + token)
            .log().all()
            .when()
            .get("/webhook/api/objects");
        return responseGetAllObj;
    }
}
