/*
 * autonomousHeadlight.h
 *
 *  Created on: 19 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef TASKDECLARATION_AUTONOMOUSHEADLIGHT_H_
#define TASKDECLARATION_AUTONOMOUSHEADLIGHT_H_

#include "Arduino.h"
#include "../componentDeclaration/Led.h"
#include "../scheduler/Task.h"
#include "../services/ThresholdLightDetector.h"
#include "aJson.h"

class AutonomousHeadlight: public Task{

private:
	int ledPin, lightSensorPin, threshold;
	Light* led;
	ThresoldLightDetector* lightDetector;
	enum {ON,OFF} state;
	bool belowTh;
public:
	AutonomousHeadlight(int ledPin, int lightSensorPin, int thresold);
	void init(int period, Data* data);
	void tick();

};

#endif /* TASKDECLARATION_AUTONOMOUSHEADLIGHT_H_ */
