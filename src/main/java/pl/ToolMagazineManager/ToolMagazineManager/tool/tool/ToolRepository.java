package pl.ToolMagazineManager.ToolMagazineManager.tool.tool;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    @Query("SELECT e FROM Tool e WHERE e.company LIKE :company")
    List<Tool> findToolByCompany(@Param("company") String company);

    @Query("SELECT e FROM Tool e WHERE e.groupName LIKE :groupName")
    List<Tool> findToolByGroupName(@Param("groupName") GroupName groupName);

    @Query("SELECT e FROM Tool e WHERE e.diameter LIKE :diameter")
    List<Tool> findToolByDiameter(@Param("diameter") String diameter);

    @Query("SELECT e FROM Tool e WHERE e.companyCode LIKE :companyCode")
    List<Tool> findToolByCompanyCode(@Param("companyCode") String companyCode);

}
