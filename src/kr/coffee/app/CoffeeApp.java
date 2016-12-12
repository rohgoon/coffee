package kr.coffee.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import kr.coffee.app.common.InputComp;
import kr.coffee.app.dao.ProductDao;
import kr.coffee.app.dao.SaleDao;
import kr.coffee.app.dto.Sale;
import kr.coffee.app.jdbc.DBCon;

@SuppressWarnings("serial")
public class CoffeeApp extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JButton btnInput;
	private JButton btnOutput1;
	private JButton btnOutput2;
	private InputComp pName;
	private InputComp pCode;
	private InputComp pPrice;
	private InputComp pCnt;
	private InputComp pMargin;
	private RankOutPut output1;
	private RankOutPut output2;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CoffeeApp frame = new CoffeeApp();
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							DBCon.closeConnection();
						}
					});
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public CoffeeApp() {
		setTitle("입력화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel pCenter = new JPanel();
		pCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		contentPane.add(pCenter, BorderLayout.CENTER);
		pCenter.setLayout(new GridLayout(0, 2, 0, 10));

		pCode = new InputComp();
		pCode.setLblNameText("제품코드 :");
		pCenter.add(pCode);

		pName = new InputComp();
		pName.setLblNameText("제품명 :");
		pName.getTfValue().addFocusListener(adapter);
		pCenter.add(pName);

		pPrice = new InputComp();
		pPrice.setLblNameText("제품단가 :");
		pPrice.getTfValue().setToolTipText("숫자 8자리까지 가능");
		pCenter.add(pPrice);

		JPanel panel_3 = new JPanel();
		pCenter.add(panel_3);

		pCnt = new InputComp();
		pCnt.setLblNameText("판매수량 :");
		pCnt.getTfValue().setToolTipText("숫자 8자리까지 가능");
		pCenter.add(pCnt);

		JPanel panel_5 = new JPanel();
		pCenter.add(panel_5);

		pMargin = new InputComp();
		pMargin.setLblNameText("마 진 율 : ");
		pMargin.getTfValue().setToolTipText("숫자 2자리까지 가능 20%이면 20입력");
		pCenter.add(pMargin);

		JPanel pBottom = new JPanel();
		contentPane.add(pBottom, BorderLayout.SOUTH);

		btnInput = new JButton("입력");
		btnInput.addActionListener(this);
		pBottom.add(btnInput);

		btnOutput1 = new JButton("출력1");
		btnOutput1.addActionListener(this);
		pBottom.add(btnOutput1);

		btnOutput2 = new JButton("출력2");
		btnOutput2.addActionListener(this);
		pBottom.add(btnOutput2);
		
		enableField(false);
	}

	FocusAdapter adapter = new FocusAdapter() {
		@Override
		public void focusGained(FocusEvent e) {
			String name = ProductDao.getInstance().selectProductNameByPcode(pCode.getTfValueText());
			if (name==null){
				JOptionPane.showMessageDialog(null, "해당 제품이 존재하지 않습니다.");
				pCode.setTfVaueText("");
				pCode.getTfValue().requestFocus();
				return;
			}
			pName.setTfVaueText(name);
			pCode.setTfVaueText(pCode.getTfValueText().toUpperCase());
			pCode.getTfValue().setEnabled(false);
			pName.getTfValue().setEnabled(false);
			enableField(true);
			pPrice.getTfValue().requestFocus();
		}
	};
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOutput2) {
			showTableList("마 진 액 순 위", RankOutPut.MARGIN_RANK);
		} else if (e.getSource() == btnOutput1) {
			showTableList("판 매 금 액 순 위", RankOutPut.SALE_RANK);
		} else if (e.getSource() == btnInput) {
			actionPerformedBtnInput(e);
		}
	}
	
	private void showTableList(String title, int state) {
		if (state==RankOutPut.SALE_RANK){
			if (output1==null) {
				output1 = new RankOutPut(state);
				output1.setTitle(title);
			}
			refreshListFrame(output1);
		}else{
			if (output2 == null){
				output2 = new RankOutPut(state);
				output2.setTitle(title);
			}
			refreshListFrame(output2);
		}
	}
	
	private void refreshListFrame(RankOutPut output) {
		output.setTableList();
		output.setVisible(true);		
	}
	
	protected void actionPerformedBtnInput(ActionEvent e) {
		if (validCheck()){
			Sale newItem = getItem();
			if (-1==SaleDao.getInstance().insertSale(newItem)){
				JOptionPane.showMessageDialog(null, "입력 실패");
			}else{
				JOptionPane.showMessageDialog(null, "입력 성공");
			}
			clearField(true);
			if (output1 != null){
				output1.setTableList();
			}
			if (output2 != null){
				output2.setTableList();
			}
		}
	}

	private Sale getItem() {
		String code = pCode.getTfValueText().trim();
		int price = Integer.valueOf(pPrice.getTfValueText().trim());
		int saleCnt = Integer.valueOf(pCnt.getTfValueText().trim());
		int marginRate = Integer.valueOf(pMargin.getTfValueText().trim());
		return new Sale(code, price, saleCnt, marginRate);
	}

	private boolean validCheck() {
		try {
			pCode.isEmptyCheck();
			pName.isEmptyCheck();
			pPrice.isEmptyCheck();
			pCnt.isEmptyCheck();
			pMargin.isEmptyCheck();
			pPrice.isValidCheck("[0-9]{1,8}","정수만 가능");
			pCnt.isValidCheck("[0-9]{1,8}", "정수만 가능");
			pMargin.isValidCheck("[0-9]{1,2}","2자리 정수로 입력");
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			return false;
		}
	}

	private void enableField(boolean isEnable){
		pPrice.getTfValue().setEnabled(isEnable);
		pCnt.getTfValue().setEnabled(isEnable);
		pMargin.getTfValue().setEnabled(isEnable);
	}
	
	private void clearField(boolean isAll){
		pPrice.setTfVaueText("");
		pCnt.setTfVaueText("");
		pMargin.setTfVaueText("");
		if (isAll){
			pCode.setTfVaueText("");
			pName.setTfVaueText("");
		}
	}
	
}
