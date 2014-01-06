package com.jconnolly.geneticAlgorithm;

public class Fitness {
	
	private String id;
	private int fitness;
	
	Fitness(String id) {
		this.id = id;
	}
	
	/**
	 * Gets the fitness of each generated individual to see how close a match it is to the original string id
	 */
	public void fitnessOfIndividual(StringBuilder ascii)
	{
		fitness = 0 ;
		for(int i = 0 ; i <= ascii.length() - 1 ; i++)
		{
			if(ascii.charAt(i) == id.charAt(i))
			{
				fitness++ ;
			}
		}
	}
	
	public int getFitness() {
		return fitness;
	}

}
