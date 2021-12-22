package pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.User;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
        //given
        long id = 1;
        Tool tool = new Tool();
        given(toolRepository.findById(id)).willReturn(Optional.of(tool));

        //when
        underTest.deleteTool(id);

        //then
        verify(toolRepository).deleteById(id);
    }

    @Test
    void willThrownWhenDeleteToolNotFound() {
        //given
        long id = 1;
        Tool tool = new Tool();
        given(toolRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then

        assertThatThrownBy(() -> underTest.deleteTool(id)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("tool with id " + id + " does not exist");
        verify(toolRepository, never()).deleteById(any());
    }

    @Test
    void canUpdateTool() {
        //given
        long id = 1;
        GroupName groupName = GroupName.DRILL_INDEXABLE_INSERTS;
        String diameter = "";
        String company = "";
        String companyCode = "";
        int minMagazineQuantity = 1;
        Tool tool = new Tool(groupName,
                "10",
                "ceratizit",
                "12345",
                5);
        given(toolRepository.findById(id)).willReturn(Optional.of(tool));

        //when
        underTest.updateTool(id, groupName, diameter, company, companyCode);

        //then
        assertThat(tool.getGroupName()).isEqualTo(groupName);
        assertThat(tool.getDiameter()).isEqualTo(diameter);
        assertThat(tool.getCompany()).isEqualTo(company);
        assertThat(tool.getCompanyCode()).isEqualTo(companyCode);
    }

    @Test
    void canBuyTool() {
        //given
        long toolId = 1;
        int boughtQuantity = 10;
        Tool tool = new Tool();
        given(toolRepository.findById(toolId)).willReturn(Optional.of(tool));

        //when
        underTest.buyTool(toolId, boughtQuantity);

        //then
        assertThat(tool.getMagazineQuantity()).isEqualTo(10);
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