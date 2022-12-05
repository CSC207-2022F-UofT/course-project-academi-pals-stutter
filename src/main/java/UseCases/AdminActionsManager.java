package UseCases;

import Entities.Course;
import Entities.InterestTag;
import Entities.Student;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Use case class for Admin operations on the database. Specifically, removing students from courses.
 */
public class AdminActionsManager extends UseCase{


    /**
     * Initializer
     * @param courseDatabase The Course Database.
     * @param userDatabase The User Database.
     * @param tagDatabase The Tag Database.
     */
    public AdminActionsManager(CourseDataManager courseDatabase, UserDataManager userDatabase, TagDataManager
            tagDatabase) {
        super(courseDatabase, userDatabase, tagDatabase);
    }

    /**
     * Removes a student from all databases.
     * @param userID the user id of the student being removed
     * @return True if student is removed from all databases, false if student does not exist.
     */
    public boolean removeUser(String userID){
        System.out.println(userID);
        if(!userExist(userID)){
            return false;
        }
        try {
            Student student = (Student) this.ub.getUserByID(userID);
            removeStudentFromCourse(student);
            removeStudentFromTag(student);
            this.ub.removeUser(userID);
            System.out.println("true");
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper method for removeUser, removes user from Course Database.
     * @param student the student that is being removed.
     * @param courseDataManager Use cases of operations on course database.
     */
    public void removeStudentFromCourse(Student student) throws IOException {
        for(String c: student.getEnrolledCourseCodes()){
            Course course = this.cb.getCourse(c, "LEC");
            this.cb.removeStudent(course.getCourseCode(), course.getCourseType(), student);
            student.removeCourse(course);
        }
        if(!student.getEnrolledCourseCodes().isEmpty()){
            for(String c: student.getEnrolledCourseCodes()){
                Course course = this.cb.getCourse(c, "TUT");
                this.cb.removeStudent(course.getCourseCode(), course.getCourseType(), student);
                student.removeCourse(course);
            }
        }
    }

    /**
     * Helper method for removeUser, removes user from Tag Database.
     * @param student the student that is being removed.
     * @param tagDataManager Use cases of operations on tag database.
     */
    private void removeStudentFromTag(Student student) throws IOException{
        for(InterestTag t: student.getTags()){
            if(!t.getName().equals("")){
                this.tb.removeStudent(t, student);
            }
        }
    }

    /**
     * Method that returns true if the user is in the user database.
     * @param userID the user to check in the user database.
     * @return true if user exists in user database, false if not.
     */
    public boolean userExist(String userID){
        return this.ub.existByID(userID);
    }
}