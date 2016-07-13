package ui;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;

public class mainwindow {

	public static JFrame mainframe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		long curren1 = System.currentTimeMillis();
		curren1 += 2500;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame("欢迎屏幕");
					frame.setIconImage(Toolkit.getDefaultToolkit().getImage(mainwindow.class.getResource("/pic/logo.png")));
			        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			        frame.setUndecorated(true);
			        welcome splash = new welcome("/Users/gaoyile/java/src/pic/login.gif", frame,2500);
			        splash.setIconImage(Toolkit.getDefaultToolkit().getImage(mainwindow.class.getResource("/pic/logo.png")));
			        frame.pack();
			        frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		int check = 0;
        while(check==0){
        	long curren2 = System.currentTimeMillis();
    		if((curren2-curren1)>400){
    			mainwindow window = new mainwindow();
				window.mainframe.setVisible(true);
				TSP2 window1 = new TSP2();
				TSP2.frmTsp.setVisible(false);
				TNRP window2 = new TNRP();
				TNRP.frame.setVisible(false);
				TSP window3 = new TSP();
				TSP.frmTsp.setVisible(false);
				check=1;
    		}
        }
	}
	/**
	 * Create the application.
	 */
	public mainwindow() {
		initialize();
		//setWindowIcon();
	}
	
	/*public void setWindowIcon()  
    {  
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/pic/logo.png"));  
        this.setIconImage(imageIcon.getImage());  
    }
    
    */

	/**
	 * Initialize the contents of the frame.
	 */
	
	public static void initialize() {
		mainframe = new JFrame("CNC");
		mainframe.setForeground(new Color(175, 238, 238));
		mainframe.setIconImage(Toolkit.getDefaultToolkit().getImage(mainwindow.class.getResource("/pic/logo.png")));
		mainframe.getContentPane().setBackground(new Color(255, 255, 224));
		mainframe.setBackground(new Color(175, 238, 238));
		mainframe.setResizable(false);
		mainframe.setBounds(100, 100, 600, 400);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.getContentPane().setLayout(null);
		mainframe.setLocationRelativeTo(null);
		
		
		JButton btnNewButton = new JButton();
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TSP-1.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TSP.png")));
			}
		});
		btnNewButton.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TSP.png")));
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.setBackground(Color.YELLOW);
		
		
		JButton btnNewButton_4 = new JButton("exit");
		btnNewButton_4.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/box_850144472276584.png")));
		btnNewButton_4.setBounds(229, 155, 146, 57);
		mainframe.getContentPane().add(btnNewButton_4);
		btnNewButton.setBounds(6, 6, 294, 182);
		mainframe.getContentPane().add(btnNewButton);
		btnNewButton_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_4.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/quit.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_4.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/box_850144472276584.png")));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		
		JButton btnNewButton_1 = new JButton();
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_1.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TSP-RESHAPE-1.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_1.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TSP-RESHAPE.png")));
			}
		});
		btnNewButton_1.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TSP-RESHAPE.png")));
		//btnNewButton_1.setEnabled(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				TSP2.frmTsp.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(300, 6, 294, 182);
		mainframe.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton();
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_3.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/Help-1.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_3.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/Help.png")));
			}
		});
		btnNewButton_3.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/Help.png")));
		int tmp=0;
		btnNewButton_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Help dialog = new Help();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				}
		});
		btnNewButton_3.setBounds(300, 190, 294, 182);
		mainframe.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_2 = new JButton();
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnNewButton_2.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TNRP-1.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnNewButton_2.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TNRP.png")));
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(mainwindow.class.getResource("/pic/TNRP.png")));
		//btnNewButton_2.setEnabled(false);
		
		btnNewButton_2.setBounds(6, 190, 294, 182);
		mainframe.getContentPane().add(btnNewButton_2);
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TNRP.frame.setVisible(true);
				
			}
		});
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TSP window = new TSP();
				window.frmTsp.setVisible(true);
				btnNewButton_2.setEnabled(true);
				btnNewButton_1.setEnabled(true);
			}
		});
	}
}