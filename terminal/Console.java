//
// Sythetic Division Calculator
// Console Class
// Jacob Steves
//

import java.io.*;
import java.util.*;
import src.*;

public class Console {
  private BufferedReader in;
  private String polynomial, quotient;

  public Console() {
    try{
      in = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("\n|--------------------------------------|");
      System.out.println("Welcome to the Synthetic Division Console!");
      System.out.println("This application will visually demonstrate");
      System.out.println("Synthetic Division. When entering numbers,");
      System.out.println("enter them like the following:");
      System.out.println("'4x^2+4x+3'\n");
      System.out.println("Press any key to continue.");
      in.read();
      while(true) {
        System.out.println("//------// Main Menu //------//");
        System.out.println("1. Divide Polynomials Synthetically");
        System.out.println("2. Save Previous Results");
        System.out.println("3. Exit");
        System.out.println("Please make your selection.");
        try {
          int select = Integer.parseInt(in.readLine());
          if (select == 1) {
            divideSynthetically();
          }
          else if (select == 2) {
            outputAnswer();
          }
          else if (select == 3) {
            System.out.println("|------ Thank you for using me! -------|");
            System.exit(0);
          }
          else {
            System.out.println("Invalid choice! Press any key to continue.");
            in.read();
          }
        }
        catch(NumberFormatException nF) {
          System.out.println("Error: You have to enter a valid number!");
          System.out.println("Press any key to continue.");
          in.read();
        }
      }
    }
    catch(IOException e){

    }
  }

  private void outputAnswer() {
    try {
      System.out.println("----- Please enter your filename! -----");
      System.out.print("Filename: ");
      String filename = in.readLine();
      if (!filename.contains(".txt"))
        filename += ".txt";
      PrintWriter out = new PrintWriter(new FileWriter(filename));
      out.println("Polynomial: " + polynomial);
      out.println("Quotient: " + quotient);
      out.println(Application.getSolution());
      out.close();
      System.out.println("File outputted successfully.");
    }
    catch(IOException e) {
      System.out.println("Error: Input is not valid. Please try again.");
      outputAnswer();
      return;
    }
  }

  private void divideSynthetically() {
    try {
      ArrayList<Term> divident = new ArrayList<Term>();
      ArrayList<Term> divisor = new ArrayList<Term>();
      System.out.println("----- Solving by Synthetic Division! -----");
      System.out.print("Please enter your equation: ");
      polynomial = in.readLine();
      divident = Utility.generateTerms(polynomial);
      System.out.print("Please enter your divident: ");
      quotient = in.readLine();
      divisor = Utility.generateTerms(quotient);

      Collections.sort(divident);
      Collections.sort(divisor);

      divident = Utility.fillTerms(divident, Utility.findGreatestExponent(divident));
      divisor = Utility.fillTerms(divisor, Utility.findGreatestExponent(divisor));

      if (Utility.findGreatestExponent(divident) < Utility.findGreatestExponent(divisor)) {
        System.out.println("Notice: Dividing polynomial with a larger exponent on the base is unsupported / is in beta.");
      }

      if (Utility.findGreatestExponent(divisor) > 2) {
        System.out.println("Error: You cannot divide by a non-quadratic in this version.");
        divideSynthetically();
        return;
      }

      Application.solveEquation(divident, divisor, false, false);
      System.out.println("|-------------------------------|");
      System.out.println(Application.getSolution());
      System.out.println("Press any key to continue.");
      in.read();
    }
    catch(IOException e) {
      System.out.println("Error: Input is not valid. Please try again.");
      divideSynthetically();
      return;
    }
  }

  public static void main (String [] args) {
    new Console();
  }
}
