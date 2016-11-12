#ifndef __TIMER__
#define __TIMER__

class Runnable {
    
public:  
  virtual void run() = 0;  
};

class Timer {
public:  
  Timer(Runnable* pRunnable);
  void setupFreq(int freq);  
  /* period in ms */
  void setupPeriod(int period);  
  void waitForNextTick();

};


#endif

