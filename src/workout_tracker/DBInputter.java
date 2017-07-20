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
		
		System.out.println("New User? [y/n]");
		if (input.hasNext() && input.nextLine().equalsIgnoreCase("y")) {
			System.out.println("Enter your name");
			name = input.nextLine();
			try {
				Statement newUser = conn.createStatement();
				ResultSet userNum = newUser.executeQuery(
					"SELECT count(*) AS num FROM user_list;");
				userNumber = userNum.getInt("num");
				newUser.executeUpdate("INSERT INTO user_list " + "VALUES (" 
					+ ++userNumber + ", '" + name + "')");
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		else {
			System.out.println("Enter your name");
			name = input.nextLine();
			try {
				Statement retUser = conn.createStatement();
				ResultSet userNum = retUser.executeQuery(
					"SELECT user_id AS num FROM user_list WHERE username =='"
					+ name + "';");
				System.out.print(userNum.getInt("num"));
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		/*System.out.println("What would you like to do today?");
		System.out.println("Build a workout [1] or update workout log [2]");
		int option = input.nextInt();
		if (option == 1) {
			System.out.println("Choose muscle(s) to workout");
			System.out.println("Abs [1]. Back [2], Biceps [3], Chest [4], "
					+ "Forearm [5], Glutes [6], Shoulders [7], Triceps [8], "
					+ "Upper Legs [9], Lower Legs [10], or Cardio [11]");
		} else {
			System.out.println("Please input excercise name");
		}
		*/
		input.close();
	}
}
