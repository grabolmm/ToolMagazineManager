package pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.GroupName;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.ToolService;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;
import pl.ToolMagazineManager.ToolMagazineManager.user.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BorrowedToolServiceTest {

    @Mock
    private BorrowedToolRepository borrowedToolRepository;
    @Mock
    private ToolService toolService;
    @Mock
    private UserService userService;
    private BorrowedToolService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BorrowedToolService(borrowedToolRepository, toolService, userService);
    }

    @Test
    void canGetBorrowedTools() {
        //given

        //when
        underTest.getBorrowedTools();

        //then
        verify(borrowedToolRepository).findAll();
    }

    @Test
    void canAddBorrowedTool() {
        //given
        User user = new User(
                "Mariusz",
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");

        long userId = 1;
        user.setId(userId);

        Tool tool = new Tool(GroupName.MILLING_CUTTER_SOLID_CARBIDE,
                "10",
                "ceratizit",
                "12345",
                5);

        long toolId = 1;
        tool.setId(toolId);
        BorrowedTool borrowedTool = new BorrowedTool(user, tool, 1, LocalDate.now().toString());
        given(toolService.findToolById(toolId)).willReturn(Optional.of(tool));
        given(userService.findUserById(userId)).willReturn(Optional.of(user));

        //when
        underTest.addBorrowedTool(toolId, userId, 1);

        //then
        ArgumentCaptor<BorrowedTool> borrowedToolArgumentCaptor = ArgumentCaptor.forClass(BorrowedTool.class);
        verify(borrowedToolRepository).save(borrowedToolArgumentCaptor.capture());
        BorrowedTool capturedBorrowedTool = borrowedToolArgumentCaptor.getValue();

        assertThat(capturedBorrowedTool.getTool().getId()).isEqualTo(borrowedTool.getTool().getId());
        assertThat(capturedBorrowedTool.getUser().getId()).isEqualTo(borrowedTool.getUser().getId());
        assertThat(capturedBorrowedTool.getBorrowedQuantity()).isEqualTo(borrowedTool.getBorrowedQuantity());
    }

    @Test
    void canGiveBackBorrowedTool() {
        //given
        long toolId = 1;
        Tool tool = new Tool();
        tool.setId(toolId);

        long userId = 1;
        User user = new User();
        user.setId(userId);

        int giveBackQuantity = 1;
        BorrowedTool borrowedTool = new BorrowedTool(user, tool, 1, LocalDate.now().toString());
        List<BorrowedTool> toolList = new ArrayList<>();
        toolList.add(borrowedTool);
        given(borrowedToolRepository.findBorrowedToolUserByUserId(user.getId())).willReturn(Optional.of(user));
        given(borrowedToolRepository.findBorrowedToolByToolId(tool.getId())).willReturn(Optional.of(tool));
        given(borrowedToolRepository.findBorrowedToolsByToolIdAndUserId(tool.getId(), user.getId())).willReturn(toolList);

        //when
        underTest.giveBackBorrowedTool(tool.getId(), user.getId(), giveBackQuantity);

        //then
        verify(borrowedToolRepository).deleteById(borrowedTool.getId());
    }

    @Test
    void willThrownWhenBorrowedToolQuantityNotEnough() {
        //given
        long toolId = 1;
        Tool tool = new Tool();
        tool.setId(toolId);

        long userId = 1;
        User user = new User();
        user.setId(userId);

        int giveBackQuantity = 2;
        BorrowedTool borrowedTool = new BorrowedTool(user, tool, 1, LocalDate.now().toString());
        List<BorrowedTool> toolList = new ArrayList<>();
        toolList.add(borrowedTool);
        given(borrowedToolRepository.findBorrowedToolUserByUserId(user.getId())).willReturn(Optional.of(user));
        given(borrowedToolRepository.findBorrowedToolByToolId(tool.getId())).willReturn(Optional.of(tool));
        given(borrowedToolRepository.findBorrowedToolsByToolIdAndUserId(tool.getId(), user.getId())).willReturn(toolList);

        //when
        //then
        assertThatThrownBy(() -> underTest.giveBackBorrowedTool(tool.getId(), user.getId(), giveBackQuantity)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("user do not has enough borrowed tools to give it back");
        verify(borrowedToolRepository, never()).deleteById(any());
    }

    @Test
    void canGetBorrowedToolsByUserId() {
        //given
        long toolId = 1;
        Tool tool = new Tool();
        tool.setId(toolId);

        long userId = 1;
        User user = new User();
        user.setId(userId);

        given(borrowedToolRepository.findBorrowedToolUserByUserId(userId)).willReturn(Optional.of(user));

        //when
        underTest.findBorrowedToolsByUserId(userId);

        //then
        verify(borrowedToolRepository).findBorrowedToolsByUserId(userId);
    }

    @Test
    void canGetBorrowedToolsUsersByToolId() {
        //given
        long toolId = 1;
        Tool tool = new Tool();
        tool.setId(toolId);

        long userId = 1;
        User user = new User();
        user.setId(userId);

        given(borrowedToolRepository.findBorrowedToolByToolId(toolId)).willReturn(Optional.of(tool));

        //when
        underTest.findBorrowedToolsUsersByToolId(toolId);

        //then
        verify(borrowedToolRepository).findBorrowedToolsUsersByToolId(toolId);
    }

    @Test
    void canFindBorrowedToolsByBorrowDate() {
        //given
        long toolId = 1;
        Tool tool = new Tool();
        tool.setId(toolId);

        long userId = 1;
        User user = new User();
        user.setId(userId);

        BorrowedTool borrowedTool = new BorrowedTool(user, tool, 1, LocalDate.now().toString());
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        String borrowDate = borrowedTool.getBorrowDate();
        given(borrowedToolRepository.findBorrowedToolsByBorrowDate(borrowDate)).willReturn(toolList);

        //when
        underTest.findBorrowedToolsByBorrowDate(borrowDate);

        //then
        verify(borrowedToolRepository).findBorrowedToolsByBorrowDate(borrowDate);
    }

    @Test
    void willThrownWhenNoActionOnDate() {
        //given
        long toolId = 1;
        Tool tool = new Tool();
        tool.setId(toolId);

        long userId = 1;
        User user = new User();
        user.setId(userId);

        BorrowedTool borrowedTool = new BorrowedTool(user, tool, 1, LocalDate.now().toString());
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        String borrowDate = borrowedTool.getBorrowDate();
        given(borrowedToolRepository.findBorrowedToolsByBorrowDate(borrowDate)).willReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> underTest.findBorrowedToolsByBorrowDate(borrowDate)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("there was no actions on date: " + borrowDate);
    }

}