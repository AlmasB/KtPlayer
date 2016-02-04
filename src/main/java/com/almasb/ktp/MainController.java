package com.almasb.ktp;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class MainController {

    private MediaPlayerService playerService;

    public void initialize() {
        playerService = KtPlayerAppKt.getInjector().getInstance(MediaPlayerService.class);
    }

    public void onOpenFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file to open");
        fileChooser.setInitialDirectory(new File("./"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            playerService.setMediaFile(file);
        }
    }

    public void onAbout() {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("About");
        info.setHeaderText(null);
        info.setContentText("KtPlayer is a kotlin/javafx media player");
        info.show();
    }

    public void onExit() {
        playerService.dispose();
        System.exit(0);
    }
}
