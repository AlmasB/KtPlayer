package com.almasb.ktp

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import javafx.stage.Stage

class KtPlayerApp : Application() {

//    private val overlayMediaPlayerCss = KtPlayerApp::class.java.getResource("OverlayMediaPlayer.css").toExternalForm()
//    private val MEDIA_URL = "http://download.oracle.com/otndocs/javafx/JavaRap_ProRes_H264_768kbit_Widescreen.mp4"
//    private var mediaPlayer: MediaPlayer? = null
//    internal val mediaWidth = 480.0
//    internal val mediaHeight = 270.0
//
//    fun createContent(): Parent {
//        mediaPlayer = MediaPlayer(Media(MEDIA_URL))
//        mediaPlayer!!.isAutoPlay = true
//        val playerPane = PlayerPane(mediaPlayer)
//        playerPane.setMinSize(mediaWidth, mediaHeight)
//        playerPane.setPrefSize(mediaWidth, mediaHeight)
//        playerPane.setMaxSize(mediaWidth, mediaHeight)
//        playerPane.stylesheets.add(overlayMediaPlayerCss)
//
//        return playerPane
//    }
//
//    fun play() {
//        val status = mediaPlayer!!.status
//        if (status == MediaPlayer.Status.UNKNOWN || status == MediaPlayer.Status.HALTED) {
//            return
//        }
//        if (status == MediaPlayer.Status.PAUSED || status == MediaPlayer.Status.STOPPED || status == MediaPlayer.Status.READY) {
//            mediaPlayer!!.play()
//        }
//    }
//
//    override fun stop() {
//        //mediaPlayer!!.stop()
//    }

    override fun start(stage: Stage) {
        //stage.scene = Scene(createContent())
        stage.scene = Scene(FXMLLoader.load(javaClass.getResource("main.fxml")))
        stage.show()

        //play()
    }
}

fun main(args: Array<String>) {
    Application.launch(KtPlayerApp::class.java)
}