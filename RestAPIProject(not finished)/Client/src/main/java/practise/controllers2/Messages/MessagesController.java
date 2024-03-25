package practise.controllers2.Messages;

import com.dlsc.gemsfx.EnhancedLabel;
import com.dlsc.gemsfx.PhotoView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import practise.HelloApplication;
import practise.items.TasksItems;
import practise.singleton.Singleton;
import practise.subs.CustomTextArea;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MessagesController implements Initializable {
    public TextField chatSearchField;
    public FontAwesomeIcon editButton;
    public VBox chatsVBox;
    public Pane sampleChatPane;
    public HBox photoViewHBox;
    public PhotoView photoView;
    public Label chatChatNameLabel;
    public Label chatNameSernameLabel;
    public Label chatMessageTextLabel;
    public Label chatTimeLabel;
    public Label chatNameLabel;
    public Label chatMembersLabel;
    public TextField messageSearchField;
    public VBox messagesVBox;
    public HBox userMessageHBox;
    public HBox myMessageHBox;
    public HBox dateHBox;
    public TextField messageInputTextField;
    public JFXButton sendMessageButton;
    public FontAwesomeIcon journalButton1;
    public VBox messageHBox;
    public VBox messageHBox1;
    public AnchorPane messageInputPane;
    public Pane unreadMessagesPane;
    public Label unreadMessagesLabel;
    public AnchorPane opacityPane;
    public JFXButton chatAddButton;
    public HBox inMessageHbox;
    public EnhancedLabel inMessageNameSername;
    public EnhancedLabel inMessageTime;
    String CurrentChatName;
    CustomTextArea messageInputArea = new CustomTextArea();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messageInputArea.setPrefWidth(messageInputTextField.getWidth());
        messageInputArea.setPrefHeight(messageInputTextField.getHeight());
        messageInputArea.setLayoutX(messageInputTextField.getLayoutX());
        messageInputArea.setLayoutY(messageInputTextField.getLayoutY());
        //messageInputPane.getChildren().remove(0);
        messageInputPane.getChildren().add(0, messageInputArea);

        if(Singleton.getInstance().getFinal_Role().equals("obey")) {
            chatAddButton.setVisible(false);
        }
        opacityPane.setVisible(true);
        try {
            OnReload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        chatsVBox.getChildren().remove(0);
    }

    public void OnReload() throws IOException {
        opacityPane.setVisible(true);
        String[] arrStr = {"GetChatsList", Singleton.getInstance().getFinal_NameSername()};
        String tempString = (String) Singleton.getInstance().getDataController().GetChatsList(arrStr);
        System.out.println(tempString);
        List<Image> recivedImages = new ArrayList<>();
        for(int i = 0; i < tempString.split(">>").length; i++)
        {
            byte[] sizeAr = new byte[4];
            Singleton.getInstance().getSock().getInputStream().read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
            byte[] imageAr = new byte[size];
            DataInputStream in = new DataInputStream(Singleton.getInstance().getSock().getInputStream());
            in.readFully(imageAr);
            //Singleton.getInstance().getIs().read(imageAr);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
            Image image2 = SwingFXUtils.toFXImage(image, null);
            //avatarImage.setImage(image2);
            recivedImages.add(image2);
        }


        tempString = tempString.replaceAll("\r", "");
        System.out.println(tempString);
        String[] resultSet = tempString.split(">>");
        for(int i = 0; i < resultSet.length; i++) {
            String[] resultSubSet = resultSet[i].split("<<");
            try {
                CreateChatPane(resultSubSet[0], resultSubSet[1], resultSubSet[2], resultSubSet[3],
                        resultSubSet[4], recivedImages.get(i));
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void CreateChatPane(String nameOfChat, String nameOfSender, String textSent, String timeSent,
                               String unreadCount, Image image) {
        Pane chatPane = new Pane();
        chatPane.setStyle("-fx-border-color: #2196f3");
        chatPane.setPrefWidth(sampleChatPane.getPrefWidth());
        chatPane.setPrefHeight(sampleChatPane.getPrefHeight());
        HBox hBox = new HBox();

        hBox.setPrefWidth(photoViewHBox.getWidth());
        hBox.setPrefHeight(photoViewHBox.getHeight());
        chatPane.getChildren().add(hBox);
        hBox.setLayoutX(photoViewHBox.getLayoutX());
        hBox.setLayoutY(photoViewHBox.getLayoutY());

        PhotoView photoView2 = new PhotoView();
        photoView2.setPrefWidth(photoView.getWidth());
        photoView2.setPrefHeight(photoView.getHeight());
        photoView2.setEditable(false);
        photoView2.setPhoto(image);
        hBox.getChildren().add(photoView2);

        Label chatName = new Label();
        chatPane.getChildren().add(chatName);
        chatName.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2196f3");
        chatName.setLayoutX(chatChatNameLabel.getLayoutX());
        chatName.setLayoutY(chatChatNameLabel.getLayoutY());
        chatName.setPrefWidth(162);
        chatName.setPrefHeight(20);
        chatName.setText(nameOfChat);

        Label nameSername = new Label();
        chatPane.getChildren().add(nameSername);
        nameSername.setStyle("-fx-font-size: 12,5px; -fx-text-fill: #2196f3");
        nameSername.setLayoutX(chatNameSernameLabel.getLayoutX());
        nameSername.setLayoutY(chatNameSernameLabel.getLayoutY());
        nameSername.setPrefWidth(97);
        nameSername.setPrefHeight(19);
        nameSername.setText(nameOfSender);

        Label messageText = new Label();
        chatPane.getChildren().add(messageText);
        messageText.setStyle("-fx-font-size: 12,5px; -fx-font-style: italic; -fx-text-fill: #2196f3");
        messageText.setLayoutX(chatMessageTextLabel.getLayoutX());
        messageText.setLayoutY(chatMessageTextLabel.getLayoutY());
        messageText.setPrefWidth(78);
        messageText.setPrefHeight(19);
        messageText.setText(textSent);

        Label time = new Label();
        chatPane.getChildren().add(time);
        time.setStyle("-fx-font-size: 12px; -fx-text-fill: #2196f3");
        time.setLayoutX(chatTimeLabel.getLayoutX());
        time.setLayoutY(chatTimeLabel.getLayoutY());
        time.setPrefWidth(25.804 + 30);
        time.setPrefHeight(17);
        time.setText(timeSent);

        Pane unread = new Pane();
        chatPane.getChildren().add(unread);
        unread.setStyle("-fx-background-color: red; -fx-background-radius: 30");
        unread.setLayoutX(unreadMessagesPane.getLayoutX());
        unread.setLayoutY(unreadMessagesPane.getLayoutY());
        unread.setPrefWidth(25);
        unread.setPrefHeight(25);

        Label unreadNumber = new Label();
        unreadNumber.setTextAlignment(TextAlignment.CENTER);
        unreadNumber.setAlignment(Pos.CENTER);
        unread.getChildren().add(unreadNumber);
        unreadNumber.setStyle("-fx-background-color: red; -fx-background-radius: 30");
        unreadNumber.setLayoutX(unreadMessagesLabel.getLayoutX());
        unreadNumber.setLayoutY(unreadMessagesLabel.getLayoutY());
        unreadNumber.setPrefWidth(26);
        unreadNumber.setPrefHeight(17);
        unreadNumber.setText(unreadCount);
        if(unreadNumber.getText().equals("0")) {
            unread.setVisible(false);
        }

        chatPane.setOnMouseClicked(event -> {
            System.out.println("This is " + nameOfChat);
            unread.setVisible(false);
            CurrentChatName = nameOfChat;
            try {
                OpenChat(nameOfChat);
            }
            catch (IOException e) {
                throw  new RuntimeException(e);
            }
        });

        chatsVBox.getChildren().add(chatPane);

    }

    public void OnChatAddButton(ActionEvent event) throws IOException {
        Singleton.getInstance().getOpacityPane().setVisible(true);
        Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0, 0.5, 0.5);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/SubFXMLs/Messanger/ChatAdd.fxml"));
        StackPane page = fxmlLoader.load();
        page.setAlignment(Pos.CENTER);
        Singleton.getInstance().PerformFadeTransition(page, 0.0, 1.0, 1.5);
        Scene scene = new Scene(page);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setX(MouseInfo.getPointerInfo().getLocation().x);
        stage.setY(MouseInfo.getPointerInfo().getLocation().y);
        stage.showAndWait();

        FadeTransition ft = Singleton.getInstance().PerformFadeTransition(Singleton.instance.getOpacityPane(), 0.5, 0, 0.5);
        ft.setOnFinished(event1 -> {
            Singleton.getInstance().getOpacityPane().setVisible(false);
        });
        chatsVBox.getChildren().clear();
        OnReload();
    }

    public void OpenChat(String chatName) throws IOException {

        String[] arrStrDelete = {chatName, Singleton.getInstance().getFinal_NameSername()};
        String tempStringDelete = (String) Singleton.getInstance().getDataController().DeleteMessageStatus(arrStrDelete);

        CurrentChatName = chatName;
        opacityPane.setVisible(false);
        List<Image> recivedImages = new ArrayList<>();
        List<String> imagesNames = new ArrayList<>();
        String[] arrStr = {chatName};
        String tempString = (String) Singleton.getInstance().getDataController().GetChatMessages(arrStr);
        tempString = tempString.replaceAll("\r", "");
        System.out.println(tempString);
        String[] resultSet = tempString.split("\\^\\^");
        System.out.println(tempString);

        for(int i = 0; i < Integer.parseInt(resultSet[0]); i++)
        {
            byte[] sizeAr = new byte[4];
            Singleton.getInstance().getSock().getInputStream().read(sizeAr);
            int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
            byte[] imageAr = new byte[size];
            DataInputStream in = new DataInputStream(Singleton.getInstance().getSock().getInputStream());
            in.readFully(imageAr);
            //Singleton.getInstance().getIs().read(imageAr);
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageAr));
            //BufferedReader brinp = new BufferedReader(new InputStreamReader(Singleton.getInstance().getIs(), "UTF-8"));
            //imageName = brinp.readLine();

            //brinp.close();
            Image image2 = SwingFXUtils.toFXImage(image, null);
            //avatarImage.setImage(image2);
            recivedImages.add(image2);
        }

        String[] arrStr2 = {"GetChatMessagesNames"};
        String tempString2 = (String) Singleton.getInstance().getDataController().GetChatMessagesNames(arrStr2);
        tempString2 = tempString2.replaceAll("\r", "");
        String[] tempString2Arr = tempString2.split(">>");
        for(String k: tempString2Arr) {
            imagesNames.add(k);
        }


        messagesVBox.getChildren().clear();
        for(int i = 1; i < resultSet.length; i++) {
            String[] resultSubSet = resultSet[i].split(">>");
            CreateHeaderDate(resultSubSet[0]);
            for(int j = 1; j < resultSubSet.length; j++) {
                String[] resultSubSubSet = resultSubSet[j].split("<<");
                try {
                    CreateMessageBox(resultSubSubSet[0], resultSubSubSet[1], resultSubSubSet[2], recivedImages, imagesNames);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void CreateHeaderDate(String date) {
        HBox headerHbox = new HBox();
        headerHbox.setStyle("-fx-border-color: #2196f3");
        headerHbox.setPrefWidth(dateHBox.getWidth());
        headerHbox.setPrefHeight(dateHBox.getHeight());
        headerHbox.setAlignment(Pos.CENTER);
        messagesVBox.getChildren().add(headerHbox);
        Label headerDate = new Label();
        headerDate.setStyle("-fx-text-fill: #2196f3");
        headerDate.setText(date);
        headerHbox.getChildren().add(headerDate);
    }

    public void CreateMessageBox(String text, String time, String nameSername, List<Image> recievedImages,
                                 List<String> imagesNames) {
        System.out.println("Messssssssssssssssssssssssssss " + text + " " + time + " " + nameSername + " " + imagesNames.get(0));
        HBox hbox = new HBox();
        hbox.setPrefWidth(userMessageHBox.getWidth());
        hbox.setPrefHeight(userMessageHBox.getHeight());
        hbox.setSpacing(10);
        if(nameSername.equals(Singleton.getInstance().getFinal_NameSername())) {
            hbox.setAlignment(Pos.TOP_RIGHT);
        }
        messagesVBox.getChildren().add(hbox);

        HBox hBoxPhoto = new HBox();
        hBoxPhoto.setPrefWidth(photoViewHBox.getWidth());
        hBoxPhoto.setPrefHeight(photoViewHBox.getHeight());
        hbox.getChildren().add(hBoxPhoto);
        hBoxPhoto.setLayoutX(photoViewHBox.getLayoutX());
        hBoxPhoto.setLayoutY(photoViewHBox.getLayoutY());

        PhotoView photoView2 = new PhotoView();
        photoView2.setPrefWidth(photoView.getWidth());
        photoView2.setPrefHeight(photoView.getHeight());
        photoView2.setEditable(false);
        for(int i = 0; i < imagesNames.size(); i++) {
            if(imagesNames.get(i).equals(nameSername)) {
                photoView2.setPhoto(recievedImages.get(i));
                break;
            }
        }
        hBoxPhoto.getChildren().add(photoView2);

        VBox bodyVBox = new VBox();
        bodyVBox.setPrefWidth(messageHBox.getWidth());
        bodyVBox.setPrefHeight(messageHBox.getHeight());
        if(nameSername.equals(Singleton.getInstance().getFinal_NameSername())) {
            bodyVBox.setStyle("-fx-background-color:  #70c945; -fx-background-radius: 30");
        }
        else {
            bodyVBox.setStyle("-fx-background-color:  #2196f3; -fx-background-radius: 30");
        }
        hbox.getChildren().add(bodyVBox);

        HBox headerHbox = new HBox();
        headerHbox.setPrefWidth(inMessageHbox.getWidth());
        headerHbox.setPrefHeight(inMessageHbox.getHeight());
        bodyVBox.getChildren().add(headerHbox);
        bodyVBox.setMargin(headerHbox, new Insets(0,0,0,15));

        Label nameSernameLabel = new Label();
        nameSernameLabel.setPrefWidth(inMessageNameSername.getWidth());
        nameSernameLabel.setPrefHeight(inMessageNameSername.getHeight());
        nameSernameLabel.setText(nameSername);
        headerHbox.getChildren().add(nameSernameLabel);

        Label timeLabel = new Label();
        timeLabel.setPrefWidth(inMessageTime.getWidth() + 20);
        timeLabel.setPrefHeight(inMessageTime.getHeight());
        timeLabel.setText(time);
        headerHbox.getChildren().add(timeLabel);
        headerHbox.setMargin(timeLabel, new Insets(2,0,0,20));

        CustomTextArea messageArea2 = new CustomTextArea();
        messageArea2.setStyle("-fx-border-color: transparent");
        bodyVBox.setPrefHeight(messageArea2.getPrefHeight());
        bodyVBox.getChildren().add(messageArea2);
        bodyVBox.setMargin(messageArea2, new Insets(0,15,0,15));
        messageArea2.setEditable(true);
        hbox.setPrefHeight(bodyVBox.getPrefHeight());
        messageArea2.setText(text);
    }

    public void OnSendMessageButton(ActionEvent event) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        String[] arrStr = {messageInputTextField.getText(), formatter.format(LocalDateTime.now()),
                Singleton.getInstance().getFinal_NameSername(),
                CurrentChatName, sdf.format(date)
        };
        String tempString = (String) Singleton.getInstance().getDataController().AddMessage(arrStr);
        tempString = tempString.replaceAll("\r", "");
        //String[] resultSet = tempString.split("<<");
        Label MessageLabel = new Label();
        OpenChat(CurrentChatName);
    }
}
