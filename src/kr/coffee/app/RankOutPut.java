package kr.coffee.app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import kr.coffee.app.dao.SaleInfoDao;
import kr.coffee.app.dto.SaleInfo;

@SuppressWarnings("serial")
public class RankOutPut extends JFrame {

	private JPanel contentPane;
	private JLabel lblTitle;
	private ArrayList<SaleInfo> rowDatas;
	private int state;
	private JTable table;
	
	private static final String[] COL_NAMES = {"순위", "제품코드", "제품명", "제품단가", "판매수량", "공급가액", "부가세액", "판매금액", "마진율", "마진액"};
	public static final int SALE_RANK = 0;
	public static final int MARGIN_RANK = 1;
	
	public RankOutPut(int state) {
		this.state = state;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		lblTitle = new JLabel("판 매 금 액 순 위");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("굴림", Font.BOLD, 20));
		contentPane.add(lblTitle, BorderLayout.NORTH);
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	public void setTitle(String title){
		lblTitle.setText(title);
	}
	
	public void setTableList() {
		if (state == SALE_RANK){
			rowDatas = SaleInfoDao.getInstance().selectAllbyOrderBySalePrice();
		}else{
			rowDatas = SaleInfoDao.getInstance().selectAllbyOrderByMarginPrice();
		}
		rowDatas.add(SaleInfoDao.getInstance().selectTotal());
		setModel();
	}
	
	private void setModel() {
		DefaultTableModel model= new DefaultTableModel(toStrArr(rowDatas), COL_NAMES);
		table.setModel(model);
		tableCellAlignment(SwingConstants.CENTER, 0,1,2);
		tableCellAlignment(SwingConstants.RIGHT, 3,4,5,6,7,8,9);			
	}
	
	private String[][] toStrArr(ArrayList<SaleInfo> rowDatas) {
		String[][] arRows = new String[rowDatas.size()][9];
		for(int i=0; i<rowDatas.size(); i++){
			arRows[i] = rowDatas.get(i).toArray();
		}
		return arRows;
	}
	
	public void tableCellAlignment(int align, int... idx) {
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(align);

		TableColumnModel model = table.getColumnModel();
		for (int i = 0; i < idx.length; i++) {
			model.getColumn(idx[i]).setCellRenderer(dtcr);
		}
	}

	public void tableSetWidth(int... width) {
		TableColumnModel cModel = table.getColumnModel();

		for (int i = 0; i < width.length; i++) {
			cModel.getColumn(i).setPreferredWidth(width[i]);
		}
	}
}
