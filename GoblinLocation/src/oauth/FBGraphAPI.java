package oauth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class FBGraphAPI {
	private String accessToken;
	public FBGraphAPI(String accessToken){
		this.accessToken = accessToken;
	}
	
	public String getFBGraph(){
		String graphJSON = null;
		try {
            String graphApiLink = "https://graph.facebook.com/v2.8/me?&fields=name,link,email,gender&access_token=" + accessToken;
            URL graphUrl = new URL(graphApiLink);
            URLConnection connection = graphUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer jsonResult = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
                jsonResult.append(inputLine + "\n");
            in.close();
            graphJSON = jsonResult.toString();
        } catch (Exception e) {
            throw new RuntimeException("ERROR in getting FB graph data. " + e);
        }
		return graphJSON;
	}
	 @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map getGraphData(String fbGraph) {
	        Map fbUser = new HashMap();
	        try {
	            JSONObject data = new JSONObject(fbGraph);
	            fbUser.put("id", data.getString("id"));
	            fbUser.put("name", data.getString("name"));
	            if (data.has("link"))
	                fbUser.put("link", data.getString("link"));
	            if (data.has("email"))
	                fbUser.put("email", data.getString("email"));
	            if (data.has("gender"))
	                fbUser.put("gender", data.getString("gender"));
	        } catch (JSONException e) {
	            throw new RuntimeException("ERROR in parsing FB graph data. " + e);
	        }
	        return fbUser;
	    }
}
