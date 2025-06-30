package helper;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigManager {
    private static final Dotenv dotenv = Dotenv.configure().load();

    public static String getBaseUrl(){
        return dotenv.get("BASE_URL");
    }

    public static String getBrowser(){
        return dotenv.get("BROWSER");
    }

    public static String getWebURL(){
        return dotenv.get("WEB_URL");

    }
}

/*public class ConfigManager2 {
    
    public static void main(String [] args){
    ConfigManager.getBaseUrl();
    System.out.println("Base URL: " + ConfigManager.getBaseUrl());
    }

}*/
