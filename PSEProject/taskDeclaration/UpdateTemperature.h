/*
 * UpdateTemperature.h
 *
 *  Created on: 20 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef TASKDECLARATION_UPDATETEMPERATURE_H_
#define TASKDECLARATION_UPDATETEMPERATURE_H_

#include "../scheduler/Task.h"
#include "../services/Thermometer.h"

class UpdateTemperature: public Task{
	int sensorPin;
	Thermometer* sensor;
public:
	UpdateTemperature(int sensorPin);
	void init(int period,Data* data);
	void tick();

};



#endif /* TASKDECLARATION_UPDATETEMPERATURE_H_ */
