#include "Arduino.h"
#include "MsgService.h"
#include "aJSON.h"
#include "../MemoryFree-master/MemoryFree-master/MemoryFree.h"


String content;
MsgServiceClass MsgService;
aJsonStream serial_stream(&Serial);
//aJsonStream serial_stream(&Serial);
unsigned long previousMillis = 0;
const long interval = 50;

bool MsgServiceClass::isMsgAvailable(){
	return msgAvailable;
}

Msg* MsgServiceClass::receiveMsg(){
	if (msgAvailable){
		Msg* msg = currentMsg;
		msgAvailable = false;
		currentMsg = NULL;
		content = "";
		return msg;
	} else {
		return NULL;
	}
}

void MsgServiceClass::init(){
	Serial.begin(115200);
	content.reserve(256);
	content = "";
	currentMsg = NULL;
	msgAvailable = false;
}

void MsgServiceClass::sendMsg(char* msg){
	Serial.println(msg);
}

void MsgServiceClass::sendJsonMsg(aJsonObject* jObj){
	aJson.print(jObj, &serial_stream);
	Serial.println();
}

aJsonObject* MsgServiceClass::createMsg(
		const char* context,
		const char* type,
		const char* content,
		float value){

	aJsonObject *obj= aJson.createObject();
	aJson.addStringToObject(obj, "CONTEXT", context);
	aJson.addStringToObject(obj, "TYPE", type);
	aJson.addStringToObject(obj, "CONTENT", content);
	aJson.addNumberToObject(obj,"VALUE", value);
	return obj;
}

aJsonObject* MsgServiceClass::createState(float front, float left, float right, int light, int temperature){
	aJsonObject *jObj= aJson.createObject();
	aJson.addStringToObject(jObj, "CONTEXT", "RASPBERRY");
	aJson.addStringToObject(jObj, "TYPE", "STATE");
	aJson.addNumberToObject(jObj,"FRONT", front);
	aJson.addNumberToObject(jObj,"LEFT", left);
	aJson.addNumberToObject(jObj,"RIGHT", right);
	aJson.addNumberToObject(jObj,"LIGHT", light);
	aJson.addNumberToObject(jObj,"TEMPERATURE", temperature);
	return jObj;
}

void serialEvent() {
	delay(50);
	//unsigned long currentMillis = millis();

//	if (currentMillis - previousMillis >= interval) {
//		// save the last time you blinked the LED
//		previousMillis = currentMillis;

		/* reading the content */
		while (Serial.available()) {
			content += (char)Serial.read();
		}
		MsgService.currentMsg = new Msg(content);
		MsgService.msgAvailable = true;
//	}
}

bool MsgServiceClass::isMsgAvailable(Pattern& pattern){
	return (msgAvailable && pattern.match(*currentMsg));
}

Msg* MsgServiceClass::receiveMsg(Pattern& pattern){
	if (msgAvailable && pattern.match(*currentMsg)){
		Msg* msg = currentMsg;
		msgAvailable = false;
		currentMsg = NULL;
		content = "";
		return msg;
	} else {
		return NULL;
	}

}


