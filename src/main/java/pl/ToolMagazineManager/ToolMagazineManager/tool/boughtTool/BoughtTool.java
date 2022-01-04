package pl.ToolMagazineManager.ToolMagazineManager.tool.boughtTool;

import org.springframework.lang.NonNull;
import pl.ToolMagazineManager.ToolMagazineManager.tool.tool.Tool;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class BoughtTool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, updatable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "tool_id")
    private Tool tool;

    private int boughtQuantity;
    private double price;
    private String invoice;
    private String boughtDate;

    public BoughtTool() {

    }

    public BoughtTool(Tool tool, int boughtQuantity, double price, String invoice, String boughtDate) {
        this.tool = tool;
        this.boughtQuantity = boughtQuantity ;
        this.price = price;
        this.invoice = invoice;
        this.boughtDate = boughtDate;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public int getBoughtQuantity() {
        return boughtQuantity;
    }

    public void setBoughtQuantity(int boughtQuantity) {
        this.boughtQuantity = boughtQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getBoughtDate() {
        return LocalDate.now().toString();
    }

    public void setBoughtDate(String boughtDate) {
        this.boughtDate = boughtDate;
    }

    @Override
    public String toString() {
        return "BoughtTool{" +
                "id=" + id +
                ", tool=" + tool +
                ", price=" + price +
                ", invoice='" + invoice + '\'' +
                ", boughtDate='" + boughtDate + '\'' +
                '}';
    }
}
