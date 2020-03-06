package igrad.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static igrad.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import igrad.model.ReadOnlyCourseBook;
import igrad.testutil.TypicalPersons;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import igrad.commons.exceptions.DataConversionException;
import igrad.model.CourseBook;

public class JsonCourseBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCourseBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCourseBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCourseBook(null));
    }

    private java.util.Optional<ReadOnlyCourseBook> readCourseBook(String filePath) throws Exception {
        return new JsonCourseBookStorage(Paths.get(filePath)).readCourseBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCourseBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCourseBook("notJsonFormatCourseBook.json"));
    }

    @Test
    public void readCourseBook_invalidCourseBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCourseBook("invalidPersonCourseBook.json"));
    }

    @Test
    public void readCourseBook_invalidAndValidPersonCourseBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCourseBook("invalidAndValidPersonCourseBook.json"));
    }

    @Test
    public void readAndSaveCourseBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCourseBook.json");
        CourseBook original = TypicalPersons.getTypicalCourseBook();
        JsonCourseBookStorage jsonCourseBookStorage = new JsonCourseBookStorage(filePath);

        // Save in new file and read back
        jsonCourseBookStorage.saveCourseBook(original, filePath);
        ReadOnlyCourseBook readBack = jsonCourseBookStorage.readCourseBook(filePath).get();
        assertEquals(original, new CourseBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(TypicalPersons.HOON);
        original.removePerson(TypicalPersons.ALICE);
        jsonCourseBookStorage.saveCourseBook(original, filePath);
        readBack = jsonCourseBookStorage.readCourseBook(filePath).get();
        assertEquals(original, new CourseBook(readBack));

        // Save and read without specifying file path
        original.addPerson(TypicalPersons.IDA);
        jsonCourseBookStorage.saveCourseBook(original); // file path not specified
        readBack = jsonCourseBookStorage.readCourseBook().get(); // file path not specified
        assertEquals(original, new CourseBook(readBack));

    }

    @Test
    public void saveCourseBook_nullCourseBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCourseBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code courseBook} at the specified {@code filePath}.
     */
    private void saveCourseBook(ReadOnlyCourseBook courseBook, String filePath) {
        try {
            new JsonCourseBookStorage(Paths.get(filePath))
                    .saveCourseBook(courseBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCourseBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCourseBook(new CourseBook(), null));
    }
}
