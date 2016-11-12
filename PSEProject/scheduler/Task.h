/*
 * Task.h

 *
 *  Created on: 19 ago 2016
 *      Author: Fabio Limardo
 */
#include "../services/EventBuffer.h"
#include "../services/Data.h"
#include <QueueList.h>

#ifndef TASKDECLARATION_TASK_H_
#define TASKDECLARATION_TASK_H_

class Task{
	int myPeriod;
	int timeElapsed;

protected:
	EventBuffer* buffer;
	Data* data;

public:
	virtual void init(int period,Data* data) {
		myPeriod = period;
		timeElapsed = 0;
		buffer = new EventBuffer();
		this->data = data;
	}
	virtual void tick() = 0;

	bool updateAndCheckTime(int basePeriod){
		timeElapsed += basePeriod;
		if(timeElapsed >= myPeriod){
			timeElapsed = 0;
			return true;
		}
		else{
			return false;
		}
	}
	EventBuffer* getEventBuffer(){
		return buffer;
	}

};



#endif /* TASKDECLARATION_TASK_H_ */
