package igrad.testutil;

import static igrad.logic.commands.CommandTestUtil.VALID_NAME_B_ARTS_PHILO;
import static igrad.logic.commands.CommandTestUtil.VALID_NAME_B_COMP_SCI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import igrad.model.course.CourseInfo;

/**
 * A utility class containing a list of {@code CourseInfo} objects to be used in tests.
 */
public class TypicalCourseInfos {
    public static final CourseInfo B_SCI_MATH = new CourseInfoBuilder()
        .withName("Bachelor of Science in Mathematics").withCap("5.0").build();

    public static final CourseInfo B_INFO_SYS = new CourseInfoBuilder()
        .withName("Bachelor of Information Systems").withCap("5.0").build();

    // Manually added - CourseInfo's details found in {@code CommandTestUtil}
    public static final CourseInfo B_ARTS_PHILO = new CourseInfoBuilder()
        .withName(VALID_NAME_B_ARTS_PHILO).withCap("5.0").build();

    public static final CourseInfo B_COMP_SCI = new CourseInfoBuilder()
        .withName(VALID_NAME_B_COMP_SCI).withCap("5.0").build();

    private TypicalCourseInfos() {
    } // prevents instantiation

    public static List<CourseInfo> getTypicalCourseInfos() {
        return new ArrayList<>(Arrays.asList(B_COMP_SCI, B_INFO_SYS, B_SCI_MATH, B_ARTS_PHILO));
    }
}
