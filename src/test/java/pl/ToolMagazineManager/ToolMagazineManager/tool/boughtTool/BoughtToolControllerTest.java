package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.ToolService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BoughtToolControllerTest {

    @Mock
    private BoughtToolService boughtToolService;
    @Mock
    private ToolService toolService;
    private BoughtToolController underTest;

    @BeforeEach
    void setUp() {
        underTest = new BoughtToolController(boughtToolService);
    }

    @Test
    void canGetBoughtTools() {
        //given

        //when
        underTest.getBoughtTools();

        //then
        verify(boughtToolService).getBoughtTools();
    }

    @Test
    @Disabled
    void canBuyTool() {
        //given
        Tool tool = new Tool();
        BoughtTool boughtTool = new BoughtTool(tool,
                5,
                5.0,
                "AAA",
                "BBB");
        given(toolService.findToolById(tool.getId())).willReturn(Optional.of(tool));

        //when
        underTest.buyTool(tool.getId(), 5, 5.0, "AAA");

        //then
        verify(boughtToolService).addBoughtTool(boughtTool);
    }

    @Test
    void canFindBoughtToolByToolId() {
        //given
        long toolId = 1;

        //when
        underTest.findBoughtToolByToolId(toolId);

        //then
        verify(boughtToolService).findBoughtToolsByToolId(toolId);
    }

    @Test
    void canFindBoughtToolByInvoice() {
        //given
        String invoice = "AAABBBCCC";

        //when
        underTest.findBoughtToolByInvoice(invoice);

        //then
        verify(boughtToolService).findBoughtToolsByInvoice(invoice);
    }

    @Test
    void canFindBoughtToolByBoughtDate() {
        //given
        String boughtDate = LocalDate.now().toString();

        //when
        underTest.findBoughtToolByBoughtDate(boughtDate);

        //then
        verify(boughtToolService).findBoughtToolsByBoughtDate(boughtDate);
    }
}