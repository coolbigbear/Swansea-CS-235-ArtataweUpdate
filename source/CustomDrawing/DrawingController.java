package CustomDrawing;


import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.io.File;

public class DrawingController {

    @FXML
    private Canvas canvas;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private TextField brushSize;

    @FXML
    private CheckBox ovalPaint;

    public void onSave(){
        try{
            Image snapshot = canvas.snapshot(null, null);
            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", new File
                    ("Images\\ProfileImage.png"));
        }catch(Exception e){
            System.out.println("Cannot save file!" + e);
        }

    }

    public void onClear(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
    }

    public void initialize(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(event -> {
            double size = Double.parseDouble(brushSize.getText());
            double x = event.getX();
            double y = event.getY();
            gc.setFill(colorPicker.getValue());
            if(ovalPaint.isSelected()) {
                gc.fillOval(x, y, size, size);
            }else{
                gc.fillRect(x, y, size, size);
            }
        });
    }

}
