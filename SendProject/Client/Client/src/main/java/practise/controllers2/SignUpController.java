package practise.controllers2;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import practise.singleton.Singleton;

public class SignUpController {
    public TextField RegLoginField;
    public TextField RegPasswordField;
    public TextField NameField;
    public TextField EmailField;
    public TextField PhoneField;
    public Label ErrorLabel;
    public VBox vBox;
    public StackPane stackPane;

    @FXML
    public void OnSignUpButton(javafx.event.ActionEvent event) {
        RegLoginField.getStyleClass().remove("tf_box_fail");
        RegPasswordField.getStyleClass().remove("tf_box_fail");
        NameField.getStyleClass().remove("tf_box_fail");
        EmailField.getStyleClass().remove("tf_box_fail");
        PhoneField.getStyleClass().remove("tf_box_fail");
        ErrorLabel.setVisible(false);

        if (RegLoginField.getText().isBlank() || RegPasswordField.getText().isBlank() || NameField.getText().isBlank() || EmailField.getText().isBlank() || PhoneField.getText().isBlank()) {
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
            if (EmailField.getText().isBlank()) {
                EmailField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
            if (PhoneField.getText().isBlank()) {
                PhoneField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
        }
        else {
            String[] arrStr = {"0", RegLoginField.getText(), RegPasswordField.getText(), NameField.getText(), PhoneField.getText(), EmailField.getText()};
            String tempString = (String) Singleton.getInstance().getDataController().Registration(arrStr);
            tempString = tempString.replaceAll("\r", "");
            Label MessageLabel = new Label();
            if(tempString.equals("0/"))
            {
                MessageLabel.setText("Ошибка регистрации");
            }
            else
            {
                MessageLabel.setText("Заявка на регистрацию успешно отправлена");
            }
            JFXDialog messageBox = new JFXDialog(stackPane, MessageLabel, JFXDialog.DialogTransition.CENTER);
            messageBox.show();
        }
    }
}
