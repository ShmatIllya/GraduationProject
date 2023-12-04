package practise.controllers2;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import practise.HelloApplication;
import practise.items.ClientsItems;
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

    //=========================Columns====================================
    JFXTreeTableColumn<ClientsItems, String> name;
    JFXTreeTableColumn<ClientsItems, String> type;
    JFXTreeTableColumn<ClientsItems, String> responsable;
    JFXTreeTableColumn<ClientsItems, Integer> id;
    //=========================Columns====================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
                    public boolean test(TreeItem<ClientsItems> clientsItemsTreeItem) {
                        Boolean flag = clientsItemsTreeItem.getValue().name.getValue().contains(t1);
                        return flag;
                    }
                });
            }
        });
        System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiii");
        OnReload();
    }

    public void OnClientAdd() throws IOException {
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

    public void OnReload() {
        ObservableList<ClientsItems> observableList = FXCollections.observableArrayList();
        String[] arrStr = {"GetClientsList"};
        String tempString = (String) Singleton.getInstance().getDataController().GetClientsList(arrStr);
        tempString = tempString.replaceAll("\r", "");
        String[] resultSet = tempString.split(">>");
        for(String i : resultSet) {
            String[] resultSubSet = i.split("<<");
            try {
                observableList.add(new ClientsItems(resultSubSet[0], resultSubSet[1], resultSubSet[2], Integer.valueOf(resultSubSet[3])));
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
}
