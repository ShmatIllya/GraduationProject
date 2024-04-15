package practise.controllers2;

import DTO.PersonalDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import practise.HelloApplication;
import practise.singleton.Singleton;

import java.io.IOException;

public class SignInController {
    public StackPane stackPane;
    public TextField loginField;
    public PasswordField passwordField;
    public Label ErrorLabel;

    @FXML
    public void OnSignInButton(javafx.event.ActionEvent event) throws IOException, JSONException {
        loginField.getStyleClass().remove("tf_box_fail");
        passwordField.getStyleClass().remove("tf_box_fail");
        ErrorLabel.setVisible(false);

        if (loginField.getText().isBlank() || passwordField.getText().isBlank()) {
            if (passwordField.getText().isBlank()) {
                passwordField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
            if (loginField.getText().isBlank()) {
                loginField.getStyleClass().add("tf_box_fail");
                ErrorLabel.setVisible(true);
            }
        }
        else {
            PersonalDTO arrStr = new PersonalDTO();
            try {
                arrStr.setLogin(loginField.getText());
                arrStr.setPassword(passwordField.getText());
            }
            catch (Exception e) {
                Label messageBox = new Label("Ошибка ввода данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                return;
            }
            JSONObject result = Singleton.getInstance().getDataController().Login(arrStr);
            Label MessageLabel = new Label();
            if(result.getString("response").equals("null"))
            {
                MessageLabel.setText("Ошибка входа");
            }
            else
            {
                Singleton.getInstance().setFinal_Role(result.getString("role"));
                Singleton.getInstance().setFinal_NameSername(result.getString("nameSername"));
                MessageLabel.setText("Вход успешно выполнен");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Dashboard.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
                OnClose();
            }
           Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
    }
    public void OnClose() {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }
}
