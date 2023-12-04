package practise.controllers2;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import practise.items.PersonalItems;
import practise.singleton.Singleton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class ClientInfoController implements Initializable {
    public StackPane stackPane;
    public JFXComboBox<String> clientTypeComboBox;
    public TextField clientNameField;
    public JFXButton responsableButton;
    public TextArea descriptionArea;
    public TextField phoneField;
    public TextField emailField;
    public TextField adressField;
    public JFXComboBox<String> responsableComboBox;
    public VBox taskVBox;
    public HBox primaryTaskHBox;
    public JFXButton addTaskButton;
    public ImageView addTaskImage;
    public VBox businessVBox;
    public HBox primaryBusinessHBox;
    public JFXButton addBusinessButton;
    public ImageView addBusinessImage;
    public VBox processVBox;
    public HBox primaryProcessHBox;
    public JFXButton addProcessButton;
    public ImageView addProcessImage;
    public VBox journalVBox1;
    public TextField journalTextField1;
    public FontAwesomeIcon journalButton1;
    public HBox sampleHBox1;
    public JFXComboBox<String> workTypeComboBox;
    public MFXDatePicker datePicker;
    boolean onChange = false;
    String old_name;
    String old_email;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> comboList = FXCollections.observableArrayList();
        String[] arrStr = {"GetPersonalObeyList"};
        String tempString = (String) Singleton.getInstance().getDataController().GetPersonalObeyList(arrStr);
        tempString = tempString.replaceAll("\r", "");
        String[] resultSet = tempString.split("<<");
        for(String i : resultSet) {
            String[] resultSubSet = i.split(">>");
            try {
                comboList.add(resultSubSet[0]);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        responsableComboBox.setItems(comboList);
        //================================DashBoard buttons================================
        JFXButton SaveButton = new JFXButton();
        JFXButton ChangeButton = new JFXButton();
        SaveButton.setDisable(true);

        SaveButton.setText("Сохранить изменения");
        SaveButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        SaveButton.setOnMouseClicked(mouseEvent -> {
            try {
            String[] arrStrUpdate = {"UpdateClientInfo", clientNameField.getText(), phoneField.getText(),
                    emailField.getText(), adressField.getText(), descriptionArea.getText(),
                    responsableComboBox.getSelectionModel().getSelectedItem(),
                    clientTypeComboBox.getSelectionModel().getSelectedItem(),
                    workTypeComboBox.getSelectionModel().getSelectedItem(),
                    datePicker.getText(), old_name, old_email};
            String tempStringUpdate = (String) Singleton.getInstance().getDataController().UpdateClientInfo(arrStrUpdate);
            tempStringUpdate = tempStringUpdate.replaceAll("\r", "");
            Label messageBox = new Label();
            if(tempStringUpdate.equals("null")) {
                messageBox.setText("Ошибка сохранения");
            }
            else {
                messageBox.setText("Успешно сохранено");
            }
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
            onChange = false;
            clientNameField.setEditable(false);
            adressField.setEditable(false);
            responsableComboBox.setDisable(true);
            clientTypeComboBox.setDisable(true);
            workTypeComboBox.setDisable(true);
            datePicker.setEditable(false);
            phoneField.setEditable(false);
            emailField.setEditable(false);
            descriptionArea.setEditable(false);
            SaveButton.setDisable(true);
            ChangeButton.setDisable(false);


            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
            finally {
                try {
                    OnReload();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ChangeButton.setText("Изменить информацию");
        ChangeButton.setStyle("-fx-pref-width: 458; -fx-pref-height: 38; -fx-text-fill: white; -fx-font-size: 16px");
        ChangeButton.setOnMouseClicked(mouseEvent -> {
            onChange = true;
            clientNameField.setEditable(true);
            adressField.setEditable(true);
            responsableComboBox.setDisable(false);
            clientTypeComboBox.setDisable(false);
            workTypeComboBox.setDisable(false);
            datePicker.setEditable(true);
            phoneField.setEditable(true);
            emailField.setEditable(true);
            descriptionArea.setEditable(true);
            SaveButton.setDisable(false);
            ChangeButton.setDisable(true);
        });


        ArrayList<JFXButton> buttons = new ArrayList<>();
        buttons.add(ChangeButton);
        buttons.add(SaveButton);
        DashboardController temp = new DashboardController();
        temp.SetVBoxButtons(buttons);
        //===============================/DashBoard buttons================================
        datePicker.setLocale(Locale.ENGLISH);
        datePicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", datePicker.getLocale()));
        ObservableList<String> list = FXCollections.observableArrayList("Клиент", "Подрядчик", "Партнер", "Поставщик");
        clientTypeComboBox.setItems(list);
        list = FXCollections.observableArrayList("Бытовые услуги", "Дом и дача", "Культура и спорт", "Мебель", "Медицина, здоровье и красота",
                 "Образование и отдых", "Органы власти");
        workTypeComboBox.setItems(list);
        try {
            OnReload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void OnReload() throws IOException {
        journalVBox1.getChildren().clear();
        String[] arrStr = {"GetClientInfo", String.valueOf(Singleton.getInstance().getClientsID())};
        //PersonalInfoClass tempString = Singleton.getInstance().getDataController().GetPersonalInfo(arrStr);
        String tempString = (String) Singleton.getInstance().getDataController().GetClientInfo(arrStr);
        tempString = tempString.replaceAll("\r", "");
        System.out.println(tempString);
        String[] resultSet = tempString.split(">>");
        int data_type = 0;
        for(String i: resultSet) {
            String subResSet[] = i.split("<<");
            switch (data_type) {
                case 0: {
                    clientNameField.setText(subResSet[0]);
                    old_name = subResSet[0];
                    responsableComboBox.getSelectionModel().select(subResSet[1]);
                    phoneField.setText(subResSet[2]);
                    emailField.setText(subResSet[3]);
                    old_email = subResSet[3];
                    adressField.setText(subResSet[4]);
                    descriptionArea.setText(subResSet[5]);
                    clientTypeComboBox.getSelectionModel().select(subResSet[6]);
                    workTypeComboBox.getSelectionModel().select(subResSet[7]);
                    datePicker.setText(subResSet[8]);
                    break;
                }
                case 1: {
                    if(!i.equals("")) {
                        for (String task : subResSet) {
                            HBox taskBox = primaryTaskHBox;
                            MFXButton button = new MFXButton(task);
                            button.setStyle("-fx-text-fill: #151928; -fx-background-color: #2196f3");
                            button.setAlignment(Pos.CENTER_LEFT);
                            button.setOnMouseEntered(event -> {
                                button.setText("Перейти к задаче");
                                button.setStyle("-fx-text-fill: #2196f3; -fx-background-color: #151928");
                            });
                            button.setOnMouseExited(event -> {
                                button.setText(task);
                                button.setStyle("-fx-text-fill: #151928; -fx-background-color: #2196f3");
                            });
                            button.setOnMouseClicked(event -> {

                            });
                            taskVBox.getChildren().add(button);
                        }
                    }
                    break;
                }
                case 2: {
                    if(!i.equals("")) {
                        for (String business : subResSet) {
                            HBox businessBox = primaryBusinessHBox;
                            MFXButton button = new MFXButton(business);
                            button.setStyle("-fx-text-fill: #151928; -fx-background-color: #2196f3");
                            button.setAlignment(Pos.CENTER_LEFT);
                            button.setOnMouseEntered(event -> {
                                button.setText("Перейти к делу");
                                button.setStyle("-fx-text-fill: #2196f3; -fx-background-color: #151928");
                            });
                            button.setOnMouseExited(event -> {
                                button.setText(business);
                                button.setStyle("-fx-text-fill: #151928; -fx-background-color: #2196f3");
                            });
                            button.setOnMouseClicked(event -> {

                            });
                            businessVBox.getChildren().add(button);
                        }
                    }
                    break;
                }
                case 3: {
                    if(!i.equals("")) {
                        for (String process : subResSet) {
                            HBox processBox = primaryProcessHBox;
                            MFXButton button = new MFXButton(process);
                            button.setStyle("-fx-text-fill: #151928; -fx-background-color: #2196f3");
                            button.setAlignment(Pos.CENTER_LEFT);
                            button.setOnMouseEntered(event -> {
                                button.setText("Перейти к процессу");
                                button.setStyle("-fx-text-fill: #2196f3; -fx-background-color: #151928");
                            });
                            button.setOnMouseExited(event -> {
                                button.setText(process);
                                button.setStyle("-fx-text-fill: #151928; -fx-background-color: #2196f3");
                            });
                            button.setOnMouseClicked(event -> {

                            });
                            processVBox.getChildren().add(button);
                        }
                    }
                    break;
                }
                case 4: {
                    if(!i.equals("")) {
                        for (String comment : subResSet) {
                            String[] commentData = comment.split("\\^\\^");
                            System.out.println(comment);
                            HBox hBox = Singleton.getInstance().SetCommentBox(sampleHBox1, commentData[0], commentData[1], commentData[2], commentData[3]);
                            journalVBox1.getChildren().add(hBox);
                        }
                    }
                    break;
                }
            }
            data_type++;
        }
    }
}
