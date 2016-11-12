/*
 * PhotoResistor.h
 *
 *  Created on: 16 ago 2016
 *      Author: Fabio Limardo
 */

#ifndef COMPONENTSDECLARATION_PHOTORESISTOR_H_
#define COMPONENTSDECLARATION_PHOTORESISTOR_H_

#include "../componentInterfaces/Photodetector.h"

class Photoresistor: public Photodetector {
public:
	Photoresistor(int pin);
	int getLightIntensity();
private:
	int pin;
};


#endif /* COMPONENTSDECLARATION_PHOTORESISTOR_H_ */
