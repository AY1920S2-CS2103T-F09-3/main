package igrad.model.requirement;

import igrad.model.module.Module;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a requirement.
 */
public interface ReadOnlyRequirement {

    /**
     * Returns the name of the requirement.
     */
    Title getTitle();

    /**
     * Returns the credits of the requirement.
     */
    Credits getCredits();

    /**
     * Returns the requirement code of the requirement
     */
    RequirementCode getRequirementCode();

    /**
     * Returns the credits required for the requirement.
     */
    String getCreditsRequired();

    /**
     * Returns the credits fulfilled for the requirement.
     */
    String getCreditsFulfilled();

    /**
     * Returns an unmodifiable view of the modules in this requirement.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns a boolean value to indicate whether the requirement is already fulfilled.
     * Returns true if requirement is fulfilled (credits are fulfilled), else return false.
     */
    boolean isFulfilled();

    /**
     * Returns a requirement code generated from requirement title
     */
    String generateRequirementCode(String requirementTitle);
}
