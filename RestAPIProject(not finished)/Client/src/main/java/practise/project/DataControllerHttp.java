package practise.project;

import DTO.*;
import DataController.IDataController;
import Network.HttpRestClient;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.singleton.Singleton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;

public class DataControllerHttp implements IDataController {
    public Object GetSomething() {
        var jsonData = "params";
        return new Object();
    }

    @Override
    public JSONObject Login(PersonalDTO arrStr) throws JSONException {
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("login", arrStr.getLogin());
        sendJSON.put("password", arrStr.getPassword());
        sendJSON.put("operationID", "1");
        String url = "/login?";
        String urlParams = null;
        try {
            urlParams = "operationID=" + sendJSON.getString("operationID") + "&login=" +
                    URLEncoder.encode(sendJSON.getString("login"), StandardCharsets.UTF_8.toString())
                    + "&password=" +
                    URLEncoder.encode(sendJSON.getString("password"), java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        url = url + urlParams;
        return SendData("GET", url, sendJSON, true);
    }

    @Override
    public JSONObject Registration(PersonalDTO arrStr) throws JSONException {
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "0");
        sendJSON.put("login", arrStr.getLogin());
        sendJSON.put("password", arrStr.getPassword());
        sendJSON.put("nameSername", arrStr.getNameSername());
        sendJSON.put("contacts", arrStr.getContacts());
        sendJSON.put("email", arrStr.getEmail());
        sendJSON.put("date", arrStr.getRegDate());
        String url = "/registration";
        return SendData("POST", url, sendJSON, true);
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
    public Object GetProjectInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTeamMembersList(String[] arrStr) {
        return null;
    }

    @Override
    public ArrayList<PersonalDTO> GetPersonalList(JSONObject arrStr) throws JSONException {
        String url = "/personal/getList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        JSONObject resJSON = SendData("GET", url, arrStr, true);
        ArrayList<PersonalDTO> personalArray = new ArrayList<PersonalDTO>();
        if(resJSON.getString("response").equals("null")) {
            personalArray = null;
            return personalArray;
        }
        JSONArray personalJSONArray = resJSON.getJSONArray("personalList");
        for(int i = 0; i < personalJSONArray.length(); i++) {
            JSONObject personalJSON = personalJSONArray.getJSONObject(i);
            PersonalDTO pers = new PersonalDTO();
            pers.setNameSername(personalJSON.getString("nameSername"));
            pers.setContacts(personalJSON.getString("contacts"));
            pers.setEmail(personalJSON.getString("email"));
            pers.setSubrole(personalJSON.getString("subrole"));
            pers.setStatus(personalJSON.getString("status"));
            pers.setLogin(personalJSON.getString("login"));
            pers.setPassword(personalJSON.getString("password"));
            personalArray.add(pers);
        }
        return personalArray;
    }

    @Override
    public JSONObject AddPersonal(PersonalDTO arrStr) throws JSONException {
        String url = "/personal/add";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddPersonal");
        sendJSON.put("login", arrStr.getLogin());
        sendJSON.put("password", arrStr.getPassword());
        sendJSON.put("nameSername", arrStr.getNameSername());
        sendJSON.put("contacts", arrStr.getContacts());
        sendJSON.put("email", arrStr.getEmail());
        sendJSON.put("role", arrStr.getRole());
        sendJSON.put("subrole", arrStr.getSubrole());
        sendJSON.put("status", arrStr.getStatus());
        sendJSON.put("date", arrStr.getRegDate());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public PersonalDTO GetPersonalInfo(PersonalDTO arrStr) throws JSONException {
        String url = "/personal/viewInfo?";
        String urlParams = null;
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "GetPersonalInfo");
        sendJSON.put("login", Singleton.getInstance().getLocalLogin());
        try {
            urlParams = "operationID=" + sendJSON.getString("operationID") + "&login="
                    + URLEncoder.encode(sendJSON.getString("login"), java.nio.charset.StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        url = url + urlParams;
        JSONObject personalJSON = SendData("GET", url, sendJSON, true);
        if(personalJSON.getString("response").equals("null")) {
            return null;
        }
        PersonalDTO personal = new PersonalDTO();
        personal.setLogin(personalJSON.getString("login"));
        personal.setPassword(personalJSON.getString("password"));
        personal.setNameSername(personalJSON.getString("nameSername"));
        personal.setContacts(personalJSON.getString("contacts"));
        personal.setEmail(personalJSON.getString("email"));
        personal.setRole(personalJSON.getString("role"));
        personal.setSubrole(personalJSON.getString("subrole"));
        personal.setStatus(personalJSON.getString("status"));
        personal.setDescription(personalJSON.getString("description"));
        personal.setRegDate(Date.valueOf(personalJSON.getString("regDate")));
        byte[] imageBytes = Base64.getDecoder().decode(personalJSON.getString("image"));
        personal.setAvatarImage(new ByteArrayInputStream(imageBytes));
        return personal;
    }

    @Override
    public JSONObject UpdatePersonalInfo(PersonalDTO arrStr, BufferedImage image) throws JSONException {
        String url = "/personal/updateInfo";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "UpdatePersonalInfo");
        sendJSON.put("login", arrStr.getLogin());
        sendJSON.put("contacts", arrStr.getContacts());
        sendJSON.put("email", arrStr.getEmail());
        sendJSON.put("description", arrStr.getDescription());
        byte[] imageBytes;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            imageBytes = baos.toByteArray();
            baos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sendJSON.put("image", Base64.getEncoder().encodeToString(imageBytes));
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject UpdatePersonalInfoAsManager(PersonalDTO arrStr) throws JSONException {
        String url = "/personal/updateInfoAsManager";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("nameSername", Singleton.getInstance().getFinal_NameSername());
        sendJSON.put("login", arrStr.getLogin());
        sendJSON.put("contacts", arrStr.getContacts());
        sendJSON.put("email", arrStr.getEmail());
        sendJSON.put("role", arrStr.getRole());
        sendJSON.put("subrole", arrStr.getSubrole());
        sendJSON.put("status", arrStr.getStatus());
        sendJSON.put("operationID", "UpdatePersonalInfoAsManager");
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public ArrayList<ClientDTO> GetClientsList(JSONObject arrStr) throws JSONException {
        String url = "/clients/getList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        JSONObject result = SendData("GET", url, arrStr, true);
        if(result.getString("response").equals("null")) {
            return null;
        }
        ArrayList<ClientDTO> clientArray = new ArrayList<>();
        for(int i = 0; i < result.getJSONArray("clientList").length(); i++) {
            JSONObject clientJSON = result.getJSONArray("clientList").getJSONObject(i);
            ClientDTO client = new ClientDTO();
            client.setName(clientJSON.getString("name"));
            client.setType(clientJSON.getString("type"));
            client.setClientsId(Integer.parseInt(clientJSON.getString("id")));
            client.setResponsible_name(clientJSON.getString("responsibleName"));
            clientArray.add(client);
        }
        return clientArray;
    }

    @Override
    public JSONObject GetClientInfo(ClientDTO arrStr) throws JSONException {
        String url = "/clients/viewInfo?";
        String urlParams = null;
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "GetClientInfo");
        sendJSON.put("id", arrStr.getClientsId());
        urlParams = "operationID=" + sendJSON.getString("operationID") + "&id="
                + sendJSON.getString("id");
        url = url + urlParams;
        return SendData("GET", url, sendJSON, true);
    }

    @Override
    public JSONObject AddClient(ClientDTO arrStr) throws JSONException {
        String url = "/clients/add";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddClient");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("email", arrStr.getEmail());
        sendJSON.put("contacts", arrStr.getPhone());
        sendJSON.put("address", arrStr.getAdress());
        sendJSON.put("description", arrStr.getDescription());
        sendJSON.put("responsibleName", arrStr.getResponsible_name());
        sendJSON.put("type", "Клиент");
        sendJSON.put("workType", "Отсутствует");
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject UpdateClientInfo(ClientDTO arrStr) throws JSONException {
        String url = "/clients/update";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "UpdateClientInfo");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("contacts", arrStr.getPhone());
        sendJSON.put("email", arrStr.getEmail());
        sendJSON.put("address", arrStr.getAdress());
        sendJSON.put("description", arrStr.getDescription());
        sendJSON.put("responsibleName", arrStr.getResponsible_name());
        sendJSON.put("type", arrStr.getType());
        sendJSON.put("workType", arrStr.getWork_type());
        sendJSON.put("date", arrStr.getReg_date());
        sendJSON.put("oldName", arrStr.getOld_name());
        sendJSON.put("oldEmail", arrStr.getOld_email());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public ArrayList<PaymentDTO> GetPaymentList(JSONObject arrStr) throws JSONException {
        arrStr.put("operationID", "GetPaymentList");
        String url = "/payments/paymentList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        JSONObject resJSON = SendData("GET", url, arrStr, true);
        if(resJSON.getString("response").equals("null")) {
            return null;
        }
        ArrayList<PaymentDTO> paymentArray = new ArrayList<>();
        for(int i = 0; i < resJSON.getJSONArray("paymentList").length(); i++) {
            JSONObject paymentJSON = resJSON.getJSONArray("paymentList").getJSONObject(i);
            PaymentDTO paymentDTO = new PaymentDTO();
            paymentDTO.setPaymentId(Integer.parseInt(paymentJSON.getString("id")));
            paymentDTO.setDeadline(Date.valueOf(paymentJSON.getString("deadline")));
            paymentDTO.setFinalPrice(paymentJSON.getInt("finalPrice"));
            paymentDTO.setPaymenterName(paymentJSON.getString("paymenterName"));
            paymentDTO.setStatus(paymentJSON.getString("status"));
            paymentDTO.setPaymenterId(paymentJSON.getInt("paymenterID"));
            paymentArray.add(paymentDTO);
        }
        return paymentArray;
    }

    @Override
    public JSONObject GetItemsList(JSONObject arrStr) throws JSONException {
        arrStr.put("operationID", "GetItemsList");
        String url = "/payments/itemList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject AddPayment(PaymentDTO arrStr) throws JSONException {
        String url = "/payments/paymentAdd";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddPayment");
        sendJSON.put("paymenterName", arrStr.getPaymenterName());
        sendJSON.put("creationDate", arrStr.getCreationDate().toString());
        sendJSON.put("deadline", arrStr.getDeadline().toString());
        sendJSON.put("subInfo", arrStr.getSubInfo());
        sendJSON.put("receiverName", arrStr.getReceiverName());
        sendJSON.put("itemID", arrStr.getItemId());
        sendJSON.put("amount", arrStr.getAmount());
        sendJSON.put("finalPrice", arrStr.getFinalPrice());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject AddItem(ItemDTO arrStr) throws JSONException {
        String url = "/payments/itemAdd";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddItem");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("articulate", arrStr.getArticul());
        sendJSON.put("price", arrStr.getPrice());
        sendJSON.put("taxes", arrStr.getTaxes());
        sendJSON.put("measurement", arrStr.getMeasurement());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject GetPaymentInfo(PaymentDTO arrStr) throws JSONException {
        String url = "/payments/getInfo?";
        String urlParams = null;
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "GetPaymentInfo");
        sendJSON.put("id", arrStr.getPaymentId());
        urlParams = "operationID=" + sendJSON.getString("operationID") + "&id="
                + sendJSON.getString("id");
        url = url + urlParams;
        return SendData("GET", url, sendJSON, true);
    }

    @Override
    public JSONObject UpdatePayment(PaymentDTO arrStr) throws JSONException {
        String url = "/payments/update";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "UpdatePayment");
        sendJSON.put("paymenterName", arrStr.getPaymenterName());
        sendJSON.put("id", arrStr.getPaymentId());
        sendJSON.put("creationDate", arrStr.getCreationDate().toString());
        sendJSON.put("deadline", arrStr.getDeadline().toString());
        sendJSON.put("subInfo", arrStr.getSubInfo());
        sendJSON.put("receiverName", arrStr.getReceiverName());
        sendJSON.put("itemID", arrStr.getItemId());
        sendJSON.put("amount", arrStr.getAmount());
        sendJSON.put("finalPrice", arrStr.getFinalPrice());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject GetFullPaymentInfo(PaymentDTO arrStr) throws JSONException {
        String url = "/payments/getFullInfo?";
        String urlParams = null;
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "GetFullPaymentInfo");
        sendJSON.put("id", arrStr.getPaymentId());
        urlParams = "operationID=" + sendJSON.getString("operationID") + "&id="
                + sendJSON.getString("id");
        url = url + urlParams;
        return SendData("GET", url, sendJSON, true);
    }

    @Override
    public JSONObject GetTasksList(JSONObject arrStr) throws JSONException {
        arrStr.put("operationID", "GetTasksList");
        String url = "/tasks/taskList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public ArrayList<PersonalDTO> GetPersonalObeyList(JSONObject arrStr) throws JSONException {
        String url = "/personal/getObeyList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        JSONObject resJSON = SendData("GET", url, arrStr, true);
        if(resJSON.getString("response").equals("null")) {
            return null;
        }
        ArrayList<PersonalDTO> personalList = new ArrayList<>();
        for(int i = 0; i < resJSON.getJSONArray("personalList").length(); i++) {
            JSONObject personalJSON = resJSON.getJSONArray("personalList").getJSONObject(i);
            PersonalDTO personal = new PersonalDTO();
            personal.setNameSername(personalJSON.getString("nameSername"));
            personal.setContacts(personalJSON.getString("contacts"));
            personal.setEmail(personalJSON.getString("email"));
            personal.setSubrole(personalJSON.getString("subrole"));
            personal.setStatus(personalJSON.getString("status"));
            personal.setLogin(personalJSON.getString("login"));
            personal.setPassword(personalJSON.getString("password"));
            personalList.add(personal);
        }
        return personalList;
    }

    @Override
    public ArrayList<PersonalDTO> GetPersonalControlList(JSONObject arrStr) throws JSONException {
        String url = "/personal/getControlList?";
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        JSONObject resJSON = SendData("GET", url, arrStr, true);
        if(resJSON.getString("response").equals("null")) {
            return null;
        }
        ArrayList<PersonalDTO> personalList = new ArrayList<>();
        for(int i = 0; i < resJSON.getJSONArray("personalList").length(); i++) {
            JSONObject personalJSON = resJSON.getJSONArray("personalList").getJSONObject(i);
            PersonalDTO personal = new PersonalDTO();
            personal.setNameSername(personalJSON.getString("nameSername"));
            personal.setContacts(personalJSON.getString("contacts"));
            personal.setEmail(personalJSON.getString("email"));
            personal.setSubrole(personalJSON.getString("subrole"));
            personal.setStatus(personalJSON.getString("status"));
            personal.setLogin(personalJSON.getString("login"));
            personal.setPassword(personalJSON.getString("password"));
            personalList.add(personal);
        }
        return personalList;
    }

    @Override
    public JSONObject AddTask(TaskDTO arrStr) throws JSONException {
        String url = "/tasks/taskAdd";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddTask");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("responsibleName", arrStr.getResponsibleName());
        sendJSON.put("checkerName", arrStr.getCheckerName());
        sendJSON.put("projectName", arrStr.getProjectName());
        sendJSON.put("processName", arrStr.getProcessName());
        sendJSON.put("clientName", arrStr.getClientName());
        sendJSON.put("description", arrStr.getDescription());
        sendJSON.put("deadline", arrStr.getDeadline());
        sendJSON.put("creationDate", arrStr.getCreationDate());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject GetProjectList(JSONObject arrStr) throws JSONException {
        String url = "/projects/getList?";
        arrStr.put("operationID", "GetProjectList");
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetProcessList(JSONObject arrStr) throws JSONException {
        String url = "/processes/getList?";
        arrStr.put("operationID", "GetProcessList");
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetTaskInfo(TaskDTO arrStr) throws JSONException {
        String url = "/tasks/getInfo?";
        String urlParams = null;
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "GetTaskInfo");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("responsibleName", arrStr.getResponsibleName());
        try {
            urlParams = "operationID=" + sendJSON.getString("operationID") + "&name="
                    + URLEncoder.encode(sendJSON.getString("name"), java.nio.charset.StandardCharsets.UTF_8.toString())
                    + "&responsibleName="
                    + URLEncoder.encode(sendJSON.getString("responsibleName"), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        url = url + urlParams;
        return SendData("GET", url, sendJSON, true);
    }

    @Override
    public JSONObject UpdateTaskInfo(TaskDTO arrStr) throws JSONException {
        String url = "/tasks/update";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "UpdateTaskInfo");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("responsibleName", arrStr.getResponsibleName());
        sendJSON.put("description", arrStr.getDescription());
        sendJSON.put("checkerName", arrStr.getCheckerName());
        sendJSON.put("deadline", arrStr.getDeadline().toString());
        sendJSON.put("priority", arrStr.getPriority());
        sendJSON.put("oldName", arrStr.getOldName());
        sendJSON.put("oldResponsibleName", arrStr.getOldResponsibleName());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject GetChatsList(JSONObject arrStr) throws JSONException {
        String url = "/chats/getList?";
        arrStr.put("operationID", "GetChatsList");
        String urlParams = null;
        try {
            urlParams = "operationID=" + arrStr.getString("operationID") + "&nameSurname="
                    + URLEncoder.encode(arrStr.getString("nameSurname"), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject AddChat(ChatDTO arrStr, BufferedImage image) throws JSONException {
        String url = "/chats/chatAdd";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddChat");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("description", arrStr.getDescription());
        JSONArray membersArr = new JSONArray();
        for (int i = 0; i < arrStr.getMembersList().size(); i++) {
            membersArr.put(arrStr.getMembersList().get(i));
        }
        sendJSON.put("membersList", membersArr);
        byte[] imageBytes;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            imageBytes = baos.toByteArray();
            baos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sendJSON.put("image", Base64.getEncoder().encodeToString(imageBytes));
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject GetChatMessages(ChatDTO arrStr) throws JSONException {
        String url = "/chats/getMessages?";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "GetChatMessages");
        sendJSON.put("name", arrStr.getName());
        String urlParams;
        try {
            urlParams = "operationID=" + sendJSON.getString("operationID") + "&name="
                    + URLEncoder.encode(sendJSON.getString("name"), StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        url = url + urlParams;
        return SendData("GET", url, sendJSON, true);
    }

    @Override
    public JSONObject AddMessage(MessageDTO arrStr) throws JSONException {
        String url = "/chats/addMessage";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddMessage");
        sendJSON.put("chatName", arrStr.getChatName());
        sendJSON.put("senderName", arrStr.getSenderName());
        sendJSON.put("text", arrStr.getText());
        sendJSON.put("date", arrStr.getDate().toString());
        sendJSON.put("time", arrStr.getTime().toString());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject DeleteMessageStatus(MessageDTO arrStr) throws JSONException {
        String url = "/chats/deleteMessageStatus";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "DeleteMessageStatus");
        sendJSON.put("chatName", arrStr.getChatName());
        sendJSON.put("senderName", arrStr.getSenderName());
        return SendData("POST", url, sendJSON, true);
    }

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
    @Override
    public JSONObject DeletePersonal(PersonalDTO arrStr) throws JSONException {
        String url = "/personal/delete";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "DeletePersonal");
        sendJSON.put("nameSurname", arrStr.getNameSername());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject AddBusiness(BusinessDTO arrStr) throws JSONException {
        String url = "/business/addBusiness";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddBusiness");
        sendJSON.put("responsibleName", arrStr.getResponsibleName());
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("date", arrStr.getDate().toString());
        sendJSON.put("type", arrStr.getType());
        sendJSON.put("linkedEntityName", arrStr.getLinkedEntityName());
        sendJSON.put("status", arrStr.getStatus());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject GetBusinessInfo(BusinessDTO arrStr) throws JSONException {
        String url = "/business/getInfo?";
        String urlParams;
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "GetBusinessInfo");
        sendJSON.put("id", arrStr.getBusinessId());
        urlParams = "operationID=" + sendJSON.getString("operationID") + "&id=" +
                sendJSON.getString("id");
        url = url + urlParams;
        return SendData("GET", url, sendJSON, true);
    }

    @Override
    public JSONObject UpdateBusiness(BusinessDTO arrStr) throws JSONException {
        String url = "/business/update";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "UpdateBusiness");
        sendJSON.put("id", arrStr.getBusinessId());
        sendJSON.put("description", arrStr.getDescription());
        sendJSON.put("place", arrStr.getPlace());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject CompleteBusiness(BusinessDTO arrStr) throws JSONException {
        String url = "/business/complete";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "CompleteBusiness");
        sendJSON.put("id", arrStr.getBusinessId());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject DeleteBusiness(BusinessDTO arrStr) throws JSONException {
        String url = "/business/delete";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "DeleteBusiness");
        sendJSON.put("id", arrStr.getBusinessId());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject AddComment(CommentDTO arrStr) throws JSONException {
        String url = "/comments/add";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "AddComment");
        sendJSON.put("senderName", arrStr.getSenderName());
        sendJSON.put("type", arrStr.getType());
        sendJSON.put("linkedEntityName", arrStr.getLinkedEntityName());
        sendJSON.put("text", arrStr.getText());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject DeleteClient(ClientDTO arrStr) throws JSONException {
        String url = "/clients/delete";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "DeleteClient");
        sendJSON.put("id", arrStr.getClientsId());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject ChangePaymentStatus(PaymentDTO arrStr) throws JSONException {
        String url = "/payments/changeStatus";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "ChangePaymentStatus");
        sendJSON.put("id", arrStr.getPaymentId());
        sendJSON.put("status", arrStr.getStatus());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject DeletePayment(PaymentDTO arrStr) throws JSONException {
        String url = "/payments/delete";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "DeletePayment");
        sendJSON.put("id", arrStr.getPaymentId());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject ChangeTaskStatus(TaskDTO arrStr) throws JSONException {
        String url = "/tasks/changeStatus";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "ChangeTaskStatus");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("responsibleName", arrStr.getResponsibleName());
        sendJSON.put("status", arrStr.getStatus());
        return SendData("POST", url, sendJSON, true);
    }

    @Override
    public JSONObject DeleteTask(TaskDTO arrStr) throws JSONException {
        String url = "/tasks/delete";
        JSONObject sendJSON = new JSONObject();
        sendJSON.put("operationID", "DeleteTask");
        sendJSON.put("name", arrStr.getName());
        sendJSON.put("responsibleName", arrStr.getResponsibleName());
        return SendData("POST", url, sendJSON, true);
    }

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
    @Override
    public JSONObject GetPersonalGeneralInfo(JSONObject arrStr) throws JSONException {
        String url = "/statistic/getPersonal?";
        arrStr.put("operationID", "GetPersonalGeneralInfo");
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetClientGeneralInfo(JSONObject arrStr) throws JSONException {
        String url = "/statistic/getClients?";
        arrStr.put("operationID", "GetClientGeneralInfo");
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetPaymentGeneralInfo(JSONObject arrStr) throws JSONException {
        String url = "/statistic/getPayments?";
        arrStr.put("operationID", "GetPaymentGeneralInfo");
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetTaskGeneralInfo(JSONObject arrStr) throws JSONException {
        String url = "/statistic/getTasks?";
        arrStr.put("operationID", "GetTaskGeneralInfo");
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetProjectGeneralInfo(JSONObject arrStr) throws JSONException {
        String url = "/statistic/getProjects?";
        arrStr.put("operationID", "GetProjectGeneralInfo");
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject GetBusinessGeneralInfo(JSONObject arrStr) throws JSONException {
        String url = "/statistic/getBusinesses?";
        arrStr.put("operationID", "GetBusinessGeneralInfo");
        String urlParams = "operationID=" + arrStr.getString("operationID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

    @Override
    public JSONObject CompletePayment(JSONObject arrStr, BufferedImage image) throws JSONException {
        String url = "/payments/completePayment";
        arrStr.put("operationID", "CompletePayment");
        byte[] imageBytes;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            baos.flush();
            imageBytes = baos.toByteArray();
            baos.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        arrStr.put("image", Base64.getEncoder().encodeToString(imageBytes));
        return SendData("POST", url, arrStr, true);
    }

    @Override
    public JSONObject GetPaymentCheck(JSONObject arrStr) throws JSONException {
        String url = "/payments/getCheck?";
        String urlParams = null;
        arrStr.put("operationID", "GetPaymentCheck");
        urlParams = "operationID=" + arrStr.getString("operationID") + "&id="
                + arrStr.getString("paymentID");
        url = url + urlParams;
        return SendData("GET", url, arrStr, true);
    }

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
            } else {
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
