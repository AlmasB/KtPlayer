package com.almasb.ktp;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 */
public class OverlayMediaPlayerController {

    private final boolean repeat = false;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;


    private MediaPlayer mediaPlayer;
    private Duration duration;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider timeSlider;

    @FXML
    private Label playTime;

    @FXML
    private Slider volumeSlider;

    @FXML
    private ToolBar bottomBar;

    private Transition transition = null;

    private MediaPlayerService playerService;

    public void initialize() {
        timeSlider.valueProperty().addListener(o -> {
            if (timeSlider.isValueChanging()) {
                // multiply duration by percentage calculated by slider position
                if (duration != null) {
                    mediaPlayer.seek(duration.multiply(timeSlider.getValue() / 100.0));
                }
                updateValues();

            }
        });

        volumeSlider.valueProperty().addListener((o, oldValue, newValue) -> {
            if (volumeSlider.isValueChanging()) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);
            }
        });




        playerService = KtPlayerAppKt.getInjector().getInstance(MediaPlayerService.class);

        mediaView.mediaPlayerProperty().bind(playerService.mediaPlayerProperty());
        mediaView.mediaPlayerProperty().addListener((observable, oldPlayer, newPlayer) -> {
            if (newPlayer != null) {
                mediaPlayer = newPlayer;
                initPlayer();
            }
        });
    }

    public void onDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles())
            event.acceptTransferModes(TransferMode.COPY);
        else
            event.consume();
    }

    public void onDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            File file = db.getFiles().get(0);

            playerService.setMediaFile(file);
//            if (path.getFileName().toString().endsWith(".java"))
        }
        event.setDropCompleted(success);
        event.consume();
    }

    public void initPlayer() {
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.currentTimeProperty().addListener((o, old, newValue) -> {
            updateValues();
        });
        mediaPlayer.setOnPlaying(() -> {
            if (stopRequested) {
                mediaPlayer.pause();
                stopRequested = false;
            }
        });
        mediaPlayer.setOnReady(() -> {
            duration = mediaPlayer.getMedia().getDuration();
            updateValues();
        });
        mediaPlayer.setOnEndOfMedia(() -> {
            if (!repeat) {
                stopRequested = true;
                atEndOfMedia = true;
            }
        });
        mediaPlayer.setCycleCount(repeat ? MediaPlayer.INDEFINITE : 1);
    }

    public void onMouseEntered() {
        if (transition != null) {
            transition.stop();
        }

        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(200), bottomBar);
        fadeTransition2.setToValue(1.0);
        fadeTransition2.setInterpolator(Interpolator.EASE_OUT);

        transition = new ParallelTransition( fadeTransition2);
        transition.play();
    }

    public void onMouseExited() {
        if (transition != null) {
            transition.stop();
        }

        FadeTransition fadeTransitionBottom = new FadeTransition(Duration.millis(800), bottomBar);
        fadeTransitionBottom.setToValue(0.0);
        fadeTransitionBottom.setInterpolator(Interpolator.EASE_OUT);

        transition = new ParallelTransition( fadeTransitionBottom);
        transition.play();
    }

    public void onBack() {
        mediaPlayer.seek(Duration.ZERO);
    }

    public void onStop() {
        mediaPlayer.stop();
    }

    public void onPlay() {
        mediaPlayer.play();
    }

    public void onPause() {
        mediaPlayer.pause();
    }

    public void onForward() {
        Duration currentTime = mediaPlayer.getCurrentTime();
        mediaPlayer.seek(Duration.seconds(currentTime.toSeconds() + 5.0));
    }

    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null && duration != null) {
            Platform.runLater(() -> {
                Duration currentTime = mediaPlayer.getCurrentTime();
                playTime.setText(formatTime(currentTime, duration));
                timeSlider.setDisable(duration.isUnknown());
                if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                    timeSlider.setValue(currentTime.divide(duration).toMillis() * 100.0);
                }
                if (!volumeSlider.isValueChanging()) {
                    volumeSlider.setValue((int) Math.round(mediaPlayer.getVolume() * 100));
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            if (durationHours > 0) {
                return String.format("%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",
                        elapsedMinutes, elapsedSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",
                        elapsedMinutes, elapsedSeconds);
            }
        }
    }
}
