import xmlrpclib.*;

XmlrpcClient client;

void setup(){
  client = new XmlrpcClient("http://192.168.5.203:8081/RPC2");
}

void draw(){
  
}

// Use arrow keys to draw on the server canvas
void keyPressed(){
  int amount = 10;
  //int[] go = {20, 20};
  int[] go = new int[2];
  
  if(keyCode == UP){
    go[0] = 0;
    go[1] = -amount;
  }else if (keyCode == DOWN){
    go[0] = 0;
    go[1] = amount;  
  }else if(keyCode == LEFT){
    go[0] = -amount;
    go[1] = 0;  
  }else if(keyCode == RIGHT){
    go[0] = amount;
    go[1] = 0;  
  }
  
  println(client.execute("testserver.update", go));
}
