package com.jconnolly.geneticAlgorithm;

public class ConvertToAscii {
	
	/**
	 * Converts the generated bit strings to ascii characters by separating the strings into bytes
	 */
	public StringBuilder binaryToASCII(StringBuilder str)
	{
		StringBuilder ascii = new StringBuilder("") ;
		String byteString ;
		int decimal ;
		int i = 0, n = 7 ;
		int baseNumber = 2 ;
		char asciiChar ;
		while(n <= 154)
		{
			byteString = str.substring(i, n) ;
			decimal = Integer.parseInt(byteString, baseNumber) ;
			i += 7 ;
			n += 7 ;
			if(decimal < 33 || decimal > 136)
			{
				decimal = 32 ;
				asciiChar = (char) decimal ;
			} else {
				asciiChar = (char) decimal ;
			}
			ascii.append(asciiChar) ;
		}
		return ascii ;
	}

}
