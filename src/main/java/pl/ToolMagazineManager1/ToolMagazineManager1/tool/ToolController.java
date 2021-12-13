package pl.ToolMagazineManager1.ToolMagazineManager1.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tools")
public class ToolController {
    private ToolService toolService;

    @Autowired
    private BoughtToolService boughtToolService;

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

    @PostMapping("/buyTool/{toolId}")
    public void buyTool (@PathVariable("toolId") Long toolId,
                         @RequestParam (required = false) int magazineQuantity,
                         @RequestParam (required = false) double price,
                         @RequestParam (required = false) String invoice){
        BoughtTool boughtTool = new BoughtTool(toolId, magazineQuantity, price, invoice);
        boughtToolService.addBoughtTool(boughtTool);
        toolService.buyTool(toolId, magazineQuantity);
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