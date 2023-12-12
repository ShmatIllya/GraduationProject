package practise.controllers2.Tasks;

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
import practise.HelloApplication;
import practise.controllers2.Business.BusinessController;
import practise.controllers2.Business.BusinessInfoController;
import practise.controllers2.DashboardController;
import practise.singleton.Singleton;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
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

        ObservableList<String> comboList = FXCollections.observableArrayList();
        String[] arrStr = {"GetPersonalObeyList"};
        String tempString = (String) Singleton.getInstance().getDataController().GetPersonalObeyList(arrStr);
        tempString = tempString.replaceAll("\r", "");
        String[] resultSet = tempString.split("<<");
        for(String i : resultSet) {
            String[] resultSubSet = i.split(">>");
            try {
                comboList.add(resultSubSet[0]);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        responsableComboBox.setItems(comboList);

        comboList = FXCollections.observableArrayList();
        String[] arrStr2 = {"GetPersonalControlList"};
        tempString = (String) Singleton.getInstance().getDataController().GetPersonalControlList(arrStr2);
        tempString = tempString.replaceAll("\r", "");
        resultSet = tempString.split("<<");
        for(String i : resultSet) {
            String[] resultSubSet = i.split(">>");
            try {
                comboList.add(resultSubSet[0]);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        checkerComboBox.setItems(comboList);
        //================================DashBoard buttons================================
        JFXButton SaveButton = new JFXButton();
        JFXButton ChangeButton = new JFXButton();
        SaveButton.setDisable(true);

        SaveButton.setText("Сохранить изменения");
        SaveButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        SaveButton.setOnMouseClicked(mouseEvent -> {
            try {
                String[] arrStrUpdate = {"UpdateTaskInfo", nameField.getText(), responsableComboBox.getSelectionModel().getSelectedItem(),
                        descriptionField.getText(), checkerComboBox.getSelectionModel().getSelectedItem(), datePicker.getText(),
                        priorityComboBox.getSelectionModel().getSelectedItem(),
                        old_name, old_responsable};
                String tempStringUpdate = (String) Singleton.getInstance().getDataController().UpdateTaskInfo(arrStrUpdate);
                tempStringUpdate = tempStringUpdate.replaceAll("\r", "");
                Label messageBox = new Label();
                if(tempStringUpdate.equals("null")) {
                    messageBox.setText("Ошибка сохранения");
                }
                else {
                    Singleton.getInstance().setTaskInfoValues(new String[]{nameField.getText(),
                            responsableComboBox.getSelectionModel().getSelectedItem()});
                    messageBox.setText("Успешно сохранено");
                }
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
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


            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
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
        if(Singleton.getInstance().getFinal_Role().equals("obey")) {
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
            if(Singleton.getInstance().getFinal_Role().equals("control")) {
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

            }
            else if (Singleton.getInstance().getFinal_Role().equals("obey") &&
                    !Singleton.getInstance().getFinal_NameSername().equals(responsableComboBox.getSelectionModel().getSelectedItem())) {
                obeyButtonsHBox.setVisible(false);
                journalButton1.setDisable(true);
            }
            if(Singleton.getInstance().getFinal_Role().equals("obey")) {
                addBusinessButton.setDisable(true);
            }
            if(!statusField.getText().equals("Назначена")) {
                System.out.println("'" + statusField.getText() + "'");
                controlButtonsHBox.setVisible(false);
                obeyButtonsHBox.setVisible(false);
            }
            //=================================/Role ans Status Actions======================================

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void OnReload() throws IOException {
        journalVBox1.getChildren().clear();
        businessVBox.getChildren().clear();
        businessVBox.getChildren().add(primaryBusinessHBox);
        String[] arrStr = {"GetTaskInfo", Singleton.getInstance().getTaskInfoValues()[0],
                Singleton.getInstance().getTaskInfoValues()[1]};
        //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
        String tempString = (String) Singleton.getInstance().getDataController().GetTaskInfo(arrStr);
        tempString = tempString.replaceAll("\r", "");
        
        String[] resultSet = tempString.split(">>");
        int data_type = 0;
        for(String i: resultSet) {
            String subResSet[] = i.split("<<");
            try {
                switch (data_type) {
                    case 0: {
                        nameField.setText(subResSet[0]);
                        old_name = subResSet[0];
                        responsableComboBox.getSelectionModel().select(subResSet[1]);
                        old_responsable = subResSet[1];
                        descriptionField.setText(subResSet[2]);
                        checkerComboBox.getSelectionModel().select(subResSet[3]);
                        datePicker.setText(subResSet[4]);
                        statusField.setText(subResSet[5]);
                        creationDateField.setText(subResSet[6]);
                        priorityComboBox.getSelectionModel().select(subResSet[7]);
                        break;
                    }
                    case 1: {
                        if(!i.equals("")) {
                            for (String business : subResSet) {
                                String[] businessSplit = business.split("\\^\\^");
                                HBox businessBox = new HBox();
                                businessBox.setStyle("-fx-background-color: #2196f3");
                                //primaryBusinessHBox;
                                MFXButton button = new MFXButton(businessSplit[1]);
                                button.setStyle("-fx-text-fill: #151928; -fx-background-color: transparent");
                                if(businessSplit[2].equals("Завершено")) {
                                    businessBox.setStyle("-fx-background-color: green");
                                }
                                button.setAlignment(Pos.CENTER_LEFT);
                                button.setOnMouseEntered(event -> {
                                    button.setText("Перейти к делу");
                                    button.setStyle("-fx-text-fill: #2196f3; -fx-background-color: #151928");
                                });
                                button.setOnMouseExited(event -> {
                                    button.setText(businessSplit[1]);
                                    button.setStyle("-fx-text-fill: #151928; -fx-background-color: transparent");
                                });
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
                                    controller.InitController(businessSplit[0], responsableComboBox.getSelectionModel().getSelectedItem());
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
                        }
                        break;
                    }
                    case 2: {
                        if (!i.equals("")) {
                            for (String comment : subResSet) {
                                String[] commentData = comment.split("\\^\\^");
                                System.out.println(comment);
                                HBox hBox = Singleton.getInstance().SetCommentBox(sampleHBox1, commentData[0], commentData[1], commentData[2], commentData[3]);
                                journalVBox1.getChildren().add(hBox);
                            }
                        }
                        break;
                    }
                }
                data_type++;
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void OnAddBusinessButton() throws IOException {
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

    public void OnAddCommentButton() throws IOException {
        if(!journalTextField1.getText().isEmpty()) {
            String[] arrStr = {"AddComment", Singleton.getInstance().getFinal_NameSername(), journalTextField1.getText(),
                    nameField.getText(), "Задача"};
            String tempString = (String) Singleton.getInstance().getDataController().AddComment(arrStr);
            tempString = tempString.replaceAll("\r", "");
            Label MessageLabel = new Label();
            if (tempString.equals("null")) {
                MessageLabel.setText("Ошибка добавления");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            }
            OnReload();
        }
    }

    public void OnCompleteButton(ActionEvent event) throws IOException {
        String[] arrStr = {"ChangeTaskStatus", Singleton.getInstance().getTaskInfoValues()[0],
                Singleton.getInstance().getTaskInfoValues()[1], "Завершена"};
        String tempString = (String) Singleton.getInstance().getDataController().ChangeTaskStatus(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.equals("null")) {
            MessageLabel.setText("Ошибка изменения статуса");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Статус успешно изменен");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnReload();
        }
    }

    public void OnFailButton(ActionEvent event) throws IOException {
        String[] arrStr = {"ChangeTaskStatus", Singleton.getInstance().getTaskInfoValues()[0],
                Singleton.getInstance().getTaskInfoValues()[1], "Провалена"};
        String tempString = (String) Singleton.getInstance().getDataController().ChangeTaskStatus(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.equals("null")) {
            MessageLabel.setText("Ошибка изменения статуса");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Статус успешно изменен");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnReload();
        }
    }

    public void OnCheckButton(ActionEvent event) throws IOException {
        String[] arrStr = {"ChangeTaskStatus", Singleton.getInstance().getTaskInfoValues()[0],
                Singleton.getInstance().getTaskInfoValues()[1], "Завершена"};
        String tempString = (String) Singleton.getInstance().getDataController().ChangeTaskStatus(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.equals("null")) {
            MessageLabel.setText("Ошибка изменения статуса");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Статус успешно изменен");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnReload();
        }
    }

    public void OnUncheckButton(ActionEvent event) {
        String[] arrStr = {"DeleteTask", Singleton.getInstance().getTaskInfoValues()[0],
                Singleton.getInstance().getTaskInfoValues()[1]};
        String tempString = (String) Singleton.getInstance().getDataController().DeleteTask(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.equals("null")) {
            MessageLabel.setText("Ошибка снятия задачи");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Задача успешно снята");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel, (Stage) addBusinessButton.getScene().getWindow());
        }
    }
}
