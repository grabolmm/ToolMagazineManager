package pl.ToolMagazineManager.ToolMagazineManager.tool.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ToolService {

    private final ToolRepository toolRepository;

    @Autowired
    public ToolService(ToolRepository toolRepository) {
        this.toolRepository = toolRepository;
    }

    public List<Tool> getTools(){
        return toolRepository.findAll();
    }

    public void addTool (Tool tool){
        toolRepository.save(tool);
    }

    public void deleteTool (Long toolId){
        Tool tool = toolRepository.findById(toolId).orElseThrow(() ->new IllegalStateException (
                "tool with id " + toolId + " does not exist"));
        toolRepository.deleteById(toolId);
    }

    @Transactional
    public void updateTool (Long id, GroupName groupName, String diameter,
                            String company, String companyCode){
        Tool tool = toolRepository.findById(id).orElseThrow(() -> new IllegalStateException (
                "tool with id " + id + " does not exist"));
        if (groupName != null){
            tool.setGroupName(groupName);
        }
        if (diameter != null){
            tool.setDiameter(diameter);
        }
        if (company != null) {
            tool.setCompany(company);
        }
        if (companyCode != null) {
            tool.setCompanyCode(companyCode);
        }
    }

    @Transactional
    public void buyTool (Long toolId, int boughtQuantity){
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist"));
        int currentMagazineQuantity = tool.getMagazineQuantity();
        int updatedMagazineQuantity = currentMagazineQuantity + boughtQuantity;
        tool.setMagazineQuantity(updatedMagazineQuantity);
    }

    @Transactional
    public void borrowTool (Long toolId, int borrowQuantity) {
            Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new IllegalStateException(
                    "tool with id " + toolId + " does not exist"));
            if (borrowQuantity != 0 && tool.getMagazineQuantity() >= borrowQuantity) {
                tool.setMagazineQuantity(tool.getMagazineQuantity() - borrowQuantity);
                tool.setInUseQuantity(tool.getInUseQuantity() + borrowQuantity);
            } else throw new IllegalStateException("magazine quantity is not enough");
            if (tool.getMagazineQuantity() < tool.getMinMagazineQuantity()) {
                throw new IllegalStateException("magazine tools quantity less than min magazine quantity - please order tools");
            }
    }

    @Transactional
    public void giveBackTool (Long toolId, int giveBackQuantity){
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist"));
        int currentMagazineQuantity = tool.getMagazineQuantity();
        int updatedMagazineQuantity = currentMagazineQuantity + giveBackQuantity;
        tool.setMagazineQuantity(updatedMagazineQuantity);
        int currentInUseQuantity = tool.getInUseQuantity();
        int updatedInUseQuantity = currentInUseQuantity - giveBackQuantity;
        tool.setInUseQuantity(updatedInUseQuantity);
    }

    public Optional<Tool> findToolById (Long toolId){
        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist"));
        return toolRepository.findById(toolId);
    }

    public List<Tool> findToolByCompany(String company){
        if (toolRepository.findToolByCompany(company).isEmpty() == false){
            return toolRepository.findToolByCompany(company);
        } else throw new IllegalStateException ("tool with company name " + company + " does not exist");
    }

    public List<Tool> findToolByGroupName(GroupName groupName){
        if (toolRepository.findToolByGroupName(groupName).isEmpty() == false){
            return toolRepository.findToolByGroupName(groupName);
        } else throw new IllegalStateException ("tool with group name " + groupName + " does not exist");
    }

    public List<Tool> findToolByDiameter(String diameter){
        if (toolRepository.findToolByDiameter(diameter).isEmpty() == false){
            return toolRepository.findToolByDiameter(diameter);
        } else throw new IllegalStateException("tool with diameter " + diameter + " does not exist");
    }

    public List<Tool> findToolByCompanyCode(String companyCode){
        if (toolRepository.findToolByCompanyCode(companyCode).isEmpty() == false){
            return toolRepository.findToolByCompanyCode(companyCode);
        } else throw new IllegalStateException("tool with company code " + companyCode + " does not exist");
    }

}
