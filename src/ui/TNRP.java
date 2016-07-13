package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JSplitPane;
import javax.swing.JLayeredPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.JList;
import javax.swing.JSlider;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import java.awt.Color;

public class TNRP {

	public static JFrame frame;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TNRP window = new TNRP();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TNRP() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	 void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(224, 255, 255));
		frame.setTitle("TNRP");
		frame.setBounds(100, 100, 570, 408);
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screensize.getWidth();
		int screenHeight = (int)screensize.getHeight();
		frame.setLocation(75, screenHeight-408);
		
		JButton btnNewButton = new JButton("上一步");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				TSP.frmTsp.setVisible(true);
			}
		});
		
		JButton button = new JButton("计算");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				code.PossibleWorld(code.count);
				SimpleAttributeSet attrset = new SimpleAttributeSet();
		        StyleConstants.setFontSize(attrset,24);

				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(59, 24, 468, 308);
				frame.getContentPane().add(scrollPane);
				
				JTextPane textPane = new JTextPane();
				scrollPane.setViewportView(textPane);
				textPane.setEditable(false);
				
				Document docs = textPane.getDocument();
				
		        try {
		        	File file=new File("/Users/gaoyile/java/possibleworld.txt");
		            if(file.isFile() && file.exists()){ //判断文件是否存在
		                InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GBK");//考虑到编码格式
		                BufferedReader bufferedReader = new BufferedReader(read);
		                String lineTxt = null;
		                while((lineTxt = bufferedReader.readLine()) != null)
		                	docs.insertString(docs.getLength(), lineTxt+"\n", attrset);
		                read.close();
		            }else
	            	System.out.println("找不到指定的文件");
				}catch (Exception e1) {
			        System.out.println("读取文件内容出错");
			        e1.printStackTrace();
				}
			}
		});
		
		button.setBounds(234, 340, 117, 29);
		frame.getContentPane().add(button);
		btnNewButton.setBounds(65, 340, 117, 29);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("下一步");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				TSP2.frmTsp.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(410, 340, 117, 29);
		frame.getContentPane().add(btnNewButton_1);
		

	}
}
