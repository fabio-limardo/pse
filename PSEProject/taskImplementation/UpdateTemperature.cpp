/*
 * UpdateTemperature.cpp

 *
 *  Created on: 20 ago 2016
 *      Author: Fabio Limardo
 */

#include "Arduino.h"
#include "../messagingService/MsgService.h"
#include "../taskDeclaration/UpdateTemperature.h"
#include "../Services/Thermometer.h"
#include "MemoryFree-master/MemoryFree.h"

long startTimeB, endTimeB, deltaTimeB;
float temp;

UpdateTemperature::UpdateTemperature(int sensorPin){
	this->sensorPin = sensorPin;
	//pinMode(13, OUTPUT);
}
void UpdateTemperature::init(int period,Data* data){
	Task::init(period,data);
	sensor = new Thermometer(sensorPin);
}

void UpdateTemperature::tick(){
	sensor->updateValue();
	temp = sensor->getValue();
	data->updateTemperature(temp);

//	aJsonObject* jObj = MsgService.createMsg("RASPBERRY", "TEMPERATURE", "NEWVALUE", temp);
//	MsgService.sendJsonMsg(jObj);
//	aJson.deleteItem(jObj);

	//digitalWrite(13,HIGH);

}





