package igrad.logic.parser;

import static igrad.commons.core.Messages.MESSAGE_COURSE_NOT_SET;
import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static igrad.logic.parser.CliSyntax.FLAG_AUTO;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import igrad.logic.commands.Command;
import igrad.logic.commands.course.CourseAddCommand;
import igrad.logic.commands.course.CourseDeleteCommand;
import igrad.logic.commands.course.CourseEditCommand;
import igrad.logic.commands.ExitCommand;
import igrad.logic.commands.ExportCommand;
import igrad.logic.commands.HelpCommand;
import igrad.logic.commands.ModuleAddCommand;
import igrad.logic.commands.ModuleDeleteCommand;
import igrad.logic.commands.ModuleEditCommand;
import igrad.logic.commands.SelectAvatarCommand;
import igrad.logic.commands.UndoCommand;
import igrad.logic.commands.requirement.RequirementAddCommand;
import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.logic.commands.requirement.RequirementEditCommand;
import igrad.logic.parser.course.CourseAddCommandParser;
import igrad.logic.parser.course.CourseEditCommandParser;
import igrad.logic.parser.exceptions.ParseException;
import igrad.logic.parser.module.AddAutoCommandParser;
import igrad.logic.parser.module.ModuleAddCommandParser;
import igrad.logic.parser.module.ModuleDeleteCommandParser;
import igrad.logic.parser.module.ModuleEditCommandParser;
import igrad.logic.parser.requirement.RequirementAddCommandParser;
import igrad.logic.parser.requirement.RequirementDeleteCommandParser;
import igrad.logic.parser.requirement.RequirementEditCommandParser;
import igrad.services.exceptions.ServiceException;

/**
 * Parses user input.
 */
public class CourseBookParser {
    /**
     * Used for initial separation of command words and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile(
        "(?<commandWord>[a-z]+(\\s[a-z]{3,})?)(?<arguments>.*)");

    /**
     * Parses avatar name entered by user into {@code SelectAvatarCommand} for execution.
     *
     * @param avatarName full user input string (consisting the avatarName)
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectAvatarCommand parseAvatarName(String avatarName) throws ParseException {
        SelectAvatarCommandParser selectAvatarCommandParser = new SelectAvatarCommandParser();
        SelectAvatarCommand selectAvatarCommand = selectAvatarCommandParser.parse(avatarName);

        return selectAvatarCommand;

    }

    /**
     * Parses avatar name entered by user into {@code SelectAvatarCommand} for execution.
     *
     * @param userInput full user input string
     * @return the {@code CourseAddCommand} command
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAddCommand parseSetCourseName(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String argumentsWithFlags = matcher.group("arguments");
        final String arguments = ArgumentTokenizer.removeFlags(argumentsWithFlags);

        if (commandWord.equals(CourseAddCommand.COMMAND_WORD)) {
            return new CourseAddCommandParser().parse(arguments);
        } else {
            throw new ParseException(MESSAGE_COURSE_NOT_SET);
        }
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException, IOException, ServiceException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String argumentsWithFlags = matcher.group("arguments");
        final String arguments = ArgumentTokenizer.removeFlags(argumentsWithFlags);

        switch (commandWord) {

        case RequirementAddCommand.COMMAND_WORD:
            return new RequirementAddCommandParser().parse(arguments);

        case RequirementEditCommand.COMMAND_WORD:
            return new RequirementEditCommandParser().parse(arguments);

        case RequirementDeleteCommand.COMMAND_WORD:
            return new RequirementDeleteCommandParser().parse(arguments);

        case ModuleAddCommand.COMMAND_WORD:

            if (ArgumentTokenizer.isFlagPresent(argumentsWithFlags, FLAG_AUTO.getFlag())) {
                return new AddAutoCommandParser().parse(arguments);
            } else {
                return new ModuleAddCommandParser().parse(arguments);
            }

        case ModuleEditCommand.COMMAND_WORD:
            return new ModuleEditCommandParser().parse(arguments);

        case ModuleDeleteCommand.COMMAND_WORD:
            return new ModuleDeleteCommandParser().parse(arguments);

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ExportCommand.COMMAND_WORD:
            return new ExportCommand();

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        case CourseAddCommand.COMMAND_WORD:
            return new CourseAddCommandParser().parse(arguments);

        case CourseDeleteCommand.COMMAND_WORD:
            // course delete has no arguments, hence no parse(argument) method needed
            return new CourseDeleteCommand();
        case CourseEditCommand.COMMAND_WORD:
            /*
             * TODO: (Teri) Here's the starting point of your implementation. There's nothing much to do here
             *  since I've already done it for you. Now, its time to get your hands dirty! First dig into
             *  the CourseEditCommandParser.java class
             */
            return new CourseEditCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
