package igrad.commons.core;

/**
 * Container for generic and global user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "%1$s does not know this command, "
        + "you may key in `help` to get a list of commands!";
    public static final String MESSAGE_UNKNOWN_COURSE_COMMAND = "%1$s does not know this course command, "
        + "you might want to try:\n"
        + "course add ...\n"
        + "course edit ...\n"
        + "course delete\n";
    public static final String MESSAGE_UNKNOWN_REQUIREMENT_COMMAND = "%1$s does not know this requirement command, "
        + "you might want to try:\n"
        + "requirement add ...\n"
        + "requirement edit ...\n"
        + "requirement delete ...\n"
        + "requirement assign ...\n";
    public static final String MESSAGE_UNKNOWN_MODULE_COMMAND = "%1$s does not know this module command, "
        + "you might want to try:\n"
        + "module add ...\n"
        + "module edit ...\n"
        + "module done ...\n"
        + "module delete\n";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!\n%1$s";
    public static final String MESSAGE_COURSE_NOT_SET = "You need to set a course first! Use this command:\n"
        + "course add n/COURSE_NAME";
    public static final String MESSAGE_COURSE_ALREADY_SET = "Course has been set! Only one course can be added";
    public static final String MESSAGE_SPECIFIER_NOT_SPECIFIED = "Please provide a non-empty specifier.\n%1$s";
    public static final String MESSAGE_SPECIFIER_INVALID = "Please enter a valid specifier.\n%1$s";

}
