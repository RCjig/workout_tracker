# workout_tracker
Python web scaper/crawler to get exercises and Java program to convert into recommended workout. 
Eventual progression into an android app.

First step is to use python and BeautifulSoup4 import to scrape exercise database from Jefit.com. 
After all exercise data has been put into lists, all data is turned into a .csv file. From there, 
another python file converts the csv file into an sqlite database using the sqlite3 import.

Using Java as an interface, users are able to do one of two main options. The first option is a
workout builder. This gets user input and suggests a workout comprised of exercises focused on
selected muscle groups. The second option allows users to enter in or updatepersonal exercise 
data such as weight, sets, and reps performed for a specific exercise into another table in the 
database.

The Java program is currently a command line app that is mainly used as a user-friendly
interface in order to interact with the SQLite database.

How to Use:
1. Run workout.py
2. Update header for exercise_database.csv file to "id'\t'equip_req'\t'ex_bname'\t'ex_type'\t'muscle'
3. Rename exercise_database.csv to data.csv
4. Run csv_to_db.py
5. Run DBInputter.java

TODO:
1. Streamline entire process (so that HOw to Use is lessened in steps)
2. Polish code (more efficient, better UI)
3a. Turn into desktop app
or 3b. Turn into Android app
