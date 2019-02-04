package makecodework.com.whoseroads.Model;

public class Roads {
    private String Name, Image, Description, Postid;

    public Roads() {
    }

    public Roads(String name, String image, String description, String postid) {
        Name = name;
        Image = image;
        Description = description;
        Postid = postid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getPostid() {
        return Postid;
    }

    public void setPostid(String postid) {
        Postid = postid;
    }
}
