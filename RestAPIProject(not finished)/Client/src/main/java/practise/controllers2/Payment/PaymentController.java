package practise.controllers2.Payment;

import DTO.PaymentDTO;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
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
import practise.items.ClientsItems;
import practise.items.PaymentItems;
import practise.items.PersonalItems;
import practise.singleton.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class PaymentController implements Initializable {
    public JFXTreeTableView<PaymentItems> tableView;
    public JFXButton addButton;
    public JFXComboBox<String> searchComboBox;
    public TextField searchField;
    public StackPane stackPane;
    JFXTreeTableColumn<PaymentItems, Integer> idColumn;
    JFXTreeTableColumn<PaymentItems, String> dateColumn;
    JFXTreeTableColumn<PaymentItems, Integer> moneyColumn;
    JFXTreeTableColumn<PaymentItems, String> paymenterColumn;
    JFXTreeTableColumn<PaymentItems, String> statusColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("ИД", "Дата создания", "Сумма", "Плательщик", "Статус");
        searchComboBox.setItems(list);
        searchComboBox.getSelectionModel().select("ИД");
        if (Singleton.getInstance().getFinal_Role().equals("obey")) {
            addButton.setVisible(false);
        }
        idColumn = new JFXTreeTableColumn<>("ИД");
        idColumn.setPrefWidth(150);
        idColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PaymentItems, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<PaymentItems, Integer> param) {
                return param.getValue().getValue().id.asObject();
            }
        });

        dateColumn = new JFXTreeTableColumn<>("Дата создания");
        dateColumn.setPrefWidth(150);
        dateColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PaymentItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PaymentItems, String> param) {
                return param.getValue().getValue().date;
            }
        });

        moneyColumn = new JFXTreeTableColumn<>("Сумма");
        moneyColumn.setPrefWidth(150);
        moneyColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PaymentItems, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<PaymentItems, Integer> param) {
                return param.getValue().getValue().money.asObject();
            }
        });

        paymenterColumn = new JFXTreeTableColumn<>("Плательщик");
        paymenterColumn.setPrefWidth(150);
        paymenterColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PaymentItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PaymentItems, String> param) {
                return param.getValue().getValue().paymenter;
            }
        });

        statusColumn = new JFXTreeTableColumn<>("Статус");
        statusColumn.setPrefWidth(150);
        statusColumn.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<PaymentItems, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<PaymentItems, String> param) {
                return param.getValue().getValue().status;
            }
        });

        tableView.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2 && (!tableView.getSelectionModel().getSelectedCells().isEmpty())) {
                Singleton.getInstance().setClientsID(tableView.getSelectionModel().getSelectedItem().getValue().id.get());
                DashboardController d = new DashboardController();
                try {
                    d.SwitchMainPane("/SubFXMLs/Payments/PaymentInfo.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                tableView.setPredicate(new Predicate<TreeItem<PaymentItems>>() {
                    @Override
                    public boolean test(TreeItem<PaymentItems> personalItemsTreeItem) {
                        Boolean flag = false;
                        switch (searchComboBox.getSelectionModel().getSelectedItem()) {
                            case "ИД": {
                                flag = personalItemsTreeItem.getValue().id.getValue().toString().contains(t1);
                                break;
                            }
                            case "Дата создания": {
                                flag = personalItemsTreeItem.getValue().date.getValue().contains(t1);
                                break;
                            }
                            case "Сумма": {
                                flag = personalItemsTreeItem.getValue().money.getValue().toString().contains(t1);
                                break;
                            }
                            case "Плательщик": {
                                flag = personalItemsTreeItem.getValue().paymenter.getValue().contains(t1);
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
        try {
            OnReload();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void OnReload() throws JSONException {
        ObservableList<PaymentItems> observableList = FXCollections.observableArrayList();
        JSONObject arrStr = new JSONObject();
        ArrayList<PaymentDTO> tempString = Singleton.getInstance().getDataController().GetPaymentList(arrStr);
        if (tempString != null) {
            for (PaymentDTO paymentDTO: tempString) {
                try {
                    observableList.add(new PaymentItems(Integer.valueOf(paymentDTO.getPaymentId()),
                            paymentDTO.getDeadline().toString(),
                            paymentDTO.getFinalPrice(),
                            paymentDTO.getPaymenterName(),
                            paymentDTO.getStatus()));
                } catch (Exception e) {
                    return;
                }
            }
            final TreeItem<PaymentItems> root = new RecursiveTreeItem<PaymentItems>(observableList, RecursiveTreeObject::getChildren);
            tableView.getColumns().setAll(idColumn, dateColumn, moneyColumn, paymenterColumn, statusColumn);
            tableView.setRoot(root);
            tableView.setShowRoot(false);
        } else {
            Label MessageLabel = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
    }

    public void OnAddButton() throws IOException, JSONException {
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Payments/PaymentAdd.fxml"));
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
}
