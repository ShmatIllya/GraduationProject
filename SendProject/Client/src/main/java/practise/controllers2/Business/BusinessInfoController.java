package practise.controllers2.Business;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
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
        this.businessId = businessId;
        this.memberName = memberName;
        textArea.getStylesheets().add("/css/styles.css");
        textArea.setEditable(true);
        textArea.getStyleClass().add("tf_box");
        descriptionHBox.setPrefHeight(textArea.getPrefHeight());
        descriptionHBox.getChildren().add(textArea);

        OnReload();
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

    public void OnReload() {
        String[] arrStr = {"GetBusinessInfo", businessId};
        String tempString = (String) Singleton.getInstance().getDataController().GetBusinessInfo(arrStr);
        tempString = tempString.replaceAll("\r", "");
        String[] resultSet = tempString.split(">>");
        if (!resultSet[6].equals("Активно")) {
            completeButton.setVisible(false);
            redactButton.setVisible(false);
        }
        nameLabel.setText(resultSet[0] + " (" + resultSet[6] + ")");
        dateField.setText(resultSet[1]);
        timeField.setText(resultSet[2]);
        textArea.setText(resultSet[3]);
        placeField.setText(resultSet[4]);
        creatorField.setText(resultSet[5]);
        memberField.setText(memberName);
        if(resultSet[6].equals("Завершено")) {

        }
    }

    public void OnCompleteButton(ActionEvent event) {
        String[] arrStr = {"CompleteBusiness", businessId};
        String tempString = (String) Singleton.getInstance().getDataController().CompleteBusiness(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.equals("null")) {
            MessageLabel.setText("Ошибка завершения");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно завершено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel, (Stage) completeButton.getScene().getWindow());
        }
    }

    public void OnRedactButton(ActionEvent event) {
        String[] arrStr = {"UpdateBusiness", businessId, textArea.getText(), placeField.getText()};
        String tempString = (String) Singleton.getInstance().getDataController().UpdateBusiness(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.equals("null")) {
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

    public void OnDeleteButton(ActionEvent event) {
        String[] arrStr = {"DeleteBusiness", businessId};
        String tempString = (String) Singleton.getInstance().getDataController().DeleteBusiness(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.equals("null")) {
            MessageLabel.setText("Ошибка удаления");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
        else {
            MessageLabel.setText("Успешно удалено");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel, (Stage) completeButton.getScene().getWindow());
        }
    }
}
