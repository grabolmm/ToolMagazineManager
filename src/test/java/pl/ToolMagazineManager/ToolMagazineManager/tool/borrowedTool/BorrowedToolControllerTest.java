package pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.internal.matchers.NotNull;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.ToolService;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;
import pl.ToolMagazineManager.ToolMagazineManager.user.UserService;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowedToolControllerTest {

    @Mock
    private BorrowedToolService borrowedToolService;

    private BorrowedToolController underTest;

    @BeforeEach
    void setUp() {
        underTest = new BorrowedToolController(borrowedToolService);
    }

    @Test
    void canGetBorrowedTools() {
        //given

        //when
        underTest.getBorrowedTools();

        //then
        verify(borrowedToolService).getBorrowedTools();
    }

    @Test
    void canAddBorrowedTool() {
        //given
        long toolId = 1;
        long userId = 1;
        Tool tool = new Tool();
        tool.setId(toolId);
        User user = new User();
        user.setId(userId);

        //when
        underTest.addBorrowedTool(toolId, userId, 1);

        //then
        verify(borrowedToolService).addBorrowedTool(toolId, userId, 1);
    }

    @Test
    void canGiveBackBorrowedTool() {
        //given
        Tool tool = new Tool();
        tool.setId((long)1);
        User user = new User();
        user.setId((long)1);
        BorrowedTool borrowedTool = new BorrowedTool(user, tool, 1, LocalDate.now().toString());
        long toolId = borrowedTool.getTool().getId();
        long userId = borrowedTool.getUser().getId();

        //when
        underTest.giveBackBorrowedTool(toolId, userId, 1);

        //then
        verify(borrowedToolService).giveBackBorrowedTool(toolId, userId, 1);
    }

    @Test
    void canFindBorrowedToolsByUserId() {
        //given
        long userId = 1;

        //when
        underTest.findBorrowedToolsByUserId(userId);

        //then
        verify(borrowedToolService).findBorrowedToolsByUserId(userId);
    }

    @Test
    void canFindBorrowedToolsUsersByToolId() {
        //given
        long toolId = 1;

        //when
        underTest.findBorrowedToolsUsersByToolId(toolId);

        //then
        verify(borrowedToolService).findBorrowedToolsUsersByToolId(toolId);
    }

    @Test
    void canFindBorrowedToolsByToolIdAndUserId() {
        //given
        long toolId = 1;
        long userId = 1;

        //when
        underTest.findBorrowedToolsByToolIdAndUserId(toolId, userId);

        //then
        verify(borrowedToolService).findBorrowedToolsByToolIdAndUserId(toolId, userId);
    }

    @Test
    void canFindBorrowedToolByToolId() {
        //given
        long toolId = 1;

        //when
        underTest.findBorrowedToolByToolId(toolId);

        //then
        verify(borrowedToolService).findBorrowedToolByToolId(toolId);
    }

    @Test
    void canFindBorrowedToolUserByUserId() {
        //given
        long userId = 1;

        //when
        underTest.findBorrowedToolUserByUserId(userId);

        //then
        verify(borrowedToolService).findBorrowedToolUserByUserId(userId);
    }

    @Test
    void canFindBorrowedToolsByBorrowDate() {
        //given
        String borrowDate = LocalDate.now().toString();

        //when
        underTest.findBorrowedToolsByBorrowDate(borrowDate);

        //then
        verify(borrowedToolService).findBorrowedToolsByBorrowDate(borrowDate);
    }


}