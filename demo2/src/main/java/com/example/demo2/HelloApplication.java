package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Главный класс приложения, запускающий JavaFX приложение
 */
public class HelloApplication extends Application {

    /**
     * Метод start - точка входа для JavaFX приложения
     * @param stage главное окно приложения
     * @throws IOException если файл FXML не найден
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Загрузка FXML файла с интерфейсом
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        // Создание сцены с заданными размерами
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        // Добавление CSS стилей
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        // Настройка главного окна
        stage.setTitle("Расчет треугольника по координатам - Zverev A.D.");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Главный метод, запускающий приложение
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        launch(args);
    }
}