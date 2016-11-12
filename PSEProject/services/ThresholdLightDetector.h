/*
 * LightLevelDetection.h
 *
 *  Created on: 19 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef TASKDECLARATION_THRESHOLDLIGHTDETECTOR_H_
#define TASKDECLARATION_THRESHOLDLIGHTDETECTOR_H_

#include "../componentDeclaration/Photoresistor.h"



class ThresoldLightDetector{
	Photodetector* lightSensor;
	int lightSensorPin, threshold;

public:
	ThresoldLightDetector(int lightSensorPin, int threshold);
	bool detected();
	int getIntensity();
};


#endif /* TASKDECLARATION_THRESHOLDLIGHTDETECTOR_H_ */
