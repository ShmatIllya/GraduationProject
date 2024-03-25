package Network;



import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.nio.ByteBuffer;

public class HttpRestClient {
    public static JSONObject Post(HttpURLConnection sock, JSONObject object, boolean need_answer) throws IOException, JSONException {
        OutputStream os = sock.getOutputStream();
        os.write(object.toString().getBytes());
        os.flush();
        os.close();
        JSONObject response = new JSONObject();
        if(need_answer == true)
        {

            int responseCode = sock.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            // Reading the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = new JSONObject(new String(IOUtils.toByteArray(sock.getInputStream())));
                System.out.println("Server response: " + response);
            }
            else {
                response.put("response", "null");
            }
        }
        return response;
    }

    public static JSONObject Get(HttpURLConnection sock, boolean need_answer) throws IOException, JSONException {
        JSONObject response = new JSONObject();
        if(need_answer == true)
        {
            int responseCode = sock.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            // Reading the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                response = new JSONObject(new String(IOUtils.toByteArray(sock.getInputStream())));
                System.out.println("Server response: " + response);
            }
            else {
                response.put("response", "null");
            }
        }
        return response;
    }
    public static Object GetWithAdditionalImageSend(HttpURLConnection sock, String[] arrStr, boolean need_answer, BufferedImage image) throws IOException {
        String message = "";
        String resString = "";
        byte[] bytemessage;
        for (String i: arrStr)
        {
            message += i + ">>";
        }
        bytemessage = message.getBytes();
        sock.getOutputStream().write(bytemessage);
        sock.getOutputStream().flush();
        //=========================================================
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            sock.getOutputStream().write(size);
            sock.getOutputStream().write(byteArrayOutputStream.toByteArray());
            sock.getOutputStream().flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        //=========================================================
        if(need_answer == true)
        {
            BufferedReader brinp = new BufferedReader(new InputStreamReader(sock.getInputStream(), "UTF-8"));
            resString = brinp.readLine();
        }
        return resString;
    }

    public static Object Post(String serverPath, String data)
    {
        return new Object();
    }
}
