package api;
import database.ConnectionMySql;

import java.sql.*;
public class Jdbc {
    String channelno[]=new  String[32];
    String channelname[]=new String[32];
    String imgurl[]=new String[32];
    public String[] getimgurl(){
        return imgurl;
    }
    public String[] getportno(){return  channelno;}


    public void jdbcrequest () {

        try {
            Connection con = ConnectionMySql.getConnection();
            Statement stmt=con.createStatement();
            /*	stmt.execute("Drop table abc");*/
            ResultSet rs=stmt.executeQuery("select channel_number,channel_name,image_url from channelists");
            int i=0;

            while(rs.next()) {
                System.out.println(rs.getString("image_url"));
                channelno[i]=rs.getString("channel_number");
                channelname[i]=rs.getString("channel_name");
                imgurl[i++]=rs.getString("image_url");


            }

            imgurl[0] = "C:\\Users\\ariva\\Downloads\\iptv-stable-v1-master\\iptv-stable-v1-master\\IPTV-JAVA-main\\src\\resources\\icons\\discovery.png";
            imgurl[1] = "C:\\Users\\ariva\\Downloads\\iptv-stable-v1-master\\iptv-stable-v1-master\\IPTV-JAVA-main\\src\\resources\\icons\\colors.png";
            imgurl[2] = "C:\\Users\\ariva\\Downloads\\iptv-stable-v1-master\\iptv-stable-v1-master\\IPTV-JAVA-main\\src\\resources\\icons\\sony.png";
            imgurl[3] = "C:\\Users\\ariva\\Downloads\\iptv-stable-v1-master\\iptv-stable-v1-master\\IPTV-JAVA-main\\src\\resources\\icons\\zeetv.png";
        }catch(Exception e){
            System.out.println(e);
        }
    }
}

