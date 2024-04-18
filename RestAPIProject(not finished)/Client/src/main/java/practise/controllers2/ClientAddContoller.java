package practise.controllers2;

import DTO.ClientDTO;
import DTO.PersonalDTO;
import com.dlsc.gemsfx.EmailField;
import com.dlsc.gemsfx.PhoneNumberField;
import com.jfoenix.controls.JFXChipView;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.items.ClientsItems;
import practise.items.PersonalItems;
import practise.singleton.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientAddContoller implements Initializable  {
    public Button submitButton;
    public Label ErrorLabel;
    public TextField descriptionField;
    public HBox PlaceChipViewHere;
    public TextField adressField;
    public PhoneNumberField phoneField;
    public EmailField emailField;
    public TextField nameField;
    public StackPane stackPane;
    JFXChipView<String> chipView = new JFXChipView<String>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> personal = new ArrayList<>();
        chipView.getStylesheets().add("/css/jfxChipsView.css");
        JSONObject arrStr = new JSONObject();
        try {
            arrStr.put("operationID", "GetPersonalObeyList");
            ArrayList<PersonalDTO> tempString = Singleton.getInstance().getDataController().GetPersonalObeyList(arrStr);
            if(tempString == null) {
                Label messageBox = new Label("Ошибка получения данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                return;
            }
            for (PersonalDTO personalDTO: tempString) {
                personal.add(personalDTO.getNameSername());
            }
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //chipView.getChips().addAll("WEF", "WWW", "JD");
        chipView.getSuggestions().addAll(personal);
        chipView.getStylesheets().add("D:\\FCP\\SEM7\\CURS\\Project\\Client\\Client\\src\\main\\resources\\css\\jfxChipsView.css");
        chipView.setStyle("-fx-text-fill: white; -fx-font-size: 12px");
        chipView.setPrefWidth(399);
        chipView.setMinWidth(chipView.getPrefWidth());
        StackPane pane = new StackPane();
        pane.getChildren().add(chipView);
        StackPane.setMargin(chipView, new Insets(100));
        PlaceChipViewHere.getChildren().add(chipView);

        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(nameField.textProperty(),
                        emailField.emailAddressProperty(),
                        chipView.getChips());
            }

            @Override
            protected boolean computeValue() {
                return (nameField.getText().isEmpty()
                        || emailField.getEmailAddress().isEmpty()
                        || !emailField.isValid()
                        || chipView.getChips().isEmpty());
            }
        };
        emailField.setEmailAddress("");
        submitButton.disableProperty().bind(submitButtonBinding);
    }

    public void OnSubmitButton() throws IOException, JSONException {
        ClientDTO arrStr = new ClientDTO();
        try {
            arrStr.setName(nameField.getText());
            arrStr.setEmail(emailField.getEmailAddress());
            if(phoneField.getPhoneNumber() == null || phoneField.getPhoneNumber().isEmpty()) {
                arrStr.setPhone("");
            }
            else {
                arrStr.setPhone(phoneField.getPhoneNumber());
            }
            if (adressField.getText() == null || adressField.getText().isEmpty()) {
                arrStr.setAdress("");
            }
            else {
                arrStr.setAdress(adressField.getText());
            }
            if (descriptionField.getText() == null || descriptionField.getText().isEmpty()) {
                arrStr.setDescription("");
            }
            else {
                arrStr.setDescription(descriptionField.getText());
            }
            arrStr.setResponsible_name(chipView.getChips().get(0));
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().AddClient(arrStr);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка добавления");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно добавлено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            //OnClose();
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
