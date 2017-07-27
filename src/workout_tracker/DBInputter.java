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

	public static void workoutMaker(Connection conn, boolean[] mOps, 
		int[] mCounts, boolean[] eOps, boolean[] tOps) {
		
		try {
			Statement results = conn.createStatement();
		
			String query;
			String equip = " AND (equip_req = ";
			String type = " AND (ex_type = ";
			boolean first = true;
			boolean eOpsBool = false;
			boolean tOpsBool = false;
			
			// set string for equipment select
			for (int i = 0; i < eOps.length; i++) {
				if (eOps[i]) {
					eOpsBool = true;
					if (first) {
						first = false;
					} else {
						equip += " OR equip_req = ";
					}
					eOpsBool = true;
					equip += "'" + EQUIPMENT[i] + "'";
				}
			}
			equip += ")";
			
			first = true;
			
			// set string for type select
			for (int i = 0; i < tOps.length; i++) {
				if (tOps[i]) {
					tOpsBool = true;
					if (first) {
						first = false;
					} else {
						type += " OR ex_type = ";
					}
					tOpsBool = true;
					type += "'" + TYPES[i] + "'";
				}
			}
			type += ")";
		
			for (int i = 0; i < mOps.length; i++) {
				if (mOps[i]) {
				
					// choose correct query string
					if (!eOpsBool && !tOpsBool) {
						query = "Please try again with correct options";
					} 
					
					else if (eOpsBool && !tOpsBool) {
						query = "SELECT ex_name FROM exercises WHERE"
								+ " muscle = '" + MUSCLES[i] + "'"
								+ equip + " ORDER BY RANDOM()"
								+ " LIMIT " + mCounts[i] + ";";
					} 
					
					else {
						query = "SELECT ex_name FROM exercises WHERE"
								+ " muscle = '" + MUSCLES[i] + "'"
								+ equip + type + " ORDER BY RANDOM()"
								+ " LIMIT " + mCounts[i] + ";";
					}
						
					ResultSet currRes = results.executeQuery(query);
					
					// print out results
					ResultSetMetaData crMD = currRes.getMetaData();
					int colNum = crMD.getColumnCount();
					while (currRes.next()) {
						for (int j = 1; j <= colNum; j++) {
							String colVal = currRes.getString(j);
							System.out.print(colVal);
						}
						System.out.println();
					}
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
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
		int userNumber = 0;
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
		String option = input.nextLine();
		
		// build a workout / recommender
		if (option.indexOf('1') >= 0) {
			System.out.println("Choose muscle(s) to workout:");
			System.out.println("Abs [a]. Back [b], Biceps [c], Chest [d], "
					+ "Forearm [e], Glutes [f], Shoulders [g], Triceps [h], "
					+ "Upper Legs [i], Lower Legs [j], and/or Cardio [k]");
			
			//TODO add user input syntax forcing
			
			// muscle option array setting
			boolean[] muscleOptions = new boolean[11];
			char optionCheck = 'a';
			
			String mOInput = input.nextLine();
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
			input.nextLine();
			
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
			
			workoutMaker(conn, muscleOptions, exerciseCounts, equipmentOptions,
				typeOptions);
			
		} else {
			// update personal log database
			try {
				while (true) {
					// get user input for data
					System.out.println("Please input excercise name");
					String exName = input.nextLine();
					
					System.out.println("New Exercise? [y/n]");
					String newOp = input.nextLine();
					
					System.out.println("Weight?");
					int weight = input.nextInt();
					
					System.out.println("Sets?");
					int sets = input.nextInt();
					input.nextLine();
					
					System.out.println("Reps? (ex: 8 8 8 8)");
					String reps = input.nextLine();
					
					// obtain exercise id
					// TODO exercise legitness check
					int exID = -1;
					Statement exIDStat = conn.createStatement();
					ResultSet exIDrs = exIDStat.executeQuery("SELECT id FROM exercises WHERE"
						+ " ex_name = '" + exName + "';");
					while (exIDrs.next()) {
						exID = exIDrs.getInt("id");
					}
					
					// new exercise entry
					if (newOp.indexOf('y') >= 0) {
						String insert = "INSERT INTO user_database(user_id, ex_id,"
							+ " weight, sets, reps_per_set) VALUES(?, ?, ?, ?, ?)";
						PreparedStatement insertEx = conn.prepareStatement(insert);
						insertEx.setDouble(1, userNumber);
						insertEx.setDouble(2, exID);
						insertEx.setDouble(3, weight);
						insertEx.setDouble(4, sets);
						insertEx.setString(5, reps);
						insertEx.executeUpdate();
					} 
					
					// old exercise update
					else {
						String update = "UPDATE user_database SET weight = ?,"
							+ " sets = ?, reps_per_set = ? WHERE user_id = ?"
							+ " AND ex_id = ?";
						PreparedStatement updateEx = conn.prepareStatement(update);
						updateEx.setDouble(1, weight);
						updateEx.setDouble(2, sets);
						updateEx.setString(3, reps);
						updateEx.setDouble(4, userNumber);
						updateEx.setDouble(5, exID);
						updateEx.executeUpdate();
					}
					
					// check if done
					System.out.println("Update more or done? [u/d]");
					String done = input.nextLine();
					if (done.indexOf('d') >= 0) {
						break;
					}
				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
			
		}
		
		input.close();
	}
}
