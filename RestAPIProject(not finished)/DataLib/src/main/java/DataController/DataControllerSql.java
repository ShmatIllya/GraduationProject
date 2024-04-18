package DataController;

import DTO.*;
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

    public JSONObject Registration(PersonalDTO arrStr) throws JSONException {
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<PersonalEntity> queryInit = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
        if(queryInit.getResultList().isEmpty()) {
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr.getLogin());
            personal.setPassword(arrStr.getPassword());
            personal.setNameSername(arrStr.getNameSername());
            personal.setContacts(arrStr.getContacts());
            personal.setEmail(arrStr.getEmail());
            personal.setRegDate(arrStr.getRegDate());
            personal.setSubrole("Менеджер");
            personal.setRole("control");
            personal.setStatus("Активен");
            personal.setDescription("Отсутствует");
            personal.setImageName("1.png");
            entityManager.persist(personal);
            s_res.put("response", "okay");
            transaction.commit();
            return s_res;
        }
        TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login AND e.nameSername =:name and e.contacts =:contacts and e.email =:email", PersonalEntity.class);
        query.setParameter("login", arrStr.getLogin());
        query.setParameter("name", arrStr.getNameSername());
        query.setParameter("contacts", arrStr.getContacts());
        query.setParameter("email", arrStr.getEmail());
        List<PersonalEntity> resultPersonal = null;
        resultPersonal = query.getResultList();

        if (resultPersonal.isEmpty()) {
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr.getLogin());
            personal.setPassword(arrStr.getPassword());
            personal.setNameSername(arrStr.getNameSername());
            personal.setContacts(arrStr.getContacts());
            personal.setEmail(arrStr.getEmail());
            personal.setRegDate(arrStr.getRegDate());
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


    public JSONObject Login(PersonalDTO arrStr) throws JSONException {
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e WHERE e.login =:login AND e.password =:password", PersonalEntity.class);
        q.setParameter("login", arrStr.getLogin());
        q.setParameter("password", arrStr.getPassword());
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
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public ArrayList<PersonalDTO> GetPersonalList(JSONObject arrStr) throws JSONException {
        ArrayList<PersonalDTO> personalArray = new ArrayList<>();
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e", PersonalEntity.class);
        try {
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                PersonalDTO pers = new PersonalDTO();
                pers.setNameSername(personal.getNameSername());
                pers.setContacts(personal.getContacts());
                pers.setEmail(personal.getEmail());
                pers.setSubrole(personal.getSubrole());
                pers.setStatus(personal.getStatus());
                pers.setLogin(personal.getLogin());
                pers.setPassword(personal.getPassword());
                personalArray.add(pers);
            }
        } catch (Exception e) {
            return null;
        }
        transaction.commit();
        entityManager.clear();
        return personalArray;
    }

    public JSONObject AddPersonal(PersonalDTO arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login or e.nameSername =:name", PersonalEntity.class);
        query.setParameter("login", arrStr.getLogin());
        query.setParameter("name", arrStr.getNameSername());
        List<PersonalEntity> resultPersonal = null;
        resultPersonal = query.getResultList();

        if (resultPersonal.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr.getLogin());
            personal.setPassword(arrStr.getPassword());
            personal.setNameSername(arrStr.getNameSername());
            personal.setContacts(arrStr.getContacts());
            personal.setEmail(arrStr.getEmail());
            personal.setSubrole(arrStr.getSubrole());
            personal.setRole(arrStr.getRole());
            personal.setStatus(arrStr.getStatus());
            personal.setDescription("Отсутствует");
            personal.setImageName("1.png");
            //personal.setDescription(arrStr[9]);
            personal.setRegDate(arrStr.getRegDate());
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

    public PersonalDTO GetPersonalInfo(PersonalDTO arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        PersonalDTO personalInfo = new PersonalDTO();
        try {
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr.getLogin());
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            personalInfo.setLogin(resultPersonal.getLogin());
            personalInfo.setPassword(resultPersonal.getPassword());
            personalInfo.setNameSername(resultPersonal.getNameSername());
            personalInfo.setContacts(resultPersonal.getContacts());
            personalInfo.setEmail(resultPersonal.getEmail());
            personalInfo.setRole(resultPersonal.getRole());
            personalInfo.setSubrole(resultPersonal.getSubrole());
            personalInfo.setStatus(resultPersonal.getStatus());
            personalInfo.setDescription(resultPersonal.getDescription());
            personalInfo.setRegDate(resultPersonal.getRegDate());
            File image = new File(getClass().getResource("/images/" + resultPersonal.getImageName()).getFile());
            //File image = new File("D:\\FCP\\SEM7\\CURS\\Project\\DataLib\\src\\main\\resources\\images\\1.png");
            globalImage = image;
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
        transaction.commit();
        entityManager.clear();
        return personalInfo;
    }

    public JSONObject UpdatePersonalInfo(PersonalDTO arrStr, BufferedImage image) throws JSONException{
        try {
            transaction.begin();
            s_res = new JSONObject();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr.getLogin());
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            //resultPersonal.setLogin(arrStr[11]);
            //resultPersonal.setPassword(arrStr[2]);
            //resultPersonal.setNameSername(arrStr[3]);
            resultPersonal.setContacts(arrStr.getContacts());
            resultPersonal.setEmail(arrStr.getEmail());
            //resultPersonal.setRole(arrStr[6]);
            //resultPersonal.setSubrole(arrStr[7]);
            //resultPersonal.setStatus(arrStr[8]);
            resultPersonal.setDescription(arrStr.getDescription());
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
            System.out.println(e);
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

    public JSONObject UpdatePersonalInfoAsManager(PersonalDTO arrStr) throws JSONException {
        try {
            transaction.begin();
            s_res = new JSONObject();
            System.out.println(arrStr.getLogin() + " !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr.getLogin());
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            resultPersonal.setContacts(arrStr.getContacts());
            resultPersonal.setEmail(arrStr.getEmail());
            resultPersonal.setRole(arrStr.getRole());
            resultPersonal.setSubrole(arrStr.getSubrole());
            resultPersonal.setStatus(arrStr.getStatus());
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

    public ArrayList<ClientDTO> GetClientsList(JSONObject arrStr) throws JSONException {
        ArrayList<ClientDTO> clientArray = new ArrayList<>();
        try {
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e", ClientsEntity.class);
            List<ClientsEntity> clientsList = new ArrayList<ClientsEntity>();
            clientsList = query.getResultList();
            for (ClientsEntity client : clientsList) {
                ClientDTO singleClient = new ClientDTO();
                singleClient.setName(client.getName());
                singleClient.setType(client.getType());
                singleClient.setClientsId(client.getClientsId());
                singleClient.setResponsible_name(client.getPersonalByResponsableId().getNameSername());
                clientArray.add(singleClient);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        transaction.commit();
        entityManager.clear();
        return clientArray;
    }

    public JSONObject GetClientInfo(ClientDTO arrStr) throws JSONException {
        try {
            transaction.begin();
            s_res = new JSONObject();
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e WHERE e.clientsId =:id", ClientsEntity.class);
            query.setParameter("id", arrStr.getClientsId());
            ClientsEntity client = new ClientsEntity();
            client = query.getSingleResult();
//            ClientDTO clientDTO = new ClientDTO();
//            clientDTO.setName(client.getName());
//            clientDTO.setResponsible_name(client.getPersonalByResponsableId().getNameSername());
//            clientDTO.setPhone(client.getPhone());
//            clientDTO.setEmail(client.getEmail());
//            clientDTO.setAdress(client.getAdress());
//            clientDTO.setDescription(client.getDescription());
//            clientDTO.setType(client.getType());
//            clientDTO.setWork_type(client.getWork_type());
//            clientDTO.setReg_date(client.getReg_date());
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
                taskJSON.put("taskResponsibleName", task.getPersonalByResponsableId().getNameSername());
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
            System.out.println(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddClient(ClientDTO arrStr) throws JSONException {
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
        queryP.setParameter("name", arrStr.getResponsible_name());
        PersonalEntity pers = queryP.getSingleResult();
        TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name or e.email =:email", ClientsEntity.class);
        query.setParameter("name", arrStr.getName());
        query.setParameter("email", arrStr.getEmail());
        List<ClientsEntity> resultClients = null;
        resultClients = query.getResultList();

        if (resultClients.isEmpty()) {
            ClientsEntity client = new ClientsEntity();
            client.setName(arrStr.getName());
            client.setEmail(arrStr.getEmail());
            client.setPhone(arrStr.getPhone());
            client.setAdress(arrStr.getAdress());
            client.setDescription(arrStr.getDescription());
            client.setReg_date(Date.valueOf(LocalDate.now()));
            client.setResponsableId(pers.getPersonalId());
            try {
                client.setType(arrStr.getType());
            }
            catch (Exception e) {
                client.setType("Клиент");
            }
            try {
                client.setWork_type(arrStr.getWork_type());
            }
            catch (Exception e) {
                client.setWork_type("Отсутствует");
            }
            entityManager.persist(client);
            query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name and e.email =:email", ClientsEntity.class);
            query.setParameter("name", arrStr.getName());
            query.setParameter("email", arrStr.getEmail());
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

    public JSONObject UpdateClientInfo(ClientDTO arrStr) throws JSONException{
        try {
            transaction.begin();
            s_res = new JSONObject();
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
            queryP.setParameter("name", arrStr.getResponsible_name());
            PersonalEntity pers = queryP.getSingleResult();

            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name and e.email =:email", ClientsEntity.class);
            query.setParameter("name", arrStr.getOld_name());
            query.setParameter("email", arrStr.getOld_email());
            ClientsEntity resultClient = null;
            resultClient = query.getSingleResult();
            resultClient.setName(arrStr.getName());
            resultClient.setPhone(arrStr.getPhone());
            resultClient.setEmail(arrStr.getEmail());
            resultClient.setAdress(arrStr.getAdress());
            resultClient.setDescription(arrStr.getDescription());
            resultClient.setResponsableId(pers.getPersonalId());
            resultClient.setType(arrStr.getType());
            resultClient.setWork_type(arrStr.getWork_type());
            java.util.Date date = new java.util.Date();
            if(arrStr.getReg_date().equals("null")) {
                date = null;
            }
            else {
                date = arrStr.getReg_date();
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

    public ArrayList<PaymentDTO> GetPaymentList(JSONObject arrStr) throws JSONException {
        ArrayList<PaymentDTO> paymentArray = new ArrayList<>();
        try {
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e", PaymentsEntity.class);
            List<PaymentsEntity> paymentsList = new ArrayList<PaymentsEntity>();
            paymentsList = query.getResultList();
            for (PaymentsEntity payment : paymentsList) {
                PaymentDTO paymentDTO = new PaymentDTO();
                paymentDTO.setPaymentId(payment.getPaymentId());
                paymentDTO.setDeadline(payment.getDeadline());
                paymentDTO.setFinalPrice(payment.getFinalPrice());
                paymentDTO.setPaymenterName(payment.getClientsByPaymenterId().getName());
                paymentDTO.setStatus(payment.getStatus());
                paymentDTO.setPaymenterId(payment.getPaymenterId());
                paymentArray.add(paymentDTO);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        transaction.commit();
        entityManager.clear();
        return paymentArray;
    }

    public JSONObject AddPayment(PaymentDTO arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        try {
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr.getPaymenterName());
            PaymentsEntity payment = new PaymentsEntity();
            payment.setCreationDate(arrStr.getCreationDate());
            payment.setDeadline(arrStr.getDeadline());
            payment.setSubInfo(arrStr.getSubInfo());
            payment.setPaymenterId(query.getSingleResult().getClientsId());

            query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr.getReceiverName());

            payment.setRecieverId(query.getSingleResult().getClientsId());
            payment.setItemId(arrStr.getItemId());
            payment.setAmount(arrStr.getAmount());
            payment.setFinalPrice(arrStr.getFinalPrice());
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

    public JSONObject UpdatePayment(PaymentDTO arrStr) throws JSONException{
        transaction.begin();
        s_res = new JSONObject();
        try {
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr.getPaymenterName());
            TypedQuery<PaymentsEntity> query2 = entityManager.createQuery("SELECT e from PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query2.setParameter("id", arrStr.getPaymentId());
            PaymentsEntity payment = query2.getSingleResult();
            if(payment.getStatus().equals("Закрыт") || payment.getStatus().equals("Отменен"))
            {
                s_res.put("response", "null");
            }
            else {
                payment.setCreationDate(arrStr.getCreationDate());
                payment.setDeadline(arrStr.getDeadline());
                payment.setSubInfo(arrStr.getSubInfo());
                payment.setPaymenterId(query.getSingleResult().getClientsId());

                query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
                query.setParameter("name", arrStr.getReceiverName());

                payment.setRecieverId(query.getSingleResult().getClientsId());
                payment.setItemId(arrStr.getItemId());
                payment.setAmount(arrStr.getAmount());
                payment.setFinalPrice(arrStr.getFinalPrice());
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

    public JSONObject AddItem(ItemDTO arrStr) throws JSONException {
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<ItemsEntity> query = entityManager.createQuery("SELECT e FROM ItemsEntity e where e.name =:name", ItemsEntity.class);
        query.setParameter("name", arrStr.getName());
        List<ItemsEntity> resultItems = null;
        resultItems = query.getResultList();

        if (resultItems.isEmpty()) {
            ItemsEntity item = new ItemsEntity();
            item.setName(arrStr.getName());
            item.setArticul(arrStr.getArticul());
            item.setPrice(arrStr.getPrice());
            item.setTaxes(arrStr.getTaxes());
            item.setMeasurement(arrStr.getMeasurement());
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

    public JSONObject GetPaymentInfo(PaymentDTO arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query.setParameter("id", arrStr.getPaymentId());
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

    public JSONObject GetFullPaymentInfo(PaymentDTO arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query.setParameter("id", arrStr.getPaymentId());
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

    public ArrayList<PersonalDTO> GetPersonalObeyList(JSONObject arrStr) throws JSONException{
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e where e.role = 'obey'", PersonalEntity.class);
        ArrayList<PersonalDTO> personalArray = new ArrayList<>();
        try {
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                PersonalDTO personalDTO = new PersonalDTO();
                personalDTO.setNameSername(personal.getNameSername());
                personalDTO.setContacts(personal.getContacts());
                personalDTO.setEmail(personal.getEmail());
                personalDTO.setSubrole(personal.getSubrole());
                personalDTO.setStatus(personal.getStatus());
                personalDTO.setLogin(personal.getLogin());
                personalDTO.setPassword(personal.getPassword());
                personalArray.add(personalDTO);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        transaction.commit();
        entityManager.clear();
        return personalArray;
    }

    public ArrayList<PersonalDTO> GetPersonalControlList(JSONObject arrStr) throws JSONException {
        transaction.begin();
        ArrayList<PersonalDTO> personalArray = new ArrayList<>();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e where e.role = 'control'", PersonalEntity.class);
        try {
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                PersonalDTO personalDTO = new PersonalDTO();
                personalDTO.setNameSername(personal.getNameSername());
                personalDTO.setContacts(personal.getContacts());
                personalDTO.setEmail(personal.getEmail());
                personalDTO.setSubrole(personal.getSubrole());
                personalDTO.setStatus(personal.getStatus());
                personalDTO.setLogin(personal.getLogin());
                personalDTO.setPassword(personal.getPassword());
                personalArray.add(personalDTO);
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

        transaction.commit();
        entityManager.clear();
        return personalArray;
    }

    public JSONObject AddTask(TaskDTO arrStr) throws JSONException {
        transaction.begin();
        s_res = new JSONObject();
        TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name " +
                "and e.personalByResponsableId.nameSername =:responsableName", TasksEntity.class);
        queryT.setParameter("name", arrStr.getName());
        queryT.setParameter("responsableName", arrStr.getResponsibleName());
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
                if(personal.getNameSername().equals(arrStr.getResponsibleName())) {
                    responsable_id = personal.getPersonalId();
                }
                else if (personal.getNameSername().equals(arrStr.getCheckerName())) {
                    checker_id = personal.getPersonalId();
                }
            }
            System.out.println("Не выбран");
            if(!arrStr.getProjectName().equals("Не выбран")) {
                TypedQuery<ProjectsEntity> queryProj = entityManager.createQuery("SELECT e FROM ProjectsEntity e where " +
                        "e.name =:name", ProjectsEntity.class);
                queryProj.setParameter("name", arrStr.getProjectName());
                task.setProjectId(queryProj.getSingleResult().getProjectsId());
            }
            if(!arrStr.getProcessName().equals("Не выбран")) {
                System.out.println(arrStr.getProcessName());
                TypedQuery<ProcessesEntity> queryProc = entityManager.createQuery("SELECT e FROM ProcessesEntity e where " +
                        "e.processesId =:id", ProcessesEntity.class);
                queryProc.setParameter("id", Integer.parseInt(arrStr.getProcessName()));
                task.setProcessId(queryProc.getSingleResult().getProcessesId());
            }
            if(!arrStr.getClientName().equals("Не выбран")) {
                TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e where " +
                        "e.name =:name", ClientsEntity.class);
                queryC.setParameter("name", arrStr.getClientName());
                task.setClientId(queryC.getSingleResult().getClientsId());
            }
            //=============================/Data Get==============================
            task.setName(arrStr.getName());
            task.setResponsableId(responsable_id);
            task.setDescription(arrStr.getDescription());
            task.setCheckerId(checker_id);
            task.setDeadline(arrStr.getDeadline());
            task.setStatus("Назначена");
            task.setCreationDate(arrStr.getCreationDate());
            entityManager.persist(task);

            queryT = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name" +
                    " and e.responsableId =:id", TasksEntity.class);
            queryT.setParameter("name", arrStr.getName());
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
            System.out.println(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetTaskInfo(TaskDTO arrStr) throws JSONException{
        try {
            transaction.begin();
            s_res = new JSONObject();
            TypedQuery<TasksEntity> query = entityManager.createQuery("SELECT e FROM TasksEntity e WHERE e.name =:name and " +
                    "e.personalByResponsableId.nameSername =:responsibleName", TasksEntity.class);
            query.setParameter("name", arrStr.getName());
            query.setParameter("responsibleName", arrStr.getResponsibleName());
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

    public JSONObject UpdateTaskInfo(TaskDTO arrStr) throws JSONException{
        try {
            transaction.begin();
            s_res = new JSONObject();
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
            List<PersonalEntity> pers = queryP.getResultList();
            int responsable_id = 0, checker_id = 0;
            for(PersonalEntity personal: pers) {
                if(personal.getNameSername().equals(arrStr.getResponsibleName())) {
                    responsable_id = personal.getPersonalId();
                }
                else if (personal.getNameSername().equals(arrStr.getCheckerName())) {
                    checker_id = personal.getPersonalId();
                }
            }
            TypedQuery<TasksEntity> query = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name and" +
                    " e.personalByResponsableId.nameSername =:responsibleName", TasksEntity.class);
            query.setParameter("name", arrStr.getOldName());
            query.setParameter("responsibleName", arrStr.getOldResponsibleName());
            TasksEntity resultTask = query.getSingleResult();
            resultTask.setName(arrStr.getName());
            resultTask.setResponsableId(responsable_id);
            resultTask.setDescription(arrStr.getDescription());
            resultTask.setCheckerId(checker_id);
            resultTask.setPriority(arrStr.getPriority());
            java.sql.Date date;
            if(arrStr.getDeadline().toString().equals("null")) {
                date = null;
            }
            else {
                date = arrStr.getDeadline();
            }
            resultTask.setDeadline(date);
            entityManager.merge(resultTask);
            s_res.put("response", "okay");
            //================================Notification==========================
        }
        catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetChatsList(JSONObject arrStr) throws JSONException {
        try {
            globalImageList = new ArrayList<>();
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e" +
                    " inner join ChatMembersEntity b on b.chatId = e.chatsId" +
                    " where b.personalByPersonalId.nameSername =: nameSurname", ChatsEntity.class);
            query.setParameter("nameSurname", arrStr.getString("nameSurname"));
            List<ChatsEntity> chatsList = new ArrayList<>();
            chatsList = query.getResultList();
            System.out.println(chatsList.get(0).getName());
            JSONArray chatList = new JSONArray();
            for (ChatsEntity chat : chatsList) {
                Iterator<MessagesEntity> iterator = chat.getMessagesByChatsId().iterator();
                MessagesEntity lastMessage = new MessagesEntity();
                while (iterator.hasNext()) {
                    lastMessage = iterator.next();
                }
                String senderName = " ", sendTime = " ", sendText = " ";
                try {
                    senderName = lastMessage.getPersonalBySenderId().getNameSername();
                    sendTime = lastMessage.getTime().toString();
                    sendText = lastMessage.getText();
                }
                catch (Exception e) {
                    System.out.println(e);
                    senderName = " ";
                }
                try {
                    //BufferedImage image = ImageIO.read(new File("/resources/images/" + chat.getImageName()));
                    File image = new File(getClass().getResource("/images/" + chat.getImageName()).getFile());
                    globalImageList.add(image);
                }
                catch (Exception e) {
                    System.out.println(e);
                    //BufferedImage image = ImageIO.read(new File("/images/1.png"));
                    File image = new File(getClass().getResource("/images/1.png").getFile());
                    globalImageList.add(image);
                }

                TypedQuery<MessageStatusEntity> query2 = entityManager.createQuery("SELECT COUNT(e)" +
                        " from MessageStatusEntity e where e.personalByPersonalId.nameSername =:personalId and e.chatId =:chatId" +
                                " and e.status = 'Не прочитано'", MessageStatusEntity.class);

                query2.setParameter("personalId", arrStr.getString("nameSurname"));
                query2.setParameter("chatId", chat.getChatsId());

                s_res.put("name", chat.getName());
                s_res.put("senderName", senderName);
                s_res.put("text", sendText);
                s_res.put("sendTime", sendTime);
                s_res.put("unreadMesCount", query2.getSingleResult());
                chatList.put(s_res);
                s_res = new JSONObject();
            }
            s_res.put("chatList", chatList);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw  new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddChat(ChatDTO arrStr, BufferedImage image) throws JSONException {
        try {
            transaction.begin();
            s_res = new JSONObject();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
            query.setParameter("name", arrStr.getName());
            List<ChatsEntity> resultChat = null;
            resultChat = query.getResultList();
            if(resultChat.isEmpty()) {
                ChatsEntity chat = new ChatsEntity();
                chat.setName(arrStr.getName());
                chat.setDescription(arrStr.getDescription());
                String newFileName = CreateImageName();
                chat.setImageName(newFileName);
                String s = "target/classes/images/" + newFileName;
                ImageIO.write(image, "PNG", new File(s));
                entityManager.persist(chat);
                query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
                query.setParameter("name", arrStr.getName());

                List<Integer> personalIdArr = new ArrayList<>();

                for(int i = 0; i < arrStr.getMembersList().size(); i++) {
                    TypedQuery<PersonalEntity> query2 = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
                            " where e.nameSername =:nameSurname", PersonalEntity.class);
                    query2.setParameter("nameSurname", arrStr.getMembersList().get(i));
                    ChatMembersEntity entity = new ChatMembersEntity();
                    entity.setChatId(query.getSingleResult().getChatsId());
                    entity.setPersonalId(query2.getSingleResult().getPersonalId());
                    entityManager.persist(entity);
                }
                //resultPersonal.setRegDate(Date.valueOf(LocalDate.parse(arrStr[10], formatter)));

                s_res.put("response", "okay");
            }
            else {
                s_res.put("response", "null");
            }
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

    public JSONObject GetChatMessages(ChatDTO arrStr) throws JSONException {
        try {

            globalImageList = new ArrayList<>();
            globalNameSernameList = new ArrayList<>();
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<ChatsEntity> queryChat = entityManager.createQuery("SELECT e FROM ChatsEntity e" +
                    " where e.name =:name", ChatsEntity.class);
            queryChat.setParameter("name", arrStr.getName());

            //=====================================================================
            TypedQuery<ChatMembersEntity> queryMembers = entityManager.createQuery("SELECT e FROM ChatMembersEntity e " +
                    "where e.chatId =:chatId", ChatMembersEntity.class);
            queryMembers.setParameter("chatId", queryChat.getSingleResult().getChatsId());
            List<ChatMembersEntity> membersList = queryMembers.getResultList();
            for(ChatMembersEntity member: membersList) {
                try {
                    //BufferedImage image = ImageIO.read(new File("/resources/images/" + chat.getImageName()));
                    File image = new File(getClass().getResource("/images/" +
                            member.getPersonalByPersonalId().getImageName()).getFile());
                    globalImageList.add(image);
                    globalNameSernameList.add(member.getPersonalByPersonalId().getNameSername());
                } catch (Exception e) {
                    System.out.println(e);
                    //BufferedImage image = ImageIO.read(new File("/images/1.png"));
                    File image = new File(getClass().getResource("/images/1.png").getFile());
                    globalImageList.add(image);
                    globalNameSernameList.add(member.getPersonalByPersonalId().getNameSername());
                }
            }
            //=====================================================================

            TypedQuery<MessagesEntity> query = entityManager.createQuery("SELECT e FROM MessagesEntity e" +
                    " where e.chatId =:id", MessagesEntity.class);
            query.setParameter("id", queryChat.getSingleResult().getChatsId());
            System.out.println(queryChat.getSingleResult().getChatsId() + " eeeeeeeeeeeeeeeeee");
            List<MessagesEntity> messagesList;
            messagesList = query.getResultList();
            String dateChecker = "0";
            s_res.put("globalNameSurnameListSize", globalNameSernameList.size());
            JSONArray messageByDateList = new JSONArray();
            JSONArray messageList = new JSONArray();
            for (MessagesEntity message : messagesList) {
                JSONObject tempObj = new JSONObject();
                tempObj.put("text", message.getText());
                tempObj.put("time", message.getTime());
                tempObj.put("senderName", message.getPersonalBySenderId().getNameSername());
                messageList.put(tempObj);
                if(!message.getDate().toString().equals(dateChecker)) {
                    tempObj = new JSONObject();
                    tempObj.put("date", message.getDate().toString());
                    tempObj.put("messageList", messageList);
                    messageList = new JSONArray();
                    messageByDateList.put(tempObj);
                    dateChecker = message.getDate().toString();
                }


            }
            s_res.put("messageByDateList", messageByDateList);
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
            //throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        s_res.put("response", "okay");
        return s_res;
    }

    public JSONObject AddMessage(MessageDTO arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();

            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
            query.setParameter("name", arrStr.getChatName());
            ChatsEntity chat = query.getSingleResult();
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
            queryP.setParameter("name", arrStr.getSenderName());
            MessagesEntity message = new MessagesEntity();
            message.setText(arrStr.getText());
            message.setDate(arrStr.getDate());
            message.setSenderId(queryP.getSingleResult().getPersonalId());
            message.setChatId(chat.getChatsId());
            message.setTime(arrStr.getTime());
            entityManager.persist(message);
            entityManager.clear();
            s_res.put("response", "okay");

            TypedQuery<MessagesEntity> queryM = entityManager.createQuery("SELECT e FROM MessagesEntity e order by e.messagesId desc", MessagesEntity.class);
            List<MessagesEntity> queryMResult = queryM.getResultList();

            TypedQuery<ChatMembersEntity> queryCM = entityManager.createQuery("SELECT e FROM ChatMembersEntity e where e.chatId =:chatID" +
                    " and e.personalByPersonalId.nameSername <>:nameSurname", ChatMembersEntity.class);
            queryCM.setParameter("nameSurname", arrStr.getSenderName());
            queryCM.setParameter("chatID", chat.getChatsId());
            System.out.println(chat.getChatsId());
            List<ChatMembersEntity> chatMembers = queryCM.getResultList();
            for(ChatMembersEntity member: chatMembers) {
                MessageStatusEntity mesStatus = new MessageStatusEntity();
                mesStatus.setPersonalId(member.getPersonalId());
                mesStatus.setMessageId(queryMResult.get(0).getMessagesId());
                mesStatus.setChatId(chat.getChatsId());
                mesStatus.setStatus("Не прочитано");
                entityManager.persist(mesStatus);
            }
        } catch (Exception e) {
            s_res.put("response", "null");
            System.out.println(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject DeleteMessageStatus(MessageDTO arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<MessageStatusEntity> query = entityManager.createQuery("SELECT e FROM MessageStatusEntity e" +
                    " WHERE e.chatByChatId.name = :chatName" +
                    " and e.personalByPersonalId.nameSername =:nameSurname", MessageStatusEntity.class);
            query.setParameter("chatName", arrStr.getChatName());
            query.setParameter("nameSurname", arrStr.getSenderName());
            List<MessageStatusEntity> statusesList = query.getResultList();
            for(MessageStatusEntity status: statusesList) {
                entityManager.remove(status);
            }
           s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            System.out.println(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

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
//            System.out.println(e);
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
//            System.out.println(e);
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
    public JSONObject DeletePersonal(PersonalDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            TypedQuery<ClientsEntity> queryClients = entityManager.createQuery("SELECT e FROM ClientsEntity e" +
                    " where e.personalByResponsableId.nameSername =:responsibleName", ClientsEntity.class);
            queryClients.setParameter("responsibleName", arrStr.getNameSername());
            List<ClientsEntity> clientsEntities = queryClients.getResultList();
            if(!clientsEntities.isEmpty()) {
                s_res.put("response", "linkDetected");
                transaction.commit();
                entityManager.clear();
                return s_res;
//                for(ClientsEntity client: clientsEntities) {
//                    client.setResponsableId(null);
//                    entityManager.merge(client);
//                }
//                transaction.commit();
            }
//            transaction.begin();
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
                    " where e.nameSername =:nameSurname", PersonalEntity.class);
            query.setParameter("nameSurname", arrStr.getNameSername());
            PersonalEntity personal = query.getSingleResult();
            entityManager.remove(personal);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddBusiness(BusinessDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        transaction.begin();
        try {
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
                    " WHERE e.nameSername =:nameSurname", PersonalEntity.class);
            queryP.setParameter("nameSurname", arrStr.getResponsibleName());
            PersonalEntity personal = queryP.getSingleResult();
            BusinessEntity businessEntity = new BusinessEntity();
            businessEntity.setName(arrStr.getName());
            businessEntity.setDate(arrStr.getDate().format(formatter));
            businessEntity.setStatus(arrStr.getStatus());
            System.out.println(arrStr.getType());
            switch (arrStr.getType()) {
                case "Клиент": {
                    TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e" +
                            " WHERE e.name =:name", ClientsEntity.class);
                    queryC.setParameter("name", arrStr.getLinkedEntityName());
                    businessEntity.setClientId(queryC.getSingleResult().getClientsId());
                    break;
                }
                case "Процесс": {
                    businessEntity.setProcessId(Integer.parseInt(arrStr.getLinkedEntityName()));
                    break;
                }
                case "Проект": {
                    TypedQuery<ProjectsEntity> queryProj = entityManager.createQuery("SELECT e FROM ProjectsEntity e" +
                            " WHERE e.name =:name", ProjectsEntity.class);
                    queryProj.setParameter("name", arrStr.getLinkedEntityName());
                    businessEntity.setProjectID(queryProj.getSingleResult().getProjectsId());
                    break;
                }
                case "Задача": {
                    TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
                            " WHERE e.name =:name", TasksEntity.class);
                    queryT.setParameter("name", arrStr.getLinkedEntityName());
                    businessEntity.setTaskID(queryT.getSingleResult().getTasksId());
                    break;
                }
                default: {
                    System.out.println("default");
                }
            }
            businessEntity.setResponsableId(personal.getPersonalId());
            businessEntity.setDescription("Отсутствует");
            businessEntity.setPlace("Отсутствует");
            entityManager.persist(businessEntity);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetBusinessInfo(BusinessDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            BusinessEntity business = entityManager.getReference(BusinessEntity.class, arrStr.getBusinessId());
            String[] dateSplit = business.getDate().toString().split(" ");
            s_res.put("name", business.getName());
            s_res.put("date", dateSplit[0]);
            s_res.put("time", dateSplit[1]);
            s_res.put("description", business.getDescription());
            s_res.put("place", business.getPlace());
            s_res.put("responsibleName", business.getPersonalByResponsableId().getNameSername());
            s_res.put("status", business.getStatus());
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject CompleteBusiness(BusinessDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            BusinessEntity business = entityManager.getReference(BusinessEntity.class, arrStr.getBusinessId());
            business.setStatus("Завершено");
            entityManager.merge(business);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject DeleteBusiness(BusinessDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            BusinessEntity business = entityManager.getReference(BusinessEntity.class, arrStr.getBusinessId());
            entityManager.remove(business);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject UpdateBusiness(BusinessDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            BusinessEntity business = entityManager.getReference(BusinessEntity.class, arrStr.getBusinessId());
            business.setDescription(arrStr.getDescription());
            business.setPlace(arrStr.getPlace());
            entityManager.merge(business);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject AddComment(CommentDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            TypedQuery<JournalsEntity> queryJ;
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
                    " where e.nameSername =:nameSurname", PersonalEntity.class);
            queryP.setParameter("nameSurname", arrStr.getSenderName());
            int personalId = queryP.getSingleResult().getPersonalId();
            CommentsEntity comment = new CommentsEntity();
            switch (arrStr.getType()) {
                case "Клиент": {
                    TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e" +
                            " where e.name =:name", ClientsEntity.class);
                    queryC.setParameter("name", arrStr.getLinkedEntityName());
                    queryJ = entityManager.createQuery("SELECT e FROM JournalsEntity e" +
                            " where e.clientId =:id", JournalsEntity.class);
                    queryJ.setParameter("id", queryC.getSingleResult().getClientsId());
                    comment.setDate(Date.valueOf(LocalDate.now()));
                    comment.setText(arrStr.getText());
                    comment.setSenderId(personalId);
                    comment.setJournalId(queryJ.getSingleResult().getJournalsId());
                    break;
                }
                case "Задача": {
                    TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
                            " where e.name =:name", TasksEntity.class);
                    queryT.setParameter("name", arrStr.getLinkedEntityName());
                    queryJ = entityManager.createQuery("SELECT e FROM JournalsEntity e" +
                            " where e.taskId =:id", JournalsEntity.class);
                    queryJ.setParameter("id", queryT.getSingleResult().getTasksId());
                    comment.setDate(Date.valueOf(LocalDate.now()));
                    comment.setText(arrStr.getText());
                    comment.setSenderId(personalId);
                    comment.setJournalId(queryJ.getSingleResult().getJournalsId());
                    break;
                }
                case "Проект": {
                    break;
                }
                case "Процесс": {
                    break;
                }
            }
           entityManager.persist(comment);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject DeleteClient(ClientDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            ClientsEntity client = entityManager.getReference(ClientsEntity.class, arrStr.getClientsId());
            entityManager.remove(client);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject ChangePaymentStatus(PaymentDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            PaymentsEntity payment = entityManager.getReference(PaymentsEntity.class, arrStr.getPaymentId());
            payment.setStatus(arrStr.getStatus());
            entityManager.merge(payment);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "okay");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject DeletePayment(PaymentDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            PaymentsEntity payment = entityManager.getReference(PaymentsEntity.class, arrStr.getPaymentId());
            if(payment.getPaymentImageName() != null) {
                Files.delete(Paths.get("target/classes/images/checks/" + payment.getPaymentImageName()));
            }
            entityManager.remove(payment);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject ChangeTaskStatus(TaskDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
                    " WHERE e.name =:name AND e.personalByResponsableId.nameSername =:nameSurname", TasksEntity.class);
            queryT.setParameter("name", arrStr.getName());
            queryT.setParameter("nameSurname", arrStr.getResponsibleName());
            TasksEntity task = queryT.getSingleResult();
            task.setStatus(arrStr.getStatus());
            entityManager.merge(task);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject DeleteTask(TaskDTO arrStr) throws JSONException {
        s_res = new JSONObject();
        transaction.begin();
        try {
            TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
                    " WHERE e.name =:name AND e.personalByResponsableId.nameSername =:nameSurname", TasksEntity.class);
            queryT.setParameter("name", arrStr.getName());
            queryT.setParameter("nameSurname", arrStr.getResponsibleName());
            TasksEntity task = queryT.getSingleResult();
            entityManager.remove(task);
            s_res.put("response", "okay");
        } catch (Exception e) {
            System.out.println(e);
            s_res.put("response", "null");
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

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
//            System.out.println(e);
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
//            System.out.println(e);
//            s_res = "null" + "\r";
//        }
//        transaction.commit();
//        entityManager.clear();
//        return s_res;
//    }
//
    public JSONObject GetPersonalGeneralInfo(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
            List<PersonalEntity> personalList;
            personalList = query.getResultList();
            JSONArray personalJSONList = new JSONArray();
            for (PersonalEntity personal : personalList) {
                JSONObject pers = new JSONObject();
                pers.put("nameSurname", personal.getNameSername());
                pers.put("role", personal.getRole());
                pers.put("subRole", personal.getSubrole());
                pers.put("status", personal.getStatus());
                pers.put("businessCount", personal.getBusinessesByPersonalId().size());
                pers.put("clientCount", personal.getClientsByPersonalId().size());
                pers.put("projectCount", personal.getProjectsByPersonalId().size());
                pers.put("taskCount", personal.getTasksByPersonalId().size());
                personalJSONList.put(pers);
            }
            s_res.put("personalList", personalJSONList);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetClientGeneralInfo(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e", ClientsEntity.class);
            List<ClientsEntity> clientList;
            clientList = query.getResultList();
            JSONArray clientJSONList =  new JSONArray();
            for (ClientsEntity client : clientList) {
                JSONObject clientJSON = new JSONObject();
                clientJSON.put("name", client.getName());
                clientJSON.put("type", client.getType());
                clientJSON.put("workType", client.getWork_type());
                clientJSON.put("businessCount", client.getBusinessesByClientsId().size());
                clientJSON.put("paymentCount", client.getPaymentsByClientsId().size());
                clientJSON.put("taskCount", client.getTasksByClientsId().size());
                clientJSONList.put(clientJSON);
            }
            s_res.put("clientList", clientJSONList);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }
    public JSONObject GetPaymentGeneralInfo(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e", PaymentsEntity.class);
            List<PaymentsEntity> paymentList;
            paymentList = query.getResultList();
            JSONArray paymentJSONList = new JSONArray();
            for (PaymentsEntity payment : paymentList) {
                JSONObject paymentJSON = new JSONObject();
                paymentJSON.put("id", payment.getPaymentId());
                paymentJSON.put("finalPrice", payment.getFinalPrice());
                paymentJSON.put("status", payment.getStatus());
                paymentJSON.put("itemName", payment.getItemsByItemId().getName());
                paymentJSONList.put(paymentJSON);
            }
            s_res.put("paymentList", paymentJSONList);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetTaskGeneralInfo(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<TasksEntity> query = entityManager.createQuery("SELECT e FROM TasksEntity e", TasksEntity.class);
            List<TasksEntity> taskList;
            taskList = query.getResultList();
            JSONArray taskJSONList = new JSONArray();
            for (TasksEntity task : taskList) {
                JSONObject taskJSON = new JSONObject();
                taskJSON.put("name", task.getName());
                taskJSON.put("status", task.getStatus());
                taskJSON.put("priority", task.getPriority());
                taskJSONList.put(taskJSON);
            }
            s_res.put("taskList", taskJSONList);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }
    public JSONObject GetProjectGeneralInfo(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<ProjectsEntity> query = entityManager.createQuery("SELECT e FROM ProjectsEntity e", ProjectsEntity.class);
            List<ProjectsEntity> projectList;
            projectList = query.getResultList();
            JSONArray projectJSONList = new JSONArray();
            for (ProjectsEntity project : projectList) {
                JSONObject projectJSON = new JSONObject();
                projectJSON.put("name", project.getName());
                projectJSON.put("status", project.getStatus());
                projectJSON.put("trudozatraty", project.getTrudozatraty());
                projectJSON.put("memberCount", project.getProjectMembersByProjectsId().size());
                projectJSON.put("taskCount", project.getTasksByProjectsId().size());
                projectJSONList.put(projectJSON);
            }
            s_res.put("projectList", projectJSONList);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public JSONObject GetBusinessGeneralInfo(JSONObject arrStr) throws JSONException {
        try {
            s_res = new JSONObject();
            transaction.begin();
            TypedQuery<BusinessEntity> query = entityManager.createQuery("SELECT e FROM BusinessEntity e", BusinessEntity.class);
            List<BusinessEntity> businessList;
            businessList = query.getResultList();
            JSONArray businessJSONList = new JSONArray();
            for (BusinessEntity business : businessList) {
                JSONObject businessJSON = new JSONObject();
                businessJSON.put("name", business.getName());
                businessJSON.put("status", business.getStatus());
                businessJSONList.put(businessJSON);
            }
            s_res.put("businessList", businessJSONList);
            s_res.put("response", "okay");
        } catch (Exception e) {
            s_res.put("response", "null");
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

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
            System.out.println(e);
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
            File image = new File(getClass().getResource("/images/checks/" + payment.getPaymentImageName()).getFile());
            globalImage = image;
            s_res.put("response", "okay");

        } catch (Exception e) {
            System.out.println(e);
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
