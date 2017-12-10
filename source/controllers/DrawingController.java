package controllers;


import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Util;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
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

    private double xbegin, xend, ybegin, yend;

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
            }
        });
        canvas.setOnMousePressed(event -> {
            xbegin = event.getX();
            ybegin = event.getY();
        });
        canvas.setOnMouseReleased(event -> {
            xend = event.getX();
            yend = event.getY();
            if(drawLine.isSelected()) {
                gc.setLineWidth(Double.parseDouble(brushSize.getText()));
                gc.strokeLine(xbegin, ybegin, xend, yend);
            }
            if(drawRectangle.isSelected()){
                double size = Double.parseDouble((brushSize.getText()));
                gc.strokeRect(xbegin,ybegin, size, size );
            }
        });
    }


    private String generateNameAndSetLocation() {
        String generatedString = UUID.randomUUID().toString();
        return "source/images/custom/" + generatedString + ".png";
    }


}
