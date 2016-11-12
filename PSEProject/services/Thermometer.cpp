/*
 * Thermometer.cpp
 *
 *  Created on: 20 ago 2016
 *      Author: Fabio Limardo
 */

#include "Thermometer.h"
#include "../componentDeclaration/I2CTemperatureSensor.h"

Thermometer::Thermometer(int sensorPin){
	sensor = new I2CTemperatureSensor(sensorPin);
}

void Thermometer::updateValue(){
	temperatureValue = sensor->getTemperature();
}

int Thermometer::getValue(){
	return temperatureValue;
}
