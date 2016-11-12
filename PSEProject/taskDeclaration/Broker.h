/*
 * CommandExecutor.h
 *
 *  Created on: 17 set 2016
 *      Author: fabio
 */

#ifndef TASKDECLARATION_BROKER_H_
#define TASKDECLARATION_BROKER_H_

#include "../scheduler/Task.h"
#include "../taskDeclaration/AutonomousHeadlight.h"
#include "../taskDeclaration/AutonomousPhase.h"
#include "../taskDeclaration/CheckDistance.h"
#include "../services/Data.h"

class Broker: public Task{

public:
	Broker(
			AutonomousPhase* autonomous,
			AutonomousHeadlight* headlight,
			CheckDistance* distanceL,
			CheckDistance* distanceR,
			CheckDistance* distanceF
			);
	Broker();
	bool addTask(Task* task);
	void init(int period,Data* data);
	void tick();

private:
	AutonomousHeadlight* headlight;
	AutonomousPhase* autonomus;
	CheckDistance* distance[3];




};



#endif /* TASKDECLARATION_BROKER_H_ */
