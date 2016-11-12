/*
 * UltrasonicSensor.h
 *
 *  Created on: 16 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef COMPONENTSDECLARATION_ULTRASONICSENSOR_H_
#define COMPONENTSDECLARATION_ULTRASONICSENSOR_H_

#include "../componentInterfaces/DistanceSensor.h"

class UltrasonicSensor: public DistanceSensor {
public:
	UltrasonicSensor(int trigPin, int echoPin);
	float getDistance(float temperature);
private:
	int trigPin, echoPin;
	float temperature;
};



#endif /* COMPONENTSDECLARATION_ULTRASONICSENSOR_H_ */
