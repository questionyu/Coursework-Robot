/*
 * Created by Question on 3/6/2016
 * Copyright (c) 2017. All Rights Reserved.
 */

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Title        RobotInterface.java
 * Description  This class contains the Robot Interface's definition.
 */

class RobotInterface {

	// Instance variables.
	private float pay = 0f;
	private String dishList = "";
	private int time = 0;

	/**
	 * This method stores the file path of welcome messages.
	 *
	 * @return randomString("Files/WelcomeMessages.txt") Give the file path to randomString method.
	 */
	@SuppressWarnings("JavaDoc")
	String getWelcome() {
		return randomString("Files/WelcomeMessages.txt");
	}

	/**
	 * This method stores the file path of joke messages.
	 *
	 * @return randomString("Files/Jokes.txt") Give the file path to randomString method.
	 */
	@SuppressWarnings("JavaDoc")
	String getJoke() {
		return randomString("Files/Jokes.txt");
	}

	/**
	 * This method stores the file path of farewell messages.
	 *
	 * @return randomString("Files/Farewells.txt") Give the file path to randomString method.
	 */
	@SuppressWarnings("JavaDoc")
	String getFarewell() {
		return randomString("Files/Farewells.txt");
	}

	/**
	 * This method get the ArrayList from readString method and return an random string from it.
	 *
	 * @param filePath The file path of you want get random string from.
	 * @return tempMessages.get(i) A random string.
	 */
	private String randomString(String filePath) {
		ArrayList<String> tempMessages = readString(filePath);
		int i = (int) (Math.random() * tempMessages.size());
		return tempMessages.get(i);
	}

	/**
	 * This method get the file's content and save them to an ArrayList.
	 * Each line will be stored in a string.
	 *
	 * @param filePath The file path of you want get content from.
	 * @return messagesList An ArrayList which contains the file's content.
	 */
	private ArrayList<String> readString(String filePath) {
		ArrayList<String> messagesList = new ArrayList<>();
		try {
			FileInputStream fileStream = new FileInputStream(filePath);
			Scanner fileScanner = new Scanner(fileStream);

			while (fileScanner.hasNextLine()) messagesList.add(fileScanner.nextLine());

			fileScanner.close();
			fileStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return messagesList;
	}

	/**
	 * This method will make ArrayList only store six strings.
	 *
	 * @param filePath The file path of you want get content from.
	 * @return limitedList An ArrayList which only contains six strings.
	 */
	private ArrayList<String> limitedString(String filePath) {
		ArrayList<String> tempList = readString(filePath);
		int itemNumber = tempList.size();
		if (itemNumber > 6) itemNumber = 6;
		ArrayList<String> limitedList = new ArrayList<>();
		for (int i = 0; i < itemNumber; i++) limitedList.add(tempList.get(i));
		return limitedList;
	}

	/**
	 * This method split strings which contains ",".
	 *
	 * @param filePath The file path of you want get content from.
	 * @return dishInformation A two-dimensional array which contains dishes' information.
	 */
	private String[][] splitString(String filePath) {
		ArrayList<String> tempList = limitedString(filePath);
		int itemNumber = tempList.size();
		String[][] dishInformation = new String[itemNumber][3];
		for (int i = 0; i < itemNumber; i++) {
			Scanner stringScanner = new Scanner(tempList.get(i));
			stringScanner.useDelimiter(",");
			for (int j = 0; j < 3; j++) dishInformation[i][j] = stringScanner.next();
		}
		return dishInformation;
	}

	/**
	 * This method stores the file path of sort messages and store these messages in a two-dimensional array.
	 *
	 * @return sortString A two-dimensional array which contains sort messages.
	 */
	String[][] getSort() {
		ArrayList<String> sortList = limitedString("Files/Sorts.txt");
		String[][] sortString = new String[1][sortList.size()];
		for (int i = 0; i < sortList.size(); i++) sortString[0][i] = sortList.get(i);
		return sortString;
	}

	/**
	 * This method get the file path from getFilePath method and give to splitString method.
	 *
	 * @return sortString A two-dimensional array which contains fish's information.
	 */
	String[][] getFish() {
		return splitString(getFilePath(0));
	}

	/**
	 * This method get the file path from getFilePath method and give to splitString method.
	 *
	 * @return sortString A two-dimensional array which contains meat's information.
	 */
	String[][] getMeat() {
		return splitString(getFilePath(1));
	}

	/**
	 * This method get the file path from getFilePath method and give to splitString method.
	 *
	 * @return sortString A two-dimensional array which contains rice's information.
	 */
	String[][] getRice() {
		return splitString(getFilePath(2));
	}

	/**
	 * This method get the file path from getFilePath method and give to splitString method.
	 *
	 * @return sortString A two-dimensional array which contains noodle's information.
	 */
	String[][] getNoodle() {
		return splitString(getFilePath(3));
	}

	/**
	 * This method get the file path from getFilePath method and give to splitString method.
	 *
	 * @return sortString A two-dimensional array which contains drink's information.
	 */
	String[][] getDrink() {
		return splitString(getFilePath(4));
	}

	/**
	 * This method add price to total price.
	 *
	 * @param price The dish's price.
	 */
	void addPay(String price) {
		pay += Float.parseFloat(price);
	}

	/**
	 * A getter method.
	 *
	 * @return pay Total price.
	 */
	float getPay() {
		return pay;
	}

	/**
	 * This method add dish to dish list.
	 *
	 * @param add The user's choice.
	 */
	void addDishList(String add) {
		dishList += (add + "<br />");
	}

	/**
	 * A getter method.
	 *
	 * @return dishList User's dishes' list.
	 */
	String getDishList() {
		return dishList;
	}

	/**
	 * A getter method that can return different file path.
	 *
	 * @param select Different number can get different file path.
	 * @return String Different file path.
	 */
	private String getFilePath(int select) {
		switch (select) {
			case 0:
				return "Files/1.FishDishes.txt";
			case 1:
				return "Files/2.MeatDishes.txt";
			case 2:
				return "Files/3.RiceDishes.txt";
			case 3:
				return "Files/4.NoodleDishes.txt";
			case 4:
				return "Files/5.DrinkDishes.txt";
			default:
				return null;
		}
	}

	/**
	 * This method will clear the file's content and write dish information into file.
	 *
	 * @param sort            Tell the method which file to write.
	 * @param dishInformation A two-dimensional array which contains dishes' information.
	 */
	void writeString(int sort, String[][] dishInformation) {
		String filePath = getFilePath(sort);
		try {
			assert filePath != null;
			FileWriter fileWriter = new FileWriter(filePath);

			for (String[] aDishInformation : dishInformation)
				fileWriter.write(aDishInformation[0] + "," + aDishInformation[1] + "," + aDishInformation[2] + "\r\n");

			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * A getter method.
	 *
	 * @return time Return the current time.
	 */
	int getTime() {
		return time;
	}

	/**
	 * A setter method.
	 *
	 * @param setTime Change time to setTime.
	 */
	void setTime(int setTime) {
		time = setTime;
	}
}
