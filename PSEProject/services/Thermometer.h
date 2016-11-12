/*
 * Thermometer.h
 *
 *  Created on: 20 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef SERVICES_THERMOMETER_H_
#define SERVICES_THERMOMETER_H_

#include "../componentDeclaration/I2CTemperatureSensor.h"

class Thermometer{

private:
	int temperatureValue;
	I2CTemperatureSensor* sensor;

public:
	Thermometer(int sensorPin);
	void updateValue();
	int getValue();
};


#endif /* SERVICES_THERMOMETER_H_ */
