package GUI;

import UIController.UIController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * This is the Login Frame. When the user launches the program, this is the first frame displayed to the user.
 * - If the user already has an account, the user can enter USER ID and password, then click the LOGIN button to
 *   enter HomeFrame
 * - If the user does not have an account, the user can then click the REGISTER button to enter RegisterFrame to
 *   register an account
 */

public class LoginFrame extends JFrame implements ActionListener, KeyListener {

    JLabel programTitle = new JLabel("STUDY BUDDY FINDER");
    JLabel userLabel = new JLabel("USER ID:");
    JLabel passwordLabel = new JLabel("Password:");
    JLabel errorLabel = new JLabel("Error: Incorrect login info");
    JButton loginBTN = new JButton("LOGIN");
    JButton registerBTN = new JButton("REGISTER");
    JTextField userIDText = new JTextField("");
    JPasswordField passwordText = new JPasswordField("");

    UIController uiController;
    Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);

    /**
     * This is the constructor method. Creates LoginFrame,
     * - User can enter USER ID
     * - User can enter password
     * - User can click LOGIN button to log in
     * - User can click REGISTER button to register a new account
     */
    public LoginFrame(UIController uiController){
        this.uiController = uiController;

        this.setTitle("Login"); // sets frame's title
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the frame
        this.setResizable(false); // fixed size for frame
        this.setLayout(null);
        this.setSize(340, 230);
        this.setLocationRelativeTo(null); // centers the frame relative to the monitor

        // buttons
        loginBTN.setBounds(60, 160, 100, 20);
        loginBTN.addActionListener(this);
        loginBTN.setFocusable(false);
        loginBTN.setEnabled(false);
        registerBTN.setBounds(180, 160, 100, 20);
        registerBTN.addActionListener(this);
        registerBTN.setFocusable(false);

        // labels
        userLabel.setBounds(10,75,100,20);
        passwordLabel.setBounds(10,100,100,20);
        programTitle.setBounds(60, 0, 300, 50);
        programTitle.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        errorLabel.setBounds(110, 120,300,20);
        errorLabel.setVisible(false);
        errorLabel.setForeground(Color.red);

        // textfields
        userIDText.setBounds(110, 75, 200, 20);
        passwordText.setBounds(110, 100, 200, 20);
        userIDText.addKeyListener(this);
        passwordText.addKeyListener(this);

        // adds objects to the frame
        this.add(loginBTN);
        this.add(registerBTN);
        this.add(userLabel);
        this.add(passwordLabel);
        this.add(errorLabel);
        this.add(programTitle);
        this.add(userIDText);
        this.add(passwordText);

        this.setVisible(true); // set frame to visible
    }

    /**
     * When LOGIN button is clicked: program calls controller to check if user has entered correct USER ID and password,
     * if all login info is correct, user is then brought to HomeFrame, else user will be warned.
     * When REGISTER button is clicked: program takes user to RegisterFrame.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginBTN){
            this.setCursor(waitCursor);
            try {
                String id = userIDText.getText();
                String password = new String(passwordText.getPassword());

                if(this.uiController.getLoginUIControl().attemptLogin(id, password)){
                    this.dispose();
                    this.uiController.updateUser();
                    this.uiController.toHome();
                }
                else{
                    this.errorLabel.setVisible(true);
                }

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.setCursor(Cursor.getDefaultCursor());
        }

        if (e.getSource() == registerBTN){
            this.dispose();
            this.uiController.toRegister();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    /**
     * The "Login" button will only be enabled when the user enters text into User ID and password text fields.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        loginBTN.setEnabled(!userIDText.getText().equals("") && !new String(passwordText.getPassword()).equals(""));
    }
}