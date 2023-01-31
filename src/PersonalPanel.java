import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;

public class PersonalPanel extends JPanel {

	private List<JComboBox<String>> comboBoxList;
	private String[] userSceduleList;
	JComboBox<String> userCB;
	JPanel mainCalendarPanel;
	private JLabel lblNewLabel;
	private JLabel lblMorning;

	public PersonalPanel() {
		setLayout(null);

		mainCalendarPanel = new JPanel();
		mainCalendarPanel.setBounds(25, 148, 525, 150);

		userSceduleList = new String[14];

		// list to show days of week.
		List<String> daysList = new ArrayList<String>(Arrays.asList("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"));
		JPanel daysPanel = new JPanel();
		daysPanel.setBounds(25, 128, 525, 20);
		daysPanel.setLayout(new GridLayout(1, 7, 2, 2));

		// loop to show add days name on top of calendar
		for (int i = 0; i < 7; i++) {
			JLabel day = new JLabel(daysList.get(i), SwingConstants.CENTER);
			day.setSize(75, 20);
			daysPanel.add(day);
		}
		userCB = new JComboBox<String>();
		userCB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		userCB.setBounds(10, 11, 75, 30);
		userCB.addItem("User1");
		userCB.addItem("User2");
		userCB.addItem("User3");
		userCB.addItem("User4");
		userCB.setSelectedIndex(0);

		// user combobox action listener
		userCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// when change occurs on combobox selection userSchedule list will be updated by getting schedule of that user
				// refresh the calendar according to updated user schedule list
				userSceduleList = getUserScheduleList(userCB.getSelectedItem().toString());
				populateCalendar();
			}
		});

		JButton saveBtn = new JButton("SAVE");

		// save button action listner
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// on clicking save button storeUserSchedule() will be invoked and store current user schedule to storage
				storeUsreSchedule(userCB.getSelectedItem().toString());
			}
		});

		saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		saveBtn.setBounds(212, 309, 121, 30);

		add(saveBtn);
		add(mainCalendarPanel);
		add(userCB);
		add(daysPanel);

		// labels to show EVENING and MORNING text on side of calendar
		// user HTML to input text in JLabel to show vertically
		lblNewLabel = new JLabel("<html><p>E<br>V<br>E<br>N<br>I<br>N<br>G</p></html>");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 8));
		lblNewLabel.setBounds(0, 223, 25, 75);
		add(lblNewLabel);
		
		lblMorning = new JLabel("<html><p>M<br>O<br>R<br>N<br>I<br>N<br>G</p></html>");
		lblMorning.setHorizontalAlignment(SwingConstants.CENTER);
		lblMorning.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 8));
		lblMorning.setBounds(0, 147, 25, 75);
		add(lblMorning);

		userSceduleList = getUserScheduleList(userCB.getSelectedItem().toString());
		populateCalendar();

	}

	/**
	 * Method to store user schedule to device storage
	 * @param user: indicate which users schedule to store
	 */

	private void storeUsreSchedule(String user) {
		// user FileOutputStream can create file with <user>.ser name
		try (FileOutputStream fos = new FileOutputStream(user + ".ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			// write userScheduleList object to file
			// whole userScheduleList will be written to file as an object.
			oos.writeObject(userSceduleList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get user schedule from store file
	 * @param user: indicate which user data need to be retrieved
	 * @return userScheduleList
	 */

	private String[] getUserScheduleList(String user) {
		// FileInputStream to open and read file
		try (FileInputStream fis = new FileInputStream(user + ".ser");
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			// readObject from file and assign it to local variable of type String[]
			String[] readArray = (String[]) ois.readObject();
			// return schedule array
			return readArray;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * Method to populate calendar based on userScheduleList
	 */

	private void populateCalendar() {
		// initialize the list of combobox
		comboBoxList = new ArrayList<JComboBox<String>>();
		// remove all component from panel so that new users schedule could be shown
		mainCalendarPanel.removeAll();
		mainCalendarPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		mainCalendarPanel.setLayout(new GridLayout(2, 7, 2, 2));

		// loop to populate combobox based on userScheduleList
		for (int i = 0; i < 14; i++) {
			JComboBox<String> cb = new JComboBox<>();
			cb.addItem("Yes");
			cb.addItem("No");
			cb.setSize(70, 25);
			cb.setSelectedItem(userSceduleList[i]);
			// actionlistener that handel change/update in schedule
			cb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// check if user select YES from combobox and wants to reserve slot
					// then also check either that slot is available of not
					// if both conditions meet
					if (cb.getSelectedItem().toString().equals("Yes") && isSlotAvaiable(comboBoxList.indexOf(cb))) {
						// then update userScheduleList to YES i.e current selection of combobox
						userSceduleList[comboBoxList.indexOf(cb)] = cb.getSelectedItem().toString();
					} else {
						// if slot not available or user select NO on combobox then set cb to NO and update userScheduleList to NO
						cb.setSelectedItem("No");
						userSceduleList[comboBoxList.indexOf(cb)] = cb.getSelectedItem().toString();
					}

				}
			});
			// add combobox to comboboxList
			comboBoxList.add(cb);
		}

		// loop to iterate comboboxList to add combobox into mainCalendarPanel
		for (int i = 0; i < 14; i++) {
			JPanel cell = new JPanel();
			cell.setLayout(new GridBagLayout());
			cell.setSize(75, 75);
			cell.setBorder(new LineBorder(new Color(0, 0, 0)));
			cell.add(comboBoxList.get(i));
			mainCalendarPanel.add(cell);
		}
		// revalidate() and repaint() GUI. i.e refresh screen.
		revalidate();
		repaint();
	}

	/**
	 * Method to check either slot at given index is available or not
	 * @param index
	 * @return true: if available, false: if already reserved by some other user
	 */
	private boolean isSlotAvaiable(int index) {
		// get current user i.e selected from user combobox
		String currentUser = userCB.getSelectedItem().toString();
		// declare and initialize list to store all users
		List<String> userList = new ArrayList<>();
		userList.add("User1");
		userList.add("User2");
		userList.add("User3");
		userList.add("User4");

		// remove the current user;
		userList.remove(currentUser);

		// iterate userList that contains all users except current one
		for (String user : userList)
		{
			// check if user schedule at current slot(index) is YES(reserved) then return false
			if (getUserScheduleList(user)[index].equals("Yes"))
			{
				JOptionPane.showMessageDialog(null, "Slot not available. Occupied by " + user);
				return false;
			}
		}
		// if loop ends without entering if condition that means slot available. then return true
		return true;
	}
}
