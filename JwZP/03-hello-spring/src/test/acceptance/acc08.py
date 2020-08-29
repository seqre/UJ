import urllib3
import json
import sys

host = sys.argv[1] + '.herokuapp.com' if len(sys.argv) > 1 else 'localhost:8080'
http = urllib3.PoolManager()
response = http.request('GET', 'http://' + host + '/person/eye?fromId=2&toId=10&color=yellow')

if response.status != 200:
    sys.exit(1)
if response.headers['Content-Type'] != 'application/json;charset=UTF-8':
    sys.exit(2)
persons = json.loads(response.data.decode('utf-8'))
if len(persons) != 2:
    sys.exit(3)

p1 = persons[0]
p2 = persons[1]

if p1['name'] != 'C-3PO' and p2['name'] != 'C-3PO':
    sys.exit(4)
if p1['name'] != 'Darth Vader' and p2['name'] != 'Darth Vader':
    sys.exit(5)
if p1['eye_color'] != 'yellow' or p2['eye_color'] != 'yellow':
    sys.exit(5)
if p1['planet']['climate'] != 'arid':
    sys.exit(6)
