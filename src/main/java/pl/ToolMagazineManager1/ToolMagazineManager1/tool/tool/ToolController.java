package pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.boughtTool.BoughtTool;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.boughtTool.BoughtToolService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tools")
public class ToolController {
    private ToolService toolService;

    @Autowired
    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping("/getTools")
    public List<Tool> getTools (){
        return toolService.getTools();
    }

    @PostMapping("/addTool")
    public void addTool (@RequestBody Tool tool){
        toolService.addTool(tool);
    }

    @DeleteMapping("deleteTool/{toolId}")
    public void deleteTool (@PathVariable("toolId") Long toolId){
        toolService.deleteTool(toolId);
    }

    @PutMapping("/updateTool/{toolId}")
    public void updateTool (@PathVariable("toolId") Long toolId,
                            @RequestParam (required = false) GroupName groupName ,
                            @RequestParam (required = false) String diameter,
                            @RequestParam (required = false) String company,
                            @RequestParam (required = false) String companyCode){
        toolService.updateTool(toolId, groupName, diameter, company, companyCode);
    }

    @GetMapping("/findToolById/{toolId}")
    public Optional<Tool> findToolById (@PathVariable("toolId") Long toolId){
        return toolService.findToolById(toolId);
    }

    @GetMapping("/findToolByCompany/{company}")
    public List<Tool> findToolByCompany (@PathVariable("company") String company) {
        return toolService.findToolByCompany(company);
    }

    @GetMapping("/findToolByGroupName/{groupName}")
    public List<Tool> findToolByGroup (@PathVariable("groupName") GroupName groupName){
        return toolService.findToolByGroupName(groupName);
    }

    @GetMapping("/findToolByDiameter/{diameter}")
    public List<Tool> findToolByDiameter (@PathVariable("diameter") String diameter){
        return toolService.findToolByDiameter(diameter);
    }
//
//    @PutMapping("/borrowTool/{toolId}/{userId}")
//    public void borrowTool (@PathVariable("toolId") Long toolId,
//                            @PathVariable("userId") Long userId,
//                            @RequestParam Integer borrowQuantity){
//        toolService.borrowTool(toolId, borrowQuantity, userId);
//    }
//
//    @PutMapping("/damageUsedTools/{toolId}")
//    public void damageUsedTools (@PathVariable("toolId") Long id, @RequestParam Integer damageUsedQuantity){
//        toolService.damageUsedTool(id, damageUsedQuantity);
//    }
//
//    @GetMapping("/getUser/{toolId}")
//    public List<User> getUsers (@PathVariable("toolId") Long toolId){
//        return toolService.getUsers(toolId);
//    }

}
