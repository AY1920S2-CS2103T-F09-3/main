package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's address in the address book.
 */
public class Description {

    public static final String MESSAGE_CONSTRAINTS = "Descriptions can take any values, and is optional";

    public final String value;

    /**
     * Constructs an {@code Description}.
     *
     * @param description A valid description.
     */
    public Description(String description) {
//        requireNonNull(description);
        value = description;
    }

//    /**
//     * Returns true if a given string is a valid email.
//     */
//    public static boolean isValidAddress(String test) {
//        return test.matches(VALIDATION_REGEX);
//    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Description // instanceof handles nulls
                && value.equals(((Description) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
