package practise.controllers2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    public TextField passwordField;
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
            JSONObject arrStr = new JSONObject();
            arrStr.put("operationID", "1");
            arrStr.put("login", loginField.getText());
            arrStr.put("password", passwordField.getText());

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
