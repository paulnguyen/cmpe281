public class helloController {
  public String name { get; set; }
  public String message { get; private set; }
  public PageReference hello() {
    message = 'Hello, ' + name;
    return null;
  }
}