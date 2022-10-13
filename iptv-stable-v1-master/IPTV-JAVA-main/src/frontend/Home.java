package frontend;
import api.Jdbc;
import iptv.Main;
import javafx.scene.CacheHint;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import videoplayer.VideoPlayer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;

public class Home extends Application {
   static Scene scene;

    static String portno;
    static String[] imageurl =new String[32];
    static String[] channelno=new String[32];
//    public Home(){
//        Jdbc obj1 =new Jdbc();
//        obj1.jdbcrequest();
//        imageurl=obj1.getimgurl();
//        channelno=obj1.getportno();
//        launch(args);
//    }

    public void start(Stage stage) throws IOException, InterruptedException {
        Label label = new Label("English Channels");
        //Setting font to the label
        Font font = Font.font("Verdana", FontWeight.EXTRA_BOLD, 25);
        label.setFont(font);
        //Filling color to the label
        label.setTextFill(Color.BLACK);
        //Setting the position
        label.setTranslateX(10);
        label.setTranslateY(25);

        Label label1 = new Label("Tamil Channels");
        label1.setFont(font);
        label1.setTextFill(Color.BLACK);
        label1.setTranslateX(10);
        label1.setTranslateY(250);



        Label label2 = new Label("Sports Channels");
        label2.setFont(font);
        label2.setTextFill(Color.BLACK);
        label2.setTranslateX(10);
        label2.setTranslateY(475);


        Label label3 = new Label("Movie Channels");
        label3.setFont(font);
        label3.setTextFill(Color.BLACK);
        label3.setTranslateX(10);
        label3.setTranslateY(700);






        Image[] imagearr =new Image[32];
        ImageView[] imageviewarr =new ImageView[32];
        double[] sizex =new double[]{40,275,510,745,980,1215,1450,1685,50,275,510,745,980,1215,1450,1685,50,275,510,745,980,1215,1450,1685,50,275,510,745,980,1215,1450,1685};
        double[] sizey =new double[]{75,75,75,75,75,75,75,75,300,300,300,300,300,300,300,300,525,525,525,525,525,525,525,525,750,750,750,750,750,750,750,750};
        Button[] Buttonarr =new Button[32];



        Group root = new Group();
        // -------------------------------------------------------------------------------
        //Creating array of objects
        for(int i=0;i<32;i++){

            System.out.println(imageurl[i]);
            imagearr[i]=new Image(imageurl[i]);
            System.out.println(imagearr[i]);
            imageviewarr[i]=new ImageView(imagearr[i]);
            System.out.println(imageviewarr[i]);
            imageviewarr[i].setFitHeight(80);
            imageviewarr[i].setPreserveRatio(true);
            imageviewarr[i].setCache(true);
            imageviewarr[i].setCacheHint(CacheHint.SPEED);
            Buttonarr[i]=new Button();
            Buttonarr[i].setTranslateX(sizex[i]);
            Buttonarr[i].setTranslateY(sizey[i]);
            Buttonarr[i].setPrefSize(200, 150);
            Buttonarr[i].setGraphic(imageviewarr[i]);
            Buttonarr[i].setMnemonicParsing(true);
            Buttonarr[i].setStyle("-fx-background-color: #FFFFFF");

            root.getChildren().add(Buttonarr[i]);
        }



        root.getChildren().add(label);
        root.getChildren().add(label1);
        root.getChildren().add(label2);
        root.getChildren().add(label3);
         scene = new Scene(root, 1920, 970,Color.BEIGE);
        stage.setTitle("Button Graphics");
        stage.setScene(scene);
        stage.show();


        for ( int i = 0; i < 32; i++) {
            String id=String.valueOf(i);
            Buttonarr[i].setId(id);

            final int finali=i;
//            Buttonarr[i].setOnAction(event -> getbuttonid((Buttonarr[finali])) );
         Buttonarr[i].setOnAction(event -> {
             try {
                 getbuttonid((Button)event.getSource(),event);;
             } catch (IOException e) {
                 throw new RuntimeException(e);
             } catch (InterruptedException e) {
                 throw new RuntimeException(e);
             }
         });
//              Buttonarr[i].setOnAction(clickEvent);

        }


    }
    public static  void getbuttonid(Button button, ActionEvent actionEvent) throws IOException, InterruptedException {
        portno=button.getId();
        System.out.println(channelno[Integer.parseInt(portno)]);

        Main obj = null;
        obj = new Main(channelno[Integer.parseInt(portno)]);
        String path=obj.getVideUrl();
        Stage stage1  = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        VideoPlayer videoPlayer = new VideoPlayer(path);
        Scene scene1 = new Scene(videoPlayer,1080,720);

        stage1.setFullScreen(true);


        stage1.setScene(scene1);
        stage1.show();

        stage1.setOnCloseRequest(windowEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("You're about to logout!");
            alert.setContentText("Do you want to continue ?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                windowEvent.consume();
                videoPlayer.mediaPlayer.stop();

                stage1.setScene(scene);
                stage1.show();
            }
        });
    };

    public static void startmain(){
        Jdbc obj1 =new Jdbc();
        obj1.jdbcrequest();
        imageurl=obj1.getimgurl();
        channelno=obj1.getportno();
        launch();
    }


//    public void backToHome(WindowEvent event, Scene root){
//        event.consume();
//        Stage stage = (Stage) (((Node)event.getSource()).getScene().getWindow());
//        stage.setScene(root);
//        stage.show();
//    }
}