package practise.controllers2;

import DTO.PersonalDTO;
import com.dlsc.gemsfx.EmailField;
import com.dlsc.gemsfx.PhoneNumberField;
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
    public EmailField emailField;
    public PhoneNumberField phoneField;
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
        emailField.setEmailAddress(email);
        phoneField.setPhoneNumber(phone);
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
                        emailField.emailAddressProperty());
            }

            @Override
            protected boolean computeValue() {
                return (subroleField.getText().isEmpty()
                        || emailField.getEmailAddress().isEmpty())
                        || !emailField.isValid();
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
        PersonalDTO arrStr = new PersonalDTO();
        try {
            arrStr.setNameSername(Singleton.getInstance().getFinal_NameSername());
            arrStr.setLogin(loginField.getText());
            if(phoneField.getPhoneNumber() == null) {
                arrStr.setContacts("");
            }
            else {
                arrStr.setContacts(phoneField.getPhoneNumber());
            }
            arrStr.setEmail(emailField.getEmailAddress());
            arrStr.setRole(roleS);
            arrStr.setSubrole(subroleField.getText());
            arrStr.setStatus(statusComboBox.getSelectionModel().getSelectedItem().toString());
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
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
