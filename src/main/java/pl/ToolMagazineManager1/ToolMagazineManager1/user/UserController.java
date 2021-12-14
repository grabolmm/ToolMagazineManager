package pl.ToolMagazineManager1.ToolMagazineManager1.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUsers")
    public List<User> getUsers (){
        return userService.getUsers();
    }

    @PostMapping("/addUser")
    public void addUser (@RequestBody User user){
        userService.addUser(user);
    }

    @DeleteMapping ("/deleteUser/{userId}")
    public void deleteUser (@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }

    @PutMapping ("updateUser/{userId}")
    public void updateUser (@PathVariable("userId") Long userId,
                            @RequestParam (required = false) String name,
                            @RequestParam (required = false) String surname,
                            @RequestParam (required = false) String email,
                            @RequestParam (required = false) String phone,
                            @RequestParam (required = false) String department,
                            @RequestParam (required = false) String position){
        userService.updateUser(userId, name, surname, email, phone, department, position);
    }

    @GetMapping ("/findUserById/{id}")
    public Optional<User> findUserById (@PathVariable("id") Long userId){
        return userService.findUserById(userId);
    }

    @GetMapping ("/findUserByName/{name}")
    public List<User> findUserByName (@PathVariable("name") String name){
        return userService.findUserByName(name);
    }

    @GetMapping ("/findUserBySurname/{surname}")
    public List<User> findUserBySurname (@PathVariable("surname") String surname){
        return userService.findUserBySurname(surname);
    }

    @GetMapping ("/findUserByEmail/{email}")
    public List<User> findUserByEmail (@PathVariable("email") String email){
        return userService.findUserByEmail(email);
    }

    @GetMapping ("findUserByPhone/{phone}")
    public List <User> findUserByPhone (@PathVariable("phone") String phone){
        return  userService.findUserByPhone(phone);
    }

    @GetMapping ("getUserByDepartment/{department}")
    public List<User> getUserByDepartment (@PathVariable("department") String department){
        return userService.getUserByDepartment(department);
    }

    @GetMapping ("getUserByPosition/{position}")
    public List<User> getUserByPosition (@PathVariable("position") String position){
        return userService.getUserByPosition(position);
    }

//    @GetMapping ("/getBorrowedTools/{userId}")
//    public List<Tool> getBorrowedTools(@PathVariable("userId") Long userId){
//        return userService.getBorrowedTools(userId);
//    }
}
