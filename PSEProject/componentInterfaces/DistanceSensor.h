/*
 * DistanceSensor.h
 *
 *  Created on: 16 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef DISTANCESENSOR_H_
#define DISTANCESENSOR_H_

class DistanceSensor {
public:
	virtual float getDistance(float temperature) = 0;
};


#endif /* DISTANCESENSOR_H_ */
