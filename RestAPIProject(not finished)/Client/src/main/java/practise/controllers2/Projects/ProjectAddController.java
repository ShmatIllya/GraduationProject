package practise.controllers2.Projects;

import DTO.PersonalDTO;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.items.TasksItems;
import practise.singleton.Singleton;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ProjectAddController implements Initializable {
    public StackPane stackPane;
    public TextField nameField;
    public HBox responsableHBox;
    public TextField descriptionField;
    public MFXDatePicker datePicker;
    public HBox checkerHBox;
    public Button submitButton;
    public TextField teamNameField;
    public JFXComboBox teamsComboBox;
    public VBox commandAddVBox;

    JFXChipView<String> responsableChips;
    JFXChipView<String> checkerChips;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", datePicker.getLocale()));
//===============================Set ups======================================
        try {
            List<String> personal = new ArrayList<>();
            JSONObject arrStr = new JSONObject();
            arrStr.put("operationID", "GetPersonalObeyList");
            ArrayList<PersonalDTO> tempString = Singleton.getInstance().getDataController().GetPersonalObeyList(arrStr);
            if(tempString == null) {
                Label messageBox = new Label("Ошибка получения данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                return;
            }
            for (PersonalDTO personalDTO: tempString) {
                try {
                    personal.add(personalDTO.getNameSername());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            //chipView.getChips().addAll("WEF", "WWW", "JD");
            responsableChips = ChipViewInit(personal);
            responsableHBox.getChildren().add(responsableChips);

            personal = new ArrayList<>();
            JSONObject arrStr2 = new JSONObject();
            arrStr2.put("operationID", "GetPersonalControlList");
            tempString = Singleton.getInstance().getDataController().GetPersonalControlList(arrStr2);
            if(tempString == null) {
                Label messageBox = new Label("Ошибка получения данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
                return;
            }
            for (PersonalDTO personalDTO: tempString) {
                try {
                    personal.add(personalDTO.getNameSername());
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            //chipView.getChips().addAll("WEF", "WWW", "JD");
            checkerChips = ChipViewInit(personal);
            checkerHBox.getChildren().add(checkerChips);

            ObservableList<String> values = FXCollections.observableArrayList();
            String[] arrStr3 = {"GetTeamsList"};
            String teamsResult = (String) Singleton.getInstance().getDataController().GetTeamsList(arrStr3);
            teamsResult = teamsResult.replaceAll("\r", "");
            String[] teamsResSet = teamsResult.split(">>");
            for (String i : teamsResSet) {
                values.add(i);
            }
            values.add("Не выбрано");
            teamsComboBox.setItems(values);
            teamsComboBox.getSelectionModel().select("Не выбрано");

            teamNameField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if (t1.isEmpty()) {
                        teamsComboBox.setDisable(false);
                    }
                }
            });
            teamsComboBox.setOnAction(event -> {
                if (teamsComboBox.getSelectionModel().getSelectedItem().equals("Не выбрано")) {
                    commandAddVBox.setDisable(false);
                    teamsComboBox.setDisable(true);
                } else {
                    commandAddVBox.setDisable(true);
                }
            });
        }
        catch (JSONException e) {
            throw new RuntimeException(e);
        }
//==========================/Set ups================================================
        BooleanBinding submitButtonBinding = new BooleanBinding() {
            {
                super.bind(nameField.textProperty(),
                        responsableChips.getChips(),
                        datePicker.textProperty(),
                        checkerChips.getChips(),
                        teamsComboBox.getSelectionModel().selectedItemProperty(),
                        teamNameField.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (nameField.getText().isEmpty()
                        || ((responsableChips.getChips().isEmpty() || teamNameField.getText().isEmpty())
                        && (teamsComboBox.getSelectionModel().getSelectedItem().equals("Не выбрано")))
                        || datePicker.getText().isEmpty()
                        || checkerChips.getChips().isEmpty());
            }
        };
        submitButton.disableProperty().bind(submitButtonBinding);
    }

    public JFXChipView<String> ChipViewInit(List<String> values) {
        JFXChipView<String> chipView = new JFXChipView<>();
        chipView.getSuggestions().addAll(values);
        chipView.getStylesheets().add("/css/jfxChipsView.css");
        chipView.setStyle("-fx-text-fill: white; -fx-font-size: 12px");
        chipView.setPrefWidth(399);
        chipView.setMinWidth(chipView.getPrefWidth());
        StackPane pane = new StackPane();
        pane.getChildren().add(chipView);
        StackPane.setMargin(chipView, new Insets(100));
        return chipView;
    }

    public void OnSubmitButton(ActionEvent event) {
        if(responsableChips.getChips().size() < 2 && teamsComboBox.isDisable()) {
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, new Label("Команда проека должна состоять" +
                    " из более чем 1 сотрудника"));
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String responsableList = "";
        String[] arrStr;
        if(teamsComboBox.getSelectionModel().getSelectedItem().equals("Не выбрано")) {
            for (String i : responsableChips.getChips()) {
                responsableList += i + ">>";
            }
            System.out.println("llllllllllllllll");
            arrStr = new String[]{"AddProject", nameField.getText(), responsableList, descriptionField.getText(),
                    checkerChips.getChips().get(0), datePicker.getText(), teamNameField.getText()
            };
        }
        else {
            arrStr = new String[]{"AddProject", nameField.getText(), "Команда существует", descriptionField.getText(),
                    checkerChips.getChips().get(0), datePicker.getText(),
                    teamsComboBox.getSelectionModel().getSelectedItem().toString()
            };
        }
        String tempString = (String) Singleton.getInstance().getDataController().AddProject(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        if(tempString.equals("null")) {
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
