package DataController;

import Subs.PersonalInfoClass;
import com.google.protobuf.Message;
import entity.*;
import jakarta.persistence.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
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
    String s_res;
    Connection db;
    StringBuffer x;
    String causeAllocation = "0";
    EntityManagerFactory managerFactory;
    EntityManager entityManager;
    EntityTransaction transaction;
    BufferedImage globalImage;
    List<BufferedImage> globalImageList = new ArrayList<>();
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

    public Object Registration(String[] arrStr) {
        transaction.begin();
        TypedQuery<PersonalEntity> queryInit = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
        if(queryInit.getResultList().isEmpty()) {
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr[1]);
            personal.setPassword(arrStr[2]);
            personal.setNameSername(arrStr[3]);
            personal.setContacts(arrStr[4]);
            personal.setEmail(arrStr[5]);
            personal.setSubrole("Менеджер");
            personal.setRole("control");
            personal.setStatus("Активен");
            entityManager.persist(personal);
            s_res = "1/" + "\r";
            return s_res;
        }
        TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login AND e.nameSername =:name and e.contacts =:contacts and e.email =:email", PersonalEntity.class);
        query.setParameter("login", arrStr[1]);
        query.setParameter("name", arrStr[3]);
        query.setParameter("contacts", arrStr[4]);
        query.setParameter("email", arrStr[5]);
        List<PersonalEntity> resultPersonal = null;
        resultPersonal = query.getResultList();

        if (resultPersonal.isEmpty()) {
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr[1]);
            personal.setPassword(arrStr[2]);
            personal.setNameSername(arrStr[3]);
            personal.setContacts(arrStr[4]);
            personal.setEmail(arrStr[5]);
            personal.setSubrole("Заявка");
            personal.setRole("obey");
            personal.setStatus("Ожидает подтверждения");
            entityManager.persist(personal);
            s_res = "1/" + "\r";
            //==================================Notification==========================
        } else {
            s_res = "0/" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object Login(String[] arrStr) {
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e WHERE e.login =:login AND e.password =:password", PersonalEntity.class);
        q.setParameter("login", arrStr[1]);
        q.setParameter("password", arrStr[2]);
        try {
            PersonalEntity entity = q.getSingleResult();
            if (entity.getStatus().equals("Активен")) {
                s_res = "success" + "<<" + entity.getRole() + "<<" + entity.getNameSername() + "\r";
            } else {
                s_res = "null" + "\r";
            }
        } catch (Exception e) {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetPersonalList(String[] arrStr) {
        s_res = "";
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e", PersonalEntity.class);
        try {
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                s_res += personal.getNameSername() + ">>" + personal.getContacts() + ">>" + personal.getEmail() + ">>" + personal.getSubrole() + ">>" + personal.getStatus() + ">>" + personal.getLogin() + ">>" + personal.getPassword();
                s_res += "<<";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddPersonal(String[] arrStr) {
        transaction.begin();
        TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login or e.nameSername =:name", PersonalEntity.class);
        query.setParameter("login", arrStr[1]);
        query.setParameter("name", arrStr[3]);
        List<PersonalEntity> resultPersonal = null;
        resultPersonal = query.getResultList();

        if (resultPersonal.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            PersonalEntity personal = new PersonalEntity();
            personal.setLogin(arrStr[1]);
            personal.setPassword(arrStr[2]);
            personal.setNameSername(arrStr[3]);
            personal.setContacts(arrStr[4]);
            personal.setEmail(arrStr[5]);
            personal.setSubrole(arrStr[7]);
            personal.setRole(arrStr[6]);
            personal.setStatus(arrStr[8]);
            personal.setImageName("1.png");
            //personal.setDescription(arrStr[9]);
            personal.setRegDate(Date.valueOf(LocalDate.parse(arrStr[10], formatter)));
            //personal.setImageName(arrStr[11]);
            entityManager.persist(personal);
            s_res = "1/" + "\r";
            //===============================Notification==========================
        } else {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetPersonalInfo(String[] arrStr) {
        transaction.begin();
        PersonalInfoClass personalInfo = null;
        try {
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr[1]);
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            String infoS = resultPersonal.getLogin() + ">>" + resultPersonal.getPassword() + ">>" + resultPersonal.getNameSername() +
                    ">>" + resultPersonal.getContacts() + ">>" + resultPersonal.getEmail() + ">>" + resultPersonal.getRole() +
                    ">>" + resultPersonal.getSubrole() + ">>" + resultPersonal.getStatus() + ">>" + resultPersonal.getDescription() +
                    ">>" + resultPersonal.getRegDate() + "\r";
            BufferedImage image = ImageIO.read(getClass().getResource("/images/" + resultPersonal.getImageName()));

            //BufferedImage image = ImageIO.read(new File("/images/" + resultPersonal.getImageName()));
            s_res = infoS;
            globalImage = image;
        }
        catch (Exception e) {
            System.out.println(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object UpdatePersonalInfo(String[] arrStr, BufferedImage image) {
        try {
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr[1]);
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            //resultPersonal.setLogin(arrStr[11]);
            //resultPersonal.setPassword(arrStr[2]);
            //resultPersonal.setNameSername(arrStr[3]);
            resultPersonal.setContacts(arrStr[2]);
            resultPersonal.setEmail(arrStr[3]);
            //resultPersonal.setRole(arrStr[6]);
            //resultPersonal.setSubrole(arrStr[7]);
            //resultPersonal.setStatus(arrStr[8]);
            resultPersonal.setDescription(arrStr[4]);
            //resultPersonal.setRegDate(Date.valueOf(LocalDate.parse(arrStr[10], formatter)));
            Path path = Paths.get(getClass().getResource("/images/" + resultPersonal.getImageName()).toURI());
            Files.delete(path);
            String newFileName = CreateImageName();
            resultPersonal.setImageName(newFileName);
            ImageIO.write(image, "PNG", new File(getClass().getResource("/images/" + newFileName).toURI()));
            entityManager.merge(resultPersonal);
            s_res = "success" + "\r";
            //================================Notification==========================
        }
        catch (Exception e) {
            System.out.println(e);
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }
    public Object GetPersonalImage() {
        return globalImage;
    }

    public List<BufferedImage> GetChatImageList() {
        return globalImageList;
    }

    public List<String> GetNameSernameList() {
        return globalNameSernameList;
    }

    public Object UpdatePersonalInfoAsManager(String[] arrStr) {
        try {
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.login =:login", PersonalEntity.class);
            query.setParameter("login", arrStr[2]);
            PersonalEntity resultPersonal = null;
            resultPersonal = query.getSingleResult();
            resultPersonal.setContacts(arrStr[3]);
            resultPersonal.setEmail(arrStr[4]);
            resultPersonal.setRole(arrStr[5]);
            resultPersonal.setSubrole(arrStr[6]);
            resultPersonal.setStatus(arrStr[7]);
            entityManager.merge(resultPersonal);
            s_res = "success" + "\r";
            //==============================Notification=====================
        }
        catch (Exception e) {
            System.out.println(e);
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetClientsList(String arrStr[]) {
        try {
            s_res = "";
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e", ClientsEntity.class);
            List<ClientsEntity> clientsList = new ArrayList<ClientsEntity>();
            clientsList = query.getResultList();
            for (ClientsEntity client : clientsList) {
                s_res += client.getName() + "<<" + client.getType() + "<<" + client.getPersonalByResponsableId().getNameSername() + "<<" + client.getClientsId();
                s_res += ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetClientInfo(String arrStr[]) {
        try {
            transaction.begin();
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e WHERE e.clientsId =:id", ClientsEntity.class);
            query.setParameter("id", arrStr[1]);
            ClientsEntity client = new ClientsEntity();
            client = query.getSingleResult();
            s_res = client.getName() + "<<" + client.getPersonalByResponsableId().getNameSername() + "<<" + client.getPhone() +
                    "<<" + client.getEmail() + "<<" + client.getAdress() + "<<" + client.getDescription() +
                    "<<" + client.getType() + "<<" + client.getWork_type() + "<<"
                    + client.getReg_date().toString().split(" ")[0] + ">>";
            List<TasksEntity> tasksEntityList = (List<TasksEntity>) client.getTasksByClientsId();
            List<BusinessEntity> businessEntityList = (List<BusinessEntity>) client.getBusinessesByClientsId();
            List<ProcessesEntity> processesEntityList = (List<ProcessesEntity>) client.getProcessesByClientsId();
            List<JournalsEntity> journalsEntityList = (List<JournalsEntity>) client.getJournalsByClientsId();
            List<CommentsEntity> commentsEntityList = (List<CommentsEntity>) journalsEntityList.get(0).getCommentsByJournalsId();
            for(TasksEntity task: tasksEntityList) {
                s_res += task.getTasksId() + "^^" + task.getName() + "^^" + task.getStatus() + "<<";
            }
            s_res += ">>";
            for(BusinessEntity business: businessEntityList) {
                s_res += business.getBusinessId() + "^^" + business.getName() + " от "
                        + business.getDate().toString().split("T")[0] + "^^" + business.getStatus() + "<<";
            }
            s_res += ">>";
            for(ProcessesEntity process: processesEntityList) {
                s_res += process.getProcessesId() + "<<";
            }
            s_res += ">>";
            for(CommentsEntity comment: commentsEntityList) {
                s_res += comment.getText() + "^^" + comment.getPersonalBySenderId().getNameSername() + "^^" + comment.getDate() + "^^" + comment.getPersonalBySenderId().getLogin() + "<<";
            }
            s_res += ">>";
            s_res += "\r";
        }
        catch (Exception e) {
            s_res = "null" + "\r";
            e.printStackTrace();
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddClient(String[] arrStr) {
        transaction.begin();

        TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
        queryP.setParameter("name", arrStr[6]);
        PersonalEntity pers = queryP.getSingleResult();

        TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name or e.email =:email", ClientsEntity.class);
        query.setParameter("name", arrStr[1]);
        query.setParameter("email", arrStr[2]);
        List<ClientsEntity> resultClients = null;
        resultClients = query.getResultList();

        if (resultClients.isEmpty()) {
            ClientsEntity client = new ClientsEntity();
            client.setName(arrStr[1]);
            client.setEmail(arrStr[2]);
            client.setPhone(arrStr[3]);
            client.setAdress(arrStr[4]);
            client.setDescription(arrStr[5]);
            client.setReg_date(Date.valueOf(LocalDate.now()));
            client.setResponsableId(pers.getPersonalId());
            entityManager.persist(client);

            query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name and e.email =:email", ClientsEntity.class);
            query.setParameter("name", arrStr[1]);
            query.setParameter("email", arrStr[2]);
            JournalsEntity journal = new JournalsEntity();
            journal.setClientId(query.getSingleResult().getClientsId());
            entityManager.persist(journal);
            s_res = "1/" + "\r";
            //===============================Notification==========================
        } else {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object UpdateClientInfo(String[] arrStr) {
        try {
            transaction.begin();
            System.out.println(arrStr[0]);
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
            queryP.setParameter("name", arrStr[6]);
            PersonalEntity pers = queryP.getSingleResult();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name and e.email =:email", ClientsEntity.class);
            query.setParameter("name", arrStr[10]);
            query.setParameter("email", arrStr[11]);
            ClientsEntity resultClient = null;
            resultClient = query.getSingleResult();
            resultClient.setName(arrStr[1]);
            resultClient.setPhone(arrStr[2]);
            resultClient.setEmail(arrStr[3]);
            resultClient.setAdress(arrStr[4]);
            resultClient.setDescription(arrStr[5]);
            resultClient.setResponsableId(pers.getPersonalId());
            resultClient.setType(arrStr[7]);
            resultClient.setWork_type(arrStr[8]);
            java.util.Date date = new java.util.Date();
            if(arrStr[9].equals("null")) {
                date = null;
            }
            else {
                date = Date.valueOf(LocalDate.parse(arrStr[9], formatter));
            }
            resultClient.setReg_date(date);
            entityManager.merge(resultClient);
            s_res = "success" + "\r";
            //================================Notification==========================
        }
        catch (Exception e) {
            System.out.println(e);
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetPaymentList(String[] arrStr) {
        try {
            s_res = "";
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e", PaymentsEntity.class);
            List<PaymentsEntity> paymentsList = new ArrayList<PaymentsEntity>();
            paymentsList = query.getResultList();
            for (PaymentsEntity payment : paymentsList) {
                s_res += payment.getPaymentId() + "<<" + payment.getDeadline() + "<<" + payment.getFinalPrice() + "<<" + payment.getClientsByPaymenterId().getName() +
                "<<" + payment.getStatus() + "<<" + payment.getPaymenterId();
                s_res += ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddPayment(String[] arrStr) {
        transaction.begin();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr[4]);
            PaymentsEntity payment = new PaymentsEntity();
            payment.setCreationDate(Date.valueOf(LocalDate.parse(arrStr[1], formatter)));
            payment.setDeadline(Date.valueOf(LocalDate.parse(arrStr[2], formatter)));
            payment.setSubInfo(arrStr[3]);
            payment.setPaymenterId(query.getSingleResult().getClientsId());

            query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr[5]);

            payment.setRecieverId(query.getSingleResult().getClientsId());
            payment.setItemId(Integer.parseInt(arrStr[6]));
            payment.setAmount(Integer.parseInt(arrStr[7]));
            payment.setFinalPrice(Integer.parseInt(arrStr[8]));
            payment.setStatus("Создан");
            entityManager.persist(payment);

            s_res = "1/" + "\r";
        }
        catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
            //===============================Notification==========================
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object UpdatePayment(String arrStr[]) {
        transaction.begin();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ClientsEntity> query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
            query.setParameter("name", arrStr[4]);
            TypedQuery<PaymentsEntity> query2 = entityManager.createQuery("SELECT e from PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query2.setParameter("id", Integer.parseInt(arrStr[9]));
            PaymentsEntity payment = query2.getSingleResult();
            if(payment.getStatus().equals("Закрыт") || payment.getStatus().equals("Отменен"))
            {
                s_res = null + "\r";
            }
            else {
                payment.setCreationDate(Date.valueOf(LocalDate.parse(arrStr[1], formatter)));
                payment.setDeadline(Date.valueOf(LocalDate.parse(arrStr[2], formatter)));
                payment.setSubInfo(arrStr[3]);
                payment.setPaymenterId(query.getSingleResult().getClientsId());

                query = entityManager.createQuery("SELECT e FROM ClientsEntity e where e.name =:name", ClientsEntity.class);
                query.setParameter("name", arrStr[5]);

                payment.setRecieverId(query.getSingleResult().getClientsId());
                payment.setItemId(Integer.parseInt(arrStr[6]));
                payment.setAmount(Integer.parseInt(arrStr[7]));
                payment.setFinalPrice(Integer.parseInt(arrStr[8]));
                entityManager.merge(payment);

                s_res = "1/" + "\r";
            }
        }
        catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
        //===============================Notification==========================
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetItemsList(String[] arrStr) {
        try {
            s_res = "";
            transaction.begin();
            TypedQuery<ItemsEntity> query = entityManager.createQuery("SELECT e FROM ItemsEntity e", ItemsEntity.class);
            List<ItemsEntity> itemsList = new ArrayList<>();
            itemsList = query.getResultList();
            for (ItemsEntity item : itemsList) {
                s_res += item.getName() + "<<" + item.getArticul() + "<<" + item.getPrice() + "<<" + item.getTaxes()
                        + "<<" + item.getMeasurement() + "<<" + item.getItemId();
                s_res += ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddItem(String[] arrStr) {
        transaction.begin();

        TypedQuery<ItemsEntity> query = entityManager.createQuery("SELECT e FROM ItemsEntity e where e.name =:name", ItemsEntity.class);
        query.setParameter("name", arrStr[1]);
        List<ItemsEntity> resultItems = null;
        resultItems = query.getResultList();

        if (resultItems.isEmpty()) {
            ItemsEntity item = new ItemsEntity();
            item.setName(arrStr[1]);
            item.setArticul(arrStr[2]);
            item.setPrice(Integer.parseInt(arrStr[3]));
            item.setTaxes(Integer.parseInt(arrStr[4]));
            item.setMeasurement(arrStr[5]);
            entityManager.persist(item);
            s_res = "1/" + "\r";
            //===============================Notification==========================
        } else {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetPaymentInfo(String[] arrStr) {
        try {
            s_res = "";
            transaction.begin();
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query.setParameter("id", arrStr[1]);
            PaymentsEntity payment = query.getSingleResult();
            s_res = payment.getCreationDate() + ">>" + payment.getClientsByPaymenterId().getName() + ">>"
                    + payment.getClientsByRecieverId().getName() + ">>"
                    + payment.getStatus() + ">>" + payment.getItemsByItemId().getItemId() + "<<"
                    + payment.getItemsByItemId().getName() + "<<" + payment.getItemsByItemId().getMeasurement() + "<<"
                    + payment.getAmount() + "<<" + payment.getItemsByItemId().getPrice() + "<<"
                    + payment.getItemsByItemId().getTaxes() + "<<"
                    + (payment.getFinalPrice()) + ">>";
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetFullPaymentInfo(String[] arrStr) {
        try {
            s_res = "";
            transaction.begin();
            TypedQuery<PaymentsEntity> query = entityManager.createQuery("SELECT e FROM PaymentsEntity e where e.paymentId =:id", PaymentsEntity.class);
            query.setParameter("id", arrStr[1]);
            PaymentsEntity payment = query.getSingleResult();
            s_res = payment.getCreationDate().toString() + ">>" + payment.getClientsByPaymenterId().getName() + ">>"
                    + payment.getClientsByRecieverId().getName() + ">>" + payment.getDeadline().toString() + ">>"
                    + payment.getSubInfo() + ">>" + payment.getItemsByItemId().getName() + ">>" + payment.getAmount() + ">>"
                    + payment.getItemsByItemId().getMeasurement() + ">>" + payment.getFinalPrice() + ">>"
                    + payment.getItemsByItemId().getTaxes() + ">>" + payment.getPaymentId();
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetTasksList(String[] arrStr) {
        s_res = "";
        transaction.begin();
        TypedQuery<TasksEntity> q = entityManager.createQuery("SELECT e from TasksEntity e", TasksEntity.class);
        try {
            List<TasksEntity> list = q.getResultList();
            for (TasksEntity task : list) {
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
                s_res += task.getName() + "<<" + (completeBCount + "/" + businessEntities.size()) + "<<"
                        + task.getCreationDate() + "<<" + task.getDeadline() + "<<"
                        + task.getPersonalByResponsableId().getNameSername() + "<<"
                        + task.getPersonalByCheckerId().getNameSername() + "<<" + task.getStatus() + "<<" + task.getTasksId();
                s_res += ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            System.out.println(e);
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetPersonalObeyList(String[] arrStr) {
        s_res = "";
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e where e.role = 'obey'", PersonalEntity.class);
        try {
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                s_res += personal.getNameSername() + ">>" + personal.getContacts() + ">>" + personal.getEmail() + ">>" + personal.getSubrole() + ">>" + personal.getStatus() + ">>" + personal.getLogin() + ">>" + personal.getPassword();
                s_res += "<<";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetPersonalControlList(String[] arrStr) {
        s_res = "";
        transaction.begin();
        TypedQuery<PersonalEntity> q = entityManager.createQuery("SELECT e from PersonalEntity e where e.role = 'control'", PersonalEntity.class);
        try {
            List<PersonalEntity> list = q.getResultList();
            for (PersonalEntity personal : list) {
                s_res += personal.getNameSername() + ">>" + personal.getContacts() + ">>" + personal.getEmail() + ">>" + personal.getSubrole() + ">>" + personal.getStatus() + ">>" + personal.getLogin() + ">>" + personal.getPassword();
                s_res += "<<";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddTask(String[] arrStr) {
        transaction.begin();

        TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name " +
                "and e.personalByResponsableId.nameSername =:responsableName", TasksEntity.class);
        queryT.setParameter("name", arrStr[1]);
        queryT.setParameter("responsableName", arrStr[2]);
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
                if(personal.getNameSername().equals(arrStr[2])) {
                    responsable_id = personal.getPersonalId();
                }
                else if (personal.getNameSername().equals(arrStr[4])) {
                    checker_id = personal.getPersonalId();
                }
            }
            if(!arrStr[6].equals("Не выбран")) {
                TypedQuery<ProjectsEntity> queryProj = entityManager.createQuery("SELECT e FROM ProjectsEntity e where " +
                        "e.name =:name", ProjectsEntity.class);
                queryProj.setParameter("name", arrStr[6]);
                task.setProjectId(queryProj.getSingleResult().getProjectsId());
            }
            if(!arrStr[7].equals("выбран")) {
                System.out.println(arrStr[7]);
                TypedQuery<ProcessesEntity> queryProc = entityManager.createQuery("SELECT e FROM ProcessesEntity e where " +
                        "e.processesId =:id", ProcessesEntity.class);
                queryProc.setParameter("id", Integer.parseInt(arrStr[7]));
                task.setProcessId(queryProc.getSingleResult().getProcessesId());
            }
            if(!arrStr[8].equals("Не выбран")) {
                TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e where " +
                        "e.name =:name", ClientsEntity.class);
                queryC.setParameter("name", arrStr[8]);
                task.setClientId(queryC.getSingleResult().getClientsId());
            }
            //=============================/Data Get==============================
            task.setName(arrStr[1]);
            System.out.println(responsable_id);
            task.setResponsableId(responsable_id);
            task.setDescription(arrStr[3]);
            task.setCheckerId(checker_id);
            task.setDeadline(Date.valueOf(LocalDate.parse(arrStr[5], formatter2)));
            task.setStatus("Назначена");
            task.setCreationDate(Date.valueOf(LocalDate.parse(arrStr[9], formatter)));
            entityManager.persist(task);

            queryT = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name" +
                    " and e.responsableId =:id", TasksEntity.class);
            queryT.setParameter("name", arrStr[1]);
            queryT.setParameter("id", responsable_id);
            JournalsEntity journal = new JournalsEntity();
            journal.setTaskId(queryT.getSingleResult().getTasksId());
            entityManager.persist(journal);
            s_res = "1/" + "\r";
            //===============================Notification==========================
        } else {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetProjectList(String[] arrStr) {
        try {
            s_res = "";
            transaction.begin();
            TypedQuery<ProjectsEntity> query = entityManager.createQuery("SELECT e FROM ProjectsEntity e", ProjectsEntity.class);
            List<ProjectsEntity> projectsList = new ArrayList<>();
            projectsList = query.getResultList();
            for (ProjectsEntity project : projectsList) {
                s_res += project.getName() + "<<" + project.getDescription() + "<<" + project.getResponsableId() + "<<"
                        + project.getDeadline() + "<<" + project.getStatus();
                s_res += ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetProcessList(String[] arrStr) {
        try {
            s_res = "";
            transaction.begin();
            TypedQuery<ProcessesEntity> query = entityManager.createQuery("SELECT e FROM ProcessesEntity e", ProcessesEntity.class);
            List<ProcessesEntity> processesList = new ArrayList<>();
            processesList = query.getResultList();
            for (ProcessesEntity process : processesList) {
                s_res += process.getProcessesId() + "<<" + process.getStatus() + "<<" + process.getClientId() + "<<"
                        + process.getResponsableId() + "<<" + process.getCheckerId() + "<<" + process.getPayment() + "<<"
                        + process.getDescription() + "<<" + process.getProjectId();
                s_res += ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            e.printStackTrace();
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetTaskInfo(String[] arrStr) {
        try {
            transaction.begin();
            TypedQuery<TasksEntity> query = entityManager.createQuery("SELECT e FROM TasksEntity e WHERE e.name =:name and " +
                    "e.personalByResponsableId.nameSername =:responsableName", TasksEntity.class);
            query.setParameter("name", arrStr[1]);
            query.setParameter("responsableName", arrStr[2]);
            TasksEntity task = new TasksEntity();
            task = query.getSingleResult();
            s_res = task.getName() + "<<" + task.getPersonalByResponsableId().getNameSername() + "<<" + task.getDescription() +
                    "<<" + task.getPersonalByCheckerId().getNameSername() + "<<" + task.getDeadline().toString().split(" ")[0] +
                    "<<" + task.getStatus() + "<<" + task.getCreationDate().toString().split(" ")[0] +
                    "<<" + task.getPriority() + ">>";
            List<BusinessEntity> businessEntityList = (List<BusinessEntity>) task.getBusinessesByTasksId();
            List<JournalsEntity> journalsEntityList = (List<JournalsEntity>) task.getJournalsByTasksId();
            List<CommentsEntity> commentsEntityList = (List<CommentsEntity>) journalsEntityList.get(0).getCommentsByJournalsId();

            for(BusinessEntity business: businessEntityList) {
                s_res += business.getBusinessId() + "^^" + business.getName() + " от "
                        + business.getDate().toString().split("T")[0] + "^^" + business.getStatus() + "<<";
            }
            s_res += ">>";
            for(CommentsEntity comment: commentsEntityList) {
                s_res += comment.getText() + "^^" + comment.getPersonalBySenderId().getNameSername() + "^^" + comment.getDate() + "^^" + comment.getPersonalBySenderId().getLogin() + "<<";
            }
            s_res += ">>";
            s_res += "\r";
        }
        catch (Exception e) {
            s_res = "null" + "\r";
            throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object UpdateTaskInfo(String[] arrStr) {
        try {
            transaction.begin();
            System.out.println(arrStr[0]);
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
            List<PersonalEntity> pers = queryP.getResultList();
            int responsable_id = 0, checker_id = 0;
            for(PersonalEntity personal: pers) {
                if(personal.getNameSername().equals(arrStr[2])) {
                    responsable_id = personal.getPersonalId();
                }
                else if (personal.getNameSername().equals(arrStr[4])) {
                    checker_id = personal.getPersonalId();
                }
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<TasksEntity> query = entityManager.createQuery("SELECT e FROM TasksEntity e where e.name =:name and" +
                    " e.personalByResponsableId.nameSername =:responsableName", TasksEntity.class);
            query.setParameter("name", arrStr[7]);
            query.setParameter("responsableName", arrStr[8]);
            TasksEntity resultTask = null;
            resultTask = query.getSingleResult();
            resultTask.setName(arrStr[1]);
            resultTask.setResponsableId(responsable_id);
            resultTask.setDescription(arrStr[3]);
            resultTask.setCheckerId(checker_id);
            resultTask.setPriority(arrStr[6]);
            java.util.Date date = new java.util.Date();
            if(arrStr[5].equals("null")) {
                date = null;
            }
            else {
                date = Date.valueOf(LocalDate.parse(arrStr[5], formatter));
            }
            resultTask.setDeadline((Date) date);
            entityManager.merge(resultTask);
            s_res = "success" + "\r";
            //================================Notification==========================
        }
        catch (Exception e) {
            System.out.println(e);
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetChatsList(String[] arrStr) {
        try {
            globalImageList = new ArrayList<>();
            s_res = "";
            transaction.begin();
            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e" +
                    " inner join ChatMembersEntity b on b.chatId = e.chatsId" +
                    " where b.personalByPersonalId.nameSername =: nameSername", ChatsEntity.class);
            query.setParameter("nameSername", arrStr[1]);
            List<ChatsEntity> chatsList = new ArrayList<>();
            chatsList = query.getResultList();
            System.out.println(chatsList.get(0).getName());
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
                    BufferedImage image = ImageIO.read(getClass().getResource("/images/" + chat.getImageName()));
                    globalImageList.add(image);
                }
                catch (Exception e) {
                    System.out.println(e);
                    //BufferedImage image = ImageIO.read(new File("/images/1.png"));
                    BufferedImage image = ImageIO.read(getClass().getResource("/images/1.png"));
                    globalImageList.add(image);
                }

                TypedQuery<MessageStatusEntity> query2 = entityManager.createQuery("SELECT COUNT(e)" +
                        " from MessageStatusEntity e where e.personalByPersonalId.nameSername =:personalId and e.chatId =:chatId" +
                                " and e.status = 'Не прочитано'", MessageStatusEntity.class);

                query2.setParameter("personalId", arrStr[1]);
                query2.setParameter("chatId", chat.getChatsId());

                s_res += chat.getName() + "<<" + senderName + "<<" + sendText + "<<" + sendTime
                        + "<<" + query2.getSingleResult();
                s_res += ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            e.printStackTrace();
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddChat(String[] arrStr, BufferedImage image) {
        try {
            transaction.begin();
            //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
            query.setParameter("name", arrStr[1]);
            List<ChatsEntity> resultChat = null;
            resultChat = query.getResultList();
            if(resultChat.isEmpty()) {
                ChatsEntity chat = new ChatsEntity();
                chat.setName(arrStr[1]);
                chat.setDescription(arrStr[2]);

                String newFileName = CreateImageName();
                chat.setImageName(newFileName);
                ImageIO.write(image, "PNG", new File(getClass().getResource("/images/" + newFileName).toURI()));
                entityManager.persist(chat);

                query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
                query.setParameter("name", arrStr[1]);

                List<Integer> personalIdArr = new ArrayList<>();

                for(int i = 3; i < arrStr.length - 1; i++) {
                    TypedQuery<PersonalEntity> query2 = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
                            " where e.nameSername =:nameSername", PersonalEntity.class);
                    query2.setParameter("nameSername", arrStr[i]);
                    ChatMembersEntity entity = new ChatMembersEntity();
                    entity.setChatId(query.getSingleResult().getChatsId());
                    entity.setPersonalId(query2.getSingleResult().getPersonalId());
                    entityManager.persist(entity);
                }
                //resultPersonal.setRegDate(Date.valueOf(LocalDate.parse(arrStr[10], formatter)));

                s_res = "success" + "\r";
            }
            else {
                s_res = "null" + "\r";
            }
            //================================Notification==========================
        }
        catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetChatMessages(String[] arrStr) {
        try {

            globalImageList = new ArrayList<>();
            globalNameSernameList = new ArrayList<>();
            s_res = "";
            transaction.begin();
            TypedQuery<ChatsEntity> queryChat = entityManager.createQuery("SELECT e FROM ChatsEntity e" +
                    " where e.name =:name", ChatsEntity.class);
            queryChat.setParameter("name", arrStr[1]);

            //=====================================================================
            TypedQuery<ChatMembersEntity> queryMembers = entityManager.createQuery("SELECT e FROM ChatMembersEntity e " +
                    "where e.chatId =:chatId", ChatMembersEntity.class);
            queryMembers.setParameter("chatId", queryChat.getSingleResult().getChatsId());
            List<ChatMembersEntity> membersList = queryMembers.getResultList();
            for(ChatMembersEntity member: membersList) {
                try {
                    //BufferedImage image = ImageIO.read(new File("/resources/images/" + chat.getImageName()));
                    BufferedImage image = ImageIO.read(getClass().getResource(
                            "/images/"
                                    + member.getPersonalByPersonalId().getImageName()));
                    globalImageList.add(image);
                    globalNameSernameList.add(member.getPersonalByPersonalId().getNameSername());
                } catch (Exception e) {
                    e.printStackTrace();
                    //BufferedImage image = ImageIO.read(new File("/images/1.png"));
                    BufferedImage image = ImageIO.read(getClass().getResource(
                            "/images/1.png"));
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
            s_res += globalNameSernameList.size();
            for (MessagesEntity message : messagesList) {
                if(!message.getDate().toString().equals(dateChecker)) {
                    s_res += "^^" + message.getDate().toString() + ">>";
                    dateChecker = message.getDate().toString();
                }
                s_res += message.getText() + "<<" + message.getTime() + "<<" + message.getPersonalBySenderId().getNameSername();
                s_res += ">>";
            }
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
            //throw new RuntimeException(e);
        }
        transaction.commit();
        entityManager.clear();
        s_res += "\r";
        return s_res;
    }

    public Object GetChatMessagesNames(String[] arrStr) {
        s_res = "";
        for(String i: globalNameSernameList) {
            s_res += i + ">>";
        }
        s_res += "\r";
        return s_res;
    }

    public Object AddMessage(String[] arrStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            s_res = "";
            transaction.begin();


            TypedQuery<ChatsEntity> query = entityManager.createQuery("SELECT e FROM ChatsEntity e where e.name =:name", ChatsEntity.class);
            query.setParameter("name", arrStr[4]);
            ChatsEntity chat = query.getSingleResult();
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e where e.nameSername =:name", PersonalEntity.class);
            queryP.setParameter("name", arrStr[3]);
            MessagesEntity message = new MessagesEntity();
            message.setText(arrStr[1]);
            message.setDate(Date.valueOf(LocalDate.parse(arrStr[2], formatter)));
            message.setSenderId(queryP.getSingleResult().getPersonalId());
            message.setChatId(chat.getChatsId());
            message.setTime(Time.valueOf(arrStr[5]));
            entityManager.persist(message);
            entityManager.clear();
            s_res = "success" + "\r";

            TypedQuery<MessagesEntity> queryM = entityManager.createQuery("SELECT e FROM MessagesEntity e order by e.messagesId desc", MessagesEntity.class);
            List<MessagesEntity> queryMResult = queryM.getResultList();

            TypedQuery<ChatMembersEntity> queryCM = entityManager.createQuery("SELECT e FROM ChatMembersEntity e where e.chatId =:chatID" +
                    " and e.personalByPersonalId.nameSername <>:nameSername", ChatMembersEntity.class);
            queryCM.setParameter("nameSername", arrStr[3]);
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
            s_res = "null" + "\r";
            e.printStackTrace();
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object DeleteMessageStatus(String[] arrStr) {
        try {
            s_res = "";
            transaction.begin();
            TypedQuery<MessageStatusEntity> query = entityManager.createQuery("SELECT e FROM MessageStatusEntity e" +
                    " WHERE e.chatByChatId.name = :chatName" +
                    " and e.personalByPersonalId.nameSername =:nameSername", MessageStatusEntity.class);
            query.setParameter("chatName", arrStr[1]);
            query.setParameter("nameSername", arrStr[2]);
            List<MessageStatusEntity> statusesList = query.getResultList();
            for(MessageStatusEntity status: statusesList) {
                entityManager.remove(status);
            }
           s_res = "succes" + "\r";
        } catch (Exception e) {
            s_res = "null" + "\r";
            e.printStackTrace();
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetProjectsList(String[] arrStr) {
        s_res = "";
        transaction.begin();
        TypedQuery<ProjectsEntity> q = entityManager.createQuery("SELECT e from ProjectsEntity e", ProjectsEntity.class);
        try {
            List<ProjectsEntity> list = q.getResultList();
            for (ProjectsEntity project : list) {
                TypedQuery<TasksEntity> q2 = entityManager.createQuery("SELECT e from TasksEntity e where " +
                        "e.projectId =:id", TasksEntity.class);
                q2.setParameter("id", project.getProjectsId());
                List<TasksEntity> tasksEntities = q2.getResultList();
                int completeBCount = 0;
                for(TasksEntity task: tasksEntities) {
                    if (!task.getStatus().equals("Назначена")) {
                        completeBCount++;
                    }
                }
                s_res += project.getName() + "<<" + (completeBCount + "/" + tasksEntities.size()) + "<<"
                        + project.getCreationDate() + "<<" + project.getDeadline().toString() + "<<"
                        + project.getProjectMembersByProjectsId().iterator().next().getTeamName() + "<<"
                        + project.getPersonalByCheckerId().getNameSername() + "<<" + project.getStatus() + "<<" + project.getProjectsId();
                s_res += ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddProject(String[] arrStr) {
        transaction.begin();

        TypedQuery<ProjectsEntity> queryT = entityManager.createQuery("SELECT e FROM ProjectsEntity e where e.name =:name",
                ProjectsEntity.class);
        queryT.setParameter("name", arrStr[1]);
        List<ProjectsEntity> checkProjects = queryT.getResultList();

        if (checkProjects.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            ProjectsEntity project = new ProjectsEntity();
            //==============================Data Get==============================
            TypedQuery<PersonalEntity> queryPers = entityManager.createQuery("SELECT e FROM PersonalEntity e", PersonalEntity.class);
            List<PersonalEntity> personalList = queryPers.getResultList();
            List<Integer> responsable_ids = new ArrayList<>();
            String[] recieved_names = arrStr[2].split(">>");
                int checker_id = 0;
                for (PersonalEntity personal : personalList) {
                    if(!arrStr[2].equals("Команда существует")) {
                        for (String i : recieved_names) {
                            if (personal.getNameSername().equals(i)) {
                                responsable_ids.add(personal.getPersonalId());
                            }
                        }
                    }
                    if (personal.getNameSername().equals(arrStr[4])) {
                        checker_id = personal.getPersonalId();
                    }
                }
            //=============================/Data Get==============================
            project.setName(arrStr[1]);
            project.setDescription(arrStr[3]);
            project.setCheckerId(checker_id);
            project.setDeadline(Date.valueOf(LocalDate.parse(arrStr[5], formatter)));
            project.setStatus("Активен");
            project.setCreationDate(Date.valueOf(LocalDate.now()));
            entityManager.persist(project);

            queryT = entityManager.createQuery("SELECT e FROM ProjectsEntity e where e.name =:name",
                    ProjectsEntity.class);
            queryT.setParameter("name", arrStr[1]);
            JournalsEntity journal = new JournalsEntity();
            journal.setProjectId(queryT.getSingleResult().getProjectsId());
            entityManager.persist(journal);

            if(arrStr[2].equals("Команда существует")) {
                TypedQuery<ProjectMembersEntity> queryPM = entityManager.createQuery("SELECT e FROM ProjectMembersEntity e" +
                        " WHERE e.teamName =:teamName", ProjectMembersEntity.class);
                queryPM.setParameter("teamName", arrStr[6]);
                List<ProjectMembersEntity> mem = queryPM.getResultList();
                for(ProjectMembersEntity me: mem) {
                    ProjectMembersEntity m = new ProjectMembersEntity();
                    m.setTeamName(me.getTeamName());
                    m.setPersonalId(me.getPersonalId());
                    m.setProjectId(queryT.getSingleResult().getProjectsId());
                    entityManager.persist(m);
                }
            }
            else {
                for (Integer i : responsable_ids) {
                    ProjectMembersEntity member = new ProjectMembersEntity();
                    member.setProjectId(queryT.getSingleResult().getProjectsId());
                    member.setPersonalId(i);
                    member.setTeamName(arrStr[6]);
                    entityManager.persist(member);
                }
            }
            s_res = "1/" + "\r";
            //===============================Notification==========================
        } else {
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetTeamsList(String[] arrStr) {
        s_res = "";
        transaction.begin();
        TypedQuery<ProjectMembersEntity> q = entityManager.createQuery("SELECT distinct e.teamName from ProjectMembersEntity e",
                ProjectMembersEntity.class);
        try {
            List<String> list = Collections.singletonList(q.getResultList().toString());
            for (String i : list) {
                i = i.replaceAll("[{}\\[\\]]", "");
                s_res += i + ">>";
            }
            s_res += "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object DeletePersonal(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            TypedQuery<PersonalEntity> query = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
                    " where e.nameSername =:nameSername", PersonalEntity.class);
            query.setParameter("nameSername", arrStr[1]);
            PersonalEntity personal = query.getSingleResult();
            entityManager.remove(personal);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddBusiness(String[] arrStr) {
        s_res = "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        transaction.begin();
        try {
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
                    " WHERE e.nameSername =:nameSername", PersonalEntity.class);
            queryP.setParameter("nameSername", arrStr[6]);
            PersonalEntity personal = queryP.getSingleResult();
            BusinessEntity businessEntity = new BusinessEntity();
            businessEntity.setName(arrStr[1]);
            businessEntity.setDate(LocalDateTime.parse(arrStr[2], formatter));
            businessEntity.setStatus(arrStr[3]);
            switch (arrStr[4]) {
                case "Клиент": {
                    TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e" +
                            " WHERE e.name =:name", ClientsEntity.class);
                    queryC.setParameter("name", arrStr[5]);
                    businessEntity.setClientId(queryC.getSingleResult().getClientsId());
                    break;
                }
                case "Процесс": {
                    businessEntity.setProcessId(Integer.parseInt(arrStr[5]));
                    break;
                }
                case "Задача": {
                    TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
                            " WHERE e.name =:name", TasksEntity.class);
                    queryT.setParameter("name", arrStr[5]);
                    businessEntity.setTaskID(queryT.getSingleResult().getTasksId());
                    break;
                }
            }
            businessEntity.setResponsableId(personal.getPersonalId());
            entityManager.persist(businessEntity);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object GetBusinessInfo(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            BusinessEntity business = entityManager.getReference(BusinessEntity.class, Integer.parseInt(arrStr[1]));
            String[] dateSplit = business.getDate().toString().split("T");
            s_res = business.getName() + ">>" + dateSplit[0] + ">>" + dateSplit[1] + ">>" + business.getDescription()
                    + ">>" + business.getPlace() + ">>" + business.getPersonalByResponsableId().getNameSername()
                    + ">>" + business.getStatus() + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object CompleteBusiness(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            BusinessEntity business = entityManager.getReference(BusinessEntity.class, Integer.parseInt(arrStr[1]));
            business.setStatus("Завершено");
            entityManager.merge(business);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object DeleteBusiness(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            BusinessEntity business = entityManager.getReference(BusinessEntity.class, Integer.parseInt(arrStr[1]));
            entityManager.remove(business);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object UpdateBusiness(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            BusinessEntity business = entityManager.getReference(BusinessEntity.class, Integer.parseInt(arrStr[1]));
            business.setDescription(arrStr[2]);
            business.setPlace(arrStr[3]);
            entityManager.merge(business);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object AddComment(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            TypedQuery<JournalsEntity> queryJ;
            TypedQuery<PersonalEntity> queryP = entityManager.createQuery("SELECT e FROM PersonalEntity e" +
                    " where e.nameSername =:nameSername", PersonalEntity.class);
            queryP.setParameter("nameSername", arrStr[1]);
            int personalId = queryP.getSingleResult().getPersonalId();
            CommentsEntity comment = new CommentsEntity();
            switch (arrStr[4]) {
                case "Клиент": {
                    TypedQuery<ClientsEntity> queryC = entityManager.createQuery("SELECT e FROM ClientsEntity e" +
                            " where e.name =:name", ClientsEntity.class);
                    queryC.setParameter("name", arrStr[3]);
                    queryJ = entityManager.createQuery("SELECT e FROM JournalsEntity e" +
                            " where e.clientId =:id", JournalsEntity.class);
                    queryJ.setParameter("id", queryC.getSingleResult().getClientsId());
                    comment.setDate(Date.valueOf(LocalDate.now()));
                    comment.setText(arrStr[2]);
                    comment.setSenderId(personalId);
                    comment.setJournalId(queryJ.getSingleResult().getJournalsId());
                    break;
                }
                case "Задача": {
                    TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
                            " where e.name =:name", TasksEntity.class);
                    queryT.setParameter("name", arrStr[3]);
                    queryJ = entityManager.createQuery("SELECT e FROM JournalsEntity e" +
                            " where e.taskId =:id", JournalsEntity.class);
                    queryJ.setParameter("id", queryT.getSingleResult().getTasksId());
                    comment.setDate(Date.valueOf(LocalDate.now()));
                    comment.setText(arrStr[2]);
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
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object DeleteClient(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            ClientsEntity client = entityManager.getReference(ClientsEntity.class, Integer.parseInt(arrStr[1]));
            entityManager.remove(client);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object ChangePaymentStatus(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            PaymentsEntity payment = entityManager.getReference(PaymentsEntity.class, Integer.parseInt(arrStr[1]));
            payment.setStatus(arrStr[2]);
            entityManager.merge(payment);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object DeletePayment(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            PaymentsEntity payment = entityManager.getReference(PaymentsEntity.class, Integer.parseInt(arrStr[1]));
            entityManager.remove(payment);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object ChangeTaskStatus(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
                    " WHERE e.name =:name AND e.personalByResponsableId.nameSername =:nameSername", TasksEntity.class);
            queryT.setParameter("name", arrStr[1]);
            queryT.setParameter("nameSername", arrStr[2]);
            TasksEntity task = queryT.getSingleResult();
            task.setStatus(arrStr[3]);
            entityManager.merge(task);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
        }
        transaction.commit();
        entityManager.clear();
        return s_res;
    }

    public Object DeleteTask(String[] arrStr) {
        s_res = "";
        transaction.begin();
        try {
            TypedQuery<TasksEntity> queryT = entityManager.createQuery("SELECT e FROM TasksEntity e" +
                    " WHERE e.name =:name AND e.personalByResponsableId.nameSername =:nameSername", TasksEntity.class);
            queryT.setParameter("name", arrStr[1]);
            queryT.setParameter("nameSername", arrStr[2]);
            TasksEntity task = queryT.getSingleResult();
            entityManager.remove(task);
            s_res = "success" + "\r";
        } catch (Exception e) {
            e.printStackTrace();
            s_res = "null" + "\r";
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
