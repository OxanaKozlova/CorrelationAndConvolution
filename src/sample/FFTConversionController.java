package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Oxana on 10.03.2016.
 */
public class FFTConversionController {
    @FXML
    public LineChart firstFunction;

    @FXML
    public LineChart secondFunction;

    @FXML
    public LineChart convolution;

    @FXML
    public LineChart correlation;


    @FXML
    public Label operations;

    public  void initialize() {
        Translation fftConversion = new Translation();
        firstFunction.getXAxis().setAutoRanging(true);
        firstFunction.getYAxis().setAutoRanging(true);
        XYChart.Series seriesFirstFunction = new XYChart.Series<>();
        seriesFirstFunction.setName("First Function");


        for(Integer i=0; i<16; i++){
            Double temp = 2*Math.PI/(16)*i;
            temp = new BigDecimal(temp).setScale(2, RoundingMode.UP).doubleValue();
            seriesFirstFunction.getData().add(new XYChart.Data<>(temp.toString(), fftConversion.getFirstFunction(i)));
        }
        firstFunction.getData().add(seriesFirstFunction);

        secondFunction.getXAxis().setAutoRanging(true);
        secondFunction.getYAxis().setAutoRanging(true);
        XYChart.Series seriesSecondFunction = new XYChart.Series<>();
        seriesSecondFunction.setName("Second Function");
        for(Integer i=0; i<16; i++){
            Double temp = 2*Math.PI/(16)*i;
            temp = new BigDecimal(temp).setScale(2, RoundingMode.UP).doubleValue();
            seriesSecondFunction.getData().add(new XYChart.Data<>(temp.toString(), fftConversion.getSecondFunction(i)));
        }
        secondFunction.getData().add(seriesSecondFunction);

        convolution.getXAxis().setAutoRanging(true);
        convolution.getYAxis().setAutoRanging(true);
        XYChart.Series seriesConvolution = new XYChart.Series<>();
        seriesConvolution.setName("Convolution");
        ArrayList<Complex> convolitionArray = fftConversion.getFFTConvolution();
        for(Integer i=0; i<16; i++){
            Double temp = 2*Math.PI/(16)*i;
            temp = new BigDecimal(temp).setScale(2, RoundingMode.UP).doubleValue();

            seriesConvolution.getData().add(new XYChart.Data<>(temp.toString(), convolitionArray.get(i).re()));
        }
        convolution.getData().add(seriesConvolution);


        correlation.getXAxis().setAutoRanging(true);
        correlation.getYAxis().setAutoRanging(true);
        XYChart.Series seriesCorrelation = new XYChart.Series<>();
        seriesCorrelation.setName("Correlation");
        ArrayList<Complex> correlationArray = fftConversion.getFFTCorrelation();
        for(Integer i=0; i<16; i++){
            Double temp = 2*Math.PI/(16)*i;
            temp = new BigDecimal(temp).setScale(2, RoundingMode.UP).doubleValue();

            seriesCorrelation.getData().add(new XYChart.Data<>(temp.toString(), correlationArray.get(i).re()));
        }
        correlation.getData().add(seriesCorrelation);
        operations.setText(operations.getText() + " " +  fftConversion.getCountOperationFFT());


    }

    public void changeScene(ActionEvent event)throws IOException {
        Parent original = FXMLLoader.load(getClass().getResource("commonConversion.fxml"));
        Scene originalScene = new Scene(original);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.hide();
        stage.setScene(originalScene);
        stage.show();
    }
}
