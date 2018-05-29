package LeastSquareRoot;

import Jama.Matrix;

public class LeastSquareRoot {
    private Double[][] trainInData;
    private Double[][] testInData;
    private Double[] trainOutData;
    private Double[] testOutData;
    private int indexTestStart;
    private int n;
    private int m;

    public LeastSquareRoot(Double[][] trainInData, Double[] trainOutData, int n, int m) {
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

    public Double[] start() throws Exception {
        double[][] values = new double[n][m];
        for (int i=0;i<indexTestStart;i++) {
            for (int j=0;j<m;j++)
                values[i][j] = trainInData[i][j];
        }

        double[] values2 = new double[n];
        for(int i=0;i<indexTestStart;i++) {
            values2[i] = trainOutData[i];
        }

        Matrix X = new Matrix(values);
        Matrix Y = new Matrix(values2,n);
        Matrix transpose = X.transpose();
        Matrix multiplication = transpose.times(X);
        if (multiplication.det()==0) {
            throw new Exception("The matrix has the determinant 0.");
        }
        Matrix reverse = multiplication.inverse();
        Matrix multiplication2 = reverse.times(transpose);
        Matrix multiplication3 = multiplication2.times(Y);

        double[] values3 = multiplication3.getColumnPackedCopy();
        Double[] coefs = new Double[values3.length];
        for (int i=0;i<values3.length;i++)
            coefs[i] = values3[i];

        return coefs;
    }

    public Double test() throws Exception {
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
