package pl.ToolMagazineManager1.ToolMagazineManager1.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    void canGetUserByDepartment() {
        String department = "R&D";

        //when
        List<User> list = underTest.getUserByDepartment(department);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }

    @Test
    void canGetUserByPosition() {
        String position = "mechanical designer";

        //when
        List<User> list = underTest.getUserByPosition(position);
        boolean expected = list.contains(user);

        //then
        assertThat(expected).isTrue();
    }
}