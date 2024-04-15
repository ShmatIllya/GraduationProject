package practise.controllers2.Tasks;

import DTO.CommentDTO;
import DTO.TaskDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.HelloApplication;
import practise.controllers2.Business.BusinessController;
import practise.controllers2.Business.BusinessInfoController;
import practise.controllers2.DashboardController;
import practise.singleton.Singleton;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class TaskInfoController implements Initializable {
    public StackPane stackPane;
    public JFXComboBox<String> checkerComboBox;
    public TextField nameField;
    public JFXComboBox<String> responsableComboBox;
    public MFXDatePicker datePicker;
    public TextField creationDateField;
    public TextField statusField;
    public TextArea descriptionField;
    public HBox obeyButtonsHBox;
    public JFXButton completeButton;
    public JFXButton uncompletedButton;
    public HBox controlButtonsHBox;
    public JFXButton checkButton;
    public JFXButton uncheckButton;
    public JFXButton addBusinessButton;
    public VBox journalVBox1;
    public TextField journalTextField1;
    public HBox sampleHBox1;
    public JFXComboBox<String> priorityComboBox;
    public HBox primaryBusinessHBox;
    public VBox businessVBox;
    public JFXButton journalButton1;
    boolean onChange = false;
    String old_name;
    String old_responsable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<String> comboList = FXCollections.observableArrayList();
            JSONObject arrStr = new JSONObject();
            arrStr.put("operationID", "GetPersonalObeyList");
            JSONObject tempString = Singleton.getInstance().getDataController().GetPersonalObeyList(arrStr);
            JSONArray resultSet = tempString.getJSONArray("personalList");
            for (int i = 0; i < resultSet.length(); i++) {
                try {
                    comboList.add(resultSet.getJSONObject(i).getString("nameSername"));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            responsableComboBox.setItems(comboList);

            comboList = FXCollections.observableArrayList();
            JSONObject arrStr2 = new JSONObject();
            arrStr2.put("operationID", "GetPersonalControlList");
            tempString = Singleton.getInstance().getDataController().GetPersonalControlList(arrStr2);
            resultSet = tempString.getJSONArray("personalList");
            for (int i = 0; i < resultSet.length(); i++) {
                try {
                    comboList.add(resultSet.getJSONObject(i).getString("nameSername"));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            checkerComboBox.setItems(comboList);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //================================DashBoard buttons================================
        JFXButton SaveButton = new JFXButton();
        JFXButton ChangeButton = new JFXButton();
        SaveButton.setDisable(true);

        SaveButton.setText("Сохранить изменения");
        SaveButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        SaveButton.setOnMouseClicked(mouseEvent -> {
            try {
                TaskDTO task = new TaskDTO();
                if (task.getName() != null) {
                    try {
                        task.setName(nameField.getText());
                        task.setResponsibleName(responsableComboBox.getSelectionModel().getSelectedItem());
                        task.setDescription(descriptionField.getText());
                        task.setCheckerName(checkerComboBox.getSelectionModel().getSelectedItem());
                        task.setDeadline(Date.valueOf(datePicker.getText()));
                        task.setPriority(priorityComboBox.getSelectionModel().getSelectedItem());
                        task.setOldName(old_name);
                        task.setOldResponsibleName(old_responsable);
                    } catch (Exception e) {
                        Label messageBox = new Label("Ошибка ввода данных");
                        Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                        throw new RuntimeException(e);

                    }

                    JSONObject tempStringUpdate = Singleton.getInstance().getDataController().UpdateTaskInfo(task);
                    Label messageBox = new Label();
                    if (tempStringUpdate.getString("response").equals("null")) {
                        messageBox.setText("Ошибка сохранения");
                    } else {
                        Singleton.getInstance().setTaskInfoValues(new String[]{nameField.getText(),
                                responsableComboBox.getSelectionModel().getSelectedItem()});
                        messageBox.setText("Успешно сохранено");
                    }
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                } else {
                    Label messageBox = new Label("Ошибка ввода данных");
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                }
                //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
                onChange = false;
                nameField.setEditable(false);
                checkerComboBox.setDisable(true);
                responsableComboBox.setDisable(true);
                priorityComboBox.setDisable(true);
                datePicker.setDisable(true);
                descriptionField.setEditable(false);
                SaveButton.setDisable(true);
                ChangeButton.setDisable(false);


            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    OnReload();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ChangeButton.setText("Изменить информацию");
        ChangeButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        ChangeButton.setOnMouseClicked(mouseEvent -> {
            onChange = true;
            nameField.setEditable(true);
            checkerComboBox.setDisable(false);
            responsableComboBox.setDisable(false);
            priorityComboBox.setDisable(false);
            datePicker.setDisable(false);
            descriptionField.setEditable(true);
            SaveButton.setDisable(false);
            ChangeButton.setDisable(true);
        });
        if (Singleton.getInstance().getFinal_Role().equals("obey")) {
            ChangeButton.setDisable(true);
        }

        ArrayList<JFXButton> buttons = new ArrayList<>();
        buttons.add(ChangeButton);
        buttons.add(SaveButton);
        DashboardController temp = new DashboardController();
        temp.SetVBoxButtons(buttons);
        //===============================/DashBoard buttons================================
        datePicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", datePicker.getLocale()));
        ObservableList<String> list = FXCollections.observableArrayList("П3 Низкий", "П2 Средний", "П1 Высокий");
        priorityComboBox.setItems(list);

        try {
            OnReload();
            //=================================Role and Status Actions=======================================
            if (Singleton.getInstance().getFinal_Role().equals("control")) {
                double X1 = controlButtonsHBox.getLayoutX();
                double Y1 = controlButtonsHBox.getLayoutY();
                double X2 = obeyButtonsHBox.getLayoutX();
                double Y2 = obeyButtonsHBox.getLayoutY();
                obeyButtonsHBox.setLayoutX(X1);
                obeyButtonsHBox.setLayoutY(Y1);
                obeyButtonsHBox.setVisible(false);

                controlButtonsHBox.setLayoutX(X2);
                controlButtonsHBox.setLayoutY(Y2);
                controlButtonsHBox.setVisible(true);

            } else if (Singleton.getInstance().getFinal_Role().equals("obey") &&
                    !Singleton.getInstance().getFinal_NameSername().equals(responsableComboBox.getSelectionModel().getSelectedItem())) {
                obeyButtonsHBox.setVisible(false);
                journalButton1.setDisable(true);
            }
            if (Singleton.getInstance().getFinal_Role().equals("obey")) {
                addBusinessButton.setDisable(true);
            }
            if (!statusField.getText().equals("Назначена")) {
                System.out.println("'" + statusField.getText() + "'");
                controlButtonsHBox.setVisible(false);
                obeyButtonsHBox.setVisible(false);
            }
            //=================================/Role ans Status Actions======================================

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void OnReload() throws IOException {
        try {
            journalVBox1.getChildren().clear();
            businessVBox.getChildren().clear();
            businessVBox.getChildren().add(primaryBusinessHBox);
            TaskDTO task = new TaskDTO();
            try {
                task.setName(Singleton.getInstance().getTaskInfoValues()[0]);
                task.setResponsibleName(Singleton.getInstance().getTaskInfoValues()[1]);
            } catch (Exception e) {
                Label messageBox = new Label("Ошибка ввода данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                throw new RuntimeException(e);

            }
            //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
            JSONObject tempString = Singleton.getInstance().getDataController().GetTaskInfo(task);

            JSONArray businessList = tempString.getJSONArray("businessList");
            JSONArray commentList = tempString.getJSONArray("commentList");
            int data_type = 0;
            for (data_type = 0; data_type <= 3; data_type++) {
                try {
                    switch (data_type) {
                        case 0: {
                            nameField.setText(tempString.getString("name"));
                            old_name = tempString.getString("name");
                            responsableComboBox.getSelectionModel().select(tempString.getString("responsibleName"));
                            old_responsable = tempString.getString("responsibleName");
                            descriptionField.setText(tempString.getString("description"));
                            checkerComboBox.getSelectionModel().select(tempString.getString("checkerName"));
                            datePicker.setText(tempString.getString("deadline"));
                            statusField.setText(tempString.getString("status"));
                            creationDateField.setText(tempString.getString("creationDate"));
                            priorityComboBox.getSelectionModel().select(tempString.getString("priority"));
                            break;
                        }
                        case 1: {
                            for (int j = 0; j < businessList.length(); j++) {
                                HBox businessBox = new HBox();
                                businessBox.setStyle("-fx-background-color: #2196f3");
                                //primaryBusinessHBox;
                                MFXButton button = new MFXButton(businessList.getJSONObject(j).getString("businessName"));
                                button.setStyle("-fx-text-fill: #151928; -fx-background-color: transparent");
                                if (businessList.getJSONObject(j).getString("businessStatus").equals("Завершено")) {
                                    businessBox.setStyle("-fx-background-color: green");
                                }
                                button.setAlignment(Pos.CENTER_LEFT);
                                button.setOnMouseEntered(event -> {
                                    button.setText("Перейти к делу");
                                    button.setStyle("-fx-text-fill: #2196f3; -fx-background-color: #151928");
                                });
                                int finalJ = j;
                                button.setOnMouseExited(event -> {
                                    try {
                                        button.setText(businessList.getJSONObject(finalJ).getString("businessName"));
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    button.setStyle("-fx-text-fill: #151928; -fx-background-color: transparent");
                                });
                                int finalJ1 = j;
                                button.setOnMouseClicked(event -> {
                                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Business/BusinessInfo.fxml"));
                                    StackPane page = null;
                                    try {
                                        page = fxmlLoader.load();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
                                    Scene scene = new Scene(page);
                                    scene.setFill(Color.TRANSPARENT);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.initStyle(StageStyle.TRANSPARENT);
                                    BusinessInfoController controller = fxmlLoader.getController();
                                    try {
                                        controller.InitController(businessList.getJSONObject(finalJ1).getString("businessID"), responsableComboBox.getSelectionModel().getSelectedItem());
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                    stage.showAndWait();
                                    try {
                                        OnReload();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                                businessBox.getChildren().add(button);
                                businessVBox.getChildren().add(businessBox);
                            }
                            break;
                        }
                        case 2: {
                            for (int i = 0; i < commentList.length(); i++) {
                                HBox hBox = Singleton.getInstance().SetCommentBox(sampleHBox1,
                                        commentList.getJSONObject(i).getString("commentText"),
                                        commentList.getJSONObject(i).getString("commentSenderName"),
                                        commentList.getJSONObject(i).getString("commentDate"),
                                        commentList.getJSONObject(i).getString("commentSenderLogin"));
                                journalVBox1.getChildren().add(hBox);
                            }
                            break;
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void OnAddBusinessButton() throws IOException, JSONException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Business/Business.fxml"));
        StackPane page = fxmlLoader.load();
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setX(MouseInfo.getPointerInfo().getLocation().x);
        stage.setY(MouseInfo.getPointerInfo().getLocation().y);
        BusinessController controller = fxmlLoader.getController();
        controller.InitController("Задача", nameField.getText());
        stage.showAndWait();
        OnReload();
    }

    public void OnAddCommentButton() throws IOException, JSONException {
        if (!journalTextField1.getText().isEmpty()) {
            CommentDTO comment = new CommentDTO();
            try {
                comment.setSenderName(Singleton.getInstance().getFinal_NameSername());
                comment.setText(journalTextField1.getText());
                comment.setLinkedEntityName(nameField.getText());
                comment.setType("Задача");
            } catch (Exception e) {
                Label messageBox = new Label("Ошибка ввода данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                return;
            }
            JSONObject tempString = Singleton.getInstance().getDataController().AddComment(comment);
            Label MessageLabel = new Label();
            if (tempString.getString("response").equals("null")) {
                MessageLabel.setText("Ошибка добавления");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
                return;
            }
            OnReload();
        }
    }

    public void OnCompleteButton(ActionEvent event) throws IOException, JSONException {
        TaskDTO task = new TaskDTO();
        try {
            task.setName(Singleton.getInstance().getTaskInfoValues()[0]);
            task.setResponsibleName(Singleton.getInstance().getTaskInfoValues()[1]);
            task.setStatus("Завершена");
        } catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().ChangeTaskStatus(task);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if (tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка изменения статуса");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        } else {
            MessageLabel.setText("Статус успешно изменен");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnReload();
        }
    }

    public void OnFailButton(ActionEvent event) throws IOException, JSONException {
        TaskDTO task = new TaskDTO();
        try {
            task.setName(Singleton.getInstance().getTaskInfoValues()[0]);
            task.setResponsibleName(Singleton.getInstance().getTaskInfoValues()[1]);
            task.setStatus("Провалена");
        } catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().ChangeTaskStatus(task);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if (tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка изменения статуса");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        } else {
            MessageLabel.setText("Статус успешно изменен");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnReload();
        }
    }

    public void OnCheckButton(ActionEvent event) throws IOException, JSONException {
        TaskDTO task = new TaskDTO();
        try {
            task.setName(Singleton.getInstance().getTaskInfoValues()[0]);
            task.setResponsibleName(Singleton.getInstance().getTaskInfoValues()[1]);
            task.setStatus("Завершена");
        } catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().ChangeTaskStatus(task);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if (tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка изменения статуса");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        } else {
            MessageLabel.setText("Статус успешно изменен");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnReload();
        }
    }

    public void OnUncheckButton(ActionEvent event) throws JSONException, IOException {
        TaskDTO task = new TaskDTO();
        try {
            task.setName(Singleton.getInstance().getTaskInfoValues()[0]);
            task.setResponsibleName(Singleton.getInstance().getTaskInfoValues()[1]);
        } catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().DeleteTask(task);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if (tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка снятия задачи");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        } else {
            MessageLabel.setText("Задача успешно снята");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel, (Stage) addBusinessButton.getScene().getWindow());
            DashboardController c = new DashboardController();
            c.SwitchMainPane("/SubFXMLs/Tasks/Tasks.fxml");
        }
    }
}
