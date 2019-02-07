import java.awt.event.ActionEvent;
import java.lang.Math;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Calculator implements ActionListener {

		
	
		JFrame frame;
		JTextField textField;
		JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bdec,badd,bsub,
		bmul,bdiv,beq,bclr,bksp,bsqr,bcbe,bsqt,blog;
		
		Double firstNumber;
		Double secondNumber;
		Double result;
		int operator;
		
		public Calculator() {
			frame = new JFrame("Calculator, Created by me");
			frame.setBackground(java.awt.Color.black);
			frame.setLayout(null);
			
			textField = new JTextField();
			
			b1 = new JButton("1");
			b1.setBounds(40,240,50,40);
			frame.add(b1);
			
			b4 = new JButton("4");
			b4.setBounds(40,170,50,40);
			frame.add(b4);
			
			b7 = new JButton("7");
			b7.setBounds(40,100,50,40);
			frame.add(b7);
		
			b2 = new JButton("2");
			b2.setBounds(110,240,50,40);
			frame.add(b2);
			
			b5 = new JButton("5");
			b5.setBounds(110,170,50,40);
			frame.add(b5);
			
			b8 = new JButton("8");
			b8.setBounds(110,100,50,40);
			frame.add(b8);
			
			b3 = new JButton("3");
			b3.setBounds(180,240,50,40);
			frame.add(b3);
			
			b6 = new JButton("6");
			b6.setBounds(180,170,50,40);
			frame.add(b6);
			
			b9 = new JButton("9");
			b9.setBounds(180,100,50,40);
			frame.add(b9);
			
			badd = new JButton("+");
			badd.setBounds(250,240,50,40);
			frame.add(badd);
			
			bsub = new JButton("-");
			bsub.setBounds(250,170,50,40);
			frame.add(bsub);
			
			bmul = new JButton("*");
			bmul.setBounds(250,100,50,40);
			frame.add(bmul);
			
			bdec = new JButton(".");
			bdec.setBounds(40,310,50,40);
			frame.add(bdec);
			
			b0 = new JButton("0");
			b0.setBounds(110,310,50,40);
			frame.add(b0);
			
			beq = new JButton("=");
			beq.setBounds(180,310,50,40);
			frame.add(beq);
			
			bdiv = new JButton("/");
			bdiv.setBounds(250,310,50,40);
			frame.add(bdiv);
			
			bsqr = new JButton("square");
			bsqr.setBounds(320,100,100,40);
			frame.add(bsqr);
			
			bcbe = new JButton("Cube");
			bcbe.setBounds(320,170,100,40);
			frame.add(bcbe);
			
			bsqt = new JButton("sq. root");
			bsqt.setBounds(320,240,100,40);
			frame.add(bsqt);
			
			blog = new JButton("log");
			blog.setBounds(320,310,100,40);
			frame.add(blog);
			
			bclr = new JButton("clear");
			bclr.setBounds(40,380,200,40);
			frame.add(bclr);
			
			bksp = new JButton("backspace");
			bksp.setBounds(270,380,150,40);
			frame.add(bksp);
			
			textField.setBounds(30,40,410,30);
			frame.add(textField);
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setSize(800, 600);
			
			b1.addActionListener(this);
			b2.addActionListener(this);
			b3.addActionListener(this);
			b4.addActionListener(this);
			b5.addActionListener(this);
			b6.addActionListener(this);
			b7.addActionListener(this);
			b8.addActionListener(this);
			b9.addActionListener(this);
			b0.addActionListener(this);
			badd.addActionListener(this);
			bsub.addActionListener(this);
			bdiv.addActionListener(this);
			bmul.addActionListener(this);
			bsqt.addActionListener(this);
			bclr.addActionListener(this);
			bksp.addActionListener(this);
			blog.addActionListener(this);
			bcbe.addActionListener(this);
			bdec.addActionListener(this);
			bsqr.addActionListener(this);
			beq.addActionListener(this);
			
			
			
			
			
			
			
			
			
			
			
			frame.setVisible(true);
}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() ==b1)
				textField.setText(textField.getText().concat("1"));
			if(e.getSource() ==b2)
				textField.setText(textField.getText().concat("2"
						+ ""));
			if(e.getSource() ==b3)
				textField.setText(textField.getText().concat("3"));
			if(e.getSource() ==b4)
				textField.setText(textField.getText().concat("4"));
			if(e.getSource() ==b5)
				textField.setText(textField.getText().concat("5"));
			if(e.getSource() ==b6)
				textField.setText(textField.getText().concat("6"));
			if(e.getSource() ==b7)
				textField.setText(textField.getText().concat("7"));
			if(e.getSource() ==b8)
				textField.setText(textField.getText().concat("8"));
			if(e.getSource() ==b9)
				textField.setText(textField.getText().concat("9"));
			if(e.getSource() ==b0)
				textField.setText(textField.getText().concat("0"));
			if(e.getSource() ==bdec)
				textField.setText(textField.getText().concat("."));
			if(e.getSource() == bclr)
				textField.setText("");
		
		
		
		
		if(e.getSource() == badd) {
			firstNumber = Double.parseDouble(textField.getText());
			operator = 1;
			textField.setText("");
		}
		if(e.getSource() == bdiv) {
			firstNumber = Double.parseDouble(textField.getText());
			operator = 2;
			textField.setText("");
		}
		if(e.getSource() == bmul) {
			firstNumber = Double.parseDouble(textField.getText());
			operator = 3;
			textField.setText("");
		}
		if(e.getSource() == bsub) {
			firstNumber = Double.parseDouble(textField.getText());
			operator = 4;
			textField.setText("");
		}
		if(e.getSource() == bsqt) {
			firstNumber = Double.parseDouble(textField.getText());
			operator = 5;
			textField.setText("");
		}
		if(e.getSource() == bsqr) {
			firstNumber = Double.parseDouble(textField.getText());
			operator = 6;
			textField.setText("");
		}
		if(e.getSource() == blog) {
			firstNumber = Double.parseDouble(textField.getText());
			operator = 7;
			textField.setText("");
		}
		if(e.getSource() == bcbe) {
			firstNumber = Double.parseDouble(textField.getText());
			operator = 8;
			textField.setText("");
		}
		if(e.getSource() == bksp) {
			String s =textField.getText();
			textField.setText("");
			for(int i=0;i<s.length()-1;i++)
				textField.setText(textField.getText()+s.charAt(i));
		}
		if(e.getSource() == beq) {
		secondNumber = Double.parseDouble(textField.getText());
		
			switch(operator) {
				case 1:
					result = firstNumber + secondNumber;
					break;
				case 2:
					result = firstNumber / secondNumber;
					break;
				case 3:
					result = firstNumber * secondNumber;
					break;
				case 4:
					result = firstNumber - secondNumber;
					break;
				case 5:
					result = Math.sqrt(firstNumber);
				case 6:
					result = firstNumber * firstNumber;
				case 7:
					result = Math.log10(firstNumber);
				case 8:
					result = firstNumber*firstNumber*firstNumber;		
		
				default: result = 0.0;
			
			}
			textField.setText(""+result);
			
		}
		
		
	}// end of action
		
}
	