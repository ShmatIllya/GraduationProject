package practise;

import DataController.DataControllerSql;
import com.logicaldoc.core.imaging.ImageUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class WebHandl implements HttpHandler {
    private DataControllerSql DataController = new DataControllerSql();

    public WebHandl() throws SQLException, ClassNotFoundException {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {


            // Reading JSON data from the client
        JSONObject requestData = new JSONObject();
        ArrayList<String> requestDataGET = new ArrayList<>();
        String operationID = null;
        try {
            if(exchange.getRequestMethod().equals("POST")) {
                requestData = new JSONObject(new String(exchange.getRequestBody().readAllBytes()));
                operationID = requestData.getString("operationID");
            }
            else {

                String query = exchange.getRequestURI().getQuery();
                query = URLDecoder.decode(query, "UTF-8");
                requestDataGET = ProcessGETData(query);
                operationID = requestDataGET.get(0);
                System.out.println(query);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        JSONObject responseData = new JSONObject();
            // Sending JSON response back to the client

        switch (operationID) {
            case "GetSomething": {
                GetSomething();
                break;
            }
            case "1": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("login", requestDataGET.get(1));
                        requestData.put("password", requestDataGET.get(2));
                        responseData = Login(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "0": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = Registration(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "AddPersonal": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = AddPersonal(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }

            case "GetPersonalList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetPersonalList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }

            case "GetPersonalInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("login", requestDataGET.get(1));
                        responseData = GetPersonalInfo(requestData);
//                        byte[] data = readImageBytes();
//                        responseData.put("image", new String(data));
                        File image = GetPersonalImage();
                        byte[] imageBytes = Files.readAllBytes(image.toPath());
                        // Создание JSONObject и добавление PNG изображения как byte[] элемент
                        responseData.put("image", Base64.getEncoder().encodeToString(imageBytes));

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }

                break;
            }

            case "UpdatePersonalInfo": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        byte[] imageBytes = Base64.getDecoder().decode(requestData.getString("image"));
                        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                        BufferedImage image = ImageIO.read(bais);
                        responseData = UpdatePersonalInfo(requestData, image);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "UpdatePersonalInfoAsManager": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = UpdatePersonalInfoAsManager(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetClientsList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetClientsList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetClientInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("id", requestDataGET.get(1));
                        responseData = GetClientInfo(requestData);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }

                break;
            }
            case "AddClient": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = AddClient(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "UpdateClientInfo": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = UpdateClientInfo(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
//            case "GetPaymentList": {
//                String res = GetPaymentList(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetItemsList": {
//                String res = GetItemsList(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "AddPayment": {
//                String res = AddPayment(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "AddItem": {
//                String res = AddItem(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetPaymentInfo": {
//                String res = GetPaymentInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "UpdatePayment": {
//                String res = UpdatePayment(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetFullPaymentInfo": {
//                String res = GetFullPaymentInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetTasksList": {
//                String res = GetTasksList(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
            case "GetPersonalObeyList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetPersonalObeyList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetPersonalControlList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetPersonalControlList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
//            case "AddTask": {
//                String res = AddTasks(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetProjectList": {
//                String res = GetProjectList(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetProcessList": {
//                String res = GetProcessList(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetTaskInfo": {
//                String res = GetTaskInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "UpdateTaskInfo": {
//                String res = UpdateTaskInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetChatsList": {
//                String res_str = GetChatsList(arrStr).toString();
//                List<BufferedImage> images = GetChatImageList();
//                out.write(res_str);
//                out.flush();
//                try {
//                    for (BufferedImage image: images) {
//                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        ImageIO.write(image, "png", byteArrayOutputStream);
//
//                        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//                        MiniServer.socket.getOutputStream().write(size);
//                        MiniServer.socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
//                        MiniServer.socket.getOutputStream().flush();
//                    }
//                }
//                catch (Exception e) {
//                    System.out.println(e);
//                    throw new RuntimeException(e);
//                }
//
//                break;
//            }
//            case "AddChat": {
//                try {
//                    byte[] sizeAr = new byte[4];
//                    MiniServer.socket.getInputStream().read(sizeAr);
//                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//                    byte[] imageAr = new byte[size];
//                    DataInputStream in = new DataInputStream(MiniServer.socket.getInputStream());
//                    in.readFully(imageAr);
//                    //Singleton.getInstance().getIs().read(imageAr);
//                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//                    String res = AddChat(arrStr, image).toString();
//                    out.write(res);
//                    out.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                break;
//            }
//            case "GetChatMessages": {
//                String res_str = GetChatMessages(arrStr).toString();
//                List<BufferedImage> images = GetChatImageList();
//                List<String> names = GetNameSernameList();
//                System.out.println(res_str);
//                out.write(res_str);
//                out.flush();
//                try {
//                    int i = 0;
//                    for (BufferedImage image: images) {
//                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                        ImageIO.write(image, "png", byteArrayOutputStream);
//                        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//                        MiniServer.socket.getOutputStream().write(size);
//                        MiniServer.socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
//                        //MiniServer.socket.getOutputStream().write((names.get(i) + "\r").getBytes("UTF-8"));
//                        MiniServer.socket.getOutputStream().flush();
//                        i++;
//                    }
//                }
//                catch (Exception e) {
//                    System.out.println(e);
//                    throw new RuntimeException(e);
//                }
//
//                break;
//            }
//            case "GetChatMessagesNames": {
//                String res = GetChatMessagesNames(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "AddMessage": {
//                String res = AddMessage(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "DeleteMessageStatus": {
//                String res = DeleteMessageStatus(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetProjectsList": {
//                String res = GetProjectsList(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "AddProject": {
//                String res = AddProject(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetTeamsList": {
//                String res = GetTeamsList(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "DeletePersonal": {
//                String res = DeletePersonal(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "AddBusiness": {
//                String res = AddBusiness(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetBusinessInfo": {
//                String res = GetBusinessInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "UpdateBusiness": {
//                String res = UpdateBusiness(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "CompleteBusiness": {
//                String res = CompleteBusiness(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "DeleteBusiness": {
//                String res = DeleteBusiness(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "AddComment": {
//                String res = AddComment(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "DeleteClient": {
//                String res = DeleteClient(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "ChangePaymentStatus": {
//                String res = ChangePaymentStatus(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "DeletePayment": {
//                String res = DeletePayment(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "ChangeTaskStatus": {
//                String res = ChangeTaskStatus(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "DeleteTask": {
//                String res = DeleteTask(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetProjectInfo": {
//                String res = GetProjectInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetTeamMembersList": {
//                String res = GetTeamMembersList(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetPersonalGeneralInfo": {
//                String res = GetPersonalGeneralInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetClientGeneralInfo": {
//                String res = GetClientGeneralInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetPaymentGeneralInfo": {
//                String res = GetPaymentGeneralInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetTaskGeneralInfo": {
//                String res = GetTaskGeneralInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetProjectGeneralInfo": {
//                String res = GetProjectGeneralInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "GetBusinessGeneralInfo": {
//                String res = GetBusinessGeneralInfo(arrStr).toString();
//                out.write(res);
//                out.flush();
//                break;
//            }
//            case "CompletePayment": {
//                try {
//                    byte[] sizeAr = new byte[4];
//                    MiniServer.socket.getInputStream().read(sizeAr);
//                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
//                    byte[] imageAr = new byte[size];
//                    DataInputStream in = new DataInputStream(MiniServer.socket.getInputStream());
//                    in.readFully(imageAr);
//                    //Singleton.getInstance().getIs().read(imageAr);
//                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
//                    String res = CompletePayment(arrStr, image).toString();
//                    out.write(res);
//                    out.flush();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                break;
//            }
//            case "GetPaymentCheck": {
//                String res_str = GetPaymentCheck(arrStr).toString();
//                BufferedImage image = (BufferedImage) GetPersonalImage();
//                out.write(res_str);
//                out.flush();
//                try {
//                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                    ImageIO.write(image, "png", byteArrayOutputStream);
//
//                    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
//                    MiniServer.socket.getOutputStream().write(size);
//                    MiniServer.socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
//                    MiniServer.socket.getOutputStream().flush();
//                }
//                catch (Exception e) {
//                    System.out.println(e);
//                    throw new RuntimeException(e);
//                }
//
//                break;
//            }
            //========================================================================
            //========================================================================
            //========================================================================

        }
        exchange.sendResponseHeaders(200, responseData.toString().getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseData.toString().getBytes());
        os.close();
    }

    public void GetSomething() {
        Object dataObject = DataController.GetSomething();
    }

    public JSONObject Login(JSONObject arrStr) throws JSONException {
        return DataController.Login(arrStr);
    }

    public JSONObject Registration(JSONObject arrStr) throws JSONException {
        return DataController.Registration(arrStr);
    }

    public JSONObject AddPersonal(JSONObject arrStr) throws JSONException {
        return DataController.AddPersonal(arrStr);
    }

    public JSONObject UpdatePersonalInfo(JSONObject arrStr, BufferedImage image) throws JSONException {
        return DataController.UpdatePersonalInfo(arrStr, image);
    }

    public JSONObject GetPersonalInfo(JSONObject arrStr) throws JSONException {
        return DataController.GetPersonalInfo(arrStr);
    }

    public File GetPersonalImage() {
        return DataController.GetPersonalImage();
    }

//    public List<BufferedImage> GetChatImageList() { return DataController.GetChatImageList();}
//
//    public List<String> GetNameSernameList() { return DataController.GetNameSernameList();}

    public JSONObject GetPersonalList(JSONObject arrStr) throws JSONException{
        return DataController.GetPersonalList(arrStr);
    }

    public JSONObject UpdatePersonalInfoAsManager(JSONObject arrStr) throws JSONException {
        return DataController.UpdatePersonalInfoAsManager(arrStr);
    }

    public JSONObject GetClientsList(JSONObject arrStr) throws JSONException {
        return DataController.GetClientsList(arrStr);
    }

    public JSONObject GetClientInfo(JSONObject arrStr) throws JSONException {
        return DataController.GetClientInfo(arrStr);
    }

    public JSONObject AddClient(JSONObject arrStr) throws JSONException {
        return DataController.AddClient(arrStr);
    }

    public JSONObject UpdateClientInfo(JSONObject arrStr) throws JSONException {
        return  DataController.UpdateClientInfo(arrStr);
    }

    public Object GetPaymentList(String[] arrStr) { return  DataController.GetPaymentList(arrStr);}

    public Object GetItemsList(String[] arrStr) { return  DataController.GetItemsList(arrStr);}

    public Object AddPayment(String[] arrStr) { return  DataController.AddPayment(arrStr);}

    public Object AddItem(String[] arrStr) { return  DataController.AddItem(arrStr);}

    public Object GetPaymentInfo(String[] arrStr) { return  DataController.GetPaymentInfo(arrStr);}

    public Object UpdatePayment(String[] arrStr) { return  DataController.UpdatePayment(arrStr);}

    public Object GetFullPaymentInfo(String[] arrStr) { return  DataController.GetFullPaymentInfo(arrStr);}

    public Object GetTasksList(String[] arrStr) { return  DataController.GetTasksList(arrStr);}

    public JSONObject GetPersonalObeyList(JSONObject arrStr) throws JSONException {
        return  DataController.GetPersonalObeyList(arrStr);
    }

    public JSONObject GetPersonalControlList(JSONObject arrStr) throws JSONException {
        return  DataController.GetPersonalControlList(arrStr);
    }

    public Object AddTasks(String[] arrStr) { return  DataController.AddTask(arrStr);}

    public Object GetProjectList(String[] arrStr) { return  DataController.GetProjectList(arrStr);}

    public Object GetProcessList(String[] arrStr) { return  DataController.GetProcessList(arrStr);}

    public Object GetTaskInfo(String[] arrStr) { return  DataController.GetTaskInfo(arrStr);}

    public Object UpdateTaskInfo(String[] arrStr) { return  DataController.UpdateTaskInfo(arrStr);}

    public Object GetChatsList(String[] arrStr) { return  DataController.GetChatsList(arrStr);}

    public Object AddChat(String[] arrStr, BufferedImage image) {
        return DataController.AddChat(arrStr, image);
    }

    public Object GetChatMessages(String[] arrStr) {
        return DataController.GetChatMessages(arrStr);
    }

    public Object GetChatMessagesNames(String[] arrStr) {
        return DataController.GetChatMessagesNames(arrStr);
    }

    public Object AddMessage(String[] arrStr) {
        return DataController.AddMessage(arrStr);
    }

    public Object DeleteMessageStatus(String[] arrStr) {
        return DataController.DeleteMessageStatus(arrStr);
    }

    public Object GetProjectsList(String[] arrStr) {
        return DataController.GetProjectsList(arrStr);
    }

    public Object AddProject(String[] arrStr) {
        return DataController.AddProject(arrStr);
    }

    public Object GetTeamsList(String[] arrStr) {
        return DataController.GetTeamsList(arrStr);
    }

    public Object DeletePersonal(String[] arrStr) {
        return DataController.DeletePersonal(arrStr);
    }

    public Object AddBusiness(String[] arrStr) {
        return DataController.AddBusiness(arrStr);
    }

    public Object GetBusinessInfo(String[] arrStr) {
        return DataController.GetBusinessInfo(arrStr);
    }

    public Object UpdateBusiness(String[] arrStr) {
        return DataController.UpdateBusiness(arrStr);
    }

    public Object CompleteBusiness(String[] arrStr) {
        return DataController.CompleteBusiness(arrStr);
    }

    public Object DeleteBusiness(String[] arrStr) {
        return DataController.DeleteBusiness(arrStr);
    }

    public Object AddComment(String[] arrStr) {
        return DataController.AddComment(arrStr);
    }

    public Object DeleteClient(String[] arrStr) {
        return DataController.DeleteClient(arrStr);
    }

    public Object ChangePaymentStatus(String[] arrStr) {
        return DataController.ChangePaymentStatus(arrStr);
    }

    public Object DeletePayment(String[] arrStr) {
        return DataController.DeletePayment(arrStr);
    }

    public Object ChangeTaskStatus(String[] arrStr) {
        return DataController.ChangeTaskStatus(arrStr);
    }

    public Object DeleteTask(String[] arrStr) {
        return DataController.DeleteTask(arrStr);
    }

    public Object GetProjectInfo(String[] arrStr) {
        return DataController.GetProjectInfo(arrStr);
    }

    public Object GetTeamMembersList(String[] arrStr) {
        return DataController.GetTeamMembersList(arrStr);
    }

    public Object GetPersonalGeneralInfo(String[] arrStr) {
        return DataController.GetPersonalGeneralInfo(arrStr);
    }

    public Object GetClientGeneralInfo(String[] arrStr) {
        return DataController.GetClientGeneralInfo(arrStr);
    }

    public Object GetPaymentGeneralInfo(String[] arrStr) {
        return DataController.GetPaymentGeneralInfo(arrStr);
    }

    public Object GetTaskGeneralInfo(String[] arrStr) {
        return DataController.GetTaskGeneralInfo(arrStr);
    }

    public Object GetProjectGeneralInfo(String[] arrStr) {
        return DataController.GetProjectGeneralInfo(arrStr);
    }

    public Object GetBusinessGeneralInfo(String[] arrStr) {
        return DataController.GetBusinessGeneralInfo(arrStr);
    }

    public Object CompletePayment(String[] arrStr, BufferedImage image) {
        return DataController.CompletePayment(arrStr, image);
    }

    public Object GetPaymentCheck(String[] arrStr) {
        return DataController.GetPaymentCheck(arrStr);
    }

    ArrayList<String> ProcessGETData(String query) {
        ArrayList<String> res = new ArrayList<>();
        String[] params = query.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
               res.add(keyValue[1]);
            }
        }
        return res;
    }

    private byte[] readImageBytes() throws IOException {
        File image = GetPersonalImage();
        byte[] imageBytes = new byte[(int) image.length()];
        try (FileInputStream fis = new FileInputStream(image)) {
            fis.read(imageBytes);
        }
        return imageBytes;
    }
    //==============================================================================================
    //==============================================================================================
    //==============================================================================================

}
