package igrad.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import igrad.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path courseBookFilePath = Paths.get("data" , "coursebook.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setCourseBookFilePath(newUserPrefs.getCourseBookFilePath());
    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getCourseBookFilePath() {
        return courseBookFilePath;
    }

    public void setCourseBookFilePath(Path courseBookFilePath) {
        requireNonNull(courseBookFilePath);
        this.courseBookFilePath = courseBookFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && courseBookFilePath.equals(o.courseBookFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, courseBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + courseBookFilePath);
        return sb.toString();
    }

}
