/*
 * AutonomousPhase.cpp
 *
 *  Created on: 19 set 2016
 *      Author: fabio
 */

#include "../taskDeclaration/AutonomousPhase.h"
#include "../messagingService/MsgService.h"
#include "../scheduler/Task.h"
#include "aJson.h"

/**
 * Utilizzato come sperimentazione, questa parte di controllo avviene su Raspberry sul prodotto finale.
 */

AutonomousPhase::AutonomousPhase(){
}

void AutonomousPhase::init(int period,Data* data){
	Task::init(period,data);
	brake = new Brake();
	steeringWheel = new SteeringWheel();
	state = SAFE;
}

void AutonomousPhase::tick(){

	if(!buffer->getEventList()->isEmpty()){
		event = buffer->getEventList()->pop();
		if(strcmp(event.type,"ALARM")==0){
			state = ALARM;
		}else{
			if(strcmp(event.type,"SAFE")==0){
				state = SAFE;
			}
		}
	}

	switch(state){
	case SAFE:
		break;
	case ALARM:

		if(!buffer->getEventList()->isEmpty()){
			event = buffer->getEventList()->pop();
			if(strcmp(event.content,"LEFT_ALARM")){
				state = TURN_RIGHT;
			}else{
			if(strcmp(event.content,"RIGHT_ALARM")){
				state = TURN_RIGHT;
			}else{
			if(strcmp(event.content,"FRONT_ALARM")){
				state = SLOWDOWN;
			}
			}

			}
		}
		break;
	case TURN_LEFT:
		steeringWheel->mockTurnLeft(event.value);
		break;
	case TURN_RIGHT:
		steeringWheel->mockTurnRight(event.value);
		break;
	case SLOWDOWN:
		brake->mockSlowDown(event.value);
		break;
	}
}

