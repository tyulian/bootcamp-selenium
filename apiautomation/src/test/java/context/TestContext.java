package context;

import java.util.HashMap;
import java.util.Map;

import io.restassured.response.Response;

public class TestContext {
    private Response response;
    private final Map<String, Object> data = new HashMap<>();

    public void setResponse(Response response){
        this.response = response;
    }

    public Response getResponse(){
        return response;
    }

    public void set(String key, Object value){
        data.put(key,value);
    }

    public <T> T get(String key, Class<T> type){
        return type.cast(data.get(key));
    }
}
