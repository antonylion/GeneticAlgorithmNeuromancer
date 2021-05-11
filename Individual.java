public abstract class Individual{
    int fitness = 0;
    double[] genes = new double[8];

    public Individual(){

    }

    public abstract void calcFitness();

    public abstract void retryFirtStrategy();

}