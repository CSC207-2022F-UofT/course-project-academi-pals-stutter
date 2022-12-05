package UIController;

import Entities.User;
import Entities.Student;
import UseCases.CourseDataManager;
import UseCases.CourseMatchManager;
import UseCases.TagDataManager;
import UseCases.UserDataManager;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Implements CourseMatchUIControl for CourseMatchFrame
 */
public class CourseMatchUIControl {
    private CourseMatchManager courseMatchManager;

    public String self;
    private ArrayList<Student> matches = new ArrayList();
    public CourseMatchUIControl(String userID, CourseDataManager courseDataManager, UserDataManager userDataManager, TagDataManager tagDataManager){
        this.self = userID;
        this.courseMatchManager = new CourseMatchManager(courseDataManager, userDataManager, tagDataManager);
    }

    /**
     * Finds student matches
     * @param min_numCommon minimum number of common sessions
     * @return a list of matched students
     * @throws IOException fails to find matching students
     */
    public ArrayList<Student> getMatches(int min_numCommon) throws IOException {
        this.matches = new ArrayList();
        Student stu = (Student) courseMatchManager.getUserByID(this.self);
        this.matches = this.courseMatchManager.getTopSameSessionStudents(stu, min_numCommon);
        return this.matches;
    }

    /**
     * Get select student ID by index
     * @param index which user id we want to get
     * @return user id of target index
     */
    public String getSelectedUserID(int index){
        return matches.get(index).getUserID();
    }

    /**
     * Find list of users by filtering labels
     * @param label labels we want to have commons with
     * @return a list of users that share common labels
     */
    private ArrayList<Student> filterByLabel(String label){
        return courseMatchManager.filterByLabel(this.matches, label);
    }

    /**
     * @return whether there is atleast a match or not
     */
    private boolean noMatches(){
        return matches.size() == 0;
    }

    /**
     * Match students by labels
     * @param label labels that users should have common with
     * @return a list of users that share the same labels
     */
    public ArrayList<Student> getLabeledMatches(String label){

        if(!noMatches()){
            return filterByLabel(label);
        }else{
            return new ArrayList<>();
        }
    }
}
