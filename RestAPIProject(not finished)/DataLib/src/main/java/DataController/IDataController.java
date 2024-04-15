package DataController;

import DTO.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.image.BufferedImage;

public interface IDataController {
    public Object GetSomething();

    public JSONObject Login(PersonalDTO arrStr) throws JSONException;

    public JSONObject Registration(PersonalDTO arrStr) throws JSONException;

    public JSONObject GetPersonalList(JSONObject arrStr) throws JSONException;

    public JSONObject AddPersonal(PersonalDTO arrStr) throws JSONException;

    public JSONObject GetPersonalInfo(PersonalDTO arrStr) throws JSONException;

    public JSONObject UpdatePersonalInfo(PersonalDTO arrStr, BufferedImage image) throws JSONException;

    public JSONObject UpdatePersonalInfoAsManager(PersonalDTO arrStr) throws JSONException;

    public JSONObject GetClientsList(JSONObject arrStr) throws JSONException;

    public JSONObject GetClientInfo(ClientDTO arrStr) throws JSONException;

    public JSONObject GetPersonalObeyList(JSONObject arrStr) throws JSONException;

    public JSONObject GetPersonalControlList(JSONObject arrStr) throws JSONException;

    public JSONObject AddClient(ClientDTO arrStr) throws JSONException;

    public JSONObject UpdateClientInfo(ClientDTO arrStr) throws JSONException;

    public JSONObject GetPaymentList(JSONObject arrStr) throws JSONException;

    public JSONObject GetItemsList(JSONObject arrStr) throws JSONException;

    public JSONObject AddPayment(PaymentDTO arrStr) throws JSONException;

    public JSONObject AddItem(ItemDTO arrStr) throws JSONException;

    public JSONObject GetPaymentInfo(PaymentDTO arrStr) throws JSONException;

    public JSONObject UpdatePayment(PaymentDTO arrStr) throws JSONException;

    public JSONObject GetFullPaymentInfo(PaymentDTO arrStr) throws JSONException;

    public JSONObject GetTasksList(JSONObject arrStr) throws JSONException;

    public JSONObject AddTask(TaskDTO arrStr) throws JSONException;

    public JSONObject GetProjectList(JSONObject arrStr) throws JSONException;

    public JSONObject GetProcessList(JSONObject arrStr) throws JSONException;

    public JSONObject GetTaskInfo(TaskDTO arrStr) throws JSONException;

    public JSONObject UpdateTaskInfo(TaskDTO arrStr) throws JSONException;

    public JSONObject GetChatsList(JSONObject arrStr) throws JSONException;

    public JSONObject AddChat(ChatDTO arrStr, BufferedImage image) throws JSONException;

    public JSONObject GetChatMessages(ChatDTO arrStr) throws JSONException;

    public JSONObject AddMessage(MessageDTO arrStr) throws JSONException;

    public JSONObject DeleteMessageStatus(MessageDTO arrStr) throws JSONException;
//===========================================================================
    //==============================ToBeImplemented=======================================
        public Object GetProjectsList(String[] arrStr);
   public Object AddProject(String[] arrStr);
public Object GetTeamsList(String[] arrStr);
    //==============================/ToBeImplemented=======================================
//===========================================================================
    public JSONObject DeletePersonal(PersonalDTO arrStr) throws JSONException;

    public JSONObject AddBusiness(BusinessDTO arrStr) throws JSONException;

    public JSONObject GetBusinessInfo(BusinessDTO arrStr) throws JSONException;

    public JSONObject UpdateBusiness(BusinessDTO arrStr) throws JSONException;

    public JSONObject CompleteBusiness(BusinessDTO arrStr) throws JSONException;

    public JSONObject DeleteBusiness(BusinessDTO arrStr) throws JSONException;

    public JSONObject AddComment(CommentDTO arrStr) throws JSONException;

    public JSONObject DeleteClient(ClientDTO arrStr) throws JSONException;

    public JSONObject ChangePaymentStatus(PaymentDTO arrStr) throws JSONException;

    public JSONObject DeletePayment(PaymentDTO arrStr) throws JSONException;

    public JSONObject ChangeTaskStatus(TaskDTO arrStr) throws JSONException;

    public JSONObject DeleteTask(TaskDTO arrStr) throws JSONException;

    public JSONObject GetPersonalGeneralInfo(JSONObject arrStr) throws JSONException;

    public JSONObject GetClientGeneralInfo(JSONObject arrStr) throws JSONException;

    public JSONObject GetPaymentGeneralInfo(JSONObject arrStr) throws JSONException;

    public JSONObject GetTaskGeneralInfo(JSONObject arrStr) throws JSONException;

    public JSONObject GetProjectGeneralInfo(JSONObject arrStr) throws JSONException;

    public JSONObject GetBusinessGeneralInfo(JSONObject arrStr) throws JSONException;
    //====================================================================
    //==============================================================
    //==============================ToBeImplemented=======================================
    public Object GetProjectInfo(String[] arrStr);

    public Object GetTeamMembersList(String[] arrStr);

    //==============================/ToBeImplemented=======================================

    public JSONObject CompletePayment(JSONObject arrStr, BufferedImage image) throws JSONException;
    public JSONObject GetPaymentCheck(JSONObject arrStr) throws JSONException;
    //============================================================
    //============================================================
}