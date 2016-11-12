/*
 * TempSender.h
 *
 *  Created on: 08 ott 2016
 *      Author: fabio
 */

#ifndef TASKDECLARATION_TEMPSENDER_H_
#define TASKDECLARATION_TEMPSENDER_H_

#include "../services/Data.h"
#include "../scheduler/Task.h"
#include "../messagingService/MsgService.h"
#include "aJson.h"

class TempSender: public Task{
public:
	TempSender();
	void init(int period,Data* data);
	void tick();
};

#endif /* TASKDECLARATION_TEMPSENDER_H_ */
