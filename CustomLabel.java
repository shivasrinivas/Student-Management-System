import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel{

	private Font font;

	CustomLabel(String labelText){
		super(labelText);
		try{
			Font font = Font.createFont(Font.TRUETYPE_FONT, getClass().getResource("/Caviar-Dreams/CaviarDreams.ttf").openStream());   
			GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
			environment.registerFont(font);	
		} catch(IOException e){
			e.printStackTrace();
		} catch(FontFormatException e){
			e.printStackTrace();
		}
		
	}

	public void setFontSize(float fontSize){
		if(font != null){
			font = font.deriveFont(fontSize);
		}
	}

	public void setLabelSize(int x, int y, int width, int height){
		this.setBounds(x, y, width, height);
	}
	
}