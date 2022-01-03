package pl.ToolMagazineManager.ToolMagazineManager.tool.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ToolRepositoryTest {

    @Autowired
    private ToolRepository underTest;
    private Tool tool;


    @BeforeEach
    void setUp() {
        GroupName groupName = GroupName.MILLING_CUTTER_SOLID_CARBIDE;

        tool = new Tool(groupName,
                "10",
                "ceratizit",
                "12345",
                5);
        underTest.save(tool);
    }

    @Test
    void canFindToolByCompany() {
        //given
        String company = "ceratizit";

        //when
        List<Tool> toolList = underTest.findToolByCompany(company);
        boolean expected = toolList.contains(tool);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindToolByGroupName() {
        //given
        GroupName groupName = GroupName.MILLING_CUTTER_SOLID_CARBIDE;

        //when
        List<Tool> toolList = underTest.findToolByGroupName(groupName);
        boolean expected = toolList.contains(tool);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindToolByDiameter() {
        //given
        String diameter = "10";

        //when
        List<Tool> toolList = underTest.findToolByDiameter(diameter);
        boolean expected = toolList.contains(tool);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindToolByCompanyCode() {
        //given
        String companyCode = "12345";

        //when
        List<Tool> toolList = underTest.findToolByCompanyCode(companyCode);
        boolean expected = toolList.contains(tool);

        //then
        assertThat(expected).isTrue();
    }
}