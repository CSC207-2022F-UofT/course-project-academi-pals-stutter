package UIController;

import UseCases.CourseDataManager;
import UseCases.LoginManager;
import UseCases.TagDataManager;
import UseCases.UserDataManager;
import Entities.User;

import java.io.IOException;

/**
 * Implements LoginUIControl for LoginFrame
 */
public class LoginUIControl{
    private LoginManager loginManager;
    public String self;

    /**
     * Constructs LoginUIControl
     * @param courseDatabase an instance of CourseDataManager
     * @param userDatabase an instance of UserDataManager
     */
    public LoginUIControl(String userID, CourseDataManager courseDataManager, UserDataManager userDataManager, TagDataManager tagDataManager){
        this.loginManager = new LoginManager(courseDataManager, userDataManager, tagDataManager);
    }

    /**
     * Try to log in with user id and password
     * @param id user id entered by user
     * @param password password entered by user
     * @return whether user successfully logged in
     * @throws IOException fails to log in
     */
    public boolean attemptLogin(String id, String password) throws IOException {

        boolean loggedIn =  loginManager.login(id, password);
        if(loggedIn){
            loadUser(loginManager.getActiveUser());
        }

        return loggedIn;
    }

    /**
     * set UIController's self to user
     * @param user an instance of Student
     */
    private void loadUser(User user){
        this.self = user.getUserID();
    }

    /**
     * @return current user
     */
    public String getUserID() throws IOException {
        return self;
    }
}
