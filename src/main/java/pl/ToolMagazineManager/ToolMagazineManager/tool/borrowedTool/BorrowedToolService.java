package pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool.BoughtTool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.ToolRepository;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.ToolService;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;
import pl.ToolMagazineManager.ToolMagazineManager.user.UserService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowedToolService {

    private final BorrowedToolRepository borrowedToolRepository;
    private ToolService toolService;
    private UserService userService;

    @Autowired
    public BorrowedToolService(BorrowedToolRepository borrowedToolRepository,
                               ToolService toolService,
                               UserService userService) {
        this.borrowedToolRepository = borrowedToolRepository;
        this.toolService = toolService;
        this.userService = userService;
    }

    public List<BorrowedTool> getBorrowedTools(){
        return borrowedToolRepository.findAll();
    }

    public void addBorrowedTool (Long toolId, Long userId, int borrowQuantity){
        String borrowDate = LocalDate.now().toString();
        Tool tool = toolService.findToolById(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist"));
        User user = userService.findUserById(userId).orElseThrow(() -> new IllegalStateException (
                "user with id " + userId + " does not exist"));

        for (int saveBorrowQuantity = 0; saveBorrowQuantity < borrowQuantity; saveBorrowQuantity++){
            BorrowedTool borrowedTool = new BorrowedTool(user, tool, borrowQuantity, borrowDate);
            borrowedToolRepository.save(borrowedTool);
        }
        toolService.borrowTool(toolId, borrowQuantity);
    }

    public void giveBackBorrowedTool (Long toolId, Long userId, int giveBackQuantity){
        List<BorrowedTool> usersBorrowedTool = findBorrowedToolsByToolIdAndUserId(toolId, userId);
        if (usersBorrowedTool.size() >= giveBackQuantity){
            toolService.giveBackTool(toolId, giveBackQuantity);
            for(int k = 0; k < giveBackQuantity; k++){
                BorrowedTool borrowedToolToGiveBack = usersBorrowedTool.get(k);
                borrowedToolRepository.deleteById(borrowedToolToGiveBack.getId());
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

    public List<BorrowedTool> findBorrowedToolsByToolIdAndUserId(Long toolId, Long userId){
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
