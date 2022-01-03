package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
