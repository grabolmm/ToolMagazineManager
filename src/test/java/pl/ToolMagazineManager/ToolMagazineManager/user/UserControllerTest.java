package pl.ToolMagazineManager.ToolMagazineManager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    private UserController underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserController(userService);
    }

    @Test
    void canGetUsers() {
        //given

        //when
        underTest.getUsers();
        //then
        verify(userService).getUsers();
    }

    @Test
    void canAddUser() {
        //given
        User user = new User(
                "Mariusz",
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");
        //when
        underTest.addUser(user);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).addUser(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canDeleteUser() {
        //given
        long id = 10;

        //when
        underTest.deleteUser(id);

        //then
        verify(userService).deleteUser(id);
    }

    @Test
    void canUpdateUser() {
        //given
        long id = 1;
        String name = "Marcin";
        String surname = "";
        String email = "";
        String phone = "";
        String department = "";
        String position = "";
        //when
        underTest.updateUser(id, name, surname, email, phone, department, position);
        //then
        verify(userService).updateUser(id, name, surname, email, phone, department, position);
    }

    @Test
    void canFindUserById() {
        //given
        long id = 1;

        //when
        underTest.findUserById(id);
        //then
        verify(userService).findUserById(id);
    }

    @Test
    void canFindUserByName() {
        //given
        String name = "Mariusz";
        //when
        underTest.findUserByName(name);
        //then
        verify(userService).findUserByName(name);
    }

    @Test
    void canFindUserBySurname() {
        //given
        String surname = "Graczyk";
        //when
        underTest.findUserBySurname(surname);
        //then
        verify(userService).findUserBySurname(surname);
    }

    @Test
    void canFindUserByEmail() {
        //given
        String email = "graczyk@gmail.com";
        //when
        underTest.findUserByEmail(email);
        //then
        verify(userService).findUserByEmail(email);
    }

    @Test
    void canFindUserByPhone() {
        //given
        String phone = "503502501";
        //when
        underTest.findUserByPhone(phone);
        //then
        verify(userService).findUserByPhone(phone);
    }

    @Test
    void canGetUserByDepartment() {
        //given
        String department = "R&D";
        //when
        underTest.getUserByDepartment(department);
        //then
        verify(userService).getUserByDepartment(department);
    }

    @Test
    void canGetUserByPosition() {
        //given
        String position = "mechanical designer";
        //when
        underTest.getUserByPosition(position);
        //then
        verify(userService).getUserByPosition(position);
    }
}