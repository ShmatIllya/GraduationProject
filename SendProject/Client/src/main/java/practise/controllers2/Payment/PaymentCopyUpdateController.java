package practise.controllers2.Payment;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.lang3.StringUtils;
import practise.HelloApplication;
import practise.items.ItemsItems;
import practise.singleton.Singleton;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentCopyUpdateController implements Initializable{
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
    public Label titleLabel;
    int KolhozTaxesContainer;
    int KolhozTaxeSContainerOriginal;
    int KolhozPriceContainerOriginal;
    Boolean choice;
    String localId;

    public void InitController(Boolean choice, ArrayList<String> values) {
        System.out.println(KolhozTaxesContainer + " " + KolhozPriceContainerOriginal + " " + KolhozPriceContainerOriginal);
        //================================Elements Set=============================
        creationDateField.setText(values.get(0));
        paymenterComboBox.getSelectionModel().select(values.get(1));
        recieverComboBox.getSelectionModel().select(values.get(2));
        datePicker.setText(values.get(3));
        subInfoField.setText(values.get(4));
        localId = values.get(10);
        //===============================/Elements Set=============================
        if(choice == true) {
            titleLabel.setText("Обновление счета");
        }
        else {
            titleLabel.setText("Копирование счета");
        }
        this.choice = choice;
    }

    public void OnSubmitButton() {
        if(choice == false) {
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
            String[] arrStr = {"AddPayment", sqlDate.toString(), datePicker.getText(), subInfoField.getText(),
                    paymenterComboBox.getSelectionModel().getSelectedItem().toString(),
                    recieverComboBox.getSelectionModel().getSelectedItem().toString(),
                    String.valueOf(itemsDataList.get(itemsComboBox.getSelectionModel().getSelectedIndex()).getId()),
                    amountField.getText(), finalPriceField.getText()};
            String tempString = (String) Singleton.getInstance().getDataController().AddPayment(arrStr);
            tempString = tempString.replaceAll("\r", "");
            //String[] resultSet = tempString.split("<<");
            Label MessageLabel = new Label();
            if (tempString.equals("null")) {
                MessageLabel.setText("Ошибка добавления");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            } else {
                MessageLabel.setText("Успешно добавлено");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
                OnClose();
            }
        }
        else {
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
            String[] arrStr = {"UpdatePayment", sqlDate.toString(), datePicker.getText(), subInfoField.getText(),
                    paymenterComboBox.getSelectionModel().getSelectedItem().toString(),
                    recieverComboBox.getSelectionModel().getSelectedItem().toString(),
                    String.valueOf(itemsDataList.get(itemsComboBox.getSelectionModel().getSelectedIndex()).getId()),
                    amountField.getText(), finalPriceField.getText(), localId};
            String tempString = (String) Singleton.getInstance().getDataController().UpdatePayment(arrStr);
            tempString = tempString.replaceAll("\r", "");
            //String[] resultSet = tempString.split("<<");
            Label MessageLabel = new Label();
            if (tempString.equals("null")) {
                MessageLabel.setText("Ошибка обновления");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            } else {
                MessageLabel.setText("Успешно обновлено");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
                OnClose();
            }
        }
    }

    public void PrepareItemBox() {
        String [] arrStr = {"GetItemsList"};
        String tempString = (String) Singleton.getInstance().getDataController().GetItemsList(arrStr);
        tempString = tempString.replaceAll("\r", "");
        if(!tempString.equals("")) {
            String[] resultSet = tempString.split(">>");
            ObservableList<String> itemNamesList = FXCollections.observableArrayList();
            for (String i : resultSet) {
                String[] resultSubSet = i.split("<<");
                itemNamesList.add(resultSubSet[0]);
                itemsDataList.add(new ItemsItems(resultSubSet[0], resultSubSet[4], resultSubSet[1],
                        Integer.parseInt(resultSubSet[2]), Integer.parseInt(resultSubSet[3]),
                        Integer.parseInt(resultSubSet[5])));
            }
            itemsComboBox.setItems(itemNamesList);
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        creationDateField.setText(String.valueOf(sqlDate));
        //================================Getting ComboBox Info================================
        String[] arrStr = {"GetClientsList"};
        String tempString = (String) Singleton.getInstance().getDataController().GetClientsList(arrStr);
        tempString = tempString.replaceAll("\r", "");
        String[] resultSet = tempString.split(">>");
        ObservableList<String> clientNamesList = FXCollections.observableArrayList();
        for(String i : resultSet) {
            String[] resultSubSet = i.split("<<");
            clientNamesList.add(resultSubSet[0]);
        }
        paymenterComboBox.setItems(clientNamesList);
        recieverComboBox.setItems(clientNamesList);
        PrepareItemBox();
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
}
