package practise.controllers2.Payment;

import DTO.ItemDTO;
import DTO.PaymentDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.animation.FadeTransition;
import javafx.beans.Observable;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.HelloApplication;
import practise.items.ClientsItems;
import practise.items.ItemsItems;
import practise.singleton.Singleton;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class PaymentAddController implements Initializable {
    public StackPane stackPane;
    public TextField creationDateField;
    public JFXComboBox paymenterComboBox;
    public JFXComboBox recieverComboBox;
    public MFXDatePicker datePicker;
    public TextField subInfoField;
    public JFXComboBox itemsComboBox;
    public JFXButton addItemButton;
    public TextField amountField;
    public TextField finalPriceField;
    public Button submitButton;
    public TextField measurementField;
    public ObservableList<ItemsItems> itemsDataList = FXCollections.observableArrayList();
    public TextField taxesField;
    int KolhozTaxesContainer;
    int KolhozTaxeSContainerOriginal;
    int KolhozPriceContainerOriginal;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        creationDateField.setText(String.valueOf(sqlDate));
        //================================Getting ComboBox Info================================
        try {
            JSONObject arrStr = new JSONObject();
            arrStr.put("operationID", "GetClientsList");
            JSONObject tempString = Singleton.getInstance().getDataController().GetClientsList(arrStr);
            JSONArray resultSet = tempString.getJSONArray("clientList");
            ObservableList<String> clientNamesList = FXCollections.observableArrayList();
            for (int i = 0; i < resultSet.length(); i++) {
                clientNamesList.add(resultSet.getJSONObject(i).getString("name"));
            }
            paymenterComboBox.setItems(clientNamesList);
            recieverComboBox.setItems(clientNamesList);
            PrepareItemBox();
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //================================/Getting ComboBox Info===============================
        paymenterComboBox.setOnAction(event -> {
            recieverComboBox.getSelectionModel().select(paymenterComboBox.getSelectionModel().getSelectedItem());
        });

        itemsComboBox.setOnAction(event -> {
            finalPriceField.setText(String.valueOf(itemsDataList.get(itemsComboBox.getSelectionModel().getSelectedIndex()).getPrice()
                    + itemsDataList.get(itemsComboBox.getSelectionModel().getSelectedIndex()).getTaxes()));
            taxesField.setText("+" + String.valueOf(itemsDataList.get(itemsComboBox.getSelectionModel().getSelectedIndex()).getTaxes())
                    + " (Налог)");
            KolhozTaxeSContainerOriginal = itemsDataList.get(itemsComboBox.getSelectionModel().getSelectedIndex()).getTaxes();
            KolhozTaxesContainer = KolhozTaxeSContainerOriginal;
            KolhozPriceContainerOriginal = Integer.parseInt(finalPriceField.getText());
            amountField.setText("1");
            measurementField.setText(itemsDataList.get(itemsComboBox.getSelectionModel().getSelectedIndex()).getMeasurement());
        });

        amountField.textProperty().addListener((observable, oldvalue, newvalue) -> {
            try {
                if (StringUtils.isNumeric(newvalue)) {
                    KolhozTaxesContainer = KolhozTaxeSContainerOriginal * Integer.parseInt(amountField.getText());
                    finalPriceField.setText(String.valueOf(KolhozPriceContainerOriginal
                            * Integer.parseInt(amountField.getText())));
                    taxesField.setText("+" + KolhozTaxesContainer + " (Налог)");
                }
                else if (newvalue.equals("")) {
                    finalPriceField.setText("");
                    taxesField.setText("");
                }
            }
            catch (Exception e) {
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, new Label("Акстись!!!"));
                throw new RuntimeException(e);
            }
        });


        datePicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", datePicker.getLocale()));
        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(paymenterComboBox.getSelectionModel().selectedItemProperty(),
                        datePicker.textProperty(),
                        itemsComboBox.getSelectionModel().selectedItemProperty(),
                        amountField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (paymenterComboBox.getSelectionModel().isEmpty()
                        || datePicker.getText().isEmpty()
                        || itemsComboBox.getSelectionModel().isEmpty()
                        || !StringUtils.isNumeric(amountField.getText())
                        || amountField.getText().isEmpty());
            }
        };
        submitButton.disableProperty().bind(submitButtonBinding);
    }

    public void OnSubmitButton() throws JSONException {
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        PaymentDTO payment = new PaymentDTO();
        try {
            payment.setCreationDate(sqlDate);
            payment.setDeadline(Date.valueOf(datePicker.getText()));
            payment.setSubInfo(subInfoField.getText());
            payment.setPaymenterName(paymenterComboBox.getSelectionModel().getSelectedItem().toString());
            payment.setReceiverName(recieverComboBox.getSelectionModel().getSelectedItem().toString());
            payment.setItemId(itemsDataList.get(itemsComboBox.getSelectionModel().getSelectedIndex()).getId());
            payment.setAmount(Integer.parseInt(amountField.getText()));
            payment.setFinalPrice(Integer.parseInt(finalPriceField.getText()));
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().AddPayment(payment);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка добавления");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно добавлено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnClose();
        }
    }

    public void PrepareItemBox() {
        try {
            JSONObject arrStr = new JSONObject();
            JSONObject tempString = Singleton.getInstance().getDataController().GetItemsList(arrStr);
            if (!tempString.getString("response").equals("null")) {
                JSONArray resultSet = tempString.getJSONArray("itemList");
                ObservableList<String> itemNamesList = FXCollections.observableArrayList();
                for (int i = 0; i < resultSet.length(); i++) {
                    itemNamesList.add(resultSet.getJSONObject(i).getString("name"));
                    itemsDataList.add(new ItemsItems(resultSet.getJSONObject(i).getString("name"),
                            resultSet.getJSONObject(i).getString("measurement"),
                            resultSet.getJSONObject(i).getString("articulate"),
                            Integer.parseInt(resultSet.getJSONObject(i).getString("price")),
                            Integer.parseInt(resultSet.getJSONObject(i).getString("taxes")),
                            Integer.parseInt(resultSet.getJSONObject(i).getString("id"))));
                }
                itemsComboBox.setItems(itemNamesList);
            }
            else {
                Label MessageLabel = new Label("Ошибка получения данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            }
        }
        catch (JSONException e) {
            Label MessageLabel = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
    }

    public void OnAddItemButtom() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Payments/AddItem.fxml"));
        StackPane page = fxmlLoader.load();
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setX(MouseInfo.getPointerInfo().getLocation().x);
        stage.setY(MouseInfo.getPointerInfo().getLocation().y);
        stage.showAndWait();

        PrepareItemBox();
    }

    public void OnClose() {
        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(stackPane, 1, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Stage st = (Stage) submitButton.getScene().getWindow();
            st.close();
        });
    }
}
