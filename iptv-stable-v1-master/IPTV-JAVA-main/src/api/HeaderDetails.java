package api;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class HeaderDetails {

    String bearer;

    public HeaderDetails(String token){
        bearer ="Bearer "+token;
    }

    public void setHeader(HttpURLConnection httpConn) throws ProtocolException {
        httpConn.setRequestMethod("GET");
        httpConn.setRequestProperty("User-Agent", "Mozilla/5.0 (QtEmbedded; U; Linux; C) AppleWebKit/533.3 (KHTML, like Gecko) MAG200 stbapp ver: 2 rev: 250 Safari/533.3");
        httpConn.setRequestProperty("X-User-Agent", "Model: MAG250; Link: WiFi");
        httpConn.setRequestProperty("Referer", "http://tv.tvzon.tv/stalker_portal/c/");
        httpConn.setRequestProperty("Cookie", "mac=00:1A:79:BC:CE:6B; stb_lang=en; timezone=GMT");
        httpConn.setRequestProperty("Authorization", bearer);
        httpConn.setRequestProperty("Accept", "/");
        httpConn.setRequestProperty("Host", "tv.tvzon.tv");
        httpConn.setRequestProperty("Connection", "Keep-Alive");
        httpConn.setRequestProperty("Accept-Encoding", "gzip");
    }
}
