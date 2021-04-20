package com.revature.Main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main mainObj = new Main();
		System.out.println(mainObj.printNumberInWord(10));
		System.out.println(mainObj.reverse("so many strings"));
	}
	/*
	 * Write a method called printNumberInWord. The method has one parameter number
	 * which is the whole number. The method needs to print "ZERO", "ONE", "TWO",
	 * ... "NINE", "OTHER" if the int parameter number is 0, 1, 2, .... 9 or other
	 * for any other number including negative numbers. You can use if-else
	 * statement or switch statement whatever is easier for you.
	 * 
	 * Signature: public String printNumberInWord(int number) { // TODO Write an
	 * implementation for this method declaration
	 * 
	 * return ; }
	 */

	public String printNumberInWord(int number) {
		String numString = "";
		switch (number) {
		case 0:
			numString = "ZERO";
			break;
		case 1:
			numString = "ONE";
			break;
		case 2:
			numString = "TWO";
			break;
		case 3:
			numString = "THREE";
			break;
		case 4:
			numString = "FOUR";
			break;
		case 5:
			numString = "FIVE";
			break;
		case 6:
			numString = "SIX";
			break;
		case 7:
			numString = "SEVEN";
			break;
		case 8:
			numString = "EIGHT";
			break;
		case 9:
			numString = "NINE";
			break;
		default:
			numString = "OTHER";
			break;
		}

		return numString;
	}

	/*
	 * Reverse String
	 * 
	 * Without using the StringBuilder or StringBuffer class, write a method that
	 * reverses a String. Example: reverse("example"); -> "elpmaxe"
	 * 
	 * Signature: public String reverse(String string) { // TODO Write an
	 * implementation for this method declaration
	 * 
	 * return; }
	 */

	public String reverse(String string) {
		String rev = "";
		for (int i = string.length() - 1; i >= 0; i--) {
			rev = rev.concat(string.charAt(i) + "");
		}

		return rev;
	}
}
