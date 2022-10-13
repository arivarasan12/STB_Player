package login;

import database.ConnectionMySql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends JFrame implements ActionListener {

    JTextField usernameField,emailField,macField;
    JPasswordField passwordField,confirmPasswordField;
    JLabel title,username,email,pack,lang,mac,password,confirmPassword,country;
    JComboBox packPeriod,langDetail,countryDetail;
    JButton submit,backToLogin;


    SignUp(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(600,600);
        setResizable(false);


        title = new JLabel("Register to STB Player");
        title.setBounds(230,30,150,30);
        title.setForeground(Color.WHITE);

        username = new JLabel("Enter your username: ");
        username.setBounds(130,80,150,30);
        username.setForeground(Color.WHITE);

        email = new JLabel("Enter your email: ");
        email.setBounds(130,130,150,30);
        email.setForeground(Color.WHITE);

        pack = new JLabel("Choose your pack: ");
        pack.setBounds(130,180,150,30);
        pack.setForeground(Color.WHITE);

        lang = new JLabel("Choose your language: ");
        lang.setBounds(130,230,150,30);
        lang.setForeground(Color.WHITE);

        mac = new JLabel("Enter your MAC address: ");
        mac.setBounds(130,280,150,30);
        mac.setForeground(Color.WHITE);

        password = new JLabel("Enter your password: ");
        password.setBounds(130,330,150,30);
        password.setForeground(Color.WHITE);

        confirmPassword = new JLabel("Confirm your password: ");
        confirmPassword.setBounds(130,380,150,30);
        confirmPassword.setForeground(Color.WHITE);

        country = new JLabel("Enter your country: ");
        country.setBounds(130,430,150,30);
        country.setForeground(Color.WHITE);

        usernameField = new JTextField();
        usernameField.setBounds(320,80,180,30);
        usernameField.setToolTipText("Username must be greater than length 6");

        emailField = new JTextField();
        emailField.setBounds(320,130,180,30);
        emailField.setToolTipText("Email id must have a domain");

        macField = new JTextField();
        macField.setBounds(320,280,180,30);


        passwordField = new JPasswordField();
        passwordField.setBounds(320,330,180,30);
        passwordField.setToolTipText("Password must be greater than length 5");


        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(320,380,180,30);


        String[] period = {"3 Months","6 Months","Free Trial"};
        packPeriod = new JComboBox(period);
        packPeriod.setBounds(320,180,180,30);
        packPeriod.addActionListener(this);


        String[] languages = {"English","Tamil"};
        langDetail = new JComboBox(languages);
        langDetail.setBounds(320,230,180,30);


        String[] countries = {"US","India","London"};
        countryDetail = new JComboBox(countries);
        countryDetail.setBounds(320,430,180,30);



        submit = new JButton("Submit");
        submit.setBounds(320,500,150,30);
        submit.addActionListener(this);

        backToLogin = new JButton("Back To Login");
        backToLogin.addActionListener(this);
        backToLogin.setBounds(150,500,150,30);

        add(title);
        add(username);
        add(usernameField);
        add(email);
        add(emailField);
        add(pack);
        add(packPeriod);
        add(lang);
        add(langDetail);
        add(mac);
        add(macField);
        add(password);
        add(passwordField);
        add(confirmPassword);
        add(confirmPasswordField);
        add(country);
        add(countryDetail);
        add(submit);
        add(backToLogin);

        setLocation(300,300);
        setTitle("STB Player-Register");
        ImageIcon icon = new ImageIcon("D:\\IPTV\\src\\resources\\logo.png");
        setIconImage(icon.getImage());
        getContentPane().setBackground(new Color(0x242B2E));
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == submit){
            try {
                registerUser();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == backToLogin){
            dispose();
            try {
                new Login();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        if(e.getSource() == packPeriod){
            //Entering the builtin mac address for free trial
            String pack = packPeriod.getSelectedItem().toString();
            if(pack.equals("Free Trial")){
                macField.setText("00:1A:79:BC:CE:6B");
                macField.setEnabled(false);
            }
        }
    }

    private void registerUser() throws SQLException, ClassNotFoundException, IOException {
        String username = usernameField.getText();
        String email = emailField.getText();
        String pack = packPeriod.getSelectedItem().toString();
        String lang = langDetail.getSelectedItem().toString();
        String mac = macField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confirmPassword = String.valueOf(confirmPasswordField.getPassword());
        String country = countryDetail.getSelectedItem().toString();




        //Validation of inputs
        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || mac.isEmpty()){
            JOptionPane.showInternalMessageDialog(null,"The fields cannot be empty !!!","Empty fields !!!",JOptionPane.ERROR_MESSAGE);
            return;
        }else if(username.length() <= 6){
            JOptionPane.showInternalMessageDialog(null,"Username should be greater than 6","Invalid Username !!!",JOptionPane.ERROR_MESSAGE);
            return;
        }else if(!regexValidator(email,"^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
            JOptionPane.showInternalMessageDialog(null,"Email id must have a domain","Invalid Email !!!",JOptionPane.ERROR_MESSAGE);
            return;
        }else if(password.length() <= 5){
            JOptionPane.showInternalMessageDialog(null,"Password must be greater than length 5","Invalid Password !!!",JOptionPane.ERROR_MESSAGE);
            return;
        }else if(!password.equals(confirmPassword)){
            JOptionPane.showInternalMessageDialog(null,"Confirm password doesn't match the password","Invalid Password !!!",JOptionPane.ERROR_MESSAGE);
            return;
        }

        //Inserting the user into the database
        Connection dbConn = ConnectionMySql.getConnection();

        String insertQuery = "INSERT INTO users (username,email,pack,lang,mac,password,country) VALUES (?,?,?,?,?,?,?)";

        PreparedStatement psmt = dbConn.prepareStatement(insertQuery);
        psmt.setString(1,username);
        psmt.setString(2,email);
        psmt.setString(3,pack);
        psmt.setString(4,lang);
        psmt.setString(5,mac);
        psmt.setString(6,password);
        psmt.setString(7,country);
        psmt.executeUpdate();

        dbConn.close();

        dispose();
        new Login();
    }

    public boolean regexValidator(String str,String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

}