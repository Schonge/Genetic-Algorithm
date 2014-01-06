package com.jconnolly.geneticAlgorithm;

import java.util.Random;

public class Population {
	
	private Individual[] pop;
	private int popSize;
	private Random rand;
	private String id;
	
	public Population(int popSize, Random rand, String id) {
		pop = new Individual[popSize];
		this.popSize = popSize;
		this.rand = rand;
		this.id = id;		
	}
	
	public void createPopulation() {
		int bits = 7 * id.length() ;
		for(int i = 0 ; i < popSize ; i++)
        {
        	StringBuilder bitString = new StringBuilder("") ;
        	for(int j = 0 ; j < bits ; j++)
        	{
                bitString.append(rand.nextInt(2)) ;
            }
        	pop[i] = new Individual(bitString, id);
        }
	}
	
	public Individual[] getPopulation() {
		return pop;
	}

}
