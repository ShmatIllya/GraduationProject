package DataController;

import org.json.JSONException;
import org.json.JSONObject;

import java.awt.image.BufferedImage;

public interface IDataController
{
    public Object GetSomething();

    public JSONObject Login(JSONObject arrStr) throws JSONException;
    public JSONObject Registration(JSONObject arrStr) throws JSONException;
    public JSONObject GetPersonalList(JSONObject arrStr) throws JSONException;
    public JSONObject AddPersonal(JSONObject arrStr) throws JSONException;
    public JSONObject GetPersonalInfo(JSONObject arrStr) throws JSONException;
    public JSONObject UpdatePersonalInfo(JSONObject arrStr, BufferedImage image) throws JSONException;
    public JSONObject UpdatePersonalInfoAsManager(JSONObject arrStr) throws JSONException;
    public JSONObject GetClientsList(JSONObject arrStr) throws JSONException;
    public JSONObject GetClientInfo(JSONObject arrStr) throws JSONException;
    public JSONObject GetPersonalObeyList(JSONObject arrStr) throws JSONException;
    public JSONObject GetPersonalControlList(JSONObject arrStr) throws JSONException;
    public JSONObject AddClient(JSONObject arrStr) throws JSONException;
    public JSONObject UpdateClientInfo(JSONObject arrStr) throws JSONException;
    //====================================================================
//    public JSONObject GetPaymentList(JSONObject arrStr) throws JSONException;
//    public JSONObject GetItemsList(JSONObject arrStr) throws JSONException;
//    public JSONObject AddPayment(JSONObject arrStr) throws JSONException;
//    public JSONObject AddItem(JSONObject arrStr) throws JSONException;
//    public JSONObject GetPaymentInfo(JSONObject arrStr) throws JSONException;
//    public JSONObject UpdatePayment(JSONObject arrStr) throws JSONException;
//    public JSONObject GetFullPaymentInfo(JSONObject arrStr) throws JSONException;
//    public JSONObject GetTasksList(JSONObject arrStr) throws JSONException;
//    public JSONObject AddTask(JSONObject arrStr) throws JSONException;
//    public JSONObject GetProjectList(JSONObject arrStr) throws JSONException;
//    public JSONObject GetProcessList(JSONObject arrStr) throws JSONException;
//    public JSONObject GetTaskInfo(JSONObject arrStr) throws JSONException;
//    public JSONObject UpdateTaskInfo(JSONObject arrStr) throws JSONException;
    //==============================================================
    public Object GetChatsList(String[] arrStr);
    public Object AddChat(String[] arrStr, BufferedImage image);
    public Object GetChatMessages(String[] arrStr);
    public Object GetChatMessagesNames(String[] arrStr);
    public Object AddMessage(String[] arrStr);
    public Object DeleteMessageStatus(String[] arrStr);
    public Object GetProjectsList(String[] arrStr);
    public Object AddProject(String[] arrStr);
    public Object GetTeamsList(String[] arrStr);
    public Object DeletePersonal(String[] arrStr);
    public Object AddBusiness(String[] arrStr);
    public Object GetBusinessInfo(String[] arrStr);
    public Object UpdateBusiness(String[] arrStr);
    public Object CompleteBusiness(String[] arrStr);
    public Object DeleteBusiness(String[] arrStr);
    public Object AddComment(String[] arrStr);
    public Object DeleteClient(String[] arrStr);
    public Object ChangePaymentStatus(String[] arrStr);
    public Object DeletePayment(String[] arrStr);
    public Object ChangeTaskStatus(String[] arrStr);
    public Object DeleteTask(String[] arrStr);
    public Object GetProjectInfo(String[] arrStr);
    public Object GetTeamMembersList(String[] arrStr);
    public Object GetPersonalGeneralInfo(String[] arrStr);
    public Object GetClientGeneralInfo(String[] arrStr);
    public Object GetPaymentGeneralInfo(String[] arrStr);
    public Object GetTaskGeneralInfo(String[] arrStr);
    public Object GetProjectGeneralInfo(String[] arrStr);
    public Object GetBusinessGeneralInfo(String[] arrStr);
//    public JSONObject CompletePayment(JSONObject arrStr, BufferedImage image) throws JSONException;
//    public JSONObject GetPaymentCheck(JSONObject arrStr) throws JSONException;
    //============================================================
    //============================================================
    public Object GetPaymentList(String[] arrStr);
    public Object GetItemsList(String[] arrStr);
    public Object AddPayment(String[] arrStr);
    public Object AddItem(String[] arrStr);
    public Object GetPaymentInfo(String[] arrStr);
    public Object UpdatePayment(String[] arrStr);
    public Object GetFullPaymentInfo(String[] arrStr);
    public Object GetTasksList(String[] arrStr);
    public Object AddTask(String[] arrStr);
    public Object GetProjectList(String[] arrStr);
    public Object GetProcessList(String[] arrStr);
    public Object GetTaskInfo(String[] arrStr);
    public Object UpdateTaskInfo(String[] arrStr);
    public Object CompletePayment(String[] arrStr, BufferedImage image);
    public Object GetPaymentCheck(String[] arrStr);
}