/*
 * EventBuffer.h
 *
 *  Created on: 19 set 2016
 *      Author: fabio
 */

#ifndef SERVICES_EVENTBUFFER_H_
#define SERVICES_EVENTBUFFER_H_

#include <QueueList.h>
#define MAX_EVENT 10
/**
 * Introdotto per avere una coda degli eventi in fase di analisi del problema come proposta di soluzione.
 *  Di fatto non viene utiilizzato, rappresenta comunque un possibile step successivo.
 */
typedef struct{
	char* context;
	char* type;
	char* content;
	int value;

}eventType;

class EventBuffer {

public:
	EventBuffer();
	QueueList<eventType>* getEventList(){
		return &eventList;
	}
	eventType getEventFromList(char* type);
private:
	//std::list<eventType> eventList;
	QueueList<eventType> eventList;
};

#endif /* SERVICES_EVENTBUFFER_H_ */
