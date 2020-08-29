import urllib3
import json
import sys

host = sys.argv[1] + '.herokuapp.com' if len(sys.argv) > 1 else 'localhost:8080'
http = urllib3.PoolManager()
response = http.request('GET', 'http://' + host + '/planet')
if response.status != 200:
    sys.exit(1)
if response.headers['Content-Type'] != 'application/json;charset=UTF-8':
    sys.exit(2)
greeting = json.loads(response.data.decode('utf-8'))
if greeting['name'] != 'Tatooine':
    sys.exit(3)
if greeting['diameter'] != 10465:
    sys.exit(4)
