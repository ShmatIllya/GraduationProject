package practise.controllers2;

import DTO.PersonalDTO;
import com.dlsc.gemsfx.EmailField;
import com.dlsc.gemsfx.PhoneNumberField;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.json.JSONException;
import org.json.JSONObject;
import practise.singleton.Singleton;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    public TextField RegLoginField;
    public PasswordField RegPasswordField;
    public TextField NameField;
    public com.dlsc.gemsfx.EmailField EmailField;
    public PhoneNumberField PhoneField;
    public Label ErrorLabel;
    public VBox vBox;
    public StackPane stackPane;
    public Button submitButton;

    @FXML
    public void OnSignUpButton(javafx.event.ActionEvent event) throws JSONException {
        RegLoginField.getStyleClass().remove("tf_box_fail");
        RegPasswordField.getStyleClass().remove("tf_box_fail");
        NameField.getStyleClass().remove("tf_box_fail");
        EmailField.getStyleClass().remove("tf_box_fail");
        PhoneField.getStyleClass().remove("tf_box_fail");
        ErrorLabel.setVisible(false);

        if (RegLoginField.getText().isBlank() || RegPasswordField.getText().isBlank() ||
                NameField.getText().isBlank() || EmailField.getEmailAddress().isBlank()
                || PhoneField.getPhoneNumber().isBlank() || !EmailField.isValid()) {
            if (RegPasswordField.getText().isBlank()) {
                RegPasswordField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
            if (RegLoginField.getText().isBlank()) {
                RegLoginField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
            if (NameField.getText().isBlank()) {
                NameField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
            if (EmailField.getEmailAddress().isBlank()) {
                EmailField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
            if (PhoneField.getPhoneNumber().isBlank()) {
                PhoneField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
        } else {
            PersonalDTO arrStr = new PersonalDTO();
            try {
                arrStr.setLogin(RegLoginField.getText());
                arrStr.setPassword(RegPasswordField.getText());
                arrStr.setNameSername(NameField.getText());
                arrStr.setContacts(PhoneField.getPhoneNumber());
                arrStr.setEmail(EmailField.getEmailAddress());
                arrStr.setRegDate(Date.valueOf(LocalDate.now()));
            } catch (Exception e) {
                Label messageBox = new Label("Ошибка ввода данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                return;
            }
            JSONObject result = Singleton.getInstance().getDataController().Registration(arrStr);
            Label MessageLabel = new Label();
            if (result.getString("response").equals("null")) {
                MessageLabel.setText("Ошибка регистрации");
            } else {
                MessageLabel.setText("Заявка на регистрацию успешно отправлена");
            }
            JFXDialog messageBox = new JFXDialog(stackPane, MessageLabel, JFXDialog.DialogTransition.CENTER);
            messageBox.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(RegLoginField.textProperty(),
                        RegPasswordField.textProperty(),
                        EmailField.emailAddressProperty(),
                        NameField.textProperty(),
                        PhoneField.phoneNumberProperty());
            }

            @Override
            protected boolean computeValue() {
                return (RegLoginField.getText().isEmpty()
                        || RegPasswordField.getText().isEmpty()
                        || EmailField.getEmailAddress().isEmpty()
                        || !EmailField.isValid()
                        || NameField.getText().isEmpty()
                        || PhoneField.getPhoneNumber().isEmpty());
            }
        };
        submitButton.disableProperty().bind(submitButtonBinding);
        EmailField.setEmailAddress("1");
        PhoneField.setPhoneNumber("1");
        RegLoginField.setText("1");
        RegPasswordField.setText("1");
        NameField.setText("1");
        EmailField.setEmailAddress("");
        PhoneField.setPhoneNumber("");
        RegLoginField.clear();
        RegPasswordField.clear();
        NameField.clear();
    }
}
