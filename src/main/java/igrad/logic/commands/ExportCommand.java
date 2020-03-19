package igrad.logic.commands;

import java.io.IOException;
import igrad.csvwriter.CsvWriter;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.Model;
import igrad.model.module.sorters.SortBySemester;

/**
 * Format full help instructions for every command for display.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports data to a CSV file.\n"
        + "Example: " + COMMAND_WORD;

    public static final String SHOWING_EXPORT_MESSAGE = "I've exported your data to a CSV file."
        + " You can find it in the same folder as this app's executable!";
    public static final String EXPORT_ERROR_MESSAGE = "Unable to export data to CSV file."
        + " Please ensure that you do not have the file <study_plan.csv> open and "
        + "each module is tagged to a semester.";

    @Override
    public CommandResult execute(Model model) throws CommandException {

        try {
            CsvWriter csvWriter = new CsvWriter(model.getSortedModuleList(new SortBySemester()));
            csvWriter.write();
        } catch (NullPointerException | IOException e) {
            throw new CommandException(EXPORT_ERROR_MESSAGE);
        }

        return new CommandResult(SHOWING_EXPORT_MESSAGE);
    }
}
