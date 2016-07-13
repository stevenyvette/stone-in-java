package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextPane;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.JScrollPane;


public class TSP2 {

	public static JFrame frmTsp;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TSP2 window = new TSP2();
					window.frmTsp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TSP2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTsp = new JFrame();
		frmTsp.getContentPane().setBackground(new Color(240, 255, 240));
		frmTsp.setTitle("TSP-Reshape");
		frmTsp.setBounds(100, 100, 450, 300);
		frmTsp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmTsp.getContentPane().setLayout(null);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screensize.getWidth();
		int screenHeight = (int)screensize.getHeight();
		frmTsp.setLocation(screenWidth-550, 80);
		
		JButton btnNewButton = new JButton("确定");
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(58, 183, 89, 55);
		frmTsp.getContentPane().add(lblNewLabel_1);
		JLabel lblNewLabel = new JLabel("最优候补者：");
		lblNewLabel.setBounds(16, 169, 141, 16);
		frmTsp.getContentPane().add(lblNewLabel);
		JLabel label_1 = new JLabel("候补者集:");
		label_1.setBounds(16, 106, 63, 29);
		frmTsp.getContentPane().add(label_1);
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBounds(16, 135, 119, 36);
		frmTsp.getContentPane().add(lblNewLabel_2);
		JTextPane textPane = new JTextPane();
		textPane.setBounds(168, 106, 256, 132);
		frmTsp.getContentPane().add(textPane);
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(159, 106, 272, 132);
		frmTsp.getContentPane().add(scrollPane);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				code.R = textField.getText().charAt(0);
				code.Candidate(code.R,code.k,code.count);
	            code.predict=-1;
	            code.max=0;
	            try{
	    			FileWriter fw = new FileWriter("/Users/gaoyile/java/re-info.txt",true);
	    			String a="       WRP       rv(v,r)     p\n------------------------------------";
				    System.out.println(a);
				    fw.write(a+'\n');

				    for(int i=0;i<code.count;i++)
				        if(i!=(code.R-65)){
				            String c="  "+(char)(i+65)+"   "
				                    +String.format("%.4f", code.WRP((char)(i+65),code.R,code.count))+"     "
				                    +String.format("%.4f", code.ReplacementValue((char)(i+65),code.R,code.count))+"    "
				                    +String.format("%.4f", code.ReplaceProbability((char)(i+65),code.R,code.count));
				            System.out.println(c);
				            fw.write(c+'\n');
				        }
				    System.out.println("------------------------------------");
				    fw.write("------------------------------------\n");

				    if(code.predict>-1){
				    	String b = "";
				        for(int i=0;i<code.count;i++)
				            if(code.candidate[i]!=(char)(0))
				                b = b + code.candidate[i] + " ";
				        lblNewLabel_2.setText(b);
				        String f="By TSP, vertex "+ code.V
				                +" is the best candidate \nwhich means it is most likely to replace vertex "+code.R+" .";
				        System.out.println(f);
				        fw.write(f+'\n');
				        lblNewLabel_1.setText(String.valueOf(code.V)); 	
				    }
				    else{
				        String f="By TSP, there is no candidate vertex to replace vertex "+code.R+" .";

				        System.out.println(f+"None");
				        fw.write('\n');
				        lblNewLabel_1.setText("None");
				        lblNewLabel_2.setText("None");
				    }
	    		    fw.close();
	    		    
	    		} catch (IOException e1) {
	                e1.printStackTrace();
	                System.out.println("写入失败");
	                System.exit(-1);
	            }
	            SimpleAttributeSet attrset = new SimpleAttributeSet();
		        StyleConstants.setFontSize(attrset,12);
	            Document docs = textPane.getDocument();
	            try {
		        	File file=new File("/Users/gaoyile/java/re-info.txt");
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
				
			    
			    code.ReshapeAdj(code.V,code.R,code.count);
			    code.ReshapePro(code.V,code.R,code.count);
			    
			}
		});
		btnNewButton.setBounds(83, 74, 63, 35);
		frmTsp.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("上一步");
		btnNewButton_2.setForeground(Color.BLACK);
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TNRP.frame.setVisible(true);
				TSP2.frmTsp.setVisible(false);
			}
		});
		btnNewButton_2.setBounds(6, 243, 117, 29);
		frmTsp.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("下一步");
		btnNewButton_3.setBounds(327, 243, 117, 29);
		btnNewButton_3.setEnabled(false);
		frmTsp.getContentPane().add(btnNewButton_3);
		
		JLabel label = new JLabel("请输入想要移除的节点：");
		label.setBounds(6, 6, 153, 16);
		frmTsp.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setFont(new Font("Lucida Grande", Font.ITALIC, 13));
		textField.setForeground(Color.LIGHT_GRAY);
		
		textField.setToolTipText("输入吧");
		textField.setText("请输入");
		textField.setBounds(16, 34, 134, 28);
		frmTsp.getContentPane().add(textField);
		textField.setColumns(10);

		textField.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
				textField.setForeground(Color.BLACK);
				textField.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
			}
		});
		
		
	}
}
