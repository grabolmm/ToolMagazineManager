package pl.ToolMagazineManager1.ToolMagazineManager1.tool.borrowedTool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.Tool;

import java.util.List;

@Service
public class BorrowedToolService {

    private final BorrowedToolRepository borrowedToolRepository;

    @Autowired
    public BorrowedToolService(BorrowedToolRepository borrowedToolRepository) {
        this.borrowedToolRepository = borrowedToolRepository;
    }

    public void addBorrowedTool (BorrowedTool borrowedTool){
        borrowedToolRepository.save(borrowedTool);
    }

    public List<BorrowedTool> getBorrowedTools(){
        return borrowedToolRepository.findAll();
    }

    public List<Tool> getBorrowedToolsByUserId (Long userId){
        return borrowedToolRepository.getBorrowedToolsByUserId(userId);
    }
}
