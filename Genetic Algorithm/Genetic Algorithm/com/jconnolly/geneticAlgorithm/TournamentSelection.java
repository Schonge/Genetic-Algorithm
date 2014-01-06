package com.jconnolly.geneticAlgorithm;

import java.io.PrintWriter;
import java.util.Random;


public class TournamentSelection {
	private int k;
	private int generations;
	private int probability;
	private double reproduction;
	private double crossover;
	private double mutation;
	private String id;
	private Individual[] population;
	private Individual[] matingPool;
	private PrintWriter writer;
	private Random rand;
	
	
	public TournamentSelection(int k, String id, Individual[] population, int generations, double reproduction, double crossover, 
			PrintWriter writer, Random rand) {
		this.k = k;
		this.generations = generations;
		this.reproduction = reproduction;
		this.crossover = crossover;
		this.mutation = mutation;
		this.id = id;
		this.population = population;
		this.writer = writer;
		this.rand = rand;
		matingPool = new Individual[population.length];
	}
	
	/*
	 * Gets the average fitness of the population of individuals
	 */
	public double getAverageFitness(Individual[] population) {
		int avgFit = 0, totalFit = 0;
		for(int i = 0 ; i < population.length ; i++) {
			totalFit += population[i].getFitness();
		}
		avgFit = totalFit/population.length;
		return avgFit;
	}
	
	/*
	 * Gets the best fitness from the population of individuals
	 */
	public double getBestFit(Individual[] population) {
		int bestFit = 0, fitness = 0;
		for(int i = 0 ; i < population.length ; i++) {
			fitness = population[i].getFitness();
			if(fitness > bestFit) {
				bestFit = fitness;
			}
		}
		return bestFit;
	}
	
	/*
	 * Gets the individual with the best fitness from the population of individuals
	 */
	public Individual getBestIndividual(Individual[] population) {
		Individual bestIndividual = population[0];
		int bestFit = 0, fitness = 0;
		for(int i = 0 ; i < population.length ; i++) {
			fitness = population[i].getFitness();
			if(fitness > bestFit) {
				bestFit = fitness;
				bestIndividual = population[i];
			}
		}
		return bestIndividual;
	}
	
	/*
	 * This method randomly selects k individuals to be added to the mating pool.
	 * It also prints out the statistics of the population such as average fitness
	 * best fitness and the best individual's in ascii form.
	 */
	public void selectIndividuals() {
		writer.println(getAverageFitness(population) + " " + getBestFit(population) + " " + getBestIndividual(population).getAscii());
		
		// If individual is equal to the id close the writer
		if(getBestFit(population) == id.length()) {
			writer.close();
			System.exit(0);
		}
		
		Individual[] selectedIndividuals = new Individual[k];
		Individual bestInd;
		int fittest = 0;
		
		for(int i = 0 ; i < matingPool.length ; i++) {
			// Randomly selects and individual from the population and puts it in array for selected individuals
			for(int j = 0 ; j < k ; j++) {
				selectedIndividuals[j] = population[rand.nextInt(population.length)];
			}
			// At the start the first individual is always used as the best until compared to rest of array (individuals)
			bestInd = selectedIndividuals[0];
			fittest = bestInd.getFitness();
			// Checks which selected individual has the best fitness
			for(int m = 0 ; m < selectedIndividuals.length ; m++) {
				if(selectedIndividuals[m].getFitness() > fittest) {
					bestInd = selectedIndividuals[m];
					fittest = selectedIndividuals[m].getFitness();
				}
			}
			// Adds best individual to the mating pool
			matingPool[i] = bestInd;
		}
		
	}
	
	/*
	 * This method runs the loop based off of the input values received in the command line.
	 * It runs a loop for the number of generations input and based off of the input values (probabilities)
	 * for crossover, mutation and reproduction it decides which one to perform on the individual.
	 */
	public void runTournament() {
		int popSize;
		int crossPoint;
		int mutPoint;
		char temp;
		// Need 2 individuals to perform a crossover operation between 2 individuals
		Individual individualOne;
		Individual individualTwo;
		// Need 2 Strings to hold the bit string of the individuals' that will have crossover performed on them
		StringBuilder bitStringOne;
		StringBuilder bitStringTwo;
		// Need 2 Strings to hold the part of the individuals' bit string that will be crossed over
		StringBuilder newBitsOne;
		StringBuilder newBitsTwo;
		
		for(int i = 0 ; i < generations ; i++) {
			writer.print(i + " ");
			selectIndividuals();
			popSize = 0;
			while(popSize < population.length) {
				crossPoint = 0;
				mutPoint = 0;
				bitStringOne = new StringBuilder("");
				bitStringTwo = new StringBuilder("");
				newBitsOne = new StringBuilder("");
				newBitsTwo = new StringBuilder("");
				
				// Probability is a random number between 1-100
				// If there was no + 1 it would be between 0-99
				probability = rand.nextInt(100) + 1;
				
				//Perform reproduction
				if(probability <= reproduction) {
					// Randomly pick an individual for reproduction
					individualOne = matingPool[rand.nextInt(matingPool.length)];
					// Add it to new population of individuals
					population[popSize] = individualOne;
					popSize++;
				}
				// Perform crossover
				else if(probability > reproduction && probability <= reproduction + crossover && popSize <= population.length - 2) {
					// Select 2 random individuals for crossover to be performed on
					individualOne = matingPool[rand.nextInt(matingPool.length)];
					individualTwo = matingPool[rand.nextInt(matingPool.length)];
					// Crossover point is the point in the strings where they will be split and the tails
					// of one individual is crossed over with the other individual
					crossPoint = rand.nextInt(individualOne.getBitString().length()-1);
					bitStringOne = individualOne.getBitString();
					bitStringTwo = individualTwo.getBitString();
					// Get the bits from the start of the string to the crossover point
					for(int k = 0 ; k < crossPoint ; k++) {
						newBitsOne.append(bitStringOne.charAt(k));
						newBitsTwo.append(bitStringTwo.charAt(k));
					}
					// Append new characters to be crossed over onto the new strings
					for(int j = crossPoint ; j < bitStringOne.length() ; j++) {
						temp = bitStringOne.charAt(j);
						newBitsOne.append(temp);
						temp = bitStringTwo.charAt(j);
						newBitsTwo.append(temp);
					}
					// Individual 1 and 2 now hold the newly created individuals which are a result of the crossover
					individualOne = new Individual(newBitsOne, id);
					individualTwo = new Individual(newBitsTwo, id);
					// They are added to the new population
					population[popSize] = individualOne;
					population[popSize + 1] = individualTwo;
					
					popSize += 2;					
				}
				// Perform mutation
				else {
					// Individual is randomly selected for mutation to be performed on
					individualOne = matingPool[rand.nextInt(matingPool.length)];
					// Holds the bit string of the chosen individual
					bitStringOne = individualOne.getBitString();
					// Mutation point is randomly selected
					mutPoint = rand.nextInt(bitStringOne.length());
					for(int m = 0 ; m < bitStringOne.length() ; m++) {
						// If m is equal to the mutation point swap what ever is at that point (1 or 0) with its opposite
						// i.e. if it is a 1 swap it with a 0 and vice versa otherwise just append what ever character is at that position
						if(m == mutPoint) {
							if(bitStringOne.charAt(mutPoint) == '1') {
								newBitsOne.append('0');
							} else {
								newBitsOne.append('1');
							}
						} else {
							newBitsOne.append(bitStringOne.charAt(m));
						}
					}
					// The mutated individual is added to the new population
					individualOne = new Individual(newBitsOne, id);
					population[popSize] = individualOne;
					popSize++;
				}
			}
		}
		writer.close();
	}
	

}
