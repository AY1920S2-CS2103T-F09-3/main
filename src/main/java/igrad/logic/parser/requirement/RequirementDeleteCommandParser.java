package igrad.logic.parser.requirement;

import static igrad.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static igrad.logic.commands.requirement.RequirementDeleteCommand.MESSAGE_USAGE;

import igrad.logic.commands.requirement.RequirementDeleteCommand;
import igrad.logic.parser.Parser;
import igrad.logic.parser.ParserUtil;
import igrad.logic.parser.Specifier;
import igrad.logic.parser.exceptions.ParseException;
import igrad.model.requirement.Name;

/**
 * Parses requirement input argument and creates a new RequirementAddCommand object.
 */
public class RequirementDeleteCommandParser implements Parser<RequirementDeleteCommand> {

    @Override
    public RequirementDeleteCommand parse(String userInput) throws ParseException {
        try {
            Specifier specifier = ParserUtil.parseSpecifier(userInput);
            return new RequirementDeleteCommand(new Name(specifier.getValue()));
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE), pe);
        }
    }
}
