package seedu.address.model.module;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's address in the address book.
 */
public class Memo {

    public static final String MESSAGE_CONSTRAINTS = "Memos can take any values, and is optional";

    public final String value;

    /**
     * Constructs an {@code Memo}.
     *
     * @param memo A valid memo.
     */
    public Memo( String memo) {
//        requireNonNull(memo);
        value = memo;
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
                || (other instanceof Memo // instanceof handles nulls
                && value.equals(((Memo) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}