package app.ui.adview;

public class Thumbnail extends Preview {
    public String path;

    public Thumbnail(String path) {
        super(path, 100, -1, 5);
        this.path = path;
    }
}
