package UI;

import EA.EvolutionaryAlgorithm;
import GradientDescent.GradientDescent;
import LeastSquareRoot.LeastSquareRoot;
import Repository.FileRepository;

import java.util.Scanner;

public class UI {
    private FileRepository fileRepository;
    private Double[][] trainInData;
    private Double[] trainOutData;
    private int n;
    private int m;
    private Double averageError;

    public UI() {
        this.fileRepository = new FileRepository("zileInsorite.csv");
        this.trainInData = fileRepository.getTrainInData();
        this.trainOutData = fileRepository.getTrainOutData();
        this.n = fileRepository.getN();
        this.m = fileRepository.getM()-1;
    }

    public void show() {
        while (true){
            System.out.println("Choose one of the methods: EA/GD/LSR");
            Scanner scanner = new Scanner(System.in);
            String method = scanner.next();
            switch (method) {
                case "EA":
                    EvolutionaryAlgorithm evolutionaryAlgorithm = new EvolutionaryAlgorithm(trainInData,trainOutData,n,m);
                    System.out.println("Insert the number of generations: ");
                    int generationsNo = scanner.nextInt();
                    evolutionaryAlgorithm.setNrGenerations(generationsNo);
                    System.out.println("Insert the size of the population: ");
                    int popSize = scanner.nextInt();
                    evolutionaryAlgorithm.setPopSize(popSize);
                    averageError = evolutionaryAlgorithm.test();
                    System.out.println("The average error for this method is: " + averageError);
                    break;
                case "GD":
                    GradientDescent gradientDescent = new GradientDescent(trainInData,trainOutData,n,m);
                    System.out.println("Insert the number of epochs: ");
                    int epochsNo = scanner.nextInt();
                    gradientDescent.setNoEpoch(epochsNo);
                    System.out.println("Insert the learning rate: ");
                    Double learningRate = scanner.nextDouble();
                    gradientDescent.setLearningRate(learningRate);
                    averageError = gradientDescent.test();
                    System.out.println("The average error for this method is: " + averageError);
                    break;
                case "LSR":
                    LeastSquareRoot leastSquareRoot = new LeastSquareRoot(trainInData,trainOutData,n,m);
                    try {
                        averageError = leastSquareRoot.test();
                        System.out.println("The average error for this method is: " + averageError);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
            }
        }
    }
}
