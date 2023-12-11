package practise.controllers2.Payment;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import practise.HelloApplication;
import practise.controllers2.DashboardController;
import practise.items.ClientsItems;
import practise.items.PaymentInfoItems;
import practise.singleton.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentInfoController implements Initializable {
    public JFXButton sendButton;
    public StackPane stackPane;
    public TableView<PaymentInfoItems> tableView;
    public TableColumn<PaymentInfoItems, Integer> idColumn;
    public TableColumn<PaymentInfoItems, String> nameColumn;
    public TableColumn<PaymentInfoItems, String> measurementColumn;
    public TableColumn<PaymentInfoItems, Integer> amountColumn;
    public TableColumn<PaymentInfoItems, Integer> priceColumn;
    public TableColumn<PaymentInfoItems, Integer> taxesColumn;
    public TableColumn<PaymentInfoItems, Integer> finalPriceColumn;
    public TextField dateField;
    public TextField paymentField;
    public TextField receiverField;
    public TextField patternField;
    public JFXButton sendButtonSample;
    public HBox ChoiceHBoxSample;
    public Label idLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, String>("name"));
        measurementColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, String>("measurement"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("price"));
        taxesColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("taxes"));
        finalPriceColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("finalPrice"));
        OnReload();
    }

    public void OnReload() {
        idLabel.setText("Счет " + Singleton.getInstance().getClientsID());
        ObservableList<ClientsItems> observableList = FXCollections.observableArrayList();
        String[] arrStr = {"GetPaymentInfo", String.valueOf(Singleton.getInstance().getClientsID())};
        String tempString = (String) Singleton.getInstance().getDataController().GetPaymentInfo(arrStr);
        tempString = tempString.replaceAll("\r", "");
        String[] resultSet = tempString.split(">>");
        dateField.setText(resultSet[0]);
        paymentField.setText(resultSet[1]);
        receiverField.setText(resultSet[2]);
        ObservableList<PaymentInfoItems> list;
        for(int i = 4; i < resultSet.length; i++) {
            String[] resultSubSet = resultSet[i].split("<<");
            list = FXCollections.observableArrayList(new PaymentInfoItems(Integer.parseInt(resultSubSet[0]),
                    resultSubSet[1], resultSubSet[2], Integer.parseInt(resultSubSet[3]),
                    Integer.parseInt(resultSubSet[4]), Integer.parseInt(resultSubSet[5]),
                    Integer.parseInt(resultSubSet[6])));
            tableView.setItems(list);
        }

        if(resultSet[3].equals("Передан в оплату")) {
            double posX = sendButton.getLayoutX();
            double posY = sendButton.getLayoutY();
            sendButton.setVisible(false);
            sendButton.setLayoutX(ChoiceHBoxSample.getLayoutX());
            sendButton.setLayoutY(ChoiceHBoxSample.getLayoutY());
            ChoiceHBoxSample.setLayoutX(posX);
            ChoiceHBoxSample.setLayoutY(posY);
            ChoiceHBoxSample.setVisible(true);
        }
        else if(resultSet[3].equals("Оплачен") || resultSet[3].equals("Отменен")) {
            sendButton.setVisible(false);
            ChoiceHBoxSample.setVisible(false);
        }
    }

    public void OnSendButton(ActionEvent event) {
        String[] arrStr = {"ChangePaymentStatus", String.valueOf(Singleton.getInstance().getClientsID()), "Передан в оплату"};
        String tempString = (String) Singleton.getInstance().getDataController().ChangePaymentStatus(arrStr);
        tempString = tempString.replaceAll("\r", "");
        Label messageBox = new Label();
        if(tempString.equals("null")) {
            messageBox.setText("Ошибка изменения");
        }
        else {
            messageBox.setText("Счет передан в оплату");
        }
        Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
        OnReload();
    }

    public void OnRedactButton(ActionEvent event) throws IOException {

        ArrayList<String> list = new ArrayList<>();
        String[] arrStr = {"GetFullPaymentInfo", String.valueOf(Singleton.getInstance().getClientsID())};
        String tempString = (String) Singleton.getInstance().getDataController().GetFullPaymentInfo(arrStr);
        tempString = tempString.replaceAll("\r", "");
        String[] resultSet = tempString.split(">>");
        for(int i = 0; i < resultSet.length; i++) {
            list.add(resultSet[i]);
        }


        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Payments/PaymentCopyUpdate.fxml"));
        StackPane page = fxmlLoader.load();
        page.setAlignment(Pos.CENTER);
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        PaymentCopyUpdateController controller = fxmlLoader.getController();
        controller.InitController(true, list);
        stage.showAndWait();

        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0.5, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Singleton.getInstance().getOpacityPane().setVisible(false);
        });
        OnReload();
    }

    public void OnCopyButton(ActionEvent event) throws IOException {

        ArrayList<String> list = new ArrayList<>();
        String[] arrStr = {"GetFullPaymentInfo", String.valueOf(Singleton.getInstance().getClientsID())};
        String tempString = (String) Singleton.getInstance().getDataController().GetFullPaymentInfo(arrStr);
        tempString = tempString.replaceAll("\r", "");
        String[] resultSet = tempString.split(">>");
        for(int i = 0; i < resultSet.length; i++) {
            list.add(resultSet[i]);
        }


        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Payments/PaymentCopyUpdate.fxml"));
        StackPane page = fxmlLoader.load();
        page.setAlignment(Pos.CENTER);
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        PaymentCopyUpdateController controller = fxmlLoader.getController();
        controller.InitController(false, list);
        stage.showAndWait();

        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0.5, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Singleton.getInstance().getOpacityPane().setVisible(false);
        });
        OnReload();
    }

    public void OnDeleteButton(ActionEvent event) throws IOException {
        String[] arrStr = {"DeletePayment", String.valueOf(Singleton.getInstance().getClientsID())};
        String tempString = (String) Singleton.getInstance().getDataController().DeletePayment(arrStr);
        DashboardController dashboardController = new DashboardController();
        dashboardController.SwitchMainPane("/SubFXMLs/Payments/Payment.fxml");
    }

    public void OnConfirmButton(ActionEvent event) {
        String[] arrStr = {"ChangePaymentStatus", String.valueOf(Singleton.getInstance().getClientsID()), "Оплачен"};
        String tempString = (String) Singleton.getInstance().getDataController().ChangePaymentStatus(arrStr);
        tempString = tempString.replaceAll("\r", "");
        Label messageBox = new Label();
        if(tempString.equals("null")) {
            messageBox.setText("Ошибка изменения");
        }
        else {
            messageBox.setText("Счет оплачен");
        }
        Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
        OnReload();
    }

    public void OnRejectButton(ActionEvent event) {
        String[] arrStr = {"ChangePaymentStatus", String.valueOf(Singleton.getInstance().getClientsID()), "Отменен"};
        String tempString = (String) Singleton.getInstance().getDataController().ChangePaymentStatus(arrStr);
        tempString = tempString.replaceAll("\r", "");
        Label messageBox = new Label();
        if(tempString.equals("null")) {
            messageBox.setText("Ошибка изменения");
        }
        else {
            messageBox.setText("Счет отменен");
        }
        Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
        OnReload();
    }

}
