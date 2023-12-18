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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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

    public void InitController(String dataType) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.dataType = dataType;
        ObservableList<String> comboBoxValues = FXCollections.observableArrayList();
        switch (dataType) {
            case "Персонал": {
                String[] arrStr = {"GetPersonalGeneralInfo"};
                String tempString = (String) Singleton.getInstance().getDataController().GetPersonalGeneralInfo(arrStr);
                tempString = tempString.replaceAll("\r", "");
                String[] resultSet = tempString.split(">>");
                for(String i: resultSet) {
                    String[] resultSubSet = i.split("<<");
                    personalList.add(new PersonalRepresentation(resultSubSet[0], resultSubSet[1], resultSubSet[2],
                            resultSubSet[3], Integer.parseInt(resultSubSet[4]), Integer.parseInt(resultSubSet[5]),
                            Integer.parseInt(resultSubSet[6]), Integer.parseInt(resultSubSet[7])));
                }
                comboBoxValues.setAll("Роль", "Должность", "Статус", "Кол-во задач", "Кол-во клиентов",
                        "Кол-во проектов", "Кол-во дел");
                categoryComboBox.setItems(comboBoxValues);
                break;
            }
            case "Клиент": {
                String[] arrStr = {"GetClientGeneralInfo"};
                String tempString = (String) Singleton.getInstance().getDataController().GetClientGeneralInfo(arrStr);
                tempString = tempString.replaceAll("\r", "");
                String[] resultSet = tempString.split(">>");
                for(String i: resultSet) {
                    String[] resultSubSet = i.split("<<");
                    clientsList.add(new ClientRepresentation(resultSubSet[0], resultSubSet[1], resultSubSet[2],
                            Integer.parseInt(resultSubSet[3]),
                            Integer.parseInt(resultSubSet[4]), Integer.parseInt(resultSubSet[5])));
                }
                comboBoxValues.setAll("Вид клиента", "Тип производства", "Кол-во задач", "Кол-во счетов", "Кол-во дел");
                categoryComboBox.setItems(comboBoxValues);
                break;
            }
            case "Счет": {
                String[] arrStr = {"GetPaymentGeneralInfo"};
                String tempString = (String) Singleton.getInstance().getDataController().GetPaymentGeneralInfo(arrStr);
                tempString = tempString.replaceAll("\r", "");
                String[] resultSet = tempString.split(">>");
                for(String i: resultSet) {
                    String[] resultSubSet = i.split("<<");
                    paymentsList.add(new PaymentRepresentation(Integer.parseInt(resultSubSet[0]),
                            Integer.parseInt(resultSubSet[1]), resultSubSet[2], resultSubSet[3]));
                }
                comboBoxValues.setAll("Цена", "Статус", "Товар");
                categoryComboBox.setItems(comboBoxValues);
                break;
            }
            case "Задача": {
                String[] arrStr = {"GetTaskGeneralInfo"};
                String tempString = (String) Singleton.getInstance().getDataController().GetTaskGeneralInfo(arrStr);
                tempString = tempString.replaceAll("\r", "");
                String[] resultSet = tempString.split(">>");
                for(String i: resultSet) {
                    String[] resultSubSet = i.split("<<");
                    tasksList.add(new TaskRepresentation(resultSubSet[0], resultSubSet[1], resultSubSet[2]));
                }
                comboBoxValues.setAll("Статус", "Приоритет");
                categoryComboBox.setItems(comboBoxValues);
                break;
            }
            case "Проект": {
                String[] arrStr = {"GetProjectGeneralInfo"};
                String tempString = (String) Singleton.getInstance().getDataController().GetProjectGeneralInfo(arrStr);
                tempString = tempString.replaceAll("\r", "");
                String[] resultSet = tempString.split(">>");
                for(String i: resultSet) {
                    String[] resultSubSet = i.split("<<");
                    projectsList.add(new ProjectRepresentation(resultSubSet[0], resultSubSet[1], resultSubSet[2],
                            Integer.parseInt(resultSubSet[3]), Integer.parseInt(resultSubSet[4])));
                }
                comboBoxValues.setAll("Статус", "Трудозатраты", "Число выполняющих", "Число задач");
                categoryComboBox.setItems(comboBoxValues);
                break;
            }
            case "Дело": {
                String[] arrStr = {"GetBusinessGeneralInfo"};
                String tempString = (String) Singleton.getInstance().getDataController().GetBusinessGeneralInfo(arrStr);
                tempString = tempString.replaceAll("\r", "");
                String[] resultSet = tempString.split(">>");
                for(String i: resultSet) {
                    String[] resultSubSet = i.split("<<");
                    businessList.add(new BusinessRepresentation(resultSubSet[0], resultSubSet[1]));
                }
                comboBoxValues.setAll("Статус");
                categoryComboBox.setItems(comboBoxValues);
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
