package igrad.model;

import static igrad.commons.util.CollectionUtil.requireAllNonNull;
import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import igrad.commons.core.GuiSettings;
import igrad.commons.core.LogsCenter;
import igrad.model.avatar.Avatar;
import igrad.model.course.Cap;
import igrad.model.course.CourseInfo;
import igrad.model.module.Module;
import igrad.model.module.ModuleCode;
import igrad.model.requirement.Credits;
import igrad.model.requirement.Requirement;
import igrad.model.requirement.RequirementCode;
import igrad.model.requirement.Title;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

/**
 * Represents the in-memory model of the course book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final CourseBook courseBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Module> filteredModules;
    private final FilteredList<Requirement> requirements;

    /**
     * Initializes a ModelManager with the given courseBook and userPrefs.
     */
    public ModelManager(ReadOnlyCourseBook courseBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(courseBook, userPrefs);

        logger.fine("Initializing with course book: " + courseBook + " and user prefs " + userPrefs);

        // Retrieving all course book data (modules, course info, requirements, from storage)
        this.courseBook = new CourseBook(courseBook);
        this.userPrefs = new UserPrefs(userPrefs);

        this.requirements = new FilteredList<>(this.courseBook.getRequirementList());
        this.filteredModules = new FilteredList<>(this.courseBook.getModuleList());
        //this.courseInfo = this.courseBook.getCourseInfo();
    }

    public ModelManager() {
        this(new CourseBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCourseBookFilePath() {
        return userPrefs.getCourseBookFilePath();
    }

    @Override
    public void setCourseBookFilePath(Path courseBookFilePath) {
        requireNonNull(courseBookFilePath);
        userPrefs.setCourseBookFilePath(courseBookFilePath);
    }

    @Override
    public Path getBackupCourseBookFilePath() {
        return userPrefs.getBackupCourseBookFilePath();
    }

    @Override
    public Avatar getAvatar() {
        return userPrefs.getAvatar();
    }

    @Override
    public void setAvatar(Avatar avatar) {
        requireNonNull(avatar);
        userPrefs.setAvatar(avatar);
    }

    @Override
    public boolean isSampleAvatar() {
        return this.getUserPrefs().getAvatar().equals(Avatar.getSampleAvatar());
    }

    //=========== CourseBook ================================================================================

    @Override
    public void resetCourseBook(ReadOnlyCourseBook courseBook) {
        this.setCourseBook(new CourseBook());
    }

    @Override
    public ReadOnlyCourseBook getCourseBook() {
        return courseBook;
    }

    @Override
    public void setCourseBook(ReadOnlyCourseBook courseBook) {
        this.courseBook.resetData(courseBook);
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);

        return courseBook.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        courseBook.removeModule(target);
        // courseBook.removeModuleFromRequirement(target);
    }

    @Override
    public CourseInfo getCourseInfo() {
        return courseBook.getCourseInfo();
    }

    @Override
    public void setCourseInfo(CourseInfo editedCourseInfo) {
        courseBook.setCourseInfo(editedCourseInfo);
    }

    @Override
    public boolean isCourseNameSet() {
        return courseBook.getCourseInfo().getName().isPresent();
    }

    @Override
    public Cap computeCap() {
        return CourseInfo.computeCap(courseBook.getModuleList());
    }

    @Override
    public void addCourseInfo(CourseInfo courseInfo) {
        courseBook.addCourseInfo(courseInfo);
    }

    @Override
    public void addModule(Module module) {
        courseBook.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        courseBook.setModule(target, editedModule);
    }

    @Override
    public boolean hasRequirement(Requirement requirement) {
        requireNonNull(requirement);
        return courseBook.hasRequirement(requirement);
    }

    @Override
    public Optional<Requirement> getRequirement(RequirementCode requirementCode) {
        // TODO: clean-up logic, and make an equivalent method in course book
        return requirements.stream()
            .filter(requirement -> requirement.getRequirementCode().equals(requirementCode))
            .findFirst();
    }

    @Override
    public List<Requirement> getRequirementsWithModule(Module module) {
        // TODO: clean-up logic, and make an equivalent method in course book
        return requirements.stream()
            .filter(requirement -> requirement.hasModule(module))
            .collect(Collectors.toList());
    }

    @Override
    public Optional<Module> getModuleByModuleCode(ModuleCode moduleCode) {
        return filteredModules.stream()
            .filter(module -> module.getModuleCode().equals(moduleCode))
            .findFirst();
    }

    @Override
    public List<Module> getModulesByModuleCode(List<ModuleCode> moduleCodes) {
        return filteredModules.stream()
            .filter(requirement -> moduleCodes.stream()
                .anyMatch(moduleCode -> moduleCode.equals(requirement.getModuleCode())))
            .collect(Collectors.toList());
    }

    @Override
    public void addRequirement(Requirement requirement) {
        courseBook.addRequirement(requirement);
        updateRequirementList(PREDICATE_SHOW_ALL_REQUIREMENTS);
    }

    @Override
    public void setRequirement(Requirement target, Requirement editedRequirement) {
        requireAllNonNull(target, editedRequirement);

        courseBook.setRequirement(target, editedRequirement);
    }

    @Override
    public void deleteRequirement(Requirement requirement) {
        courseBook.removeRequirement(requirement);
    }

    //========================================================================================================

    @Override
    public int getTotalCreditsRequired() {

        return requirements
            .stream()
            .mapToInt(requirement -> requirement.getCreditsRequired())
            .sum();

    }

    @Override
    public int getTotalCreditsFulfilled() {
        int totalCreditsFulfilled = 0;
        int totalCreditsRequired = getTotalCreditsRequired();

        for (Requirement requirement : requirements) {
            int creditsFulfilled = filteredModules
                .stream()
                .filter(module -> requirement.getModuleList().contains(module) && module.isDone())
                .mapToInt(module -> module.getCredits().toInteger())
                .sum();

            totalCreditsFulfilled += creditsFulfilled;
        }

        if (totalCreditsFulfilled > totalCreditsRequired) {
            totalCreditsFulfilled = totalCreditsRequired;
        }

        return totalCreditsFulfilled;

    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedCourseBook}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public List<Module> getSortedModuleList(Comparator<Module> comparator) {

        ObservableList<Module> tempList = getFilteredModuleList();
        List<Module> sortedList = new ArrayList<>(tempList);
        sortedList.sort(comparator);

        return sortedList;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Requirement List Accessors =============================================================

    @Override
    public ObservableList<Requirement> getRequirementList() {
        return requirements;
    }

    @Override
    public void updateRequirementList(Predicate<Requirement> predicate) {
        requireNonNull(predicate);
        requirements.setPredicate(predicate);
    }

    @Override
    public void recalculateRequirementList() {

        int[] requirementCredits = new int[requirements.size()];

        for (Module module : filteredModules) {
            int requirementIndex = 0;
            for (Requirement requirement : requirements) {
                ObservableList<Module> requirementModules = requirement.getModuleList();
                if (requirementModules.contains(module)) {
                    requirementCredits[requirementIndex] += module.getCredits().toInteger();
                }
                requirementIndex++;
            }
        }

        for (int i = 0; i < requirementCredits.length; i++) {
            // Compute credits fulfilled based on modules in the module list
            Requirement requirement = requirements.get(i);

            // TODO: Improve design of this part, can move logic to CourseBook itself maybe hmm

            // Copy all other requirement fields over
            Title title = requirement.getTitle();
            List<Module> modules = requirement.getModuleList();
            RequirementCode requirementCode = requirement.getRequirementCode();
            Credits credits = new Credits(requirement.getCreditsRequired(), requirementCredits[i]);

            Requirement updatedRequirement = new Requirement(requirementCode, title, credits, modules);
            setRequirement(requirement, updatedRequirement);
        }

        this.updateRequirementList(PREDICATE_SHOW_ALL_REQUIREMENTS);

    }

    @Override
    public Cap computeEstimatedCap(Cap capToAchieve, int semsLeft) {
        int totalSems;

        Optional<Cap> current = courseBook.getCourseInfo().getCap();

        if (current.isEmpty()) {
            totalSems = semsLeft;
        } else {
            totalSems = semsLeft + 1;
        }

        Cap currentCap = courseBook.getCourseInfo().getCap().orElse(new Cap("0"));
        double capWanted = capToAchieve.getValue();
        double capNow = currentCap.getValue();

        double estimatedCapEachSem = ((capWanted * totalSems) - capNow) / semsLeft;
        Cap capToAchieveEachSem = new Cap(estimatedCapEachSem + "");

        return capToAchieveEachSem;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return courseBook.equals(other.courseBook)
            && userPrefs.equals(other.userPrefs)
            && filteredModules.equals(other.filteredModules);
    }

}
