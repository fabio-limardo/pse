/*
 * AutonomousHeadlight.cpp
 *
 *  Created on: 19 ago 2016
 *      Author: Fabio Limardo
 */

#include "Arduino.h"
#include "aJSON.h"
#include "../messagingService/MsgService.h"
#include "../taskDeclaration/AutonomousHeadlight.h"
#include "../componentDeclaration/Led.h"
#include "../Services/ThresholdLightDetector.h"
#include "MemoryFree-master/MemoryFree.h"

AutonomousHeadlight::AutonomousHeadlight(int ledPin, int lightSensorPin, int threshold){
	this->ledPin = ledPin;
	this->lightSensorPin = lightSensorPin;
	this->threshold = threshold;
}

void AutonomousHeadlight::init(int period,Data* data){
	Task::init(period,data);
	led = new Led(ledPin);
	lightDetector = new ThresoldLightDetector(lightSensorPin, threshold);
	state = OFF;
	belowTh = 0;
}

void AutonomousHeadlight::tick(){

	if(!buffer->getEventList()->isEmpty()){
		eventType event = buffer->getEventList()->pop();
		belowTh = event.value;
	}
	switch(state){
	case OFF:
		if(belowTh){
			led->switchOn();
			state = ON;
		}
		break;
	case ON:
		if(!belowTh){
			led->switchOff();
			state = OFF;
		}
		break;
	}

	int intensity = lightDetector->getIntensity();
	data->updateLight(intensity);
// 	data->updateLight(freeMemory());

	aJsonObject* jObj = MsgService.createMsg("RASPBERRY", "LIGHT", "INTENSITY", intensity);
	MsgService.sendJsonMsg(jObj);
	aJson.deleteItem(jObj);

}


