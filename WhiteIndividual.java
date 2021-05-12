import java.util.Random;
import java.util.StringTokenizer;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class WhiteIndividual extends Individual{
    //int fitness = 0;
    //double[] genes = new double[8];
    double[] initialGenes = {5000, 100, 200, -100, -170, -100, 50, -50};

    //Weights indexes
    final static int WEIGHT_VICTORY = 0;
    final static int KING_POSITION = 1;
    final static int NUMBER_WHITES = 2;
    final static int SURROUNDING_BLACKS = 3;
    final static int NUMBER_BLACKS = 4;
    final static int THREAT = 5;
    final static int BLACK_NEAR_KING = 6;
    final static int WHITE_NEAR_KING=7;

    //variables for first strategy
    //static int countWeightIndex = 0;
    final static int NUMBER_GENERATION_PER_INDEX = 15;
    static int numberOfGeneration = NUMBER_GENERATION_PER_INDEX;


    //parameters for parallel computing
    /*
        static int numberOfGeneration = NUMBER_GENERATION_PER_INDEX;
     */

    public WhiteIndividual(String outputFilePath, int id){
        super(outputFilePath, id);
        rn = new Random();
        genes[WEIGHT_VICTORY] = initialGenes[WEIGHT_VICTORY];
        genes[KING_POSITION] = initialGenes[KING_POSITION];
        genes[NUMBER_WHITES] = initialGenes[NUMBER_WHITES];
        genes[SURROUNDING_BLACKS] = initialGenes[SURROUNDING_BLACKS];
        genes[NUMBER_BLACKS] = initialGenes[NUMBER_BLACKS];
        genes[THREAT] = initialGenes[THREAT];
        genes[BLACK_NEAR_KING] = initialGenes[BLACK_NEAR_KING];
        genes[WHITE_NEAR_KING] = initialGenes[WHITE_NEAR_KING];

        fitness = 0;
        try {

            var myWriter = new FileWriter(outputFilePath + id);
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            var myWriter = new FileWriter(historyFilePath + id);
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            System.exit(1);
        }

    }


    public void changeOneWeight(){

        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == WEIGHT_VICTORY) genes[WEIGHT_VICTORY] = 5000;
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == KING_POSITION) genes[KING_POSITION] = rn.nextInt(150) + 50;
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == NUMBER_WHITES) genes[NUMBER_WHITES] = rn.nextInt(200) + 100;
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == SURROUNDING_BLACKS) genes[SURROUNDING_BLACKS] = (rn.nextInt(199) + 50) * (-1);
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == NUMBER_BLACKS) genes[NUMBER_BLACKS] = (rn.nextInt(200) + 100) * (-1);
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == THREAT) genes[THREAT] = (rn.nextInt(100) + 50) * (-1);
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == BLACK_NEAR_KING) genes[BLACK_NEAR_KING] = (rn.nextInt(99) + 1) *(-1);
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX == WHITE_NEAR_KING) genes[WHITE_NEAR_KING] = rn.nextInt(99) + 1;
    }

    public void fixOtherWeights(){
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != WEIGHT_VICTORY) genes[WEIGHT_VICTORY] = initialGenes[WEIGHT_VICTORY];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != KING_POSITION) genes[KING_POSITION] = initialGenes[KING_POSITION];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != NUMBER_WHITES) genes[NUMBER_WHITES] = initialGenes[NUMBER_WHITES];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != SURROUNDING_BLACKS) genes[SURROUNDING_BLACKS] = initialGenes[SURROUNDING_BLACKS];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != NUMBER_BLACKS) genes[NUMBER_BLACKS] = initialGenes[NUMBER_BLACKS];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != THREAT) genes[THREAT] = initialGenes[THREAT];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != BLACK_NEAR_KING) genes[BLACK_NEAR_KING] = initialGenes[BLACK_NEAR_KING];
        if (numberOfGeneration / NUMBER_GENERATION_PER_INDEX != WHITE_NEAR_KING) genes[WHITE_NEAR_KING] = initialGenes[WHITE_NEAR_KING];
    }

    public void retryFirtStrategy(){
        System.out.println("ENTRO retryFirtStrategy");
        System.out.println("Number of generation: " + numberOfGeneration + ". numberOfGeneration / NUMBER_GENERATION_PER_INDEX = " + (numberOfGeneration / NUMBER_GENERATION_PER_INDEX));

        //Search for a different configuration
        changeOneWeight();

        //Restore previous weight initial value if previous computation did't found better fitness
        fixOtherWeights();

        while(!isUnique(genes)){ //While individual, composed by genes, is similar to one already tried
            //Search for a different configuration
            changeOneWeight();

            //Restore previous weight initial value if previous computation did't found better fitness
            fixOtherWeights();
        }

        numberOfGeneration++;

        try { //write to evolution.txt for NeuroApp input
            var myWriter = new FileWriter(outputFilePath + id);
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        try { //write (IN APPEND) to History.txt for unique generations
            var myWriter = new FileWriter(historyFilePath + id, true);
            for(double peso : genes){
                myWriter.write(peso + ";");
            }
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void retrySecondStrategy(){

    }


}
