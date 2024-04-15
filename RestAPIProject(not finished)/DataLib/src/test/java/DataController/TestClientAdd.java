package DataController;

import entity.ClientsEntity;
import jakarta.persistence.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class TestClientAdd {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction transaction;
    private static DataControllerSql dbManager;

    private JSONObject client;

    @BeforeClass
    public static void setUp() throws SQLException, ClassNotFoundException {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        dbManager = new DataControllerSql();
        dbManager.entityManager = entityManager;
    }

    @Test
    public void testAddClientToDatabase() throws JSONException, SQLException, ClassNotFoundException {
        if (entityManager != null && entityManager.getTransaction() != null) {
            client = new JSONObject();
            client.put("name", "testName");
            client.put("responsibleName", "2");
            client.put("contacts", "testContacts");
            client.put("email","testEmail");
            client.put("address", "testAddress");
            client.put("description", "testDescription");
            client.put("type", "testType");
            client.put("workType", "testWorkType");
            client.put("regDate", "2023-11-12");
            dbManager = new DataControllerSql();
            //dbManager.AddClient(client);
            entityManager.getTransaction().begin();
            TypedQuery<ClientsEntity> q = entityManager.createQuery("SELECT u FROM ClientsEntity u WHERE u.name = 'testName'", ClientsEntity.class);
            entityManager.getTransaction().commit();
            assertNotNull(entityManager.find(ClientsEntity.class, q.getSingleResult().getClientsId()));
        } else {
            fail("EntityManager or EntityTransaction is null");
        }
    }

    @After
    public void tearDownTestData() {
        if (entityManager != null && entityManager.getTransaction() != null) {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM ClientsEntity u WHERE u.name = 'testName'").executeUpdate();
            entityManager.getTransaction().commit();
        } else {
            fail("EntityManager or EntityTransaction is null");
        }
    }

    @AfterClass
    public static void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}

