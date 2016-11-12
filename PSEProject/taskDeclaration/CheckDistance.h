/*
 * CheckDistance.h
 *
 *  Created on: 21 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef TASKDECLARATION_CHECKDISTANCE_H_
#define TASKDECLARATION_CHECKDISTANCE_H_

#include "../scheduler/Task.h"
#include "../services/SafetyDistance.h"
#include "aJson.h"



class CheckDistance: public Task {
private:
	int trigPin,echoPin;
	char type;
	enum {SAFETY,ALARM} state;
	float minDistance, temperature = 20;
	SafetyDistance* checker;
	char* typeC;

public:
	CheckDistance(int trigPin,int echoPin,char type,float minDistance);
	void init(int period,Data* data);
	void tick();

};



#endif /* TASKDECLARATION_CHECKDISTANCE_H_ */
