package seedu.address.logic.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Remark;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

class RemarkCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp(){
        model = new ModelManager( getTypicalAddressBook(), new UserPrefs(  ) );
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs(  ));
    }

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Remark remark = new Remark("Hello");
        Index index = Index.fromZeroBased( 1 );
        assertCommandSuccess( new RemarkCommand(index, remark), model, RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, expectedModel );
    }
}