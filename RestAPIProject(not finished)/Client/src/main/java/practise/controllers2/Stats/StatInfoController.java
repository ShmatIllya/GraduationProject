package practise.controllers2.Stats;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import entity.TasksEntity;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.controllers2.Stats.Representations.*;
import practise.singleton.Singleton;

import java.sql.Date;
import java.sql.Struct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StatInfoController {
    public StackPane stackPane;
    public JFXComboBox<String> categoryComboBox;
    public MFXDatePicker datePicker;
    public MFXDatePicker datePicker2;
    public Button submitButton;
    public JFXButton cancelButton;
    public BarChart barChart;
    public String dataType;
    ObservableList<PersonalRepresentation> personalList = FXCollections.observableArrayList();
    ObservableList<ClientRepresentation> clientsList = FXCollections.observableArrayList();
    ObservableList<PaymentRepresentation> paymentsList = FXCollections.observableArrayList();
    ObservableList<TaskRepresentation> tasksList = FXCollections.observableArrayList();
    ObservableList<ProjectRepresentation> projectsList = FXCollections.observableArrayList();
    ObservableList<BusinessRepresentation> businessList = FXCollections.observableArrayList();

    public void InitController(String dataType) throws JSONException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dataType = dataType;
        ObservableList<String> comboBoxValues = FXCollections.observableArrayList();
        switch (dataType) {
            case "Персонал": {
                JSONObject pers = new JSONObject();
                JSONObject tempString = Singleton.getInstance().getDataController().GetPersonalGeneralInfo(pers);
                if (!tempString.getString("response").equals("null")) {
                    JSONArray resultSet = tempString.getJSONArray("personalList");
                    for (int i = 0; i < resultSet.length(); i++) {
                        JSONObject personal = resultSet.getJSONObject(i);
                        personalList.add(new PersonalRepresentation(personal.getString("nameSurname"),
                                personal.getString("role"), personal.getString("subRole"),
                                personal.getString("status"), Integer.parseInt(personal.getString("businessCount")),
                                Integer.parseInt(personal.getString("clientCount")),
                                Integer.parseInt(personal.getString("projectCount")),
                                Integer.parseInt(personal.getString("taskCount"))));
                    }
                    comboBoxValues.setAll("Роль", "Должность", "Статус", "Кол-во задач", "Кол-во клиентов",
                            "Кол-во проектов", "Кол-во дел");
                    categoryComboBox.setItems(comboBoxValues);
                } else {
                    Label messageBox = new Label("Ошибка получения данных");
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                    return;
                }
                break;
            }
            case "Клиент": {
                JSONObject arrStr = new JSONObject();
                JSONObject tempString = Singleton.getInstance().getDataController().GetClientGeneralInfo(arrStr);
                if (!tempString.getString("response").equals("null")) {
                    JSONArray resultSet = tempString.getJSONArray("clientList");
                    for (int i = 0; i < resultSet.length(); i++) {
                        JSONObject client = resultSet.getJSONObject(i);
                        clientsList.add(new ClientRepresentation(client.getString("name"), client.getString("type"),
                                client.getString("workType"), Integer.parseInt(client.getString("businessCount")),
                                Integer.parseInt(client.getString("paymentCount")),
                                Integer.parseInt(client.getString("taskCount"))));
                    }
                    comboBoxValues.setAll("Вид клиента", "Тип производства", "Кол-во задач", "Кол-во счетов", "Кол-во дел");
                    categoryComboBox.setItems(comboBoxValues);
                } else {
                    Label messageBox = new Label("Ошибка получения данных");
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                    return;
                }
                break;
            }
            case "Счет": {
                JSONObject arrStr = new JSONObject();
                JSONObject tempString = Singleton.getInstance().getDataController().GetPaymentGeneralInfo(arrStr);
                if (!tempString.getString("response").equals("null")) {
                    JSONArray resultSet = tempString.getJSONArray("paymentList");
                    for (int i = 0; i < resultSet.length(); i++) {
                        JSONObject payment = resultSet.getJSONObject(i);
                        paymentsList.add(new PaymentRepresentation(Integer.parseInt(payment.getString("id")),
                                Integer.parseInt(payment.getString("finalPrice")), payment.getString("status"),
                                payment.getString("itemName")));
                    }
                    comboBoxValues.setAll("Цена", "Статус", "Товар");
                    categoryComboBox.setItems(comboBoxValues);
                } else {
                    Label messageBox = new Label("Ошибка получения данных");
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                    return;
                }
                break;
            }
            case "Задача": {
                JSONObject arrStr = new JSONObject();
                JSONObject tempString = Singleton.getInstance().getDataController().GetTaskGeneralInfo(arrStr);
                if (!tempString.getString("response").equals("null")) {
                    JSONArray resultSet = tempString.getJSONArray("taskList");
                    for (int i = 0; i < resultSet.length(); i++) {
                        JSONObject task = resultSet.getJSONObject(i);
                        tasksList.add(new TaskRepresentation(task.getString("name"), task.getString("status"),
                                task.getString("priority")));
                    }
                    comboBoxValues.setAll("Статус", "Приоритет");
                    categoryComboBox.setItems(comboBoxValues);
                } else {
                    Label messageBox = new Label("Ошибка получения данных");
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                    return;
                }
                break;
            }
            case "Проект": {
                JSONObject arrStr = new JSONObject();
                JSONObject tempString = Singleton.getInstance().getDataController().GetProjectGeneralInfo(arrStr);
                if (!tempString.getString("response").equals("null")) {
                    JSONArray resultSet = tempString.getJSONArray("projectList");
                    for (int i = 0; i < resultSet.length(); i++) {
                        JSONObject project = resultSet.getJSONObject(i);
                        projectsList.add(new ProjectRepresentation(project.getString("name"),
                                project.getString("status"), project.getString("trudozatraty"),
                                Integer.parseInt(project.getString("memberCount")),
                                Integer.parseInt(project.getString("taskCount"))));
                    }
                    comboBoxValues.setAll("Статус", "Трудозатраты", "Число выполняющих", "Число задач");
                    categoryComboBox.setItems(comboBoxValues);
                } else {
                    Label messageBox = new Label("Ошибка получения данных");
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                    return;
                }
                break;
            }
            case "Дело": {
                JSONObject arrStr = new JSONObject();
                JSONObject tempString = Singleton.getInstance().getDataController().GetBusinessGeneralInfo(arrStr);
                if (!tempString.getString("response").equals("null")) {
                    JSONArray resultSet = tempString.getJSONArray("taskList");
                    for (int i = 0; i < resultSet.length(); i++) {
                        JSONObject business = resultSet.getJSONObject(i);
                        businessList.add(new BusinessRepresentation(business.getString("name"),
                                business.getString("status")));
                    }
                    comboBoxValues.setAll("Статус");
                    categoryComboBox.setItems(comboBoxValues);
                } else {
                    Label messageBox = new Label("Ошибка получения данных");
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                    return;
                }
                break;
            }
        }
    }

    public void OnClose(ActionEvent event) {
        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(stackPane, 1, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Stage st = (Stage) submitButton.getScene().getWindow();
            st.close();
        });
    }

    public void OnSubmitButton(ActionEvent event) {
        XYChart.Series set = new XYChart.Series<>();
        barChart.getData().clear();

        Map<String,Integer> hm = new HashMap();

        switch (dataType) {
            case "Персонал": {
               switch (categoryComboBox.getSelectionModel().getSelectedItem()) {
                   case "Роль": {
                       for(PersonalRepresentation x: personalList) {
                           if(!hm.containsKey(x.getRole())){
                               hm.put(x.getRole(), 1);
                           } else {
                               hm.put(x.getRole(), hm.get(x.getRole()) + 1);
                           }
                       }
                       break;
                   }
                   case "Должность": {
                       for(PersonalRepresentation x: personalList) {
                           if(!hm.containsKey(x.getSubrole())){
                               hm.put(x.getSubrole(), 1);
                           } else {
                               hm.put(x.getSubrole(), hm.get(x.getSubrole()) + 1);
                           }
                       }
                       break;
                   }
                   case "Статус": {
                       for(PersonalRepresentation x: personalList) {
                           if(!hm.containsKey(x.getStatus())){
                               hm.put(x.getStatus(), 1);
                           } else {
                               hm.put(x.getStatus(), hm.get(x.getStatus()) + 1);
                           }
                       }
                       break;
                   }
                   case "Кол-во задач": {
                       for(PersonalRepresentation x: personalList) {
                           hm.put(x.getNameSername(), x.getTasksCount());
                       }
                       break;
                   }
                   case "Кол-во клиентов": {
                       for(PersonalRepresentation x: personalList) {
                           hm.put(x.getNameSername(), x.getClientsCount());
                       }
                       break;
                   }
                   case "Кол-во проектов": {
                       for(PersonalRepresentation x: personalList) {
                           hm.put(x.getNameSername(), x.getProjectsCount());
                       }
                       break;
                   }
                   case "Кол-во дел": {
                       for(PersonalRepresentation x: personalList) {
                           hm.put(x.getNameSername(), x.getBusinessCount());
                       }
                       break;
                   }
               }
                break;
            }
            case "Клиент": {
                switch (categoryComboBox.getSelectionModel().getSelectedItem()) {
                    case "Вид клиента": {
                        for (ClientRepresentation x : clientsList) {
                            if (!hm.containsKey(x.getType())) {
                                hm.put(x.getType(), 1);
                            } else {
                                hm.put(x.getType(), hm.get(x.getType()) + 1);
                            }
                        }
                        break;
                    }
                    case "Тип производства": {
                        for (ClientRepresentation x : clientsList) {
                            if (!hm.containsKey(x.getWork_type())) {
                                hm.put(x.getWork_type(), 1);
                            } else {
                                hm.put(x.getWork_type(), hm.get(x.getWork_type()) + 1);
                            }
                        }
                        break;
                    }
                    case "Кол-во задач": {
                        for(ClientRepresentation x: clientsList) {
                            hm.put(x.getName(), x.getTasksCount());
                        }
                        break;
                    }
                    case "Кол-во счетов": {
                        for(ClientRepresentation x: clientsList) {
                            hm.put(x.getName(), x.getPaymentCount());
                        }
                        break;
                    }
                    case "Кол-во дел": {
                        for(ClientRepresentation x: clientsList) {
                            hm.put(x.getName(), x.getBusinessCount());
                        }
                        break;
                    }
                }
                break;
            }
            case "Счет": {
                switch (categoryComboBox.getSelectionModel().getSelectedItem()) {
                    case "Цена": {
                        for (PaymentRepresentation x : paymentsList) {
                            hm.put("Счет " + x.getId(), x.getFinalPrice());
                        }
                        break;
                    }
                    case "Статус": {
                        for (PaymentRepresentation x : paymentsList) {
                            if (!hm.containsKey(x.getStatus())) {
                                hm.put(x.getStatus(), 1);
                            } else {
                                hm.put(x.getStatus(), hm.get(x.getStatus()) + 1);
                            }
                        }
                        break;
                    }
                    case "Товар": {
                        for (PaymentRepresentation x : paymentsList) {
                            if (!hm.containsKey(x.getItemName())) {
                                hm.put(x.getItemName(), 1);
                            } else {
                                hm.put(x.getItemName(), hm.get(x.getItemName()) + 1);
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case "Задача": {
                switch (categoryComboBox.getSelectionModel().getSelectedItem()) {
                    case "Статус": {
                        for (TaskRepresentation x : tasksList) {
                            if (!hm.containsKey(x.getStatus())) {
                                hm.put(x.getStatus(), 1);
                            } else {
                                hm.put(x.getStatus(), hm.get(x.getStatus()) + 1);
                            }
                        }
                        break;
                    }
                    case "Приоритет": {
                        for (TaskRepresentation x : tasksList) {
                            if (!hm.containsKey(x.getPriority())) {
                                hm.put(x.getPriority(), 1);
                            } else {
                                hm.put(x.getPriority(), hm.get(x.getPriority()) + 1);
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case "Проект": {
                switch (categoryComboBox.getSelectionModel().getSelectedItem()) {
                    case "Статус": {
                        for (ProjectRepresentation x : projectsList) {
                            if (!hm.containsKey(x.getStatus())) {
                                hm.put(x.getStatus(), 1);
                            } else {
                                hm.put(x.getStatus(), hm.get(x.getStatus()) + 1);
                            }
                        }
                        break;
                    }
                    case "Трудозатраты": {
                        for (ProjectRepresentation x : projectsList) {
                            hm.put(x.getName(), Integer.valueOf(x.getTrudozatraty()));
                        }
                        break;
                    }
                    case "Число выполняющих": {
                        for (ProjectRepresentation x : projectsList) {
                            hm.put(x.getName(), x.getMembersCount());
                        }
                        break;
                    }
                    case "Число задач": {
                        for (ProjectRepresentation x : projectsList) {
                            hm.put(x.getName(), Integer.valueOf(x.getTasksCount()));
                        }
                        break;
                    }
                }
                break;
            }
            case "Дело": {
                switch (categoryComboBox.getSelectionModel().getSelectedItem()) {
                    case "Статус": {
                        for (BusinessRepresentation x : businessList) {
                            if (!hm.containsKey(x.getStatus())) {
                                hm.put(x.getStatus(), 1);
                            } else {
                                hm.put(x.getStatus(), hm.get(x.getStatus()) + 1);
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        for(String i: hm.keySet().toArray(new String[hm.size()])) {
            set.getData().add(new XYChart.Data(i, hm.get(i)));
        }
        barChart.getData().addAll(set);
    }
}
