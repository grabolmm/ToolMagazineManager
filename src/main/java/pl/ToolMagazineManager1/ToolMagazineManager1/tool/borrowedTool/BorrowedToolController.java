package pl.ToolMagazineManager1.ToolMagazineManager1.tool.borrowedTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.boughtTool.BoughtTool;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.Tool;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.ToolService;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.User;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.UserService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/borrowedTools")
public class BorrowedToolController {

    private final BorrowedToolService borrowedToolService;

    @Autowired
    private ToolService toolService;

    @Autowired
    private UserService userService;

    @Autowired
    public BorrowedToolController(BorrowedToolService borrowedToolService) {
        this.borrowedToolService = borrowedToolService;
    }

    @PostMapping("/borrowTool/{toolId}/{userId}")
    public void borrowTool (@PathVariable("toolId") Long toolId,
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

    @GetMapping("/getBorrowedTools")
    public List<BorrowedTool> getBorrowedTools(){
        return borrowedToolService.getBorrowedTools();
    }

    @GetMapping("/getBorrowedToolsByUserId/{userId}")
    public List<Tool> getBorrowedToolsByUserId (@PathVariable("userId") Long userId){
        return borrowedToolService.getBorrowedToolsByUserId(userId);
    }

}
