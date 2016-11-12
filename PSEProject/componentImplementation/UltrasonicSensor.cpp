/*
 * UltrasonicSensor.cpp
 *
 *  Created on: 16 ago 2016
 *      Author: Fabio Limardo
 */

#include "Arduino.h"
#include "../componentDeclaration/UltrasonicSensor.h"

UltrasonicSensor::UltrasonicSensor(int trigPin, int echoPin){
	this->trigPin = trigPin;
	this->echoPin = echoPin;
	pinMode(trigPin, OUTPUT);
	pinMode(echoPin, INPUT);
	temperature = 20;
}

float UltrasonicSensor::getDistance(float temperature){

	this->temperature = temperature;
	float speedOfSound = 331.5 + 0.6*temperature; //approssimazione lineare del cambiamento

	/* invio impulso */
	digitalWrite(trigPin,LOW);
	delayMicroseconds(3);
	digitalWrite(trigPin,HIGH);
	delayMicroseconds(5);
	digitalWrite(trigPin,LOW);

	 /* ricevi l’eco */
	float tUS = pulseIn(echoPin, HIGH);
	float t = tUS / 1000.0 / 1000.0 / 2; //fattore conversione e distanza percorsa due volte
	float distance = t*speedOfSound;
	//Serial.println(distance);
	return distance;
}



