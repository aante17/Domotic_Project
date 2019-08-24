#include <ArduinoJson.h> // esp32 library for reading json code
#include <ESP32Servo.h> // servo motor library ti open/close door 


Servo servo1; // create instance of the servos for the door
Servo servo2;
int servo1Pin = 32; // create a variable to save the value of the digital pin that they will  be attached to
int servo2Pin = 33;
int pos = 0;


#include <WiFi.h>                                               // esp32 WiFi library
#include <IOXhop_FirebaseESP32.h>                                             // firebase library

#define FIREBASE_HOST "https://housetic.firebaseio.com"
#define FIREBASE_AUTH "W6EQPDdqioYizI6r7ig7039ffkwK22LWUxh7p0ig"                    // the secret key generated from firebase
#define WIFI_SSID "HUAWEI Mate 10"                                          // input your home or public wifi name
#define WIFI_PASSWORD "0f1991634d4d"                                    //password of wifi ssid

String fireStatus = "";                                                     // led status received from firebase
int lPins[] = {5, // comedor/luces
              18, // dormitorio/persianas
              19, // garage/luces
              21, // dormitorio/luces
              13, // jardin/alarma
              12, // jardin/luces
              14, // sala/luces
              27  // sala/ventilador
              };
String lDisp[]={"comedor/luces","dormitorio/persianas","garage/luces","dormitorio/luces",
                "jardin/alarma","jardin/luces","sala/luces","sala/ventilador"};

void recorrerDisps(){           // method used to iterate each device that works with an On/Off logic (lights,fans, etc) 
  for (int i=0;i<8;i++){
    fireStatus = Firebase.getString(lDisp[i]);                     // get led status input from firebase
    if (fireStatus == "ON") {                         // compare the input of led status received from firebase
      Serial.println(lDisp[i]+" ON");
      digitalWrite(lPins[i], LOW);                                                         // make output led ON
    }
    else if (fireStatus == "OFF") {              // compare the input of led status received from firebase
      Serial.println(lDisp[i]+" OFF");
      digitalWrite(lPins[i], HIGH);                                                         // make output led OFF
    }
    else {
      Serial.println("Wrong Credential! Please send ON/OFF");  // Validation that is throw if there is a manual change in data base 
    }
    
 }
}


void controlPuerta(){
  // cambiar estado de puerta (abierta o cerrada)
  fireStatus = Firebase.getString("garage/puertas");                     // get servo status input from firebase
    if (fireStatus == "ON") {                         // compare the input of servos status received from firebase
      Serial.println("Puerta abierta");
      servo1.write(0);                                                        // make output servos go to position 0
      servo2.write(0);
    }
    else if (fireStatus == "OFF") {              // compare the input of servos status received from firebase
      Serial.println("Puerta cerrada");
      servo1.write(180);                                                        // make output servos go to position 180
      servo2.write(180);
    }
    else {
      Serial.println("Wrong Credential! Please send ON/OFF");     // Validation that is throw if there is a manual change in data base
    }
}




void setup() {  // this code will run once whe the code runs 
  
  Serial.begin(9600);  // begin to work in Serial screen at a 9600 baud
  delay(1000);

  servo1.attach(servo1Pin);     // attach the servos to the specified digital pins
  servo2.attach(servo2Pin);

  
  for (int i=0;i<8;i++){       // initialize each pin that works with On/Off logic as an output
    pinMode(lPins[i],OUTPUT);
  }
  
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);                                      //try to connect with wifi
  Serial.print("Connecting to ");
  Serial.print(WIFI_SSID);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("Connected to ");
  Serial.println(WIFI_SSID);
  Serial.print("IP Address is : ");
  Serial.println(WiFi.localIP());                                                      //print local IP address
  Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);                                       // connect to firebase
  
}


void loop() {    //loop that will run continiusly until the code stops 
  
  recorrerDisps();   //    calling methos to iterate over all the devices and the servo
  controlPuerta();  
  Serial.println("Fin");   // End of one of the void loops
  delay(100);
}
