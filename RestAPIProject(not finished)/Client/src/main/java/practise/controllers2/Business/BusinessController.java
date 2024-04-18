package practise.controllers2.Business;

import DTO.BusinessDTO;
import DTO.PersonalDTO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import javafx.animation.FadeTransition;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import practise.singleton.Singleton;

import javax.swing.event.ChangeListener;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BusinessController {
    public JFXButton addButton;
    public StackPane stackPane;
    public javafx.scene.layout.AnchorPane AnchorPane;
    public HBox layoutHBox;
    String fromCall;
    String value;
    JFXChipView<String> promptChips = new JFXChipView<>();



    public void InitController(String fromCall, String value) throws JSONException {
        addButton.setDisable(true);
        AnchorPane.getChildren().add(promptChips);
        promptChips.setPrefHeight(38.4);
        promptChips.setLayoutX(68);
        promptChips.setLayoutY(22);
        promptChips.getStylesheets().add("/css/jfxChipsView.css");
        layoutHBox.getChildren().add(promptChips);
        promptChips.setPrefWidth(302.4);
        promptChips.setMinWidth(302.4);

        String[] businessType = {"Дело", "Личное", "Звонок", "Встреча", "Совещание", "Праздник", "Спорт"};
        String[] businessDate = {"Завтра", "Послезавтра", "Через неделю", "Через месяц"};
        String[] businessTime = {"в 09", "в 10", "в 11", "в 12", "в 13", "в 14", "в 15", "в 16", "в 17", "в 18"};
        String[] businessMinutes = {":00", ":05", ":10", ":15", ":20", ":25", ":30", ":35", ":40", ":45", ":50", ":55"};
        promptChips.getSuggestions().addAll(businessType);

        List<String> personal = new ArrayList<>();
        JSONObject arrStr = new JSONObject();
        arrStr.put("operationID", "GetPersonalList");
        ArrayList<PersonalDTO> tempString = Singleton.getInstance().getDataController().GetPersonalList(arrStr);
        if(tempString == null) {
            Label messageBox = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        for (PersonalDTO pers: tempString) {
            try {
                personal.add(pers.getNameSername());
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }

        promptChips.getChips().addListener(new WeakListChangeListener<>(change -> {
            switch (promptChips.getChips().size()) {
                case 0: {
                    promptChips.getSuggestions().clear();
                    promptChips.getSuggestions().addAll(businessType);
                    break;
                }
                case 1: {
                    promptChips.getSuggestions().clear();
                    promptChips.getSuggestions().addAll(businessDate);
                    break;
                }
                case 2: {
                    promptChips.getSuggestions().clear();
                    promptChips.getSuggestions().addAll(businessTime);
                    break;
                }
                case 3: {
                    promptChips.getSuggestions().clear();
                    promptChips.getSuggestions().addAll(businessMinutes);
                    break;
                }
                case 4: {
                    addButton.setDisable(false);
                    promptChips.getSuggestions().clear();
                    promptChips.getSuggestions().addAll(personal);
                    break;
                }
            }
        }));
        //==================================================================
        this.fromCall = fromCall;
        this.value = value;
    }
    public void OnAddButton() throws JSONException {
        LocalDate date = LocalDate.now();
        switch (promptChips.getChips().get(1)) {
            case "Завтра": {
                date = date.plusDays(1);
                break;
            }
            case "Послезавтра": {
                date = date.plusDays(2);
                break;
            }
            case "Через неделю": {
                date = date.plusDays(7);
                break;
            }
            case "Через месяц": {
                date = date.plusDays(30);
                break;
            }
        }
        String dateString = date + " " + promptChips.getChips().get(2).replaceAll("в ", "")
                + promptChips.getChips().get(3) + ":00";
        BusinessDTO business = new BusinessDTO();
        try {
            System.out.println(promptChips.getChips().get(0));
            System.out.println(promptChips.getChips().get(4));
            System.out.println(value);
            System.out.println(fromCall);
            System.out.println(dateString);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            business.setName(promptChips.getChips().get(0));
            business.setResponsibleName(promptChips.getChips().get(4));
            business.setLinkedEntityName(value);
            business.setType(fromCall);
            business.setDate(LocalDateTime.parse(dateString, formatter));
            System.out.println(business.getDate());
            business.setStatus("Активно");
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
//        String[] arrStr = {promptChips.getChips().get(0), dateString, "Активно", fromCall, value,
//                promptChips.getChips().get(4)};
        JSONObject tempString = Singleton.getInstance().getDataController().AddBusiness(business);
        if (tempString.getString("response").equals("null")) {
            Label messageBox = new Label("Ошибка добавления данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        OnClose();
    }

    public void OnClose() {
        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(stackPane, 1, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Stage st = (Stage) addButton.getScene().getWindow();
            st.close();
        });
    }
}
