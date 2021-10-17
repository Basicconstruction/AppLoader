import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Utils {
    public static void Browse(String url_path){
        Desktop dek = Desktop.getDesktop();
        if(dek.isSupported(Desktop.Action.BROWSE)){
            try {
                dek.browse(new URI(url_path));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }
}
