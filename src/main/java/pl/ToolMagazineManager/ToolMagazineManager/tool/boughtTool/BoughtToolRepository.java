package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;

import java.util.List;

public interface BoughtToolRepository extends JpaRepository<BoughtTool, Long> {

    @Query("SELECT e.tool FROM BoughtTool e WHERE e.tool.id = :toolId")
    List<Tool> findBoughtToolsByToolId (@Param("toolId") Long toolId);

    @Query("SELECT e.tool FROM BoughtTool e WHERE e.invoice = :invoice")
    List<Tool> findBoughtToolsByInvoice (@Param("invoice") String invoice);

    @Query("SELECT e.tool FROM BoughtTool e WHERE e.boughtDate = :boughtDate")
    List<Tool> findBoughtToolsByBoughtDate (@Param("boughtDate") String boughtDate);
}
