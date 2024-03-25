package practise.controllers2;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.HelloApplication;
import practise.items.PersonalItems;
import practise.singleton.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PersonalController implements Initializable {
    @FXML
    public JFXTreeTableView<PersonalItems> treeTable;
    public TextField searchField;
    public ScrollPane mainPane;
    public javafx.scene.layout.AnchorPane AnchorPane;
    public JFXButton redactButton;
    public JFXButton deleteButton;
    public JFXButton addButton;
    public JFXComboBox<String> searchComboBox;

    //=========================Columns====================================
    JFXTreeTableColumn<PersonalItems, String> nameSername;
    JFXTreeTableColumn<PersonalItems, String> contacts;
    JFXTreeTableColumn<PersonalItems, String> subRole;
    JFXTreeTableColumn<PersonalItems, String> email;
    JFXTreeTableColumn<PersonalItems, String> status;
    JFXTreeTableColumn<PersonalItems, String> login;
    //=========================Columns====================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("ФИО", "Телефон", "Email", "Должность", "Статус");
        searchComboBox.setItems(list);
        searchComboBox.getSelectionModel().select("ФИО");
        if(Singleton.getInstance().getFinal_Role().equals("obey"))
        {
            addButton.setVisible(false);
            redactButton.setVisible(false);
            deleteButton.setVisible(false);
        }

        nameSername = new JFXTreeTableColumn<>("Ф.И.О");
        nameSername.setPrefWidth(150);
        nameSername.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalItems, String> param) {
                return param.getValue().getValue().nameSername;
            }
        });

        contacts = new JFXTreeTableColumn<>("Телефон");
        contacts.setPrefWidth(150);
        contacts.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalItems, String> param) {
                return param.getValue().getValue().contacts;
            }
        });

        email = new JFXTreeTableColumn<>("Email");
        email.setPrefWidth(150);
        email.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalItems, String> param) {
                return param.getValue().getValue().email;
            }
        });

        subRole = new JFXTreeTableColumn<>("Должность");
        subRole.setPrefWidth(150);
        subRole.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalItems, String> param) {
                return param.getValue().getValue().subrole;
            }
        });

        status = new JFXTreeTableColumn<>("Статус");
        status.setPrefWidth(150);
        status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalItems, String> param) {
                return param.getValue().getValue().status;
            }
        });

        login = new JFXTreeTableColumn<>("Логин");
        login.setPrefWidth(150);
        login.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PersonalItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PersonalItems, String> param) {
                return param.getValue().getValue().login;
            }
        });
        login.setVisible(false);
        //========================================================================================
        //===============================AddingElements===========================================
        //========================================================================================
       OnReload();
        //==========================================================================================
        //====================================DoubleClick===========================================
        //==========================================================================================
        treeTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && (! treeTable.getSelectionModel().getSelectedCells().isEmpty()) ) {
                Singleton.getInstance().setPersonalName(String.valueOf(treeTable.getSelectionModel().getSelectedItem().getValue().nameSername));
                DashboardController d = new DashboardController();
                try {
                    Singleton.getInstance().setLocalLogin(String.valueOf(treeTable.getSelectionModel().getSelectedItem().getValue().login.getValue()));
                    d.SwitchMainPane( "/SubFXMLs/Personal/PersonalInfo.fxml");
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(mouseEvent.getClickCount() == 1 && (! treeTable.getSelectionModel().getSelectedCells().isEmpty()) ) {
                redactButton.setDisable(false);
                if(!treeTable.getSelectionModel().getSelectedItem().getValue().status.getValue().equals("Активен")) {
                    deleteButton.setDisable(false);
                }
                else {
                    deleteButton.setDisable(true);
                }
            }
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                treeTable.setPredicate(new Predicate<TreeItem<PersonalItems>>() {
                    @Override
                    public boolean test(TreeItem<PersonalItems> personalItemsTreeItem) {
                        Boolean flag = false;
                        switch (searchComboBox.getSelectionModel().getSelectedItem()) {
                            case "ФИО": {
                                flag = personalItemsTreeItem.getValue().nameSername.getValue().contains(t1);
                                break;
                            }
                            case "Телефон": {
                                flag = personalItemsTreeItem.getValue().contacts.getValue().contains(t1);
                                break;
                            }
                            case "Email": {
                                flag = personalItemsTreeItem.getValue().email.getValue().contains(t1);
                                break;
                            }
                            case "Должность": {
                                flag = personalItemsTreeItem.getValue().subrole.getValue().contains(t1);
                                break;
                            }
                            case "Статус": {
                                flag = personalItemsTreeItem.getValue().status.getValue().contains(t1);
                                break;
                            }

                        }
                        return flag;
                    }
                });
            }
        });
    }

    public void OnAddPersonal() throws IOException {
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Personal/PersonalAdd.fxml"));
        StackPane page = fxmlLoader.load();
        page.setAlignment(Pos.CENTER);
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.showAndWait();

        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0.5, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Singleton.getInstance().getOpacityPane().setVisible(false);
        });
        OnReload();
    }

    public void OnRedactPersonal() throws IOException {
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Personal/PersonalUpdate.fxml"));
        StackPane page = fxmlLoader.load();
        page.setAlignment(Pos.CENTER);
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);

        PersonalUpdateController updateController = fxmlLoader.getController();
        PersonalItems selectedPersonal = treeTable.getSelectionModel().getSelectedItem().getValue();
        String[] nameSername = selectedPersonal.nameSername.getValue().split(" ");
        String sername = "", name = "", otchestvo = "";
        sername = nameSername[0];
        try {
            name = nameSername[1];
            otchestvo = nameSername[2];
        }
        catch (ArrayIndexOutOfBoundsException e) {

        }
        updateController.setData(sername, name, otchestvo, selectedPersonal.email.getValue(),
                selectedPersonal.contacts.getValue(), selectedPersonal.subrole.getValue(), selectedPersonal.status.getValue(),
                selectedPersonal.login.getValue(), selectedPersonal.password.getValue());
        stage.showAndWait();

        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0.5,
                0, 0.5);
        ft.setOnFinished(event1 -> {
            Singleton.getInstance().getOpacityPane().setVisible(false);
        });
        OnReload();
    }

    public void OnReload() {
        redactButton.setDisable(true);
        deleteButton.setDisable(true);
        ObservableList<PersonalItems> observableList = FXCollections.observableArrayList();
        try {
            JSONObject arrStr = new JSONObject();
            arrStr.put("operationID", "GetPersonalList");
            JSONObject tempString = Singleton.getInstance().getDataController().GetPersonalList(arrStr);
            JSONArray resultSet = tempString.getJSONArray("personalList");
            for (int j = 0; j < resultSet.length(); j++) {
                try {
                    observableList.add(new PersonalItems(
                            resultSet.getJSONObject(j).getString("nameSername"),
                            resultSet.getJSONObject(j).getString("contacts"),
                            resultSet.getJSONObject(j).getString("email"),
                            resultSet.getJSONObject(j).getString("subrole"),
                            resultSet.getJSONObject(j).getString("status"),
                            resultSet.getJSONObject(j).getString("login"),
                            resultSet.getJSONObject(j).getString("password")
                    ));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        final TreeItem<PersonalItems> root = new RecursiveTreeItem<PersonalItems>(observableList, RecursiveTreeObject::getChildren);
        treeTable.getColumns().setAll(nameSername, contacts, email, subRole, status, login);
        treeTable.setRoot(root);
        treeTable.setShowRoot(false);
    }

    public void OnDeleteButton(ActionEvent event) {
        String[] arrStr = {Singleton.getInstance().getFinal_NameSername()};
        String tempString = (String) Singleton.getInstance().getDataController().DeletePersonal(arrStr);
        tempString = tempString.replaceAll("\r", "");
        OnReload();
    }

    /*public void fillDashbordPanel() {

        ChangeButton.setText("Изменить информацию");
        ChangeButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        ChangeButton.setOnMouseClicked(mouseEvent -> {
            onChange = true;
            phoneField.setEditable(true);
            emailField.setEditable(true);
            descriptionArea.setEditable(true);
            SaveButton.setDisable(false);
            ChangeButton.setDisable(true);
        });
        ArrayList<JFXButton> buttons = new ArrayList<>();
        buttons.add(ChangeButton);
        buttons.add(SaveButton);

        try {
            String nameSername = OnReload();
            if(Singleton.getInstance().getFinal_NameSername().equals(nameSername)) {
                DashboardController temp = new DashboardController();
                temp.SetVBoxButtons(buttons);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
