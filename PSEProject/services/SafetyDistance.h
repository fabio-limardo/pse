/*
 * SafetyDistanceFront.h
 *
 *  Created on: 20 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef TASKDECLARATION_SAFETYDISTANCE_H_
#define TASKDECLARATION_SAFETYDISTANCE_H_

#include "../componentDeclaration/UltrasonicSensor.h"

class SafetyDistance {
	int trigPin,echoPin, minDistance;
	UltrasonicSensor* sensor;
public:
	SafetyDistance(int trigPin, int echoPin);
	bool isSafety(float minDistance, float temperature);
	float distance(int temperature);
};




#endif /* TASKDECLARATION_SAFETYDISTANCE_H_ */
