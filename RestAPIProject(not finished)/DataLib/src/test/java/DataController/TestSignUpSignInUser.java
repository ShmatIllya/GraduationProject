package DataController;

import entity.PersonalEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class TestSignUpSignInUser {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction transaction;
    private static DataControllerSql dbManager;

    private JSONObject personalData;

    @BeforeClass
    public static void setUp() throws SQLException, ClassNotFoundException {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        dbManager = new DataControllerSql();
        dbManager.entityManager = entityManager;
    }

    @Before
    public void setUpData() throws JSONException {
        personalData = new JSONObject();
        personalData.put("login","testLogin");
        personalData.put("password","testPassword");
        personalData.put("nameSername", "testNameSername");
        personalData.put("contacts", "testContacts");
        personalData.put("email", "testEmail");
    }


    @Test
    public void testSignUpUser() throws JSONException, SQLException, ClassNotFoundException {
        if (entityManager != null && entityManager.getTransaction() != null) {
            dbManager = new DataControllerSql();
            dbManager.Registration(personalData);
            assertNotNull(dbManager.GetPersonalInfo(personalData));
        } else {
            fail("EntityManager or EntityTransaction is null");
        }
    }

    @Test
    public void testSignInUser() throws JSONException, SQLException, ClassNotFoundException {
        if (entityManager != null && entityManager.getTransaction() != null) {
            dbManager = new DataControllerSql();
            JSONObject result = dbManager.Login(personalData);
            assertEquals("null", result.getString("response"));
        } else {
            fail("EntityManager or EntityTransaction is null");
        }
    }

    @After
    public void tearDownData() {
        if (entityManager != null && entityManager.getTransaction() != null) {
            entityManager.getTransaction().begin();
            entityManager.createQuery("DELETE FROM PersonalEntity u WHERE u.login = 'testlogin'").executeUpdate();
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
