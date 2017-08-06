//
// Sythetic Division Calculator
// Synthetic App Class
// Jacob Steves
//

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.*;


public class Interface extends JPanel implements ActionListener{

  public static JTable table;
  static JScrollPane scrollPane;
  static Container programContainer;
  static Container temporaryContainer;
  static double  [][] data;
  static final JFrame jFrame = new JFrame("Synthetic Division Calculator");
  static boolean variableExists = false;
  static String fileName = "";
  static String div1;
  static String div2;

  //
  // This method is the main method
  //
  public static void main (String [] args){
    new Interface();
  }

  //
  // This method flips the array
  //
  public Object [] []  flipArr(Object [] [] initialArray){
    Object [] [] flippedArray = new Object[initialArray[0].length][initialArray.length];
    for(int x = 0; x < initialArray.length; x++){
      for(int y = 0; y < initialArray[0].length; y++){
        flippedArray[y][x] = (initialArray[x][y] == null) ? ('-') : (initialArray[x][y]);
      }
    }
    return flippedArray;
  }

  //
  // This method removes the existing table
  //
  public static void removeExist(){
    temporaryContainer.remove(programContainer);
  }

  //
  // This method adds a new table
  //
  public static void addNewTable(Object [][] arr){
    if (variableExists){
      removeExist();
    }
    variableExists = true;
    String [] arrS = new String[arr[0].length];
    for(int i = 0; i < arr[0].length; i++){
      arrS[i] = ((i == 0) ? ("Divident Coefficients") : (i == 1) ? ("*") : (i < arr[0].length -2) ? ("Coefficient: " + (i - 1)) : (i == arr[0].length -2) ? ("Res Coef w/o Monic") : ("Res Coef w/ Monic: "));

    }
    table = new JTable(arr, arrS);

    scrollPane = new JScrollPane(table);
    table.setVisible(true);
    scrollPane.setVisible(true);

    table.repaint();
    table.validate();

    programContainer = new Container();
    programContainer.add(scrollPane);
    programContainer.setBounds(7, 90, 600, 300);
    scrollPane.setBounds(7, 10, 600, 300);
    programContainer.setVisible(true);
    programContainer.repaint();
    programContainer.validate();
    temporaryContainer.add(programContainer);

    scrollPane.getViewport().setOpaque(false);
    scrollPane.setBorder(null);

    programContainer.repaint();
    programContainer.validate();

    jFrame.repaint();
  }

  //
  // This method updates the table entries
  //
  public static void updateTableEntries(double [][] initialBoard, double [] leftPane, double [][] resBoard, int focalPoint){
    Object [] [] finalTable = new Object[2 + initialBoard.length][leftPane.length+3];
    //seperate by *
    for(int i = 1; i < leftPane.length; i++){
      finalTable[0][i] = leftPane[i] + "";
    }

    for(int i = 0; i < leftPane.length; i++){
      finalTable[1][i] = "*";
    }
    for(int i = 1; i < initialBoard.length; i++){
      for(int x = 0; x < initialBoard[0].length; x++){
        finalTable[2 + i][x] = initialBoard[i][x] + "";
      }
    }

    for(int i = 0; i < initialBoard.length; i++){
      finalTable[i][initialBoard[0].length] = "*";
    }
    for(int i = 0; i < initialBoard[0].length; i++){
      finalTable[1][i] = "*";
    }
    for(int i = 2; i < resBoard.length + 2; i++){
      finalTable[i][finalTable[0].length-1] = resBoard[i-2];
    }

    String [] columns = new String[2 + initialBoard.length];
    columns[0] = "LC";
    columns[1] = "*";
    for(int i = 0; i < initialBoard.length; i++){
      columns[2 + i] = "C" + (i+1);
    }
    table.setFillsViewportHeight(true);
    table.repaint();
    table.revalidate();
    scrollPane.repaint();
    scrollPane.validate();
    programContainer.repaint();
    programContainer.validate();

    jFrame.validate();
    jFrame.repaint();
  }

  //
  // This method overrides ActionPerformed
  //
  public void actionPerformed(ActionEvent e){

  }

  //
  // This method exports the data
  //
  public  void export(String rawDivisor, String rawDivident){
    if (rawDivisor.equals("") || rawDivident.equals("")){
      JOptionPane.showMessageDialog (null, "Error: You cannot leave a field blank!", "Error: Blank field", JOptionPane.WARNING_MESSAGE);
    }
    else{
      div1 = rawDivisor;
      div2 = rawDivident;
      ArrayList<Term> divident = new ArrayList<Term>();
      ArrayList<Term> divisor = new ArrayList<Term>();
      divident = Utility.generateTerms(rawDivisor);
      divisor = Utility.generateTerms(rawDivident);
      Collections.sort(divident);
      Collections.sort(divisor);
      divident = Utility.fillTerms(divident, Utility.findGreatestExponent(divident));
      divisor = Utility.fillTerms(divisor, Utility.findGreatestExponent(divisor));


      if (Utility.findGreatestExponent(divisor) > 2){
        JOptionPane.showMessageDialog (null, "Error: You cannot divide by a non-quadratic in this version.", "Error: Divisor degree is too large", JOptionPane.WARNING_MESSAGE);
        return;
      }
      fileName=   JOptionPane.showInputDialog ("Please enter your desired filename." );

      Application.solveEquation(divident, divisor, true, true);
    }
  }

  //
  // This method generates the constructor & JFrame
  //
  public Interface(){
    jFrame.setResizable(false);
    jFrame.setSize(new Dimension(640, 550));
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jFrame.setVisible(true);
    temporaryContainer = new Container();
    temporaryContainer.setLayout(null); //no layout

    JMenuBar jMenuBar = new JMenuBar();
    JMenu file = new JMenu("File");


    JMenuItem newExportSolution = new JMenuItem("Export Solution", KeyEvent.VK_N);
    JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);
    final JTextField dividendJText = new JTextField();
    final JTextField divisorJText = new JTextField();

    newExportSolution.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        // Do magic here
        export(dividendJText.getText(), divisorJText.getText());
      }
    });

    exit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        System.exit(0);
      }
    });

    file.add(newExportSolution);
    file.add(exit);

    jMenuBar.add(file);

    jFrame.setJMenuBar(jMenuBar);

    JButton buttonOne = new JButton("Calculate");
    buttonOne.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        if (dividendJText.getText().equals("") || divisorJText.getText().equals("")){
          JOptionPane.showMessageDialog (null, "Error: You cannot leave a field blank!", "Error: Blank field", JOptionPane.WARNING_MESSAGE);

        }
        else{
          ArrayList<Term> divident = new ArrayList<Term>();
          ArrayList<Term> divisor = new ArrayList<Term>();

          divident = Utility.generateTerms(dividendJText.getText());
          divisor = Utility.generateTerms(divisorJText.getText());

          Collections.sort(divident);
          Collections.sort(divisor);

          divident = Utility.fillTerms(divident, Utility.findGreatestExponent(divident));
          divisor = Utility.fillTerms(divisor, Utility.findGreatestExponent(divisor));

          if (Utility.findGreatestExponent(divident) < Utility.findGreatestExponent(divisor)){
            JOptionPane.showMessageDialog (null, "Notice; Dividing polynomial with a larger exponent on the base is unsupported / is in beta.", "Notice: Invalid operation.", JOptionPane.WARNING_MESSAGE);
          }

          if (Utility.findGreatestExponent(divisor) > 2){
            JOptionPane.showMessageDialog (null, "Error: You cannot divide by a non-quadratic in this version.", "Error: Divisor degree is too large", JOptionPane.WARNING_MESSAGE);
            return;
          }

          Application.solveEquation(divident, divisor, false, true);
        }
      }
    });
    JButton buttonTwo = new JButton("Export Solution");

    buttonTwo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        //Execute when button is pressed
        export(dividendJText.getText(), divisorJText.getText());
      }
    });

    JButton buttonThree = new JButton("Instructions");
    buttonThree.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e)
      {
        //Execute when button is pressed
        JOptionPane.showMessageDialog (null, "Instructions: Enter the divident in the textbox, in the format as:\n"
                                         + "x^2+3x+4\n", "Instructions", JOptionPane.WARNING_MESSAGE);

      }
    });

    JLabel div = new JLabel("Enter the dividend:");
    JLabel div2 = new JLabel("Enter the divisor:");

    temporaryContainer.add(buttonOne);
    temporaryContainer.add(buttonTwo);
    temporaryContainer.add(buttonThree);
    temporaryContainer.add(div);
    temporaryContainer.add(div2);
    temporaryContainer.add(dividendJText);
    temporaryContainer.add(divisorJText);



    Insets insets = temporaryContainer.getInsets();
    Dimension size = buttonOne.getPreferredSize();
    buttonOne.setBounds(250 + insets.left, 470 + insets.top,
                 size.width, size.height);
    size = buttonTwo.getPreferredSize();
    buttonTwo.setBounds(10 + insets.left, 470 + insets.top,
                 size.width, size.height);
    size = buttonThree.getPreferredSize();
    buttonThree.setBounds(515 + insets.left, 470 + insets.top,
                 size.width, size.height);
    div.setBounds(7, 20, 200, 30);
    div2.setBounds(7, 50, 200, 30);
    dividendJText.setBounds(220, 20, 400, 30);
    divisorJText.setBounds(220, 50, 400, 30);
    jFrame.add(temporaryContainer);
    jFrame.repaint();
    jFrame.validate();
  }

}
