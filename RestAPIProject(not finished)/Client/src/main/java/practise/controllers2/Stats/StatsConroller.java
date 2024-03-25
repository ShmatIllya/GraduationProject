package practise.controllers2.Stats;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import practise.HelloApplication;
import practise.singleton.Singleton;

import java.io.IOException;

public class StatsConroller {
    public StackPane stackPane;
    public JFXButton personalButton;
    public JFXButton clientsButton;
    public JFXButton paymentsButton;
    public JFXButton tasksButton;
    public JFXButton projectsButton;
    public JFXButton businessButton;

    public void OnPersonalButton(ActionEvent event) throws IOException {
        InitController("/SubFXMLs/Stats/SubStat.fxml", "Персонал");
    }

    public void OnClientsButton(ActionEvent event) throws IOException {
        InitController("/SubFXMLs/Stats/SubStat.fxml", "Клиент");
    }

    public void OnPaymentsButton(ActionEvent event) throws IOException {
        InitController("/SubFXMLs/Stats/SubStat.fxml", "Счет");
    }

    public void OnTasksButton(ActionEvent event) throws IOException {
        InitController("/SubFXMLs/Stats/SubStat.fxml", "Задача");
    }

    public void OnProjectsButton(ActionEvent event) throws IOException {
        InitController("/SubFXMLs/Stats/SubStat.fxml", "Проект");
    }

    public void OnBusinessButton(ActionEvent event) throws IOException {
        InitController("/SubFXMLs/Stats/SubStat.fxml", "Дело");
    }

    public void InitController(String fxml, String dataType) throws IOException {
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        StackPane page = fxmlLoader.load();
        page.setAlignment(Pos.CENTER);
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        StatInfoController statInfoController = fxmlLoader.getController();
        statInfoController.InitController(dataType);
        stage.showAndWait();

        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0.5, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Singleton.getInstance().getOpacityPane().setVisible(false);
        });
    }
}
