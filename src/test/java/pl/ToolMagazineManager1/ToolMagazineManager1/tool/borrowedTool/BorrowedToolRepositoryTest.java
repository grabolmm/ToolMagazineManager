package pl.ToolMagazineManager1.ToolMagazineManager1.tool.borrowedTool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.GroupName;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.Tool;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BorrowedToolRepositoryTest {

    @Autowired
    private BorrowedToolRepository underTest;
    private Tool tool;
    private User user;
    private BorrowedTool borrowedTool;

    @BeforeEach
    void setUp() {
        tool = new Tool(GroupName.MILLING_CUTTER_SOLID_CARBIDE,
                "10",
                "ceratizit",
                "12345",
                5);

        user = new User("Mariusz",
                "Graczyk",
                "graczyk@gmail.com",
                "500600700",
                "R&D",
                "mechanical designer");

        borrowedTool = new BorrowedTool(user, tool, 5, LocalDate.now().toString());
        underTest.save(borrowedTool);
    }

    @Test
    void canGetBorrowedToolsByUserId() {
        //given
        long userId = user.getId();

        //when
        List<Tool> list = underTest.getBorrowedToolsByUserId(userId);
        boolean expected = list.contains(tool);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canGetBorrowedToolsUsersByToolId() {
        //given
        long toolId = tool.getId();

        //when
        List<User> list = underTest.getBorrowedToolsUsersByToolId(toolId);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canGetBorrowedToolsByToolIdAndUserId() {
        //given
        long toolId = tool.getId();
        long userId = user.getId();

        //when
        List<Tool> list = underTest.getBorrowedToolsByToolIdAndUserId(toolId, userId);
        boolean expected = list.contains(tool);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindBorrowedToolByToolId() {
        //given
        long toolId = tool.getId();

        //when
        Optional<Tool> expectedTool = underTest.findBorrowedToolByToolId(toolId);

        //then
        assertThat(expectedTool).isEqualTo(Optional.of(tool));
    }

    @Test
    void findBorrowedToolUserByUserId() {
        //given
        long userId = user.getId();

        //when
        Optional<User> expectedUser = underTest.findBorrowedToolUserByUserId(userId);

        //then
        assertThat(expectedUser).isEqualTo(Optional.of(user));
    }
}