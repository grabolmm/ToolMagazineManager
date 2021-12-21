package pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ToolServiceTest {

    @Mock
    private ToolRepository toolRepository;
    private ToolService underTest;

    @BeforeEach
    void setUp() {
        underTest = new ToolService(toolRepository);
    }

    @Test
    void canGetTools() {
        //give

        //when
        underTest.getTools();

        //then
        verify(toolRepository).findAll();
    }

    @Test
    void canAddTool() {
        //given
        Tool tool = new Tool();

        //when
        underTest.addTool(tool);

        //then
        ArgumentCaptor<Tool> toolArgumentCaptor = ArgumentCaptor.forClass(Tool.class);
        verify(toolRepository).save(toolArgumentCaptor.capture());
        Tool capturedTool = toolArgumentCaptor.getValue();
        assertThat(capturedTool).isEqualTo(tool);
    }

    @Test
    void deleteTool() {
    }

    @Test
    void updateTool() {
    }

    @Test
    void buyTool() {
    }

    @Test
    void borrowTool() {
    }

    @Test
    void giveBackTool() {
    }

    @Test
    void findToolById() {
    }

    @Test
    void findToolByCompany() {
    }

    @Test
    void findToolByGroupName() {
    }

    @Test
    void findToolByDiameter() {
    }

    @Test
    void findToolByCompanyCode() {
    }
}