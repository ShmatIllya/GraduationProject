package practise.controllers2.Messages;

import DTO.ChatDTO;
import com.dlsc.gemsfx.PhotoView;
import com.jfoenix.controls.JFXChipView;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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
import practise.singleton.Singleton;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatAddController implements Initializable {
    public StackPane stackPane;
    public PhotoView photoView;
    public TextField nameField;
    public TextField descriptionField;
    public HBox membersHBox;
    public TextField membersField;
    public Button submitButton;
    JFXChipView<String> chatMembersChips = new JFXChipView<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> resultSubSet = new ArrayList<>();
        try {

            JSONObject arrStr = new JSONObject();
            arrStr.put("operationID", "GetPersonalList");
            JSONObject tempString = Singleton.getInstance().getDataController().GetPersonalList(arrStr);
            JSONArray resultSet = tempString.getJSONArray("personalList");
            for (int j = 0; j < resultSet.length(); j++) {
                resultSubSet.add(resultSet.getJSONObject(j).getString("nameSername"));
            }
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
        chatMembersChips.getSuggestions().addAll(resultSubSet);
        chatMembersChips.getStylesheets().add("/css/jfxChipsView.css");
        chatMembersChips.setStyle("-fx-text-fill: white; -fx-font-size: 12px");
        chatMembersChips.setPrefWidth(399);
        chatMembersChips.setMinWidth(chatMembersChips.getPrefWidth());
        StackPane pane = new StackPane();
        pane.getChildren().add(chatMembersChips);
        StackPane.setMargin(chatMembersChips, new Insets(100));
        membersHBox.getChildren().remove(1);
        membersHBox.getChildren().add(chatMembersChips);

        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(nameField.textProperty(),
                        chatMembersChips.getChips(),
                        nameField.textProperty(),
                        photoView.photoProperty());
            }

            @Override
            protected boolean computeValue() {
                return (nameField.getText().isEmpty()
                        || chatMembersChips.getChips().isEmpty()
                        || nameField.getText().isEmpty()
                        || photoView.photoProperty().isNull().getValue());
            }
        };
        submitButton.disableProperty().bind(submitButtonBinding);
    }

    public void OnSubmitButton(ActionEvent event) throws JSONException {
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        ChatDTO arrStr = new ChatDTO();
        try {
            arrStr.setName(nameField.getText());
            arrStr.setDescription(descriptionField.getText());
            for (String i : chatMembersChips.getChips()) {
                arrStr.getMembersList().add(i);
            }
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            throw new RuntimeException(e);
        }
        //========================================================
        BufferedImage sendImage = null;
        sendImage = SwingFXUtils.fromFXImage(photoView.getPhoto(), null);
        //========================================================
        JSONObject tempString = Singleton.getInstance().getDataController().AddChat(arrStr,
                sendImage);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка добавления");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно добавлено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
            OnClose(event);
        }
    }

    public void OnClose(ActionEvent event) {
        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(stackPane, 1, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Stage st = (Stage) submitButton.getScene().getWindow();
            st.close();
        });
    }

}
