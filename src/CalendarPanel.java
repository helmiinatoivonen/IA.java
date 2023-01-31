import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import java.awt.Font;

public class CalendarPanel extends JPanel {
	private String[] scheduleList;

	public CalendarPanel() {
		setLayout(null);

		JPanel mainCalendarPanel = new JPanel();
		mainCalendarPanel.setBounds(25, 189, 525, 150);
		scheduleList = new String[14];

		// call getSchedule() method that will update scheduleList
		getSchedule();

		mainCalendarPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		mainCalendarPanel.setLayout(new GridLayout(2, 7, 2, 2));

		// loop iterate schedulList to show timeTable on calendar
		for (int i = 0; i < 14; i++) {
			JPanel cell = new JPanel();
			cell.setLayout(new BorderLayout());
			cell.setSize(75, 75);
			cell.setBorder(new LineBorder(new Color(0, 0, 0)));
			// check if scheduleList at current index is null that means no user has reserved that slot
			if (scheduleList[i] == null) {
				// set background to black
				cell.setBackground(Color.black);
			} else {
				// else if slot is reserved by a user then set Label text to that user
				JLabel user = new JLabel(scheduleList[i], SwingConstants.CENTER);
				cell.add(user);
			}

			// add current cell to mainCalendarPanel. cell contain(userLabel)
			mainCalendarPanel.add(cell);
		}

		// add mianCalendarPanel to frame(window)
		add(mainCalendarPanel);

		JPanel daysPanel = new JPanel();
		daysPanel.setBounds(25, 169, 525, 20);
		daysPanel.setLayout(new GridLayout(1, 7, 1, 0));

		// list to show days of week.
		List<String> daysList = new ArrayList<String>(Arrays.asList("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"));
		// loop to show add days name on top of calendar
		for (int i = 0; i < 7; i++) {
			JLabel day = new JLabel(daysList.get(i), SwingConstants.CENTER);
			day.setSize(75, 20);
			daysPanel.add(day);
		}
		add(daysPanel);

		// labels to show EVENING and MORNING text on side of calendar
		// user HTML to input text in JLabel to show vertically

		JLabel lblMorning = new JLabel("<html><p>M<br>O<br>R<br>N<br>I<br>N<br>G</p></html>");
		lblMorning.setHorizontalAlignment(SwingConstants.CENTER);
		lblMorning.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 8));
		lblMorning.setBounds(0, 188, 25, 75);
		add(lblMorning);
		
		JLabel lblNewLabel = new JLabel("<html><p>E<br>V<br>E<br>N<br>I<br>N<br>G</p></html>");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 8));
		lblNewLabel.setBounds(0, 264, 25, 75);
		add(lblNewLabel);

	}

	/**
	 * Method to get all users schedule and update scheduleList
	 */
	private void getSchedule() {

		// initialize userList and populate it will all users
		List<String> userList = new ArrayList<>();
		userList.add("User1");
		userList.add("User2");
		userList.add("User3");
		userList.add("User4");

		// loop to iterate all users
		for (String user : userList) {
			// get schedule of user
			String[] userSchedule = getUserScheduleList(user);
			// iterate user schedule
			for (int i = 0; i < 14; i++) {
				// check if userSchedule at index i is YES
				if (userSchedule[i].equals("Yes")) {
					// add user to scheduleList
					scheduleList[i] = user;
				}
			}
		}
	}

	/**
	 * Method to get user schedule from store file
	 * @param user: indicate which user data need to be retrieved
	 * @return user schedule
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
}
