package GUI;

import Database.CourseManager;
import Database.FirebaseCollection;
import Database.TagManager;
import Database.UserManager;
import Users.InterestTag;
import Users.Student;
import Users.User;
import useCases.LoginManager;
import useCases.TagMatchManager;
import useCases.TagSelectManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class UIController{

    private User self;
    private CourseManager cb;
    private UserManager ub;
    private LoginManager loginManager;
    private TagManager tb;

    private TagMatchManager tagMatchManager;
    private TagSelectManager tagSelectManager;

    public UIController(User self, CourseManager courseDatabase, UserManager userDatabase, TagManager tb){
        this.self = self;
        this.cb = courseDatabase;
        this.ub = userDatabase;
        this.tb = tb;


        // UseCases
        this.loginManager = new LoginManager(courseDatabase, userDatabase);
        this.tagMatchManager = new TagMatchManager(courseDatabase, userDatabase, tb);
        this.tagSelectManager = new TagSelectManager(courseDatabase, userDatabase, tb);

    }

    public boolean attemptLogin(String id, String password) throws IOException {

        return loginManager.login(id, password);

    }

    public DefaultListModel<String> getNameList(){
        return (DefaultListModel<String>) tagMatchManager.getStudentName(self);
    }

    public void setSelectedtag(String selected){
        tagMatchManager.setSelectedTag(selected);
    }

    public String getSelectedIndex(int index){
        return tagMatchManager.getStudentByIndex(index).getUser_id();
    }

    public boolean getStudentTagState(String tagName){
        return tagSelectManager.getStudentTagState((Student) self, tagName);
    }

    public void updateStudentTag(String tagName, boolean selected){
        tagSelectManager.updateStudentTag((Student) self, tagName, selected);
    }


}
