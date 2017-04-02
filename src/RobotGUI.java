/**
 * Title        RobotGUI.java
 * Description  This class contains the Robot GUI's definition.
 * Copyright    (c) 2017 Copyright Holder All Rights Reserved.
 *
 * @author Question
 * @date 30/05/2016
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class RobotGUI extends JFrame implements ActionListener {

	private int width = (int) ((float) (Toolkit.getDefaultToolkit().getScreenSize().width) / 2); // Set the width of Robot GUI.
	private int height = (int) ((float) (width) / 16 * 9); // Set the height of Robot GUI.
	private int helloFontSize = (int) ((float) (height) / 32 * 3); // The font size of hello screen.
	private Color helloFontColor = Color.BLACK; // The font color of hello screen.
	private Font helloFont = new Font("Comic Sans MS", Font.BOLD, helloFontSize);
	private int listFontSize = (int) ((float) (helloFontSize) / 3 * 2); // The font size of option screen.
	private Color listFontColor = Color.BLACK; // The font color of option screen.
	private Font listFont = new Font("Comic Sans MS", Font.BOLD, listFontSize); // The font option screen.
	private Color[] buttonColor = {new Color(157, 195, 229), new Color(243, 177, 131), // The color of six buttons.
			new Color(127, 224, 248), new Color(255, 254, 167),
			new Color(229, 146, 223), new Color(142, 250, 165)}; // Get these color from PhotoShop.

	// Instance variables.
	private RobotInterface robotInterface = new RobotInterface(); // RobotInterface will help robot do other things except GUI.
	private int sort = -1; // Use this variable to know which option user choose.
	private int listNumber; // The number of items that display on the button.
	private String[][] dishInformation; // Contains the dishes' information or sort messages.
	private listButton[] button = new listButton[6]; // The select buttons on the window.
	private JButton leave, backToSort, pay, stopJoke;
	private JTextField selectTextField;
	private JPanel helloPanel, dishPanel, jokePanel;
	private boolean isPaid = true;

	// Program starts here.
	public static void main(String[] args) {
		new RobotGUI();
	}

	/**
	 * This method create a JFrame and set some settings.
	 */
	private RobotGUI() {
		super("Robot"); // Set the title of Robot GUI.
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Do nothing when click the exit button.
		Color guiBackgroundColor = new Color(90, 154, 212);
		getContentPane().setBackground(guiBackgroundColor); // Set the background color of window.
		setSize(width, height); // Set the size of window.
		setResizable(false); // Forbid to resize the window.
		setLocationRelativeTo(null); // Set the window in the centre of screen.
		setVisible(true);
		timeToSleep(); // Make timer start.
		sayHello();
	}

	/**
	 * This method will print welcome message on the screen.
	 */
	private void sayHello() {
		JLabel helloLabel = new JLabel(robotInterface.getWelcome(), JLabel.CENTER);
		helloLabel.setFont(helloFont);
		helloLabel.setForeground(helloFontColor);

		JButton enter = new JButton("Enter");
		enter.setFont(listFont);
		enter.setForeground(listFontColor);
		enter.addActionListener(e -> {
			remove(helloPanel);
			dishPanel(robotInterface.getSort(), true);
		});
		JPanel helloSouthPanel = new JPanel();
		helloSouthPanel.setOpaque(false);
		helloSouthPanel.add(enter);

		helloPanel = new JPanel(new BorderLayout());
		helloPanel.setOpaque(false);
		helloPanel.add(helloLabel, BorderLayout.CENTER);
		helloPanel.add(helloSouthPanel, BorderLayout.SOUTH);

		add(helloPanel);
		validate(); // Refresh the window to show the welcome message.
	}

	/**
	 * This method will draw a select panel that allow user to choose one.
	 *
	 * @param tempString The list that will provide to user to choose.
	 * @param isSort     Tell the method if is going to draw sort panel or dish panel.
	 */
	private void dishPanel(String[][] tempString, boolean isSort) {
		int gap = (int) ((float) (width) / 150);
		dishPanel = new JPanel(new BorderLayout(gap, gap));
		dishPanel.setOpaque(false);
		dishInformation = tempString;

		// The north part of order panel.
		String headString;
		if (isSort) headString = "Please select an option:";
		else headString = "Please select a dish to order:";
		JLabel headLabel = new JLabel(headString, JLabel.LEFT);
		headLabel.setFont(listFont);
		headLabel.setForeground(listFontColor);
		dishPanel.add(headLabel, BorderLayout.NORTH);

		// The centre part of order panel. Contain 6 buttons.
		JPanel buttonPanel = new JPanel(new GridLayout(2, 3, gap, gap));
		buttonPanel.setOpaque(false);

		if (isSort) listNumber = dishInformation[0].length;
		else listNumber = dishInformation.length;
		int j = 0;
		for (; j < listNumber; j++) {
			if (isSort) button[j] = new listButton(buttonColor[j], "" + (j + 1) + "<br />" + dishInformation[0][j]);
			else button[j] = new listButton(buttonColor[j], "" + (j + 1) + "<br />" + dishInformation[j][0]);
			button[j].addActionListener(this);
			buttonPanel.add(button[j]);
		}
		for (; j < 6; j++) {
			button[j] = new listButton(buttonColor[j]);
			buttonPanel.add(button[j]);
		}

		dishPanel.add(buttonPanel, BorderLayout.CENTER);

		// The south part of order panel.
		leave = new JButton("Leave");
		leave.setFont(listFont);
		leave.setForeground(listFontColor);
		leave.addActionListener(this);

		pay = new JButton("Pay");
		pay.setFont(listFont);
		pay.setForeground(listFontColor);
		pay.addActionListener(this);

		backToSort = new JButton("Back");
		backToSort.setFont(listFont);
		backToSort.setForeground(listFontColor);
		backToSort.addActionListener(this);

		String endString = "Option selected:";
		JLabel endLabel = new JLabel(endString, JLabel.RIGHT);
		endLabel.setFont(listFont);
		endLabel.setForeground(listFontColor);

		selectTextField = new JTextField(gap);
		selectTextField.setMaximumSize(selectTextField.getPreferredSize());
		selectTextField.addActionListener(this);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.X_AXIS));
		southPanel.setOpaque(false);
		southPanel.add(leave);
		southPanel.add(Box.createHorizontalStrut(gap * 10));
		southPanel.add(backToSort);
		if (isSort) backToSort.setEnabled(false);
		else backToSort.setEnabled(true);
		southPanel.add(Box.createHorizontalStrut(gap * 10));
		southPanel.add(pay);
		if (isPaid) pay.setEnabled(false);
		else pay.setEnabled(true);
		southPanel.add(Box.createGlue());
		southPanel.add(endLabel);
		southPanel.add(selectTextField);
		dishPanel.add(southPanel, BorderLayout.SOUTH);

		add(dishPanel);
		validate();
	}

	/**
	 * This method will draw a panel which contains all choices.
	 *
	 * @param number Tell Robot to change which panel.
	 */
	private void changeDishPanel(int number) {
		remove(dishPanel);
		sort = number;
		switch (number) {
			case 0:
				dishPanel(robotInterface.getFish(), false);
				break;
			case 1:
				dishPanel(robotInterface.getMeat(), false);
				break;
			case 2:
				dishPanel(robotInterface.getRice(), false);
				break;
			case 3:
				dishPanel(robotInterface.getNoodle(), false);
				break;
			case 4:
				dishPanel(robotInterface.getDrink(), false);
				break;
			case 5:
				break; // Robot now don't have this sort.
		}
	}

	/**
	 * If sort equals -1, this method will call changeDishPanel method.
	 * Otherwise, this method will call order method.
	 *
	 * @param select Tell Robot the user's choice.
	 */
	private void clickButton(int select) {
		switch (sort) {
			case -1:
				changeDishPanel(select);
				break;
			default:
				order(select);
		}
	}

	/**
	 * This method will order a dish if available.
	 *
	 * @param dishNumber Tell Robot the which dish that user choose.
	 */
	private void order(int dishNumber) {
		int dishQuantity = Integer.parseInt(dishInformation[dishNumber][2]);
		if (dishQuantity > 0) {
			isPaid = false;
			robotInterface.addPay(dishInformation[dishNumber][1]);
			robotInterface.addDishList(dishInformation[dishNumber][0]);
			dishInformation[dishNumber][2] = String.valueOf(--dishQuantity);
			robotInterface.writeString(sort, dishInformation);
			pay.setEnabled(true);
			validate();
			JLabel orderSuccess = new JLabel("Successful!", JLabel.CENTER);
			orderSuccess.setFont(listFont);
			orderSuccess.setForeground(listFontColor);
			JOptionPane.showMessageDialog(null, orderSuccess, "See here~", JOptionPane.INFORMATION_MESSAGE);
			robotInterface.setTime(0);
		} else { // Give a dialog to show another dish.
			int randomSelect;
			do randomSelect = (int) (Math.random() * listNumber);
			while (randomSelect == dishNumber);
			JLabel errorLabel = new JLabel("<html><center>This choice now is not available~<br />Why not try "
					+ dishInformation[randomSelect][0] + "?</center></html>");
			errorLabel.setFont(listFont);
			errorLabel.setForeground(listFontColor);
			JOptionPane.showMessageDialog(null, errorLabel, "Whoops!", JOptionPane.ERROR_MESSAGE);
			robotInterface.setTime(0);
		}
	}

	/**
	 * Check user if has paid, or will ask user to pay.
	 */
	private void leave() {
		if (isPaid) { // If have paid, user can leave.
			remove(dishPanel);

			JLabel leaveLabel = new JLabel(robotInterface.getFarewell(), JLabel.CENTER);
			leaveLabel.setFont(helloFont);
			leaveLabel.setForeground(helloFontColor);

			JButton exit = new JButton("Exit");
			exit.setFont(listFont);
			exit.setForeground(listFontColor);
			exit.addActionListener(e -> System.exit(0));
			JPanel leaveSouthPanel = new JPanel();
			leaveSouthPanel.setOpaque(false);
			leaveSouthPanel.add(exit);

			JPanel leavePanel = new JPanel(new BorderLayout());
			leavePanel.setOpaque(false);
			leavePanel.add(leaveLabel, BorderLayout.CENTER);
			leavePanel.add(leaveSouthPanel, BorderLayout.SOUTH);

			add(leavePanel);
			validate();
		} else { // User cannot leave if have not pay.
			JLabel errorLabel = new JLabel("<html><center>You must pay for your meals.<br />Do you want to check out now?</center></html>");
			errorLabel.setFont(listFont);
			int select = JOptionPane.showConfirmDialog(null, errorLabel, "Whoops!", JOptionPane.YES_NO_OPTION);
			robotInterface.setTime(0);
			if (select == 0) pay(true);
		}
	}

	/**
	 * This method make robot go back to sort panel.
	 *
	 * @param isJoke If now is in joke panel.
	 */
	private void backToSort(boolean isJoke) {
		if (isJoke) remove(jokePanel);
		else remove(dishPanel);
		sort = -1; // Change sort to -1, i.e. user is seeing sort panel.
		dishPanel(robotInterface.getSort(), true);
	}

	/**
	 * This method will check out, list all order and total price.
	 *
	 * @param isLeaving If the user is going to leave.
	 */
	private void pay(boolean isLeaving) {
		JLabel payConfirmLabel = new JLabel("<html><center>Do you want to check out?~<br />"
				+ robotInterface.getDishList()
				+ "Your cost: " + robotInterface.getPay() + " yuan</center></html>");
		payConfirmLabel.setFont(listFont);
		int select = JOptionPane.showConfirmDialog(null, payConfirmLabel, "See here~", JOptionPane.YES_NO_OPTION); // YES = 0 / NO = 1
		robotInterface.setTime(0);
		if (select == 0) {
			isPaid = true;
			robotInterface = new RobotInterface();
			JLabel payLabel = new JLabel("<html><center>Payment successful!~<br />Enjoy your meal~</center></html>");
			payLabel.setFont(listFont);
			JOptionPane.showMessageDialog(null, payLabel, "See here~", JOptionPane.INFORMATION_MESSAGE);
			robotInterface.setTime(0);
			if (isLeaving) leave();
			else showJoke();
		}
	}

	/**
	 * This method will make robot to tell a joke.
	 */
	private void showJoke() {
		remove(dishPanel);

		JTextPane jokePane = new JTextPane();
		jokePane.setOpaque(false);
		jokePane.setEditable(false);
		jokePane.setFont(listFont);
		jokePane.setText(robotInterface.getJoke());

		stopJoke = new JButton("Stop joking");
		stopJoke.setFont(listFont);
		stopJoke.setForeground(listFontColor);
		stopJoke.addActionListener(this);
		JPanel stopPanel = new JPanel();
		stopPanel.setOpaque(false);
		stopPanel.add(stopJoke);

		jokePanel = new JPanel(new BorderLayout());
		jokePanel.setOpaque(false);
		jokePanel.add(jokePane, BorderLayout.CENTER);
		jokePanel.add(stopPanel, BorderLayout.SOUTH);

		add(jokePanel);
		validate();
	}

	/**
	 * Let the robot start the timer.
	 */
	private void timeToSleep() { // This only can run once.
		Timer timeToSleep = new Timer();
		timeToSleep.schedule(new sleepTask(), 0, 1000); // Let Robot will run the sleepTask every second.
	}

	/**
	 * This method is an override method which override the method in ActionListener Interface.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		robotInterface.setTime(0);
		if (e.getSource().equals(leave)) leave();
		if (e.getSource().equals(backToSort)) backToSort(false);
		if (e.getSource().equals(pay)) pay(false);
		if (e.getSource().equals(selectTextField)) {
			switch (selectTextField.getText()) {
				case "1":
				case "2":
				case "3":
				case "4":
				case "5":
				case "6":
					int selectNumber = Integer.parseInt(selectTextField.getText());
					if (selectNumber <= listNumber) {
						clickButton(selectNumber - 1);
						break;
					}
				default:
					JLabel errorLabel = new JLabel("Input Error!~");
					errorLabel.setFont(listFont);
					JOptionPane.showMessageDialog(null, errorLabel, "Whoops!", JOptionPane.ERROR_MESSAGE);
					robotInterface.setTime(0);
			}
		}
		if (e.getSource().equals(button[0])) clickButton(0);
		if (e.getSource().equals(button[1])) clickButton(1);
		if (e.getSource().equals(button[2])) clickButton(2);
		if (e.getSource().equals(button[3])) clickButton(3);
		if (e.getSource().equals(button[4])) clickButton(4);
		if (e.getSource().equals(button[5])) clickButton(5);
		if (e.getSource().equals(stopJoke)) backToSort(true);
	}

	private class listButton extends JButton {
		// Constructor.
		listButton(Color buttonColor) {
			super();
			setBackground(buttonColor);
		}

		// Another constructor.
		listButton(Color buttonColor, String s) {
			super("<html><center>" + s + "</center></html>");
			setBackground(buttonColor);
			setFont(listFont);
			setForeground(listFontColor);
		}
	}

	private class sleepTask extends TimerTask {
		@Override
		public void run() {
			System.out.println(robotInterface.getTime());
			robotInterface.setTime(robotInterface.getTime() + 1); // Make time plus 1.
			if (robotInterface.getTime() == 30) { // If time past 30s, robot will sleep.
				setVisible(false);

				JLabel sleepLabel = new JLabel("I'm sleeping...", JLabel.CENTER);
				sleepLabel.setFont(helloFont);
				sleepLabel.setForeground(helloFontColor);

				JOptionPane.showMessageDialog(null, sleepLabel, "Sleeping...", JOptionPane.INFORMATION_MESSAGE);
				robotInterface.setTime(0);

				setVisible(true);
			}
		}
	}
}
