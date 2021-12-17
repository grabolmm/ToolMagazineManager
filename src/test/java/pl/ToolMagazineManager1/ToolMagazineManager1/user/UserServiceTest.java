package pl.ToolMagazineManager1.ToolMagazineManager1.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    void willThrownWhenDeleteUserNotFound() {
        //given
        long id = 10;
        User user = new User();
        user.setId(id);
        given(userRepository.findById(id)).willReturn(Optional.empty());

        //when
        //then

        assertThatThrownBy(() -> underTest.deleteUser(id)).isInstanceOf(IllegalStateException.class).hasMessageContaining("user with id " + id + " does not exist");
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
        User user = new User();
        user.setName(name);
        Object given = given(userRepository.findUserByName(name)).equals(user);

        //when
        underTest.findUserByName(name);

        //then
        assertThat(userRepository.findUserByName(name)).isEqualTo(given);

    }

    @Test
    void findUserBySurname() {
    }

    @Test
    void findUserByEmail() {
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