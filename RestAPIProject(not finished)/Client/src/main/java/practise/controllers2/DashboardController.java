package practise.controllers2;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import practise.singleton.Singleton;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    public ImageView menuImage;
    public ImageView personalImage;
    @FXML
    public AnchorPane mainPane;
    public AnchorPane globalOpacityPane;
    public VBox buttonVBox;
    public ImageView clientImage;
    public ImageView paymentImage;
    public ImageView taskImage;
    public ImageView messengerImage;
    public ImageView projectImage;
    public ImageView statImage;

    @FXML
    private AnchorPane opacityPane, drawerPane;

    @FXML
    private ImageView exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainPane.setVisible(false);
        Singleton.getInstance().setInitialPane(mainPane);
        Singleton.getInstance().setOpacityPane(globalOpacityPane);
        Singleton.getInstance().setDashboardVBox(buttonVBox);
        exit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        opacityPane.setVisible(false);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), opacityPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), drawerPane);
        translateTransition.setByX(-600);
        translateTransition.play();

        menuImage.setOnMouseClicked(event -> {


            opacityPane.setVisible(true);

            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), opacityPane);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        personalImage.setOnMouseClicked(event -> {
            try {
                SwitchMainPane("/SubFXMLs/Personal/Personal.fxml");
            } catch (Exception e) {

            }
        });

        clientImage.setOnMouseClicked(event -> {
            try {
                SwitchMainPane("/SubFXMLs/Clients/Client.fxml");
            } catch (Exception e) {

            }
        });

        paymentImage.setOnMouseClicked(event -> {
            try {
                //paymentImage.setImage(new Image("@images/PaymentIcons/Payment1.png"));
                SwitchMainPane("/SubFXMLs/Payments/Payment.fxml");
            } catch (Exception e) {

            }
        });

        taskImage.setOnMouseClicked(event -> {
            try {
                //paymentImage.setImage(new Image("@images/PaymentIcons/Payment1.png"));
                SwitchMainPane("/SubFXMLs/Tasks/Tasks.fxml");
            } catch (Exception e) {

            }
        });

        messengerImage.setOnMouseClicked(event -> {
            try {
                //paymentImage.setImage(new Image("@images/PaymentIcons/Payment1.png"));
                SwitchMainPane("/SubFXMLs/Messanger/Messanger.fxml");
            } catch (Exception e) {

            }
        });

        projectImage.setOnMouseClicked(event -> {
            try {
                //paymentImage.setImage(new Image("@images/PaymentIcons/Payment1.png"));
                SwitchMainPane("/SubFXMLs/Projects/Project.fxml");
            } catch (Exception e) {

            }
        });

        statImage.setOnMouseClicked(event -> {
            try {
                //paymentImage.setImage(new Image("@images/PaymentIcons/Payment1.png"));
                SwitchMainPane("/SubFXMLs/Stats/Stat.fxml");
            } catch (Exception e) {

            }
        });


        opacityPane.setOnMouseClicked(event -> {


            FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), opacityPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                opacityPane.setVisible(false);
            });


            TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), drawerPane);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });
    }

    public void SwitchMainPane(String fxml) throws IOException {
        Singleton.getInstance().getDashboardVBox().getChildren().clear();
        if (Singleton.getInstance().getActivePane() != null) {
            Singleton.getInstance().getActivePane().getChildren().clear();
        }
        Singleton.getInstance().setActivePane(Singleton.getInstance().getInitialPane());
        Singleton.getInstance().getActivePane().getChildren().add(FXMLLoader.load(getClass().getResource(fxml)));
        Singleton.getInstance().getActivePane().setVisible(true);
        Singleton.getInstance().getActivePane().setTopAnchor(Singleton.getInstance().getActivePane().getChildren().get(0), 10.00);
        Singleton.getInstance().getActivePane().setLeftAnchor(Singleton.getInstance().getActivePane().getChildren().get(0), 10.00);
        Singleton.getInstance().getActivePane().setRightAnchor(Singleton.getInstance().getActivePane().getChildren().get(0), 10.00);
        Singleton.getInstance().getActivePane().setBottomAnchor(Singleton.getInstance().getActivePane().getChildren().get(0), 10.00);
    }

    public void SetVBoxButtons(ArrayList<JFXButton> buttons) {
        Singleton.getInstance().getDashboardVBox().getChildren().clear();
        Singleton.getInstance().getDashboardVBox().getChildren().setAll(buttons);
    }
}
