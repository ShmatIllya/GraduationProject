package practise.controllers2;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONException;
import org.json.JSONObject;
import practise.HelloApplication;
import practise.singleton.Singleton;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class PersonalAddController implements Initializable {
    public TextField sernameField;
    public TextField nameField;
    public TextField otchestvoField;
    public TextField emailField;
    public TextField phoneField;
    public TextField subroleField;
    public TextField loginField;
    public PasswordField passwordField;
    public PasswordField password2Field;
    public Label ErrorLabel;
    public Button submitButton;
    public ToggleGroup role;
    public StackPane stackPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(nameField.textProperty(),
                        sernameField.textProperty(),
                        emailField.textProperty(),
                        loginField.textProperty(),
                        passwordField.textProperty(),
                        password2Field.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (nameField.getText().isEmpty()
                        || sernameField.getText().isEmpty()
                        || emailField.getText().isEmpty()
                        || loginField.getText().isEmpty()
                        || passwordField.getText().isEmpty()
                        || password2Field.getText().isEmpty());
            }
        };
        submitButton.disableProperty().bind(submitButtonBinding);
    }

    public void OnSubmitButton() throws IOException, JSONException {
        passwordField.getStyleClass().remove("tf_box_fail");
        password2Field.getStyleClass().remove("tf_box_fail");
        ErrorLabel.setVisible(false);

        if (!passwordField.getText().equals(password2Field.getText())) {
            passwordField.getStyleClass().add("tf_box_fail");
            password2Field.getStyleClass().add("tf_box_fail");
            ErrorLabel.setVisible(true);
        }
        else {
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
            java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
            JSONObject arrStr = new JSONObject();
            arrStr.put("operationID", "AddPersonal");
            arrStr.put("login", loginField.getText());
            arrStr.put("password", passwordField.getText());
            arrStr.put("nameSername", nameField.getText() + " " + sernameField.getText() + " " + otchestvoField.getText());
            arrStr.put("contacts", phoneField.getText());
            arrStr.put("email", emailField.getText());
            arrStr.put("role", roleS);
            arrStr.put("subrole", subroleField.getText());
            arrStr.put("status", "Активен");
            arrStr.put("date", sqlDate.toString());
//            String[] arrStr = {loginField.getText(), passwordField.getText(), nameField.getText()
//                    + " " + sernameField.getText() + " " + otchestvoField.getText(), phoneField.getText(),
//                    emailField.getText(), roleS, subroleField.getText(), "Активен", "", sqlDate.toString(), ""};

            JSONObject result = Singleton.getInstance().getDataController().AddPersonal(arrStr);

            Label MessageLabel = new Label();
            if(result.getString("response").equals("null")) {
                MessageLabel.setText("Ошибка входа");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            }
            else {
                MessageLabel.setText("Успешно добавлено");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
                OnClose();
            }
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
