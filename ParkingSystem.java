package parking;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ParkingSystem {

	// Define the maximum capacity of the parking lot
	private static final int MAX_CAPACITY = 20;
	private static final int DEFAULT_SIZE = 0;

	// customize the file path where it should be created
	private static final String FILE_PATH = "config.properties";

	private static OutputStream writeFIleData;
	private static File file = new File(FILE_PATH);
	private static Properties prop = new Properties();
	private static BufferedReader br;
	private static List<Integer> parkingSize = new ArrayList<>();

	public static void main(String[] args) throws IOException {

		if (!file.exists()) {
			try {
				writeFIleData = new FileOutputStream(FILE_PATH);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		br = new BufferedReader(new FileReader(FILE_PATH));

		if (br.readLine() == null) {
			prop.setProperty("capacity", String.valueOf(MAX_CAPACITY));
			prop.setProperty("size", String.valueOf(DEFAULT_SIZE));
			prop.store(writeFIleData, null);
			writeFIleData.close();
		}

		// Create a scanner object to read user input
		Scanner scanner = new Scanner(System.in);

		while (true) {
			// Display the parking menu
			System.out.println("\n\nCar Parking System Menu:");
			System.out.println("1. Park a car");
			System.out.println("2. Remove a car");
			System.out.println("3. View parking lot status");
			System.out.println("4. Exit");

			// Get the user's choice
			System.out.print("\nEnter your choice (1-4): ");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				// Park a car
				collectCar();
				break;
			case 2:
				// Remove a car
				removeCar();
				break;
			case 3:
				// View parking lot status
				viewParkingLotStatus();
				break;
			case 4:
				// Exit the program
				parkCar();
				System.out.println("\nThank you...");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("\nInvalid choice! Please try again.");
			}
		}
	}

	private static void collectCar() {
		try {

			int initialCar = 1;
			int carToBeParked = 0;

			InputStream readFileData = readFileData();
			prop.load(readFileData);
			readFileData.close();

			int alreadyFilledCars = Integer.parseInt(prop.getProperty("size"));
			if (alreadyFilledCars >= MAX_CAPACITY) {
				// No parking spots are available
				System.out.println("\nSorry, the parking lot is full.");
				return;
			}

			System.out.println("afc : " + alreadyFilledCars);

			if (alreadyFilledCars == 0 && parkingSize.size() == 0) {
				parkingSize.add(initialCar);
				System.out.println("\nCar parked at slot " + parkingSize.get(0));
			} else {
				if (parkingSize.get(0) != null) {
					if (alreadyFilledCars != 0) {

						carToBeParked = alreadyFilledCars + 1;
					} else {
						carToBeParked = parkingSize.get(0) + 1;
					}
					System.out.println("ctbp : " + carToBeParked);
					parkingSize.set(0, carToBeParked);
					System.out.println("\nCar parked at slot " + parkingSize.get(0));
				}
			}

			// System.out.println("117 : " + parkingSize.get(0));
//			carToBeParked = alreadyFilledCars + 1;

//			writeFIleData = new FileOutputStream(FILE_PATH);
//			prop.setProperty("size", String.valueOf(carToBeParked));
//			prop.store(writeFIleData, null);
//			System.out.println("\nCar parked at slot " + carToBeParked);
//			writeFIleData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void parkCar() {
		try {
			InputStream readFileData = readFileData();
			prop.load(readFileData);
			readFileData.close();

			if (parkingSize.get(0) == null || parkingSize.get(0) == 0) {
				return;
			}

			writeFIleData = new FileOutputStream(FILE_PATH);
			prop.setProperty("size", String.valueOf(parkingSize.get(0)));
			prop.store(writeFIleData, null);
			writeFIleData.close();

		} catch (IOException e) {

		}
	}

	private static void removeCar() {
		try {
			InputStream readFileData = readFileData();
			prop.load(readFileData);
			readFileData.close();

			int filledCarsPosition = Integer.parseInt(prop.getProperty("size"));

			if (filledCarsPosition <= 0) {
				System.out.println("Parking slot is already empty");
				return;
			} else {
				int carPositionToBeOut = filledCarsPosition - 1;
				writeFIleData = new FileOutputStream(FILE_PATH);
				prop.setProperty("size", String.valueOf(carPositionToBeOut));
				prop.store(writeFIleData, null);
				System.out.println("\nCar removed from slot ");
				writeFIleData.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void viewParkingLotStatus() {
		System.out.println("\nParking lot status:");
		try {
			InputStream readFileData = readFileData();
			prop.load(readFileData);
			readFileData.close();

			int filledCarsPosition = Integer.parseInt(prop.getProperty("size"));
			int capacity = Integer.parseInt(prop.getProperty("capacity"));

			int avaliableSlots = capacity - filledCarsPosition;
			System.out.println("\nAvailable slots in parking : " + avaliableSlots);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static FileInputStream readFileData() throws FileNotFoundException {
		return new FileInputStream(FILE_PATH);
	}
}
