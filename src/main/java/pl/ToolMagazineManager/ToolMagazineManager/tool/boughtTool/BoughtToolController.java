package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.ToolService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/boughtTools")
public class BoughtToolController {

    private final BoughtToolService boughtToolService;

    @Autowired
    private ToolService toolService;

    @Autowired
    public BoughtToolController(BoughtToolService boughtToolService) {
        this.boughtToolService = boughtToolService;
    }

    @GetMapping("/getBoughtTools")
    public List<BoughtTool> getBoughtTools(){
        return boughtToolService.getBoughtTools();
    }

    @PostMapping("/buyTool/{toolId}")
    public void buyTool (@PathVariable("toolId") Long toolId,
                         @RequestParam (required = true) int boughtQuantity,
                         @RequestParam (required = true) double price,
                         @RequestParam (required = true) String invoice){
        Tool tool = toolService.findToolById(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist"));
        String boughtDate = LocalDate.now().toString();
        BoughtTool boughtTool = new BoughtTool(tool, boughtQuantity, price, invoice, boughtDate);
        boughtToolService.addBoughtTool(boughtTool);
        toolService.buyTool(toolId, boughtQuantity);
    }
}
