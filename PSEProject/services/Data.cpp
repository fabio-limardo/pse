/*
 * Data.cpp
 *
 *  Created on: 30 set 2016
 *      Author: fabio
 */

#include "Data.h"

Data::Data() {
	state.temperature = 10;
	// TODO Auto-generated constructor stub

}
void Data::updateLight(int light){
	state.light = light;
}
void Data::updateLeft(float left){
	state.left  = left;
}
void Data::updateRight(float right){
	state.right = right;
}
void Data::updateFront(float front){
	state.front = front;
}
void Data::updateTemperature(int temperature){
	state.temperature = temperature;
}
State Data::getCurrentState(){
	return state;
}

