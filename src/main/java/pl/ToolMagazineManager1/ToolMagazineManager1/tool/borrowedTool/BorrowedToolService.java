package pl.ToolMagazineManager1.ToolMagazineManager1.tool.borrowedTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.GroupName;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.Tool;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.User;

import java.util.List;
import java.util.Optional;

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
        List<Tool> usersBorrowedTool = getBorrowedToolsByToolIdAndUserId(toolId, userId);
        if (usersBorrowedTool.size() >= giveBackQuantity){
            for(int k = 0; k < giveBackQuantity; k++){
                Tool toolToGiveBack = usersBorrowedTool.get(k);
                borrowedToolRepository.deleteById(toolToGiveBack.getId());
            }
        } else throw new IllegalStateException("user do not has enough borrowed tools to give it back");
    }

    public List<Tool> getBorrowedToolsByUserId (Long userId){
        findBorrowedToolUserByUserId(userId);
        return borrowedToolRepository.getBorrowedToolsByUserId(userId);
    }

    public List<User> getBorrowedToolsUsersByToolId (Long toolId){
        findBorrowedToolByToolId(toolId);
        return borrowedToolRepository.getBorrowedToolsUsersByToolId(toolId);
    }

    public List<Tool> getBorrowedToolsByToolIdAndUserId (Long toolId, Long userId){
        findBorrowedToolUserByUserId(userId);
        findBorrowedToolByToolId(toolId);
        return borrowedToolRepository.getBorrowedToolsByToolIdAndUserId(toolId, userId);
    }


    public Tool findBorrowedToolByToolId (Long toolId){
        return borrowedToolRepository.findBorrowedToolByToolId(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist, or has not been borrowed"));
    }

    public User findBorrowedToolUserByUserId (Long userId){
        return borrowedToolRepository.findBorrowedToolUserByUserId(userId).orElseThrow(() -> new IllegalStateException (
                "user with id " + userId + " does not exist, or has not borrowed tools"));
    }





}
