# workout_tracker
Python web scaper/crawler to get exercises and Java program to convert into recommended workout. 
Eventual progression into an android app.

First step is to use python and BeautifulSoup4 import to scrape exercise database from Jefit.com. 
After all exercise data has been put into lists, all data is turned into a .csv file. From there, 
another python file converts the csv file into an sqlite database using the sqlite3 import.

TODO:
Use Java as interface and connect to sql database for user interaction.
