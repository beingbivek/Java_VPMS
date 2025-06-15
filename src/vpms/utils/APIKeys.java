/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.utils;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author being
 */
public class APIKeys {
    public String getStripeKeys(){
        try {
            String urlString = "https://dummyjson.com/c/9f7a-d8ca-4849-b801";
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            
            String keyValue = jsonObject.has("key") && !jsonObject.get("key").isJsonNull()
                    ? jsonObject.get("key").getAsString()
                    : null;
            
            return keyValue;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    
    

}
