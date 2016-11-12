/*
 * Led.cpp
 *
 *  Created on: 16 ago 2016
 *      Author: Fabio Limardo
 */

#include "Arduino.h"
#include "../componentDeclaration/Led.h"

Led::Led(int pin){
	this->pin = pin;
	pinMode(pin,OUTPUT);
}

void Led::switchOn(){
	digitalWrite(pin,HIGH);
}

void Led::switchOff(){
	digitalWrite(pin,LOW);
}

