/*
 * Data.h
 *
 *  Created on: 30 set 2016
 *      Author: fabio
 */

#ifndef SERVICES_DATA_H_
#define SERVICES_DATA_H_

#include "aJSON.h"
/*
 * Pensato per avere sempre uno stato in cui poter recuperare lo stato attuale del sistema
 * attraverso le carateristiche che lo delineano. Non utilizzato completamente al momento.
 *
 */
typedef struct{
	int light;
	float left;
	float right;
	float front;
	int temperature;
}State;

class Data {

public:
	Data();
	void updateLight(int light);
	void updateLeft(float left);
	void updateRight(float right);
	void updateFront(float front);
	void updateTemperature(int temperature);
	State getCurrentState();

private:
	State state;
};

#endif /* SERVICES_DATA_H_ */
