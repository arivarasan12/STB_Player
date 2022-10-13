package login;


import database.ConnectionMySql;
import frontend.Home;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import videoplayer.VideoPlayer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends JFrame implements ActionListener {


    JTextField usernameField;
    JPasswordField passwordField;
    JButton login,signup;
    JLabel username,password;
    JLabel imageLabel;


    Login() throws IOException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(640,460);
        setResizable(false);

        getContentPane().setBackground(new Color(0x242B2E));


        username = new JLabel("Username: ");
        username.setBounds(160,210,75,25);
        username.setForeground(Color.WHITE);

        password = new JLabel("Password: ");
        password.setBounds(160,260,75,25);
        password.setForeground(Color.WHITE);

        usernameField = new JTextField();
        usernameField.setBounds(245,210,200,25);

        passwordField = new JPasswordField();
        passwordField.setBounds(245,260,200,25);

        login = new JButton("Login");
        login.setBounds(240,310,100,25);
        login.addActionListener(this);

        signup = new JButton("SignUp");
        signup.setBounds(350,310,100,25);
        signup.addActionListener(this);

        imageLabel = new JLabel();
        BufferedImage img = ImageIO.read(new File("C:\\Users\\ariva\\Downloads\\abc.png"));
        ImageIcon loginImage = new ImageIcon(img);
        imageLabel.setIcon(loginImage);
        imageLabel.setBackground(new Color(0x242B2E));
        setForeground(new Color(0x242B2E));
        imageLabel.setOpaque(false);
        imageLabel.setBounds(200,15,400,177);


        setLocation(300,300);
        setTitle("STB Player-Login");

        this.add(imageLabel);
        this.add(username);
        this.add(password);
        this.add(usernameField);
        this.add(passwordField);
        this.add(login);
        this.add(signup);

        ImageIcon icon = new ImageIcon("C:\\Users\\ariva\\Downloads\\iptv-stable-v1-master\\iptv-stable-v1-master\\IPTV-JAVA-main\\src\\resources\\logo.png");
        setIconImage(icon.getImage());
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Event handling for login button
        if(e.getSource() == login){
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());
            Boolean isLoggedIn = false;

            try {
                Connection dbCon = ConnectionMySql.getConnection();
                Statement smt = dbCon.createStatement();
                String loginQuery = "SELECT username,password FROM account";
                ResultSet rs = smt.executeQuery(loginQuery);


                while (rs.next()) {
                    isLoggedIn =  rs.getString("username").equals(username) &&
                            rs.getString("password").equals(password);

                    if(isLoggedIn) {
                        // TODO: Direct to home page and dispose the current window
                        dispose();
                      Home.startmain();
                    }
                }

                dbCon.close();

            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if(!isLoggedIn){
                JOptionPane.showInternalMessageDialog(null,"Enter correct username or password !!!","Invalid Credentials !!!",JOptionPane.ERROR_MESSAGE);
            }

        }

        // Event handling for signup button
        if(e.getSource() == signup){
            dispose();
            new SignUp();
        }

    }



    public static void main(String[] args) throws IOException {
        Login loginTest = new Login();
    }
}