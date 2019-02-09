package makecodework.com.whoseroads.Model;

public class User {
    private String name;
    private String password;
    private String isStaff;
    private String image;
    private String phone;
    private String secureCode;

    public User() {
    }

    public User(String name, String password, String secureCode) {
        this.name = name;
        this.password = password;
        this.secureCode = secureCode;

    }

    public User(String phone, String image) {
        this.phone = phone;
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSecureCode() {
        return secureCode;
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
    }
}
