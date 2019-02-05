package makecodework.com.whoseroads.Model;

public class Roads {
    private String DefectName, Image, Description, Street;

    public Roads() {
    }

    public Roads(String defectName, String image, String description, String street) {
        DefectName = defectName;
        Image = image;
        Description = description;
        Street = street;
    }

    public String getDefectName() {
        return DefectName;
    }

    public void setDefectName(String defectName) {
        DefectName = defectName;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }
}
