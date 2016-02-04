package com.almasb.ktp

import com.google.inject.Guice
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class KtPlayerApp : Application() {

    override fun start(stage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("main.fxml"))

        stage.scene = Scene(loader.load())
        stage.setOnCloseRequest {
            it.consume()
            loader.getController<MainController>().onExit()
        }
        stage.show()
    }
}

val injector = Guice.createInjector(KtModule())

fun main(args: Array<String>) {
    Application.launch(KtPlayerApp::class.java)
}