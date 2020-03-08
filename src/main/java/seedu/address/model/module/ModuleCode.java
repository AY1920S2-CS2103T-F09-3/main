package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidModuleCode(String)}
 */
public class ModuleCode {


    public static final String MESSAGE_CONSTRAINTS =
            "Module code should contain two letters at the front and four numbers at the back, with an optional letter at the end.";
    public static final String VALIDATION_REGEX = ".{2}\\d{4}.?";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param moduleCode A valid module code.
     */
    public ModuleCode( String moduleCode) {
        requireNonNull(moduleCode);
        checkArgument( isValidModuleCode(moduleCode), MESSAGE_CONSTRAINTS);
        value = moduleCode;
    }

    /**
     * Returns true if a given string is a valid module code.
     */
    public static boolean isValidModuleCode( String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleCode // instanceof handles nulls
                && value.equals(((ModuleCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
