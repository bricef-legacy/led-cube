
import processing.serial.*;

Serial myPort;  // Create object from Serial class
int val;        // Data received from the serial port

void setup() 
{
 
  println(Serial.list());
  String portName = Serial.list()[1];
  myPort = new Serial(this, portName, 9600);//must match the baud rate on the arduino.
}
int i=0;
int returned=0;
 byte[] buffer = {12,11,10,9,8,7,6,5,4,3,2,1,0};
void draw() {
  int del=100;
  
  while(returned!=32){
    myPort.write(31);
    print("sending[31] ");
    delay(del);
    returned=myPort.read();
    print("received[");print(returned);println("] ");
  }
   
  delay(del);
  
  for(; i<13; i++){
    myPort.write(buffer[i]);
    print(buffer[i]);print(" >> ");
    delay(del);
    println(myPort.read());
  }
}

