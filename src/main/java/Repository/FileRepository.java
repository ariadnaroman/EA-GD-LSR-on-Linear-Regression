package Repository;

import EA.Individual;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRepository {
    private Double[][] trainInData;
    private Double[] trainOutData;
    private int n;
    private int m;
    private String filename;

    public FileRepository(String filename) {
        this.filename = filename;
        readFromFile();
    }

    private void readFromFile() {
        String line;
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            line = bufferedReader.readLine();
            if (line != null) {
                n = Integer.parseInt(line.trim());
            }
            line = bufferedReader.readLine();
            if (line != null) {
                m = Integer.parseInt(line.trim());
            }

            trainInData = new Double[n][m];
            trainOutData = new Double[n];

            for (int i=0;i<n;i++) {
                line = bufferedReader.readLine();
                String elems[] = line.split(",");
                for (int j=0;j<m;j++) {
                    double value = Double.parseDouble(elems[j]);
                    if (j != m - 1) {
                        trainInData[i][j] = value;
                    } else {
                        trainOutData[i] = value;
                    }
                }
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + filename + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + filename + "'");
        }
    }

    public Double[][] getTrainInData() {
        return trainInData;
    }

    public void setTrainInData(Double[][] trainInData) {
        this.trainInData = trainInData;
    }

    public Double[] getTrainOutData() {
        return trainOutData;
    }

    public void setTrainOutData(Double[] trainOutData) {
        this.trainOutData = trainOutData;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }
}
