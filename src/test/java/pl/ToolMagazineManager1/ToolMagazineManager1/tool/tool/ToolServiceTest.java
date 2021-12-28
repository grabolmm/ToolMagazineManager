package pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    void canDeleteTool() {
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
        int expected = tool.getMagazineQuantity() + boughtQuantity;
        underTest.buyTool(toolId, boughtQuantity);

        //then
        assertThat(tool.getMagazineQuantity()).isEqualTo(expected);
    }

    @Test
    void canBorrowTool() {
        //given
        long toolId = 1;
        int borrowQuantity = 10;
        Tool tool = new Tool();
        tool.setMagazineQuantity(10);
        tool.setInUseQuantity(0);
        given(toolRepository.findById(toolId)).willReturn(Optional.of(tool));

        //when
        int expectedMagazineQuantity = tool.getMagazineQuantity() - borrowQuantity;
        int expectedInUseQuantity = tool.getInUseQuantity() + borrowQuantity;

        underTest.borrowTool(toolId, borrowQuantity);

        //then
        assertThat(tool.getMagazineQuantity()).isEqualTo(expectedMagazineQuantity);
        assertThat(tool.getInUseQuantity()).isEqualTo(expectedInUseQuantity);
    }

    @Test
    void willThrownWhenMagazineQuantityIsNotEnough(){
        //given
        long toolId = 1;
        int borrowQuantity = 10;
        Tool tool = new Tool();
        tool.setMagazineQuantity(9);
        tool.setInUseQuantity(0);
        given(toolRepository.findById(toolId)).willReturn(Optional.of(tool));

        //when
        //then
        assertThatThrownBy(() -> underTest.borrowTool(toolId, borrowQuantity)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("magazine quantity is not enough");
    }

    @Test
    void willThrownWhenMagazineQuantityIsLessThanMinMagazineQuantity(){
        //given
        long toolId = 1;
        int borrowQuantity = 10;
        Tool tool = new Tool();
        tool.setMinMagazineQuantity(10);
        tool.setMagazineQuantity(10);
        tool.setInUseQuantity(0);
        given(toolRepository.findById(toolId)).willReturn(Optional.of(tool));

        //when
        //then
        assertThatThrownBy(() -> underTest.borrowTool(toolId, borrowQuantity)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("magazine tools quantity less than min magazine quantity - please order tools");
    }

    @Test
    void canGiveBackTool() {
        //given
        long toolId = 1;
        int giveBackQuantity = 2;
        Tool tool = new Tool();
        tool.setMagazineQuantity(10);
        tool.setInUseQuantity(10);
        given(toolRepository.findById(toolId)).willReturn(Optional.of(tool));

        //when
        int expectedMagazineQuantity = tool.getMagazineQuantity() + giveBackQuantity;
        int expectedInUseQuantity = tool.getInUseQuantity() - giveBackQuantity;

        underTest.giveBackTool(toolId, giveBackQuantity);

        //then
        assertThat(tool.getMagazineQuantity()).isEqualTo(expectedMagazineQuantity);
        assertThat(tool.getInUseQuantity()).isEqualTo(expectedInUseQuantity);
    }

    @Test
    void canFindToolById() {
        //given
        long id = 1;
        Tool tool = new Tool();
        tool.setId(id);
        given(toolRepository.findById(id)).willReturn(Optional.of(tool));

        //when
        Optional<Tool> expectedTool = underTest.findToolById(id);

        //then
        assertThat(toolRepository.findById(id)).isEqualTo(expectedTool);
    }

    @Test
    void canFindToolByCompany() {
        //given
        String company = "ceratizit";
        Tool tool = new Tool();
        tool.setCompany(company);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(toolRepository.findToolByCompany(company)).willReturn(toolList);

        //when
        List<Tool> expected = underTest.findToolByCompany(company);

        //then
        assertThat(toolRepository.findToolByCompany(company)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenToolByCompanyNotFound() {
        //given
        String company = "ceratizit";
        Tool tool = new Tool();
        tool.setCompany(company);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(toolRepository.findToolByCompany(company)).willReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> underTest.findToolByCompany(company)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("tool with company name " + company + " does not exist");
    }

    @Test
    void canFindToolByGroupName() {
        //given
        GroupName groupName = GroupName.MILLING_CUTTER_SOLID_CARBIDE;
        Tool tool = new Tool();
        tool.setGroupName(groupName);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(toolRepository.findToolByGroupName(groupName)).willReturn(toolList);

        //when
        List<Tool> expected = underTest.findToolByGroupName(groupName);

        //then
        assertThat(toolRepository.findToolByGroupName(groupName)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenToolByGroupNameNotFound() {
        //given
        GroupName groupName = GroupName.MILLING_CUTTER_SOLID_CARBIDE;
        Tool tool = new Tool();
        tool.setGroupName(groupName);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(toolRepository.findToolByGroupName(groupName)).willReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> underTest.findToolByGroupName(groupName)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("tool with group name " + groupName + " does not exist");
    }

    @Test
    void canFindToolByDiameter() {
        //given
        String diameter = "10";
        Tool tool = new Tool();
        tool.setDiameter(diameter);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(toolRepository.findToolByDiameter(diameter)).willReturn(toolList);

        //when
        List<Tool> expected = underTest.findToolByDiameter(diameter);

        //then
        assertThat(toolRepository.findToolByDiameter(diameter)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenToolByDiameterNotFound() {
        //given
        String diameter = "10";
        Tool tool = new Tool();
        tool.setDiameter(diameter);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(toolRepository.findToolByDiameter(diameter)).willReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> underTest.findToolByDiameter(diameter)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("tool with diameter " + diameter + " does not exist");
    }

    @Test
    void canFindToolByCompanyCode() {
        //given
        String companyCode = "123456";
        Tool tool = new Tool();
        tool.setCompanyCode(companyCode);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(toolRepository.findToolByCompanyCode(companyCode)).willReturn(toolList);

        //when
        List<Tool> expected = underTest.findToolByCompanyCode(companyCode);

        //then
        assertThat(toolRepository.findToolByCompanyCode(companyCode)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenToolByCompanyCodeNotFound() {
        //given
        String companyCode = "123456";
        Tool tool = new Tool();
        tool.setCompanyCode(companyCode);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(toolRepository.findToolByCompanyCode(companyCode)).willReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> underTest.findToolByCompanyCode(companyCode)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("tool with company code " + companyCode + " does not exist");
    }
}