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
                            @RequestParam Integer borrowQuantity){
        borrowedToolService.addBorrowedTool(toolId, userId, borrowQuantity);
//        toolService.borrowTool(toolId, borrowQuantity);
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
    public List<Tool> findBorrowedToolsByUserId(@PathVariable("userId") Long userId){
       return borrowedToolService.findBorrowedToolsByUserId(userId);
    }

    @GetMapping("/getBorrowedToolsUsersByToolId/{toolId}")
    public List<User> findBorrowedToolsUsersByToolId(@PathVariable("toolId") Long toolId) {
        return borrowedToolService.findBorrowedToolsUsersByToolId(toolId);
    }

    @GetMapping("/findBorrowedToolByToolId/{toolId}")
    public Tool findBorrowedToolByToolId (@PathVariable("toolId") Long toolId){
        return borrowedToolService.findBorrowedToolByToolId(toolId);
    }

    @GetMapping("/findBorrowedToolUserByUserId/{userId}")
    public User findBorrowedToolUserByUserId (@PathVariable("userId") Long userId){
        return borrowedToolService.findBorrowedToolUserByUserId(userId);
    }

    @GetMapping("/findBorrowedToolsByBorrowDate/{borrowedDate}")
    public List<Tool> findBorrowedToolsByBorrowDate (@PathVariable("borrowedDate") String borrowDate){
        return borrowedToolService.findBorrowedToolsByBorrowDate(borrowDate);
    }


}
