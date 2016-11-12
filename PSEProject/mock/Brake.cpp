/*
 * Brake.cpp
 *
 *  Created on: 17 set 2016
 *      Author: fabio
 */

#include "Brake.h"
#include "../messagingService/MsgService.h"

Brake::Brake() {

}
/*
 * Non avendo dei veri freni si ipotizza che sia possibile emularlo in una configurazione hardware leggermente più complessa
 * della nostra con dei motori elettrici che muovono le ruote.
 * @param gValue intendiamo la forza g che dovrebbe essere applicata in frenata 1g = 9,81 m/s^2
 */
void Brake::mockSlowDown(int gValue){
	aJsonObject* jObj = MsgService.createMsg("RASPBERRY", "BRAKE", "GFORCE", gValue);
	//MsgService.sendJsonMsg(jObj);
//    aJson.print(jObj, &serial_stream);
//    Serial.println();
    aJson.deleteItem(jObj);
}

