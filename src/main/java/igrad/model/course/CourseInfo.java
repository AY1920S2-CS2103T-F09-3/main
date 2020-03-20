package igrad.model.course;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;
import java.util.Optional;

/**
 * Represents all the (additional) details a Course (there's only one of which), might have e.g, course name.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class CourseInfo {

    // Identity fields
    private final Optional<Name> name;

    // Data fields

    public CourseInfo() {
        this.name = Optional.empty();
    }

    /**
     * Every field must be present and not null.
     */
    public CourseInfo(Optional<Name> name) {
        requireAllNonNull(name);
        this.name = name;
    }

    public Optional<Name> getName() {
        return name;
    }

    /**
     * Returns true if both modules have the same identity and data fields.
     * This defines a stronger notion of equality between two modules.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CourseInfo)) {
            return false;
        }

        CourseInfo otherCourseInfo = (CourseInfo) other;
        return otherCourseInfo.getName().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName().get());
        return builder.toString();
    }

}
