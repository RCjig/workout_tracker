# TODO
# define a function to convert bs4 strings to reg strings
# create main list
# export list into a csv
# to be continued in java

import requests
from bs4 import BeautifulSoup

# main url
url = """https://www.jefit.com/exercises/bodypart.php?id=11&exercises=All&page=
	0&All=0&Bands=0&Bench=0&Dumbbell=0&EZBar=0&Kettlebell=0&MachineStrength
	=0&MachineCardio=0&Barbell=0&BodyOnly=0&ExerciseBall=0&FoamRoll=0&PullB
	ar=0&WeightPlate=0&Other=0&Strength=0&Stretching=0&Powerlifting=0&Olymp
	icWeightLifting=0&Beginner=0&Intermediate=0&Expert=0"""

# set up bs4 object
page = requests.get(url)
soup = BeautifulSoup(page.content, 'html.parser')

# parse to body parts
outer_container = soup.find(class_="container content")
table = outer_container.find(class_="JefitMainTable")
inner_container = table.find('tr')
name_source = inner_container.find('ul')
name_lis = name_source.findAll('li')

# list for body part names
body_parts = []
for li in name_lis:
	body_part_name = li.find(text=True)
	if (body_part_name != "> All"):
		body_parts.append(unicode((body_part_name)).encode('ascii', 'ignore'))

# keeps track of url
id = 0

# loop through each body part page
for body_part in body_parts:

	# first page of current body part
	first_url = "https://www.jefit.com/exercises/bodypart.php?id=" + str(id) + "&exercises=" + body_part + """&page=
		0&All=0&Bands=0&Bench=0&Dumbbell=0&EZBar=0&Kettlebell=0&MachineStrength
		=0&MachineCardio=0&Barbell=0&BodyOnly=0&ExerciseBall=0&FoamRoll=0&PullB
		ar=0&WeightPlate=0&Other=0&Strength=0&Stretching=0&Powerlifting=0&Olymp
		icWeightLifting=0&Beginner=0&Intermediate=0&Expert=0"""


	page = requests.get(first_url)
	soup = BeautifulSoup(page.content, 'html.parser')

	hold_num_pgs = soup.find(class_="pageCell")
	num_pages = unicode((hold_num_pgs.find(text=True))).encode('ascii', 'ignore')[10:]

	# loop through each page for current body part
	for current_page in range(1, int(num_pages) + 1):
		
		current_url = "https://www.jefit.com/exercises/bodypart.php?id=" + str(id) + "&exercises=" + body_part + """&page=
			0&All=0&Bands=0&Bench=0&Dumbbell=0&EZBar=0&Kettlebell=0&MachineStrength
			=0&MachineCardio=0&Barbell=0&BodyOnly=0&ExerciseBall=0&FoamRoll=0&PullB
			ar=0&WeightPlate=0&Other=0&Strength=0&Stretching=0&Powerlifting=0&Olymp
			icWeightLifting=0&Beginner=0&Intermediate=0&Expert=0&page=""" + str(current_page)

		page = requests.get(current_url)
		soup = BeautifulSoup(page.content, 'html.parser')

	
		# add exercise names and equipment required to lists
		exercise_names_container = soup.findAll('h3')
		exercise_name_list = []

		for h3 in exercise_names_container:
			exercise_name_list.append(unicode(h3.find(text=True)).encode('ascii', 'ignore'))

		equipment_container = soup.findAll('p')
		equipment_list = []

		for p in equipment_container:
			if "Equipment : " in unicode(p.find(text=True)).encode('ascii', 'ignore'):
				equipment_list.append(unicode(p.find(text=True)).encode('ascii', 'ignore')[12:-1])

		for i in range(len(exercise_name_list)):
			print exercise_name_list[i]
			#print body_part
			#print equipment_list[i]

	id += 1
