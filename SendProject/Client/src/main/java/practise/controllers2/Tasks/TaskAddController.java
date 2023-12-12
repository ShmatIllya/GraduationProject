package practise.controllers2.Tasks;

import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.animation.FadeTransition;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import practise.singleton.Singleton;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TaskAddController implements Initializable {
    public StackPane stackPane;
    public TextField nameField;
    public HBox responsableHBox;
    public TextField descriptionField;
    public MFXDatePicker datePicker;
    public HBox checkerHBox;
    public JFXComboBox processComboBox;
    public JFXComboBox projectComboBox;
    public Button submitButton;
    public HBox processProjectHBox;
    JFXChipView<String> responsableChips;
    JFXChipView<String> checkerChips;
    String Client = "Не выбран";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            datePicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", datePicker.getLocale()));
//===============================Set ups======================================
            List<String> personal = new ArrayList<>();
            String[] arrStr = {"GetPersonalObeyList"};
            String tempString = (String) Singleton.getInstance().getDataController().GetPersonalObeyList(arrStr);
            tempString = tempString.replaceAll("\r", "");
            String[] resultSet = tempString.split("<<");
            for (String i : resultSet) {
                String[] resultSubSet = i.split(">>");
                try {
                    personal.add(resultSubSet[0]);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            //chipView.getChips().addAll("WEF", "WWW", "JD");
            responsableChips = ChipViewInit(personal);
            responsableHBox.getChildren().add(responsableChips);

            personal = new ArrayList<>();
            String[] arrStr2 = {"GetPersonalControlList"};
            tempString = (String) Singleton.getInstance().getDataController().GetPersonalControlList(arrStr2);
            tempString = tempString.replaceAll("\r", "");
            resultSet = tempString.split("<<");
            for (String i : resultSet) {
                String[] resultSubSet = i.split(">>");
                try {
                    personal.add(resultSubSet[0]);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            //chipView.getChips().addAll("WEF", "WWW", "JD");
            checkerChips = ChipViewInit(personal);
            checkerHBox.getChildren().add(checkerChips);

            ObservableList<String> values = FXCollections.observableArrayList();
            String[] arrStr3 = {"GetProjectList"};
            tempString = (String) Singleton.getInstance().getDataController().GetProjectList(arrStr3);
            tempString = tempString.replaceAll("\r", "");
            resultSet = tempString.split("<<");
            for (String i : resultSet) {
                String[] resultSubSet = i.split(">>");
                try {
                    values.add(resultSubSet[0]);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            values.add("Не выбран");
            projectComboBox.setItems(values);

            values = FXCollections.observableArrayList();
            String[] arrStr4 = {"GetProcessList"};
            tempString = (String) Singleton.getInstance().getDataController().GetProcessList(arrStr4);
            tempString = tempString.replaceAll("\r", "");
            resultSet = tempString.split("<<");
            for (String i : resultSet) {
                String[] resultSubSet = i.split(">>");
                try {
                    values.add("Процесс " + resultSubSet[0]);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            values.add("Не выбран");
            processComboBox.setItems(values);

            projectComboBox.getSelectionModel().select("Не выбран");
            processComboBox.getSelectionModel().select("Не выбран");
//==========================/Set ups================================================
            BooleanBinding submitButtonBinding = new BooleanBinding() {
                {
                    super.bind(nameField.textProperty(),
                            responsableChips.getChips(),
                            datePicker.textProperty(),
                            checkerChips.getChips());
                }

                @Override
                protected boolean computeValue() {
                    return (nameField.getText().isEmpty()
                            || responsableChips.getChips().isEmpty()
                            || datePicker.getText().isEmpty()
                            || checkerChips.getChips().isEmpty());
                }
            };
            submitButton.disableProperty().bind(submitButtonBinding);

            processComboBox.setOnAction(event -> {
                if (processComboBox.getSelectionModel().getSelectedItem().equals("Не выбран")) {
                    projectComboBox.setDisable(false);
                } else {
                    projectComboBox.setDisable(true);
                }
            });
            projectComboBox.setOnAction(event -> {
                if (projectComboBox.getSelectionModel().getSelectedItem().equals("Не выбран")) {
                    processComboBox.setDisable(false);
                } else {
                    processComboBox.setDisable(true);
                }
            });
    }

    public void InitClientTask(String Client) {
        processProjectHBox.setVisible(false);
        this.Client = Client;
    }
    public void OnClose(ActionEvent event) {
        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(stackPane, 1, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Stage st = (Stage) submitButton.getScene().getWindow();
            st.close();
        });
    }

    public void OnSubmitButton(ActionEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String[] splitProcess = processComboBox.getSelectionModel().getSelectedItem().toString().split(" ");
        String[] arrStr = {"AddTask", nameField.getText(), responsableChips.getChips().get(0), descriptionField.getText(),
                checkerChips.getChips().get(0), datePicker.getText(),
                projectComboBox.getSelectionModel().getSelectedItem().toString(),
                splitProcess[1], Client, formatter.format(LocalDateTime.now())
        };
        String tempString = (String) Singleton.getInstance().getDataController().AddTask(arrStr);
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
}
