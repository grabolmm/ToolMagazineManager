package pl.ToolMagazineManager.ToolMagazineManager.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith (MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userRepository);
    }

    @Test
    void canGetUsers() {
        //when
        underTest.getUsers();
        //then
        verify(userRepository).findAll();
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
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    void canDeleteUser() {
        //given
        long id = 10;
        User user = new User();
        user.setId(id);
        given(userRepository.findById(id)).willReturn(Optional.of(user));

        //when
        underTest.deleteUser(id);

        //then
        verify(userRepository).deleteById(id);
    }

    @Test
    void willThrownWhenUserToDeleteNotFound() {
        //given
        long id = 10;
        User user = new User();
        user.setId(id);
        given(userRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then

        assertThatThrownBy(() -> underTest.deleteUser(id)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("user with id " + id + " does not exist");
        verify(userRepository, never()).deleteById(any());
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
        User user = new User(
                "Mariusz",
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");
        given(userRepository.findById(id)).willReturn(Optional.of(user));

        //when
        underTest.updateUser(id, name, surname, email, phone, department, position);

        //then
        assertThat(user.getName()).isEqualTo(name);
        assertThat(user.getSurname()).isEqualTo(surname);
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPhone()).isEqualTo(phone);
        assertThat(user.getDepartment()).isEqualTo(department);
        assertThat(user.getPosition()).isEqualTo(position);
    }

    @Test
    void canFindUserById() {
        //given
        long id = 1;
        User user = new User();
        user.setId(id);
        given(userRepository.findById(id)).willReturn(Optional.of(user));

        //when
        Optional<User> expectedUser = underTest.findUserById(id);

        //then
        assertThat(userRepository.findById(id)).isEqualTo(expectedUser);
    }

    @Test
    void canFindUserByName() {
        //given
        String name = "Maciej";
        User user = new User(
                name,
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByName(name)).willReturn(userList);

        //when
        List<User> expected = underTest.findUserByName(name);

        //then
        assertThat(userRepository.findUserByName(name)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenUserByNameNotFound() {
        //given
        String name = "Maciej";
        User user = new User(
                name,
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");
        List<User> userListContainsUser = new ArrayList<>();
        userListContainsUser.add(user);
        given(userRepository.findUserByName(name)).willReturn(Collections.emptyList());

        //when
        //then

        assertThatThrownBy(() -> underTest.findUserByName(name)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("user with name " + name + " does not exist");

    }

    @Test
    void canFindUserBySurname() {
        //given
        String surname = "Graczyk";
        User user = new User(
                "Maciej",
                surname,
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserBySurname(surname)).willReturn(userList);

        //when
        List<User> expected = underTest.findUserBySurname(surname);

        //then
        assertThat(userRepository.findUserBySurname(surname)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenUserBySurnameNotFound() {
        //given
        String surname = "Graczyk";
        User user = new User(
                "Maciej",
                surname,
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserBySurname(surname)).willReturn(Collections.emptyList());

        //when
        //then

        assertThatThrownBy(() -> underTest.findUserBySurname(surname)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("user with surname " + surname + " does not exist");

    }

    @Test
    void canFindUserByEmail() {
        //given
        String email = "graczyk@gmail.com";
        User user = new User(
                "Maciej",
                "Graczyk",
                email,
                "503502501",
                "R&D",
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByEmail(email)).willReturn(userList);

        //when
        List<User> expected = underTest.findUserByEmail(email);

        //then
        assertThat(userRepository.findUserByEmail(email)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenUserByEmailNotFound() {
        //given
        String email = "graczyk@gmail.com";
        User user = new User(
                "Maciej",
                "Graczyk",
                email,
                "503502501",
                "R&D",
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByEmail(email)).willReturn(Collections.emptyList());

        //when
        //then

        assertThatThrownBy(() -> underTest.findUserByEmail(email)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("user with email " + email + " does not exist");

    }

    @Test
    void canFindUserByPhone() {
        //given
        String phone = "503502501";
        User user = new User(
                "Maciej",
                "Graczyk",
                "graczyk@gmail.com",
                phone,
                "R&D",
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByPhone(phone)).willReturn(userList);

        //when
        List<User> expected = underTest.findUserByPhone(phone);

        //then
        assertThat(userRepository.findUserByPhone(phone)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenUserByPhoneNotFound() {
        //given
        String phone = "503502501";
        User user = new User(
                "Maciej",
                "Graczyk",
                "graczyk@gmail.com",
                phone,
                "R&D",
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByPhone(phone)).willReturn(Collections.emptyList());

        //when
        //then

        assertThatThrownBy(() -> underTest.findUserByPhone(phone)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("user with phone " + phone + " does not exist");

    }

    @Test
    void canFindUserByDepartment() {
        //given
        String department = "R&D";
        User user = new User(
                "Maciej",
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                department,
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByDepartment(department)).willReturn(userList);

        //when
        List<User> expected = underTest.findUserByDepartment(department);

        //then
        assertThat(userRepository.findUserByDepartment(department)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenUserByDepartmentNotFound() {
        //given
        String department = "R&D";
        User user = new User(
                "Maciej",
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                department,
                "mechanical designer");
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByDepartment(department)).willReturn(Collections.emptyList());

        //when
        //then

        assertThatThrownBy(() -> underTest.findUserByDepartment(department)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("there is no user from " + department + " department");

    }

    @Test
    void canFindUserByPosition() {
        //given
        String position = "mechanical designer";
        User user = new User(
                "Maciej",
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                position);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByPosition(position)).willReturn(userList);

        //when
        List<User> expected = underTest.findUserByPosition(position);

        //then
        assertThat(userRepository.findUserByPosition(position)).isEqualTo(expected);
    }

    @Test
    void willThrownWhenUserByPositionNotFound() {
        //given
        String position = "mechanical designer";
        User user = new User(
                "Maciej",
                "Graczyk",
                "graczyk@gmail.com",
                "503502501",
                "R&D",
                position);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        given(userRepository.findUserByPosition(position)).willReturn(Collections.emptyList());

        //when
        //then

        assertThatThrownBy(() -> underTest.findUserByPosition(position)).isInstanceOf(IllegalStateException.class).
                hasMessageContaining("there is no user on " + position + " position");

    }
}