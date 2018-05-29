package GradientDescent;

import java.util.List;

public class GradientDescent {
    private Double[][] trainInData;
    private Double[][] testInData;
    private Double[] trainOutData;
    private Double[] testOutData;
    private int indexTestStart;
    private int n;
    private int m;
    private Double learningRate = 0.05;
    private int noEpoch = 2;

    public void setLearningRate(Double learningRate) {
        this.learningRate = learningRate;
    }

    public void setNoEpoch(int noEpoch) {
        this.noEpoch = noEpoch;
    }

    public GradientDescent(Double[][] trainInData, Double[] trainOutData, int n, int m) {
        this.indexTestStart = n*80/100;
        this.n=n;
        this.m=m;
        this.trainInData = new Double[indexTestStart][m];
        this.trainOutData = new Double[indexTestStart];
        this.testInData = new Double[n-indexTestStart][m];
        this.testOutData = new Double[n-indexTestStart];
        for (int i=0;i<indexTestStart;i++) {
            for (int j=0;j<m;j++) {
                this.trainInData[i][j] = trainInData[i][j];
            }
            this.trainOutData[i] = trainOutData[i];
        }
        for (int i=indexTestStart;i<n;i++) {
            for (int j=0;j<m;j++) {
                this.testInData[i-indexTestStart][j] = trainInData[i][j];
            }
            this.testOutData[i-indexTestStart]=trainOutData[i];
        }
    }

    public Double predict(Double[] data, Double[] coef) {
        Double s = 0.0;
        for (int i=0;i<data.length-1;i++) {
            s += coef[i]*data[i];
        }
        s += coef[data.length];
        return s;
    }

    public Double[] start() {
        Double[] coef = new Double[m + 1];
        for (int i=0;i<m+1;i++) {
            coef[i]=0.0;
        }
        for (int epoch=0;epoch<noEpoch;epoch++) {
            Double sumError = 0.0;
            for (int i=0;i<indexTestStart;i++) {
                Double out = predict(trainInData[i],coef);
                Double crtError = out - trainOutData[i];
                crtError *= 0.001;
                sumError += crtError*crtError;
                for (int j=0;j<m;j++) {
                    Double calc =  learningRate*crtError*trainInData[i][j];
                    coef[j] = coef[j] -calc;
                }
                coef[m] = coef[m]-learningRate*crtError;
            }
        }
        return coef;
    }

    public Double test() {
        Double[] coef = start();
        Double[] errors = new Double[testInData.length];
        for (int i=0;i<testInData.length;i++) {
            Double result = 0.0;
            for (int j=0;j<m;j++) {
                result += testInData[i][j]*coef[j];
            }
            errors[i] = Math.abs(result-testOutData[i]);
        }
        Double sumError = 0.0;
        for(int i=0;i<testInData.length;i++) {
            sumError += errors[i];
        }
        Double averageError = sumError/testInData.length;
        return averageError;
    }
}
