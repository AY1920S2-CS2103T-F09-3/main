package igrad.model.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.model.module.Module;
import igrad.model.module.UniqueModuleList;
import javafx.collections.ObservableList;

/**
 * The Requirement class contains the data required at the requirement level.
 * A Requirement has a RequirementCode attribute, a Title attribute, a Credits attribute
 * and a list of modules.
 * Guarantees: immutable, field values are validated, non-null.
 */
public class Requirement implements ReadOnlyRequirement {

    private final RequirementCode requirementCode; // unique requirement code of the requirement
    private final Title title; // title of the requirement
    private final Credits credits; // credit information for the requirement
    private final UniqueModuleList modules = new UniqueModuleList(); // list of modules associated with requirement

    /**
     * Creates a {@code Requirement} object with given {@code requirementCode}, {@code title}
     * and {@code credits} and a default empty modules list.
     *
     * @param requirementCode RequirementCode of the requirement.
     * @param title           Title of the requirement.
     * @param credits         Credits of the requirement.
     */
    public Requirement(RequirementCode requirementCode, Title title, Credits credits) {
        requireAllNonNull(requirementCode, title, credits);

        this.requirementCode = requirementCode;
        this.title = title;
        this.credits = credits;
    }

    /**
     * Creates a {@code Requirement} object with given {@code requirementCode}, {@code title}, {@code credits} and
     * a list of {@code modules}.
     *
     * @param requirementCode RequirementCode of the requirement.
     * @param title           Title of the requirement.
     * @param credits         Credits of the requirement.
     * @param modules         List of modules belonging in the requirement.
     */
    public Requirement(RequirementCode requirementCode, Title title, Credits credits, List<Module> modules) {
        requireAllNonNull(requirementCode, title, credits, modules);

        this.requirementCode = requirementCode;
        this.title = title;
        this.credits = credits;
        setModules(modules);
    }

    /**
     * Creates a requirement by making a copy from an existing requirement {@code toBeCopied}.
     *
     * @param toBeCopied Requirement to copy from.
     */
    public Requirement(ReadOnlyRequirement toBeCopied) {
        requireNonNull(toBeCopied);

        this.requirementCode = toBeCopied.getRequirementCode();
        this.title = toBeCopied.getTitle();
        this.credits = toBeCopied.getCredits();
        resetModules(toBeCopied);
    }

    // requirement-level operations

    /**
     * Resets the existing modules of this {@code Requirement} with {@code newData}.
     */
    public void resetModules(ReadOnlyRequirement newData) {
        requireNonNull(newData);

        setModules(newData.getModuleList());
    }

    /**
     * Replaces the contents of the module list with {@code modules}.
     * The list must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
    }

    // module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the list.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);

        return modules.contains(module);
    }

    /**
     * Returns true if any modules in {@code modules} with the same identity as {@code module} exists in the list.
     */
    public boolean hasModule(List<Module> modules) {
        requireNonNull(modules);

        return this.modules.contains(modules);
    }

    /**
     * Adds a {@code module} to the list.
     * The module must not already exist in the list.
     */
    public void addModule(Module module) {
        requireNonNull(module);

        this.modules.add(module);
    }

    /**
     * Adds a {@code module} to the list.
     * The module must not already exist in the list.
     */
    public void addModules(List<Module> modules) {
        requireNonNull(modules);

        this.modules.add(modules);
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * The {@code target} module must exist in the list.
     * The module identity of {@code editedModule} must not be the same as another
     * existing module in the list.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
    }

    /**
     * Removes {@code module} from this {@code Requirement}.
     * The {@code module} must exist in the list.
     */
    public void removeModule(Module module) {
        modules.remove(module);
    }

    // util methods

    /**
     * Calculates the credits fulfilled of this requirement
     * based on its module list
     */
    private int calculateCreditsFulfilled() {

        int creditsFulfilled = 0;

        for (Module module : modules) {
            if (module.isDone()) {
                creditsFulfilled += module.getCredits().toInteger();
            }
        }

        return creditsFulfilled;
    }

    @Override
    public Title getTitle() {
        return title;
    }

    /**
     * Checks if {@code otherRequirement} has the same title as this requirement.
     */
    public boolean hasSameTitle(Requirement otherRequirement) {
        return this.title.equals(otherRequirement.title);
    }

    /**
     * Checks if {@code otherRequirement} has the same credits as this requirement.
     */
    public boolean hasSameCredits(Requirement otherRequirement) {
        return this.credits.equals(otherRequirement.credits);
    }

    @Override
    public Credits getCredits() {
        return credits;
    }

    @Override
    public RequirementCode getRequirementCode() {
        return requirementCode;
    }

    @Override
    public int getCreditsRequired() {
        return credits.getCreditsRequired();
    }

    @Override
    public int getCreditsFulfilled() {
        return credits.getCreditsFulfilled();
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public boolean isFulfilled() {
        return credits.isFulfilled();
    }

    @Override
    public String generateRequirementCode(String requirementTitle) {
        StringBuilder code = new StringBuilder();
        String[] words = requirementTitle.split(" ");

        for (String word : words) {
            code.append(word.split("")[0]);
        }

        return code.toString();
    }

    @Override
    public String toString() {
        return "Requirement: " + title + ", " + credits + " creditsRequired and "
            + getCreditsFulfilled() + " creditsFulfilled has "
            + modules.asUnmodifiableObservableList().size() + " modules";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Requirement // check properties
            && requirementCode.equals(((Requirement) other).requirementCode)
            && title.equals(((Requirement) other).title)
            && credits.equals(((Requirement) other).credits)
            && modules.equals(((Requirement) other).modules));
    }

    @Override
    public int hashCode() {
        return modules.hashCode();
    }
}
