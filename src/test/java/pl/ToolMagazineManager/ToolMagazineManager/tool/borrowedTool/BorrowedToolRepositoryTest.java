package pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.GroupName;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    void canFindBorrowedToolsByUserId() {
        //given
        long userId = user.getId();

        //when
        List<Tool> list = underTest.findBorrowedToolsByUserId(userId);
        boolean expected = list.contains(tool);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindBorrowedToolsUsersByToolId() {
        //given
        long toolId = tool.getId();

        //when
        List<User> list = underTest.findBorrowedToolsUsersByToolId(toolId);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindBorrowedToolsByToolIdAndUserId() {
        //given
        long toolId = tool.getId();
        long userId = user.getId();

        //when
        List<BorrowedTool> list = underTest.findBorrowedToolsByToolIdAndUserId(toolId, userId);
        boolean expected = list.contains(borrowedTool);

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
    void canFindBorrowedToolUserByUserId() {
        //given
        long userId = user.getId();

        //when
        Optional<User> expectedUser = underTest.findBorrowedToolUserByUserId(userId);

        //then
        assertThat(expectedUser).isEqualTo(Optional.of(user));
    }

    @Test
    void canFindBorrowedToolsByBorrowDate() {
        //given
        String borrowDate = borrowedTool.getBorrowDate();

        //when
        List<Tool> list = underTest.findBorrowedToolsByBorrowDate(borrowDate);
        boolean expected = list.contains(borrowedTool.getTool());

        //then
        assertThat(expected).isTrue();
    }
}