/*
 * AutonomousPhase.h
 *
 *  Created on: 19 set 2016
 *      Author: fabio
 */

#ifndef TASKDECLARATION_AUTONOMOUSPHASE_H_
#define TASKDECLARATION_AUTONOMOUSPHASE_H_

#include "../services/EventBuffer.h"
#include "../mock/Brake.h"
#include "../mock/SteeringWheel.h"
#include "../scheduler/Task.h"
#include "aJson.h"

class AutonomousPhase: public Task {
public:
	AutonomousPhase();
	void init(int period,Data* data);
	void tick();
private:
	eventType event;
	Brake* brake;
	SteeringWheel* steeringWheel;
	enum {SAFE,ALARM,SLOWDOWN,TURN_LEFT,TURN_RIGHT} state;

};


#endif /* TASKDECLARATION_AUTONOMOUSPHASE_H_ */
