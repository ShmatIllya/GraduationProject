package practise.singleton;

import DataController.IDataController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import practise.project.DataControllerBuilder;
import practise.subs.CustomTextArea;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Singleton {
    public static Singleton instance;
    private Socket sock;
    private InputStream is;
    private OutputStream os;
    private IDataController DataController;
    private String Final_NameSername;
    private String Final_Role;
    private String personalName;
    private AnchorPane activePane;
    private AnchorPane initialPane;
    private AnchorPane opacityPane;
    private String localLogin;
    private VBox dashboardVBox;
    private int ClientsID;
    private String[] TaskInfoValues;
    private String ProjectInfoValue;
    //===============================================================================
    //===============================OldValues=======================================
    //===============================================================================

    private ArrayList<String> Path = new ArrayList<String>();

    private String System;


    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private Singleton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            DataController = DataControllerBuilder.getDataController();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            sock = new Socket(InetAddress.getByName("localhost"), 1488);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            is = sock.getInputStream();

            os = sock.getOutputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Socket getSock() {
        return sock;
    }

    public InputStream getIs() {
        return is;
    }

    public OutputStream getOs() {
        return os;
    }

    public IDataController getDataController() {
        return DataController;
    }

    public String getFinal_NameSername() {
        return Final_NameSername;
    }

    public void setFinal_NameSername(String final_NameSername) {
        Final_NameSername = final_NameSername;
    }

    public String getFinal_Role() {
        return Final_Role;
    }

    public void setFinal_Role(String final_Role) {
        Final_Role = final_Role;
    }
    public ArrayList<String> getPath() {
        return Path;
    }
    public void setPath(String element) {
        Path.add(element);
    }


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }


    public String getSystem() {
        return System;
    }

    public void setSystem(String system) {
        System = system;
    }


    public static boolean YesNoMessageBox(String text) {
        AtomicBoolean result = new AtomicBoolean(false);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Вы уверены?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Вы уверены?");
        alert.setHeaderText(text);
        alert.showAndWait().ifPresent(rs -> {
            if (rs == ButtonType.YES) {
                result.set(true);
            }
            else if (rs == ButtonType.NO) {
                result.set(false);

            }
        });
        return result.get();
    }


    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public AnchorPane getInitialPane() {
        return initialPane;
    }

    public void  setInitialPane(AnchorPane initialPane) {
        this.initialPane = initialPane;
    }

    public AnchorPane getActivePane() {
        return activePane;
    }

    public void setActivePane(AnchorPane activePane) {
        this.activePane = activePane;
    }

    public AnchorPane getOpacityPane() {
        return opacityPane;
    }

    public void setOpacityPane(AnchorPane opacityPane) {
        this.opacityPane = opacityPane;
    }


    public String getLocalLogin() {
        return localLogin;
    }

    public void setLocalLogin(String localLogin) {
        this.localLogin = localLogin;
    }

    public VBox getDashboardVBox() {
        return dashboardVBox;
    }

    public void setDashboardVBox(VBox dashboardVBox) {
        this.dashboardVBox = dashboardVBox;
    }

    public int getClientsID() {
        return ClientsID;
    }

    public void setClientsID(int clientsID) {
        ClientsID = clientsID;
    }

    public String[] getTaskInfoValues() {
        return TaskInfoValues;
    }

    public void setTaskInfoValues(String[] taskInfoValues) {
        TaskInfoValues = taskInfoValues;
    }

    public FadeTransition PerformFadeTransition(Node node, double fromValue, double toValue, double duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.play();
        return fadeTransition;
    }

    public void ShowJFXDialogStandart(StackPane pane, Label MessageLabel, Stage st) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setStyle("-fx-background-color: #151928");
        MessageLabel.setStyle("-fx-text-fill: #2196f3");

        JFXDialog messageBox = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER);

        JFXButton btn = new JFXButton("Ясненько");
        btn.setOnAction(event -> {
            messageBox.close();
            st.close();
        });
        layout.setActions(btn);
        layout.setBody(MessageLabel);
        messageBox.show();
    }

    public void ShowJFXDialogStandart(StackPane pane, Label MessageLabel) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setStyle("-fx-background-color: #151928");
        MessageLabel.setStyle("-fx-text-fill: #2196f3");

        JFXDialog messageBox = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER);

        JFXButton btn = new JFXButton("Ясненько");
        btn.setStyle("-fx-text-fill: #2196f3");
        btn.setOnAction(event -> {
            messageBox.close();
        });
        layout.setActions(btn);
        layout.setBody(MessageLabel);
        messageBox.show();
    }

    public void ShowJFXDialogYesNo(StackPane pane, Label MessageLabel) {
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setStyle("-fx-background-color: #151928");
        MessageLabel.setStyle("-fx-text-fill: #2196f3");
        layout.setBody(MessageLabel);
        JFXDialog messageBox = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER);
        messageBox.show();
    }

    public HBox SetCommentBox(HBox originHBox, String text, String sender, String date, String login) {
        HBox resHBox = new HBox();
        resHBox.setVisible(true);
        CustomTextArea area = new CustomTextArea();
        area.setText(text);
        VBox newPane = new VBox();
        newPane.setPrefWidth(198);
        Label shapka = new Label();
        shapka.setStyle("-fx-font-size: 14");
        if(sender.equals(getFinal_NameSername())) {
            newPane.setStyle("-fx-background-color: white; -fx-background-radius: 5");
            shapka.setText("Вы" + "  " + date);
            VBox.setMargin(resHBox, new Insets(0,0,0,210));
        }
        else {
            newPane.setStyle("-fx-background-color: #848484; -fx-background-radius: 5");
            shapka.setText(sender + "  " + date);
            VBox.setMargin(resHBox, new Insets(0,0,0,0));
        }
        newPane.getChildren().addAll(shapka, area);
        resHBox.setPrefHeight(newPane.getPrefHeight());
        resHBox.getChildren().add(newPane);
        Random rand = new Random();
        resHBox.setId(String.valueOf(rand.nextInt(1000000)));
        return resHBox;
    }

    public String getProjectInfoValue() {
        return ProjectInfoValue;
    }

    public void setProjectInfoValue(String projectInfoValue) {
        ProjectInfoValue = projectInfoValue;
    }
}
