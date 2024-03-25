package practise.controllers2.Projects;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import practise.controllers2.Business.BusinessInfoController;
import practise.controllers2.DashboardController;
import practise.singleton.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ProjectInfoController implements Initializable {
    public StackPane stackPane;
    public JFXComboBox<String> checkerComboBox;
    public TextField nameField;
    public TextArea descriptionArea;
    public TextField statusField;
    public JFXComboBox<String> teamNameComboBox;
    public VBox taskVBox;
    public HBox primaryTaskHBox;
    public JFXButton addTaskButton;
    public VBox businessVBox;
    public HBox primaryBusinessHBox;
    public JFXButton addBusinessButton;
    public VBox journalVBox1;
    public TextField journalTextField1;
    public JFXButton journalButton1;
    public HBox sampleHBox1;
    public MFXDatePicker datePicker;
    public TextField creationDateField;
    public TextField startField;
    public TextField durationField;
    public TextField finalHoursField;
    public TextField startValField;
    public TextField planValField;
    public TextField factValField;
    public TextField progressField;
    public TextField izm1Field;
    public TextField izm2Field;
    public TextField izm3Field;
    public JFXListView<String> membersListView;
    String projectName;
    boolean onChange = false;
    String old_name;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.projectName = Singleton.getInstance().getProjectInfoValue();

        //================================DashBoard buttons================================
        JFXButton SaveButton = new JFXButton();
        JFXButton ChangeButton = new JFXButton();
        SaveButton.setDisable(true);

        SaveButton.setText("Сохранить изменения");
        SaveButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        SaveButton.setOnMouseClicked(mouseEvent -> {
            try {
//                String[] arrStrUpdate = {"UpdateProjectInfo", clientNameField.getText(), phoneField.getText(),
//                        emailField.getText(), adressField.getText(), descriptionArea.getText(),
//                        responsableComboBox.getSelectionModel().getSelectedItem(),
//                        clientTypeComboBox.getSelectionModel().getSelectedItem(),
//                        workTypeComboBox.getSelectionModel().getSelectedItem(),
//                        datePicker.getText(), old_name, old_email};
//                String tempStringUpdate = (String) Singleton.getInstance().getDataController().UpdateProjectInfo(arrStrUpdate);
//                tempStringUpdate = tempStringUpdate.replaceAll("\r", "");
//                Label messageBox = new Label();
//                if(tempStringUpdate.equals("null")) {
//                    messageBox.setText("Ошибка сохранения");
//                }
//                else {
//                    messageBox.setText("Успешно сохранено");
//                }
//                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
                onChange = false;
                nameField.setEditable(false);
                descriptionArea.setEditable(false);
                checkerComboBox.setDisable(true);
                teamNameComboBox.setDisable(true);
                creationDateField.setEditable(false);
                datePicker.setEditable(false);
                startField.setEditable(false);
                descriptionArea.setEditable(false);
                finalHoursField.setEditable(false);
                startValField.setEditable(false);
                planValField.setEditable(false);
                factValField.setEditable(false);
                izm1Field.setEditable(false);
                izm2Field.setEditable(false);
                izm3Field.setEditable(false);
                SaveButton.setDisable(true);
                ChangeButton.setDisable(false);


            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                OnReload();
            }
        });

        ChangeButton.setText("Изменить информацию");
        ChangeButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        ChangeButton.setOnMouseClicked(mouseEvent -> {
            onChange = true;
            nameField.setEditable(true);
            descriptionArea.setEditable(true);
            checkerComboBox.setDisable(false);
            teamNameComboBox.setDisable(false);
            creationDateField.setEditable(true);
            datePicker.setEditable(true);
            startField.setEditable(true);
            descriptionArea.setEditable(true);
            finalHoursField.setEditable(true);
            startValField.setEditable(true);
            planValField.setEditable(true);
            factValField.setEditable(true);
            izm1Field.setEditable(true);
            izm2Field.setEditable(true);
            izm3Field.setEditable(true);
            SaveButton.setDisable(false);
            ChangeButton.setDisable(true);
        });


        ArrayList<JFXButton> buttons = new ArrayList<>();
        buttons.add(ChangeButton);
        buttons.add(SaveButton);
        if(Singleton.getInstance().getFinal_Role().equals("control")) {
            DashboardController temp = new DashboardController();
            temp.SetVBoxButtons(buttons);
        }
        else {
            for(String i: membersListView.getItems()) {
                if (!Singleton.getInstance().getFinal_NameSername().equals(i)) {
                    journalButton1.setDisable(true);
                    break;
                }
            }
            addBusinessButton.setDisable(true);
            addTaskButton.setDisable(true);
        }
        //===============================/DashBoard buttons================================
        datePicker.setLocale(Locale.ENGLISH);
        datePicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", datePicker.getLocale()));
        try {
            ObservableList<String> comboList = FXCollections.observableArrayList();
            JSONObject arrStr = new JSONObject();
            arrStr.put("operationID", "GetPersonalControlList");
            JSONObject tempString = Singleton.getInstance().getDataController().GetPersonalControlList(arrStr);
            JSONArray resultSet = tempString.getJSONArray("personalList");
            for (int i = 0; i < resultSet.length(); i++) {
                try {
                    comboList.add(resultSet.getJSONObject(i).getString("nameSername"));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            checkerComboBox.setItems(comboList);
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        OnReload();
    }

    public void OnReload() {
        journalVBox1.getChildren().clear();
        businessVBox.getChildren().clear();
        taskVBox.getChildren().clear();
        businessVBox.getChildren().add(primaryBusinessHBox);
        taskVBox.getChildren().add(primaryTaskHBox);
        //==============================ComboBoxInit======================================
        try {
            ObservableList<String> comboList = FXCollections.observableArrayList();
            JSONObject arrStrControl = new JSONObject();
            arrStrControl.put("operationID", "GetPersonalControlList");
            JSONObject tempString = Singleton.getInstance().getDataController().GetPersonalControlList(arrStrControl);
            JSONArray resultSet = tempString.getJSONArray("personalList");
            for (int i = 0; i < resultSet.length(); i++) {
                try {
                    comboList.add(resultSet.getJSONObject(i).getString("nameSername"));
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            checkerComboBox.setItems(comboList);

            comboList = FXCollections.observableArrayList();
            String[] arrStrTeams = {"GetTeamsList"};
            String teamsList = (String) Singleton.getInstance().getDataController().GetTeamsList(arrStrTeams);
            teamsList = teamsList.replaceAll("\r", "");
            String[] teamsResSet = teamsList.split(">>");
            for (String i : teamsResSet) {
                comboList.add(i);
            }
            teamNameComboBox.setItems(comboList);
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //================================/ComboBoxInit======================================
        String[] arrStr = {projectName};
        //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
        String projectInfo = (String) Singleton.getInstance().getDataController().GetProjectInfo(arrStr);
        projectInfo = projectInfo.replaceAll("\r", "");
        String[] projectInfoResSet = projectInfo.split(">>");
        int data_type = 0;
        for(String i: projectInfoResSet) {
            String subResSet[] = i.split("<<");
            switch (data_type) {
                case 0: {
                    nameField.setText(subResSet[0]);
                    old_name = subResSet[0];
                    descriptionArea.setText(subResSet[1]);
                    datePicker.setText(subResSet[2]);
                    statusField.setText(subResSet[3]);
                    creationDateField.setText(subResSet[4]);
                    startField.setText(subResSet[4]);
                    finalHoursField.setText(subResSet[5]);
                    startValField.setText(subResSet[6]);
                    factValField.setText(subResSet[7]);
                    checkerComboBox.getSelectionModel().select(subResSet[8]);
                    planValField.setText(subResSet[9]);
                    izm1Field.setText(subResSet[10]);
                    izm2Field.setText(subResSet[10]);
                    izm3Field.setText(subResSet[10]);
                    teamNameComboBox.getSelectionModel().select(subResSet[11]);
                    break;
                }
                case 1: {
                    if(!i.equals("")) {
                        for (String task : subResSet) {
                            String[] taskSplit = task.split("\\^\\^");
                            HBox taskBox = new HBox();
                            taskBox.setStyle("-fx-background-color: #2196f3");
                            MFXButton button = new MFXButton(taskSplit[1]);
                            button.setStyle("-fx-text-fill: #151928; -fx-background-color: transparent");
                            if(taskSplit[2].equals("Завершена")) {
                                taskBox.setStyle("-fx-background-color: green");
                            }
                            button.setAlignment(Pos.CENTER_LEFT);
                            button.setOnMouseEntered(event -> {
                                button.setText("Перейти к задаче");
                                button.setStyle("-fx-text-fill: #2196f3; -fx-background-color: #151928");
                            });
                            button.setOnMouseExited(event -> {
                                button.setText(taskSplit[1]);
                                button.setStyle("-fx-text-fill: #151928; -fx-background-color: transparent");
                            });
                            button.setOnMouseClicked(event -> {
                                Singleton.getInstance().setTaskInfoValues(new String[]{taskSplit[1],
                                        membersListView.getSelectionModel().getSelectedItem()});
                                DashboardController dashboardController = new DashboardController();
                                try {
                                    dashboardController.SwitchMainPane("/SubFXMLs/Tasks/TaskInfo.fxml");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                OnReload();
                            });
                            taskBox.getChildren().add(button);
                            taskVBox.getChildren().add(taskBox);
                        }
                    }
                    break;
                }
                case 2: {
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
                                button.setStyle("-fx-text-fill: #2196f3; -fx-background-color: transparent");
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
                                controller.InitController(businessSplit[0], membersListView.getSelectionModel().getSelectedItem());
                                stage.showAndWait();
                                OnReload();
                            });
                            businessBox.getChildren().add(button);
                            businessVBox.getChildren().add(businessBox);
                        }
                    }
                    break;
                }
                case 3: {
                    if(!i.equals("")) {
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

        //======================================ComboBoxInit===========================================
        ObservableList<String> comboList = FXCollections.observableArrayList();
        String[] arrStrTeamMembers = {"GetTeamMembersList", teamNameComboBox.getSelectionModel().getSelectedItem()};
        projectInfo = (String) Singleton.getInstance().getDataController().GetTeamMembersList(arrStrTeamMembers);
        projectInfo = projectInfo.replaceAll("\r", "");
        projectInfoResSet = projectInfo.split(">>");
        for(String i : projectInfoResSet) {
            comboList.add(i);
        }
        membersListView.setItems(comboList);
        //===================================/ComboBoxInit==============================================
    }

    public void OnAddBusinessButton(ActionEvent event) {
    }

    public void OnAddCommentButton(ActionEvent event) {
    }

    public void OnAddTaskButton(ActionEvent event) {
    }

}
