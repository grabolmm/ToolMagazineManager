package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool.BorrowedTool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.GroupName;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoughtToolRepositoryTest {

    @Autowired
    private BoughtToolRepository underTest;
    private Tool tool;
    private BoughtTool boughtTool;

    @BeforeEach
    void setUp() {
        tool = new Tool(GroupName.MILLING_CUTTER_SOLID_CARBIDE,
                "10",
                "ceratizit",
                "12345",
                5);

        boughtTool = new BoughtTool(tool, 5, 5.0, "AAABBB");
        boughtTool.setBoughtDate(LocalDate.now().toString());
        underTest.save(boughtTool);
    }

    @Test
    void canFindBoughtToolsByToolId() {
        //given
        long toolId = tool.getId();

        //when
        List<Tool> list = underTest.findBoughtToolsByToolId(toolId);
        boolean expected = list.contains(tool);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void findBoughtToolsByInvoice() {
        //given
        String invoice = boughtTool.getInvoice();

        //when
        List<Tool> list = underTest.findBoughtToolsByInvoice(invoice);
        boolean expected = list.contains(boughtTool.getTool());

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void findBoughtToolsByBoughtDate() {
        //given
        String boughtDate = boughtTool.getBoughtDate();

        //when
        List<Tool> list = underTest.findBoughtToolsByBoughtDate(boughtDate);
        boolean expected = list.contains(boughtTool.getTool());

        //then
        assertThat(expected).isTrue();
    }
}