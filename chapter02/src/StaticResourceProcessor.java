import java.io.IOException;

/**
 * @author lijiedong
 * @version 1.0
 * @date 4/18/21 7:46 PM
 */
public class StaticResourceProcessor {
    public void process(Request request, Response response) {
        try {
            response.sendStaticResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
