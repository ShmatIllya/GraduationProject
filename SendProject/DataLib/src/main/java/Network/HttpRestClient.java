package Network;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

public class HttpRestClient {
    public static Object Get(OutputStream os, InputStream is, String[] arrStr, boolean need_answer) throws IOException {
        String message = "";
        String resString = "";
        byte[] bytemessage;
        for (String i: arrStr)
        {
            message += i + ">>";
        }
        bytemessage = message.getBytes();
        System.out.println(bytemessage);
        System.out.println(message);
        os.write(bytemessage);
        if(need_answer == true)
        {
            BufferedReader brinp = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            resString = brinp.readLine();
        }
        return resString;
    }

    public static Object GetWithAdditionalImageSend(OutputStream os, InputStream is, String[] arrStr, boolean need_answer, BufferedImage image) throws IOException {
        String message = "";
        String resString = "";
        byte[] bytemessage;
        for (String i: arrStr)
        {
            message += i + ">>";
        }
        bytemessage = message.getBytes();
        os.write(bytemessage);
        os.flush();
        //=========================================================
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
            os.write(size);
            os.write(byteArrayOutputStream.toByteArray());
            os.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        //=========================================================
        if(need_answer == true)
        {
            BufferedReader brinp = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            resString = brinp.readLine();
        }
        return resString;
    }

    public static Object Post(String serverPath, String data)
    {
        return new Object();
    }
}
