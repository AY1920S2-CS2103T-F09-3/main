package igrad.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static igrad.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static igrad.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import igrad.testutil.CourseBookBuilder;
import igrad.testutil.Assert;
import igrad.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;

import igrad.commons.core.GuiSettings;
import igrad.model.person.NameContainsKeywordsPredicate;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new CourseBook(), new CourseBook(modelManager.getCourseBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setCourseBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setCourseBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setCourseBookFilePath_nullPath_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.setCourseBookFilePath(null));
    }

    @Test
    public void setCourseBookFilePath_validPath_setsCourseBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setCourseBookFilePath(path);
        assertEquals(path, modelManager.getCourseBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> modelManager.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInCourseBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInCourseBook_returnsTrue() {
        modelManager.addPerson(TypicalPersons.ALICE);
        assertTrue(modelManager.hasPerson(TypicalPersons.ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        Assert.assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void equals() {
        CourseBook courseBook = new CourseBookBuilder().withPerson(TypicalPersons.ALICE).withPerson(TypicalPersons.BENSON).build();
        CourseBook differentCourseBook = new CourseBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(courseBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(courseBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different courseBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCourseBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = TypicalPersons.ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(courseBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCourseBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(courseBook, differentUserPrefs)));
    }
}
