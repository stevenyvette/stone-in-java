package ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class TSP {

	public static JFrame frmTsp;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TSP window = new TSP();
					window.frmTsp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the application.
	 */
	public TSP() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmTsp = new JFrame();
		frmTsp.getContentPane().setBackground(new Color(253, 245, 230));
		frmTsp.setTitle("TSP");
		frmTsp.setBounds(100, 100, 450, 300);
		frmTsp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTsp.getContentPane().setLayout(null);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screensize.getWidth();
		int screenHeight = (int)screensize.getHeight();
		frmTsp.setLocation(150, 80);
		
		JPanel panel = new JPanel();
		panel.setBounds(158, 6, 273, 102);
		frmTsp.getContentPane().add(panel);
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(new Color(250, 250, 210));
		
		textPane.setBounds(0, 69, 275, 110);
		frmTsp.getContentPane().add(textPane);
		
		SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,12);
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(156, 121, 275, 110);
		textPane.setEditable(false);
		frmTsp.getContentPane().add(scrollPane);
		Document docs = textPane.getDocument();
		
		JButton btnNewButton = new JButton("打开网络结构文件");
		btnNewButton.setFont(new Font("Bradley Hand", Font.BOLD | Font.ITALIC, 13));
		btnNewButton.setForeground(new Color(255, 140, 0));
		btnNewButton.setBackground(new Color(230, 230, 250));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				test c = new test();
				c.test();
				
				code.adjfile("/Users/gaoyile/lab/qt/adjacency.txt");
				try {
		            String encoding="GBK";
		            File file=new File("/Users/gaoyile/java/settled.txt");
		            if(file.isFile() && file.exists()){ //判断文件是否存在
		                InputStreamReader read = new InputStreamReader(
		                new FileInputStream(file),encoding);//考虑到编码格式
		                BufferedReader bufferedReader = new BufferedReader(read);
		                String lineTxt = null;
		                while((lineTxt = bufferedReader.readLine()) != null){
		                	docs.insertString(docs.getLength(), lineTxt+"\n", attrset);
		                }
		                read.close();
		            }else
		            	System.out.println("找不到指定的文件");
		        } catch (Exception e1) {
		        System.out.println("读取文件内容出错");
		        e1.printStackTrace();
		        }
			}
		});
		btnNewButton.setBounds(6, 6, 120, 60);
		frmTsp.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("打开网络信息文件");
		btnNewButton_1.setFont(new Font("Bradley Hand", Font.BOLD | Font.ITALIC, 13));
		btnNewButton_1.setForeground(new Color(255, 127, 80));
		btnNewButton_1.setBackground(Color.GRAY);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				code.profile("/Users/gaoyile/lab/qt/property.txt");
				code.POCC(code.count);
				try {
		            String encoding="GBK";
		            File file=new File("/Users/gaoyile/java/settled.txt");
		            if(file.isFile() && file.exists()){ //判断文件是否存在
		                InputStreamReader read = new InputStreamReader(
		                new FileInputStream(file),encoding);//考虑到编码格式
		                BufferedReader bufferedReader = new BufferedReader(read);
		                String lineTxt = null;
		                int tmp = 0;
		                while((lineTxt = bufferedReader.readLine()) != null){
		                	if(tmp>=11)
		                		docs.insertString(docs.getLength(), lineTxt+"\n", attrset);
		                	else
		                		tmp++;
		                }
		                read.close();
		            }else
		            	System.out.println("找不到指定的文件");
		        } catch (Exception e1) {
		        System.out.println("读取文件内容出错");
		        e1.printStackTrace();
		        }
			}
		});
		btnNewButton_1.setBounds(6, 84, 120, 60);
		frmTsp.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("上一步");
		btnNewButton_2.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmTsp.setVisible(false);
			}
		});
		btnNewButton_2.setBounds(6, 243, 117, 29);
		btnNewButton_2.setEnabled(false);
		frmTsp.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("下一步");
		btnNewButton_3.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmTsp.setVisible(false);
				TNRP.frame.setVisible(true);
			}
		});
		btnNewButton_3.setBounds(324, 243, 120, 29);
		frmTsp.getContentPane().add(btnNewButton_3);

	}
}
