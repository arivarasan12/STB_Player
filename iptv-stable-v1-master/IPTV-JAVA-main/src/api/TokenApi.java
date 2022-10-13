package api;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TokenApi {

    String token;

    public TokenApi(){
        tokenRequest();
    }

    public String tokenParser(String responseBody)throws InterruptedException {
//        System.out.println(responseBody);
        JSONObject obj=new JSONObject(responseBody);
        System.out.println(responseBody);
        if(obj.get("js") instanceof  JSONObject){
            JSONObject temp= (JSONObject) obj.get("js");
            token=(String) temp.get("token");
        }else{
            Thread.sleep(2000);
            System.out.println("Makine request again");
            tokenRequest();
        }

        return  null;
    }

    public void tokenRequest(){
        String apiEndPoint = "http://tv.tvzon.tv/stalker_portal/server/load.php?type=stb&action=handshake&token=&prehash=0&JsHttpRequest=1-xml";
        HttpClient client =HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder().uri(URI.create(apiEndPoint))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply((body)-> {
                    try {
                        return (tokenParser(body));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).join();
    }

    public String getToken() {
        return token;
    }
}
