#include "Arduino.h"
#include "Wire.h"
#include "aJSON.h"
#include "componentDeclaration/I2CTemperatureSensor.h"
#include "componentDeclaration/Led.h"
#include "componentDeclaration/Photoresistor.h"
#include "componentDeclaration/UltrasonicSensor.h"

#include "messagingService/MsgService.h"
#include "scheduler/Scheduler.h"
#include "taskDeclaration/AutonomousHeadlight.h"
#include "taskDeclaration/CheckDistance.h"
#include "taskDeclaration/UpdateTemperature.h"
#include "taskDeclaration/AutonomousPhase.h"
#include "taskDeclaration/Broker.h"
#include "taskDeclaration/TempSender.h";
#include "../MemoryFree-master/MemoryFree-master/MemoryFree.h"


#define TEMPSENSOR_ADDRESS 72
#define LED_PIN 13
#define LIGHTSENSOR_PIN A0
#define DIST_TRIG_RIGHT 12
#define DIST_ECHO_RIGHT 11
#define DIST_TRIG_FRONT 10
#define DIST_ECHO_FRONT 9
#define DIST_TRIG_LEFT 8
#define DIST_ECHO_LEFT 7
#define LED_ON_THRESOLD 120

Scheduler scheduler;
Task* updateTemperature;
Task* broker;
unsigned long lastReading;
bool flag = false;

void setup(){
	//Serial.begin(115000);
	MsgService.init();
	Wire.begin();

	scheduler.init(200);

	Data* data = new Data();

	Task* autonomousPhase = new AutonomousPhase();
	autonomousPhase->init(200,data);
//
	Task* checkDistanceFront = new CheckDistance(DIST_TRIG_FRONT,DIST_ECHO_FRONT,'F',100);
	checkDistanceFront->init(200,data);
//
	Task* checkDistanceRight = new CheckDistance(DIST_TRIG_RIGHT,DIST_ECHO_RIGHT,'R',100);
	checkDistanceRight->init(200,data);
//
	Task* checkDistanceLeft = new CheckDistance(DIST_TRIG_LEFT,DIST_ECHO_LEFT,'L',100);
	checkDistanceLeft->init(200,data);
//
	Task* autonomousHeadlight = new AutonomousHeadlight(LED_PIN,LIGHTSENSOR_PIN,LED_ON_THRESOLD);
	autonomousHeadlight->init(400,data);
//
	updateTemperature = new UpdateTemperature(TEMPSENSOR_ADDRESS);
	updateTemperature->init(400,data);
//
	Task* tempSender = new TempSender();
	tempSender->init(800,data);


	broker = new Broker(
			autonomousPhase,
			autonomousHeadlight,
			checkDistanceLeft,
			checkDistanceRight,
			checkDistanceFront);
//	broker = new Broker();
	broker->init(400,data);


	scheduler.addTask(broker);
	//scheduler.addTask(autonomousPhase);
	scheduler.addTask(checkDistanceFront);
	scheduler.addTask(checkDistanceRight);
	scheduler.addTask(checkDistanceLeft);
	scheduler.addTask(autonomousHeadlight);
	scheduler.addTask(tempSender);

}

void loop(){

		scheduler.sleep();
		//scheduler.schedule();
		/*
		 * Impossible utilizzare il servio di aggiornamento del termometro all'interno dello sleep in quanto
		 * in quanto all'interno dell'ISR del timer le interruzioni sono disabilitate compromettendo dunque
		 * il corretto funzionamento del termometro che fa riferimento al sensore di temperarura che utilizza I2C come
		 * protocollo di trasferimento che necessita delle interruzioni abilitate.
		 */

	  if (millis () - lastReading >= 300)
		    {
			  lastReading = millis ();
			  updateTemperature->tick();
	    }


}



