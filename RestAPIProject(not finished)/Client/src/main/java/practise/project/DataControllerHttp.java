package practise.project;

import DataController.IDataController;
import Network.HttpRestClient;
import org.json.JSONException;
import org.json.JSONObject;
import practise.singleton.Singleton;
import retrofit2.http.HTTP;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.Base64;

public class DataControllerHttp implements IDataController {
    public Object GetSomething() {
        var jsonData = "params";
        return new Object();
    }

    @Override
    public JSONObject Login(JSONObject arrStr) throws JSONException {

        String url = "/login?";
        String urlParams = "operationID=" + arrStr.getString("operationID") + "&login=" + arrStr.getString("login")
                + "&password=" + arrStr.getString("password");
        url= url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject Registration(JSONObject arrStr) throws JSONException {
        String url = "/registration";

        return SendData("POST", url, arrStr, true);
    }

    @Override
    public Object GetPaymentList(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetItemsList(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddPayment(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddItem(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetPaymentInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object UpdatePayment(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetFullPaymentInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTasksList(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddTask(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetProjectList(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetProcessList(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTaskInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object UpdateTaskInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetChatsList(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddChat(String[] arrStr, BufferedImage image) {
        return null;
    }

    @Override
    public Object GetChatMessages(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetChatMessagesNames(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddMessage(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeleteMessageStatus(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetProjectsList(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddProject(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTeamsList(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeletePersonal(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddBusiness(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetBusinessInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object UpdateBusiness(String[] arrStr) {
        return null;
    }

    @Override
    public Object CompleteBusiness(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeleteBusiness(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddComment(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeleteClient(String[] arrStr) {
        return null;
    }

    @Override
    public Object ChangePaymentStatus(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeletePayment(String[] arrStr) {
        return null;
    }

    @Override
    public Object ChangeTaskStatus(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeleteTask(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetProjectInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTeamMembersList(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetPersonalGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetClientGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetPaymentGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTaskGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetProjectGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetBusinessGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object CompletePayment(String[] arrStr, BufferedImage image) {
        return null;
    }

    @Override
    public Object GetPaymentCheck(String[] arrStr) {
        return null;
    }

    @Override
    public JSONObject GetPersonalList(JSONObject arrStr) throws JSONException{
        String url = "/personal/getlist?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url= url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject AddPersonal(JSONObject arrStr) throws JSONException {
        String url = "/personal/add";

        return SendData("POST", url, arrStr, true);
    }

    @Override
    public JSONObject GetPersonalInfo(JSONObject arrStr) throws JSONException{
        String url = "/personal/viewinfo?";
        String urlParams = null;
        try {
            urlParams = "operationID=" + arrStr.getString("operationID") + "&login="
                    + URLEncoder.encode(arrStr.getString("login"), java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        url= url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject UpdatePersonalInfo(JSONObject arrStr, BufferedImage image) throws JSONException {
        String url = "/personal/updateinfo";
        byte[] imageBytes;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            imageBytes = baos.toByteArray();
            baos.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        arrStr.put("image", Base64.getEncoder().encodeToString(imageBytes));
        return SendData("POST", url, arrStr, true);
    }

    @Override
    public JSONObject UpdatePersonalInfoAsManager(JSONObject arrStr) throws JSONException {
        String url = "/personal/updateInfoAsManager";
        return SendData("POST", url, arrStr, true);
    }

    @Override
    public JSONObject GetClientsList(JSONObject arrStr) throws JSONException {
        String url = "/clients/getlist?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url= url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetClientInfo(JSONObject arrStr) throws JSONException{
        String url = "/clients/viewinfo?";
        String urlParams = null;
        urlParams = "operationID=" + arrStr.getString("operationID") + "&id="
                    + arrStr.getString("id");
        url= url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject AddClient(JSONObject arrStr) throws JSONException {
        String url = "/clients/add";
        return SendData("POST", url, arrStr, true);
    }

    @Override
    public JSONObject UpdateClientInfo(JSONObject arrStr) throws JSONException {
        String url = "/clients/update";
        return SendData("POST", url, arrStr, true);
    }

//    @Override
//    public Object GetPaymentList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetItemsList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object AddPayment(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object AddItem(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetPaymentInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object UpdatePayment(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetFullPaymentInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetTasksList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
    @Override
    public JSONObject GetPersonalObeyList(JSONObject arrStr) throws JSONException {
        String url = "/personal/getObeyList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url= url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetPersonalControlList(JSONObject arrStr) throws JSONException {
        String url = "/personal/getControlList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url= url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

//    @Override
//    public Object AddTask(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetProjectList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetProcessList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetTaskInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object UpdateTaskInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetChatsList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object AddChat(String[] arrStr, BufferedImage image) {
//        return SendDataWithImage(arrStr, true, image);
//    }
//
//    @Override
//    public Object GetChatMessages(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetChatMessagesNames(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object AddMessage(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object DeleteMessageStatus(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetProjectsList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object AddProject(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetTeamsList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object DeletePersonal(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object AddBusiness(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetBusinessInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object UpdateBusiness(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object CompleteBusiness(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object DeleteBusiness(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object AddComment(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object DeleteClient(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object ChangePaymentStatus(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object DeletePayment(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object ChangeTaskStatus(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object DeleteTask(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetProjectInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetTeamMembersList(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetPersonalGeneralInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetClientGeneralInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetPaymentGeneralInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetTaskGeneralInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetProjectGeneralInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object GetBusinessGeneralInfo(String[] arrStr) {
//        return SendData(arrStr, true);
//    }
//
//    @Override
//    public Object CompletePayment(String[] arrStr, BufferedImage image) {
//        return SendDataWithImage(arrStr, true, image);
//    }
//
//    @Override
//    public Object GetPaymentCheck(String[] arrStr) {
//        return SendData(arrStr, true);
//    }

    //=============================================================================
    //=============================================================================


    public JSONObject SendData(String requestType, String url, JSONObject object, boolean need_answer) throws JSONException {
        System.out.println(url);
        System.out.println(object);
        JSONObject res = new JSONObject();
        try {
            Singleton.getInstance().setSock((HttpURLConnection) new URL("http://localhost:2024" + url).openConnection(),
                    requestType);
            if (requestType.equals("POST")) {
                res = HttpRestClient.Post(Singleton.getInstance().getSock(), object, need_answer);
            }
            else {
                res = HttpRestClient.Get(Singleton.getInstance().getSock(), need_answer);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return res;
    }

    public Object SendDataWithImage(String[] arrStr, boolean need_answer, BufferedImage image) {
        Object res = "";
        try {
            res = HttpRestClient.GetWithAdditionalImageSend(Singleton.getInstance().getSock(), arrStr, need_answer, image);
        } catch (IOException e) {
            System.out.println(e);
        }
        return res;
    }
}
