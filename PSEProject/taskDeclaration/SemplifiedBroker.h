/*
 * SemplifiedBroker.h
 *
 *  Created on: 06 ott 2016
 *      Author: fabio
 */

#ifndef TASKDECLARATION_SEMPLIFIEDBROKER_H_
#define TASKDECLARATION_SEMPLIFIEDBROKER_H_

class SemplifiedBroker: public Task{

public:
	SemplifiedBroker(
			AutonomousPhase* autonomous,
			AutonomousHeadlight* headlight,
			CheckDistance* distanceL,
			CheckDistance* distanceR,
			CheckDistance* distanceF
			);
	SemplifiedBroker();
	bool addTask(Task* task);
	void init(int period,Data* data);
	void tick();

private:



#endif /* TASKDECLARATION_SEMPLIFIEDBROKER_H_ */
