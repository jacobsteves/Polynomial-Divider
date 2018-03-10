//
// Sythetic Division Calculator
// Term Class
// Jacob Steves
//

package src;

public class Term implements Comparable<Term>{
  // For an example of the following terms:
  // 3x^2 =>
  //        power = 2
  //    and
  //        coefficient = 3

  private int power = 0x3ff;
  private double coefficient = 0x3ff;

  //
  // This method returns and compares an item.
  // @return int The return val.
  //
  public int compareTo(Term o){
    if (o.getPower() < power){
      return 0;
    }
    return 1;
  }

  //
  // This method returns the coefficient.
  // @return double Coefficient
  //
  public double getCoefficient(){
    return coefficient;
  }

  //
  // This method returns the power.
  // @return int This is the power
  //
  public int getPower(){
    return power;
  }

  //
  // This method is the class constructor
  //
  public Term(int power, double coefficient){
    this.power = power;
    this.coefficient = coefficient;
  }
}
