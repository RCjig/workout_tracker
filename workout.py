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
