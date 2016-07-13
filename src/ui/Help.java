package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Help extends JDialog {

	public final static JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Help dialog = new Help();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Help() {
		getContentPane().setBackground(new Color(135, 206, 250));
		setTitle("Help");
		setBounds(100, 100, 450, 300);
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screensize.getWidth();
		int screenHeight = (int)screensize.getHeight();
		setLocation(screenWidth-550,screenHeight-408);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(new Color(240, 248, 255));
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(new Color(240, 248, 255));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton okButton = new JButton("确定");
		okButton.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					setVisible(false);   //隐藏窗口
				}
			});
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
	}
}
