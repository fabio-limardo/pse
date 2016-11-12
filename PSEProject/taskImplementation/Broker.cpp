/*
 * CommandExecutor.cpp
 *
 *  Created on: 17 set 2016
 *      Author: fabio
 */

#include "Arduino.h"
#include "aJson.h"
#include "../taskDeclaration/Broker.h"
#include "../messagingService/MsgService.h"
#include "../MemoryFree-master/MemoryFree-master/MemoryFree.h"

/**
 * Message JSON
 * {
 * 		CONTEXT = (String)context,
 * 		TYPE = (String)type,
 * 		CONTENT = (String)content,
 * 		VALUE = (int)value
 * 	}
 *
 */

/**
 * Pensato inizialmente per come ricevitore e gestori di messaggi eventi, non viene più utilizzato nel prodotto finale,
 * il tick invia semplicemente la quantità di RAM utilizzata.
 */
Broker::Broker(
		AutonomousPhase* autonomous,
		AutonomousHeadlight* headlight,
		CheckDistance* distanceL,
		CheckDistance* distanceR,
		CheckDistance* distanceF
		){
	this->autonomus = autonomous;
	this->headlight = headlight;
	this->distance[0] = distanceL;
	this->distance[1] = distanceR;
	this->distance[2] = distanceF;

}
Broker::Broker(){

}

void Broker::init(int period, Data* data){
	Task::init(period,data);
}

void Broker::tick(){

	aJsonObject* jObj = MsgService.createMsg("RASPBERRY", "STATE", "RAM", freeMemory());
	MsgService.sendJsonMsg(jObj);
	aJson.deleteItem(jObj);

}
	//Serial.println(freeMemory());
//	if (MsgService.isMsgAvailable()){
//		Msg* msg = MsgService.receiveMsg();
//		String sMessage = msg->getContent();
//		//Serial.println(freeMemory());
//		char* message = sMessage.c_str();
//		aJsonObject* jObj= aJson.parse(message);
//		aJsonObject* jType = aJson.getObjectItem(jObj, "TYPE");
//		char* value = jType->valuestring;
//		if(strcmp(value,"GETSTATE")==0){
//			State currentState = data->getCurrentState();
//			aJsonObject* jObj = MsgService.createState(
//					currentState.front,
//					currentState.left,
//					currentState.right,
//					currentState.light,
//					currentState.temperature);
//			MsgService.sendJsonMsg(jObj);
//			aJson.deleteItem(jObj);
//			delete msg;
//	}
//		}
//		else{
//			aJsonObject* jContext = aJson.getObjectItem(jObj, "CONTEXT");
//			aJsonObject* jContent = aJson.getObjectItem(jObj, "CONTENT");
//			aJsonObject* jValue = aJson.getObjectItem(jObj, "VALUE");
//
//			eventType event;
//			event.context = jContext->valuestring;
//			event.type = jType->valuestring;
//			event.content = jContent->valuestring;
//			event.value = jValue->valueint;
//
//			if(strcmp(event.type,"ALARM")){
//				autonomus->getEventBuffer()->getEventList()->push(event);
//			}
//			else{
//				if(strcmp(event.type,"LIGHT")){
//					headlight->getEventBuffer()->getEventList()->push(event);
//				}else{
//					if(strcmp(event.type,"TEMPERATURE")){
//						for(int i=0; i<3;i++){
//							distance[i]->getEventBuffer()->getEventList()->push(event);
//						}
//					}
//				}
//			}
//		}
//		delete msg;
//		aJson.deleteItem(jObj);
//	}
//}


//	if (MsgService.isMsgAvailable()){
//		Msg* msg = MsgService.receiveMsg();
//
//
//		String sMessage = msg->getContent();
//		char* message = sMessage.c_str();
//		aJsonObject* jObj= aJson.parse(message);
//
//	    aJsonObject* jContext = aJson.getObjectItem(jObj, "CONTEXT");
//	    aJsonObject* jType = aJson.getObjectItem(jObj, "CONTENT");
//	    aJsonObject* jContent = aJson.getObjectItem(jObj, "TYPE");
//	    aJsonObject* jValue = aJson.getObjectItem(jObj, "VALUE");
//
//	    eventType event;
//	    event.context = jContext->valuestring;
//	    event.type = jType->valuestring;
//	    event.content = jContent->valuestring;
//	    event.value = jValue->valueint;
//
//	    if(strcmp(event.type,"ALARM")){
//	    	autonomus->getEventBuffer()->getEventList()->push(event);
//	    }
//	    else{
//	    	if(strcmp(event.type,"LIGHT")){
//	    		headlight->getEventBuffer()->getEventList()->push(event);
//	    	}else{
//	    		if(strcmp(event.type,"TEMPERATURE")){
//	    			for(int i=0; i<3;i++){
//	    			distance[i]->getEventBuffer()->getEventList()->push(event);
//	    			}
//	    		}
//	    	}
//	    }
//		delete msg;
//		aJson.deleteItem(jObj);
//	}






