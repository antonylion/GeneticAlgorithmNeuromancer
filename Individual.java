import java.util.Random;

public class Individual{
    int fitness = 0;
    int[] genes = new int[8];
    int geneLength = 8;

    //Weights indexes
    //static double weightVictory = 5000;
    final static int WEIGHT_VICTORY = 0;
    //static double weightKingPosition = 200;
    final static int KING_POSITION = 1;
    //static double weightDistanceFromCentre = 250;
    final static int DISTANCE_CENTRE = 2;
    //static double weightNumberOfWhites = 170;
    final static int NUMBER_WHITES = 3;
    //static double weightSurroundingBlackPawn = -100;
    final static int SURROUNDING_BLACKS = 4;
    //static double weightNumberOfBlacks = -170;
    final static int NUMBER_BLACKS = 5;
    //static double weightThreat = -190;
    final static int THREAT = 6;
    //static double weightScatter = 100;
    final static int SCATTER = 7;

    public Individual(){
        Random rn = new Random();

        //Set genes randomly for each individual, considering a reasoned range for everyone of it
        for (int i = 0; i < genes.length; i++) {
            genes[WEIGHT_VICTORY] = rn.nextInt(1000) + 4500;
            genes[KING_POSITION] = rn.nextInt(350) + 50;
            genes[DISTANCE_CENTRE] = rn.nextInt(100) + 100;
            genes[NUMBER_WHITES] = rn.nextInt(200) + 100;
            genes[SURROUNDING_BLACKS] = (rn.nextInt(199) + 50) * (-1);
            genes[NUMBER_BLACKS] = (rn.nextInt(200) + 100) * (-1);
            genes[THREAT] = (rn.nextInt(200) + 100) * (-1);
            genes[SCATTER] = rn.nextInt(150) + 50;
        }
        fitness = 0;
    }

    //Calculate fitness
    public void calcFitness() {

        fitness = 0;
        //TODO: define how to calculate our fitness
    }

}