package EA;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EvolutionaryAlgorithm  {
    private int nrGenerations = 5;
    private int popSize = 50;
    private int genesNo = 386;
    private Population population;
    private Double[][] trainInData;
    private Double[][] testInData;
    private Double[] trainOutData;
    private Double[] testOutData;
    private int indexTestStart;
    private Individual fittest;
    int n;
    int m;

    private Random random = new Random();

    public EvolutionaryAlgorithm(Double[][] trainInData, Double[] trainOutData,int n,int m) {
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

    public void init() {
        population = new Population();

        for (int i=0;i<popSize;i++) {
            List<Double> coefs = new ArrayList<>();
            for (int j=0;j<genesNo;j++) {
                Double coef = random.nextDouble()*2;
                coefs.add(coef);
            }
            Individual individual = new Individual(coefs);
            if (i==0) {
                fittest = individual;
            }
            population.addIndividual(individual);
        }
    }

    public void eval() {
        for (Individual individual : population.getIndividuals()) {
            individual.evaluate(trainInData,trainOutData);
        }
    }

    public Individual selection() {
        Individual individual1 = population.getIndividual(Math.abs(random.nextInt())%popSize);
        Individual individual2 = population.getIndividual(Math.abs(random.nextInt())%popSize);
        if ( individual1.getFitness() < individual2.getFitness() )
            return  individual1;
        else
            return individual2;
    }

    public Individual findFittest() {
        init();
        eval();
        for (int i=0;i<nrGenerations;i++) {
            Population aux = new Population();
            for (int j=0;j<popSize;j++) {
                Individual M = selection();
                Individual F = selection();
                Individual offspring = crossover(M,F);
                mutateIndividual(offspring);
                aux.addIndividual(offspring);
            }
            population = aux;
            eval();
            Individual popFittest = population.getFittest();
            if (popFittest.getFitness()>fittest.getFitness())
                fittest = popFittest;
        }
        return fittest;
    }

    private void mutateIndividual(Individual individual) {
        for (int i=0;i<genesNo/10;i++) {
            int pos = Math.abs(random.nextInt())%genesNo;
            individual.getGenotype().set(pos,random.nextDouble()*2);
        }
    }

    private Individual crossover(Individual M, Individual F) {
        Individual offspring = new Individual();
        List<Double> gen = new ArrayList<>();
        for (int i=0;i<M.getGenotype().size();i++) {
            gen.add((M.getGenotype().get(i)+F.getGenotype().get(i))/2.0);
        }
        offspring.setGenotype(gen);
        return offspring;
    }

    public List<Double> getFittestsGenotype() {
        Individual fittest = findFittest();
        return  fittest.getGenotype();
    }

    public void setNrGenerations(int nrGenerations) {
        this.nrGenerations = nrGenerations;
    }

    public void setPopSize(int popSize) {
        this.popSize = popSize;
    }

    public Double test() {
        List<Double> coef = getFittestsGenotype();
        Double[] errors = new Double[testInData.length];
        for (int i=0;i<testInData.length;i++) {
            Double result = 0.0;
            for (int j=0;j<m;j++) {
                result += testInData[i][j]*coef.get(j);
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
