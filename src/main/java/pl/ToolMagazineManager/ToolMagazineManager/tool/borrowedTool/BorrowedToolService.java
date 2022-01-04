package pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool.BoughtTool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;

import java.util.List;

@Service
public class BorrowedToolService {

    private final BorrowedToolRepository borrowedToolRepository;

    @Autowired
    public BorrowedToolService(BorrowedToolRepository borrowedToolRepository) {
        this.borrowedToolRepository = borrowedToolRepository;
    }

    public List<BorrowedTool> getBorrowedTools(){
        return borrowedToolRepository.findAll();
    }

    public void addBorrowedTool (BorrowedTool borrowedTool){
        int borrowQuantity = borrowedTool.getBorrowedQuantity();
        for (int saveBorrowQuantity = 0; saveBorrowQuantity < borrowQuantity; saveBorrowQuantity++){
            borrowedToolRepository.save(borrowedTool);
        }
    }

    public void giveBackBorrowedTool (Long toolId, Long userId, int giveBackQuantity){
        List<Tool> usersBorrowedTool = findBorrowedToolsByToolIdAndUserId(toolId, userId);
        if (usersBorrowedTool.size() >= giveBackQuantity){
            for(int k = 0; k < giveBackQuantity; k++){
                Tool toolToGiveBack = usersBorrowedTool.get(k);
                borrowedToolRepository.deleteById(toolToGiveBack.getId());
            }
        } else throw new IllegalStateException("user do not has enough borrowed tools to give it back");
    }

    public List<Tool> findBorrowedToolsByUserId(Long userId){
        findBorrowedToolUserByUserId(userId);
        return borrowedToolRepository.findBorrowedToolsByUserId(userId);
    }

    public List<User> findBorrowedToolsUsersByToolId(Long toolId){
        findBorrowedToolByToolId(toolId);
        return borrowedToolRepository.findBorrowedToolsUsersByToolId(toolId);
    }

    public List<Tool> findBorrowedToolsByToolIdAndUserId(Long toolId, Long userId){
        findBorrowedToolUserByUserId(userId);
        findBorrowedToolByToolId(toolId);
        return borrowedToolRepository.findBorrowedToolsByToolIdAndUserId(toolId, userId);
    }

    public Tool findBorrowedToolByToolId (Long toolId){
        return borrowedToolRepository.findBorrowedToolByToolId(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist, or has not been borrowed"));
    }

    public User findBorrowedToolUserByUserId (Long userId){
        return borrowedToolRepository.findBorrowedToolUserByUserId(userId).orElseThrow(() -> new IllegalStateException (
                "user with id " + userId + " does not exist, or has not borrowed tools"));
    }

    public List<Tool> findBorrowedToolsByBorrowDate (String borrowDate){
        List<Tool> borrowedToolsList = borrowedToolRepository.findBorrowedToolsByBorrowDate(borrowDate);
        if (borrowedToolsList.isEmpty()){
            throw new IllegalStateException("there was no actions on date: " + borrowDate);
        } else return borrowedToolsList;
    }
}
