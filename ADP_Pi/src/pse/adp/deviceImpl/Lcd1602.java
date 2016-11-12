package pse.adp.deviceImpl;

import com.pi4j.wiringpi.Gpio;
import com.pi4j.wiringpi.Lcd;

import pse.adp.deviceInterface.Console;

public class Lcd1602 implements Console{
	
    public final static int LCD_ROWS = 2;
    public final static int LCD_COLUMNS = 16;
    public final static int LCD_BITS = 4;
    private String line1, line2;
    private int lcdHandle;

    public Lcd1602(){
    	if (Gpio.wiringPiSetup() == -1) {
            System.out.println(" ==>> GPIO SETUP FAILED");
            return;
        }

        // initialize LCD
        lcdHandle= Lcd.lcdInit(LCD_ROWS,     // number of row supported by LCD
                                   LCD_COLUMNS,  // number of columns supported by LCD
                                   LCD_BITS,     // number of bits used to communicate to LCD
                                   11,           // LCD RS pin
                                   10,           // LCD strobe pin
                                   0,            // LCD data bit 1
                                   1,            // LCD data bit 2
                                   2,            // LCD data bit 3
                                   3,            // LCD data bit 4
                                   0,            // LCD data bit 5 (set to 0 if using 4 bit communication)
                                   0,            // LCD data bit 6 (set to 0 if using 4 bit communication)
                                   0,            // LCD data bit 7 (set to 0 if using 4 bit communication)
                                   0);           // LCD data bit 8 (set to 0 if using 4 bit communication)
        if (lcdHandle == -1) {
            System.out.println(" ==>> LCD INIT FAILED");
            return;
        }
        Lcd.lcdClear(lcdHandle);
        line1 = "TEST";
        line2 = "----------------";
        Lcd.lcdClear(lcdHandle);
        
        Lcd.lcdHome(lcdHandle);
        //Lcd.lcdPosition (lcdHandle, 0, 0) ;
         
        Lcd.lcdPuts (lcdHandle, line1);
       
    }

	@Override
	public  synchronized void writeMsg(String...strings) {
		String[] lines = strings;
		
		if(lines.length == 2){
			line1 = lines[0];
			line2 = lines[1];
		}else
			if(lines.length == 1){
				line2 = lines[0];
			}
		Lcd.lcdClear(lcdHandle);
        // write line 1 to LCD
        Lcd.lcdHome(lcdHandle);
        //Lcd.lcdPosition (lcdHandle, 0, 0) ;
        Lcd.lcdPuts (lcdHandle, line1) ;

        // write line 2 to LCD
        Lcd.lcdPosition (lcdHandle, 0, 1) ;
        Lcd.lcdPuts (lcdHandle, line2) ;
	}
	
}
