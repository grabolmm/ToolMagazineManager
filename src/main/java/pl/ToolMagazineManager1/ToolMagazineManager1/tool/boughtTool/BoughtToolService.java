package pl.ToolMagazineManager1.ToolMagazineManager1.tool.boughtTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.Tool;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.ToolRepository;

import java.util.List;

@Service
public class BoughtToolService {

    private final BoughtToolRepository boughtToolRepository;

    @Autowired
    public BoughtToolService(BoughtToolRepository boughtToolRepository) {
        this.boughtToolRepository = boughtToolRepository;
    }

    public void addBoughtTool(BoughtTool boughtTool){
        boughtToolRepository.save(boughtTool);
    }

    public List<BoughtTool> getBoughtTools(){
        return boughtToolRepository.findAll();
    }
}
