/*
 * TempSender.cpp
 *
 *  Created on: 08 ott 2016
 *      Author: fabio
 */

#include "../taskDeclaration/TempSender.h"

TempSender::TempSender(){
}
void TempSender::init(int period, Data* data){
	Task::init(period,data);
}

void TempSender::tick(){

	aJsonObject* jObj = MsgService.createMsg("RASPBERRY", "TEMPERATURE", "NEWVALUE", data->getCurrentState().temperature);
	MsgService.sendJsonMsg(jObj);
	aJson.deleteItem(jObj);

}
