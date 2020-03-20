package igrad.logic.parser;

import static java.util.Objects.requireNonNull;

import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.Name;

/**
 * Represents a generic course command parser
 */
public class CourseCommandParser {
    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code title} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(igrad.model.module.Title.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }
}
