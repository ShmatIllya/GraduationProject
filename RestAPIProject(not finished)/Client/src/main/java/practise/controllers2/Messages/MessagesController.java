package practise.controllers2.Messages;

import DTO.ChatDTO;
import DTO.MessageDTO;
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
import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
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
    public StackPane stackPane;
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

        if (Singleton.getInstance().getFinal_Role().equals("obey")) {
            chatAddButton.setVisible(false);
        }
        opacityPane.setVisible(true);
        try {
            OnReload();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        chatsVBox.getChildren().remove(0);
    }

    public void OnReload() throws IOException, JSONException {
        opacityPane.setVisible(true);
        JSONObject chat = new JSONObject();
        chat.put("nameSurname", Singleton.getInstance().getFinal_NameSername());
        JSONObject tempString = Singleton.getInstance().getDataController().GetChatsList(chat);
        System.out.println(tempString);
        try {
            if (!tempString.getString("response").equals("null")) {
                JSONArray chatList = tempString.getJSONArray("chatList");
                for (int i = 0; i < chatList.length(); i++) {
                    byte[] imageBytes = Base64.getDecoder().decode(chatList.getJSONObject(i).getString("image"));
                    Image image2 = new Image(new ByteArrayInputStream(imageBytes));
                    CreateChatPane(chatList.getJSONObject(i).getString("name"),
                            chatList.getJSONObject(i).getString("senderName"),
                            chatList.getJSONObject(i).getString("text"),
                            chatList.getJSONObject(i).getString("sendTime"),
                            chatList.getJSONObject(i).getString("unreadMesCount"), image2);
                    //Singleton.getInstance().getIs().read(imageAr);
                    //avatarImage.setImage(image2);
                }
            } else {
                Label messageBox = new Label("Ошибка обработки данных");
                Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            }
        } catch (Exception e) {
            Label messageBox = new Label("Ошибка обработки данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
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
        if (unreadNumber.getText().equals("0")) {
            unread.setVisible(false);
        }

        chatPane.setOnMouseClicked(event -> {
            System.out.println("This is " + nameOfChat);
            unread.setVisible(false);
            CurrentChatName = nameOfChat;
            try {
                OpenChat(nameOfChat);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        chatsVBox.getChildren().add(chatPane);

    }

    public void OnChatAddButton(ActionEvent event) throws IOException, JSONException {
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

    public void OpenChat(String chatName) throws IOException, JSONException {
        MessageDTO arrStrDelete = new MessageDTO();
        try {
            arrStrDelete.setChatName(chatName);
            arrStrDelete.setSenderName(Singleton.getInstance().getFinal_NameSername());
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempStringDelete = Singleton.getInstance().getDataController().DeleteMessageStatus(arrStrDelete);
        if(tempStringDelete.getString("response").equals("null")) {
            Label messageBox = new Label("Ошибка обновления статусов");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }

        CurrentChatName = chatName;
        opacityPane.setVisible(false);
        List<Image> recivedImages = new ArrayList<>();
        List<String> imagesNames = new ArrayList<>();
        ChatDTO arrStr = new ChatDTO();
        try {
            arrStr.setName(chatName);
        } catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().GetChatMessages(arrStr);
        JSONArray resultSet = tempString.getJSONArray("imagesList");

        for (int i = 0; i < resultSet.length(); i++) {
            byte[] imageBytes = Base64.getDecoder().decode(resultSet.getJSONObject(i).getString("image"));
            Image image = new Image(new ByteArrayInputStream(imageBytes));
            recivedImages.add(image);
            imagesNames.add(resultSet.getJSONObject(i).getString("nameSurname"));
        }

        messagesVBox.getChildren().clear();
        resultSet = tempString.getJSONArray("messageByDateList");
        for (int i = 0; i < resultSet.length(); i++) {
            CreateHeaderDate(resultSet.getJSONObject(i).getString("date"));
            JSONArray messageList = resultSet.getJSONObject(i).getJSONArray("messageList");
            for (int j = 0; j < messageList.length(); j++) {
                try {
                    CreateMessageBox(messageList.getJSONObject(j).getString("text"),
                            messageList.getJSONObject(j).getString("time"),
                            messageList.getJSONObject(j).getString("senderName"), recivedImages, imagesNames);
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
        if (nameSername.equals(Singleton.getInstance().getFinal_NameSername())) {
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
        for (int i = 0; i < imagesNames.size(); i++) {
            if (imagesNames.get(i).equals(nameSername)) {
                photoView2.setPhoto(recievedImages.get(i));
                break;
            }
        }
        hBoxPhoto.getChildren().add(photoView2);

        VBox bodyVBox = new VBox();
        bodyVBox.setPrefWidth(messageHBox.getWidth());
        bodyVBox.setPrefHeight(messageHBox.getHeight());
        if (nameSername.equals(Singleton.getInstance().getFinal_NameSername())) {
            bodyVBox.setStyle("-fx-background-color:  #70c945; -fx-background-radius: 30");
        } else {
            bodyVBox.setStyle("-fx-background-color:  #2196f3; -fx-background-radius: 30");
        }
        hbox.getChildren().add(bodyVBox);

        HBox headerHbox = new HBox();
        headerHbox.setPrefWidth(inMessageHbox.getWidth());
        headerHbox.setPrefHeight(inMessageHbox.getHeight());
        bodyVBox.getChildren().add(headerHbox);
        bodyVBox.setMargin(headerHbox, new Insets(0, 0, 0, 15));

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
        headerHbox.setMargin(timeLabel, new Insets(2, 0, 0, 20));

        CustomTextArea messageArea2 = new CustomTextArea();
        messageArea2.setStyle("-fx-border-color: transparent");
        bodyVBox.setPrefHeight(messageArea2.getPrefHeight());
        bodyVBox.getChildren().add(messageArea2);
        bodyVBox.setMargin(messageArea2, new Insets(0, 15, 0, 15));
        messageArea2.setEditable(true);
        hbox.setPrefHeight(bodyVBox.getPrefHeight());
        messageArea2.setText(text);
    }

    public void OnSendMessageButton(ActionEvent event) throws IOException, JSONException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        MessageDTO message = new MessageDTO();
        try {
            message.setText(messageInputTextField.getText());
            message.setDate(Date.valueOf(formatter.format(LocalDateTime.now())));
            message.setSenderName(Singleton.getInstance().getFinal_NameSername());
            message.setChatName(CurrentChatName);
            message.setTime(Time.valueOf(sdf.format(date)));
        }
        catch (Exception e) {
            Label messageBox = new Label("Ошибка ввода данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        JSONObject tempString = Singleton.getInstance().getDataController().AddMessage(message);
        if(tempString.getString("response").equals("null")) {
            Label messageBox = new Label("Ошибка отправки данных");
            Singleton.getInstance().ShowJFXDialogStandart(stackPane, messageBox);
            return;
        }
        OpenChat(CurrentChatName);
    }
}
