package Calculator;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner (System.in);
		
		System.out.println("Input first number: ");
		double num = sc.nextDouble();
		
		System.out.println("Input second number: ");
		double num2 = sc.nextDouble();
		
		System.out.println("Input sign (+, - ,/ , *, ^): ");
		char sign = sc.next().charAt(0);
		
		System.out.println(CalMeth.oper (num, num2, sign));
		
	}

}
