/*
 * CheckDistance.cpp
 *
 *  Created on: 21 ago 2016
 *      Author: Fabio Limardo
 */

#include "Arduino.h"
#include "string.h"
#include "aJson.h"
#include "../messagingService/MsgService.h"
#include "../taskDeclaration/CheckDistance.h"
#include "../Services/SafetyDistance.h"
#include "MemoryFree-master/MemoryFree.h"

long startTimeA,endTimeA, deltaTimeA;

CheckDistance::CheckDistance(int trigPin, int echoPin, char type,float minDistance){
	this->trigPin = trigPin;
	this->echoPin = echoPin;
	this->type = type;
	this->minDistance = minDistance; //Dipende dalla velocità, ocio dunque
}

void CheckDistance::init(int period,Data* data){
	Task::init(period,data);
	state = SAFETY;
	checker = new SafetyDistance(trigPin, echoPin);

}


void CheckDistance::tick(){

	if(!buffer->getEventList()->isEmpty()){
		eventType event = buffer->getEventList()->pop();
		temperature = data->getCurrentState().temperature;
	}
	temperature = data->getCurrentState().temperature;
	float value = checker->distance(temperature);

	switch(type){
	case 'F':
		data->updateFront(value);
		typeC = "F";
		break;
	case 'L':
		data->updateLeft(value);
		typeC = "L";
		break;
	case 'R':
		data->updateRight(value);
		typeC = "R";
		break;
	}

	aJsonObject* jObj = MsgService.createMsg("RASPBERRY","DISTANCE",typeC,value);
	MsgService.sendJsonMsg(jObj);
	aJson.deleteItem(jObj);


}



