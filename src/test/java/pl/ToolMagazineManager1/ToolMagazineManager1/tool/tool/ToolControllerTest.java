package pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ToolControllerTest {

    @Mock
    private ToolService toolService;
    private ToolController underTest;

    @BeforeEach
    void setUp() {
        underTest = new ToolController(toolService);
    }

    @Test
    void canGetTools() {
        //give

        //when
        underTest.getTools();

        //then
        verify(toolService).getTools();
    }

    @Test
    void canAddTool() {
        //given
        Tool tool = new Tool();

        //when
        underTest.addTool(tool);

        //then
        ArgumentCaptor<Tool> toolArgumentCaptor = ArgumentCaptor.forClass(Tool.class);
        verify(toolService).addTool(toolArgumentCaptor.capture());
        Tool capturedTool = toolArgumentCaptor.getValue();
        assertThat(capturedTool).isEqualTo(tool);
    }

    @Test
    void canDeleteTool() {
        //given
        long toolId = 1;

        //when
        underTest.deleteTool(toolId);

        //then
        verify(toolService).deleteTool(toolId);
    }

    @Test
    void canUpdateTool() {
        //given
        long toolId = 1;
        GroupName groupName = GroupName.MILLING_CUTTER_SOLID_CARBIDE;
        String diameter = "10";
        String company = "ceratizit";
        String companyCode = "12345";

        //when
        underTest.updateTool(toolId, groupName, diameter, company, companyCode);

        //then
        verify(toolService).updateTool(toolId, groupName, diameter, company, companyCode);
    }

    @Test
    void canFindToolById() {
        //given
        long toolId = 1;

        //when
        underTest.findToolById(toolId);

        //then
        verify(toolService).findToolById(toolId);
    }

    @Test
    void canFindToolByCompany() {
        //given
        String company = "ceratizit";

        //when
        underTest.findToolByCompany(company);

        //then
        toolService.findToolByCompany(company);
    }

    @Test
    void canFindToolByGroup() {
        //given
        GroupName groupName = GroupName.DRILL_INDEXABLE_INSERTS;

        //when
        underTest.findToolByGroup(groupName);

        //then
        verify(toolService).findToolByGroupName(groupName);
    }

    @Test
    void findToolByDiameter() {
        //given
        String diameter = "10";

        //when
        underTest.findToolByDiameter(diameter);

        //then
        verify(toolService).findToolByDiameter(diameter);
    }

    @Test
    void findToolByCompanyCode() {
        //given
        String companyCode = "12345";

        //when
        underTest.findToolByCompanyCode(companyCode);

        //then
        verify(toolService).findToolByCompanyCode(companyCode);
    }
}