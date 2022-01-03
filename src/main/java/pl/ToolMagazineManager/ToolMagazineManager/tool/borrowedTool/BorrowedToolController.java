package pl.ToolMagazineManager.ToolMagazineManager.tool.borrowedTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.ToolService;
import pl.ToolMagazineManager.ToolMagazineManager.user.User;
import pl.ToolMagazineManager.ToolMagazineManager.user.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/borrowedTools")
public class BorrowedToolController {

    private BorrowedToolService borrowedToolService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private UserService userService;

    @Autowired
    public BorrowedToolController(BorrowedToolService borrowedToolService) {
        this.borrowedToolService = borrowedToolService;
    }

    @PostMapping("/borrowTool/{toolId}/{userId}")
    public void addBorrowedTool (@PathVariable("toolId") Long toolId,
                            @PathVariable("userId") Long userId,
                            @RequestParam int borrowQuantity){
        Tool tool = toolService.findToolById(toolId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + toolId + " does not exist"));
        User user = userService.findUserById(userId).orElseThrow(() -> new IllegalStateException (
                "tool with id " + userId + " does not exist"));
        String borrowDate = LocalDate.now().toString();
        BorrowedTool borrowedTool = new BorrowedTool(user, tool, borrowQuantity, borrowDate);
        borrowedToolService.addBorrowedTool(borrowedTool);
        toolService.borrowTool(toolId, borrowQuantity);
    }

    @DeleteMapping("/giveBackBorrowedTool/{toolId}/{userId}")
    public void giveBackBorrowedTool (@PathVariable("toolId") Long toolId,
                                      @PathVariable("userId") Long userId,
                                      @RequestParam int giveBackQuantity){
        borrowedToolService.giveBackBorrowedTool(toolId, userId, giveBackQuantity);
        toolService.giveBackTool(toolId, giveBackQuantity);
    }

    @GetMapping("/getBorrowedTools")
    public List<BorrowedTool> getBorrowedTools(){
        return borrowedToolService.getBorrowedTools();
    }

    @GetMapping("/getBorrowedToolsByUserId/{userId}")
    public List<Tool> getBorrowedToolsByUserId (@PathVariable("userId") Long userId){
       return borrowedToolService.getBorrowedToolsByUserId(userId);
    }

    @GetMapping("/getBorrowedToolsUsersByToolId/{toolId}")
    public List<User> getBorrowedToolsUsersByToolId (@PathVariable("toolId") Long toolId) {
        return borrowedToolService.getBorrowedToolsUsersByToolId(toolId);
    }

    @GetMapping("/findBorrowedToolByToolId/{toolId}")
    public Tool findBorrowedToolByToolId (@PathVariable("toolId") Long toolId){
        return borrowedToolService.findBorrowedToolByToolId(toolId);
    }

    @GetMapping("/findBorrowedToolUserByUserId/{userId}")
    public User findBorrowedToolUserByUserId (@PathVariable("userId") Long userId){
        return borrowedToolService.findBorrowedToolUserByUserId(userId);
    }


}
