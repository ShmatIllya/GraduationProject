package practise.subs;

import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class CustomTextArea extends JFXTextArea {
    public CustomTextArea() {
        setWrapText(true);
        setEditable(false);
        setFont(Font.font("Regular", 12));

        sceneProperty().addListener((observableNewScene, oldScene, newScene) -> {
            if (newScene != null) {
                applyCss();
                Node text = lookup(".text");

                // 2)
                prefHeightProperty().bind(Bindings.createDoubleBinding(() -> {
                    return getFont().getSize() + text.getBoundsInLocal().getHeight();
                }, text.boundsInLocalProperty()));

                // 1)
                text.boundsInLocalProperty().addListener((observableBoundsAfter, boundsBefore, boundsAfter) -> {
                    Platform.runLater(() -> requestLayout());
                });
            }
        });
    }
}
