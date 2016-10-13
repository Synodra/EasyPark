/*  
 *  ------ [C_08] - sending smart parking values via 868 -------- 
 *  
 *  Explanation: This example reads the status of a parking lot using 
 *  the parking sensor board and sends its status via XBee 868.
 *  
 *  Copyright (C) 2015 Libelium Comunicaciones Distribuidas S.L. 
 *  http://www.libelium.com 
 *  
 *  This program is free software: you can redistribute it and/or modify 
 *  it under the terms of the GNU General Public License as published by 
 *  the Free Software Foundation, either version 3 of the License, or 
 *  (at your option) any later version. 
 *  
 *  This program is distributed in the hope that it will be useful, 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 *  GNU General Public License for more details. 
 *  
 *  You should have received a copy of the GNU General Public License 
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 *  
 *  Version:           0.2 
 *  Design:            David Gascón 
 *  Implementation:    Javier Siscart
 */
#include <WaspXBee802.h>
#include <WaspFrame.h>
#include <WaspSensorParking.h>

// Destination MAC address
char MAC_ADDRESS[]="0013A2004125EAFC";

// Node identifier
char NODE_ID[]="N01";

// Sleeping time DD:hh:mm:ss
char sleepTime[] = "00:00:01:00";    

// Sensor variables
int temperature;
boolean status;

// define variable
uint8_t error;

void setup()
{
  // 0. Init USB port for debugging
  USB.ON();
  USB.println(F("C_08 Example"));

  ////////////////////////////////////////////////
  // 1. Initial message composition
  ////////////////////////////////////////////////
  
  // 1.1 Set mote Identifier (16-Byte max)
  frame.setID(NODE_ID);	
  
  // 1.2 Set frame maximum size (Link encryp disabled, AES encryp disabled)
  frame.setFrameSize(XBEE_868, DISABLED, DISABLED);
  USB.print(F("\nframe size (xbee802, UNICAST_64B, XBee encryp Disabled, AES encryp Disabled):"));
  USB.println(frame.getFrameSize(),DEC);   
  
  // 1.3 Create new frame
  frame.createFrame(ASCII);  

  // 1.4 Set frame fields (String - char*)
  frame.addSensor(SENSOR_STR, (char*) "C_08 Example");

  // 1.5 Print frame
  frame.showFrame();


  ////////////////////////////////////////////////
  // 2. Send initial message
  ////////////////////////////////////////////////

  // 2.1 Power XBee
  xbee802.ON();
  
  // 2.2 Set destination XBee parameters to packet
  error = xbee802.send( MAC_ADDRESS, frame.buffer, frame.length );


  if( error == 0 ) 
  {
    USB.println(F("ok"));
  }
  else 
  {
    USB.println(F("error"));
  }

  // 2.3 Communication module to OFF
  xbee802.OFF();
  delay(100);
  
  
  ////////////////////////////////////////////////
  // 3. Parking calibration process
  ////////////////////////////////////////////////

  // Once "start calibration" command is received, calibration starts. 
  SensorParking.loadReference();
  SensorParking.ON();
  delay(100);
  SensorParking.calibration();  
  SensorParking.OFF();


}

void loop()
{

  ////////////////////////////////////////////////
  // 4. Measure corresponding values
  ////////////////////////////////////////////////
  USB.println(F("Measuring sensors..."));

  // 4.1 Turn on the sensor board
  SensorParking.setBoardMode(SENS_ON);
  delay(10);
  
  // 4.2 Prepare parking sensor
  SensorParking.readParkingSetReset();
  
  // 4.3 Read parking temperature
  temperature = SensorParking.readTemperature();
  
  // 4.4 calculate reference for status stimation
  SensorParking.calculateReference(temperature);
  
  // 4.5 Estimate parking lot status
  status = SensorParking.estimateState();

  // 4.6 Turn OFF parking sensor board.
  SensorParking.setBoardMode(SENS_OFF);

  // 4.7 Set RTC on and get time
  RTC.ON();
  RTC.getTime();

  ////////////////////////////////////////////////
  // 5. Message composition
  ////////////////////////////////////////////////

  // 5.1 Create new frame
  frame.createFrame(ASCII);  

  // 5.2 Add frame fields
  frame.addSensor(SENSOR_PS, status);
  frame.addSensor(SENSOR_MF, SensorParking.valueX, SensorParking.valueY, SensorParking.valueZ);
  frame.addSensor(SENSOR_TIME, RTC.hour, RTC.minute, RTC.second );  
  frame.addSensor(SENSOR_BAT, PWR.getBatteryLevel() );
  
  // 5.3 Print frame
  // Example: <=>#35689722#N01#3#PS:1#MF:969;300;282#TIME:13-53-37#BAT:63#
  frame.showFrame();


  ////////////////////////////////////////////////
  // 6. Send message
  ////////////////////////////////////////////////

  // 6.1 Power XBee
  xbee802.ON();

  // 6.2 Set destination XBee parameters to packet
  error = xbee802.send( MAC_ADDRESS, frame.buffer, frame.length );

  // 6.3 Check TX flag
  if( xbee802.error_TX == 0 ) 
  {
    USB.println(F("ok"));
  }
  else 
  {
    USB.println(F("error"));
  }

  // 6.4 Communication module to OFF
  xbee802.OFF();
  delay(100);


  ////////////////////////////////////////////////
  // 7. Entering Deep Sleep mode
  ////////////////////////////////////////////////
  USB.println(F("Going to sleep..."));
  USB.println();
  PWR.deepSleep(sleepTime, RTC_OFFSET, RTC_ALM1_MODE1, ALL_OFF);
}


