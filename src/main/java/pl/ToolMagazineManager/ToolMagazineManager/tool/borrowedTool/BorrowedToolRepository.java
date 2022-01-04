package pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;

import java.util.List;
import java.util.Optional;

public interface BorrowedToolRepository extends JpaRepository<BorrowedTool, Long> {

    @Query("SELECT e.tool FROM BorrowedTool e WHERE e.user.id = :userId")
    List<Tool> findBorrowedToolsByUserId(@Param("userId") Long userId);

    @Query("SELECT e.user FROM BorrowedTool e WHERE e.tool.id = :toolId")
    List<User> findBorrowedToolsUsersByToolId(@Param("toolId") Long toolId);

    @Query("SELECT e.tool FROM BorrowedTool e WHERE e.tool.id = :toolId AND e.user.id = :userId")
    List<Tool> findBorrowedToolsByToolIdAndUserId(@Param("toolId") Long toolId, @Param("userId") Long userId);

    @Query("SELECT e.tool FROM BorrowedTool e WHERE e.tool.id = :toolId")
    Optional<Tool> findBorrowedToolByToolId (@Param("toolId") Long toolId);

    @Query("SELECT e.user FROM BorrowedTool e WHERE e.user.id = :userId")
    Optional<User> findBorrowedToolUserByUserId (@Param("userId") Long userId);

    @Query("SELECT e.tool FROM BorrowedTool e WHERE e.borrowDate = :borrowDate")
    List<Tool> findBorrowedToolsByBorrowDate(@Param("borrowDate") String borrowDate);
}

