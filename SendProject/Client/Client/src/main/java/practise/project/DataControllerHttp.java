package practise.project;

import DataController.IDataController;
import Network.HttpRestClient;
import Subs.PersonalInfoClass;
import practise.singleton.Singleton;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class DataControllerHttp implements IDataController {
    public Object GetSomething() {
        var jsonData = "params";
        return new Object();
    }

    @Override
    public Object Login(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object Registration(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetPersonalList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddPersonal(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetPersonalInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdatePersonalInfo(String[] arrStr, BufferedImage image) {
        return SendDataWithImage(arrStr, true, image);
    }

    @Override
    public Object UpdatePersonalInfoAsManager(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetClientsList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetClientInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddClient(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateClientInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetPaymentList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetItemsList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddPayment(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddItem(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetPaymentInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdatePayment(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetFullPaymentInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetTasksList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetPersonalObeyList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetPersonalControlList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddTask(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetProjectList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetProcessList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetTaskInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateTaskInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetChatsList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddChat(String[] arrStr, BufferedImage image) {
        return SendDataWithImage(arrStr, true, image);
    }

    @Override
    public Object GetChatMessages(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetChatMessagesNames(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddMessage(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteMessageStatus(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetProjectsList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddProject(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetTeamsList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeletePersonal(String[] arrStr) {
        return SendData(arrStr, true);
    }

    //=============================================================================
    //=============================================================================
    @Override
    public Object AddSeries(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateSeriesPage(String[] arrStr)
    {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateModelPage(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddModel(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddMalCode(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateMalCode(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetSystems(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateCodesPage(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateCausesPage(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddCause(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateCause(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateStepsPage(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddStep(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object SaveActions(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateStep(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateSystemsPage(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddSystem(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateSystem(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteSystem(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateManifestationsPage(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddManifestation(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateManifestation(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteManifestation(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetSystemOnce(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public void SetCauseAllocation(String[] arrStr) {
        SendData(arrStr, false);
    }

    @Override
    public Object GetCauseResult(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteCode(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteCause(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteStep(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteSeries(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateSeries(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteModel(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateModel(String[] arrStr) {
        return SendData(arrStr, true);
    }

    public Object SendData(String[] arrStr, boolean need_answer)
    {
        Object res = "";
        try {
            res = HttpRestClient.Get(Singleton.getInstance().getOs(), Singleton.getInstance().getIs(), arrStr, need_answer);
        } catch (IOException e) {
            System.out.println(e);
        }
        return res;
    }

    public Object SendDataWithImage(String[] arrStr, boolean need_answer, BufferedImage image) {
        Object res = "";
        try {
            res = HttpRestClient.GetWithAdditionalImageSend(Singleton.getInstance().getOs(), Singleton.getInstance().getIs(), arrStr, need_answer, image);
        } catch (IOException e) {
            System.out.println(e);
        }
        return res;
    }
}
