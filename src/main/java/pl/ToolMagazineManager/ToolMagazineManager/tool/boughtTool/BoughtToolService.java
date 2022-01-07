package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.ToolService;

import java.time.LocalDate;
import java.util.List;

@Service
public class BoughtToolService {

    private final BoughtToolRepository boughtToolRepository;
    private ToolService toolService;

    @Autowired
    public BoughtToolService(BoughtToolRepository boughtToolRepository, ToolService toolService) {
        this.boughtToolRepository = boughtToolRepository;
        this.toolService = toolService;
    }

    public List<BoughtTool> getBoughtTools(){
        return boughtToolRepository.findAll();
    }

    public void addBoughtTool(Long toolId, int boughtQuantity, double price, String invoice){
        Tool tool = toolService.findToolById(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist"));
        BoughtTool boughtTool = new BoughtTool(tool, boughtQuantity, price, invoice);
        boughtToolRepository.save(boughtTool);
        toolService.buyTool(toolId, boughtQuantity);

    }

    public List<Tool> findBoughtToolsByToolId (Long toolId){
        List<Tool> boughtToolList = boughtToolRepository.findBoughtToolsByToolId(toolId);
        if (boughtToolList.isEmpty()){
            throw new IllegalStateException("tool with id " + toolId + " was never bought");
        } else return boughtToolList;
    }

    public List<Tool> findBoughtToolsByInvoice (String invoice){
        List<Tool> boughtToolList = boughtToolRepository.findBoughtToolsByInvoice(invoice);
        if (boughtToolList.isEmpty()){
            throw new IllegalStateException("can not find data for invoice: " + invoice);
        } else return boughtToolList;
    }

    public List<Tool> findBoughtToolsByBoughtDate (String boughtDate){
        List<Tool> boughtToolList = boughtToolRepository.findBoughtToolsByBoughtDate(boughtDate);
        if (boughtToolList.isEmpty()){
            throw new IllegalStateException("can not find data for date: " + boughtDate);
        } else return boughtToolList;
    }
}
