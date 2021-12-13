package pl.ToolMagazineManager1.ToolMagazineManager1.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
