package pl.ToolMagazineManager1.ToolMagazineManager1.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.Tool;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void addUser (User user){
        userRepository.save(user);
    }

    public void deleteUser (Long userId){
        User user = userRepository.findById(userId).orElseThrow(() ->new IllegalStateException (
                "user with id " + userId + " does not exist"));
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser (Long userId, String name, String surname, String email,
                            String phone, String department, String position){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException (
                "user with id " + userId + " does not exist"));
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPhone(phone);
        user.setDepartment(department);
        user.setPosition(position);
    }

    public Optional<User> findUserById(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException (
                "user with id " + userId + " does not exist"));
        return userRepository.findById(userId);
    }

    public List<User> findUserByName (String name){
        if (userRepository.findUserByName(name).isEmpty() == false){
            return userRepository.findUserByName(name);
        } else throw new IllegalStateException ("user with name " + name + " does not exist");
    }

    public List<User> findUserBySurname (String surname){
        if (userRepository.findUserBySurname(surname).isEmpty() == false){
            return userRepository.findUserBySurname(surname);
        } else throw new IllegalStateException ("user with surname " + surname + " does not exist");
    }

    public List<User> findUserByEmail (String email){
        if (userRepository.findUserByEmail(email).isEmpty() == false){
            return userRepository.findUserByEmail(email);
        } else throw new IllegalStateException("user with email " + email + " does not exist");
    }

    public List <User> findUserByPhone (String phone){
        if (userRepository.findUserByPhone(phone).isEmpty() == false){
            return userRepository.findUserByPhone(phone);
        } else throw new IllegalStateException("user with phone " + phone + " does not exist");
    }

    public List<User> getUserByDepartment (String department){
        if (userRepository.getUserByDepartment(department).isEmpty() == false){
            return userRepository.getUserByDepartment(department);
        } else throw new IllegalStateException("there is no user from " + department + " department");
    }

    public List<User> getUserByPosition (String position){
        if (userRepository.getUserByPosition(position).isEmpty() == false){
            return userRepository.getUserByPosition(position);
        } else throw new IllegalStateException("there is no user on " + position + " position");
    }

    public List<Tool> getBorrowedTools (Long userId){
        if (userRepository.getBorrowedTools(userId).isEmpty() == false){
            return userRepository.getBorrowedTools(userId);
        } else throw new IllegalStateException ("user with id " + userId + " does not has borrowed tools");
    }
}
