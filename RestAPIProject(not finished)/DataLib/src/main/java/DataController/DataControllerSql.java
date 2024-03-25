package DataController;

import Subs.PersonalInfoClass;
import com.google.protobuf.Message;
import entity.*;
import jakarta.persistence.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class DataControllerSql implements IDataController {
    public static Statement sq;
    JSONObject s_res = new JSONObject();
    Connection db;
    StringBuffer x;
    String causeAllocation = "0";
    EntityManagerFactory managerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;
    File globalImage;
    List<File> globalImageList = new ArrayList<>();
    List<String> globalNameSernameList = new ArrayList<>();
    public DataControllerSql() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        db = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice", "root", "Gfhjkm159357");
        sq = db.createStatement();
        managerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = managerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        x = new StringBuffer();

    }

    public Object GetSomething() {
        return "SELECT ...";
    }

    public JSONObject Registration(JSONObject arrStr) throws JSONException {
        transaction.begin();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        s_res = new JSONObject();
        TypedQuery<PersonalEntity> queryInit = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
        if(queryInit.getResultList().isEmpty()) {
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr.getString("login"));
            personal.setPassword(arrStr.getString("password"));
            personal.setNameSername(arrStr.getString("nameSername"));
            personal.setContacts(arrStr.getString("contacts"));
            personal.setEmail(arrStr.getString("email"));
            personal.setSubrole("Менеджер");
            personal.setRole("control");
            personal.setStatus("Активен");
            personal.setDescription("Отсутствует");
            personal.setImageName("1.png");
            personal.setRegDate(Date.valueOf(LocalDate.parse(arrStr.getString("date"), formatter)));
            entityManager.persist(personal);
            s_res.put("response", "okay");
            transaction.commit();
            return s_res;
        }
        TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login AND e.nameSername =:name and e.contacts =:contacts and e.email =:email", PersonalEntity.class);
        query.setParameter("login", arrStr.getString("login"));
        query.setParameter("name", arrStr.getString("nameSername"));
        query.setParameter("contacts", arrStr.getString("contacts"));
        query.setParameter("email", arrStr.getString("email"));
        List<PersonalEntity> resultPersonal = null;
        resultPersonal = query.getResultList();

        if (resultPersonal.isEmpty()) {
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr.getString("login"));
            personal.setPassword(arrStr.getString("password"));
            personal.setNameSername(arrStr.getString("nameSername"));
            personal.setContacts(arrStr.getString("contacts"));
            personal.setEmail(arrStr.getString("email"));
            personal.setSubrole("Заявка");
            personal.setRole("obey");
            personal.setStatus("Ожидает подтверждения");
            personal.setDescription("Отсутствует");
            personal.setImageName("1.png");
            entityManager.persist(personal);
            s_res.put("response", "okay");
            //==================================Notification==========================
        } else {
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    @Override
    public Object GetChatsList(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddChat(String[] arrStr, BufferedImage image) {
        return null;
    }

    @Override
    public Object GetChatMessages(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetChatMessagesNames(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddMessage(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeleteMessageStatus(String[] arrStr) {
        return null;
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
    public Object DeletePersonal(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddBusiness(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetBusinessInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object UpdateBusiness(String[] arrStr) {
        return null;
    }

    @Override
    public Object CompleteBusiness(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeleteBusiness(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddComment(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeleteClient(String[] arrStr) {
        return null;
    }

    @Override
    public Object ChangePaymentStatus(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeletePayment(String[] arrStr) {
        return null;
    }

    @Override
    public Object ChangeTaskStatus(String[] arrStr) {
        return null;
    }

    @Override
    public Object DeleteTask(String[] arrStr) {
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
    public Object GetPersonalGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetClientGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetPaymentGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTaskGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetProjectGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetBusinessGeneralInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetPaymentList(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetItemsList(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddPayment(String[] arrStr) {
        return null;
    }

    @Override
    public Object AddItem(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetPaymentInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object UpdatePayment(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetFullPaymentInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTasksList(String[] arrStr) {
        return null;
    }
    @Override
    public Object AddTask(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetProjectList(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetProcessList(String[] arrStr) {
        return null;
    }

    @Override
    public Object GetTaskInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object UpdateTaskInfo(String[] arrStr) {
        return null;
    }

    @Override
    public Object CompletePayment(String[] arrStr, BufferedImage image) {
        return null;
    }

    @Override
    public Object GetPaymentCheck(String[] arrStr) {
        return null;
    }


    public JSONObject Login(JSONObject arrStr) throws JSONException {
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e WHERE e.login =:login AND e.password =:password", PersonalEntity.class);
        q.setParameter("login", arrStr.getString("login"));
        q.setParameter("password", arrStr.getString("password"));
        try {
            PersonalEntity entity = q.getSingleResult();
            if (entity.getStatus().equals("Активен")) {
                s_res.put("response", "okay");
                s_res.put("role", entity.getRole());
                s_res.put("nameSername", entity.getNameSername());
            } else {
                s_res.put("response", "null");
            }
        } catch (Exception e) {
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetPersonalList(JSONObject arrStr) throws JSONException {
        s_res = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e", PersonalEntity.class);
        try {
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                s_res.put("nameSername", personal.getNameSername());
                s_res.put("contacts", personal.getContacts());
                s_res.put("email", personal.getEmail());
                s_res.put("subrole", personal.getSubrole());
                s_res.put("status", personal.getStatus());
                s_res.put("login", personal.getLogin());
                s_res.put("password", personal.getPassword());
                jsonArray.put(s_res);
                s_res = new JSONObject();
            }
            s_res.put("response", "okay");
            s_res.put("personalList", jsonArray);
        } catch (Exception e) {
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddPersonal(JSONObject arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login or e.nameSername =:name", PersonalEntity.class);
        query.setParameter("login", arrStr.getString("login"));
        query.setParameter("name", arrStr.getString("nameSername"));
        List<PersonalEntity> resultPersonal = null;
        resultPersonal = query.getResultList();

        if (resultPersonal.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr.getString("login"));
            personal.setPassword(arrStr.getString("password"));
            personal.setNameSername(arrStr.getString("nameSername"));
            personal.setContacts(arrStr.getString("contacts"));
            personal.setEmail(arrStr.getString("email"));
            personal.setSubrole(arrStr.getString("subrole"));
            personal.setRole(arrStr.getString("role"));
            personal.setStatus(arrStr.getString("status"));
            personal.setDescription("Отсутствует");
            personal.setImageName("1.png");
            //personal.setDescription(arrStr[9]);
            personal.setRegDate(Date.valueOf(LocalDate.parse(arrStr.getString("date"), formatter)));
            //personal.setImageName(arrStr[11]);
            entityManager.persist(personal);
            s_res.put("response", "okay");
            //===============================Notification==========================
        } else {
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetPersonalInfo(JSONObject arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        PersonalInfoClass personalInfo = null;
        try {
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr.getString("login"));
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            JSONObject infoS = new JSONObject();
            infoS.put("login", resultPersonal.getLogin());
            infoS.put("password", resultPersonal.getPassword());
            infoS.put("nameSername", resultPersonal.getNameSername());
            infoS.put("contacts", resultPersonal.getContacts());
            infoS.put("email", resultPersonal.getEmail());
            infoS.put("role", resultPersonal.getRole());
            infoS.put("subrole", resultPersonal.getSubrole());
            infoS.put("status", resultPersonal.getStatus());
            infoS.put("description", resultPersonal.getDescription());
            infoS.put("regDate", resultPersonal.getRegDate());
            File image = new File(getClass().getResource("/images/" + resultPersonal.getImageName()).getFile());
            //File image = new File("D:\\FCP\\SEM7\\CURS\\Project\\DataLib\\src\\main\\resources\\images\\1.png");
            s_res = infoS;
            s_res.put("response", "okay");
            globalImage = image;
        }
        catch (Exception e) {
            s_res.put("response", "null");
            e.printStackTrace();
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject UpdatePersonalInfo(JSONObject arrStr, BufferedImage image) throws JSONException{
        try {
            transaction.begin();
            s_res = new JSONObject();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr.getString("login"));
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            //resultPersonal.setLogin(arrStr[11]);
            //resultPersonal.setPassword(arrStr[2]);
            //resultPersonal.setNameSername(arrStr[3]);
            resultPersonal.setContacts(arrStr.getString("contacts"));
            resultPersonal.setEmail(arrStr.getString("email"));
            //resultPersonal.setRole(arrStr[6]);
            //resultPersonal.setSubrole(arrStr[7]);
            //resultPersonal.setStatus(arrStr[8]);
            resultPersonal.setDescription(arrStr.getString("description"));
            //resultPersonal.setRegDate(Date.valueOf(LocalDate.parse(arrStr[10], formatter)));
            Path path = Paths.get(getClass().getResource("/images/" + resultPersonal.getImageName()).toURI());
            if(!resultPersonal.getImageName().equals("1.png")) {
                Files.delete(path);
            }
            String newFileName = CreateImageName();
            resultPersonal.setImageName(newFileName);
            String s = "target/classes/images/" + newFileName;
            ImageIO.write(image, "PNG", new File(s));
            entityManager.merge(resultPersonal);
            s_res.put("response", "okay");
            //================================Notification==========================
        }
        catch (Exception e) {
            e.printStackTrace();
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }
    public File GetPersonalImage() {
        System.out.println(globalImage);
        return globalImage;
    }

    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================
    //=========================================================================================================

    public List<File> GetChatImageList() {
        return globalImageList;
    }

    public List<String> GetNameSernameList() {
        return globalNameSernameList;
    }

    public JSONObject UpdatePersonalInfoAsManager(JSONObject arrStr) throws JSONException {
        try {
            transaction.begin();
            s_res = new JSONObject();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr.getString("login"));
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            resultPersonal.setContacts(arrStr.getString("contacts"));
            resultPersonal.setEmail(arrStr.getString("email"));
            resultPersonal.setRole(arrStr.getString("role"));
            resultPersonal.setSubrole(arrStr.getString("subrole"));
            resultPersonal.setStatus(arrStr.getString("status"));
            entityManager.merge(resultPersonal);
            s_res.put("response", "okay");
            //==============================Notification=====================
        }
        catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetClientsList(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e", ClientsEntity.class);
            List<ClientsEntity> clientsList = new ArrayList<ClientsEntity>();
            clientsList = query.getResultList();
            for (ClientsEntity client : clientsList) {
                JSONObject singleClient = new JSONObject();
                singleClient.put("name", client.getName());
                singleClient.put("type", client.getType());
                singleClient.put("responsibleName", client.getPersonalByResponsableId().getNameSername());
                singleClient.put("id", client.getClientsId());
                jsonArray.put(singleClient);
            }
            s_res.put("clientList", jsonArray);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "okay");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetClientInfo(JSONObject arrStr) throws JSONException {
        try {
            transaction.begin();
            s_res = new JSONObject();
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e WHERE e.clientsId =:id", ClientsEntity.class);
            query.setParameter("id", arrStr.getString("id"));
            ClientsEntity client = new ClientsEntity();
            client = query.getSingleResult();
            s_res.put("name", client.getName());
            s_res.put("responsibleName", client.getPersonalByResponsableId().getNameSername());
            s_res.put("contacts", client.getPhone());
            s_res.put("email", client.getEmail());
            s_res.put("address", client.getAdress());
            s_res.put("description", client.getDescription());
            s_res.put("type", client.getType());
            s_res.put("workType", client.getWork_type());
            s_res.put("regDate", client.getReg_date().toString().split(" ")[0]);
            JSONArray jsonArray = new JSONArray();
            List<TasksEntity> tasksEntityList = (List<TasksEntity>) client.getTasksByClientsId();
            List<BusinessEntity> businessEntityList = (List<BusinessEntity>) client.getBusinessesByClientsId();
            List<ProcessesEntity> processesEntityList = (List<ProcessesEntity>) client.getProcessesByClientsId();
            List<JournalsEntity> journalsEntityList = (List<JournalsEntity>) client.getJournalsByClientsId();
            List<CommentsEntity> commentsEntityList = (List<CommentsEntity>) journalsEntityList.get(0).getCommentsByJournalsId();
            for(TasksEntity task: tasksEntityList) {
                JSONObject taskJSON = new JSONObject();
                taskJSON.put("taskID", task.getTasksId());
                taskJSON.put("taskName", task.getName());
                taskJSON.put("taskStatus", task.getStatus());
                jsonArray.put(taskJSON);
            }
            s_res.put("taskList", jsonArray);
            jsonArray = new JSONArray();
            for(BusinessEntity business: businessEntityList) {
                JSONObject businessJSON = new JSONObject();
                businessJSON.put("businessID", business.getBusinessId());
                businessJSON.put("businessName", business.getName() + " от " + business.getDate().toString().split("T")[0]);
                businessJSON.put("businessStatus", business.getStatus());
                jsonArray.put(businessJSON);
            }
            s_res.put("businessList", jsonArray);
            jsonArray = new JSONArray();
            for(ProcessesEntity process: processesEntityList) {
                JSONObject processJSON = new JSONObject();
                processJSON.put("processID", process.getProcessesId());
                jsonArray.put(processJSON);
            }
            s_res.put("processList", jsonArray);
            jsonArray = new JSONArray();
            for(CommentsEntity comment: commentsEntityList) {
                JSONObject commentJSON = new JSONObject();
                commentJSON.put("commentText", comment.getText());
                commentJSON.put("commentSenderName", comment.getPersonalBySenderId().getNameSername());
                commentJSON.put("commentDate", comment.getDate());
                commentJSON.put("commentSenderLogin", comment.getPersonalBySenderId().getLogin());
                jsonArray.put(commentJSON);
            }
            s_res.put("commentList", jsonArray);
            s_res.put("response", "okay");
        }
        catch (Exception e) {
            s_res.put("response", "null");
            e.printStackTrace();
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddClient(JSONObject arrStr) throws JSONException {
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
        queryP.setParameter("name", arrStr.getString("responsibleName"));
        PersonalEntity pers = queryP.getSingleResult();

        TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name or e.email =:email", ClientsEntity.class);
        query.setParameter("name", arrStr.getString("name"));
        query.setParameter("email", arrStr.getString("email"));
        List<ClientsEntity> resultClients = null;
        resultClients = query.getResultList();

        if (resultClients.isEmpty()) {
            ClientsEntity client = new ClientsEntity();
            client.setName(arrStr.getString("name"));
            client.setEmail(arrStr.getString("email"));
            client.setPhone(arrStr.getString("contacts"));
            client.setAdress(arrStr.getString("address"));
            client.setDescription(arrStr.getString("description"));
            client.setReg_date(Date.valueOf(LocalDate.now()));
            client.setResponsableId(pers.getPersonalId());
            try {
                client.setType(arrStr.getString("type"));
            }
            catch (Exception e) {
                client.setType("Клиент");
            }
            try {
                client.setWork_type(arrStr.getString("workType"));
            }
            catch (Exception e) {
                client.setWork_type("Отсутствует");
            }
            entityManager.persist(client);

            query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name and e.email =:email", ClientsEntity.class);
            query.setParameter("name", arrStr.getString("name"));
            query.setParameter("email", arrStr.getString("email"));
            JournalsEntity journal = new JournalsEntity();
            journal.setClientId(query.getSingleResult().getClientsId());
            entityManager.persist(journal);
            s_res.put("response", "okay");
            //===============================Notification==========================
        } else {
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject UpdateClientInfo(JSONObject arrStr) throws JSONException{
        try {
            transaction.begin();
            s_res = new JSONObject();
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
            queryP.setParameter("name", arrStr.getString("responsibleName"));
            PersonalEntity pers = queryP.getSingleResult();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name and e.email =:email", ClientsEntity.class);
            query.setParameter("name", arrStr.getString("oldName"));
            query.setParameter("email", arrStr.getString("oldEmail"));
            ClientsEntity resultClient = null;
            resultClient = query.getSingleResult();
            resultClient.setName(arrStr.getString("name"));
            resultClient.setPhone(arrStr.getString("contacts"));
            resultClient.setEmail(arrStr.getString("email"));
            resultClient.setAdress(arrStr.getString("address"));
            resultClient.setDescription(arrStr.getString("description"));
            resultClient.setResponsableId(pers.getPersonalId());
            resultClient.setType(arrStr.getString("type"));
            resultClient.setWork_type(arrStr.getString("workType"));
            java.util.Date date = new java.util.Date();
            if(arrStr.getString("date").equals("null")) {
                date = null;
            }
            else {
                date = Date.valueOf(LocalDate.parse(arrStr.getString("date"), formatter));
            }
            resultClient.setReg_date(date);
            entityManager.merge(resultClient);
            s_res.put("response", "okay");
            //================================Notification==========================
        }
        catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetPaymentList(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e", PaymentsEntity.class);
            List<PaymentsEntity> paymentsList = new ArrayList<PaymentsEntity>();
            paymentsList = query.getResultList();
            JSONArray paymentArray = new JSONArray();
            for (PaymentsEntity payment : paymentsList) {
                JSONObject paymentJSON = new JSONObject();
                paymentJSON.put("id", payment.getPaymentId());
                paymentJSON.put("deadline", payment.getDeadline());
                paymentJSON.put("finalPrice", payment.getFinalPrice());
                paymentJSON.put("paymenterName", payment.getClientsByPaymenterId().getName());
                paymentJSON.put("status", payment.getStatus());
                paymentJSON.put("paymenterID", payment.getPaymenterId());
                paymentArray.put(paymentJSON);
            }
            s_res.put("paymentList", paymentArray);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddPayment(JSONObject arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr.getString("paymenterName"));
            PaymentsEntity payment = new PaymentsEntity();
            payment.setCreationDate(Date.valueOf(LocalDate.parse(arrStr.getString("creationDate"), formatter)));
            payment.setDeadline(Date.valueOf(LocalDate.parse(arrStr.getString("deadline"), formatter)));
            payment.setSubInfo(arrStr.getString("subInfo"));
            payment.setPaymenterId(query.getSingleResult().getClientsId());

            query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr.getString("receiverName"));

            payment.setRecieverId(query.getSingleResult().getClientsId());
            payment.setItemId(Integer.parseInt(arrStr.getString("itemID")));
            payment.setAmount(Integer.parseInt(arrStr.getString("amount")));
            payment.setFinalPrice(Integer.parseInt(arrStr.getString("finalPrice")));
            payment.setStatus("Создан");
            entityManager.persist(payment);

            s_res.put("response", "okay");
        }
        catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
            //===============================Notification==========================
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject UpdatePayment(JSONObject arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr.getString("paymenterName"));
            TypedQuery<PaymentsEntity> query2 = entityManager.createQuery("SELECT e from PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query2.setParameter("id", Integer.parseInt(arrStr.getString("id")));
            PaymentsEntity payment = query2.getSingleResult();
            if(payment.getStatus().equals("Закрыт") || payment.getStatus().equals("Отменен"))
            {
                s_res.put("response", "null");
            }
            else {
                payment.setCreationDate(Date.valueOf(LocalDate.parse(arrStr.getString("creationDate"), formatter)));
                payment.setDeadline(Date.valueOf(LocalDate.parse(arrStr.getString("deadline"), formatter)));
                payment.setSubInfo(arrStr.getString("subInfo"));
                payment.setPaymenterId(query.getSingleResult().getClientsId());

                query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
                query.setParameter("name", arrStr.getString("receiverName"));

                payment.setRecieverId(query.getSingleResult().getClientsId());
                payment.setItemId(Integer.parseInt(arrStr.getString("itemID")));
                payment.setAmount(Integer.parseInt(arrStr.getString("amount")));
                payment.setFinalPrice(Integer.parseInt(arrStr.getString("finalPrice")));
                entityManager.merge(payment);

                s_res.put("response", "okay");
            }
        }
        catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        //===============================Notification==========================
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetItemsList(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<ItemsEntity> query = entityManager.createQuery("SELECT e FROM ItemsEntity e", ItemsEntity.class);
            List<ItemsEntity> itemsList = new ArrayList<>();
            itemsList = query.getResultList();
            JSONArray itemArray = new JSONArray();
            for (ItemsEntity item : itemsList) {
                JSONObject itemJSON = new JSONObject();
                itemJSON.put("name", item.getName());
                itemJSON.put("articulate", item.getArticul());
                itemJSON.put("price", item.getPrice());
                itemJSON.put("taxes", item.getTaxes());
                itemJSON.put("measurement", item.getMeasurement());
                itemJSON.put("id", item.getItemId());
                itemArray.put(itemJSON);
            }
            s_res.put("itemList", itemArray);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddItem(JSONObject arrStr) throws JSONException {
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<ItemsEntity> query = entityManager.createQuery("SELECT e FROM ItemsEntity e where e.name =:name", ItemsEntity.class);
        query.setParameter("name", arrStr.getString("name"));
        List<ItemsEntity> resultItems = null;
        resultItems = query.getResultList();

        if (resultItems.isEmpty()) {
            ItemsEntity item = new ItemsEntity();
            item.setName(arrStr.getString("name"));
            item.setArticul(arrStr.getString("articulate"));
            item.setPrice(Integer.parseInt(arrStr.getString("price")));
            item.setTaxes(Integer.parseInt(arrStr.getString("taxes")));
            item.setMeasurement(arrStr.getString("measurement"));
            entityManager.persist(item);
            s_res.put("response", "okay");
            //===============================Notification==========================
        } else {
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetPaymentInfo(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query.setParameter("id", arrStr.getString("id"));
            PaymentsEntity payment = query.getSingleResult();
            s_res.put("creationDate", payment.getCreationDate().toString());
            s_res.put("paymenterName", payment.getClientsByPaymenterId().getName());
            s_res.put("receiverName", payment.getClientsByRecieverId().getName());
            s_res.put("status", payment.getStatus());
            s_res.put("itemID", payment.getItemsByItemId().getItemId());
            s_res.put("itemName", payment.getItemsByItemId().getName());
            s_res.put("measurement", payment.getItemsByItemId().getMeasurement());
            s_res.put("amount", payment.getAmount());
            s_res.put("itemPrice", payment.getItemsByItemId().getPrice());
            s_res.put("taxes", payment.getItemsByItemId().getTaxes());
            s_res.put("finalPrice", payment.getFinalPrice());
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetFullPaymentInfo(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query.setParameter("id", arrStr.getString("id"));
            PaymentsEntity payment = query.getSingleResult();
            s_res.put("creationDate", payment.getCreationDate().toString());
            s_res.put("paymenterName", payment.getClientsByPaymenterId().getName());
            s_res.put("receiverName", payment.getClientsByRecieverId().getName());
            s_res.put("deadline", payment.getDeadline().toString());
            s_res.put("subInfo", payment.getSubInfo());
            s_res.put("itemName", payment.getItemsByItemId().getName());
            s_res.put("measurement", payment.getItemsByItemId().getMeasurement());
            s_res.put("amount", payment.getAmount());
            s_res.put("taxes", payment.getItemsByItemId().getTaxes());
            s_res.put("finalPrice", payment.getFinalPrice());
            s_res.put("id", payment.getPaymentId());
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetTasksList(JSONObject arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        TypedQuery<TasksEntity> q = entityManager.createQuery("SELECT e from TasksEntity e", TasksEntity.class);
        try {
            List<TasksEntity> list = q.getResultList();
            JSONArray taskArray = new JSONArray();
            for (TasksEntity task : list) {
                JSONObject taskJSON = new JSONObject();
                TypedQuery<BusinessEntity> q2 = entityManager.createQuery("SELECT e from BusinessEntity e where " +
                        "e.taskID =:id", BusinessEntity.class);
                q2.setParameter("id", task.getTasksId());
                List<BusinessEntity> businessEntities = q2.getResultList();
                int completeBCount = 0;
                for(BusinessEntity business: businessEntities) {
                    if (!business.getStatus().equals("Активно")) {
                        completeBCount++;
                    }
                }
                taskJSON.put("name", task.getName());
                taskJSON.put("completionInfo", (completeBCount + "/" + businessEntities.size()));
                taskJSON.put("creationDate", task.getCreationDate());
                taskJSON.put("deadline", task.getDeadline());
                taskJSON.put("responsibleName", task.getPersonalByResponsableId().getNameSername());
                taskJSON.put("checkerName", task.getPersonalByCheckerId().getNameSername());
                taskJSON.put("status", task.getStatus());
                taskJSON.put("id", task.getTasksId());
                taskArray.put(taskJSON);
            }
            s_res.put("taskList", taskArray);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetPersonalObeyList(JSONObject arrStr) throws JSONException{
        s_res = new JSONObject();
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e where e.role = 'obey'", PersonalEntity.class);
        try {
            JSONArray personalArray = new JSONArray();
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                JSONObject personalJSON = new JSONObject();
                personalJSON.put("nameSername", personal.getNameSername());
                personalJSON.put("contacts", personal.getContacts());
                personalJSON.put("email", personal.getEmail());
                personalJSON.put("subrole", personal.getSubrole());
                personalJSON.put("status", personal.getStatus());
                personalJSON.put("login", personal.getLogin());
                personalJSON.put("password", personal.getPassword());
                personalArray.put(personalJSON);
            }
            s_res.put("personalList", personalArray);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetPersonalControlList(JSONObject arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e where e.role = 'control'", PersonalEntity.class);
        try {
            JSONArray personalArray = new JSONArray();
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                JSONObject personalJSON = new JSONObject();
                personalJSON.put("nameSername", personal.getNameSername());
                personalJSON.put("contacts", personal.getContacts());
                personalJSON.put("email", personal.getEmail());
                personalJSON.put("subrole", personal.getSubrole());
                personalJSON.put("status", personal.getStatus());
                personalJSON.put("login", personal.getLogin());
                personalJSON.put("password", personal.getPassword());
                personalArray.put(personalJSON);
            }
            s_res.put("personalList", personalArray);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
        }

        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddTask(JSONObject arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name " +
                "and e.personalByResponsableId.nameSername =:responsableName", TasksEntity.class);
        queryT.setParameter("name", arrStr.getString("name"));
        queryT.setParameter("responsableName", arrStr.getString("responsibleName"));
        List<TasksEntity> checkTasks = queryT.getResultList();

        if (checkTasks.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TasksEntity task = new TasksEntity();
            //==============================Data Get==============================
            TypedQuery<PersonalEntity> queryPers = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
            List<PersonalEntity> personalList = queryPers.getResultList();
            int responsable_id = 0, checker_id = 0;
            for(PersonalEntity personal: personalList) {
                if(personal.getNameSername().equals(arrStr.getString("responsibleName"))) {
                    responsable_id = personal.getPersonalId();
                }
                else if (personal.getNameSername().equals(arrStr.getString("checkerName"))) {
                    checker_id = personal.getPersonalId();
                }
            }
            System.out.println("Не выбран");
            if(!arrStr.getString("projectName").equals("Не выбран")) {
                TypedQuery<ProjectsEntity> queryProj = entityManager.createQuery("SELECT e FROM ProjectsEntity e where " +
                        "e.name =:name", ProjectsEntity.class);
                queryProj.setParameter("name", arrStr.getString("projectName"));
                task.setProjectId(queryProj.getSingleResult().getProjectsId());
            }
            if(!arrStr.getString("processName").equals("Не выбран")) {
                System.out.println(arrStr.getString("processName"));
                TypedQuery<ProcessesEntity> queryProc = entityManager.createQuery("SELECT e FROM ProcessesEntity e where " +
                        "e.processesId =:id", ProcessesEntity.class);
                queryProc.setParameter("id", Integer.parseInt(arrStr.getString("processName")));
                task.setProcessId(queryProc.getSingleResult().getProcessesId());
            }
            if(!arrStr.getString("clientName").equals("Не выбран")) {
                TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e where " +
                        "e.name =:name", ClientsEntity.class);
                queryC.setParameter("name", arrStr.getString("clientName"));
                task.setClientId(queryC.getSingleResult().getClientsId());
            }
            //=============================/Data Get==============================
            task.setName(arrStr.getString("name"));
            task.setResponsableId(responsable_id);
            task.setDescription(arrStr.getString("description"));
            task.setCheckerId(checker_id);
            task.setDeadline(Date.valueOf(LocalDate.parse(arrStr.getString("deadline"), formatter2)));
            task.setStatus("Назначена");
            task.setCreationDate(Date.valueOf(LocalDate.parse(arrStr.getString("creationDate"), formatter)));
            entityManager.persist(task);

            queryT = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name" +
                    " and e.responsableId =:id", TasksEntity.class);
            queryT.setParameter("name", arrStr.getString("name"));
            queryT.setParameter("id", responsable_id);
            JournalsEntity journal = new JournalsEntity();
            journal.setTaskId(queryT.getSingleResult().getTasksId());
            entityManager.persist(journal);
            s_res.put("response", "okay");
            //===============================Notification==========================
        } else {
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetProjectList(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<ProjectsEntity> query = entityManager.createQuery("SELECT e FROM ProjectsEntity e", ProjectsEntity.class);
            List<ProjectsEntity> projectsList = new ArrayList<>();
            projectsList = query.getResultList();
            JSONArray projectArray = new JSONArray();
            for (ProjectsEntity project : projectsList) {
                JSONObject projectJSON = new JSONObject();
                projectJSON.put("name", project.getName());
                projectJSON.put("description", project.getDescription());
                projectJSON.put("responsibleID", project.getResponsableId());
                projectJSON.put("deadline", project.getDeadline());
                projectJSON.put("status", project.getStatus());
                projectArray.put(projectJSON);
            }
            s_res.put("projectList", projectArray);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetProcessList(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<ProcessesEntity> query = entityManager.createQuery("SELECT e FROM ProcessesEntity e", ProcessesEntity.class);
            List<ProcessesEntity> processesList = new ArrayList<>();
            processesList = query.getResultList();
            JSONArray processArray = new JSONArray();
            for (ProcessesEntity process : processesList) {
                JSONObject processJSON = new JSONObject();
                processJSON.put("id", process.getProcessesId());
                processJSON.put("status", process.getStatus());
                processJSON.put("clientID", process.getClientId());
                processJSON.put("responsibleID", process.getResponsableId());
                processJSON.put("checkerID", process.getCheckerId());
                processJSON.put("payment", process.getPayment());
                processJSON.put("description", process.getDescription());
                processJSON.put("projectID", process.getProjectId());
                processArray.put(processJSON);
            }
            s_res.put("processList", processArray);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            e.printStackTrace();
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetTaskInfo(JSONObject arrStr) throws JSONException{
        try {
            transaction.begin();
            s_res = new JSONObject();
            TypedQuery<TasksEntity> query = entityManager.createQuery("SELECT e FROM TasksEntity e WHERE e.name =:name and " +
                    "e.personalByResponsableId.nameSername =:responsableName", TasksEntity.class);
            query.setParameter("name", arrStr.getString("name"));
            query.setParameter("responsableName", arrStr.getString("responsibleName"));
            TasksEntity task = new TasksEntity();
            task = query.getSingleResult();
            s_res.put("name", task.getName());
            s_res.put("responsibleName", task.getPersonalByResponsableId().getNameSername());
            s_res.put("description", task.getDescription());
            s_res.put("checkerName", task.getPersonalByCheckerId().getNameSername());
            s_res.put("deadline", task.getDeadline().toString().split(" ")[0]);
            s_res.put("status", task.getStatus());
            s_res.put("creationDate", task.getCreationDate().toString().split(" ")[0]);
            s_res.put("priority", task.getPriority());
            List<BusinessEntity> businessEntityList = (List<BusinessEntity>) task.getBusinessesByTasksId();
            List<JournalsEntity> journalsEntityList = (List<JournalsEntity>) task.getJournalsByTasksId();
            List<CommentsEntity> commentsEntityList = (List<CommentsEntity>) journalsEntityList.get(0).getCommentsByJournalsId();
            JSONArray jsonArray = new JSONArray();
            for(BusinessEntity business: businessEntityList) {
                JSONObject businessJSON = new JSONObject();
                businessJSON.put("businessID", business.getBusinessId());
                businessJSON.put("businessName", business.getName() + " от " + business.getDate().toString().split("T")[0]);
                businessJSON.put("businessStatus", business.getStatus());
                jsonArray.put(businessJSON);
            }
            s_res.put("businessList", jsonArray);
            jsonArray = new JSONArray();
            for(CommentsEntity comment: commentsEntityList) {
                JSONObject commentJSON = new JSONObject();
                commentJSON.put("commentText", comment.getText());
                commentJSON.put("commentSenderName", comment.getPersonalBySenderId().getNameSername());
                commentJSON.put("commentDate", comment.getDate());
                commentJSON.put("commentSenderLogin", comment.getPersonalBySenderId().getLogin());
                jsonArray.put(commentJSON);
            }
            s_res.put("commentList", jsonArray);
            s_res.put("response", "okay");
        }
        catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject UpdateTaskInfo(JSONObject arrStr) throws JSONException{
        try {
            transaction.begin();
            s_res = new JSONObject();
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
            List<PersonalEntity> pers = queryP.getResultList();
            int responsable_id = 0, checker_id = 0;
            for(PersonalEntity personal: pers) {
                if(personal.getNameSername().equals(arrStr.getString("responsibleName"))) {
                    responsable_id = personal.getPersonalId();
                }
                else if (personal.getNameSername().equals(arrStr.getString("checkerName"))) {
                    checker_id = personal.getPersonalId();
                }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<TasksEntity> query = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name and" +
                    " e.personalByResponsableId.nameSername =:responsableName", TasksEntity.class);
            query.setParameter("name", arrStr.getString("oldName"));
            query.setParameter("responsableName", arrStr.getString("oldResponsibleName"));
            TasksEntity resultTask = null;
            resultTask = query.getSingleResult();
            resultTask.setName(arrStr.getString("name"));
            resultTask.setResponsableId(responsable_id);
            resultTask.setDescription(arrStr.getString("description"));
            resultTask.setCheckerId(checker_id);
            resultTask.setPriority(arrStr.getString("priority"));
            java.util.Date date = new java.util.Date();
            if(arrStr.getString("deadline").equals("null")) {
                date = null;
            }
            else {
                date = Date.valueOf(LocalDate.parse(arrStr.getString("deadline"), formatter));
            }
            resultTask.setDeadline((Date) date);
            entityManager.merge(resultTask);
            s_res.put("response", "okay");
            //================================Notification==========================
        }
        catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

//    public Object GetChatsList(String[] arrStr) {
//        try {
//            globalImageList = new ArrayList<>();
//            s_res = "";
//            transaction.begin();
//            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e" +
//                    " inner join ChatMembersEntity b on b.chatId = e.chatsId" +
//                    " where b.personalByPersonalId.nameSername =: nameSername", ChatsEntity.class);
//            query.setParameter("nameSername", arrStr[1]);
//            List<ChatsEntity> chatsList = new ArrayList<>();
//            chatsList = query.getResultList();
//            System.out.println(chatsList.get(0).getName());
//            for (ChatsEntity chat : chatsList) {
//                Iterator<MessagesEntity> iterator = chat.getMessagesByChatsId().iterator();
//                MessagesEntity lastMessage = new MessagesEntity();
//                while (iterator.hasNext()) {
//                    lastMessage = iterator.next();
//                }
//                String senderName = " ", sendTime = " ", sendText = " ";
//                try {
//                    senderName = lastMessage.getPersonalBySenderId().getNameSername();
//                    sendTime = lastMessage.getTime().toString();
//                    sendText = lastMessage.getText();
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                    senderName = " ";
//                }
//                try {
//                    //BufferedImage image = ImageIO.read(new File("/resources/images/" + chat.getImageName()));
//                    BufferedImage image = ImageIO.read(getClass().getResource("/images/" + chat.getImageName()));
//                    globalImageList.add(image);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                    //BufferedImage image = ImageIO.read(new File("/images/1.png"));
//                    BufferedImage image = ImageIO.read(getClass().getResource("/images/1.png"));
//                    globalImageList.add(image);
//                }
//
//                TypedQuery<MessageStatusEntity> query2 = entityManager.createQuery("SELECT COUNT(e)" +
//                        " from MessageStatusEntity e where e.personalByPersonalId.nameSername =:personalId and e.chatId =:chatId" +
//                                " and e.status = 'Не прочитано'", MessageStatusEntity.class);
//
//                query2.setParameter("personalId", arrStr[1]);
//                query2.setParameter("chatId", chat.getChatsId());
//
//                s_res += chat.getName() + "<<" + senderName + "<<" + sendText + "<<" + sendTime
//                        + "<<" + query2.getSingleResult();
//                s_res += ">>";
//
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            e.printStackTrace();
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object AddChat(String[] arrStr, BufferedImage image) {
//        try {
//            transaction.begin();
//            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
//            query.setParameter("name", arrStr[1]);
//            List<ChatsEntity> resultChat = null;
//            resultChat = query.getResultList();
//            if(resultChat.isEmpty()) {
//                ChatsEntity chat = new ChatsEntity();
//                chat.setName(arrStr[1]);
//                chat.setDescription(arrStr[2]);
//
//                String newFileName = CreateImageName();
//                chat.setImageName(newFileName);
//                String s = "target/classes/images/" + newFileName;
//                ImageIO.write(image, "PNG", new File(s));
//                entityManager.persist(chat);
//
//                query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
//                query.setParameter("name", arrStr[1]);
//
//                List<Integer> personalIdArr = new ArrayList<>();
//
//                for(int i = 3; i < arrStr.length - 1; i++) {
//                    TypedQuery<PersonalEntity> query2 = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
//                            " where e.nameSername =:nameSername", PersonalEntity.class);
//                    query2.setParameter("nameSername", arrStr[i]);
//                    ChatMembersEntity entity = new ChatMembersEntity();
//                    entity.setChatId(query.getSingleResult().getChatsId());
//                    entity.setPersonalId(query2.getSingleResult().getPersonalId());
//                    entityManager.persist(entity);
//                }
//                //resultPersonal.setRegDate(Date.valueOf(LocalDate.parse(arrStr[10], formatter)));
//
//                s_res = "success" + "\r";
//            }
//            else {
//                s_res = "null" + "\r";
//            }
//            //================================Notification==========================
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object GetChatMessages(String[] arrStr) {
//        try {
//
//            globalImageList = new ArrayList<>();
//            globalNameSernameList = new ArrayList<>();
//            s_res = "";
//            transaction.begin();
//            TypedQuery<ChatsEntity> queryChat = entityManager.createQuery("SELECT e FROM ChatsEntity e" +
//                    " where e.name =:name", ChatsEntity.class);
//            queryChat.setParameter("name", arrStr[1]);
//
//            //=====================================================================
//            TypedQuery<ChatMembersEntity> queryMembers = entityManager.createQuery("SELECT e FROM ChatMembersEntity e " +
//                    "where e.chatId =:chatId", ChatMembersEntity.class);
//            queryMembers.setParameter("chatId", queryChat.getSingleResult().getChatsId());
//            List<ChatMembersEntity> membersList = queryMembers.getResultList();
//            for(ChatMembersEntity member: membersList) {
//                try {
//                    //BufferedImage image = ImageIO.read(new File("/resources/images/" + chat.getImageName()));
//                    BufferedImage image = ImageIO.read(getClass().getResource(
//                            "/images/"
//                                    + member.getPersonalByPersonalId().getImageName()));
//                    globalImageList.add(image);
//                    globalNameSernameList.add(member.getPersonalByPersonalId().getNameSername());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    //BufferedImage image = ImageIO.read(new File("/images/1.png"));
//                    BufferedImage image = ImageIO.read(getClass().getResource(
//                            "/images/1.png"));
//                    globalImageList.add(image);
//                    globalNameSernameList.add(member.getPersonalByPersonalId().getNameSername());
//                }
//            }
//            //=====================================================================
//
//            TypedQuery<MessagesEntity> query = entityManager.createQuery("SELECT e FROM MessagesEntity e" +
//                    " where e.chatId =:id", MessagesEntity.class);
//            query.setParameter("id", queryChat.getSingleResult().getChatsId());
//            System.out.println(queryChat.getSingleResult().getChatsId() + " eeeeeeeeeeeeeeeeee");
//            List<MessagesEntity> messagesList;
//            messagesList = query.getResultList();
//            String dateChecker = "0";
//            s_res += globalNameSernameList.size();
//            for (MessagesEntity message : messagesList) {
//                if(!message.getDate().toString().equals(dateChecker)) {
//                    s_res += "^^" + message.getDate().toString() + ">>";
//                    dateChecker = message.getDate().toString();
//                }
//                s_res += message.getText() + "<<" + message.getTime() + "<<" + message.getPersonalBySenderId().getNameSername();
//                s_res += ">>";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//            //throw new RuntimeException(e);
//        }
//        transaction.commit();
//        entityManager.clear();
//        s_res += "\r";
//        return s_res;
//    }
//
//    public Object GetChatMessagesNames(String[] arrStr) {
//        s_res = "";
//        for(String i: globalNameSernameList) {
//            s_res += i + ">>";
//        }
//        s_res += "\r";
//        return s_res;
//    }
//
//    public Object AddMessage(String[] arrStr) {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            s_res = "";
//            transaction.begin();
//
//
//            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
//            query.setParameter("name", arrStr[4]);
//            ChatsEntity chat = query.getSingleResult();
//            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
//            queryP.setParameter("name", arrStr[3]);
//            MessagesEntity message = new MessagesEntity();
//            message.setText(arrStr[1]);
//            message.setDate(Date.valueOf(LocalDate.parse(arrStr[2], formatter)));
//            message.setSenderId(queryP.getSingleResult().getPersonalId());
//            message.setChatId(chat.getChatsId());
//            message.setTime(Time.valueOf(arrStr[5]));
//            entityManager.persist(message);
//            entityManager.clear();
//            s_res = "success" + "\r";
//
//            TypedQuery<MessagesEntity> queryM = entityManager.createQuery("SELECT e FROM MessagesEntity e order by e.messagesId desc", MessagesEntity.class);
//            List<MessagesEntity> queryMResult = queryM.getResultList();
//
//            TypedQuery<ChatMembersEntity> queryCM = entityManager.createQuery("SELECT e FROM ChatMembersEntity e where e.chatId =:chatID" +
//                    " and e.personalByPersonalId.nameSername <>:nameSername", ChatMembersEntity.class);
//            queryCM.setParameter("nameSername", arrStr[3]);
//            queryCM.setParameter("chatID", chat.getChatsId());
//            System.out.println(chat.getChatsId());
//            List<ChatMembersEntity> chatMembers = queryCM.getResultList();
//            for(ChatMembersEntity member: chatMembers) {
//                MessageStatusEntity mesStatus = new MessageStatusEntity();
//                mesStatus.setPersonalId(member.getPersonalId());
//                mesStatus.setMessageId(queryMResult.get(0).getMessagesId());
//                mesStatus.setChatId(chat.getChatsId());
//                mesStatus.setStatus("Не прочитано");
//                entityManager.persist(mesStatus);
//            }
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            e.printStackTrace();
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object DeleteMessageStatus(String[] arrStr) {
//        try {
//            s_res = "";
//            transaction.begin();
//            TypedQuery<MessageStatusEntity> query = entityManager.createQuery("SELECT e FROM MessageStatusEntity e" +
//                    " WHERE e.chatByChatId.name = :chatName" +
//                    " and e.personalByPersonalId.nameSername =:nameSername", MessageStatusEntity.class);
//            query.setParameter("chatName", arrStr[1]);
//            query.setParameter("nameSername", arrStr[2]);
//            List<MessageStatusEntity> statusesList = query.getResultList();
//            for(MessageStatusEntity status: statusesList) {
//                entityManager.remove(status);
//            }
//           s_res = "succes" + "\r";
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            e.printStackTrace();
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object GetProjectsList(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        TypedQuery<ProjectsEntity> q = entityManager.createQuery("SELECT e from ProjectsEntity e", ProjectsEntity.class);
//        try {
//            List<ProjectsEntity> list = q.getResultList();
//            for (ProjectsEntity project : list) {
//                TypedQuery<TasksEntity> q2 = entityManager.createQuery("SELECT e from TasksEntity e where " +
//                        "e.projectId =:id", TasksEntity.class);
//                q2.setParameter("id", project.getProjectsId());
//                List<TasksEntity> tasksEntities = q2.getResultList();
//                int completeBCount = 0;
//                for(TasksEntity task: tasksEntities) {
//                    if (!task.getStatus().equals("Назначена")) {
//                        completeBCount++;
//                    }
//                }
//                s_res += project.getName() + "<<" + (completeBCount + "/" + tasksEntities.size()) + "<<"
//                        + project.getCreationDate() + "<<" + project.getDeadline().toString() + "<<"
//                        + project.getProjectMembersByProjectsId().iterator().next().getTeamName() + "<<"
//                        + project.getPersonalByCheckerId().getNameSername() + "<<" + project.getStatus() + "<<" + project.getProjectsId();
//                s_res += ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object AddProject(String[] arrStr) {
//        transaction.begin();
//
//        TypedQuery<ProjectsEntity> queryT = entityManager.createQuery("SELECT e FROM ProjectsEntity e where e.name =:name",
//                ProjectsEntity.class);
//        queryT.setParameter("name", arrStr[1]);
//        List<ProjectsEntity> checkProjects = queryT.getResultList();
//
//        if (checkProjects.isEmpty()) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            ProjectsEntity project = new ProjectsEntity();
//            //==============================Data Get==============================
//            TypedQuery<PersonalEntity> queryPers = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
//            List<PersonalEntity> personalList = queryPers.getResultList();
//            List<Integer> responsable_ids = new ArrayList<>();
//            String[] recieved_names = arrStr[2].split(">>");
//                int checker_id = 0;
//                for (PersonalEntity personal : personalList) {
//                    if(!arrStr[2].equals("Команда существует")) {
//                        for (String i : recieved_names) {
//                            if (personal.getNameSername().equals(i)) {
//                                responsable_ids.add(personal.getPersonalId());
//                            }
//                        }
//                    }
//                    if (personal.getNameSername().equals(arrStr[4])) {
//                        checker_id = personal.getPersonalId();
//                    }
//                }
//            //=============================/Data Get==============================
//            project.setName(arrStr[1]);
//            project.setDescription(arrStr[3]);
//            project.setCheckerId(checker_id);
//            project.setDeadline(Date.valueOf(LocalDate.parse(arrStr[5], formatter)));
//            project.setStatus("Активен");
//            project.setCreationDate(Date.valueOf(LocalDate.now()));
//            entityManager.persist(project);
//
//            queryT = entityManager.createQuery("SELECT e FROM ProjectsEntity e where e.name =:name",
//                    ProjectsEntity.class);
//            queryT.setParameter("name", arrStr[1]);
//            JournalsEntity journal = new JournalsEntity();
//            journal.setProjectId(queryT.getSingleResult().getProjectsId());
//            entityManager.persist(journal);
//
//            if(arrStr[2].equals("Команда существует")) {
//                TypedQuery<ProjectMembersEntity> queryPM = entityManager.createQuery("SELECT e FROM ProjectMembersEntity e" +
//                        " WHERE e.teamName =:teamName", ProjectMembersEntity.class);
//                queryPM.setParameter("teamName", arrStr[6]);
//                List<ProjectMembersEntity> mem = queryPM.getResultList();
//                for(ProjectMembersEntity me: mem) {
//                    ProjectMembersEntity m = new ProjectMembersEntity();
//                    m.setTeamName(me.getTeamName());
//                    m.setPersonalId(me.getPersonalId());
//                    m.setProjectId(queryT.getSingleResult().getProjectsId());
//                    entityManager.persist(m);
//                }
//            }
//            else {
//                for (Integer i : responsable_ids) {
//                    ProjectMembersEntity member = new ProjectMembersEntity();
//                    member.setProjectId(queryT.getSingleResult().getProjectsId());
//                    member.setPersonalId(i);
//                    member.setTeamName(arrStr[6]);
//                    entityManager.persist(member);
//                }
//            }
//            s_res = "1/" + "\r";
//            //===============================Notification==========================
//        } else {
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object GetTeamsList(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        TypedQuery<ProjectMembersEntity> q = entityManager.createQuery("SELECT distinct e.teamName from ProjectMembersEntity e",
//                ProjectMembersEntity.class);
//        try {
//            List<String> list = Collections.singletonList(q.getResultList().toString());
//            String distValue = list.get(0).replaceAll("[{}\\[\\]]", "");
//            String[] finalList = distValue.split(", ");
//            for (String i : finalList) {
//                s_res += i + ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object DeletePersonal(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
//                    " where e.nameSername =:nameSername", PersonalEntity.class);
//            query.setParameter("nameSername", arrStr[1]);
//            PersonalEntity personal = query.getSingleResult();
//            entityManager.remove(personal);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object AddBusiness(String[] arrStr) {
//        s_res = "";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        transaction.begin();
//        try {
//            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
//                    " WHERE e.nameSername =:nameSername", PersonalEntity.class);
//            queryP.setParameter("nameSername", arrStr[6]);
//            PersonalEntity personal = queryP.getSingleResult();
//            BusinessEntity businessEntity = new BusinessEntity();
//            businessEntity.setName(arrStr[1]);
//            businessEntity.setDate(LocalDateTime.parse(arrStr[2], formatter));
//            businessEntity.setStatus(arrStr[3]);
//            switch (arrStr[4]) {
//                case "Клиент": {
//                    TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e" +
//                            " WHERE e.name =:name", ClientsEntity.class);
//                    queryC.setParameter("name", arrStr[5]);
//                    businessEntity.setClientId(queryC.getSingleResult().getClientsId());
//                    break;
//                }
//                case "Процесс": {
//                    businessEntity.setProcessId(Integer.parseInt(arrStr[5]));
//                    break;
//                }
//                case "Проект": {
//                    TypedQuery<ProjectsEntity> queryProj = entityManager.createQuery("SELECT e FROM ProjectsEntity e" +
//                            " WHERE e.name =:name", ProjectsEntity.class);
//                    queryProj.setParameter("name", arrStr[5]);
//                    businessEntity.setProjectID(queryProj.getSingleResult().getProjectsId());
//                    break;
//                }
//                case "Задача": {
//                    TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
//                            " WHERE e.name =:name", TasksEntity.class);
//                    queryT.setParameter("name", arrStr[5]);
//                    businessEntity.setTaskID(queryT.getSingleResult().getTasksId());
//                    break;
//                }
//            }
//            businessEntity.setResponsableId(personal.getPersonalId());
//            entityManager.persist(businessEntity);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object GetBusinessInfo(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            BusinessEntity business = entityManager.getReference(BusinessEntity.class, Integer.parseInt(arrStr[1]));
//            String[] dateSplit = business.getDate().toString().split("T");
//            s_res = business.getName() + ">>" + dateSplit[0] + ">>" + dateSplit[1] + ">>" + business.getDescription()
//                    + ">>" + business.getPlace() + ">>" + business.getPersonalByResponsableId().getNameSername()
//                    + ">>" + business.getStatus() + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object CompleteBusiness(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            BusinessEntity business = entityManager.getReference(BusinessEntity.class, Integer.parseInt(arrStr[1]));
//            business.setStatus("Завершено");
//            entityManager.merge(business);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object DeleteBusiness(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            BusinessEntity business = entityManager.getReference(BusinessEntity.class, Integer.parseInt(arrStr[1]));
//            entityManager.remove(business);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object UpdateBusiness(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            BusinessEntity business = entityManager.getReference(BusinessEntity.class, Integer.parseInt(arrStr[1]));
//            business.setDescription(arrStr[2]);
//            business.setPlace(arrStr[3]);
//            entityManager.merge(business);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object AddComment(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            TypedQuery<JournalsEntity> queryJ;
//            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
//                    " where e.nameSername =:nameSername", PersonalEntity.class);
//            queryP.setParameter("nameSername", arrStr[1]);
//            int personalId = queryP.getSingleResult().getPersonalId();
//            CommentsEntity comment = new CommentsEntity();
//            switch (arrStr[4]) {
//                case "Клиент": {
//                    TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e" +
//                            " where e.name =:name", ClientsEntity.class);
//                    queryC.setParameter("name", arrStr[3]);
//                    queryJ = entityManager.createQuery("SELECT e FROM JournalsEntity e" +
//                            " where e.clientId =:id", JournalsEntity.class);
//                    queryJ.setParameter("id", queryC.getSingleResult().getClientsId());
//                    comment.setDate(Date.valueOf(LocalDate.now()));
//                    comment.setText(arrStr[2]);
//                    comment.setSenderId(personalId);
//                    comment.setJournalId(queryJ.getSingleResult().getJournalsId());
//                    break;
//                }
//                case "Задача": {
//                    TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
//                            " where e.name =:name", TasksEntity.class);
//                    queryT.setParameter("name", arrStr[3]);
//                    queryJ = entityManager.createQuery("SELECT e FROM JournalsEntity e" +
//                            " where e.taskId =:id", JournalsEntity.class);
//                    queryJ.setParameter("id", queryT.getSingleResult().getTasksId());
//                    comment.setDate(Date.valueOf(LocalDate.now()));
//                    comment.setText(arrStr[2]);
//                    comment.setSenderId(personalId);
//                    comment.setJournalId(queryJ.getSingleResult().getJournalsId());
//                    break;
//                }
//                case "Проект": {
//                    break;
//                }
//                case "Процесс": {
//                    break;
//                }
//            }
//           entityManager.persist(comment);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object DeleteClient(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            ClientsEntity client = entityManager.getReference(ClientsEntity.class, Integer.parseInt(arrStr[1]));
//            entityManager.remove(client);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object ChangePaymentStatus(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            PaymentsEntity payment = entityManager.getReference(PaymentsEntity.class, Integer.parseInt(arrStr[1]));
//            payment.setStatus(arrStr[2]);
//            entityManager.merge(payment);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object DeletePayment(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            PaymentsEntity payment = entityManager.getReference(PaymentsEntity.class, Integer.parseInt(arrStr[1]));
//            if(!payment.getPaymentImageName().isEmpty()) {
//                Files.delete(Paths.get("target/classes/images/checks/" + payment.getPaymentImageName()));
//            }
//
//
//            entityManager.remove(payment);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object ChangeTaskStatus(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
//                    " WHERE e.name =:name AND e.personalByResponsableId.nameSername =:nameSername", TasksEntity.class);
//            queryT.setParameter("name", arrStr[1]);
//            queryT.setParameter("nameSername", arrStr[2]);
//            TasksEntity task = queryT.getSingleResult();
//            task.setStatus(arrStr[3]);
//            entityManager.merge(task);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object DeleteTask(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
//                    " WHERE e.name =:name AND e.personalByResponsableId.nameSername =:nameSername", TasksEntity.class);
//            queryT.setParameter("name", arrStr[1]);
//            queryT.setParameter("nameSername", arrStr[2]);
//            TasksEntity task = queryT.getSingleResult();
//            entityManager.remove(task);
//            s_res = "success" + "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object GetProjectInfo(String[] arrStr) {
//        try {
//            transaction.begin();
//            TypedQuery<ProjectsEntity> query = entityManager.createQuery("SELECT e FROM ProjectsEntity e WHERE e.name =:name", ProjectsEntity.class);
//            query.setParameter("name", arrStr[1]);
//            ProjectsEntity project;
//            project = query.getSingleResult();
//            TypedQuery<ProjectMembersEntity> queryPM = entityManager.createQuery("SELECT e FROM ProjectMembersEntity e" +
//                    " WHERE e.projectsByProjectId.name =:name", ProjectMembersEntity.class);
//            queryPM.setParameter("name", arrStr[1]);
//            List<ProjectMembersEntity> projectMembers = queryPM.setMaxResults(1).getResultList();
//            s_res = project.getName() + "<<" + project.getDescription() + "<<" + project.getDeadline() +
//                    "<<" + project.getStatus() + "<<" + project.getCreationDate() + "<<" + project.getTrudozatraty() +
//                    "<<" + project.getStartControl() + "<<" + project.getEnd_control() + "<<"
//                    + project.getPersonalByCheckerId().getNameSername() + "<<" + project.getPlan_control() +
//                    "<<" + project.getIzm() + "<<" + projectMembers.get(0).getTeamName() + ">>";
//            List<TasksEntity> tasksEntityList = (List<TasksEntity>) project.getTasksByProjectsId();
//            List<BusinessEntity> businessEntityList = (List<BusinessEntity>) project.getBusinessByProjectsId();
//            List<JournalsEntity> journalsEntityList = (List<JournalsEntity>) project.getJournalsByProjectsId();
//            List<CommentsEntity> commentsEntityList = (List<CommentsEntity>) journalsEntityList.get(0).getCommentsByJournalsId();
//            for(TasksEntity task: tasksEntityList) {
//                s_res += task.getTasksId() + "^^" + task.getName() + "^^" + task.getStatus() + "<<";
//            }
//            s_res += ">>";
//            for(BusinessEntity business: businessEntityList) {
//                s_res += business.getBusinessId() + "^^" + business.getName() + " от "
//                        + business.getDate().toString().split("T")[0] + "^^" + business.getStatus() + "<<";
//            }
//            s_res += ">>";
//            for(CommentsEntity comment: commentsEntityList) {
//                s_res += comment.getText() + "^^" + comment.getPersonalBySenderId().getNameSername() + "^^" + comment.getDate() + "^^" + comment.getPersonalBySenderId().getLogin() + "<<";
//            }
//            s_res += ">>";
//            s_res += "\r";
//        }
//        catch (Exception e) {
//            s_res = "null" + "\r";
//            e.printStackTrace();
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object GetTeamMembersList(String[] arrStr) {
//        s_res = "";
//        transaction.begin();
//        try {
//            TypedQuery<ProjectMembersEntity> query = entityManager.createQuery("SELECT distinct" +
//                    " e.personalByPersonalId.nameSername FROM ProjectMembersEntity e" +
//                    " WHERE e.teamName =:name", ProjectMembersEntity.class);
//            query.setParameter("name", arrStr[1]);
//            List<String> list = Collections.singletonList(query.getResultList().toString());
//            String distValue = list.get(0).replaceAll("[{}\\[\\]]", "");
//            String[] finalList = distValue.split(", ");
//            for (String i : finalList) {
//                s_res += i + ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            e.printStackTrace();
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object GetPersonalGeneralInfo(String[] arrStr) {
//        try {
//            s_res = "";
//            transaction.begin();
//            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
//            List<PersonalEntity> personalList;
//            personalList = query.getResultList();
//            for (PersonalEntity personal : personalList) {
//                s_res += personal.getNameSername() + "<<" + personal.getRole() + "<<" + personal.getSubrole() +
//                        "<<" + personal.getStatus() +
//                        "<<" + personal.getBusinessesByPersonalId().size() + "<<" + personal.getClientsByPersonalId().size() +
//                        "<<" + personal.getProjectsByPersonalId().size() + "<<" + personal.getTasksByPersonalId().size();
//                s_res += ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            throw new RuntimeException(e);
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
//    public Object GetClientGeneralInfo(String[] arrStr) {
//        try {
//            s_res = "";
//            transaction.begin();
//            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e", ClientsEntity.class);
//            List<ClientsEntity> clientList;
//            clientList = query.getResultList();
//            for (ClientsEntity client : clientList) {
//                s_res += client.getName() + "<<" + client.getType() + "<<" + client.getWork_type() +
//                        "<<" + client.getBusinessesByClientsId().size() +
//                        "<<" + client.getPaymentsByClientsId().size() + "<<" + client.getTasksByClientsId().size();
//                s_res += ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            throw new RuntimeException(e);
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//    public Object GetPaymentGeneralInfo(String[] arrStr) {
//        try {
//            s_res = "";
//            transaction.begin();
//            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e", PaymentsEntity.class);
//            List<PaymentsEntity> paymentList;
//            paymentList = query.getResultList();
//            for (PaymentsEntity payment : paymentList) {
//                s_res += payment.getPaymentId() + "<<" + payment.getFinalPrice() + "<<" + payment.getStatus() +
//                        "<<" + payment.getItemsByItemId().getName();
//                s_res += ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            throw new RuntimeException(e);
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//    public Object GetTaskGeneralInfo(String[] arrStr) {
//        try {
//            s_res = "";
//            transaction.begin();
//            TypedQuery<TasksEntity> query = entityManager.createQuery("SELECT e FROM TasksEntity e", TasksEntity.class);
//            List<TasksEntity> taskList;
//            taskList = query.getResultList();
//            for (TasksEntity task : taskList) {
//                s_res += task.getName() + "<<" + task.getStatus() +
//                        "<<" + task.getPriority();
//                s_res += ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            throw new RuntimeException(e);
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//    public Object GetProjectGeneralInfo(String[] arrStr) {
//
//
//        try {
//            s_res = "";
//            transaction.begin();
//            TypedQuery<ProjectsEntity> query = entityManager.createQuery("SELECT e FROM ProjectsEntity e", ProjectsEntity.class);
//            List<ProjectsEntity> projectList;
//            projectList = query.getResultList();
//            for (ProjectsEntity project : projectList) {
//                s_res += project.getName() + "<<" + project.getStatus() +
//                        "<<" + project.getTrudozatraty() +
//                        "<<" + project.getProjectMembersByProjectsId().size() + "<<" + project.getTasksByProjectsId().size();
//                s_res += ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            throw new RuntimeException(e);
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//    public Object GetBusinessGeneralInfo(String[] arrStr) {
//        try {
//            s_res = "";
//            transaction.begin();
//            TypedQuery<BusinessEntity> query = entityManager.createQuery("SELECT e FROM BusinessEntity e", BusinessEntity.class);
//            List<BusinessEntity> businessList;
//            businessList = query.getResultList();
//            for (BusinessEntity business : businessList) {
//                s_res += business.getName() + "<<" + business.getStatus();
//                s_res += ">>";
//            }
//            s_res += "\r";
//        } catch (Exception e) {
//            s_res = "null" + "\r";
//            throw new RuntimeException(e);
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
    public JSONObject CompletePayment(JSONObject arrStr, BufferedImage image) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            PaymentsEntity payment = entityManager.getReference(PaymentsEntity.class, Integer.parseInt(arrStr.getString("paymentID")));
            payment.setStatus("Оплачен");
            String newFileName = "Payment_" + payment.getPaymentId() + "_Check.png";
            String s = "target/classes/images/checks/" + newFileName;
            ImageIO.write(image, "PNG", new File(s));
            payment.setPaymentImageName(newFileName);
            entityManager.merge(payment);
            s_res.put("response", "okay");

        } catch (Exception e) {
            e.printStackTrace();
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }
    public JSONObject GetPaymentCheck(JSONObject arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            PaymentsEntity payment = entityManager.getReference(PaymentsEntity.class, Integer.parseInt(arrStr.getString("paymentID")));
            System.out.println(payment.getPaymentImageName());
            File image = new File("/images/checks/" + payment.getPaymentImageName());
            globalImage = image;
            s_res.put("response", "okay");

        } catch (Exception e) {
            e.printStackTrace();
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }
    //==============================================================================================
    //==============================================================================================
    //==============================================================================================

    public String CreateImageName() throws IOException, URISyntaxException {
        File dir = new File(getClass().getResource("/images").toURI());
        Random rand = new Random();
        String fileName = String.valueOf(rand.nextInt(100000000)) + ".png";
        for (final File f : dir.listFiles()) {
            // BufferedImage img = null;

            try {
                System.out.println(f.getName());
                if (fileName.equals(f.getName())) {
                    fileName = CreateImageName();
                }
//                    img = ImageIO.read(f);
//
//                    System.out.println("image: " + f.getName());
//                    System.out.println(" width : " + img.getWidth());
//                    System.out.println(" height: " + img.getHeight());
//                    System.out.println(" size  : " + f.length());
            }
            catch (final IOException e) {

            }
        }
        return fileName;
    }
}
