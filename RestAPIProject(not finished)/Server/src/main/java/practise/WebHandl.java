package practise;

import DTO.*;
import DataController.DataControllerSql;
import com.logicaldoc.core.imaging.ImageUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import entity.*;
import jakarta.persistence.TypedQuery;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            if (exchange.getRequestMethod().equals("POST")) {
                requestData = new JSONObject(new String(exchange.getRequestBody().readAllBytes()));
                operationID = requestData.getString("operationID");
            } else {

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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetPaymentList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetPaymentList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetItemsList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetItemsList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "AddPayment": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = AddPayment(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "AddItem": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = AddItem(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetPaymentInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("id", requestDataGET.get(1));
                        responseData = GetPaymentInfo(requestData);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "UpdatePayment": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = UpdatePayment(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetFullPaymentInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("id", requestDataGET.get(1));
                        responseData = GetFullPaymentInfo(requestData);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetTasksList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetTasksList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetPersonalObeyList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetPersonalObeyList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
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
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "AddTask": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = AddTasks(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetProjectList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetProjectList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetProcessList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetProcessList(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetTaskInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("name", requestDataGET.get(1));
                        requestData.put("responsibleName", requestDataGET.get(2));
                        responseData = GetTaskInfo(requestData);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "UpdateTaskInfo": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = UpdateTaskInfo(requestData);
                        System.out.println(responseData.getString("response"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetChatsList": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("nameSurname", requestDataGET.get(1));
                        responseData = GetChatsList(requestData);
                        List<File> images = GetChatImageList();
                        for (int i = 0; i < images.size(); i++) {
                            File image = images.get(i);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] imageBytes = Files.readAllBytes(image.toPath());
                            responseData.getJSONArray("chatList").getJSONObject(i).put("image",
                                    Base64.getEncoder().encodeToString(imageBytes));
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }

                break;
            }
            case "AddChat": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        byte[] imageBytes = Base64.getDecoder().decode(requestData.getString("image"));
                        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                        BufferedImage image = ImageIO.read(bais);
                        responseData = AddChat(requestData, image);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetChatMessages": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("name", requestDataGET.get(1));
                        responseData = GetChatMessages(requestData);
                        List<File> images = GetChatImageList();
                        List<String> names = GetNameSernameList();
                        JSONArray imagesList = new JSONArray();
                        for (int i = 0; i < images.size(); i++) {
                            JSONObject tempObj = new JSONObject();
                            tempObj.put("nameSurname", names.get(i));
                            File image = images.get(i);
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            byte[] imageBytes = Files.readAllBytes(image.toPath());
                            tempObj.put("image", Base64.getEncoder().encodeToString(imageBytes));
                            imagesList.put(tempObj);
                        }
                        responseData.put("imagesList", imagesList);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }

                break;
            }
            case "AddMessage": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = AddMessage(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "DeleteMessageStatus": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = DeleteMessageStatus(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
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
            case "DeletePersonal": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = DeletePersonal(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "AddBusiness": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = AddBusiness(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetBusinessInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("id", requestDataGET.get(1));
                        responseData = GetBusinessInfo(requestData);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "UpdateBusiness": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = UpdateBusiness(requestData);
                        System.out.println(responseData.getString("response"));
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "CompleteBusiness": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = CompleteBusiness(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "DeleteBusiness": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = DeleteBusiness(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "AddComment": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = AddComment(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "DeleteClient": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = DeleteClient(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "ChangePaymentStatus": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = ChangePaymentStatus(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "DeletePayment": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = DeletePayment(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "ChangeTaskStatus": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = ChangeTaskStatus(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "DeleteTask": {
                if ("POST".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = DeleteTask(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
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
            case "GetPersonalGeneralInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetPersonalGeneralInfo(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetClientGeneralInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetClientGeneralInfo(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetPaymentGeneralInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetPaymentGeneralInfo(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetTaskGeneralInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetTaskGeneralInfo(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetProjectGeneralInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetProjectGeneralInfo(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "GetBusinessGeneralInfo": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        responseData = GetBusinessGeneralInfo(requestData);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
            case "CompletePayment": {
                try {
                    if ("POST".equals(exchange.getRequestMethod())) {
                        try {
                            byte[] imageBytes = Base64.getDecoder().decode(requestData.getString("image"));
                            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
                            BufferedImage image = ImageIO.read(bais);
                            responseData = CompletePayment(requestData, image);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                        return;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "GetPaymentCheck": {
                if ("GET".equals(exchange.getRequestMethod())) {
                    try {
                        requestData.put("paymentID", requestDataGET.get(1));
                        responseData = GetPaymentCheck(requestData);
//                        byte[] data = readImageBytes();
//                        responseData.put("image", new String(data));
                        File image = GetPersonalImage();
                        byte[] imageBytes = Files.readAllBytes(image.toPath());
                        // Создание JSONObject и добавление PNG изображения как byte[] элемент
                        responseData.put("image", Base64.getEncoder().encodeToString(imageBytes));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    exchange.sendResponseHeaders(405, 0); // Method Not Allowed
                    return;
                }
                break;
            }
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
        PersonalDTO personal = new PersonalDTO();
        personal.setLogin(arrStr.getString("login"));
        personal.setPassword(arrStr.getString("password"));
        return DataController.Login(personal);
    }

    public JSONObject Registration(JSONObject arrStr) throws JSONException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        PersonalDTO personal = new PersonalDTO();
        personal.setLogin(arrStr.getString("login"));
        personal.setPassword(arrStr.getString("password"));
        personal.setNameSername(arrStr.getString("nameSername"));
        personal.setContacts(arrStr.getString("contacts"));
        personal.setEmail(arrStr.getString("email"));
        personal.setRegDate(Date.valueOf(LocalDate.parse(arrStr.getString("date"), formatter)));
        return DataController.Registration(personal);
    }

    public JSONObject AddPersonal(JSONObject arrStr) throws JSONException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        PersonalDTO personal = new PersonalDTO();
        personal.setLogin(arrStr.getString("login"));
        personal.setPassword(arrStr.getString("password"));
        personal.setNameSername(arrStr.getString("nameSername"));
        personal.setContacts(arrStr.getString("contacts"));
        personal.setEmail(arrStr.getString("email"));
        personal.setSubrole(arrStr.getString("subrole"));
        personal.setRole(arrStr.getString("role"));
        personal.setStatus(arrStr.getString("status"));
        personal.setRegDate(Date.valueOf(LocalDate.parse(arrStr.getString("date"), formatter)));
        return DataController.AddPersonal(personal);
    }

    public JSONObject UpdatePersonalInfo(JSONObject arrStr, BufferedImage image) throws JSONException {
        PersonalDTO personal = new PersonalDTO();
        personal.setLogin(arrStr.getString("login"));
        personal.setContacts(arrStr.getString("contacts"));
        personal.setEmail(arrStr.getString("email"));
        personal.setDescription(arrStr.getString("description"));
        return DataController.UpdatePersonalInfo(personal, image);
    }

    public JSONObject GetPersonalInfo(JSONObject arrStr) throws JSONException {
        PersonalDTO personal = new PersonalDTO();
        personal.setLogin(arrStr.getString("login"));
        return DataController.GetPersonalInfo(personal);
    }

    public File GetPersonalImage() {
        return DataController.GetPersonalImage();
    }

    public List<File> GetChatImageList() {
        return DataController.GetChatImageList();
    }

    public List<String> GetNameSernameList() { return DataController.GetNameSernameList();}

    public JSONObject GetPersonalList(JSONObject arrStr) throws JSONException {
        return DataController.GetPersonalList(arrStr);
    }

    public JSONObject UpdatePersonalInfoAsManager(JSONObject arrStr) throws JSONException {
        PersonalDTO personal = new PersonalDTO();
        personal.setLogin(arrStr.getString("login"));
        personal.setContacts(arrStr.getString("contacts"));
        personal.setEmail(arrStr.getString("email"));
        personal.setRole(arrStr.getString("role"));
        personal.setSubrole(arrStr.getString("subrole"));
        personal.setStatus(arrStr.getString("status"));
        return DataController.UpdatePersonalInfoAsManager(personal);
    }

    public JSONObject GetClientsList(JSONObject arrStr) throws JSONException {
        return DataController.GetClientsList(arrStr);
    }

    public JSONObject GetClientInfo(JSONObject arrStr) throws JSONException {
        ClientDTO client = new ClientDTO();
        client.setClientsId(Integer.parseInt(arrStr.getString("id")));
        return DataController.GetClientInfo(client);
    }

    public JSONObject AddClient(JSONObject arrStr) throws JSONException {
        ClientDTO client = new ClientDTO();
        client.setResponsible_name(arrStr.getString("responsibleName"));
        client.setName(arrStr.getString("name"));
        client.setEmail(arrStr.getString("email"));
        client.setPhone(arrStr.getString("contacts"));
        client.setAdress(arrStr.getString("address"));
        client.setDescription(arrStr.getString("description"));
        client.setType(arrStr.getString("type"));
        client.setWork_type(arrStr.getString("workType"));
        return DataController.AddClient(client);
    }

    public JSONObject UpdateClientInfo(JSONObject arrStr) throws JSONException {
        ClientDTO client = new ClientDTO();
        client.setResponsible_name(arrStr.getString("responsibleName"));
        client.setOld_name(arrStr.getString("oldName"));
        client.setOld_email(arrStr.getString("oldEmail"));
        client.setName(arrStr.getString("name"));
        client.setPhone(arrStr.getString("contacts"));
        client.setEmail(arrStr.getString("email"));
        client.setAdress(arrStr.getString("address"));
        client.setDescription(arrStr.getString("description"));
        client.setType(arrStr.getString("type"));
        client.setWork_type(arrStr.getString("workType"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        client.setReg_date(Date.valueOf(LocalDate.parse(arrStr.getString("date"), formatter)));
        return DataController.UpdateClientInfo(client);
    }

    public JSONObject GetPaymentList(JSONObject arrStr) throws JSONException {
        return DataController.GetPaymentList(arrStr);
    }

    public JSONObject GetItemsList(JSONObject arrStr) throws JSONException {
        return DataController.GetItemsList(arrStr);
    }

    public JSONObject AddPayment(JSONObject arrStr) throws JSONException {
        PaymentDTO payment = new PaymentDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        payment.setPaymenterName(arrStr.getString("paymenterName"));
        payment.setCreationDate(Date.valueOf(LocalDate.parse(arrStr.getString("creationDate"), formatter)));
        payment.setDeadline(Date.valueOf(LocalDate.parse(arrStr.getString("deadline"), formatter)));
        payment.setSubInfo(arrStr.getString("subInfo"));
        payment.setReceiverName(arrStr.getString("receiverName"));
        payment.setItemId(Integer.parseInt(arrStr.getString("itemID")));
        payment.setAmount(Integer.parseInt(arrStr.getString("amount")));
        payment.setFinalPrice(Integer.parseInt(arrStr.getString("finalPrice")));
        return DataController.AddPayment(payment);
    }

    public JSONObject AddItem(JSONObject arrStr) throws JSONException {
        ItemDTO item = new ItemDTO();
        item.setName(arrStr.getString("name"));
        item.setArticul(arrStr.getString("articulate"));
        item.setPrice(Integer.parseInt(arrStr.getString("price")));
        item.setTaxes(Integer.parseInt(arrStr.getString("taxes")));
        item.setMeasurement(arrStr.getString("measurement"));
        return DataController.AddItem(item);
    }

    public JSONObject GetPaymentInfo(JSONObject arrStr) throws JSONException {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentId(Integer.parseInt(arrStr.getString("id")));
        return DataController.GetPaymentInfo(payment);
    }

    public JSONObject UpdatePayment(JSONObject arrStr) throws JSONException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymenterName(arrStr.getString("paymenterName"));
        payment.setPaymentId(Integer.parseInt(arrStr.getString("id")));
        payment.setCreationDate(Date.valueOf(LocalDate.parse(arrStr.getString("creationDate"), formatter)));
        payment.setDeadline(Date.valueOf(LocalDate.parse(arrStr.getString("deadline"), formatter)));
        payment.setSubInfo(arrStr.getString("subInfo"));
        payment.setReceiverName(arrStr.getString("receiverName"));
        payment.setItemId(Integer.parseInt(arrStr.getString("itemID")));
        payment.setAmount(Integer.parseInt(arrStr.getString("amount")));
        payment.setFinalPrice(Integer.parseInt(arrStr.getString("finalPrice")));
        return DataController.UpdatePayment(payment);
    }

    public JSONObject GetFullPaymentInfo(JSONObject arrStr) throws JSONException {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentId(arrStr.getInt("id"));
        return DataController.GetFullPaymentInfo(payment);
    }

    public JSONObject GetTasksList(JSONObject arrStr) throws JSONException {
        return DataController.GetTasksList(arrStr);
    }

    public JSONObject GetPersonalObeyList(JSONObject arrStr) throws JSONException {
        return DataController.GetPersonalObeyList(arrStr);
    }

    public JSONObject GetPersonalControlList(JSONObject arrStr) throws JSONException {
        return DataController.GetPersonalControlList(arrStr);
    }

    public JSONObject AddTasks(JSONObject arrStr) throws JSONException {
        TaskDTO task = new TaskDTO();
        task.setName(arrStr.getString("name"));
        task.setResponsibleName(arrStr.getString("responsibleName"));
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        task.setCheckerName(arrStr.getString("checkerName"));
        task.setProjectName(arrStr.getString("projectName"));
        task.setProcessName(arrStr.getString("processName"));
        task.setClientName(arrStr.getString("clientName"));
        task.setDescription(arrStr.getString("description"));
        task.setDeadline(Date.valueOf(LocalDate.parse(arrStr.getString("deadline"), formatter2)));
        task.setCreationDate(LocalDateTime.parse(arrStr.getString("creationDate")));
        return DataController.AddTask(task);
    }

    public JSONObject GetProjectList(JSONObject arrStr) throws JSONException {
        return DataController.GetProjectList(arrStr);
    }

    public JSONObject GetProcessList(JSONObject arrStr) throws JSONException {
        return DataController.GetProcessList(arrStr);
    }

    public JSONObject GetTaskInfo(JSONObject arrStr) throws JSONException {
        TaskDTO task = new TaskDTO();
        task.setName(arrStr.getString("name"));
        task.setResponsibleName(arrStr.getString("responsibleName"));
        return DataController.GetTaskInfo(task);
    }

    public JSONObject UpdateTaskInfo(JSONObject arrStr) throws JSONException {
        TaskDTO task = new TaskDTO();
        task.setResponsibleName(arrStr.getString("responsibleName"));
        task.setCheckerName(arrStr.getString("checkerName"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        task.setOldName(arrStr.getString("oldName"));
        task.setOldResponsibleName(arrStr.getString("oldResponsibleName"));
        task.setName(arrStr.getString("name"));
        task.setDescription(arrStr.getString("description"));
        task.setPriority(arrStr.getString("priority"));
        task.setDeadline(Date.valueOf(LocalDate.parse(arrStr.getString("deadline"), formatter)));
        return DataController.UpdateTaskInfo(task);
    }

    public JSONObject GetChatsList(JSONObject arrStr) throws JSONException {
        return DataController.GetChatsList(arrStr);
    }

    public JSONObject AddChat(JSONObject arrStr, BufferedImage image) throws JSONException {
        ChatDTO chat = new ChatDTO();
        chat.setName(arrStr.getString("name"));
        chat.setDescription(arrStr.getString("description"));
        for (int i = 0; i < arrStr.getJSONArray("membersList").length(); i++) {
            chat.getMembersList().add(arrStr.getJSONArray("membersList").getString(i));
        }
        return DataController.AddChat(chat, image);
    }

    public JSONObject GetChatMessages(JSONObject arrStr) throws JSONException {
        ChatDTO chat = new ChatDTO();
        chat.setName(arrStr.getString("name"));
        return DataController.GetChatMessages(chat);
    }


    public JSONObject AddMessage(JSONObject arrStr) throws JSONException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        MessageDTO message = new MessageDTO();
        message.setChatName(arrStr.getString("chatName"));
        message.setSenderName(arrStr.getString("senderName"));
        message.setText(arrStr.getString("text"));
        message.setDate(Date.valueOf(LocalDate.parse(arrStr.getString("date"), formatter)));
        message.setTime(Time.valueOf(arrStr.getString("time")));

        return DataController.AddMessage(message);
    }

    public JSONObject DeleteMessageStatus(JSONObject arrStr) throws JSONException {
        MessageDTO message = new MessageDTO();
        message.setChatName(arrStr.getString("chatName"));
        message.setSenderName(arrStr.getString("senderName"));
        return DataController.DeleteMessageStatus(message);
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

    public JSONObject DeletePersonal(JSONObject arrStr) throws JSONException {
        PersonalDTO personal = new PersonalDTO();
        personal.setNameSername(arrStr.getString("nameSurname"));
        return DataController.DeletePersonal(personal);
    }

    public JSONObject AddBusiness(JSONObject arrStr) throws JSONException {
        BusinessDTO business = new BusinessDTO();
        business.setResponsibleName(arrStr.getString("responsibleName"));
        business.setName(arrStr.getString("name"));
        business.setDate(LocalDateTime.parse(arrStr.getString("date")));
        business.setType(arrStr.getString("type"));
        business.setLinkedEntityName(arrStr.getString("linkedEntityName"));
        business.setStatus(arrStr.getString("status"));
        return DataController.AddBusiness(business);
    }

    public JSONObject GetBusinessInfo(JSONObject arrStr) throws JSONException {
        BusinessDTO business = new BusinessDTO();
        business.setBusinessId(Integer.parseInt(arrStr.getString("id")));
        return DataController.GetBusinessInfo(business);
    }

    public JSONObject UpdateBusiness(JSONObject arrStr) throws JSONException {
        BusinessDTO business = new BusinessDTO();
        System.out.println(arrStr.getString("id"));
        business.setBusinessId(Integer.parseInt(arrStr.getString("id")));
        business.setDescription(arrStr.getString("description"));
        business.setPlace(arrStr.getString("place"));
        return DataController.UpdateBusiness(business);
    }

    public JSONObject CompleteBusiness(JSONObject arrStr) throws JSONException {
        BusinessDTO business = new BusinessDTO();
        business.setBusinessId(Integer.parseInt(arrStr.getString("id")));
        return DataController.CompleteBusiness(business);
    }

    public JSONObject DeleteBusiness(JSONObject arrStr) throws JSONException {
        BusinessDTO business = new BusinessDTO();
        business.setBusinessId(Integer.parseInt(arrStr.getString("id")));
        return DataController.DeleteBusiness(business);
    }

    public JSONObject AddComment(JSONObject arrStr) throws JSONException {
        CommentDTO comment = new CommentDTO();
        comment.setSenderName(arrStr.getString("senderName"));
        comment.setType(arrStr.getString("type"));
        comment.setLinkedEntityName(arrStr.getString("linkedEntityName"));
        comment.setText(arrStr.getString("text"));
        return DataController.AddComment(comment);
    }

    public JSONObject DeleteClient(JSONObject arrStr) throws JSONException {
        ClientDTO client = new ClientDTO();
        client.setClientsId(Integer.parseInt(arrStr.getString("id")));
        return DataController.DeleteClient(client);
    }

    public JSONObject ChangePaymentStatus(JSONObject arrStr) throws JSONException {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentId(Integer.parseInt(arrStr.getString("id")));
        payment.setStatus(arrStr.getString("status"));
        return DataController.ChangePaymentStatus(payment);
    }

    public JSONObject DeletePayment(JSONObject arrStr) throws JSONException {
        PaymentDTO payment = new PaymentDTO();
        payment.setPaymentId(Integer.parseInt(arrStr.getString("id")));
        return DataController.DeletePayment(payment);
    }

    public JSONObject ChangeTaskStatus(JSONObject arrStr) throws JSONException {
        TaskDTO task = new TaskDTO();
        task.setName(arrStr.getString("name"));
        task.setResponsibleName(arrStr.getString("responsibleName"));
        task.setStatus(arrStr.getString("status"));
        return DataController.ChangeTaskStatus(task);
    }

    public JSONObject DeleteTask(JSONObject arrStr) throws JSONException {
        TaskDTO task = new TaskDTO();
        task.setName(arrStr.getString("name"));
        task.setResponsibleName(arrStr.getString("responsibleName"));
        return DataController.DeleteTask(task);
    }

    public Object GetProjectInfo(String[] arrStr) {
        return DataController.GetProjectInfo(arrStr);
    }

    public Object GetTeamMembersList(String[] arrStr) {
        return DataController.GetTeamMembersList(arrStr);
    }

    public JSONObject GetPersonalGeneralInfo(JSONObject arrStr) throws JSONException {
        return DataController.GetPersonalGeneralInfo(arrStr);
    }

    public JSONObject GetClientGeneralInfo(JSONObject arrStr) throws JSONException {
        return DataController.GetClientGeneralInfo(arrStr);
    }

    public JSONObject GetPaymentGeneralInfo(JSONObject arrStr) throws JSONException {
        return DataController.GetPaymentGeneralInfo(arrStr);
    }

    public JSONObject GetTaskGeneralInfo(JSONObject arrStr) throws JSONException {
        return DataController.GetTaskGeneralInfo(arrStr);
    }

    public JSONObject GetProjectGeneralInfo(JSONObject arrStr) throws JSONException {
        return DataController.GetProjectGeneralInfo(arrStr);
    }

    public JSONObject GetBusinessGeneralInfo(JSONObject arrStr) throws JSONException {
        return DataController.GetBusinessGeneralInfo(arrStr);
    }

    public JSONObject CompletePayment(JSONObject arrStr, BufferedImage image) throws JSONException {
        return DataController.CompletePayment(arrStr, image);
    }

    public JSONObject GetPaymentCheck(JSONObject arrStr) throws JSONException {
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
