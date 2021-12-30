package pl.ToolMagazineManager1.ToolMagazineManager1.tool.borrowedTool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.Tool;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.ToolRepository;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.ToolService;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.User;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.UserService;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BorrowedToolControllerTest {

    @Mock
    private BorrowedToolService borrowedToolService;
    @Mock
    private ToolService toolService;
    @Mock
    private UserService userService;

    private BorrowedToolController underTest;

    @BeforeEach
    void setUp() {
        underTest = new BorrowedToolController(borrowedToolService);

    }

    @Test
    @Disabled
    void canAddBorrowedTool() {
        //given
        Tool tool = new Tool();
        tool.setId((long)1);
        User user = new User();
        user.setId((long)1);
        BorrowedTool borrowedTool = new BorrowedTool(user, tool, 1, LocalDate.now().toString());
        long toolId = borrowedTool.getTool().getId();
        long userId = borrowedTool.getUser().getId();
//        given(toolService.findToolById(toolId)).willReturn(Optional.of(tool));
        given(userService.findUserById(userId)).willReturn(Optional.of(user));

        //when
//        assertThat(toolService.findToolById(toolId)).isEqualTo(Optional.of(tool));
//        assertThat(userService.findUserById(toolId)).isEqualTo(Optional.of(user));
        underTest.addBorrowedTool(toolId, userId, 1);

        //then
//        verify(borrowedToolService).addBorrowedTool(borrowedTool);
//        verify(toolService).borrowTool(toolId, 1);
        ArgumentCaptor<BorrowedTool> toolArgumentCaptor = ArgumentCaptor.forClass(BorrowedTool.class);
        verify(borrowedToolService).addBorrowedTool(toolArgumentCaptor.capture());
        BorrowedTool capturedTool = toolArgumentCaptor.getValue();
        assertThat(capturedTool).isEqualTo(borrowedTool);
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
        a
//        given(toolService.giveBackTool(toolId, 1)).
//        given(toolService).getMock();
//        when(toolService.)
//        given(toolService.equals(toolService);

        //when
        underTest.giveBackBorrowedTool(toolId, userId, 1);

        //then
        verify(borrowedToolService).giveBackBorrowedTool(toolId, userId, 1);

    }

    @Test
    void getBorrowedTools() {
    }

    @Test
    void getBorrowedToolsByUserId() {
    }

    @Test
    void getBorrowedToolsUsersByToolId() {
    }

    @Test
    void findBorrowedToolByToolId() {
    }

    @Test
    void findBorrowedToolUserByUserId() {
    }
}