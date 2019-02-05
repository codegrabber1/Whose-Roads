package makecodework.com.whoseroads.Model;

public class Category {
    private String ObjectName, Description, Performer, Price,DataRelease;


    public Category() {
    }

    public Category(String objectName, String description, String performer, String price, String dataRelease) {
        ObjectName = objectName;
        Description = description;
        Performer = performer;
        Price = price;
        DataRelease = dataRelease;
    }

    public String getObjectName() {
        return ObjectName;
    }

    public void setObjectName(String objectName) {
        ObjectName = objectName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPerformer() {
        return Performer;
    }

    public void setPerformer(String performer) {
        Performer = performer;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDataRelease() {
        return DataRelease;
    }

    public void setDataRelease(String dataRelease) {
        DataRelease = dataRelease;
    }
}
