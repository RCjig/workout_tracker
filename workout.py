# TODO
# create main list
# export list into a csv
# to be continued in java

import requests
from bs4 import BeautifulSoup

# function to convert bs4 objects to strings
def bs4_to_str(bs4_object):
	txt = unicode(bs4_object.find(text=True)).encode('ascii', 'ignore')
	return txt

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
	if (bs4_to_str(li) != "> All"):
		body_parts.append(bs4_to_str(li))

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
	num_pages = bs4_to_str(hold_num_pgs)[10:]

	# loop through each page for current body part
	for current_page in range(1, int(num_pages) + 1):
		
		current_url = "https://www.jefit.com/exercises/bodypart.php?id=" + str(id) + "&exercises=" + body_part + """&page=
			0&All=0&Bands=0&Bench=0&Dumbbell=0&EZBar=0&Kettlebell=0&MachineStrength
			=0&MachineCardio=0&Barbell=0&BodyOnly=0&ExerciseBall=0&FoamRoll=0&PullB
			ar=0&WeightPlate=0&Other=0&Strength=0&Stretching=0&Powerlifting=0&Olymp
			icWeightLifting=0&Beginner=0&Intermediate=0&Expert=0&page=""" + str(current_page)

		page = requests.get(current_url)
		soup = BeautifulSoup(page.content, 'html.parser')

	
		# add exercise names and equipment required and exercise type to lists
		exercise_names_container = soup.findAll('h3')
		exercise_name_list = []

		for h3 in exercise_names_container:
			exercise_name_list.append(bs4_to_str(h3))

		info_container = soup.findAll('p')
		equipment_list = []
		type_list = []

		for p in info_container:
			info = bs4_to_str(p)
			if "Equipment : " in info:
				equipment_list.append(info[12:-1])
			elif "Type : " in info:
				type_list.append(info[7:-1])


		for i in range(len(exercise_name_list)):
			print exercise_name_list[i]
			#print body_part
			print equipment_list[i]
			print type_list[i]

	id += 1
