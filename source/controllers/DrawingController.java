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
    private RadioMenuItem drawLine;

    private double xBegin;
    private double xEnd;
    private double yBegin;
    private double yEnd;

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
        Stage stage = (Stage) colorPicker.getScene().getWindow();
        stage.setResizable(false);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(event -> {
            if (freeDraw.isSelected()) {
                double size = Double.parseDouble(brushSize.getText());
                double x = event.getX();
                double y = event.getY();
                gc.setFill(colorPicker.getValue());
                gc.fillOval(x, y, size / 2, size / 2);
            }
        });
        canvas.setOnMousePressed(event -> {
            xBegin = event.getX();
            yBegin = event.getY();
        });
        canvas.setOnMouseReleased(event -> {
            xEnd = event.getX();
            yEnd = event.getY();
            if(drawLine.isSelected()) {
                gc.setFill(colorPicker.getValue());
                gc.setLineWidth(Double.parseDouble(brushSize.getText()));
                gc.strokeLine(xBegin, yBegin, xEnd, yEnd);
            }
            if(drawRectangle.isSelected()){
                double size = Double.parseDouble((brushSize.getText()));
                gc.setFill(colorPicker.getValue());
                gc.strokeRect(xBegin, yBegin, size, size );
            }
        });
    }


    private String generateNameAndSetLocation() {
        String generatedString = UUID.randomUUID().toString();
        return "source/images/custom/" + generatedString + ".png";
    }


}
