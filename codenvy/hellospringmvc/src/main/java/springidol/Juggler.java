package springidol;

public class Juggler implements Performer {
  private int beanBags = 3;

  public Juggler() {
  }

  public Juggler(int beanBags) {
    this.beanBags = beanBags;
  }

  public String perform() throws PerformanceException {
    System.out.println("JUGGLING " + beanBags + " BEANBAGS");
    return ( "JUGGLING " + beanBags + " BEANBAGS" ) ;   
  }
}