import csv
import sqlite3

conn = sqlite3.connect('exercise.db')
curs = conn.cursor()
curs.execute("CREATE TABLE exercises (id, equip_req, ex_name, ex_type, muscle);")

with open('data.csv', 'rb') as open_csv:
	dict_csv = csv.DictReader(open_csv, delimiter = '\t')
	to_db = [(i['id'], i['equip_req'], i['ex_name'], i['ex_type'], i['muscle']) for i in dict_csv]

curs.executemany("INSERT INTO exercises (id, equip_req, ex_name, ex_type, muscle) VALUES (?, ?, ?, ?, ?);", to_db)

conn.commit()
conn.close()
