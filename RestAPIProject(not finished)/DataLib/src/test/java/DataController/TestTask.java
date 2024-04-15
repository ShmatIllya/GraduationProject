package DataController;

import entity.ItemsEntity;
import entity.PaymentsEntity;
import entity.TasksEntity;
import jakarta.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestTask {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction transaction;
    private static DataControllerSql dbManager;
    static JSONObject task;
    static TasksEntity taskEntity;


    @BeforeClass
    public static void setUp() throws SQLException, ClassNotFoundException {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        dbManager = new DataControllerSql();
        dbManager.entityManager = entityManager;

    }

    @Before
    public void setUpTestData() throws JSONException {
        task = new JSONObject();

        task.put("name", "testTestName");
        task.put("responsibleName", "f f f");
        task.put("checkerName", "2");
        task.put("projectName", "–ù–µ –≤—ã–±—Ä–∞–Ω");
        task.put("processName", "–ù–µ –≤—ã–±—Ä–∞–Ω");
        task.put("clientName", "–ù–µ –≤—ã–±—Ä–∞–Ω");
        task.put("description", "testDescription");
        task.put("deadline", "2023-12-13");
        task.put("creationDate", "2023-12-12 12:12");
    }

    @Test
    public void TestAAddTask() throws SQLException, ClassNotFoundException, JSONException {
        System.out.println("Commencing TestAddTask");
        if (entityManager != null && entityManager.getTransaction() != null) {
            dbManager = new DataControllerSql();
            //dbManager.AddTask(task);
            JSONObject taskList = dbManager.GetTasksList(task);
            dbManager.entityManager.getTransaction().begin();
            TypedQuery<TasksEntity> q = dbManager.entityManager.createQuery("SELECT u FROM TasksEntity u order by u.tasksId asc", TasksEntity.class);
            List<TasksEntity> tasksEntities = new ArrayList<TasksEntity>();
            tasksEntities = q.getResultList();
            taskEntity = tasksEntities.get(taskList.length() + 1);
            dbManager.entityManager.getTransaction().commit();
            assertNotNull(entityManager.find(TasksEntity.class, taskEntity.getTasksId()));
        } else {
            Assertions.fail("EntityManager or EntityTransaction is null");
        }
    }

    @Test
    public void TestBStatusChange() throws JSONException, SQLException, ClassNotFoundException {
        System.out.println("Commencing TestStatusChange");
        dbManager = new DataControllerSql();
        if (entityManager != null && entityManager.getTransaction() != null) {
            taskEntity.setStatus("¬˚ÔÓÎÌÂÌ");
            entityManager.getTransaction().begin();
            entityManager.merge(taskEntity);
            TypedQuery<TasksEntity> q = entityManager.createQuery("SELECT u FROM TasksEntity u where u.tasksId =:taskID", TasksEntity.class);
            q.setParameter("taskID", taskEntity.getTasksId());
            String status;
            status = q.getSingleResult().getStatus();
            entityManager.getTransaction().commit();
            assertEquals("¬˚ÔÓÎÌÂÌ", status);
        } else {
            Assertions.fail("EntityManager or EntityTransaction is null");
        }
    }

    @AfterClass
    public static void tearDown() throws SQLException, ClassNotFoundException {
        if (entityManager != null && entityManager.getTransaction() != null) {
            System.out.println("Commencing TearDown");
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("Delete from TasksEntity u where u.tasksId =:taskID");
            query.setParameter("taskID", taskEntity.getTasksId());
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } else {
            fail("EntityManager or EntityTransaction is null");
        }
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
