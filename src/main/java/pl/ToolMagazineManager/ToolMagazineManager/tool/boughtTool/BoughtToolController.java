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
        boughtToolService.addBoughtTool(toolId, boughtQuantity, price, invoice);
    }

    @GetMapping("/findBoughtToolByToolId/{toolId}")
    public List<Tool> findBoughtToolByToolId (@PathVariable("toolId") Long toolId){
        return boughtToolService.findBoughtToolsByToolId(toolId);
    }

    @GetMapping("/findBoughtToolByInvoice/{invoice}")
    public List<Tool> findBoughtToolByInvoice (@PathVariable("invoice") String invoice){
        return boughtToolService.findBoughtToolsByInvoice(invoice);
    }

    @GetMapping("/findBoughtToolByBoughtDate/{boughtDate}")
    public List<Tool> findBoughtToolByBoughtDate (@PathVariable("boughtDate") String boughtDate){
        return boughtToolService.findBoughtToolsByBoughtDate(boughtDate);
    }
}
