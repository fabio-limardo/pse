/*
 * LightLevelDetector.cpp
 *
 *  Created on: 19 ago 2016
 *      Author: Fabio Limardo
 */

#include "../componentDeclaration/Photoresistor.h"
#include "Arduino.h"
#include "ThresholdLightDetector.h"

ThresoldLightDetector::ThresoldLightDetector(int lightSensorPin, int thresold){
	this->lightSensorPin = lightSensorPin;
	this->threshold = thresold;
	lightSensor = new Photoresistor(lightSensorPin);
}

bool ThresoldLightDetector::detected(){
	//Serial.println(lightSensor->getLightIntensity());
	if(lightSensor->getLightIntensity() <= threshold)
		return true;
	else
		return false;
}

int ThresoldLightDetector::getIntensity(){
	return lightSensor->getLightIntensity();
}

