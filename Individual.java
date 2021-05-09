import java.util.Random;
import java.util.StringTokenizer;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Individual{
    int fitness = 0;
    int[] genes = new int[9];
    int geneLength = 9;
    StringTokenizer st;

    //Weights indexes
    final static int WEIGHT_VICTORY = 0;
    final static int KING_POSITION = 1;
    final static int DISTANCE_CENTRE = 2;
    final static int NUMBER_WHITES = 3;
    final static int SURROUNDING_BLACKS = 4;
    final static int NUMBER_BLACKS = 5;
    final static int THREAT = 6;
    final static int SCATTER = 7;
    final static int NEAR_KING = 8;

    public Individual(){
        Random rn = new Random();

        //Set genes randomly for each individual, considering a reasoned range for everyone of it
        for (int i = 0; i < genes.length; i++) {
            genes[WEIGHT_VICTORY] = rn.nextInt(1000) + 4500;
            genes[KING_POSITION] = rn.nextInt(150) + 50;
            genes[DISTANCE_CENTRE] = 0;
            genes[NUMBER_WHITES] = rn.nextInt(200) + 100;
            genes[SURROUNDING_BLACKS] = (rn.nextInt(199) + 50) * (-1);
            genes[NUMBER_BLACKS] = (rn.nextInt(200) + 100) * (-1);
            genes[THREAT] = (rn.nextInt(200) + 100) * (-1);
            genes[SCATTER] = rn.nextInt(150) + 50;
            genes[NEAR_KING] = rn.nextInt(99) + 1;
        }
        fitness = 0;
    }

    //Calculate fitness
    public void calcFitness() {
        String matchResult = "";
        String data = "";
        fitness = 0;
        try {
            File myObj = new File("/Users/antonyzappacosta/Desktop/filesForGenetic/NeuroAppOutput.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                st = new StringTokenizer(data, ";");
                System.out.println("LETTA RIGA: " + data);
                System.out.println(data);
                //LEGGO SE HO VINTO O PERSO
                matchResult = st.nextToken().trim();
                break;
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        System.out.println("RISULTATO PARTITA: " + matchResult);

        if(matchResult.equals("WIN")){
            fitness = 1;
        }
    }

}