package workout_tracker;
import java.sql.*;
import java.util.Scanner;

public class DBInputter {
	
	public static final String[] MUSCLES = {"Abs", "Back", "Biceps", "Chest",
		"Forearm", "Glutes", "Shoulders", "Triceprs", "Upper Legs",
		"Lower Legs", "Cardio"
	};
	
	public static final String[] EQUIPMENT = {"Bands", "Barbell",
		"Bench", "Body Only", "Dumbbell", "Exercise Ball", "EZ - Bar",
		"Foam Roll", "Kettlebell", "Machine - Cardio", "Machine - Strength",
		"Other", "Pull Bar", "Weight Plate"
	};
	
	public static final String[] TYPES = {"Strength", "Stretching", "Powerlifting",
		"Olympic Weight Lifting"
	};

	public static void main(String[] args) {
		
		// set up connect
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:exercise.db";
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		// set up scanner and username
		String name;
		int userNumber;
		Scanner input = new Scanner(System.in);
		
		// new user
		System.out.println("New User? [y/n]");
		if (input.hasNext() && input.nextLine().equalsIgnoreCase("y")) {
			
			// get name and insert into table in database
			System.out.println("Enter your name");
			name = input.nextLine();
			
			// TODO add duplicate user prevention
			try {
				Statement newUser = conn.createStatement();
				ResultSet userNum = newUser.executeQuery(
					"SELECT count(*) AS num FROM user_list;");
				userNumber = userNum.getInt("num");
				newUser.executeUpdate("INSERT INTO user_list " + "VALUES (" 
					+ ++userNumber + ", '" + name + "')");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		else {
			// returning user
			System.out.println("Enter your name");
			name = input.nextLine();
			
			// obtain user id number
			try {
				Statement retUser = conn.createStatement();
				ResultSet userNum = retUser.executeQuery(
					"SELECT user_id AS num FROM user_list WHERE username =='"
					+ name + "';");
				userNumber = userNum.getInt("num");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// program options
		System.out.println("What would you like to do today?");
		System.out.println("Build a workout [1] or update workout log [2]");
		int option = input.nextInt();
		
		// build a workout / recommender
		if (option == 1) {
			System.out.println("Choose muscle(s) to workout:");
			System.out.println("Abs [a]. Back [b], Biceps [c], Chest [d], "
					+ "Forearm [e], Glutes [f], Shoulders [g], Triceps [h], "
					+ "Upper Legs [i], Lower Legs [j], and/or Cardio [k]");
			
			//TODO add user input syntax forcing
			
			// muscle option array setting
			boolean[] muscleOptions = new boolean[11];
			String mOInput = input.nextLine();
			char optionCheck = 'a';
			
			for (int i = 0; i < 11; i++) {
				if (mOInput.indexOf(optionCheck++) >= 0) {
					muscleOptions[i] = true;
				}
			}
			
			// obtain number of exercises for each muscle
			System.out.println("How many different exercises would you like for"
					+ " each exercise?");
			
			int[] exerciseCounts = new int[11];
			for (int i = 0; i < 11; i++) {
				if (muscleOptions[i]) {
					System.out.println(MUSCLES[i] + "?");
					exerciseCounts[i] = input.nextInt();
				}
			}
			
			System.out.println("What equipment is available?");
			System.out.println("All [a], Bands [b], barbell [c], bench [d], "
					+ "Body Only [e], Dumbbell [f], Exercise Ball [g], EZ - Bar [h], "
					+ "Foam Roll [i], Kettlebell [j], Machine - Cardio [k], "
					+ "Machine - Strength [l], Other [m], Pull Bar [n], "
					+ "and/or Weight Plate [o]");
			
			boolean[] equipmentOptions = new boolean[14];
			optionCheck = 'a';
			
			// option select for equipment
			String eOInput = input.nextLine();
			if (eOInput.indexOf(optionCheck++) >= 0) {
				for (int i = 0; i < 14; i++) {
					equipmentOptions[i] = true;
				}
			} else {
				for (int i = 0; i < 14; i++) {
					if (eOInput.indexOf(optionCheck++) >= 0) {
						equipmentOptions[i] = true;
					}
				}
			}
			
			System.out.println("What type of exercises?");
			System.out.println("Strength [a], Stretching [b], "
					+ "Powerlifting [c], and/or Olympic Weight Lifting [d]");
			
			boolean[] typeOptions = new boolean[4];
			optionCheck = 'a';
			
			// option select for type
			String tOInput = input.nextLine();
			for (int i = 0; i < 4; i++) {
				if (tOInput.indexOf(optionCheck++) >= 0) {
					typeOptions[i] = true;
				}
			}
			
		} else {
			// update personal log in database
			System.out.println("Please input excercise name");
		}
		
		input.close();
	}
}
