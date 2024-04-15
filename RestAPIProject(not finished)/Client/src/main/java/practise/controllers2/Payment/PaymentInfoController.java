package practise.controllers2.Payment;

import DTO.PaymentDTO;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.jfoenix.controls.JFXButton;
import com.sun.javafx.application.HostServicesDelegate;
import javafx.animation.FadeTransition;
import javafx.application.HostServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.transform.Scale;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONException;
import org.json.JSONObject;
import practise.HelloApplication;
import practise.controllers2.DashboardController;
import practise.items.ClientsItems;
import practise.items.PaymentInfoItems;
import practise.singleton.Singleton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.ResourceBundle;

public class PaymentInfoController implements Initializable {
    public JFXButton sendButton;
    public StackPane stackPane;
    public TableView<PaymentInfoItems> tableView;
    public TableColumn<PaymentInfoItems, Integer> idColumn;
    public TableColumn<PaymentInfoItems, String> nameColumn;
    public TableColumn<PaymentInfoItems, String> measurementColumn;
    public TableColumn<PaymentInfoItems, Integer> amountColumn;
    public TableColumn<PaymentInfoItems, Integer> priceColumn;
    public TableColumn<PaymentInfoItems, Integer> taxesColumn;
    public TableColumn<PaymentInfoItems, Integer> finalPriceColumn;
    public TextField dateField;
    public TextField paymentField;
    public TextField receiverField;
    public TextField patternField;
    public JFXButton sendButtonSample;
    public HBox ChoiceHBoxSample;
    public Label idLabel;
    public JFXButton redactButton;
    public JFXButton copyButton;
    public JFXButton deleteButton;
    public JFXButton printButton;

    public javafx.scene.layout.AnchorPane AnchorPane;
    public javafx.scene.layout.AnchorPane AnchorPane2;
    public HBox PDFHBox;
    Boolean responsableValue = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, String>("name"));
        measurementColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, String>("measurement"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("price"));
        taxesColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("taxes"));
        finalPriceColumn.setCellValueFactory(new PropertyValueFactory<PaymentInfoItems, Integer>("finalPrice"));
        try {
            OnReload();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void InitFromClient() {
        responsableValue = true;
    }

    public void OnReload() throws URISyntaxException, IOException, JSONException {
        idLabel.setText("Счет " + Singleton.getInstance().getClientsID());
        ObservableList<ClientsItems> observableList = FXCollections.observableArrayList();
        PaymentDTO arrStr = new PaymentDTO();
        arrStr.setPaymentId(Singleton.getInstance().getClientsID());
        JSONObject tempString = Singleton.getInstance().getDataController().GetPaymentInfo(arrStr);
        if(!tempString.getString("response").equals("null")) {
            dateField.setText(tempString.getString("creationDate"));
            paymentField.setText(tempString.getString("paymenterName"));
            receiverField.setText(tempString.getString("receiverName"));
            ObservableList<PaymentInfoItems> list;
//        for(int i = 4; i < resultSet.length; i++) {
//            String[] resultSubSet = resultSet[i].split("<<");
//            list = FXCollections.observableArrayList(new PaymentInfoItems(Integer.parseInt(resultSubSet[0]),
//                    resultSubSet[1], resultSubSet[2], Integer.parseInt(resultSubSet[3]),
//                    Integer.parseInt(resultSubSet[4]), Integer.parseInt(resultSubSet[5]),
//                    Integer.parseInt(resultSubSet[6])));
//            tableView.setItems(list);
//        }
            list = FXCollections.observableArrayList(new PaymentInfoItems(Integer.parseInt(tempString.getString("itemID")),
                    tempString.getString("itemName"), tempString.getString("measurement"),
                    Integer.parseInt(tempString.getString("amount")),
                    Integer.parseInt(tempString.getString("itemPrice")), Integer.parseInt(tempString.getString("taxes")),
                    Integer.parseInt(tempString.getString("finalPrice"))));
            tableView.setItems(list);

            if (tempString.getString("status").equals("Передан в оплату")) {
                double posX = sendButton.getLayoutX();
                double posY = sendButton.getLayoutY();
                sendButton.setVisible(false);
                sendButton.setLayoutX(ChoiceHBoxSample.getLayoutX());
                sendButton.setLayoutY(ChoiceHBoxSample.getLayoutY());
                ChoiceHBoxSample.setLayoutX(posX);
                ChoiceHBoxSample.setLayoutY(posY);
                ChoiceHBoxSample.setVisible(true);
            } else if (tempString.getString("status").equals("Отменен")) {
                sendButton.setVisible(false);
                ChoiceHBoxSample.setVisible(false);
            } else if (tempString.getString("status").equals("Оплачен")) {
                double posX;
                double posY;
                if(PDFHBox.isVisible()) {

                }
                else if (ChoiceHBoxSample.isVisible()) {
                    posX = ChoiceHBoxSample.getLayoutX();
                    posY = ChoiceHBoxSample.getLayoutY();
                    ChoiceHBoxSample.setVisible(false);
                    ChoiceHBoxSample.setLayoutX(PDFHBox.getLayoutX());
                    ChoiceHBoxSample.setLayoutY(PDFHBox.getLayoutY());
                    PDFHBox.setLayoutX(posX);
                    PDFHBox.setLayoutY(posY);
                    PDFHBox.setVisible(true);
                } else {
                    posX = sendButton.getLayoutX();
                    posY = sendButton.getLayoutY();
                    sendButton.setVisible(false);
                    sendButton.setLayoutX(PDFHBox.getLayoutX());
                    sendButton.setLayoutY(PDFHBox.getLayoutY());
                    PDFHBox.setLayoutX(posX);
                    PDFHBox.setLayoutY(posY);
                    PDFHBox.setVisible(true);
                }


                //ImageIO.write(SwingFXUtils.fromFXImage(image, null), "PNG", new File("D:\\check.png"));
            }
            if (Singleton.getInstance().getFinal_Role().equals("obey") && responsableValue != true) {
                sendButton.setVisible(false);
                redactButton.setVisible(false);
                copyButton.setVisible(false);
                deleteButton.setVisible(false);
                ChoiceHBoxSample.setVisible(false);
            }
        }
        else {
            Label MessageLabel = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, MessageLabel);
        }
    }

    public void OnSendButton(ActionEvent event) throws URISyntaxException, IOException, JSONException {
        PaymentDTO payment = new PaymentDTO();
        try {
            payment.setPaymentId(Singleton.getInstance().getClientsID());
            payment.setStatus("Передан в оплату");
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().ChangePaymentStatus(payment);
        Label messageBox = new Label();
        if(tempString.getString("response").equals("null")) {
            messageBox.setText("Ошибка изменения");
            return;
        }
        else {
            messageBox.setText("Счет передан в оплату");
        }
        Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
        OnReload();
    }

    public void OnRedactButton(ActionEvent event) throws IOException, URISyntaxException, JSONException {

        ArrayList<String> list = PerformFullInfoGet();
        if (list.isEmpty()) {
            return;
        }
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Payments/PaymentCopyUpdate.fxml"));
        StackPane page = fxmlLoader.load();
        page.setAlignment(Pos.CENTER);
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        PaymentCopyUpdateController controller = fxmlLoader.getController();
        controller.InitController(true, list);
        stage.showAndWait();

        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0.5, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Singleton.getInstance().getOpacityPane().setVisible(false);
        });
        OnReload();
    }

    public void OnCopyButton(ActionEvent event) throws IOException, URISyntaxException, JSONException {

        ArrayList<String> list = PerformFullInfoGet();
        if (list.isEmpty()) {
            return;
        }
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Payments/PaymentCopyUpdate.fxml"));
        StackPane page = fxmlLoader.load();
        page.setAlignment(Pos.CENTER);
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        PaymentCopyUpdateController controller = fxmlLoader.getController();
        controller.InitController(false, list);
        stage.showAndWait();

        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0.5, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Singleton.getInstance().getOpacityPane().setVisible(false);
        });
        OnReload();
    }

    public void OnDeleteButton(ActionEvent event) throws IOException, JSONException {
        PaymentDTO payment = new PaymentDTO();
        try {
            payment.setPaymentId(Singleton.getInstance().getClientsID());
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().DeletePayment(payment);
        if(tempString.getString("response").equals("null")) {
            Label messageBox = new Label("Ошибка удаления данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        DashboardController dashboardController = new DashboardController();
        dashboardController.SwitchMainPane("/SubFXMLs/Payments/Payment.fxml");
    }

    public void OnConfirmButton(ActionEvent event) throws URISyntaxException, IOException, JSONException {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg"));
        File file = chooser.showOpenDialog(null);
        BufferedImage image = SwingFXUtils.fromFXImage(new javafx.scene.image.Image(file.getPath()), null);
        JSONObject arrStr = new JSONObject();
        arrStr.put("paymentID", Singleton.getInstance().getClientsID());
        JSONObject tempString = Singleton.getInstance().getDataController().CompletePayment(arrStr, image);
        Label messageBox = new Label();
        if(tempString.getString("response").equals("null")) {
            messageBox.setText("Ошибка изменения");
        }
        else {
            messageBox.setText("Счет оплачен");
        }
        Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
        OnReload();
    }

    public void OnRejectButton(ActionEvent event) throws URISyntaxException, IOException, JSONException {
        PaymentDTO payment = new PaymentDTO();
        try {
            payment.setPaymentId(Singleton.getInstance().getClientsID());
            payment.setStatus("Отменен");
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().ChangePaymentStatus(payment);
        Label messageBox = new Label();
        if(tempString.getString("response").equals("null")) {
            messageBox.setText("Ошибка изменения");
            return;
        }
        else {
            messageBox.setText("Счет отменен");
        }
        Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
        OnReload();
    }

    public void OnPrintButton(ActionEvent event) throws DocumentException, IOException {
        redactButton.setVisible(false);
        copyButton.setVisible(false);
        deleteButton.setVisible(false);
        PDFHBox.setVisible(false);
        AnchorPane2.setStyle("-fx-background-color: white");
        saveAsPng(stackPane, "snapshot");
        redactButton.setVisible(true);
        copyButton.setVisible(true);
        deleteButton.setVisible(true);
        PDFHBox.setVisible(true);
        AnchorPane2.setStyle("-fx-background-color: #151928");
        Singleton.getInstance().ShowJFXDialogStandart(stackPane, new Label("Чек успешно распечатан"));
    }

    public void saveAsPng(Node node, String fname) throws DocumentException, IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Select Directory");
        File selectedDirectory = chooser.showDialog(AnchorPane.getScene().getWindow());
        saveAsPdf(node, fname, new SnapshotParameters(), selectedDirectory);
    }

    public void saveAsPdf(Node node, String fname, SnapshotParameters ssp, File selectedDirectory) throws IOException,
            DocumentException {
        if(selectedDirectory == null) {
            return;
        }
        WritableImage image = node.snapshot(ssp, null);
        File file = new File(fname + ".png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        Document document = new Document(PageSize.A4.rotate());
        FileOutputStream fos = new FileOutputStream(selectedDirectory + "/check.pdf");
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        writer.open();
        document.open();
        Image img = Image.getInstance(fname + ".png");
        double scaler = (((document.getPageSize().getWidth() - document.leftMargin()
                - document.rightMargin()) / image.getWidth()) * 100);

        img.scalePercent((float) scaler);
        document.add(img);
        document.close();
        writer.close();
        Files.delete(Paths.get(fname + ".png"));
    }

    public void OnShowButton(ActionEvent event) throws DocumentException, IOException, URISyntaxException, InterruptedException, JSONException {
        /*redactButton.setVisible(false);
        copyButton.setVisible(false);
        deleteButton.setVisible(false);
        PDFHBox.setVisible(false);
        AnchorPane2.setStyle("-fx-background-color: white");
        saveAsPdf(stackPane, "snapshot", new SnapshotParameters(),
                new File(getClass().getResource("/images").toURI()));
        HelloApplication h = new HelloApplication();
        HostServices hostServices = h.getHostServices();
        hostServices.showDocument(getClass().getResource("/images/check.pdf").toString());
        //Files.delete(Paths.get(getClass().getResource("/images/check.pdf").toURI()));
        redactButton.setVisible(true);
        copyButton.setVisible(true);
        deleteButton.setVisible(true);
        PDFHBox.setVisible(true);
        AnchorPane2.setStyle("-fx-background-color: #151928");*/
        JSONObject arrStr = new JSONObject();
        arrStr.put("paymentID", Singleton.getInstance().getClientsID());
        JSONObject tempString = Singleton.getInstance().getDataController().GetPaymentCheck(arrStr);
        if(tempString.getString("response").equals("null"))
        {
            Label messageBox = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        else {
            byte[] imageBytes = Base64.getDecoder().decode(tempString.getString("image"));
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            String s = "target/classes/images/" + "tempCheck.png";
            ImageIO.write(image, "PNG", new File(s));

            HelloApplication h = new HelloApplication();
            HostServices hostServices = h.getHostServices();
            System.out.println(getClass().getResource("/images/tempCheck.png"));
            hostServices.showDocument(getClass().getResource("/images/tempCheck.png").toString());
            //Files.delete(Paths.get("src/main/resources/images/" + "tempCheck.png"));
        }
    }

    public ArrayList<String> PerformFullInfoGet() throws JSONException {
        ArrayList<String> list = new ArrayList<>();
        PaymentDTO arrStr = new PaymentDTO();
        try {
            arrStr.setPaymentId(Singleton.getInstance().getClientsID());
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка получения данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return null;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().GetFullPaymentInfo(arrStr);
        try {
            list.add(tempString.getString("creationDate"));
            list.add(tempString.getString("paymenterName"));
            list.add(tempString.getString("receiverName"));
            list.add(tempString.getString("deadline"));
            list.add(tempString.getString("subInfo"));
            list.add(tempString.getString("itemName"));
            list.add(tempString.getString("measurement"));
            list.add(tempString.getString("amount"));
            list.add(tempString.getString("taxes"));
            list.add(tempString.getString("finalPrice"));
            list.add(tempString.getString("id"));
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return null;
        }
        return list;
    }
}
