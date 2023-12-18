package DataController;

import java.awt.image.BufferedImage;

public interface IDataController
{
    public Object GetSomething();


    public Object Login(String[] arrStr);
    public Object Registration(String[] arrStr);
    public Object GetPersonalList(String[] arrStr);
    public Object AddPersonal(String[] arrStr);
    public Object GetPersonalInfo(String[] arrStr);
    Object UpdatePersonalInfo(String[] arrStr, BufferedImage image);
    Object UpdatePersonalInfoAsManager(String[] arrStr);
    public Object GetClientsList(String[] arrStr);
    public Object GetClientInfo(String[] arrStr);
    public Object AddClient(String[] arrStr);
    public Object UpdateClientInfo(String[] arrStr);
    public Object GetPaymentList(String[] arrStr);
    public Object GetItemsList(String[] arrStr);
    public Object AddPayment(String[] arrStr);
    public Object AddItem(String[] arrStr);
    public Object GetPaymentInfo(String[] arrStr);
    public Object UpdatePayment(String[] arrStr);
    public Object GetFullPaymentInfo(String[] arrStr);
    public Object GetTasksList(String[] arrStr);
    public Object GetPersonalObeyList(String[] arrStr);
    public Object GetPersonalControlList(String[] arrStr);
    public Object AddTask(String[] arrStr);
    public Object GetProjectList(String[] arrStr);
    public Object GetProcessList(String[] arrStr);
    public Object GetTaskInfo(String[] arrStr);
    public Object UpdateTaskInfo(String[] arrStr);
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
    //============================================================
    //============================================================

}