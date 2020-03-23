package igrad.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import igrad.commons.exceptions.IllegalValueException;
import igrad.logic.commands.exceptions.CommandException;
import igrad.model.CourseBook;
import igrad.model.ReadOnlyCourseBook;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.requirement.Requirement;

/**
 * An Immutable CourseBook that is serializable to JSON format.
 */
@JsonRootName(value = "coursebook")
class JsonSerializableCourseBook {

    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";
    public static final String MESSAGE_DUPLICATE_REQUIREMENT = "Requirement list contains duplicate requirement(s).";

    private final CourseInfo courseInfo;
    private final List<JsonAdaptedModule> modules = new ArrayList<>();
    private final List<JsonAdaptedRequirement> requirements = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCourseBook} with the given modules.
     */
    @JsonCreator
    public JsonSerializableCourseBook(@JsonProperty("courseInfo") CourseInfo courseInfo,
                                      @JsonProperty("modules") List<JsonAdaptedModule> modules,
                                      @JsonProperty("requirements") List<JsonAdaptedRequirement> requirements) {
        this.courseInfo = courseInfo;
        this.modules.addAll(modules);
        this.requirements.addAll(requirements);
    }

    /**
     * Converts a given {@code ReadOnlyCourseBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCourseBook}.
     */
    public JsonSerializableCourseBook(ReadOnlyCourseBook source) {
        courseInfo = source.getCourseInfo();

        modules.addAll(source.getModuleList().stream()
            .map(JsonAdaptedModule::new)
            .collect(Collectors.toList()));

        requirements.addAll(source.getRequirementList().stream()
            .map(JsonAdaptedRequirement::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this course book into the model's {@code CourseBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CourseBook toModelType() throws IllegalValueException, CommandException {
        CourseBook courseBook = new CourseBook();

        courseBook.addCourseInfo(courseInfo);

        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (courseBook.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            courseBook.addModule(module);
        }

        List<Module> moduleList = courseBook.getModuleList();
        for (JsonAdaptedRequirement jsonAdaptedRequirement : requirements) {
            Requirement requirement = jsonAdaptedRequirement.toModelType(moduleList);
            if (courseBook.hasRequirement(requirement)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_REQUIREMENT);
            }
            courseBook.addRequirement(requirement);
        }

        return courseBook;
    }

}
