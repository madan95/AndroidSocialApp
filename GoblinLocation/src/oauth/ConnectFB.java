package oauth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectFB {
	public static final String APP_ID = "YOUR_FACEBOOK_APP_ID";
	public static final String APP_SECRET = "YOUR_FACEBOOK_APP_SECRET";
    public static final String REDIRECT_URI = "http://10.130.22.87:8080/GoblinLocation/OAuth";
    String accessToken = "";
    
    //Method to Setup URL to use FB API
	public String getFBLoginDialogUrl(){
		String loginUrl="";
		try{
			loginUrl="https://www.facebook.com/v2.8/dialog/oauth?"
                    + "client_id="+ APP_ID
                    + "&redirect_uri=" + URLEncoder.encode(REDIRECT_URI,"UTF-8")
                    + "&scope=email";
		}catch(Exception e){
			e.printStackTrace();
		}
		return loginUrl;
	}
	public String getGraphUrl(String code){
		String graphUrl="";
		try{
			graphUrl= "https://graph.facebook.com/v2.8/oauth/access_token?"
                    + "client_id="+ APP_ID
                    + "&redirect_uri="+ URLEncoder.encode(REDIRECT_URI, "UTF-8") 
                    + "&client_secret=" + APP_SECRET
                    + "&code=" + code;
		}catch(Exception e){
			e.printStackTrace();
		}
		return graphUrl;
	}
	public String getLongLivedAccessTokenUrl(String shortLivedToken){
		String longLivedAccessTokenUrl="";
		try {
			longLivedAccessTokenUrl = "https://graph.facebook.com/v2.8/oauth/access_token?"
                    +"client_id=" + APP_ID
                    +"&client_secret=" + APP_SECRET
                    +"&grant_type=fb_exchange_token"
                    +"&fb_exchange_token=" + shortLivedToken;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return longLivedAccessTokenUrl;
	}

	//Get JSON with Access-Token From FB 
	public String getShortLivedAccessToken(String code){
			URL fbGraphURL;
	        try {
	            fbGraphURL = new URL(getGraphUrl(code));
	        } catch (MalformedURLException e) {
	            throw new RuntimeException("Invalid code received " + e);
	        }
			accessToken = getTokenFromJson(getBufferedInputFromUrl(fbGraphURL));
			//Create Long Lived Access-Token to be used in Database and Client
			//getLongLivedAccessToken(accessToken);
		return accessToken;
	}
	public String getLongLivedAccessToken(String shortLivedToken){
        URL fbGraphURL;
        try {
            fbGraphURL = new URL(getLongLivedAccessTokenUrl(shortLivedToken));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid code received " + e);
        }
        return getTokenFromJson(getBufferedInputFromUrl(fbGraphURL));
	}

	//Utils to get Specific data from JSON and openConnection to URL
	public String getTokenFromJson(String json){
    	String accessToken="";
    	 try {
             JSONObject data = new JSONObject(json);
             accessToken=data.getString("access_token");
             } catch (JSONException e) {
             throw new RuntimeException("ERROR in parsing FB graph data. " + e);
         }
       	return accessToken;
    }
	public String getBufferedInputFromUrl(URL fbGraphUrl){
		URLConnection fbConnection;
        StringBuffer b = null;
        try {
            fbConnection = fbGraphUrl.openConnection();
            BufferedReader in;
            in = new BufferedReader(new InputStreamReader(fbConnection.getInputStream()));
            String inputLine;
            b = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                b.append(inputLine);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to connect with Facebook " + e);
        }
        return b.toString();
	}

	
}
