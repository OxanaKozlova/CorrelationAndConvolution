package sample;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Created by Oxana on 10.03.2016.
 */
public class Translation {
    private int N = 16;
    private int countOperationFFT = 0;
    private int countOperationTranslation = 0;
    int t = 0;



    public Double getFirstFunction(int h){
        Double value = Math.cos(2*Math.PI*h/N);
        value = new BigDecimal(value).setScale(2, RoundingMode.UP).doubleValue();
       return value;

    }

    public Double getSecondFunction(int h){
        Double value = Math.sin(2*Math.PI*h/N);
        value = new BigDecimal(value).setScale(2, RoundingMode.UP).doubleValue();
        return value;

    }


    public ArrayList<Double> getTransformation(int sing){
        ArrayList<Double> arrayCoeff = new ArrayList<>();
        Double coeff = 0.0;
        for(int m = 0; m < N; m++){
            for(int i = 0; i < N; i++){
                coeff += getFirstFunction(i) * getSecondFunction(m + i * sing);
                coeff = new BigDecimal(coeff).setScale(2, RoundingMode.UP).doubleValue();
                countOperationTranslation++;
            }
            coeff = coeff/this.N;
            coeff = new BigDecimal(coeff).setScale(2, RoundingMode.UP).doubleValue();
            arrayCoeff.add(coeff);
        }
        return arrayCoeff;
    }

    public ArrayList<Complex> getComplexFirstFunction(){
        ArrayList<Complex> array = new ArrayList<>();
        for(int i = 0; i < N; i++){
            array.add(new Complex(Math.cos(2*Math.PI*i/16),0));
        }
        return array ;
    }

    public ArrayList<Complex> getComplexSecondFunction(){
        ArrayList<Complex> array = new ArrayList<>();
        for(int i = 0; i<16; i++){
            array.add(new Complex(Math.sin(2*Math.PI*i/16),0));
        }
        return array ;
    }

    public ArrayList<Complex> getFFTConvolution(){
        ArrayList<Complex> arrayCoeff = new ArrayList<>();
        ArrayList<Complex> firstFFT = fft(getComplexFirstFunction(), 1);
        ArrayList<Complex> secondFFT = fft(getComplexSecondFunction(),1);
        for(int i = 0; i< N; i++){
            firstFFT.set(i,firstFFT.get(i).divides(new Complex(16, 0)));
            secondFFT.set(i, secondFFT.get(i).divides(new Complex(16,0)));
        }
        Complex coeff ;
        for(int i = 0; i < N; i++){
            coeff = firstFFT.get(i).times(secondFFT.get(i));
            arrayCoeff.add(coeff);
            countOperationFFT++;
        }
        ArrayList<Complex> reverseFFT = fft(arrayCoeff, -1);
        for(int i = 0; i < N; i++){
            reverseFFT.set(i, reverseFFT.get(i));
        }
        return reverseFFT;
    }


    public ArrayList<Complex> getFFTCorrelation(){
        ArrayList<Complex> arrayCoeff = new ArrayList<>();
        ArrayList<Complex> firstFFT = fft(getComplexFirstFunction(), 1);
        ArrayList<Complex> secondFFT = fft(getComplexSecondFunction(),1);
        for(int i = 0; i< N; i++){
            firstFFT.set(i,firstFFT.get(i).divides(new Complex(16, 0)));
            secondFFT.set(i, secondFFT.get(i).divides(new Complex(16,0)));
        }
        Complex coeff ;
        Complex conjugate;
        for(int i = 0; i < N; i++){
            conjugate = firstFFT.get(i).conjugate();
            coeff = secondFFT.get(i).times(conjugate);
            arrayCoeff.add(coeff);
        }
        ArrayList<Complex> reverseFFT = fft(arrayCoeff, -1);
        for(int i = 0; i < N; i++){
            reverseFFT.set(i, reverseFFT.get(i));
        }
        return reverseFFT;
    }

    public ArrayList<Complex> fft(ArrayList<Complex> variables, int dir ) {
        if (variables.size() == 1) {
            ArrayList<Complex> temp = new ArrayList<>();
            temp.add(variables.get(0));
            return temp;
        }
        ArrayList< Complex> even = new ArrayList<>();
        ArrayList<Complex> odd = new ArrayList<>();
        for (int i = 0; i < (variables.size() / 2); i++) {
            even.add( variables.get(2*i));
            odd.add(variables.get(2 * i + 1));
        }
        ArrayList<Complex> beven = fft(even, dir);
        ArrayList<Complex> bodd = fft(odd, dir);
        int n = variables.size();
        Complex wn = new Complex(Math.cos(2 * Math.PI / n), dir * Math.sin(2 * Math.PI / n));

        ArrayList<Complex> result = new ArrayList<Complex>(n);
        for(int i = 0; i<n; i++){
            result.add(new Complex(0,0));
        }
        Complex w = new Complex(1, 0);
        for (int i = 0; i < n / 2; i++) {
            Complex tempValue = new Complex (bodd.get(i).times(w).re(), bodd.get(i).times(w).im());
            result.set(i,beven.get(i).plus(tempValue));
            result.set((i+n/2), beven.get(i).minus(tempValue));
            w = w.times(wn);
        }
        return result;
    }
    public int getCountOperationFFT(){
        return this.countOperationFFT;
    }

    public int getCountOperationTranslation(){
        return this.countOperationTranslation;
    }
}
