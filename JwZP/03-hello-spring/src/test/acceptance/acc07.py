import urllib3
import json
import sys

host = sys.argv[1] + '.herokuapp.com' if len(sys.argv) > 1 else 'localhost:8080'
http = urllib3.PoolManager()
response = http.request('GET', 'http://' + host + '/person?id=10')
if response.status != 200:
    sys.exit(1)
if response.headers['Content-Type'] != 'application/json;charset=UTF-8':
    sys.exit(2)
person = json.loads(response.data.decode('utf-8'))
if person['name'] != 'Obi-Wan Kenobi':
    sys.exit(3)
if person['hair_color'] != 'auburn, white':
    sys.exit(4)

planet = person['planet']
if planet['name'] != 'Stewjon':
    sys.exit(5)
if planet['terrain'] != 'grass':
    sys.exit(6)
