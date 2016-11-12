/*
 * Led.h
 *
 *  Created on: 25 lug 2016
 *      Author: Fabio Limardo
 */

#ifndef LED_H_
#define LED_H_

#include "../componentInterfaces/Light.h"

class Led: public Light {
public:
	Led(int pin);
	void switchOn();
	void switchOff();
private:
	int pin;
};



#endif /* LED_H_ */
