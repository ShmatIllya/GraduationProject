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

    @Override
    public Object AddBusiness(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetBusinessInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object UpdateBusiness(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object CompleteBusiness(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteBusiness(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object AddComment(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteClient(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object ChangePaymentStatus(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeletePayment(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object ChangeTaskStatus(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object DeleteTask(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetProjectInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetTeamMembersList(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetPersonalGeneralInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetClientGeneralInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetPaymentGeneralInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetTaskGeneralInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetProjectGeneralInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    @Override
    public Object GetBusinessGeneralInfo(String[] arrStr) {
        return SendData(arrStr, true);
    }

    //=============================================================================
    //=============================================================================


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
