package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoughtToolServiceTest {

    @Mock
    private BoughtToolRepository boughtToolRepository;
    @Mock
    private ToolService toolService;
    private BoughtToolService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BoughtToolService(boughtToolRepository, toolService);
    }

    @Test
    void canGetBoughtTools() {
        //given

        //when
        underTest.getBoughtTools();

        //then
        verify(boughtToolRepository).findAll();
    }

    @Test
    void canAddBoughtTool() {
        //given
        GroupName groupName = GroupName.MILLING_CUTTER_SOLID_CARBIDE;

        Tool tool = new Tool(groupName,
                "10",
                "ceratizit",
                "12345",
                5);

        long toolId = 1;
        tool.setId(toolId);
        BoughtTool boughtTool = new BoughtTool(tool, 5, 100.0, "AAA");
        given(toolService.findToolById(toolId)).willReturn(Optional.of(tool));

        //when
        underTest.addBoughtTool(toolId, boughtTool.getBoughtQuantity(), boughtTool.getPrice(), boughtTool.getInvoice());

        //then
        ArgumentCaptor<BoughtTool> boughtToolArgumentCaptor = ArgumentCaptor.forClass(BoughtTool.class);
        verify(boughtToolRepository).save(boughtToolArgumentCaptor.capture());
        BoughtTool capturedBoughtTool = boughtToolArgumentCaptor.getValue();

        assertThat(capturedBoughtTool.getTool().getId()).isEqualTo(boughtTool.getTool().getId());
        assertThat(capturedBoughtTool.getBoughtQuantity()).isEqualTo(boughtTool.getBoughtQuantity());
        assertThat(capturedBoughtTool.getPrice()).isEqualTo(boughtTool.getPrice());
        assertThat(capturedBoughtTool.getInvoice()).isEqualTo(boughtTool.getInvoice());
    }

    @Test
    void canFindBoughtToolsByToolId() {
        //given
        Tool tool = new Tool();
        long toolId = 1;
        tool.setId(toolId);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(boughtToolRepository.findBoughtToolsByToolId(toolId)).willReturn(toolList);

        //when
        underTest.findBoughtToolsByToolId(toolId);

        //then
        verify(boughtToolRepository).findBoughtToolsByToolId(toolId);
    }

    @Test
    void willThrownWhenToolWithIdDoesNotExists() {
        //given
        Tool tool = new Tool();
        long toolId = 1;
        tool.setId(toolId);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(tool);
        given(boughtToolRepository.findBoughtToolsByToolId(toolId)).willReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> underTest.findBoughtToolsByToolId(toolId)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("tool with id " + toolId + " was never bought");

    }

    @Test
    void findBoughtToolsByInvoice() {
        //given
        Tool tool = new Tool();
        String invoice = "AAABBBCCC";
        BoughtTool boughtTool = new BoughtTool(tool, 5, 5.0, invoice);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(boughtTool.getTool());
        given(boughtToolRepository.findBoughtToolsByInvoice(invoice)).willReturn(toolList);

        //when
        underTest.findBoughtToolsByInvoice(invoice);

        //then
        verify(boughtToolRepository).findBoughtToolsByInvoice(invoice);
    }

    @Test
    void willThrownWhenToolWithInvoiceDoesNotExists() {
        //given
        Tool tool = new Tool();
        String invoice = "AAABBBCCC";
        BoughtTool boughtTool = new BoughtTool(tool, 5, 5.0, invoice);
        List<Tool> toolList = new ArrayList<>();
        toolList.add(boughtTool.getTool());
        given(boughtToolRepository.findBoughtToolsByInvoice(invoice)).willReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> underTest.findBoughtToolsByInvoice(invoice)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("can not find data for invoice: " + invoice);
    }

    @Test
    void findBoughtToolsByBoughtDate() {
        //given
        Tool tool = new Tool();
        String boughtDate = LocalDate.now().toString();
        BoughtTool boughtTool = new BoughtTool(tool, 5, 5.0, "invoice");
        List<Tool> toolList = new ArrayList<>();
        toolList.add(boughtTool.getTool());
        given(boughtToolRepository.findBoughtToolsByBoughtDate(boughtDate)).willReturn(toolList);

        //when
        underTest.findBoughtToolsByBoughtDate(boughtDate);

        //then
        verify(boughtToolRepository).findBoughtToolsByBoughtDate(boughtDate);
    }

    @Test
    void willThrownWhenToolWithBoughtDateDoesNotExists() {
        //given
        Tool tool = new Tool();
        String boughtDate = LocalDate.now().toString();
        BoughtTool boughtTool = new BoughtTool(tool, 5, 5.0, "invoice");
        List<Tool> toolList = new ArrayList<>();
        toolList.add(boughtTool.getTool());
        given(boughtToolRepository.findBoughtToolsByBoughtDate(boughtDate)).willReturn(Collections.emptyList());

        //when
        //then
        assertThatThrownBy(() -> underTest.findBoughtToolsByBoughtDate(boughtDate)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("can not find data for date: " + boughtDate);
    }
}