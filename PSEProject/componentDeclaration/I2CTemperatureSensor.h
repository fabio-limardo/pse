/*
 * I2CTempSensor.h
 *
 *  Created on: 16 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef COMPONENTSDECLARATION_I2CTEMPERATURESENSOR_H_
#define COMPONENTSDECLARATION_I2CTEMPERATURESENSOR_H_

#include "../componentInterfaces/TemperatureSensor.h"

class I2CTemperatureSensor: public TemperatureSensor{
public:
	I2CTemperatureSensor(int address);
	float getTemperature();
private:
	int address;
};



#endif /* COMPONENTSDECLARATION_I2CTEMPERATURESENSOR_H_ */
