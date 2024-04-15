package practise.controllers2;

import DTO.ClientDTO;
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
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.HelloApplication;
import practise.items.ClientsItems;
import practise.items.PersonalItems;
import practise.singleton.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ClientController implements Initializable {
    @FXML
    public JFXTreeTableView<ClientsItems> treeTable;
    public TextField searchField;
    public ScrollPane mainPane;
    public javafx.scene.layout.AnchorPane AnchorPane;
    public JFXButton redactButton;
    public JFXButton deleteButton;
    public JFXButton addButton;
    public JFXComboBox<String> searchComboBox;
    public StackPane stackPane;

    //=========================Columns====================================
    JFXTreeTableColumn<ClientsItems, String> name;
    JFXTreeTableColumn<ClientsItems, String> type;
    JFXTreeTableColumn<ClientsItems, String> responsable;
    JFXTreeTableColumn<ClientsItems, Integer> id;
    //=========================Columns====================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Название", "Тип клиента", "Ответственный");
        searchComboBox.setItems(list);
        searchComboBox.getSelectionModel().select("Название");
        if(Singleton.getInstance().getFinal_Role().equals("obey")) {
            addButton.setVisible(false);
            deleteButton.setVisible(false);
        }
        name = new JFXTreeTableColumn<>("Название");
        name.setPrefWidth(150);
        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientsItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientsItems, String> param) {
                return param.getValue().getValue().name;
            }
        });

        type = new JFXTreeTableColumn<>("Тип клиента");
        type.setPrefWidth(150);
        type.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientsItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientsItems, String> param) {
                return param.getValue().getValue().type;
            }
        });

        responsable = new JFXTreeTableColumn<>("Ответственный");
        responsable.setPrefWidth(150);
        responsable.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientsItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ClientsItems, String> param) {
                return param.getValue().getValue().responsable;
            }
        });

        id = new JFXTreeTableColumn<>("ИД");
        id.setPrefWidth(150);
        id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ClientsItems, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<ClientsItems, Integer> param) {
                return param.getValue().getValue().id.asObject();
            }
        });
        id.setVisible(false);
        //========================================================================================
        //===============================AddingElements===========================================
        //========================================================================================
        //OnReload();
        //==========================================================================================
        //====================================DoubleClick===========================================
        //==========================================================================================
        treeTable.setOnMouseClicked(mouseEvent -> {
            if(!treeTable.getSelectionModel().getSelectedCells().isEmpty()) {
                if(Singleton.getInstance().getFinal_Role().equals("control")) {
                    deleteButton.setDisable(false);
                }
            }
            if (mouseEvent.getClickCount() == 2 && (! treeTable.getSelectionModel().getSelectedCells().isEmpty()) ) {
                Singleton.getInstance().setClientsID(treeTable.getSelectionModel().getSelectedItem().getValue().id.get());
                DashboardController d = new DashboardController();
                try {
                    d.SwitchMainPane( "/SubFXMLs/Clients/ClientInfo.fxml");
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                treeTable.setPredicate(new Predicate<TreeItem<ClientsItems>>() {
                    @Override
                    public boolean test(TreeItem<ClientsItems> personalItemsTreeItem) {
                        Boolean flag = false;
                        switch (searchComboBox.getSelectionModel().getSelectedItem()) {
                            case "Название": {
                                flag = personalItemsTreeItem.getValue().name.getValue().contains(t1);
                                break;
                            }
                            case "Тип клиента": {
                                flag = personalItemsTreeItem.getValue().type.getValue().contains(t1);
                                break;
                            }
                            case "Ответственный": {
                                flag = personalItemsTreeItem.getValue().responsable.getValue().contains(t1);
                                break;
                            }

                        }
                        return flag;
                    }
                });
            }
        });
        try {
            OnReload();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void OnClientAdd() throws IOException, JSONException {
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Clients/ClientAdd.fxml"));
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

    public void OnReload() throws JSONException {
        deleteButton.setDisable(true);
        ObservableList<ClientsItems> observableList = FXCollections.observableArrayList();
        JSONObject arrStr = new JSONObject();
        arrStr.put("operationID", "GetClientsList");
        JSONObject tempString = Singleton.getInstance().getDataController().GetClientsList(arrStr);
        JSONArray resultSet = tempString.getJSONArray("clientList");
        for(int i = 0; i < resultSet.length(); i++) {

            try {
                observableList.add(new ClientsItems(
                        resultSet.getJSONObject(i).getString("name"),
                        resultSet.getJSONObject(i).getString("type"),
                        resultSet.getJSONObject(i).getString("responsibleName"),
                        resultSet.getJSONObject(i).getInt("id")));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

        final TreeItem<ClientsItems> root = new RecursiveTreeItem<ClientsItems>(observableList, RecursiveTreeObject::getChildren);
        treeTable.getColumns().setAll(name, type, responsable, id);
        treeTable.setRoot(root);
        treeTable.setShowRoot(false);
    }

    public void OnDeleteButton(ActionEvent event) throws JSONException {
        ClientDTO client = new ClientDTO();
        try {
            client.setClientsId(treeTable.getSelectionModel().getSelectedItem().getValue().id.get());
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().DeleteClient(client);
        if(tempString.getString("response").equals("null")) {
            Label messageBox = new Label("Ошибка удаления данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        OnReload();
    }
}
