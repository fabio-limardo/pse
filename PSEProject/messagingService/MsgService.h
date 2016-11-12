#ifndef __MSGSERVICE__
#define __MSGSERVICE__

#include "Arduino.h"
#include "aJson.h"


class Msg {
  String content;


public:
  Msg(String content){
    this->content = content;
  }
  
  String getContent(){
    return content;
  }
};

class Pattern {
public:
  virtual boolean match(const Msg& m) = 0;  
};


class MsgServiceClass {


public: 
  
  Msg* currentMsg;
  bool msgAvailable;


  void init();  

  bool isMsgAvailable();
  Msg* receiveMsg();

  bool isMsgAvailable(Pattern& pattern);
  Msg* receiveMsg(Pattern& pattern);
  
  void sendMsg(char* msg);
  void sendJsonMsg(aJsonObject* jObj);
  aJsonObject* createMsg(
		  const char* context,
		  const char* type,
		  const char* content,
		  float value);

  aJsonObject* createState(
		  float front,
		  float left,
		  float right,
		  int light,
		  int temperature);
};



extern MsgServiceClass MsgService;

#endif

