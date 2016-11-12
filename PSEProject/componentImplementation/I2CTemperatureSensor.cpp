/*
 * I2CTemperatureSensor.cpp
 *
 *  Created on: 16 ago 2016
 *      Author: Fabio Limardo
 */

#include "Wire.h"
#include "Arduino.h"
#include "../componentDeclaration/I2CTemperatureSensor.h"

I2CTemperatureSensor::I2CTemperatureSensor(int address){
	this->address = address;
}

float I2CTemperatureSensor::getTemperature(){
	Wire.beginTransmission(address);
	Wire.write(0);
	Wire.endTransmission();
	Wire.requestFrom(address, 1);
	while (Wire.available() == 0);
	float temperature = Wire.read();
	return temperature;
}

