import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 

public class Login {
    JTextField tf1,tf2,tf3;  
    JButton b1,b2;  
    Login(){  
        JFrame frame= new JFrame();  
        frame.setBounds(100, 100, 1024, 780);
		frame.getContentPane().setBackground(Color.GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(6, 6, 1012, 746);
		panel.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		frame.getContentPane().add(panel);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon("njit_logo_small.png"));
		lblNewLabel_1.setBounds(6, 6, 1012, 746);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBackground(Color.WHITE);
		panel.add(lblNewLabel_1, "24, 4");

		JLabel lblNewLabel = new JLabel("Student Management System");
		lblNewLabel.setForeground(Color.DARK_GRAY);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Serif", Font.PLAIN, 34));
		panel.add(lblNewLabel, "24, 8");

        frame.setVisible(true);  
        panel.setVisible(true);
    }         
      
	public static void main(String[] args) {  
    new Login();  
	} 
}  
