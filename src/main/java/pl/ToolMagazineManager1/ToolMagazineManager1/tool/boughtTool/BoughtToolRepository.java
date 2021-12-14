package pl.ToolMagazineManager1.ToolMagazineManager1.tool.boughtTool;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BoughtToolRepository extends JpaRepository<BoughtTool, Long> {

//    @Query("SELECT e.Tool FROM Tool e WHERE e.id = :toolId")
//    List<Tool> getBoughtToolByGroupName (@Param("toolId") Long toolId);
}
