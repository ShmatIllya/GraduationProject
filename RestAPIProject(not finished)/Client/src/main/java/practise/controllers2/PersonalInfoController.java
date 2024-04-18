package practise.controllers2;

import DTO.PersonalDTO;
import Subs.PersonalInfoClass;
import com.dlsc.gemsfx.EmailField;
import com.dlsc.gemsfx.PhoneNumberField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import org.json.JSONException;
import org.json.JSONObject;
import practise.items.PersonalItems;
import practise.singleton.Singleton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class PersonalInfoController implements Initializable {
    public StackPane stackPane;
    public Label subroleLabel;
    public Label workperiodLabel;
    public Label phoneLabel;
    public Label emailLabel;
    public JFXComboBox reportBox;
    public BarChart reportChart;
    public ImageView avatarImage;
    public Pane changeAvatarPane;
    public ImageView changeAvatarImage;
    public Label changeAvatarLabel;
    public PhoneNumberField phoneField;
    public EmailField emailField;
    public TextArea descriptionArea;
    public Pane mouseEventPane;
    boolean onChange = false;
    File file;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //======================================================
        //==========================Events======================
        //======================================================
        mouseEventPane.setOnMouseEntered(event -> {
            if (onChange) {
                changeAvatarPane.setOpacity(0.5);
                changeAvatarPane.setVisible(true);
                changeAvatarImage.setVisible(true);
                changeAvatarLabel.setVisible(true);
            }
        });
        mouseEventPane.setOnMouseExited(event -> {
            if (onChange) {
                changeAvatarPane.setVisible(false);
                changeAvatarImage.setVisible(false);
                changeAvatarLabel.setVisible(false);
            }
        });
        mouseEventPane.setOnMouseClicked(event -> {
            if (onChange) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
                file = chooser.showOpenDialog(null);
                avatarImage.setImage(new Image(file.getPath()));
            }
        });
        //====================================================================
        //================================/Events=============================
        //====================================================================

        JFXButton SaveButton = new JFXButton();
        JFXButton ChangeButton = new JFXButton();
        SaveButton.setDisable(true);

        SaveButton.setText("Сохранить изменения");
        SaveButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        SaveButton.setOnMouseClicked(mouseEvent -> {
            //========================================================
            BufferedImage sendImage = null;
            sendImage = SwingFXUtils.fromFXImage(avatarImage.getImage(), null);
            //========================================================
            Label messageBox = new Label();
            try {
                if (phoneField.getPhoneNumber() != null && !emailField.getEmailAddress().isEmpty() && emailField.isValid()) {
                    PersonalDTO arrStr = new PersonalDTO();
                    try {
                        arrStr.setLogin(Singleton.getInstance().getLocalLogin());
                        arrStr.setContacts(phoneField.getPhoneNumber());
                        arrStr.setEmail(emailField.getEmailAddress());
                        arrStr.setDescription(descriptionArea.getText());
                    } catch (Exception e) {
                        messageBox = new Label("Ошибка ввода данных");
                        Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                        return;
                    }
                    JSONObject responseJSON = Singleton.getInstance().getDataController().UpdatePersonalInfo(arrStr, sendImage);
                    if (responseJSON.getString("response").equals("null")) {
                        messageBox.setText("Ошибка сохранения");
                    } else {
                        messageBox.setText("Успешно сохранено");
                    }
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                } else {
                    messageBox = new Label("Ошибка ввода данных");
                    Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
            onChange = false;
            phoneField.setDisable(true);
            emailField.setDisable(true);
            descriptionArea.setEditable(false);
            SaveButton.setDisable(true);
            ChangeButton.setDisable(false);
            try {
                OnReload();
            } catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        });

        ChangeButton.setText("Изменить информацию");
        ChangeButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        ChangeButton.setOnMouseClicked(mouseEvent -> {
            onChange = true;
            phoneField.setDisable(false);
            emailField.setDisable(false);
            descriptionArea.setEditable(true);
            SaveButton.setDisable(false);
            ChangeButton.setDisable(true);
        });

        ArrayList<JFXButton> buttons = new ArrayList<>();
        buttons.add(ChangeButton);
        buttons.add(SaveButton);

        try {
            String nameSername = OnReload();
            if (Singleton.getInstance().getFinal_NameSername().equals(nameSername)) {
                DashboardController temp = new DashboardController();
                temp.SetVBoxButtons(buttons);
            }
            System.out.println(Singleton.getInstance().getFinal_NameSername() + " " + nameSername);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    public String OnReload() throws IOException, JSONException {
        PersonalDTO arrStr = new PersonalDTO();
        try {
            arrStr.setLogin(Singleton.getInstance().getLocalLogin());
        } catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return null;
        }
        //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
        PersonalDTO tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
        if (tempString == null) {
            Label messageBox = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return null;
        } else {
            subroleLabel.setText(tempString.getSubrole());
            phoneField.setPhoneNumber(tempString.getContacts());
            emailField.setEmailAddress(tempString.getEmail());
            descriptionArea.setText(tempString.getDescription());
            workperiodLabel.setText("Работает с " + tempString.getRegDate());
            avatarImage.setImage(new Image(tempString.getAvatarImage()));
            return tempString.getNameSername();
        }
    }
}
