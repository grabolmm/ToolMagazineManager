package pl.ToolMagazineManager1.ToolMagazineManager1.tool;

import javax.persistence.*;

@Entity
@Table
public class BoughtTool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, updatable = false)
    private Long id;
    private Long toolId;
    private int magazineQuantity;
    private double price;
    private String invoice;

    public BoughtTool() {
    }

    public BoughtTool(Long toolId, int magazineQuantity, double price, String invoice) {
        this.toolId = toolId;
        this.magazineQuantity = magazineQuantity;
        this.price = price;
        this.invoice = invoice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getToolId() {
        return toolId;
    }

    public void setToolId(Long toolId) {
        this.toolId = toolId;
    }

    public int getMagazineQuantity() {
        return magazineQuantity;
    }

    public void setMagazineQuantity(int magazineQuantity) {
        this.magazineQuantity = magazineQuantity;
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

    @Override
    public String toString() {
        return "BoughtTool{" +
                "id=" + id +
                ", toolId=" + toolId +
                ", price=" + price +
                ", invoice='" + invoice + '\'' +
                '}';
    }
}
