import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;

public class StudentDetails{
	public static void main(String[] args) {  
		JFrame jFrame = new JFrame();
    	jFrame.setSize(1024, 800);
		jFrame.setLayout();
		
		jFrame.getContentPane().setBackground(Color.white);

		JPanel jPanel = new JPanel();
		jPanel.setBounds(5, 5, 1014, 768);
		jPanel.setBackground(Color.white);
		jFrame.add(jPanel);

		JLabel jLabel = new JLabel("Student Management System");
		jLabel.setBounds(300,64, 500,30);
		jLabel.setFont(new Font("Serif", Font.PLAIN, 34));
		jPanel.add(jLabel);


		JTextField jTextField = new JTextField("A hint here");
		jTextField.setBounds(50,150, 200,300);  
		jPanel.add(jTextField);
		jFrame.setVisible(true);
		jPanel.setVisible(true);
	}
}