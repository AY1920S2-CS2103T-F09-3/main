package igrad.logic.commands.requirement;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.parser.CliSyntax.PREFIX_CREDITS;
import static igrad.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import igrad.logic.commands.CommandResult;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Title;
import igrad.model.requirement.Requirement;

/**
 * Modifies an existing requirement in the course book.
 */
public class RequirementEditCommand extends RequirementCommand {
    public static final String COMMAND_WORD = REQUIREMENT_COMMAND_WORD + "edit";

    public static final String MESSAGE_DETAILS = COMMAND_WORD + ": Edits the requirement. "
        + "Existing requirement will be overwritten by the input name and/or credits.\n";

    public static final String MESSAGE_USAGE = "Parameter: "
        + "[" + PREFIX_NAME + "NEW_NAME] "
        + "[" + PREFIX_CREDITS + "NEW_CREDITS]\n"
        + "Example: " + COMMAND_WORD + " Unrestrained Elves "
        + PREFIX_NAME + "Unrestricted Electives";

    public static final String MESSAGE_HELP = MESSAGE_DETAILS + MESSAGE_USAGE;

    public static final String MESSAGE_REQUIREMENT_EDIT_SUCCESS = "Edited Requirement: %1$s";
    public static final String MESSAGE_REQUIREMENT_NOT_EDITED = "Edited requirement must be provided with prefix "
        + "[" + PREFIX_NAME + "] and/or "
        + "[" + PREFIX_CREDITS + "].";
    public static final String MESSAGE_REQUIREMENT_EMPTY_PARAMETERS = "Please provide a non-empty string.";
    public static final String MESSAGE_REQUIREMENT_SAME_PARAMETERS = "Please change the name and/or the credits.";
    public static final String MESSAGE_REQUIREMENT_DUPLICATE = "This requirement already exists. "
        + "Please change to a different name or delete the requirement if you no longer need it.";

    private final Title originalTitle;

    private final Optional<Title> newTitle;

    private final Optional<Credits> newCredits;

    public RequirementEditCommand(Title originalTitle,
                                  Optional<Title> newTitle, Optional<Credits> newCredits) {
        requireAllNonNull(originalTitle, newTitle, newCredits);

        this.originalTitle = originalTitle;
        this.newTitle = newTitle;
        this.newCredits = newCredits;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Requirement> requirements = model.getRequirementList();

        Requirement requirementToEdit = requirements.stream()
            .filter(requirement -> requirement.getTitle().equals(originalTitle))
            .findFirst()
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));

        Title editedName = newTitle.orElse(requirementToEdit.getTitle());
        Credits editedCredits = newCredits.orElse(requirementToEdit.getCredits());
        Requirement editedRequirement = new Requirement(editedName, editedCredits, requirementToEdit.getModuleList());

        // If the provided name is same as before and/or if the provided credits is same as before
        if (newTitle.isPresent() && requirementToEdit.hasSameName(editedRequirement)
            || newCredits.isPresent() && requirementToEdit.hasSameCredits(editedRequirement)) {
            throw new CommandException(MESSAGE_REQUIREMENT_SAME_PARAMETERS);
        }

        // If changed name is the same as an existing name
        if (requirements.stream()
                .anyMatch(requirement -> !requirement.getTitle().equals(originalTitle)
                                             && requirement.hasSameName(editedRequirement))) {
            throw new CommandException(MESSAGE_REQUIREMENT_DUPLICATE);
        }

        model.setRequirement(requirementToEdit, editedRequirement);
        model.updateRequirementList(Model.PREDICATE_SHOW_ALL_REQUIREMENTS);

        return new CommandResult(
            String.format(MESSAGE_REQUIREMENT_EDIT_SUCCESS, editedRequirement));
    }
}
