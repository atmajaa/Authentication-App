import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
public class Registration extends Frame implements ActionListener {   
    private TextField userNameField;
    private JPasswordField passwordField,confirmPasswordField;
    private Button registerBtn, loginBtn;
    private Label messageLabel;
    //Connection
    private Connection connection;
    //Creating a reference of JFrame for Login and Registration screen
    JFrame frameObj;    
    public Registration(){
        super("Login");
        //Create frame object
        frameObj = new JFrame("Registration");
        //Create text fields
        userNameField = new TextField(3);
        passwordField = new JPasswordField(3);
        confirmPasswordField = new JPasswordField(3);  
        //Create Buttons
        registerBtn = new Button("Register");
        registerBtn.addActionListener(this);
        loginBtn = new Button("Login");
        loginBtn.addActionListener(this);
        //Message label
        messageLabel = new Label(""); 
        //Add components to the Grid
        //For Registration Screen
        frameObj.setLayout(new GridLayout(5,0,5,5));
        frameObj.add(new Label("Username"));
        frameObj.add(userNameField);
        frameObj.add(new Label("Password"));
        frameObj.add(passwordField);
        frameObj.add(new Label("Confirm Password"));
        frameObj.add(confirmPasswordField);
        frameObj.add(registerBtn);
        frameObj.add(loginBtn);
        // Set window size and visibility
        frameObj.setSize(400, 200);
        frameObj.setVisible(true);
        //Sets the frame position to the center of the screen
        frameObj.setLocationRelativeTo(null);
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
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
        //For registration
        try{
            String name = userNameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmpassword = new String(confirmPasswordField.getPassword());
            Statement statement = connection.createStatement();
            if(e.getSource() == registerBtn){
                //ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM UserDetails");
                //resultSet.next();
                //int id = 2;
                //System.out.println(id);
                if(password.equals(confirmpassword)){
                statement.executeUpdate("INSERT INTO UserDetails (username, userpassword) VALUES ('" + name + "', '" + password + "')");
                JDialog dialog = new JDialog(frameObj, "Error");
                //Create a label
                JLabel l = new JLabel("User Registered Sucessfully! Please Log In");
                dialog.add(l);
                //Setsize of dialog
                dialog.setSize(350, 100);
                //Set visibility of dialog
                dialog.setVisible(true);
                //Set the dialog box to center
                dialog.setLocationRelativeTo(null);
                }
                else{
                JDialog dialog = new JDialog(frameObj, "Error");
                //Create a label
                JLabel l = new JLabel("Passwords did not match. Try again");
                dialog.add(l);
                //Setsize of dialog
                dialog.setSize(350, 100);
                //Set visibility of dialog
                dialog.setVisible(true);
                //Set the dialog box to center
                dialog.setLocationRelativeTo(null);
                }
            }
        }
        catch(Exception ex){
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
        new Registration();
    }
}
