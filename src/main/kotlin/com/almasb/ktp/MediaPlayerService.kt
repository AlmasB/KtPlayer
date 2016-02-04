package com.almasb.ktp

import javafx.beans.property.ObjectProperty
import javafx.scene.media.MediaPlayer
import java.io.File

interface MediaPlayerService {

    fun mediaPlayerProperty(): ObjectProperty<MediaPlayer>

    fun getMediaPlayer(): MediaPlayer? = mediaPlayerProperty().get()

    fun setMediaFile(file: File)

    fun dispose()
}
