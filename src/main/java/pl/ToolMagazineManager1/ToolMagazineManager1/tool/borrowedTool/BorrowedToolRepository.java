package pl.ToolMagazineManager1.ToolMagazineManager1.tool.borrowedTool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.Tool;

import java.util.List;

public interface BorrowedToolRepository extends JpaRepository<BorrowedTool, Long> {

    @Query("SELECT e.tool FROM BorrowedTool e WHERE User e WHERE e.id = :userId")
    List<Tool> getBorrowedToolsByUserId (@Param("userId") Long userId);
}

