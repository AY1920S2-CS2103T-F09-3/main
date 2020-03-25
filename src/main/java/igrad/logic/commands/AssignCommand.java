package igrad.logic.commands;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static igrad.logic.commands.requirement.RequirementCommand.MESSAGE_MODULES_ALREADY_EXIST_IN_REQUIREMENT;
import static igrad.logic.commands.requirement.RequirementCommand.MESSAGE_REQUIREMENT_ALREADY_FULFILLED;
import static igrad.logic.commands.requirement.RequirementCommand.MESSAGE_REQUIREMENT_NON_EXISTENT;
import static igrad.logic.commands.requirement.RequirementCommand.MESSAGE_REQUIREMENT_POTENTIALLY_FULFILLED;
import static java.util.Objects.requireNonNull;

import java.util.List;

import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.Module;
import igrad.model.requirement.Name;
import igrad.model.requirement.Requirement;

/**
 * Assigns modules under a particular requirement.
 */
public class AssignCommand extends Command {
    public static final String COMMAND_WORD = "assign";

    private Name requirementName;
    private List<Module> modules;

    public AssignCommand(Name requirementName, List<Module> modules) {
        requireAllNonNull(requirementName, modules);

        this.requirementName = requirementName;
        this.modules = modules;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Retrieve the requirement in question that we want to assign modules under..

        // First check if the requirement exists in the course book
        Requirement requirementToAssign = model.getRequirementByName(requirementName)
            .orElseThrow(() -> new CommandException(MESSAGE_REQUIREMENT_NON_EXISTENT));

        Requirement editedRequirement = new Requirement(requirementToAssign);

        // First check, if all modules are existent modules in the course book (they should all be)
        if (!model.hasAllModules(modules)) {
            throw new CommandException(MESSAGE_MODULES_ALREADY_EXIST_IN_REQUIREMENT);
        }

        // Now check, if any modules specified are existent in the requirement (they should not)
        if (!editedRequirement.hasModule(modules)) {
            throw new CommandException(MESSAGE_MODULES_ALREADY_EXIST_IN_REQUIREMENT);
        }

        // Try to assign the specified modules to it
        if (editedRequirement.isFulfilled()) {
            // If requirement is already full, don't allow to add
            throw new CommandException(MESSAGE_REQUIREMENT_ALREADY_FULFILLED);
        } else if (editedRequirement.isFulfilled(modules)) {
            // If requirement would be potentially full, don't allow to add
            throw new CommandException(MESSAGE_REQUIREMENT_POTENTIALLY_FULFILLED);
        }

        // Finally if everything alright, we can assign the specified modules under this requirement

        requirementToAssign.addModules(modules);

        model.setRequirement(requirementToAssign, editedRequirement);
        return null;
    }
}
