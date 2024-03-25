package practise.controllers2.Tasks;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import practise.HelloApplication;
import practise.controllers2.DashboardController;
import practise.items.PaymentItems;
import practise.items.PersonalItems;
import practise.items.TasksItems;
import practise.singleton.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class TasksController implements Initializable {

    public JFXTreeTableView<TasksItems> treeTable;
    public TextField searchField;
    public JFXButton redactButton;
    public Label windowTypeLabel;
    public JFXButton deleteButton;
    public JFXComboBox<String> searchComboBox;

    //=========================Columns====================================
    JFXTreeTableColumn<TasksItems, String> name;
    JFXTreeTableColumn<TasksItems, String> businesses;
    JFXTreeTableColumn<TasksItems, String> creationDate;
    JFXTreeTableColumn<TasksItems, String> deadline;
    JFXTreeTableColumn<TasksItems, String> responsable;
    JFXTreeTableColumn<TasksItems, String> checker;
    JFXTreeTableColumn<TasksItems, String> status;
    JFXTreeTableColumn<TasksItems, Integer> id;
    //=========================Columns====================================
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("Наименование", "Счетчик дел", "Дата создания", "Дедлайн", "Ответственный", "Аудитор", "Статус");
        searchComboBox.setItems(list);
        searchComboBox.getSelectionModel().select("Наименование");
        name = new JFXTreeTableColumn<>("Наименование");
        name.setPrefWidth(150);
        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TasksItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TasksItems, String> param) {
                return param.getValue().getValue().name;
            }
        });

        businesses = new JFXTreeTableColumn<>("Счетчик дел");
        businesses.setPrefWidth(150);
        businesses.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TasksItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TasksItems, String> param) {
                return param.getValue().getValue().businesses;
            }
        });

        creationDate = new JFXTreeTableColumn<>("Дата создания");
        creationDate.setPrefWidth(150);
        creationDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TasksItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TasksItems, String> param) {
                return param.getValue().getValue().creationDate;
            }
        });

        deadline = new JFXTreeTableColumn<>("Дедлайн");
        deadline.setPrefWidth(150);
        deadline.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TasksItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TasksItems, String> param) {
                return param.getValue().getValue().deadline;
            }
        });

        responsable = new JFXTreeTableColumn<>("Ответственный");
        responsable.setPrefWidth(150);
        responsable.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TasksItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TasksItems, String> param) {
                return param.getValue().getValue().responsable;
            }
        });

        checker = new JFXTreeTableColumn<>("Аудитор");
        checker.setPrefWidth(150);
        checker.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TasksItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TasksItems, String> param) {
                return param.getValue().getValue().checker;
            }
        });

        status = new JFXTreeTableColumn<>("Статус");
        status.setPrefWidth(150);
        status.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TasksItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<TasksItems, String> param) {
                return param.getValue().getValue().status;
            }
        });

        id = new JFXTreeTableColumn<>("ИД");
        id.setPrefWidth(150);
        id.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<TasksItems, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<TasksItems, Integer> param) {
                return param.getValue().getValue().id.asObject();
            }
        });
        id.setVisible(false);
        //========================================================================================
        //===============================AddingElements===========================================
        //========================================================================================

        OnReload();
        //==========================================================================================
        //====================================DoubleClick===========================================
        //==========================================================================================
        treeTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && (! treeTable.getSelectionModel().getSelectedCells().isEmpty()) ) {
                //Singleton.getInstance().setPersonalName(String.valueOf(treeTable.getSelectionModel().getSelectedItem().getValue().name));
                DashboardController d = new DashboardController();
                try {
                    if(windowTypeLabel.getText().equals("Task")) {
                        Singleton.getInstance().setTaskInfoValues(new String[]{
                                treeTable.getSelectionModel().getSelectedItem().getValue().name.getValue(),
                                treeTable.getSelectionModel().getSelectedItem().getValue().responsable.getValue()
                        });
                        d.SwitchMainPane("/SubFXMLs/Tasks/TaskInfo.fxml");
                    }
                    else {
                        Singleton.getInstance().setProjectInfoValue(treeTable.getSelectionModel().getSelectedItem().
                                getValue().name.getValue());
                        d.SwitchMainPane("/SubFXMLs/Projects/ProjectInfo.fxml");
                    }
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else if(mouseEvent.getClickCount() == 1 && (! treeTable.getSelectionModel().getSelectedCells().isEmpty()) ) {
                redactButton.setDisable(false);
            }
        });

        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                treeTable.setPredicate(new Predicate<TreeItem<TasksItems>>() {
                    @Override
                    public boolean test(TreeItem<TasksItems> personalItemsTreeItem) {
                        Boolean flag = false;
                        switch (searchComboBox.getSelectionModel().getSelectedItem()) {
                            case "Наименование": {
                                flag = personalItemsTreeItem.getValue().name.getValue().contains(t1);
                                break;
                            }
                            case "Счетчик дел": {
                                flag = personalItemsTreeItem.getValue().businesses.getValue().contains(t1);
                                break;
                            }
                            case "Дата создания": {
                                flag = personalItemsTreeItem.getValue().creationDate.getValue().contains(t1);
                                break;
                            }
                            case "Дедлайн": {
                                flag = personalItemsTreeItem.getValue().deadline.getValue().contains(t1);
                                break;
                            }
                            case "Ответственный": {
                                flag = personalItemsTreeItem.getValue().responsable.getValue().contains(t1);
                                break;
                            }
                            case "Аудитор": {
                                flag = personalItemsTreeItem.getValue().checker.getValue().contains(t1);
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

    public void OnReload() {
        redactButton.setDisable(true);
        ObservableList<TasksItems> observableList = FXCollections.observableArrayList();
        String[] arrStr;
        String tempString;
        if(windowTypeLabel.getText().equals("Task")) {
             arrStr = new String[]{"GetTasksList"};
             tempString = (String) Singleton.getInstance().getDataController().GetTasksList(arrStr);
        }
        else {
            arrStr = new String[]{"GetProjectsList"};
            tempString = (String) Singleton.getInstance().getDataController().GetProjectsList(arrStr);
        }
        tempString = tempString.replaceAll("\r", "");
        System.out.println(tempString);
        String[] resultSet = tempString.split(">>");
        for(String i : resultSet) {
            String[] resultSubSet = i.split("<<");
            try {
                observableList.add(new TasksItems(resultSubSet[0], resultSubSet[1], resultSubSet[2], resultSubSet[3],
                        resultSubSet[4], resultSubSet[5], resultSubSet[6], Integer.parseInt(resultSubSet[7])));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        final TreeItem<TasksItems> root = new RecursiveTreeItem<TasksItems>(observableList, RecursiveTreeObject::getChildren);
        treeTable.getColumns().setAll(name, businesses, creationDate, deadline, responsable, checker);
        treeTable.setRoot(root);
        treeTable.setShowRoot(false);
    }

    public void OnAddTask() throws IOException {
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);
        FXMLLoader fxmlLoader;
        if(windowTypeLabel.getText().equals("Task")) {
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Tasks/TaskAdd.fxml"));
        }
        else {
            fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Projects/ProjectAdd.fxml"));
        }
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

    public void OnRedactTask() {

    }

    public void OnDeleteButton(ActionEvent event) {
        String[] arrStr = {treeTable.getSelectionModel().getSelectedItem().getValue().name.getValue(),
                treeTable.getSelectionModel().getSelectedItem().getValue().responsable.getValue()};
        String tempString = (String) Singleton.getInstance().getDataController().DeleteTask(arrStr);
        tempString = tempString.replaceAll("\r", "");
    }
}
