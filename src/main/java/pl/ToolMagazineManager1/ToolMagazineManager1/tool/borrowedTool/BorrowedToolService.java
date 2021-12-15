package pl.ToolMagazineManager1.ToolMagazineManager1.tool.borrowedTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public void addBorrowedTool (BorrowedTool borrowedTool){
        int borrowQuantity = borrowedTool.getBorrowedQuantity();
        for (int saveBorrowQuantity = 0; saveBorrowQuantity < borrowQuantity; saveBorrowQuantity++){
            borrowedToolRepository.save(borrowedTool);
        }

    }

    public void giveBackBorrowedTool (Long borrowedToolId, Long userId){

    }

    public List<BorrowedTool> getBorrowedTools(){
        return borrowedToolRepository.findAll();
    }

    public List<Tool> getBorrowedToolsByUserId (Optional<User> user){
        return borrowedToolRepository.getBorrowedToolsByUserId(user);
    }

    public List<User> getBorrowedToolsUsersByToolId (Long toolId){
        return borrowedToolRepository.getBorrowedToolsUsersByToolId(toolId);
    }

}
