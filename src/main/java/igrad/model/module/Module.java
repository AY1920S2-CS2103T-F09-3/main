package igrad.model.module;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import igrad.model.tag.Tag;

/**
 * Represents a Module in the course book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Module {

    // Identity fields
    private final Title title;
    private final ModuleCode moduleCode;
    private final Credits credits;

    // Data fields
    private final Memo memo;
    private final Semester semester;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Module( Title title, ModuleCode moduleCode, Credits credits, Memo memo, Semester semester, Set<Tag> tags) {
        requireAllNonNull( title, moduleCode, credits, memo, tags );
        this.title = title;
        this.moduleCode = moduleCode;
        this.credits = credits;
        this.memo = memo;
        this.semester = semester;
        this.tags.addAll( tags );
    }

    public Title getTitle() {
        return title;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Credits getCredits() {
        return credits;
    }

    public Memo getMemo() {
        return memo;
    }

    public Semester getSemester(){ return semester; }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both modules of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two modules.
     */
    public boolean isSameModule(Module otherModule) {
        if (otherModule == this) {
            return true;
        }

        return otherModule != null
                && otherModule.getTitle().equals(getTitle())
                && otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getCredits().equals(getCredits())
                && otherModule.getMemo().equals(getMemo())
                && otherModule.getSemester().equals(getSemester())
                && otherModule.getTags().equals(getTags());
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

        if (!(other instanceof Module)) {
            return false;
        }

        Module otherModule = (Module) other;
        return otherModule.getTitle().equals(getTitle())
                && otherModule.getModuleCode().equals(getModuleCode())
                && otherModule.getCredits().equals(getCredits())
                && otherModule.getMemo().equals(getMemo())
                && otherModule.getSemester().equals(getSemester())
                && otherModule.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(title, moduleCode, credits, memo, semester, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append( getTitle())
                .append(" Code: ")
                .append(getModuleCode())
                .append(" Credits: ")
                .append(getCredits())
                .append(" Memo: ")
                .append(getMemo())
                .append(" Semester: ")
                .append(getSemester())
                .append("Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

}
