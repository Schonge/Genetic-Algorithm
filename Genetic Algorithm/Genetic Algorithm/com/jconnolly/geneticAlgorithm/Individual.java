package com.jconnolly.geneticAlgorithm;

public class Individual {
	
	private StringBuilder bitString;
	private StringBuilder ascii;
	private ConvertToAscii convert;
	private Fitness fit;
	private int fitness;
	
	public Individual(StringBuilder bitString, String id) {
		this.bitString = bitString;
		convert = new ConvertToAscii();
		ascii = convert.binaryToASCII(bitString);
		fit = new Fitness(id);
		fit.fitnessOfIndividual(ascii);
		fitness = fit.getFitness();
	}
	
	public int getFitness() {
		return fitness;
	}
	
	public StringBuilder getAscii() {
		return ascii;
	}
	
	public StringBuilder getBitString() {
		return bitString;
	}

}
