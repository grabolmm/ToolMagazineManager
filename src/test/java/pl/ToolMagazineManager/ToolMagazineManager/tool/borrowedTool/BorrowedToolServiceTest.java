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
    void canAddBorrowedTool() {
        //given
        BorrowedTool borrowedTool = new BorrowedTool(new User(), new Tool(), 1, LocalDate.now().toString());

        //when
        underTest.addBorrowedTool(borrowedTool);

        //then
        ArgumentCaptor<BorrowedTool> borrowedToolArgumentCaptor = ArgumentCaptor.forClass(BorrowedTool.class);
        verify(borrowedToolRepository).save(borrowedToolArgumentCaptor.capture());
        BorrowedTool capturedBorrowedTool = borrowedToolArgumentCaptor.getValue();
        assertThat(capturedBorrowedTool).isEqualTo(borrowedTool);
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
        given(borrowedToolRepository.getBorrowedToolsByToolIdAndUserId(tool.getId(), user.getId())).willReturn(toolList);

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
        given(borrowedToolRepository.getBorrowedToolsByToolIdAndUserId(tool.getId(), user.getId())).willReturn(toolList);

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
        underTest.getBorrowedToolsByUserId(userId);

        //then
        verify(borrowedToolRepository).getBorrowedToolsByUserId(userId);
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
        underTest.getBorrowedToolsUsersByToolId(toolId);

        //then
        verify(borrowedToolRepository).getBorrowedToolsUsersByToolId(toolId);
    }

    @Test
    @Disabled
    void getBorrowedToolsByToolIdAndUserId() {
    }

    @Test
    @Disabled
    void findBorrowedToolByToolId() {
    }

    @Test
    @Disabled
    void findBorrowedToolUserByUserId() {
    }
}