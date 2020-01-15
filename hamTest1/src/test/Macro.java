package test;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Macro {
	public static void main(String[] args) {
		Macro mc = new Macro();
		mc.exe();
	}
	
	void exe() {
		
		Runtime rt = Runtime.getRuntime();
		Process pc = null;
		BufferedReader input = null;
		String line;
		Robot rb = null;
		
		try {
			
			System.out.println("START!!");
			int cnt=3;
			while(true) {

				System.out.println(cnt);
				
				if(cnt--==0) {
					break;
				}
				Thread.sleep(1000);				
			}
			
//			pc = rt.exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
			pc = new ProcessBuilder("tasklist", "/FI", "\"IMAGENAME eq Red Stone.exe\"").start();
			input = new BufferedReader(new InputStreamReader(pc.getInputStream()));

			rb = new Robot();
			
			rb.mouseMove(1016, 478);
			
			rb.mousePress(InputEvent.BUTTON1_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_MASK);
			
			Thread.sleep(1000);
			
			rb.mousePress(InputEvent.BUTTON1_MASK);
			rb.mouseRelease(InputEvent.BUTTON1_MASK);
			
//			rb.keyPress(keycode);
			
			while((line = input.readLine())!= null) {
				System.out.println(line);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(input!=null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
