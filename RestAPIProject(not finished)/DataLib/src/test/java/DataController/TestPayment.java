package DataController;

import entity.ClientsEntity;
import entity.ItemsEntity;
import entity.PaymentsEntity;
import entity.PersonalEntity;
import jakarta.persistence.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.runners.MethodSorters;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPayment {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static EntityTransaction transaction;
    private static DataControllerSql dbManager;
    static JSONObject payment;
    static PaymentsEntity paymentEntity;
    static JSONObject itemJSON;
    static ItemsEntity itemEntity;

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
            payment = new JSONObject();

            payment.put("creationDate", "2023-12-12");
            payment.put("deadline", "2023-12-13");
            payment.put("subInfo", "testSubInfo");
            payment.put("paymenterName", "TestClient");
            payment.put("receiverName", "TestClient");
            payment.put("itemID", "1");
            payment.put("amount", "12");
            payment.put("finalPrice", "122");
            payment.put("status", "Оплачен");
            payment.put("paymentImageName", "Payment_7_Check.png");
    }

    @Test
    public void TestAAddPayment() throws SQLException, ClassNotFoundException, JSONException {
        System.out.println("Commencing TestAddPayment");
        if (entityManager != null && entityManager.getTransaction() != null) {
            dbManager = new DataControllerSql();
            dbManager.AddPayment(payment);
            JSONObject paymentList = dbManager.GetPaymentList(payment);
            dbManager.entityManager.getTransaction().begin();
            TypedQuery<PaymentsEntity> q = dbManager.entityManager.createQuery("SELECT u FROM PaymentsEntity u order by u.paymentId asc", PaymentsEntity.class);
            List<PaymentsEntity> paymentsEntities = new ArrayList<PaymentsEntity>();
            paymentsEntities = q.getResultList();
            paymentEntity = paymentsEntities.get(paymentList.length() + 1);
            dbManager.entityManager.getTransaction().commit();
            assertNotNull(entityManager.find(PaymentsEntity.class, paymentEntity.getPaymentId()));
        } else {
            Assertions.fail("EntityManager or EntityTransaction is null");
        }
    }

    @Test
    public void TestBStatusChange() throws JSONException, SQLException, ClassNotFoundException {
        System.out.println("Commencing TestStatusChange");
        dbManager = new DataControllerSql();
        if (entityManager != null && entityManager.getTransaction() != null) {
            System.out.println(paymentEntity.getPaymentId());
            paymentEntity.setStatus("Оплачено");
            paymentEntity.setPaymentImageName(payment.getString("paymentImageName"));
            entityManager.getTransaction().begin();
            entityManager.merge(paymentEntity);
            TypedQuery<PaymentsEntity> q = entityManager.createQuery("SELECT u FROM PaymentsEntity u where u.paymentId =:paymentID", PaymentsEntity.class);
            q.setParameter("paymentID", paymentEntity.getPaymentId());
            String status;
            status = q.getSingleResult().getStatus();
            entityManager.getTransaction().commit();
            assertEquals("Оплачено", status);
        } else {
            Assertions.fail("EntityManager or EntityTransaction is null");
        }
    }

    @Test
    public void TestCAddItem() throws JSONException, SQLException, ClassNotFoundException {
        System.out.println("Commencing TestAddItem");
        if (entityManager != null && entityManager.getTransaction() != null) {
            itemJSON = new JSONObject();
            dbManager = new DataControllerSql();
            itemJSON.put("name", "testTestItem");
            itemJSON.put("articulate", "tti");
            itemJSON.put("price", "12");
            itemJSON.put("taxes", "3");
            itemJSON.put("measurement", "sm");
            dbManager.AddItem(itemJSON);
            entityManager.getTransaction().begin();
            TypedQuery<ItemsEntity> q = entityManager.createQuery("SELECT u from ItemsEntity u where u.name = 'testTestItem'", ItemsEntity.class);
            itemEntity = q.getSingleResult();
            entityManager.getTransaction().commit();
            assertNotNull(itemEntity);
        } else {
            Assertions.fail("EntityManager or EntityTransaction is null");
        }
    }

    @Test
    public void TestDGetCheque() throws JSONException, SQLException, ClassNotFoundException {
        System.out.println("Commencing TestGetCheque");
        if (entityManager != null && entityManager.getTransaction() != null) {
            dbManager = new DataControllerSql();
            payment.put("paymentID", String.valueOf(paymentEntity.getPaymentId()));

            assertNotNull(dbManager.GetPaymentCheck(payment));
        } else {
            Assertions.fail("EntityManager or EntityTransaction is null");
        }
    }

    @AfterClass
    public static void tearDown() throws SQLException, ClassNotFoundException {
        if (entityManager != null && entityManager.getTransaction() != null) {
            System.out.println("Commencing TearDown");
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("Delete from PaymentsEntity u where u.paymentId =:paymentID");
            query.setParameter("paymentID", paymentEntity.getPaymentId());
            query.executeUpdate();
            query = entityManager.createQuery("Delete from ItemsEntity u where u.name =:name");
            query.setParameter("name", itemEntity.getName());
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

