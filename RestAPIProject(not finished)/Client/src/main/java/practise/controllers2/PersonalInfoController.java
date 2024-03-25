package practise.controllers2;

import Subs.PersonalInfoClass;
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
    public TextField phoneField;
    public TextField emailField;
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

                JSONObject arrStr = new JSONObject();
                arrStr.put("operationID", "UpdatePersonalInfo");
                arrStr.put("login", Singleton.getInstance().getLocalLogin());
                arrStr.put("contacts", phoneField.getText());
                arrStr.put("email", emailField.getText());
                arrStr.put("description", descriptionArea.getText());
                JSONObject responseJSON = Singleton.getInstance().getDataController().UpdatePersonalInfo(arrStr, sendImage);
                if (responseJSON.getString("response").equals("null")) {
                    messageBox.setText("Ошибка сохранения");
                } else {
                    messageBox.setText("Успешно сохранено");
                }
            }
            catch (JSONException e) {
                throw new RuntimeException(e);
            }
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
            onChange = false;
            phoneField.setEditable(false);
            emailField.setEditable(false);
            descriptionArea.setEditable(false);
            SaveButton.setDisable(true);
            ChangeButton.setDisable(false);
            try {
                OnReload();
            }
            catch (IOException | JSONException e) {
                throw new RuntimeException(e);
            }
        });

        ChangeButton.setText("Изменить информацию");
        ChangeButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        ChangeButton.setOnMouseClicked(mouseEvent -> {
            onChange = true;
            phoneField.setEditable(true);
            emailField.setEditable(true);
            descriptionArea.setEditable(true);
            SaveButton.setDisable(false);
            ChangeButton.setDisable(true);
        });

        ArrayList<JFXButton> buttons = new ArrayList<>();
        buttons.add(ChangeButton);
        buttons.add(SaveButton);

        try {
            String nameSername = OnReload();
            if(Singleton.getInstance().getFinal_NameSername().equals(nameSername)) {
                DashboardController temp = new DashboardController();
                temp.SetVBoxButtons(buttons);
            }
            System.out.println(Singleton.getInstance().getFinal_NameSername() + " " + nameSername);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    public String OnReload() throws IOException, JSONException {
        JSONObject arrStr = new JSONObject();
        arrStr.put("operationID", "GetPersonalInfo");
        arrStr.put("login", Singleton.getInstance().getLocalLogin());
        //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
        JSONObject tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);

        subroleLabel.setText(tempString.getString("subrole"));
        phoneField.setText(tempString.getString("contacts"));
        emailField.setText(tempString.getString("email"));
        descriptionArea.setText(tempString.getString("description"));
        workperiodLabel.setText("Работает с " + tempString.getString("regDate"));
        byte[] imageBytes = Base64.getDecoder().decode(tempString.getString("image"));
        Image image2 = new Image(new ByteArrayInputStream(imageBytes));
        avatarImage.setImage(image2);
        return tempString.getString("nameSername");
    }
}
