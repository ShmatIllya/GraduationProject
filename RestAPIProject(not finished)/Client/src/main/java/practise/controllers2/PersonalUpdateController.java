package practise.controllers2;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import practise.singleton.Singleton;

import java.io.IOException;

public class PersonalUpdateController {
    public StackPane stackPane;
    public TextField sernameField;
    public TextField nameField;
    public TextField otchestvoField;
    public TextField emailField;
    public TextField phoneField;
    public TextField subroleField;
    public ToggleGroup role;
    public JFXComboBox statusComboBox;
    public TextField loginField;
    public PasswordField passwordField;
    public Button submitButton;

    public void setData(String sername, String name, String otchestvo, String email, String phone, String subrole,
                        String status, String login, String password) {
        sernameField.setText(sername);
        nameField.setText(name);
        otchestvoField.setText(otchestvo);
        emailField.setText(email);
        phoneField.setText(phone);
        subroleField.setText(subrole);
        ObservableList<String> list = FXCollections.observableArrayList("Активен", "Отпуск", "Уволен", "Приостановлен");
        statusComboBox.setItems(list);
        statusComboBox.getSelectionModel().select(status);
        loginField.setText(login);
        passwordField.setText(password);
        //==================================================================
        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(subroleField.textProperty(),
                        emailField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (subroleField.getText().isEmpty()
                        || emailField.getText().isEmpty());
            }
        };
        submitButton.disableProperty().bind(submitButtonBinding);
        //==================================================================
    }

    public void OnSubmitButton() throws IOException, JSONException {
        RadioButton radioButton = (RadioButton) role.getSelectedToggle();
        String roleS = "";
        switch (radioButton.getText()) {
            case "Да": {
                roleS = "control";
                break;
            }
            case "Нет": {
                roleS = "obey";
                break;
            }
        }
        JSONObject arrStr = new JSONObject();
        arrStr.put("nameSername", Singleton.getInstance().getFinal_NameSername());
        arrStr.put("login", loginField.getText());
        arrStr.put("contacts", phoneField.getText());
        arrStr.put("email", emailField.getText());
        arrStr.put("role", roleS);
        arrStr.put("subrole", subroleField.getText());
        arrStr.put("status", statusComboBox.getSelectionModel().getSelectedItem().toString());
        arrStr.put("operationID", "UpdatePersonalInfoAsManager");
        JSONObject tempString = Singleton.getInstance().getDataController().UpdatePersonalInfoAsManager(arrStr);
        Label MessageLabel = new Label();
        if(tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка операции");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно изменено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnClose();
        }
    }

    public void OnClose() throws IOException {
        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(stackPane, 1, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Stage st = (Stage) submitButton.getScene().getWindow();
            st.close();
        });
    }
}
