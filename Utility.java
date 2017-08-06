//
// Sythetic Division Calculator
// Utility Class
// Jacob Steves
//

import java.util.*;
import javax.swing.*;

public class Utility {

  //
  // This method clears the console screen
  //
  public static void clearScreen() {
    for(int i = 0; i < 20; i++)
      System.out.println();
  }

  //
  // This method finds the greatest exponent.
  // @return int This returns the greatest exponent
  //
  public static int findGreatestExponent(ArrayList <Term> terms) {
    int largest = 0;
    for(int i = 0; i < terms.size(); i++) {
      if (terms.get(i).getPower() > largest)
        largest = terms.get(i).getPower();
    }
    return largest;
  }

  //
  // This method outputs the terms.
  //
  public static void outputTerms(ArrayList<Term> terms, boolean diag) {
    if (diag)
      System.out.println("Terms outputted: ");
    for(int i = 0; i < terms.size(); i++) {
      System.out.print(terms.get(i).getCoefficient());
      if (terms.get(i).getPower() != 0) {
        System.out.print("x^" + terms.get(i).getPower() + " ");
      }
      else {
        System.out.print(" ");
      }
    }
    System.out.print("\n");
  }

  //
  // This method will fill the term with empty values like 0x^0
  // @return ArrayList<Term>, the resultant terms in ArrayList form
  //
  public static ArrayList<Term> fillTerms(ArrayList<Term> terms, int greatestExponent){
    ArrayList<Term> ret = new ArrayList<Term> ();

    int currentEx = greatestExponent;
    for(int i = 0; i < terms.size(); i++) {
      if (terms.get(i).getPower() != currentEx) {
        ret.add(new Term(currentEx, 0));  //add new 0 ^ x
        i--;
      }
      else
        ret.add(terms.get(i));
      currentEx--;
      if (currentEx < 0) {
        break;
      }
    }
    return ret;
  }

  //
  // This method will generate terms based off of string input
  //  @return ArrayList<Term> These are the terms returned from the generatino
  //
  public static ArrayList<Term> generateTerms(String input) {
    String formattedString = input.replace("+", " +").replace("-", " -").replace("*", " *").replace("/", " /");
    String[] arrayOfTerms = formattedString.split("\\s");
    int arrayOfTermsLen = arrayOfTerms.length;
    ArrayList<Term> returnValue = new ArrayList<Term>();
    for(int x = 0; x < arrayOfTermsLen; x++){
      String temp = arrayOfTerms[x];
      String coefficientString = "";
      String exponentString = "";
      boolean isNegative = false;
      boolean foundX = false;
      if (temp.charAt(0) == '-') {
        isNegative = true;
      }
      boolean doneNumerical = false;
      for(int i = (isNegative) ? (1) : (0); i < temp.length(); i++) {
        if (doneNumerical) {
          if (temp.charAt(i) <= '9' && temp.charAt(i) >= 0) {
            exponentString += temp.charAt(i);
          }
        }
        else {
          if (temp.charAt(i) > '9' || temp.charAt(i) < '0' && (temp.charAt(i) != '+') && temp.charAt(i) != '.') {
            doneNumerical = true;
            if (temp.charAt(i) == 'x' || temp.charAt(i) == 'X') {
              foundX = true;
            }
          }
          else if (temp.charAt(i) != '+') {
            coefficientString += temp.charAt(i);
          }
        }
      }

      exponentString = ((exponentString.equals("") && foundX) ? ("1") : ((exponentString.equals("") && !foundX) ? ("0") : (exponentString)));
      coefficientString = ((coefficientString.equals("") && foundX) ? ("1") : ((coefficientString.equals("") && !foundX) ? ("0") : (coefficientString)));
      try {
        returnValue.add(new Term(Integer.parseInt(exponentString), (isNegative) ? (Double.parseDouble(coefficientString) * -1) : (Double.parseDouble(coefficientString))));
      }
      catch(NumberFormatException e) {
        JOptionPane.showMessageDialog (null, "Error: Invalid formatting! Please check your formatting.", "Invalid Formatting", JOptionPane.WARNING_MESSAGE);
      }
    }

    return returnValue;
  }

  public Utility(){}
}
