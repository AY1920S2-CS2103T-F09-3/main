package igrad.logic.parser;

import static igrad.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import igrad.commons.core.Messages;
import igrad.logic.commands.CourseAddCommand;
import igrad.logic.commands.ModuleAddCommand;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.course.CourseInfo;
import igrad.model.course.Name;

/**
 * Parses input arguments and creates a new CourseAddCommand object.
 */
public class CourseAddCommandParser implements Parser<CourseAddCommand> {

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses the given {@code String} of arguments in the context of the CourseAddCommand
     * and returns an CourseAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public CourseAddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ModuleAddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseModuleName(argMultimap.getValue(PREFIX_NAME).get());

        CourseInfo courseInfo = new CourseInfo(name);

        return new CourseAddCommand(courseInfo);
    }
}
