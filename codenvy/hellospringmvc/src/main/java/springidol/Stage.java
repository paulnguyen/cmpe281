package springidol;

public class Stage {
  private Stage() {
  }

  private static class StageSingletonHolder {
    static Stage instance = new Stage(); 
  }

  public static Stage getInstance() {
    Stage obj = StageSingletonHolder.instance; 
    System.out.println( "Stage Object Ref: " + obj.toString() ) ;
    return obj ;
  }
}