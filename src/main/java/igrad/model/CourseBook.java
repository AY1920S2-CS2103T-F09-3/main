package igrad.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.model.course.Course;
import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import javafx.collections.ObservableList;

/**
 * Wraps all data at the course-book level.
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class CourseBook implements ReadOnlyCourseBook {

    private final UniqueModuleList modules;
    private Course course;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
        course = null;
    }

    public CourseBook() {}

    /**
     * Creates an CourseBook using the Persons in the {@code toBeCopied}.
     */
    public CourseBook(ReadOnlyCourseBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    // list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Resets the existing data of this {@code CourseBook} with {@code newData}.
     */
    public void resetData(ReadOnlyCourseBook newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    // module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the course book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds the given course (only one course can exist(ever be created) in the system).
     */
    public void addCourse(Course c) {
        course = c;
    }

    /**
     * Adds a module to the course book.
     * The module must not already exist in the course book.
     */
    public void addModule(Module m) {
        modules.add(m);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the course book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the course book.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code key} from this {@code CourseBook}.
     * {@code key} must exist in the course book.
     */
    public void removePerson(Module key) {
        modules.remove(key);
    }

    // util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CourseBook // instanceof handles nulls
                && modules.equals(((CourseBook) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
