package pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool;

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
            if (borrowQuantity != 0 && tool.getMagazineQuantity() > 0) {
                tool.setMagazineQuantity(tool.getMagazineQuantity() - borrowQuantity);
                tool.setInUseQuantity(tool.getInUseQuantity() + borrowQuantity);
            } else throw new IllegalStateException("magazine quantity is not enough");
            if (tool.getMagazineQuantity() < tool.getMinMagazineQuantity()) {

                System.out.println("magazine tools quantity less than min magazine quantity - please order tools");
            }
        }

//    @Transactional
//    public void updateTool (Long id, Integer minMagazineQuantity, Integer magazineQuantity,
//                            Integer inUseQuantity){
//        Tool tool = toolRepository.findById(id).orElseThrow(() -> new IllegalStateException (
//                "tool with id " + id + " does not exist"));
//        if (minMagazineQuantity != null){
//            tool.setMinMagazineQuantity(minMagazineQuantity);
//        }
//        if (magazineQuantity != null){
//            tool.setMagazineQuantity(magazineQuantity);
//        }
//        if (inUseQuantity != null) {
//            tool.setInUseQuantity(inUseQuantity);
//        }
//    }

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
//
//    @Transactional
//    public void borrowTool (Long toolId, Integer borrowQuantity, Long userId){
//        Tool tool = toolRepository.findById(toolId).orElseThrow(() -> new IllegalStateException (
//                "tool with id " + toolId + " does not exist"));
//        User user = new User(userId); // dodać wyszukiwanie usera po Id
//        if (borrowQuantity != 0 && tool.getMagazineQuantity() > 0){
//            tool.setMagazineQuantity(tool.getMagazineQuantity() - borrowQuantity);
//            tool.setInUseQuantity(tool.getInUseQuantity() + borrowQuantity);
//        } else throw new IllegalStateException ("magazine quantity is not enough");
//        if (tool.getMagazineQuantity() < tool.getMinMagazineQuantity()) {
//            System.out.println("magazine tools quantity less than min magazine quantity - please order tools");
//        }
//        for (int saveBorrowQuantity = 0; saveBorrowQuantity < borrowQuantity; saveBorrowQuantity++){
//            tool.addUser(user);
//            toolRepository.save(tool);
//        }
//    }
//
//    @Transactional
//    public void damageUsedTool (Long id, Integer damageUsedQuantity){
//        Tool tool = toolRepository.findById(id).orElseThrow(() -> new IllegalStateException (
//                "tool with id " + id + " does not exist"));
//        if (damageUsedQuantity !=0 && tool.getInUseQuantity() > 0 ){
//            tool.setInUseQuantity(tool.getInUseQuantity() - damageUsedQuantity);
//        }
//    }
//
//    public List<User> getUsers (Long toolId){
//        return toolRepository.getUsers(toolId);
//    }
}
