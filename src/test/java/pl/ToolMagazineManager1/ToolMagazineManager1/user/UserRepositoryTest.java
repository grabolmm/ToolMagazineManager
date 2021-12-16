package pl.ToolMagazineManager1.ToolMagazineManager1.user;

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

    @Test
    void canFindUserByName(){
        //given
        String name = "Mariusz";
        User user = new User(
                name,
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");
        underTest.save(user);

        //when
        List<User> list = underTest.findUserByName(name);
        boolean expected = list.isEmpty();

        //then
        assertThat(expected).isFalse();

    }

    @Test
    void canFindUserBySurname(){
        //given
        String surname = "Graczyk";

        //when
        List<User> list = underTest.findUserBySurname(surname);
        boolean expected = list.isEmpty();

        //then
        assertThat(expected).isFalse();
    }


    @Test
    void findUserByEmail() {
        //given
        String email = "graczyk@gmail.com";

        //when
        List<User> list = underTest.findUserByEmail(email);
        boolean expected = list.isEmpty();

        //then
        assertThat(expected).isFalse();
    }

    @Test
    void findUserByPhone() {
    }

    @Test
    void getUserByDepartment() {
    }

    @Test
    void getUserByPosition() {
    }
}