package pl.ToolMagazineManager1.ToolMagazineManager1.tool.borrowedTool;

import pl.ToolMagazineManager1.ToolMagazineManager1.tool.tool.Tool;
import pl.ToolMagazineManager1.ToolMagazineManager1.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class BorrowedTool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, updatable = false)
    private Long id;

    @OneToOne
    private User user;

    @OneToOne
    private Tool tool;

    private int borrowedQuantity;
    private String borrowDate;

    public BorrowedTool() {
    }

    public BorrowedTool(User user, Tool tool, int borrowedQuantity, String borrowDate) {
        this.user = user;
        this.tool = tool;
        this.borrowedQuantity = borrowedQuantity;
        this.borrowDate = borrowDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public int getBorrowedQuantity() {
        return borrowedQuantity;
    }

    public void setBorrowedQuantity(int borrowedQuantity) {
        this.borrowedQuantity = borrowedQuantity;
    }

    public String getBorrowDate() {
        return LocalDate.now().toString();
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    @Override
    public String toString() {
        return "BorrowedTool{" +
                "id=" + id +
                ", user=" + user +
                ", tool=" + tool +
                ", borrowedQuantity=" + borrowedQuantity +
                ", borrowDate=" + borrowDate +
                '}';
    }
}

