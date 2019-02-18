package makecodework.com.pits.Model;

public class About {
    private String aboutTitle, fullText;

    public About() {
    }

    public About(String aboutTitle, String fullText) {
        this.aboutTitle = aboutTitle;
        this.fullText = fullText;
    }

    public String getAboutTitle() {
        return aboutTitle;
    }

    public void setAboutTitle(String aboutTitle) {
        this.aboutTitle = aboutTitle;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }
}
