package EA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Individual> individuals;
    private static Random random = new Random();

    public Population(List<Individual> individuals) {
        this.individuals = individuals;
    }

    public Population() {
        this.individuals= new ArrayList<>();
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }


    public Individual getFittest() {
        Individual fittest = individuals.get(0);
        for (Individual individual : individuals) {
            if (individual.getFitness() < fittest.getFitness()) {
                fittest = individual;
            }
        }
        return fittest;
    }

    public int size() {
        return individuals.size();
    }

    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    public void removeIndividual(Individual individual) {
        individuals.remove(individual);
    }

    public Individual getRandomIndividual() {
        int index = random.nextInt();
        return getIndividual(index);
    }

}
