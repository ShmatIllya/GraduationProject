package practise.controllers2.Tasks;

import DTO.TaskDTO;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
    public StackPane stackPane;

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

        try {
            OnReload();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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

    public void OnReload() throws JSONException {
        redactButton.setDisable(true);
        deleteButton.setDisable(true);
        ObservableList<TasksItems> observableList = FXCollections.observableArrayList();
        JSONObject arrStr;
        JSONObject tempString = new JSONObject();
        if(windowTypeLabel.getText().equals("Task")) {
             arrStr = new JSONObject();
             tempString = Singleton.getInstance().getDataController().GetTasksList(arrStr);
        }
        else {
            arrStr = new JSONObject();
            tempString = Singleton.getInstance().getDataController().GetProjectList(arrStr);
        }
        JSONArray resultSet = tempString.getJSONArray("taskList");
        for(int i = 0; i < resultSet.length(); i++) {
            try {
                observableList.add(new TasksItems(resultSet.getJSONObject(i).getString("name"),
                        resultSet.getJSONObject(i).getString("completionInfo"),
                        resultSet.getJSONObject(i).getString("creationDate"),
                        resultSet.getJSONObject(i).getString("deadline"),
                        resultSet.getJSONObject(i).getString("responsibleName"),
                        resultSet.getJSONObject(i).getString("checkerName"),
                        resultSet.getJSONObject(i).getString("status"),
                        Integer.parseInt(resultSet.getJSONObject(i).getString("id"))));
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

    public void OnAddTask() throws IOException, JSONException {
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

    public void OnDeleteButton(ActionEvent event) throws JSONException {
        TaskDTO task = new TaskDTO();
        try {
            task.setName(treeTable.getSelectionModel().getSelectedItem().getValue().name.getValue());
            task.setResponsibleName(treeTable.getSelectionModel().getSelectedItem().getValue().responsable.getValue());
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().DeleteTask(task);
        if(tempString.getString("response").equals("null")) {
            Label messageBox = new Label("Ошибка удаления данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        OnReload();
    }
}
