/*
 * Photoresistor.cpp
 *
 *  Created on: 17 ago 2016
 *      Author: Fabio Limardo
 */

#include "Arduino.h"
#include "../componentDeclaration/Photoresistor.h"

Photoresistor::Photoresistor(int pin){
	this->pin = pin;
}

int Photoresistor::getLightIntensity(){
	 int lightIntensity = analogRead(pin);
	 lightIntensity = map(lightIntensity, 0, 1023, 0, 255);
	 return lightIntensity;
}
