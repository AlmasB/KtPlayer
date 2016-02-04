package com.almasb.ktp

import com.google.inject.Guice
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class KtPlayerApp : Application() {

    override fun start(stage: Stage) {
        stage.scene = Scene(FXMLLoader.load(javaClass.getResource("main.fxml")))
        stage.show()
    }
}

val injector = Guice.createInjector(KtModule())

fun main(args: Array<String>) {
    Application.launch(KtPlayerApp::class.java)
}