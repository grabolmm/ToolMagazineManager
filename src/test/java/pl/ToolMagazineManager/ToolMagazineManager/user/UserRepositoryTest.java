package pl.ToolMagazineManager.ToolMagazineManager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User(
                "Mariusz",
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");
        underTest.save(user);
    }

    @Test
    void canFindUserByName(){
        //given
        String name = "Mariusz";

        //when
        List<User> list = underTest.findUserByName(name);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();

    }

    @Test
    void canFindUserBySurname(){
        //given
        String surname = "Graczyk";

        //when
        List<User> list = underTest.findUserBySurname(surname);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }


    @Test
    void canFindUserByEmail() {
        //given
        String email = "graczyk@gmail.com";

        //when
        List<User> list = underTest.findUserByEmail(email);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindUserByPhone() {
        //given
        String phone = "503502501";

        //when
        List<User> list = underTest.findUserByPhone(phone);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindUserByDepartment() {
        String department = "R&D";

        //when
        List<User> list = underTest.findUserByDepartment(department);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canFindUserByPosition() {
        String position = "mechanical designer";

        //when
        List<User> list = underTest.findUserByPosition(position);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }
}