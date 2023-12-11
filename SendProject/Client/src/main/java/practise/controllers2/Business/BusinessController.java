package practise.controllers2.Business;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import javafx.animation.FadeTransition;
import javafx.collections.WeakListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;
import practise.singleton.Singleton;

import javax.swing.event.ChangeListener;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    public void InitController(String fromCall, String value) {

        AnchorPane.getChildren().add(promptChips);
        promptChips.setPrefHeight(38.4);
        promptChips.setLayoutX(68);
        promptChips.setLayoutY(22);
        promptChips.getStylesheets().add("/css/jfxChipsView.css");
        layoutHBox.getChildren().add(promptChips);
        promptChips.setPrefWidth(302.4);

        String[] businessType = {"Дело", "Личное", "Звонок", "Встреча", "Совещание", "Праздник", "Спорт"};
        String[] businessDate = {"Завтра", "Послезавтра", "Через неделю", "Через месяц"};
        String[] businessTime = {"в 09", "в 10", "в 11", "в 12", "в 13", "в 14", "в 15", "в 16", "в 17", "в 18"};
        String[] businessMinutes = {":00", ":05", ":10", ":15", ":20", ":25", ":30", ":35", ":40", ":45", ":50", ":55"};
        promptChips.getSuggestions().addAll(businessType);

        List<String> personal = new ArrayList<>();
        String[] arrStr = {"GetPersonalList"};
        String tempString = (String) Singleton.getInstance().getDataController().GetPersonalList(arrStr);
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


        promptChips.getChips().addListener(new WeakListChangeListener<>(change -> {
            System.out.println("tttttttttttttttttttttttttttttt");
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
    public void OnAddButton() {
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
        String[] arrStr = {"AddBusiness", promptChips.getChips().get(0), dateString, "Активно", fromCall, value,
                promptChips.getChips().get(4)};
        String tempString = (String) Singleton.getInstance().getDataController().AddBusiness(arrStr);
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
