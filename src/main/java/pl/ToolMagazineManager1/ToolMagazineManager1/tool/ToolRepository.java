package pl.ToolMagazineManager1.ToolMagazineManager1.tool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.User;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    @Query("SELECT e FROM Tool e WHERE e.company LIKE :company")
    List<Tool> findToolByCompany(@Param("company") String company);

    @Query("SELECT e FROM Tool e WHERE e.groupName LIKE :groupName")
    List<Tool> findToolByGroupName(@Param("groupName") GroupName groupName);

    @Query("SELECT DISTINCT e.toolUsers FROM Tool e WHERE e.id = :toolId")
    List<User> getUsers (@Param("toolId") Long toolId);
}
