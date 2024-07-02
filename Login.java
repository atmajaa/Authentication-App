import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
public class Login extends Frame implements ActionListener {
    private TextField userNameField;
    private JPasswordField passwordField;
    private Button registerBtn, loginBtn;
    private Label messageLabel;
    //Connection
    private Connection connection;
    //Creating a reference of JFrame for Login and Registration screen
    JFrame frameObj;    
    JFrame frameObj2;
    public Login(){
        super("Login");
        //Create frame object
        frameObj2 = new JFrame("Login");
        //Create text fields
        userNameField = new TextField(3);
        passwordField = new JPasswordField(3); 
        //Create Buttons
        registerBtn = new Button("Register");
        registerBtn.addActionListener(this);
        loginBtn = new Button("Login");
        loginBtn.addActionListener(this);
        //Message label
        messageLabel = new Label("");
        //Add components to the Grid
        //For Login Screen
        frameObj2.setLayout(new GridLayout(4,0,10,10));
        frameObj2.setSize(400, 200);
        frameObj2.add(new Label("Username"));
        frameObj2.add(userNameField);
        frameObj2.add(new Label("Password"));
        frameObj2.add(passwordField);
        frameObj2.add(loginBtn);        
        frameObj2.add(registerBtn);
        frameObj2.setLocationRelativeTo(null);
        frameObj2.setVisible(true);
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");//Loads JDBC Driver
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/UserLogin", "root", "");
        } 
        catch 
        (Exception ex) 
        {
            //Display error with the help of dialog box.
            JDialog dialog = new JDialog(frameObj, "Error");
            //Create a label
            JLabel l = new JLabel("Error connecting to database: " + ex.getMessage());
            dialog.add(l);
            //Setsize of dialog
            dialog.setSize(350, 100);
            //Set visibility of dialog
            dialog.setVisible(true);
            //Set the dialog box to center
            dialog.setLocationRelativeTo(null);
        }
    }
    public void actionPerformed(ActionEvent e){
        //For Login
        try {
            //Get username and password from textfields
            String name = userNameField.getText();
            String password = new String(passwordField.getPassword());

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM UserDetails WHERE username='" + name + "' AND userpassword='" + password + "'");
             if (e.getSource().equals(loginBtn)) {
                //If user found login success
                if (resultSet.next()) {
                // User successfully logged in
                JDialog dialog = new JDialog(frameObj, "Success");
                JLabel l = new JLabel("User " + name + " logged in successfully");
                dialog.add(l);
                dialog.setSize(350, 100);
                dialog.setVisible(true);
                dialog.setLocationRelativeTo(null);
                } else {
                    // Incorrect username or password
                    JDialog dialog = new JDialog(frameObj, "Error");
                    JLabel l = new JLabel("Invalid username or password. Try again.");
                    dialog.add(l);
                    dialog.setSize(350, 100);
                    dialog.setVisible(true);
                    dialog.setLocationRelativeTo(null);
            } 
        }
    }
        catch (Exception ex) {
            messageLabel.setText("Error performing operation: " + ex.getMessage());
            } 
        finally {
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException ex) {
                    // handle SQL exceptions
                }
            }
        }

    public static void main(String[] args) {
        new Login();
    }
}
