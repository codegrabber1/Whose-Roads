package makecodework.com.whoseroads.Model;

public class Category {
    private String Defect;
    private String Address;
    private String Image;

    public Category() {
    }

    public Category(String defect, String address, String image) {
        Defect = defect;
        Address = address;
        Image = image;
    }

    public String getDefect() {
        return Defect;
    }

    public void setDefect(String defect) {
        Defect = defect;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
