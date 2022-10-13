package videoplayer;


import iptv.Main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Player extends Application
{

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {

//        Getting the video url
        Main obj = new Main("1000");
        String path=obj.getVideUrl();


        VideoPlayer videoPlayer = new VideoPlayer(path);

        Scene scene = new Scene(videoPlayer,1330,820, Color.ORANGE);
//        primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Playing video");
        primaryStage.show();
    }




    public static  void main(){
        launch();
    }
}

