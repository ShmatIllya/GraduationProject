package practise.controllers2.Payment;

import DTO.ItemDTO;
import com.jfoenix.controls.JFXComboBox;
import com.mysql.cj.conf.StringProperty;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import practise.singleton.Singleton;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {
    public StackPane stackPane;
    public TextField nameField;
    public TextField articulField;
    public TextField priceField;
    public TextField finalPriceField;
    public JFXComboBox<String> taxComboBox;
    public JFXComboBox<String> measurementComboBox;
    public Button submitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("кг", "шт", "г", "м", "см");
        measurementComboBox.setItems(list);
        list = FXCollections.observableArrayList("20", "15", "10", "5", "Отсутствует");
        taxComboBox.setItems(list);
        taxComboBox.getSelectionModel().select("Отсутствует");
        //==========================Events===============================
            taxComboBox.setOnAction(event -> {
                try {
                    if (StringUtils.isNumeric(priceField.getText())) {
                        if (taxComboBox.getSelectionModel().getSelectedItem().equals("Отсутствует")) {
                            finalPriceField.setText(priceField.getText());
                        } else {
                            finalPriceField.setText(String.valueOf((Integer.parseInt(priceField.getText())
                                    * (Integer.parseInt(taxComboBox.getSelectionModel().getSelectedItem()) + 100)) / 100));
                        }
                    }
                }
                catch (Exception e) {
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, new Label("Проверятор, нахуй пошел"));
                }
            });
            priceField.textProperty().addListener((observable, oldvalue, newvalue) -> {
                try {
                    if (StringUtils.isNumeric(newvalue)) {
                        if (taxComboBox.getSelectionModel().getSelectedItem().equals("Отсутствует")) {
                            finalPriceField.setText(newvalue);
                        } else {
                            finalPriceField.setText(String.valueOf((Integer.parseInt(newvalue)
                                    * (Integer.parseInt(taxComboBox.getSelectionModel().getSelectedItem()) + 100)) / 100));
                        }
                    }
                    else if (newvalue.equals("")) {
                        finalPriceField.setText("");
                    }
                }
                 catch (Exception e) {
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, new Label("Проверятор, нахуй пошел"));
                }
            });

        //==========================/Events==============================
        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(nameField.textProperty(),
                        priceField.textProperty(),
                        measurementComboBox.getSelectionModel().selectedItemProperty());
            }

            @Override
            protected boolean computeValue() {
                return (nameField.getText().isEmpty()
                        || priceField.getText().isEmpty()
                        || measurementComboBox.getSelectionModel().isEmpty())
                        || !StringUtils.isNumeric(priceField.getText());
            }
        };
        submitButton.disableProperty().bind(submitButtonBinding);
    }

    public void OnSubmitButton() throws JSONException {
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        ItemDTO item = new ItemDTO();
        try {
            item.setName(nameField.getText());
            item.setArticul(articulField.getText());
            item.setPrice(Integer.parseInt(finalPriceField.getText()));
            item.setTaxes(Integer.parseInt(finalPriceField.getText()) - Integer.parseInt(priceField.getText()));
            item.setMeasurement(measurementComboBox.getSelectionModel().getSelectedItem());
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().AddItem(item);
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

    public void OnClose() {
        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(stackPane, 1, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Stage st = (Stage) submitButton.getScene().getWindow();
            st.close();
        });
    }
}
