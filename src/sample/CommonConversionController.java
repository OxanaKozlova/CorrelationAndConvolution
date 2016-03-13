package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Created by Oxana on 10.03.2016.
 */
public class CommonConversionController {
    @FXML
    public LineChart firstFunction;

    @FXML
    public LineChart secondFunction;

    @FXML
    public LineChart convolution;

    @FXML
    public LineChart correlation;

    public  void initialize() {
        Translation commonConversion = new Translation();
        firstFunction.getXAxis().setAutoRanging(true);
        firstFunction.getYAxis().setAutoRanging(true);
        XYChart.Series seriesFirstFunction = new XYChart.Series<>();
        seriesFirstFunction.setName("First Function");


        for(Integer i=0; i<16; i++){
            Double temp = 2*Math.PI/(16)*i;
            temp = new BigDecimal(temp).setScale(2, RoundingMode.UP).doubleValue();
            seriesFirstFunction.getData().add(new XYChart.Data<>(temp.toString(), commonConversion.getFirstFunction(i)));
        }
        firstFunction.getData().add(seriesFirstFunction);

        secondFunction.getXAxis().setAutoRanging(true);
        secondFunction.getYAxis().setAutoRanging(true);
        XYChart.Series seriesSecondFunction = new XYChart.Series<>();
        seriesSecondFunction.setName("Second Function");
        for(Integer i=0; i<16; i++){
            Double temp = 2*Math.PI/(16)*i;
            temp = new BigDecimal(temp).setScale(2, RoundingMode.UP).doubleValue();
            seriesSecondFunction.getData().add(new XYChart.Data<>(temp.toString(), commonConversion.getSecondFunction(i)));
        }
        secondFunction.getData().add(seriesSecondFunction);

        convolution.getXAxis().setAutoRanging(true);
        convolution.getYAxis().setAutoRanging(true);
        XYChart.Series seriesConvolution = new XYChart.Series<>();
        seriesConvolution.setName("Convolution");
        for(Integer i=0; i<16; i++){
            Double temp = 2*Math.PI/(16)*i;
            temp = new BigDecimal(temp).setScale(2, RoundingMode.UP).doubleValue();

            seriesConvolution.getData().add(new XYChart.Data<>(temp.toString(), commonConversion.getTransformation(-1).get(i)));
        }
        convolution.getData().add(seriesConvolution);


        correlation.getXAxis().setAutoRanging(true);
        correlation.getYAxis().setAutoRanging(true);
        XYChart.Series seriesCorrelation = new XYChart.Series<>();
        seriesCorrelation.setName("Correlation");
        for(Integer i=0; i<16; i++){
            Double temp = 2*Math.PI/(16)*i;
            temp = new BigDecimal(temp).setScale(2, RoundingMode.UP).doubleValue();

            seriesCorrelation.getData().add(new XYChart.Data<>(temp.toString(), commonConversion.getTransformation(1).get(i)));
        }
        correlation.getData().add(seriesCorrelation);



    }

    public void changeScene(ActionEvent event)throws IOException {
        Parent original = FXMLLoader.load(getClass().getResource("fftConversion.fxml"));
        Scene originalScene = new Scene(original);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(originalScene);
        stage.show();
    }
}
