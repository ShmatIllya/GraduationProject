package DataController;

import Subs.PersonalInfoClass;

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
    //============================================================
    //============================================================
    public Object AddSeries(String[] arrStr);
    public Object UpdateSeriesPage(String[] arrStr);
    public Object UpdateModelPage(String[] arrStr);
    public Object AddModel(String[] arrStr);
    public Object AddMalCode(String[] arrStr);
    public Object UpdateMalCode(String[] arrStr);
    public Object GetSystems(String[] arrStr);
    public Object UpdateCodesPage(String[] arrStr);
    public Object UpdateCausesPage(String[] arrStr);
    public Object AddCause(String[] arrStr);
    public Object UpdateCause(String[] arrStr);
    public Object UpdateStepsPage(String[] arrStr);
    public Object AddStep(String[] arrStr);
    public Object SaveActions(String[] arrStr);
    public Object UpdateStep(String[] arrStr);
    public Object UpdateSystemsPage(String[] arrStr);
    public Object AddSystem(String[] arrStr);
    public Object UpdateSystem(String[] arrStr);
    public Object DeleteSystem(String[] arrStr);
    public Object UpdateManifestationsPage(String[] arrStr);
    public Object AddManifestation(String[] arrStr);
    public Object UpdateManifestation(String[] arrStr);
    public Object DeleteManifestation(String[] arrStr);
    public Object GetSystemOnce(String[] arrStr);
    public void SetCauseAllocation(String[] arrStr);
    public Object GetCauseResult(String[] arrStr);
    public Object DeleteCode(String[] arrStr);
    public Object DeleteCause(String[] arrStr);
    public Object DeleteStep(String[] arrStr);
    public Object DeleteSeries(String[] arrStr);
    public Object UpdateSeries(String[] arrStr);
    public Object DeleteModel(String[] arrStr);
    public Object UpdateModel(String[] arrStr);
}