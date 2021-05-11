import java.util.StringTokenizer;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
public class Population {

    int N_INDIVIDUALS = 1;

    int popSize = 1;
    Individual[] individuals = new Individual[N_INDIVIDUALS];
    //FOR SEMPLICITY, LET THE POPULATION BE COMPOSED BY ONE INDIVIDUAL
    int fittest = 0;

    //Initialize population
    public void initializePopulation(int size, String player) {
        if(player.equals("WHITE")){
            for (int i = 0; i < individuals.length; i++) {
                individuals[i] = new WhiteIndividual();
            }
        } else{ //PLAYER BLACK
            for (int i = 0; i < individuals.length; i++) {
                individuals[i] = new BlackIndividual();
            }
        }
    }

    //Get the fittest individual
    public Individual getFittest() {
        int maxFit = Integer.MIN_VALUE;
        int maxFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (maxFit <= individuals[i].fitness) {
                maxFit = individuals[i].fitness;
                maxFitIndex = i;
            }
        }
        fittest = individuals[maxFitIndex].fitness;
        return individuals[maxFitIndex];
    }

    //Get the second most fittest individual
    /*
    public Individual getSecondFittest() {
        int maxFit1 = 0;
        int maxFit2 = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (individuals[i].fitness > individuals[maxFit1].fitness) {
                maxFit2 = maxFit1;
                maxFit1 = i;
            } else if (individuals[i].fitness > individuals[maxFit2].fitness) {
                maxFit2 = i;
            }
        }
        return individuals[maxFit2];
    }*/

    //Get index of least fittest individual
    /*
    public int getLeastFittestIndex() {
        int minFitVal = Integer.MAX_VALUE;
        int minFitIndex = 0;
        for (int i = 0; i < individuals.length; i++) {
            if (minFitVal >= individuals[i].fitness) {
                minFitVal = individuals[i].fitness;
                minFitIndex = i;
            }
        }
        return minFitIndex;
    }*/

    //Calculate fitness of each individual
    public void calculateFitness() {

        for (int i = 0; i < individuals.length; i++) {
            individuals[i].calcFitness();
        }
        getFittest();
    }

    /*public void inidividualRetry(){
        individuals[0].retry();
    }*/

    public void retryWithFirstStrategy(){
        individuals[0].retryFirtStrategy();
    }

    /*
    public void retryWithSecondStrategy(){
        individuals[0].retrySecondStrategy();
    }*/

}