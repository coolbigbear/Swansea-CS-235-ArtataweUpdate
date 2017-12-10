package controllers;


import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Util;

import javax.imageio.ImageIO;
import java.io.File;
import java.util.UUID;

public class DrawingController {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private RadioMenuItem freeDraw;

    @FXML
    private RadioMenuItem drawRectangle;

    @FXML
    private RadioMenuItem drawCircle;

    @FXML
    private RadioMenuItem drawLine;

    private String customPath;
    public void onSave() {
        try {
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File
                    (generateNameAndSetLocation()));

            Stage stage = (Stage) colorPicker.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            System.out.println("Cannot save file!" + e);
        }
    }

    public void onClear() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void initialize() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        canvas.setOnMouseDragged(event -> {
            if (freeDraw.isSelected()) {
                double size = Double.parseDouble(brushSize.getText());
                double x = event.getX();
                double y = event.getY();
                gc.setFill(colorPicker.getValue());
                gc.fillOval(x, y, size / 2, size / 2);
            } else if (drawLine.isSelected()) { //TODO FIX LINE
                double x = event.getX();
                double y = event.getY();
                gc.setFill(colorPicker.getValue());
                gc.fillRect(x,y,30,30);
                double x1 = event.getX();
                double y1 = event.getY();
                gc.lineTo(x1,y1);
            }
        });
        canvas.setOnMouseClicked(event -> {
            if (drawRectangle.isSelected()) {
                double x = event.getX();
                double y = event.getY();
                gc.setFill(colorPicker.getValue());
                gc.fillRect(x, y, 40, 40);
            } else if (drawCircle.isSelected()) {
                double x = event.getX();
                double y = event.getY();
                gc.setFill(colorPicker.getValue());
                gc.fillOval(x, y, 40, 40);
            }
        });
    }

    private String generateNameAndSetLocation() {
        String generatedString = UUID.randomUUID().toString();
        customPath = "images/custom/" + generatedString + ".png";
        return "source/images/custom/" + generatedString + ".png";
    }

}
