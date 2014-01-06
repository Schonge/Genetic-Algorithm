package com.jconnolly.geneticAlgorithm;

/**
 * You are required to write a Genetic Algorithm (GA) using the Java programming language.
 * The objective of your GA will be to evolve your name and ID number {Firstname, Lastname, IDNumber}.
 * The GA should read the various parameters required using command line arguments, evolve potential solutions
 * over the course of a number of generations, and output its results to a text file.
 * 
 * @author John Connolly
 * @version v1.0
 *
 */

import java.util.Random;
import java.io.*;

public class NewGeneticAlgorithm {
	
	private static String id = "John Connolly 09005970";
	
	public static void main(String[] args) {
		int popSize = 0 ;
		int gen = 0 ;
		double repRate = 0 ;
		double crossRate = 0 ;
		double mutRate = 0 ;
		File file = null;
		PrintWriter writer = null;
		int seed = 9005970 ;
		Random rand = new Random(seed) ;
		
		NewGeneticAlgorithm genAlg = new NewGeneticAlgorithm();
		
		if(args.length != 6) {
			System.err.println("Invalid number of arguments!");
			System.exit(1);
		}
		
		try {
			popSize = genAlg.validateIntArg(args[0], "population rate");
            gen = genAlg.validateIntArg(args[1], "generation rate");
            repRate = genAlg.validateDoubleArg(args[2], "reproduction rate");
            crossRate = genAlg.validateDoubleArg(args[3], "crossover rate");
            mutRate = genAlg.validateDoubleArg(args[4], "mutationRate") ;
            file = new File(args[5]);
            if(repRate + crossRate + mutRate != 1.0)
            {
                System.err.println("The Reproduction Rate, Crossover Rate and Mutation Rate when sumed together must equal 1.0!") ;
                System.exit(1) ;
            }
		} catch(NumberFormatException n) {
			System.err.println("Invalid argument type") ;
            System.exit(1);
		}
		
		try {
			writer = new PrintWriter(file);
		} catch(FileNotFoundException f) {
			System.err.println("File not found");
			System.exit(1);
		}
		
		writer.println(popSize + " " + gen + " " + repRate + " " + crossRate + " " + mutRate + " " + file + " " + seed) ;
		
		
		Population population = new Population(popSize, rand, id);
		population.createPopulation();
		
		TournamentSelection tournament = new TournamentSelection(5, id, population.getPopulation(), gen, repRate, crossRate, writer, rand);
		tournament.runTournament();
		
		writer.close();
	}
	
	
	
	
	
	/**
	 * This method validates all arguments that take in an integer to make sure they meet certain criteria
	 */
	private int validateIntArg(String arg, String argName)
	{
		int n = Integer.parseInt(arg) ;
        if (n < 0 || n > 100000)
        {
            System.err.print("Invalid number! A positive number between 0 and 100000 must be used for the " + argName + "!") ;
            System.exit(1) ;
        }
        return n ;
	}
	
	/**
	 * This method validates all arguments that take in a double to make sure they meet certain criteria
	 */
	private double validateDoubleArg(String arg, String argName)
	{
		double n = Double.parseDouble(arg) ;
        if (n < 0 || n > 1.0) {
            System.err.print("Invalid number! A positive decimal point number between 0 and 1.0 must be used for the " + argName + "!") ;
            System.exit(1) ;
        }

        return n ;
	}

}
