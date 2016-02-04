package com.almasb.ktp

import com.google.inject.AbstractModule

class KtModule : AbstractModule() {
    override fun configure() {
        bind(MediaPlayerService::class.java).to(KtMediaPlayer::class.java)
    }
}
