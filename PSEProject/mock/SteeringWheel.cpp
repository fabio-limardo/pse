/*
 * SteeringWheel.cpp
 *
 *  Created on: 17 set 2016
 *      Author: fabio
 */

#include "SteeringWheel.h"
#include "../messagingService/MsgService.h"

SteeringWheel::SteeringWheel() {
	// TODO Auto-generated constructor stub
}
/*
 * In questo caso stiamo ipotizzando di avere controllo sullo sterzo nel caso reale
 * Per avere un confronto di come potrebbe funzionare (pur semplificando forse esageratamente)
 * abbiamo immaginato un funzionamento non troppo dissimile all'avere un motore elettrico csu cui si possa regolare l'angolo.
 */
void SteeringWheel::mockTurnLeft(int angle){
	aJsonObject* jObj = MsgService.createMsg("RASPBERRY", "STEER", "LEFT", angle);
	//MsgService.sendJsonMsg(jObj);
   // Serial.println();
    aJson.deleteItem(jObj);
}
void SteeringWheel::mockTurnRight(int angle){
	aJsonObject* jObj = MsgService.createMsg("RASPBERRY", "STEER", "RIGHT", angle);

//    aJson.print(jObj, &serial_stream);
//    Serial.println();
    aJson.deleteItem(jObj);
}

