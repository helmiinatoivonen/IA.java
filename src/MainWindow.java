import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.LineBorder;
import java.awt.Color;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JLabel titleLabel;
	private JComboBox<String> menuCB;
	private String[] menuItems;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 735, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		menuItems = new String[] { "Main", "Personal", "Quit" };
		JPanel mainPanel = new JPanel();
		contentPane.add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);

		JPanel calendarPanel = new JPanel();
		calendarPanel.setBounds(134, 71, 565, 350);
		calendarPanel.setLayout(new BorderLayout(0, 0));

		menuCB = new JComboBox<String>(menuItems);
		menuCB.setFont(new Font("Tahoma", Font.BOLD, 14));
		menuCB.setBounds(10, 11, 115, 30);
		// action listner of menu combobox
		menuCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// remove all components from calendarPanel
				calendarPanel.removeAll();
				// check if selected menu is MAIN
				if (menuCB.getSelectedItem().toString().equals("Main")) {
					// get CalendarPanel
					CalendarPanel cPanel = new CalendarPanel();
					cPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
					// add calendarPanel
					calendarPanel.add(cPanel);
					// revalidate() and repaint() current screen
					revalidate();
					repaint();

				} else if (menuCB.getSelectedItem().toString().equals("Personal")) {
					// if selected menu is PERSONAL
					// get PersonalPanel
					PersonalPanel pPanel = new PersonalPanel();
					pPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
					// add personal panel
					calendarPanel.add(pPanel);
					revalidate();
					repaint();

				} else {
					System.exit(0);
				}
			}
		});

		CalendarPanel cPanel = new CalendarPanel();
		cPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		calendarPanel.add(cPanel);

		mainPanel.add(menuCB);
		mainPanel.add(calendarPanel);

		titleLabel = new JLabel("Main Schedule");
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(134, 11, 425, 30);
		mainPanel.add(titleLabel);
	}
}
