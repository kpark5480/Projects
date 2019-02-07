

import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;



public class SwingConverter implements ActionListener{
// class scope: anything added here can be used down below
// want the narrowest scope possible
private JPanel inputPanel = initInputPanel();
private JTextField inputText;
private JTextArea resultArea;
private JButton enterButton;
public SwingConverter() {
        JFrame myWindow = new javax.swing.JFrame("Sphere Converter - Created by Kyle");
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.add(inputPanel);
        myWindow.setSize(600, 500);
        myWindow.setVisible(true);

}
    private JPanel initInputPanel() {
// TODO Auto-generated method stub
    JPanel panel = new JPanel();
    panel.setPreferredSize(new java.awt.Dimension(500, 500));
    panel.setLayout(null);
    panel.setBackground(java.awt.Color.gray);
    JLabel label = new JLabel("Enter the radius: ");
    label.setBounds(50, 50, 220, 30);
    panel.add(label);
    //method scope: can only use it in this method
    JTextField text;
    text = inputText = new JTextField();
    text.setBounds(230, 50, 100, 30);
    panel.add(text);
   
    JButton button = new JButton("Enter");
    button.setActionCommand("Enter");
    button.setBounds(340, 50, 80, 30);
    button.addActionListener(this);
    panel.add(button);
    // save to the class variable, to be sued in other methods
    enterButton = button; // assign in to a class var to be used in other methods
    button = new JButton("Reset");
    button.setActionCommand("Reset");
    button.setBounds(430, 50, 80, 30);
    button.addActionListener(this);
    panel.add(button);
   
    resultArea = new JTextArea();
    resultArea.setBounds(50, 180, 500, 200);
    resultArea.setEditable(false);
    resultArea.setBackground(java.awt.Color.magenta);
    panel.add(resultArea);
   
   
return panel;
}






public static void main(String[] args) throws Exception {
        //SwingConverter baseConverter=new SwingConverter();
        new SwingConverter();
    }



@Override
public void actionPerformed(ActionEvent ev) {
        String command=ev.getActionCommand();
        if (command.equals("Enter")) {
            showResult();
        }
        else if (command.equals("Reset")) {
            reset();
        }
    }
    private void showResult() {
    String hexUpperCase;
    inputText.setEnabled(false);
    enterButton.setEnabled(false);
    Integer n = getInputNumber();
    if (n == null) {
    resultArea.append("Invalid Number");
    return;
    }
   
    int radius = n.intValue();
    resultArea.append("radius = " + radius + "\n");
    resultArea.append("volume = " + (4 * Math.PI * Math.pow(radius, 3))/3 + "\n");
    resultArea.append("surface area = " + (4 * Math.PI * Math.pow(radius, 2)) + "\n");
   


    }
    private void reset() {
        inputText.setText("");
        resultArea.setText("");
        inputText.setEnabled(true);
        enterButton.setEnabled(true);
        inputText.requestFocus();
    }
    
    private Integer getInputNumber() {
    try {
    Integer n = Integer.parseInt(inputText.getText());
    return n;
    }
    catch (Exception e) {
    return null;
    }
   
    }
}