package practise.controllers2.Business;

import DTO.BusinessDTO;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import practise.items.ClientsItems;
import practise.items.PaymentInfoItems;
import practise.singleton.Singleton;
import practise.subs.CustomTextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class BusinessInfoController {
    public StackPane stackPane;
    public Label nameLabel;
    public TextField dateField;
    public TextField timeField;
    public HBox descriptionHBox;
    public TextField placeField;
    public TextField creatorField;
    public HBox PlaceChipViewHere;
    public TextField memberField;
    public JFXButton completeButton;
    public JFXButton redactButton;
    public JFXButton deleteButton;
    String businessId;
    String memberName;
    CustomTextArea textArea = new CustomTextArea();

    public void InitController(String businessId, String memberName) {
        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(textArea.textProperty(),
                        placeField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (textArea.getText().isEmpty()
                        || placeField.getText().isEmpty());
            }
        };
        redactButton.disableProperty().bind(submitButtonBinding);

        this.businessId = businessId;
        this.memberName = memberName;
        textArea.getStylesheets().add("/css/styles.css");
        textArea.setEditable(true);
        textArea.getStyleClass().add("tf_box");
        descriptionHBox.setPrefHeight(textArea.getPrefHeight());
        descriptionHBox.getChildren().add(textArea);

        try {
            OnReload();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if(!Singleton.getInstance().getFinal_Role().equals("control")) {
            redactButton.setVisible(false);
            deleteButton.setVisible(false);
        }
        if(!Singleton.getInstance().getFinal_NameSername().equals(creatorField.getText())
                && !Singleton.getInstance().getFinal_NameSername().equals(memberField.getText())
                && !Singleton.getInstance().getFinal_Role().equals("control")) {
            completeButton.setVisible(false);
            redactButton.setVisible(false);
            deleteButton.setVisible(false);
        }
    }

    public void OnReload() throws JSONException {
        BusinessDTO business = new BusinessDTO();
        try {
            business.setBusinessId(Integer.parseInt(businessId));
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().GetBusinessInfo(business);
        try {
            if(tempString.getString("response").equals("okay")) {
                if (!tempString.getString("status").equals("Активно")) {
                    completeButton.setVisible(false);
                    redactButton.setVisible(false);
                }
                nameLabel.setText(tempString.getString("name") + " (" + tempString.getString("status") + ")");
                dateField.setText(tempString.getString("date"));
                timeField.setText(tempString.getString("time"));
                textArea.setText(tempString.getString("description"));
                placeField.setText(tempString.getString("place"));
                creatorField.setText(tempString.getString("responsibleName"));
                memberField.setText(memberName);
                if (tempString.getString("status").equals("Завершено")) {

                }
            }
            else {
                Label messageBox = new Label("Ошибка получения данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                return;
            }
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
    }

    public void OnCompleteButton(ActionEvent event) throws JSONException {
        BusinessDTO business = new BusinessDTO();
        business.setBusinessId(Integer.parseInt(businessId));
        JSONObject tempString = Singleton.getInstance().getDataController().CompleteBusiness(business);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка завершения");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно завершено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel, (Stage) completeButton.getScene().getWindow());
        }
    }

    public void OnRedactButton(ActionEvent event) throws JSONException {
        BusinessDTO business = new BusinessDTO();
        try {
            business.setBusinessId(Integer.parseInt(businessId));
            business.setDescription(textArea.getText());
            business.setPlace(placeField.getText());
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().UpdateBusiness(business);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка обновления");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно обновлено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
    }

    public void OnClose(ActionEvent event) {
        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(stackPane, 1, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Stage st = (Stage) completeButton.getScene().getWindow();
            st.close();
        });
    }

    public void OnDeleteButton(ActionEvent event) throws JSONException {
        BusinessDTO business = new BusinessDTO();
        business.setBusinessId(Integer.parseInt(businessId));
        JSONObject tempString = Singleton.getInstance().getDataController().DeleteBusiness(business);
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.getString("response").equals("null")) {
            MessageLabel.setText("Ошибка удаления");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно удалено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel, (Stage) completeButton.getScene().getWindow());
        }
    }
}
