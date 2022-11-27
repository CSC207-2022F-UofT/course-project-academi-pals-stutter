package UIController;

import UseCases.CourseDataManager;
import UseCases.TagDataManager;
import UseCases.UserDataManager;
import Entities.User;
import GUI.*;
import UseCases.RegisterUIManager;
import UseCases.TagMatchUIManager;
import UseCases.TagSelectUIManager;


public class UIController{

    //TODO: make UIController as parent class. Create seperate UIControl for each Frame, e.g. LoginUIControl, etc.

    private User self;
    private CourseDataManager courseManager;
    private UserDataManager userManager;
    private RegisterUIManager registerManager;
    private TagDataManager tagManager;

    private TagMatchUIManager tagMatchManager;
    private TagSelectUIManager tagSelectManager;

    protected LoginUIControl loginUIControl;
    protected RegisterUIControl registerUIControl;

    public LoginUIControl getLoginUIControl() {
        return loginUIControl;
    }

    public RegisterUIControl getRegisterUIControl() {
        return registerUIControl;
    }

    public TagMatchUIControl getTagMatchUIControl() {
        return tagMatchUIControl;
    }

    public TagSelectUIControl getTagSelectUIControl() {
        return tagSelectUIControl;
    }

    protected TagMatchUIControl tagMatchUIControl;
    protected TagSelectUIControl tagSelectUIControl;

    public UIController(User self, CourseDataManager courseManager, UserDataManager userManager, TagDataManager tagManager){
        this.self = self;
        this.courseManager = courseManager;
        this.userManager = userManager;
        this.tagManager = tagManager;


        // UseCases


        this.tagMatchManager = new TagMatchUIManager(courseManager, userManager, tagManager);
        this.tagSelectManager = new TagSelectUIManager(courseManager, userManager, tagManager);

        this.loginUIControl = new LoginUIControl(courseManager, userManager);
        this.registerUIControl = new RegisterUIControl(courseManager, userManager);
        this.tagMatchUIControl = new TagMatchUIControl(self, courseManager, userManager, tagManager);
        this.tagSelectUIControl = new TagSelectUIControl(self, courseManager, userManager, tagManager);


    }

    public void updateUser(){
        this.self = this.loginUIControl.getUser();
    }

    public void unloadUser() {
        this.self = null;
    }


    public void toLogin(){
        LoginFrame loginFrame = new LoginFrame(this);
        this.unloadUser();
    }

    public void toHome(){
        HomeFrame HomeFrame = new HomeFrame(this);
    }

    public void toMatch(){
        MatchFrame matchFrame = new MatchFrame(this);
    }

    public void toRegister(){
        RegisterFrame registerFrame = new RegisterFrame(this);
    }

    public void toTagMatch(){
        TagMatchFrame tagMatchFrame = new TagMatchFrame(this);
    }

    public void toTagSelect(){
        TagSelectFrame tagSelectFrame = new TagSelectFrame(this);
    }

}
