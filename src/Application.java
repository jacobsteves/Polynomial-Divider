//
// Sythetic Division Calculator
// Solving Class
// Jacob Steves
//

package src;

import java.io.*;
import java.util.*;
import javax.swing.*;

public class Application {

  public static Object [] [] table;

  public static String quotient = "";
  public static String remainder = "";
  private static String solution = "";

  private static String fileName = "";
  private static String div1 = "";
  private static String div2 = "";

  private static double [] [] board;
  private static double [] multiSide;
  private static double [] [] res;

  //
  // This method outputs the board
  //
  private static void drawBoard() {
    System.out.println("CONSOLE SOLUTION: ");
    for(int i = 0; i < multiSide.length - 1; i++){
      System.out.print(multiSide[i] + " || ");
      for(int x = 0; x < board.length; x++){
        System.out.print(board[x][i] + " | ");
      }
      System.out.println();
    }
    System.out.println("***************************************");
    for(int i = 0; i < 2; i++){
      System.out.print("\t|| ");
      for(int x = 0; x < board.length; x++){
        System.out.print(res[x][i] + " ");
      }
      System.out.println();
    }
  }

  public static void export(ArrayList<Term> divident, ArrayList<Term> divisor, boolean shouldPrint, boolean hasGUI, String fileName, String div1, String div2) {
    fileName = fileName;
    div1 = div1;
    div2 = div2;
    solveEquation(divident, divisor, shouldPrint, hasGUI);
  }

  //
  // This method outputs the answer
  //
  private static void printAnswer() {

    try {
      System.out.println(quotient + " | " + remainder);
      PrintWriter out = new PrintWriter(new FileWriter(fileName + ".txt"));
      out.println("Jacob Steves - Synthetic Division Table");
      out.println("Command: Divide " + div1 + " by: " + div2 + ".");
      for(int i = 0; i < multiSide.length - 1; i++) {
        out.print(multiSide[i] + " || ");
        for(int x = 0; x < board.length; x++){
          out.print(board[x][i] + " | ");
        }
        out.println();
      }
      out.println("***************************************");
      for(int i = 0; i < 2; i++) {
        out.print("\t\t|| ");
        for(int x = 0; x < board.length; x++) {
          out.print(res[x][i] + " ");
        }
        out.println();
      }
      out.println(solution);
      out.close();
    }
    catch(IOException e){

    }
  }

  //
  // This outputs the array for the table
  //
  public static Object [] [] getTableData() {
    Object [] [] returnArray = new Object[ board.length + 2][board[0].length + res[0].length +2];
    for(int i = 0; i < returnArray[0].length - 2; i++)
      returnArray[1][i] = '*';
    for(int i = 0; i < returnArray.length; i++) {
      returnArray[i][1] = '*';
    }
    for(int i = 0; i < returnArray[0].length; i++) {
      returnArray[1][i] = '*';
    }

    for(int i = 1; i < multiSide.length; i++) {
      returnArray[0][i] = multiSide[i-1];
    }

    for(int i = 2; i < returnArray.length; i++) {
      returnArray[i][0] = board[i - 2][0];
    }
    for(int i = 2; i < returnArray.length; i++) {
      for(int x = 1; x < board[0].length; x++) {
        returnArray[i][x + 1] = board[i - 2][x];
      }
    }
    for(int x = 0; x < 2; x++) {
      for(int i = 2; i < returnArray.length; i++) {
        returnArray[i][x + board[0].length + 2] = res[i - 2][x];
      }
    }

    table = returnArray;
    return returnArray;

  }

  //
  // This method returns if the divident is monic
  // @return boolean if it is monic
  //
  private static boolean isMonic(ArrayList<Term> divident) {
    if (divident.get(0).getCoefficient() == 1)
      return true;
    return false;
  }

  //
  // This method outputs the answer of the program.
  //
  public static void outputAnswer(double [] [] res, int cutOff){
    int degree = res.length - cutOff; //plus 1?
    System.out.print("p(x) = ");
    for(int i = degree; (i - cutOff) >= 0; i--) {
      if (i < degree)
        System.out.print(" +");
      System.out.print(res[degree -i][1] + "x^" + (i - cutOff));
    }
    System.out.print("\nr(x) = ");
    for(int i = cutOff+1; i <= res.length - 1; i++) {
      if (i > cutOff + 1)
        System.out.print(" +");
      System.out.print(res[i][0] + "x^" + (res.length - i - 1) );
    }
    System.out.println();

  }

  //
  // This method SOLVES the equations!
  //
  public static void solveEquation(ArrayList<Term> divident, ArrayList<Term> divisor, boolean shouldPrint, boolean hasGUI){
    // Time to get the coefficients and set up the arrays.
    double [] [] board = new double [divident.size()][divisor.size()];
    if (board[0].length == 0) //RETURN IF THE ARRAY HAS NOTHING IN IT
      return;
    // Let's fill the top of the board with the appropriate initial values.
    for(int i = 0; i < divident.size(); i++){
      //System.out.println("COEFF: " + divident.get(i).getCoefficient());
      board[i][0] = divident.get(i).getCoefficient();
    }
    boolean monic = isMonic(divident);

    double [] multiSide = new double[divisor.size() + 1];
    // Populate left most table. Update the monic restriction for flipping all values, too.
    for(int i = divisor.size() - 1; i >0; i--){
      multiSide[divisor.size() - i] = ((monic) ? (divisor.get(i).getCoefficient() * -1) : (divisor.get(i).getCoefficient() * -1.0));  // times *-1
    }

    double [][] res = new double[divident.size()][2];
    double leadingCoef = divisor.get(0).getCoefficient();

    // Now to go sequentially through the top list. And do the appropriate things.
    for(int qq = 0; qq < divident.size(); qq++){
      // Go down the table, adding every value.
      double sum = 0;
      for(int p = 0; p < divisor.size(); p++){ //minus 2? //-1
        sum += board[qq][p];
      }
      res[qq][0] = sum;
      if (divident.get(0).getPower() - qq < divisor.get(0).getPower()){} // nothing.
      else{
        res[qq][1] = res[qq][0]/leadingCoef;
        // Now to populate the rest of the board.
        int x = qq+1; //wrong.;
        int y = divisor.size() - 1;
        while(true){
          if (y <= 0 || x > divident.size() - 1) //<= ?? // -2 or -1 //<= 0
            break;
          board[x][y] = res[qq][1] * multiSide[y];
          x++;
          y--;
        }
      }
    }
    outputPxRx(Utility.findGreatestExponent(divident), Utility.findGreatestExponent(divisor), res, hasGUI);

    board = board;
    multiSide = multiSide;
    res = res;

    if (hasGUI){
      drawBoard();
      getTableData();
    }
    if (shouldPrint)
      printAnswer();
  }

  //
  // Insane amount of ternary operators.
  // Essentially outputs the solution
  //
  public static void outputPxRx(int expOne, int expTwo, double [][] res, boolean shouldOutput){
    String quot = "";
    String rem = "";
    int currEx = expOne - expTwo;
    int exp = res.length - (expOne - expTwo) - 2; //x -1
    for(int i = 0; i < res.length; i++){
      if (res[i][1] == 0 && res[i][0] != 0){
        if (currEx >= 0){
          quot += ((res[i][0] > 0 && i != 0) ? ("+") :  "") + ((res[i][0] != 1 && res[i][0] != -1) ? (res[i][0]) : (res[i][0] == -1 && currEx != 0) ? ("-") : (res[i][0] == 1 && currEx == 0) ? (1) : ("")) + (((currEx != 0 && currEx != 1)) ? ("x^" + (currEx)) : (currEx == 1) ? ("x") : ("")) + " ";
          currEx--;
        }
        else{
          rem += ((res[i][0] > 0 && i != 0) ? ("+") :  "") + ((res[i][0] != 1 && res[i][0] != -1) ? (res[i][0]) : (res[i][0] == -1 && exp != 0) ? ("-") : (res[i][0] == 1 && exp == 0) ? (1) : ("")) + ((exp != 0 && exp != 1) ? ("x^" + exp) : (exp == 1) ? ("x") : ("")) + " ";
          exp--;
        }
      }
      else{
        if (res[i][1] != 0){
          if (currEx >= 0){
            quot += ((res[i][1] > 0 && i != 0) ? ("+") : "") + ((res[i][1] != 1 && res[i][1] != -1) ? (res[i][1]) : (res[i][1] == -1 && currEx != 0) ? ("-") : (res[i][1] == 1 && currEx == 0) ? (1) : ("")) + ((currEx != 0 && currEx != 1) ? ("x^" + currEx) : (currEx == 1) ? ("x") : ("")) + " ";
            currEx--;
          }
          else{
            rem += ((res[i][1] > 0 && i != 0) ? ("+") :  "") + ((res[i][1] != 1 && res[i][1] != -1) ? (res[i][1]) : (res[i][1] == -1 && exp != 0) ? ("-") : (res[i][1] == 1 && exp == 0) ? (1) : ("")) + ((exp != 0 && exp!= 1) ? ("x^" + exp) : (exp == 1) ? ("x") : ("")) + " ";
            exp--;
          }
        }
      }
    }
    if (shouldOutput)
      JOptionPane.showMessageDialog (null, "Solution: \nQ(x) = " + quot + "\nR(x) = " + ((rem.equals("")) ? ("None") : (rem)), "Solution", JOptionPane.WARNING_MESSAGE);
    solution =  ("Solution: \nQ(x) = " + quot + "\nR(x) = " + ((rem.equals("")) ? ("None") : (rem)));
  }

  public static String getSolution(){
    return solution;
  }
}
