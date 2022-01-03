package pl.ToolMagazineManager.ToolMagazineManager.tool.tool;

import javax.persistence.*;

@Entity
@Table
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (nullable = false, updatable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private GroupName groupName;
    private String diameter;
    private String company;
    private String companyCode;
    private int minMagazineQuantity;
    private int magazineQuantity;
    private int inUseQuantity;
    @Transient
    private int totalQuantity;

//    @ManyToMany
//    @JoinTable(
//            name = "borrowed_tools",
//            joinColumns = @JoinColumn (name = "tool_id"),
//            inverseJoinColumns = @JoinColumn (name = "user_id")
//    )
//    private List<User> toolUsers = new ArrayList<>();

    public Tool() {
    }

    public Tool(GroupName groupName, String diameter, String company, String companyCode,
                int minMagazineQuantity) {
        this.groupName = groupName;
        this.diameter = diameter;
        this.company = company;
        this.companyCode = companyCode;
        this.minMagazineQuantity = minMagazineQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GroupName getGroupName() {
        return groupName;
    }

    public void setGroupName(GroupName groupName) {
        this.groupName = groupName;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getMinMagazineQuantity() {
        return minMagazineQuantity;
    }

    public void setMinMagazineQuantity(int minMagazineQuantity) {
        this.minMagazineQuantity = minMagazineQuantity;
    }

    public int getMagazineQuantity() {
        return magazineQuantity;
    }

    public void setMagazineQuantity(int magazineQuantity) {
        this.magazineQuantity = magazineQuantity;
    }

    public int getInUseQuantity() {
        return inUseQuantity;
    }

    public void setInUseQuantity(int inUseQuantity) {
        this.inUseQuantity = inUseQuantity;
    }

    public int getTotalQuantity() {
        return (this.inUseQuantity + this.magazineQuantity);
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    @Override
    public String toString() {
        return "Tool{" +
                "id=" + id +
                ", groupName=" + groupName +
                ", diameter='" + diameter + '\'' +
                ", company='" + company + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", minMagazineQuantity=" + minMagazineQuantity +
                ", magazineQuantity=" + magazineQuantity +
                ", inUseQuantity=" + inUseQuantity +
                ", totalQuantity=" + totalQuantity +
                '}';
    }

//    public void addUser (User user){
//        toolUsers.add(user);
//    }
}
