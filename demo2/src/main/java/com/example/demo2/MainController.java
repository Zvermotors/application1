package com.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * Контроллер для управления интерфейсом приложения
 * Обрабатывает пользовательский ввод и управляет логикой отображения
 */
public class MainController {

    // Поля для координат вершин треугольника
    @FXML private TextField txtX1;
    @FXML private TextField txtY1;
    @FXML private TextField txtX2;
    @FXML private TextField txtY2;
    @FXML private TextField txtX3;
    @FXML private TextField txtY3;

    // Поля для результатов вычислений
    @FXML private TextField txtPerimeter;
    @FXML private TextField txtArea;

    // Слайдеры для дублирования ввода координат
    @FXML private Slider sliderX1;
    @FXML private Slider sliderY1;
    @FXML private Slider sliderX2;
    @FXML private Slider sliderY2;
    @FXML private Slider sliderX3;
    @FXML private Slider sliderY3;

    // Кнопки управления
    @FXML private Button btnCalculate;
    @FXML private Button btnReset;
    @FXML private Button btnExit;

    private boolean initializing = true;

    /**
     * Инициализация контроллера
     * Настраивает начальные значения слайдеров и обработчики событий
     */
    @FXML
    public void initialize() {
        // Настройка диапазонов слайдеров
        setupSliders();

        // Установка начальных значений в текстовые поля
        setInitialTextValues();

        // Синхронизация слайдеров с текстовыми полями
        setupSliderListeners();

        initializing = false;
    }

    /**
     * Устанавливает начальные значения в текстовые поля
     */
    private void setInitialTextValues() {
        txtX1.setText("0.0");
        txtY1.setText("0.0");
        txtX2.setText("0.0");
        txtY2.setText("0.0");
        txtX3.setText("0.0");
        txtY3.setText("0.0");
        txtPerimeter.setText("");
        txtArea.setText("");
    }

    /**
     * Настраивает диапазоны значений для слайдеров
     */
    private void setupSliders() {
        Slider[] sliders = {sliderX1, sliderY1, sliderX2, sliderY2, sliderX3, sliderY3};
        for (Slider slider : sliders) {
            slider.setMin(-100);
            slider.setMax(100);
            slider.setValue(0);
            slider.setShowTickLabels(true);
            slider.setShowTickMarks(true);
            slider.setMajorTickUnit(50);
            slider.setMinorTickCount(5);
            slider.setSnapToTicks(false); // Разрешаем плавное перемещение
        }
    }

    /**
     * Настраивает слушателей для синхронизации слайдеров и текстовых полей
     */
    private void setupSliderListeners() {
        setupSliderTextFieldSync(sliderX1, txtX1);
        setupSliderTextFieldSync(sliderY1, txtY1);
        setupSliderTextFieldSync(sliderX2, txtX2);
        setupSliderTextFieldSync(sliderY2, txtY2);
        setupSliderTextFieldSync(sliderX3, txtX3);
        setupSliderTextFieldSync(sliderY3, txtY3);
    }

    /**
     * Связывает слайдер и текстовое поле для двусторонней синхронизации
     * @param slider слайдер для связывания
     * @param textField текстовое поле для связывания
     */
    private void setupSliderTextFieldSync(Slider slider, TextField textField) {
        // Обновление текстового поля при изменении слайдера
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (!initializing) {
                textField.setText(String.format("%.2f", newVal.doubleValue()));
            }
        });

        // Обновление слайдера при изменении текстового поля
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!initializing && newVal != null && !newVal.trim().isEmpty()) {
                try {
                    // Используем Double.parseDouble вместо Integer.parseInt
                    double value = Double.parseDouble(newVal.trim().replace(',', '.'));
                    // Ограничиваем значение диапазоном слайдера
                    value = Math.max(slider.getMin(), Math.min(slider.getMax(), value));
                    slider.setValue(value);
                } catch (NumberFormatException e) {
                    // Не показываем ошибку при вводе, только при расчете
                }
            }
        });
    }

    /**
     * Обрабатывает событие расчета периметра и площади
     */
    @FXML
    private void handleCalculate() {
        try {
            // Получение координат из текстовых полей
            double x1 = parseCoordinate(txtX1.getText(), "X1");
            double y1 = parseCoordinate(txtY1.getText(), "Y1");
            double x2 = parseCoordinate(txtX2.getText(), "X2");
            double y2 = parseCoordinate(txtY2.getText(), "Y2");
            double x3 = parseCoordinate(txtX3.getText(), "X3");
            double y3 = parseCoordinate(txtY3.getText(), "Y3");

            // Проверка, образуют ли точки треугольник
            if (!TriangleCalculator.isTriangle(x1, y1, x2, y2, x3, y3)) {
                showErrorAlert("Ошибка", "Точки лежат на одной прямой и не образуют треугольник");
                return;
            }

            // Вычисление периметра и площади
            double perimeter = TriangleCalculator.calculatePerimeter(x1, y1, x2, y2, x3, y3);
            double area = TriangleCalculator.calculateArea(x1, y1, x2, y2, x3, y3);

            // Отображение результатов
            txtPerimeter.setText(String.format("%.4f", perimeter));
            txtArea.setText(String.format("%.4f", area));

        } catch (NumberFormatException e) {
            // Ошибка уже обработана в parseCoordinate
        }
    }

    /**
     * Парсит координату из строки с проверкой ошибок
     * @param value строковое значение координаты
     * @param coordinateName название координаты для сообщения об ошибке
     * @return числовое значение координаты
     * @throws NumberFormatException если значение не является числом
     */
    private double parseCoordinate(String value, String coordinateName) throws NumberFormatException {
        if (value == null || value.trim().isEmpty()) {
            showErrorAlert("Ошибка ввода", String.format("Координата %s не заполнена", coordinateName));
            throw new NumberFormatException("Empty coordinate");
        }

        try {
            // Заменяем запятую на точку для корректного парсинга
            String normalizedValue = value.trim().replace(',', '.');
            return Double.parseDouble(normalizedValue);
        } catch (NumberFormatException e) {
            showErrorAlert("Ошибка формата", String.format("Координата %s должна быть числом (можно использовать десятичную точку или запятую)", coordinateName));
            throw e;
        }
    }

    /**
     * Обрабатывает событие сброса всех значений
     */
    @FXML
    private void handleReset() {
        // Временно устанавливаем флаг инициализации
        initializing = true;

        // Очистка текстовых полей и установка нулевых значений
        txtX1.setText("0.0");
        txtY1.setText("0.0");
        txtX2.setText("0.0");
        txtY2.setText("0.0");
        txtX3.setText("0.0");
        txtY3.setText("0.0");
        txtPerimeter.setText("");
        txtArea.setText("");

        // Сброс слайдеров к нулевым значениям
        Slider[] sliders = {sliderX1, sliderY1, sliderX2, sliderY2, sliderX3, sliderY3};
        for (Slider slider : sliders) {
            slider.setValue(0);
        }

        // Снимаем флаг инициализации
        initializing = false;
    }

    /**
     * Обрабатывает событие выхода из приложения
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

    /**
     * Показывает диалоговое окно с ошибкой
     * @param title заголовок окна
     * @param message сообщение об ошибке
     */
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Обработчик движения слайдера X1
     */
    @FXML
    private void handleSliderX1(MouseEvent event) {
        if (!initializing) {
            txtX1.setText(String.format("%.2f", sliderX1.getValue()));
        }
    }

    /**
     * Обработчик движения слайдера Y1
     */
    @FXML
    private void handleSliderY1(MouseEvent event) {
        if (!initializing) {
            txtY1.setText(String.format("%.2f", sliderY1.getValue()));
        }
    }

    /**
     * Обработчик движения слайдера X2
     */
    @FXML
    private void handleSliderX2(MouseEvent event) {
        if (!initializing) {
            txtX2.setText(String.format("%.2f", sliderX2.getValue()));
        }
    }

    /**
     * Обработчик движения слайдера Y2
     */
    @FXML
    private void handleSliderY2(MouseEvent event) {
        if (!initializing) {
            txtY2.setText(String.format("%.2f", sliderY2.getValue()));
        }
    }

    /**
     * Обработчик движения слайдера X3
     */
    @FXML
    private void handleSliderX3(MouseEvent event) {
        if (!initializing) {
            txtX3.setText(String.format("%.2f", sliderX3.getValue()));
        }
    }

    /**
     * Обработчик движения слайдера Y3
     */
    @FXML
    private void handleSliderY3(MouseEvent event) {
        if (!initializing) {
            txtY3.setText(String.format("%.2f", sliderY3.getValue()));
        }
    }
}