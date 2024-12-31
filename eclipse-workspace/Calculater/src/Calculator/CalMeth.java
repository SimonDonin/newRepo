package Calculator;

public class CalMeth {
	
	public static double oper(double num, double num2, double sign) {
		double res = 0.0;
		if (sign == '+') {res = num + num2;}
		else if (sign =='-') {res = num - num2;}
		else if (sign =='*') {res = num * num2;}
		else if (sign =='/' && num2 != 0) {res = num / num2;}
		else if (sign =='/' && num2 == 0) {System.out.println("Incorrect input of second number"); return 0;}
		else if (sign =='^') {res = Math.pow(num, num2);}
		else {System.out.println("Incorrect sign input"); return 0;}
		
				
		return  res; 	
	}
	
	
	
}
