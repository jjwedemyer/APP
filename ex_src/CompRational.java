package app.exercise.algebra;

public class CompRational extends Rational implements Comparable<T>{
  public CompRational(long zaehler, long nenner) {
    super(zaehler, nenner);
  }
  public int compareTo(Rational to) {
    if(this.equals(to)) {
      return 0;
    } else if( ((double)this.getZaehler() / (double)this.getNenner()) > ((double)to.getZaehler() / (double)to.getNenner())) {
      return 1;
    } else return -1;
  }
}
