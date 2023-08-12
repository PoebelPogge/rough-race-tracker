#include <Arduino.h>
/*
 * --------------------------------------------------------------------------------------------------------------------
 * Example sketch/program showing how to read data from a PICC to serial.
 * --------------------------------------------------------------------------------------------------------------------
 * This is a MFRC522 library example; for further details and other examples see: https://github.com/miguelbalboa/rfid
 *
 * Example sketch/program showing how to read data from a PICC (that is: a RFID Tag or Card) using a MFRC522 based RFID
 * Reader on the Arduino SPI interface.
 *
 * When the Arduino and the MFRC522 module are connected (see the pin layout below), load this sketch into Arduino IDE
 * then verify/compile and upload it. To see the output: use Tools, Serial Monitor of the IDE (hit Ctrl+Shft+M). When
 * you present a PICC (that is: a RFID Tag or Card) at reading distance of the MFRC522 Reader/PCD, the serial output
 * will show the ID/UID, type and any data blocks it can read. Note: you may see "Timeout in communication" messages
 * when removing the PICC from reading distance too early.
 *
 * If your reader supports it, this sketch/program will read all the PICCs presented (that is: multiple tag reading).
 * So if you stack two or more PICCs on top of each other and present them to the reader, it will first output all
 * details of the first and then the next PICC. Note that this may take some time as all data blocks are dumped, so
 * keep the PICCs at reading distance until complete.
 *
 * @license Released into the public domain.
 *
 * Typical pin layout used:
 * -----------------------------------------------------------------------------------------
 *             MFRC522      Arduino       Arduino   Arduino    Arduino          Arduino
 *             Reader/PCD   Uno/101       Mega      Nano v3    Leonardo/Micro   Pro Micro
 * Signal      Pin          Pin           Pin       Pin        Pin              Pin
 * -----------------------------------------------------------------------------------------
 * RST/Reset   RST          9             5         D9         RESET/ICSP-5     RST
 * SPI SS      SDA(SS)      10            53        D10        10               10
 * SPI MOSI    MOSI         11 / ICSP-4   51        D11        ICSP-4           16
 * SPI MISO    MISO         12 / ICSP-1   50        D12        ICSP-1           14
 * SPI SCK     SCK          13 / ICSP-3   52        D13        ICSP-3           15
 *
 * More pin layouts for other boards can be found here: https://github.com/miguelbalboa/rfid#pin-layout
 */

#include <SPI.h>
#include <MFRC522.h>

#define RST_PIN 5 // Configurable, see typical pin layout above
#define SS_PIN 10 // Configurable, see typical pin layout above

#define MESSAGE_DELAY 5000
#define RACER 3

const String sendMessages[RACER][2];
const String COMMAND_RACE_START = "RACE_START";
const String COMMAND_RACE_STOP = "RACE_STOP";

MFRC522 mfrc522(SS_PIN, RST_PIN); // Create MFRC522 instance

boolean raceStart = false;
boolean raceRunning = false;

void sendMeasurement(String);
boolean transmitted(String, int);
void dumpMessages();

void setup()
{
  Serial.begin(9600);                // Initialize serial communications with the PC
  while (!Serial);                   // Do nothing if no serial port is opened (added for Arduinos based on ATMEGA32U4)
  SPI.begin();                       // Init SPI bus
  mfrc522.PCD_Init();                // Init MFRC522
  delay(4);                          // Optional delay. Some board do need more time after init to be ready, see Readme
  pinMode(LED_BUILTIN, OUTPUT);
  mfrc522.PCD_DumpVersionToSerial(); // Show details of PCD - MFRC522 Card Reader details
  Serial.println(F("Scan PICC to see UID, SAK, type, and data blocks..."));
}

void loop()
{
  if (Serial.available() > 0)
  {
    String command = Serial.readString();
    command.trim();

    char type = command.charAt(0);
    String body = command.substring(2);

    Serial.println("type: " + String(type) + " body: " + body);
    
    if(type == 'C'){
      if(COMMAND_RACE_START.equals(body)){
        raceStart = true;
        raceRunning = true;
      } else if (COMMAND_RACE_STOP.equals(body)){
        raceStart = false;
        raceRunning = false;
      }
    }
  }

  if(raceRunning){
    digitalWrite(LED_BUILTIN, HIGH);
  } else {
    digitalWrite(LED_BUILTIN, LOW);
  }

  // Reset the loop if no new card present on the sensor/reader. This saves the entire process when idle.
  if (!mfrc522.PICC_IsNewCardPresent())
  {
    return;
  }

  // Select one of the cards
  if (!mfrc522.PICC_ReadCardSerial())
  {
    return;
  }

  // Show UID on serial monitor
  String content = "";

  for (byte i = 0; i < mfrc522.uid.size; i++)
  {
    // Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
    // Serial.print(mfrc522.uid.uidByte[i], HEX);
    content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? "0" : ""));
    content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  content.toUpperCase();
  if (!transmitted(content, MESSAGE_DELAY) && raceRunning)
  {
    sendMeasurement(content);
    Serial.println("M:" + content + ";");
  }
  // dumpMessages();
  //delay(200);
}

void sendMeasurement(String racerId)
{
  String empty = "";
  for (int i = 0; i < RACER; i++)
  {
    if (empty.equals(sendMessages[i][0]))
    {
      sendMessages[i][0] = racerId;
      sendMessages[i][1] = String(millis());
      return;
    }
  }
}

boolean transmitted(String raccerId, int delay)
{
  for (int i = 0; i < RACER; i++)
  {
    unsigned long delayedValue = atol(sendMessages[i][1].c_str()) + delay;
    if (raccerId.equals(sendMessages[i][0]) && delayedValue > millis())
    {
      //Serial.println("{racerId: " + sendMessages[i][0] + " timestamp: " + sendMessages[i][1] + " delayed Timestamp:" + delayedValue + " millis: " + String(millis()) + "}");

      return true;
    }
    else
    {
      if (delayedValue < millis())
      {
        sendMessages[i][0] = "";
        sendMessages[i][1] = "";
        return false;
      }
    }
  }
  return false;
}

void dumpMessages()
{
  Serial.println("Messages: ->");
  for (int i = 0; i < RACER; i++)
  {
    Serial.print("{racerId: " + sendMessages[i][0] + " -> timestamp: " + sendMessages[i][1] + "}; ");
  }
  Serial.print("\n");
}
