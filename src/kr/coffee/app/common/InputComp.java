package kr.coffee.app.common;

import java.awt.GridLayout;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class InputComp extends JPanel {
	private JTextField tfValue;
	private JLabel lblName;

	public InputComp() {
		setLayout(new GridLayout(1, 0, 10, 0));
		
		lblName = new JLabel("New label");
		lblName.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lblName);
		
		tfValue = new JTextField();
		add(tfValue);
		tfValue.setColumns(10);

	}

	public void setTfVaueText(String value){
		tfValue.setText(value.trim());
	}
	
	public String getTfValueText(){
		return tfValue.getText().trim();
	}
	
	public void setLblNameText(String name){
		lblName.setText(name.trim());
	}
	
	public String getLblNameText(){
		return lblName.getText().trim();
	}

	public JTextField getTfValue() {
		return tfValue;
	}
	
	public void isEmptyCheck() throws Exception{
		if(getTfValueText().equals("")){
			throw new Exception("공백 존재");
		}
	}
	
	public void isValidCheck(String pattern, String msg) throws Exception{
		if(!Pattern.matches(pattern, getTfValueText())){
			throw new Exception(msg);
		}
	}
}
