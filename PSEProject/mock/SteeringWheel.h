/*
 * SteeringWheel.h
 *
 *  Created on: 17 set 2016
 *      Author: fabio
 */

#ifndef MOCK_STEERINGWHEEL_H_
#define MOCK_STEERINGWHEEL_H_

#include "aJson.h"

class SteeringWheel {
public:
	SteeringWheel();
	void mockTurnLeft(int angle);
	void mockTurnRight(int angle);
};

#endif /* MOCK_STEERINGWHEEL_H_ */
