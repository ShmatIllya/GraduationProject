package practise;

import DataController.DataControllerSql;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.sql.SQLException;
import java.util.List;

public class WebHandl {
    private DataControllerSql DataController = new DataControllerSql();

    public WebHandl() throws SQLException, ClassNotFoundException {
    }

    public void Receiver(String[] arrStr, PrintWriter out) throws SQLException {
        switch (arrStr[0]) {
            case "GetSomething": {
                GetSomething();
                break;
            }
            case "1": {
                String res = Login(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "0": {
                String res = Registration(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddPersonal": {
                String res = AddPersonal(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }

            case "GetPersonalList": {
                String res = GetPersonalList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }

            case "GetPersonalInfo": {
                String res_str = GetPersonalInfo(arrStr).toString();
                BufferedImage image = (BufferedImage) GetPersonalImage();
                out.write(res_str);
                out.flush();
                try {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "png", byteArrayOutputStream);

                    byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                    MiniServer.socket.getOutputStream().write(size);
                    MiniServer.socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
                    MiniServer.socket.getOutputStream().flush();
                }
                catch (Exception e) {
                    System.out.println(e);
                    throw new RuntimeException(e);
                }

                break;
            }
            case "UpdatePersonalInfo": {
                try {
                    byte[] sizeAr = new byte[4];
                    MiniServer.socket.getInputStream().read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                    byte[] imageAr = new byte[size];
                    DataInputStream in = new DataInputStream(MiniServer.socket.getInputStream());
                    in.readFully(imageAr);
                    //Singleton.getInstance().getIs().read(imageAr);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                    String res = UpdatePersonalInfo(arrStr, image).toString();
                    out.write(res);
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "UpdatePersonalInfoAsManager": {
                String res = UpdatePersonalInfoAsManager(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetClientsList": {
                String res = GetClientsList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetClientInfo": {
                String res = GetClientInfo(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddClient": {
                String res = AddClient(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "UpdateClientInfo": {
                String res = UpdateClientInfo(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetPaymentList": {
                String res = GetPaymentList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetItemsList": {
                String res = GetItemsList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddPayment": {
                String res = AddPayment(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddItem": {
                String res = AddItem(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetPaymentInfo": {
                String res = GetPaymentInfo(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "UpdatePayment": {
                String res = UpdatePayment(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetFullPaymentInfo": {
                String res = GetFullPaymentInfo(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetTasksList": {
                String res = GetTasksList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetPersonalObeyList": {
                String res = GetPersonalObeyList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetPersonalControlList": {
                String res = GetPersonalControlList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddTask": {
                String res = AddTasks(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetProjectList": {
                String res = GetProjectList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetProcessList": {
                String res = GetProcessList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetTaskInfo": {
                String res = GetTaskInfo(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "UpdateTaskInfo": {
                String res = UpdateTaskInfo(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetChatsList": {
                String res_str = GetChatsList(arrStr).toString();
                List<BufferedImage> images = GetChatImageList();
                out.write(res_str);
                out.flush();
                try {
                    for (BufferedImage image: images) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(image, "png", byteArrayOutputStream);

                        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                        MiniServer.socket.getOutputStream().write(size);
                        MiniServer.socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
                        MiniServer.socket.getOutputStream().flush();
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                    throw new RuntimeException(e);
                }

                break;
            }
            case "AddChat": {
                try {
                    byte[] sizeAr = new byte[4];
                    MiniServer.socket.getInputStream().read(sizeAr);
                    int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                    byte[] imageAr = new byte[size];
                    DataInputStream in = new DataInputStream(MiniServer.socket.getInputStream());
                    in.readFully(imageAr);
                    //Singleton.getInstance().getIs().read(imageAr);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
                    String res = AddChat(arrStr, image).toString();
                    out.write(res);
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            case "GetChatMessages": {
                String res_str = GetChatMessages(arrStr).toString();
                List<BufferedImage> images = GetChatImageList();
                List<String> names = GetNameSernameList();
                System.out.println(res_str);
                out.write(res_str);
                out.flush();
                try {
                    int i = 0;
                    for (BufferedImage image: images) {
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(image, "png", byteArrayOutputStream);
                        byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                        MiniServer.socket.getOutputStream().write(size);
                        MiniServer.socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
                        //MiniServer.socket.getOutputStream().write((names.get(i) + "\r").getBytes("UTF-8"));
                        MiniServer.socket.getOutputStream().flush();
                        i++;
                    }
                }
                catch (Exception e) {
                    System.out.println(e);
                    throw new RuntimeException(e);
                }

                break;
            }
            case "GetChatMessagesNames": {
                String res = GetChatMessagesNames(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddMessage": {
                String res = AddMessage(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "DeleteMessageStatus": {
                String res = DeleteMessageStatus(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetProjectsList": {
                String res = GetProjectsList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddProject": {
                String res = AddProject(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetTeamsList": {
                String res = GetTeamsList(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "DeletePersonal": {
                String res = DeletePersonal(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddBusiness": {
                String res = AddBusiness(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "GetBusinessInfo": {
                String res = GetBusinessInfo(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "UpdateBusiness": {
                String res = UpdateBusiness(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "CompleteBusiness": {
                String res = CompleteBusiness(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "DeleteBusiness": {
                String res = DeleteBusiness(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "AddComment": {
                String res = AddComment(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "DeleteClient": {
                String res = DeleteClient(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "ChangePaymentStatus": {
                String res = ChangePaymentStatus(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "DeletePayment": {
                String res = DeletePayment(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "ChangeTaskStatus": {
                String res = ChangeTaskStatus(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            case "DeleteTask": {
                String res = DeleteTask(arrStr).toString();
                out.write(res);
                out.flush();
                break;
            }
            //========================================================================
            //========================================================================
            //========================================================================

        }
    }

    public void GetSomething() {
        Object dataObject = DataController.GetSomething();
    }

    public Object Login(String[] arrStr) throws SQLException {
        return DataController.Login(arrStr);
    }

    public Object Registration(String[] arrStr) {
        return DataController.Registration(arrStr);
    }

    public Object AddPersonal(String[] arrStr) {
        return DataController.AddPersonal(arrStr);
    }

    public Object UpdatePersonalInfo(String[] arrStr, BufferedImage image) {
        return DataController.UpdatePersonalInfo(arrStr, image);
    }

    public Object GetPersonalInfo(String[] arrStr) {
        return DataController.GetPersonalInfo(arrStr);
    }

    public Object GetPersonalImage() {
        return DataController.GetPersonalImage();
    }

    public List<BufferedImage> GetChatImageList() { return DataController.GetChatImageList();}

    public List<String> GetNameSernameList() { return DataController.GetNameSernameList();}

    public Object GetPersonalList(String[] arrStr) {
        return DataController.GetPersonalList(arrStr);
    }

    public Object UpdatePersonalInfoAsManager(String[] arrStr) { return DataController.UpdatePersonalInfoAsManager(arrStr);}

    public Object GetClientsList(String[] arrStr) { return DataController.GetClientsList(arrStr);}

    public Object GetClientInfo(String[] arrStr) { return DataController.GetClientInfo(arrStr);}

    public Object AddClient(String[] arrStr) { return DataController.AddClient(arrStr);}

    public Object UpdateClientInfo(String[] arrStr) { return  DataController.UpdateClientInfo(arrStr);}

    public Object GetPaymentList(String[] arrStr) { return  DataController.GetPaymentList(arrStr);}

    public Object GetItemsList(String[] arrStr) { return  DataController.GetItemsList(arrStr);}

    public Object AddPayment(String[] arrStr) { return  DataController.AddPayment(arrStr);}

    public Object AddItem(String[] arrStr) { return  DataController.AddItem(arrStr);}

    public Object GetPaymentInfo(String[] arrStr) { return  DataController.GetPaymentInfo(arrStr);}

    public Object UpdatePayment(String[] arrStr) { return  DataController.UpdatePayment(arrStr);}

    public Object GetFullPaymentInfo(String[] arrStr) { return  DataController.GetFullPaymentInfo(arrStr);}

    public Object GetTasksList(String[] arrStr) { return  DataController.GetTasksList(arrStr);}

    public Object GetPersonalObeyList(String[] arrStr) { return  DataController.GetPersonalObeyList(arrStr);}

    public Object GetPersonalControlList(String[] arrStr) { return  DataController.GetPersonalControlList(arrStr);}

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
    //==============================================================================================
    //==============================================================================================
    //==============================================================================================

}
