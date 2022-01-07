package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    void canBuyTool() {
        //given
        Tool tool = new Tool();
        long toolId = 1;
        tool.setId(toolId);
        BoughtTool boughtTool = new BoughtTool(tool,
                5,
                5.0,
                "AAA");

        //when
        underTest.buyTool(boughtTool.getTool().getId(),
                boughtTool.getBoughtQuantity(),
                boughtTool.getPrice(),
                boughtTool.getInvoice());

        //then
        verify(boughtToolService).addBoughtTool(boughtTool.getTool().getId(),
                boughtTool.getBoughtQuantity(),
                boughtTool.getPrice(),
                boughtTool.getInvoice());
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