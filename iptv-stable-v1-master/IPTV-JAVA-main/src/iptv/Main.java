package iptv;
import api.ChannelLink;
import api.TokenApi;
import api.UserDetails;
import videoplayer.Player;

import java.io.IOException;


public class Main {
    String portno;

    static String token;
    public  static String videoURL;


    public String getVideUrl(){
        return videoURL;
    }

    void  startOperationSendingRequests() throws IOException, InterruptedException {
        TokenApi tokenApi = new TokenApi();
        token = tokenApi.getToken();

        UserDetails userDetails = new UserDetails(token);
        userDetails.userDetails();

        ChannelLink channelLink = new ChannelLink(token,portno);
        videoURL = channelLink.channelLink();


    }

    public  void reload() throws IOException, InterruptedException {
        startOperationSendingRequests();
    }

    public Main(String portno1) throws IOException, InterruptedException {

        portno=portno1;
        System.out.println('p'+portno);
        startOperationSendingRequests();
    }

}
