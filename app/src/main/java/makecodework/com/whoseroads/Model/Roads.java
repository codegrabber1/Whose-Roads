package makecodework.com.whoseroads.Model;

public class Roads {
    private String author, defectName, description, image, street;

    public Roads() {
    }

    public Roads(String author, String defectName, String description, String image, String street) {
        this.author = author;
        this.defectName = defectName;
        this.description = description;
        this.image = image;
        this.street = street;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDefectName() {
        return defectName;
    }

    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
