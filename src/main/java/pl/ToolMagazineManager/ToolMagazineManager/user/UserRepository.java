package pl.ToolMagazineManager.ToolMagazineManager.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT e FROM User e WHERE e.name = :name")
    List<User> findUserByName (@Param("name") String name);

    @Query("SELECT e FROM User e WHERE e.surname = :surname")
    List<User> findUserBySurname (@Param("surname") String surname);

    @Query("SELECT e FROM User e WHERE e.email = :email")
    List<User> findUserByEmail (@Param("email") String email);

    @Query("SELECT e FROM User e WHERE e.phone = :phone")
    List<User> findUserByPhone (@Param("phone") String phone);

    @Query("SELECT e FROM User e WHERE e.department = :department")
    List<User> getUserByDepartment (@Param("department") String department);

    @Query("SELECT e FROM User e WHERE e.position = :position")
    List<User> getUserByPosition (@Param("position") String position);

}
