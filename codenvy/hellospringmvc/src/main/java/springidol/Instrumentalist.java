package springidol;

public class Instrumentalist implements Performer {
  public Instrumentalist() {
    this.age = 0 ;
  }
 
  public String perform() throws PerformanceException {
    System.out.print("Playing " + song + " : ");
    instrument.play();
    return " " ;
  }


  private int age ;
  public int getAge() { return this.age; }
  public void setAge(int newAge) { this.age = newAge ; }


  private String song;
  public void setSong(String song) { 
    this.song = song;
  }
  public String getSong() {
    return song;
  }
  public String screamSong() {
    return song;
  }


  private Instrument instrument;
  public void setInstrument(Instrument instrument) { 
    this.instrument = instrument;
  }
  



}