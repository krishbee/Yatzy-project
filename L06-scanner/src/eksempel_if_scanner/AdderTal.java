package eksempel_if_scanner;

import java.util.Scanner;

public class AdderTal {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Tal 1 ");
		int tal1 = scan.nextInt();
		
		System.out.print("Tal 2 ");
		int tal2 = scan.nextInt();
		
		System.out.println("Resultat addition " + (tal1 + tal2));
// til e5.1
		int tal3 = tal1	+ tal2;
		if (tal3 == 0){
			System.out.println("Svaret er 0");
		} else if (tal3 > 0){
			System.out.println("svaret er positivt");
		} else if (tal3 < 0){
			System.out.println("svaret er negativt");
		}
		scan.close();

	}
}
