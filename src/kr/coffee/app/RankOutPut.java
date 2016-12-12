package kr.coffee.app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.List;

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

import kr.coffee.app.dao.SaleDao;
import kr.coffee.app.dto.Sale;

@SuppressWarnings("serial")
public class RankOutPut extends JFrame {

	private JPanel contentPane;
	private JLabel lblTitle;
	private JTable table;
	
	private static final String[] COL_NAMES = {"순위", "제품코드", "제품명", "제품단가", "판매수량", "공급가액", "부가세액", "판매금액", "마진율", "마진액"};
	public boolean isSalePrice;
	
	public RankOutPut(boolean isSalePrice) {
		this.isSalePrice = isSalePrice;
		
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
	
	public String[][] getRowDatas() {
		List<Sale> rowDatas = SaleDao.getInstance().selectSaleDetailOrderBySalePrice(isSalePrice);
		String[][] arRowDatas = new String[rowDatas.size()+1][];
		for(int i=0; i<rowDatas.size(); i++){
			arRowDatas[i]=rowDatas.get(i).toArray();
		}
		arRowDatas[rowDatas.size()]=SaleDao.getInstance().getTotal();
		return arRowDatas;
	}
	
	public void reload() {
		DefaultTableModel model= new DefaultTableModel(getRowDatas(), COL_NAMES);
		table.setModel(model);
		tableCellAlignment(SwingConstants.CENTER, 0,1,2);
		tableCellAlignment(SwingConstants.RIGHT, 3,4,5,6,7,8,9);			
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
