package workout_tracker;
import java.sql.*;
import java.util.Scanner;

public class DBInputter {

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
					+ "Upper Legs [i], Lower Legs [j], or Cardio [k]");
			
			//String options = input.nextLine();
			//boolean a, b, c, d, e, f, g, h, i, j, k;
			
		} else {
			// update personal log in database
			System.out.println("Please input excercise name");
		}
		
		input.close();
	}
}
