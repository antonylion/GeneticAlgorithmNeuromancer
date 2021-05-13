import java.util.StringTokenizer;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
public class Population {

    int N_INDIVIDUALS = 1;

    Individual[] individuals = new Individual[N_INDIVIDUALS];
    //FOR SEMPLICITY, LET THE POPULATION BE COMPOSED BY ONE INDIVIDUAL
    String myPlayer;
    int fittest = 0;

    //Initialize population
    public void initializePopulation(int size, String player, String outputFilePath, int id) {
        if(player.equals("WHITE")){
            for (int i = 0; i < individuals.length; i++) {
                individuals[i] = new WhiteIndividual(outputFilePath, id);
            }
        } else{ //PLAYER BLACK
            for (int i = 0; i < individuals.length; i++) {
                individuals[i] = new BlackIndividual(outputFilePath, id);
            }
        }
        myPlayer = player;
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

    public void markImprovement(String improvementOutputFilePath, double[] genes) {
        individuals[0].markImprovement(improvementOutputFilePath, genes);
    }

    /*
    public void retryWithSecondStrategy(){
        individuals[0].retrySecondStrategy();
    }*/

}
