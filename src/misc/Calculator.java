package misc;

import GUI.Button;
import GUI.MasterGUI;
import text.Text;

public class Calculator {
	
	private static String[] numerals = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	public static void update() {
		
		Text displayText = (Text)MasterGUI.getComponent("displayText");
		StringBuilder sb = new StringBuilder(displayText.getText());
		boolean foo = false;
		
		for(String s: numerals) {
			if(((Button)MasterGUI.getComponent(s)).justPressed()) {
				sb.append(s);
				foo = true;
			}
		}
		
		if(foo)
		displayText.setText(sb.toString());
		
	}
	
}
