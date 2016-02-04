package com.almasb.ktp

import com.google.inject.Singleton
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import java.io.File

@Singleton
class KtMediaPlayer : MediaPlayerService {

    private val player = SimpleObjectProperty<MediaPlayer>()

    override fun mediaPlayerProperty(): ObjectProperty<MediaPlayer> {
        return player
    }

    override fun setMediaFile(file: File) {
        val oldPlayer = getMediaPlayer()

        val source = file.toURI().toURL().toExternalForm()
        player.set(MediaPlayer(Media(source)))

        oldPlayer?.stop()
        oldPlayer?.dispose()
    }

    override fun dispose() {
        val oldPlayer = getMediaPlayer()

        player.set(null)

        oldPlayer?.stop()
        oldPlayer?.dispose()
    }
}
