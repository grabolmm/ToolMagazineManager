package pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private BorrowedToolService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BorrowedToolService(borrowedToolRepository);
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
    @Disabled
    void canAddBorrowedTool() {
        //given
//        BorrowedTool borrowedTool = new BorrowedTool(new User(), new Tool(), 1, LocalDate.now().toString());

        //when
//        underTest.addBorrowedTool(borrowedTool);

        //then
//        ArgumentCaptor<BorrowedTool> borrowedToolArgumentCaptor = ArgumentCaptor.forClass(BorrowedTool.class);
//        verify(borrowedToolRepository).save(borrowedToolArgumentCaptor.capture());
//        BorrowedTool capturedBorrowedTool = borrowedToolArgumentCaptor.getValue();
//        assertThat(capturedBorrowedTool).isEqualTo(borrowedTool);
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
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(borrowedToolRepository.findBorrowedToolUserByUserId(user.getId())).willReturn(Optional.of(user));
        given(borrowedToolRepository.findBorrowedToolByToolId(tool.getId())).willReturn(Optional.of(tool));
        given(borrowedToolRepository.findBorrowedToolsByToolIdAndUserId(tool.getId(), user.getId())).willReturn(toolList);

        //when
        underTest.giveBackBorrowedTool(tool.getId(), user.getId(), giveBackQuantity);

        //then
        verify(borrowedToolRepository).deleteById(tool.getId());
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
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
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
    @Disabled
    void canFindBorrowedToolsByToolIdAndUserId() {
    }

    @Test
    @Disabled
    void canFindBorrowedToolByToolId() {
    }

    @Test
    @Disabled
    void canFindBorrowedToolUserByUserId() {
    }


}