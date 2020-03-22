package igrad.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import igrad.commons.exceptions.IllegalValueException;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Name;
import igrad.model.requirement.Requirement;

/**
 * Jackson-friendly version of {@link Requirement}.
 */
class JsonAdaptedRequirement {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Requirement's %s field is missing!";

    private final String name;
    private final String credits;
    private final List<String> moduleCodes = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedRequirement} with the given module details.
     */
    @JsonCreator
    public JsonAdaptedRequirement(@JsonProperty("name") String name,
                                  @JsonProperty("credits") String credits,
                                  @JsonProperty("modules") List<String> moduleCodes) {
        this.name = name;
        this.credits = credits;
        if (moduleCodes != null) {
            this.moduleCodes.addAll(moduleCodes);
        }
    }

    /**
     * Converts a given {@code Requirement} into this class for Jackson use.
     */
    public JsonAdaptedRequirement(Requirement source) {
        name = source.getName().toString();
        credits = source.getCreditsRequired();
        moduleCodes.addAll(source.getModuleList().stream()
            .map(module -> module.getModuleCode().toString())
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted requirement object into the model's {@code Requirement} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted requirement.
     */
    public Requirement toModelType(List<Module> moduleList) throws IllegalValueException {
        final List<Module> modelModules = new ArrayList<>();

        modelModules.addAll(moduleList.stream() // for all modules
            .filter(module -> moduleCodes.stream() // find a module which has the same moduleCode as one in moduleCodes
                .anyMatch(moduleCode -> module.hasModuleCodeOf(new ModuleCode(moduleCode))))
            .collect(Collectors.toList()));

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        if (credits == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Credits.class.getSimpleName()));
        }

        if (!Credits.isValidCredits(credits)) {
            throw new IllegalValueException(Credits.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        final Credits modelCredits;

        int creditsFulfilled = 0;
        for (Module module : modelModules) {
            if (module.isDone()) {
                creditsFulfilled += module.getCredits().toInteger();
            }
        }

        modelCredits = new Credits(credits, String.valueOf(creditsFulfilled));

        return new Requirement(modelName, modelCredits, modelModules);
    }

}
