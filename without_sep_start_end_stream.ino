const int lock=9;
const int led =13;
int id = 0;
int button=4;
boolean doorState=false;


#include <Adafruit_Fingerprint.h>
#include <SoftwareSerial.h>
#include <EEPROM.h>

//this is the program we shall be using
//his too
int getFingerprintIDez();

// pin #2 is IN from sensor (GREEN wire)
// pin #3 is OUT from arduino  (WHITE wire)
SoftwareSerial mySerial(2, 3);


Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial);

void setup()  
{
  pinMode(13, OUTPUT);
  pinMode(button, OUTPUT);
  pinMode(lock, OUTPUT);
  Serial.begin(9600);
  //Serial.println("fingertest");

  // set the data rate for the sensor serial port
  finger.begin(57600);
  
  if (finger.verifyPassword()) {
    //Serial.println("Found fingerprint sensor!");
  } else {
    //Serial.println("Did not find fingerprint sensor :(");
    while (1);
  }
  //Serial.println("Waiting for valid finger...");
}

void loop()                     // run over and over again
{
 // Serial.println("Type in the ID # you want to save this finger as...");
int condition=0;
char c;
      //you can replace with this later => condition=(digitalRead(button))  
      
      if(condition=(Serial.available()>0))
      {
      id=(EEPROM.read(1)+1);
      getFingerprintEnroll(id);
      Serial.end();
      Serial.begin(9600);
      }
      else
        {
          getFingerprintID();
          delay(50);            //don't ned to run this at full speed.
        }
}

uint8_t getFingerprintID() 
{
  uint8_t p = finger.getImage();
  switch (p) {
    case FINGERPRINT_OK:
      //Serial.println("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      //Serial.println("0");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("IE");//IE- IMAGING ERROR
      return p;
    default:
     
      Serial.print("UE");
       
      Serial.println("");
      return p;
       
  }

  // OK success!

  p = finger.image2Tz();
  switch (p) {
    case FINGERPRINT_OK:
      //Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
       
      Serial.print("Could not find fingerprint features");
       
      Serial.println("");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
     
      Serial.print("Could not find fingerprint features");
       
      Serial.println("");
      return p;
    default:
      Serial.println("UE");//Unknown Error
      return p;
  }
  
  // OK converted!
  p = finger.fingerFastSearch();
  if (p == FINGERPRINT_OK) 
  {
    //To find a print match
    //Serial.println("Found a print match!");
    digitalWrite(13, HIGH);
    delay(300);
    digitalWrite(13, LOW);
  } 
  else if (p == FINGERPRINT_PACKETRECIEVEERR) 
  {
    Serial.println("Communication error");
    return p;
  } 
  else if (p == FINGERPRINT_NOTFOUND) 
  {
    //no match found
    Serial.print("");
     
    Serial.print("NM");//NO MATCH
     
    Serial.println("");
    
    digitalWrite(13, HIGH);
    delay(150);
    digitalWrite(13, LOW);
    delay(150);
    digitalWrite(13, HIGH);
    delay(150);
    digitalWrite(13, LOW);
    return p;
     
  }
  else 
  {
    Serial.println("UE");
    return p;
  }   
  
  // found a match!
  //Serial.println("Found ID #"); 
  Serial.print(""); 
   
  Serial.print(finger.fingerID); 
    
  Serial.println("");
  //confidence
  //Serial.println(finger.confidence); 
  doorLock();  
}

// returns -1 if failed, otherwise returns ID #
int getFingerprintIDez() {
  uint8_t p = finger.getImage();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.image2Tz();
  if (p != FINGERPRINT_OK)  return -1;

  p = finger.fingerFastSearch();
  if (p != FINGERPRINT_OK)  return -1;
  
  // found a match!
  Serial.print("Found ID #"); 
  Serial.print(" "+finger.fingerID); 
  Serial.print(" with confidence of "); Serial.println(finger.confidence);
  return finger.fingerID; 
   
}
void doorLock(){
  if(doorState==false){
    digitalWrite(lock, HIGH);
    doorState=true;
  delay(2500);
  digitalWrite(lock, LOW);
  }
}


uint8_t getFingerprintEnroll(int id) 
{
 Serial.print("");
  
 Serial.print("NW");// NW-Expected id of next enrolling finger
   
 Serial.println("");
 
  
 Serial.print(id);
  
  Serial.println("");
  
  int p = -1;
  //waiting to enroll finger
   Serial.print(" "); 
    
  Serial.print("WF");//WF - waiting to enroll finger
   
   Serial.println("");
   
  while (p != FINGERPRINT_OK) 
  {
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      //mike when image is taken
      //Serial.println("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
     // Serial.println(".");
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      break;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("IE");//Imaging error
      break;
    default:
    //mike When error is unknown
      //Serial.println("Unknown error");
      break;
      
    }
  }
  // OK success!

  p = finger.image2Tz(1);
  switch (p) {
    case FINGERPRINT_OK:
    //mike when image is converted
    //  Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      
      Serial.print("Could not find fingerprint features");
       
      Serial.println("");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
       
      Serial.print("Could not find fingerprint features");
       
      Serial.println("");
      return p;
    default:
      Serial.println("UE"); // Unknown Error
      return p;
  }
  //mike remove finger
   Serial.print("");
    
  Serial.print("RF"); // RF - remove finger
   
   Serial.println("");
   
  delay(2000);
  p = 0;
  while (p != FINGERPRINT_NOFINGER) {
    p = finger.getImage();
  }

  p = -1;
  //mike place the same finger again
   Serial.print("");
    
  Serial.print("PF");//PF - place the same finger again
   
  Serial.println("");
   
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
    //mike image taken
     Serial.print("");
      
      Serial.print("IT"); //IT - Image Taken
       
      Serial.println("");
      break;
    case FINGERPRINT_NOFINGER:
      //Serial.print(".");
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      break;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Imaging error");
      break;
    default:
     
      Serial.print("UE");
       
      Serial.println("");
       
      break;
    }
  }

  // OK success!

  p = finger.image2Tz(2);
  switch (p) {
    case FINGERPRINT_OK:
    //mike image converted
      //Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
       
      Serial.print("Could not find fingerprint features");
       
      Serial.println("");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
       
      Serial.print("Could not find fingerprint features");
       
      Serial.println("");
      return p;
    default:
      Serial.println("UE");// UE - Unknown error
      return p;
  }
  
  
  // OK converted!
  p = finger.createModel();
  if (p == FINGERPRINT_OK) 
  {
    //mike when prints have matched during taking the finger print comparison
     Serial.print("");
      
    Serial.print("PM");//PM - Prints Matched
     
    Serial.println("");
    //return p;
  } 
  else if (p == FINGERPRINT_PACKETRECIEVEERR) 
  {
    Serial.println("Communication error");
    return p;
  } 
  else if (p == FINGERPRINT_ENROLLMISMATCH) 
  {
    //this is to show finger prints did not match when comparing the two finger prints during registration
    //mike Fingerprints did not match
    Serial.println("FN");// FN -Finger prints did not match
    return p;
  } 
  else 
  {
    Serial.println("UE");//Unknown error
    return p;
  }   
   Serial.print("");
    
    //This is the id is ** part of the program
  //Serial.print("ID");
     
    //Serial.println("");

  Serial.print("IS");
    Serial.println("");
    
  id=0; 
  id=EEPROM.read(1)+1;
  Serial.print(" ");
   
  Serial.print(id);
   
  Serial.println("");
   
  p = finger.storeModel(id);
  
  if (p == FINGERPRINT_OK) 
  {
    //This is to store the image, in my case it will show the id of the finger print
    //Serial.println("Stored!");
    EEPROM.write(1, id);
  } 
  else if (p == FINGERPRINT_PACKETRECIEVEERR) 
  {
    Serial.println("Communication error");
    return p;
  } 
  else if (p == FINGERPRINT_BADLOCATION) 
  {
    Serial.println("Could not store in that location");
    return p;
  } 
  else if (p == FINGERPRINT_FLASHERR) 
  {
    Serial.println("Error writing to flash");
    return p;
  } else 
  {
     
    Serial.print("UE");
     
    Serial.println("");
    return p;
  }   
}
