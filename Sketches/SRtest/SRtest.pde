//shift register test....

//Pin Definiitions

int sPin = 8;
int sclk = 12;
int storeclk = 10;
int i;



void setup(void) 
{
  pinMode(sPin, OUTPUT);
  pinMode(sclk, OUTPUT);
  pinMode(storeclk, OUTPUT);
  
  
}


void loop(void) 
{
  digitalWrite(sPin, LOW);
  for (i=0; i<8; i++)
  {
    digitalWrite(sclk, LOW);
    //delay(10);
    digitalWrite(sclk, HIGH);
    //delay(10);
  }
  
  delay(500);
  digitalWrite(storeclk, HIGH);
  delay(500);
  digitalWrite(storeclk, LOW);
  
  digitalWrite(sPin, HIGH);
  for (i=0; i<6; i++)
  {
    digitalWrite(sclk, LOW);
    //delay(10);
    digitalWrite(sclk, HIGH);
    //delay(10);
  }
  
  delay(500);
  digitalWrite(storeclk, HIGH);
  delay(500);
  digitalWrite(storeclk, LOW);
    
}
