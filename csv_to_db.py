import csv
import sqlite3

# create database file
conn = sqlite3.connect('exercise.db')
curs = conn.cursor()

# exercises table
curs.execute("CREATE TABLE exercises(id, equip_req, ex_name, ex_type, muscle);")

# import data from csv into db
with open('data.csv', 'rb') as open_csv:
	dict_csv = csv.DictReader(open_csv, delimiter = '\t')
	to_db = [(i['id'], i['equip_req'], i['ex_name'], i['ex_type'], i['muscle']) for i in dict_csv]

curs.executemany("INSERT INTO exercises (id, equip_req, ex_name, ex_type, muscle) VALUES (?, ?, ?, ?, ?);", to_db)

# create tables for user use
curs.execute("CREATE TABLE user_database(user_id INTEGER, ex_id INTEGER, weight INTEGER, sets INTEGER, reps_per_set TEXT);")
curs.execute("CREATE TABLE user_list(user_id INTEGER, username TEXT);")

conn.commit()
conn.close()
