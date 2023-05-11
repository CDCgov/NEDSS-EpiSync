import json
import csv
from hl7_to_dict import hl7_str_to_dict
from jsonflat import JsonFlat

with open('hl7.txt', 'r') as file:
    hl7_msg = file.read()
    hl7_dict = hl7_str_to_dict(hl7_msg, use_long_name=True)

    #print(json.dumps(hl7_dict, indent=4))

    flat_hl7 = JsonFlat().flatten([hl7_dict])
    #print(flat_hl7)

    with open('hl7.csv', 'w', encoding='UTF8') as f:
        writer = csv.writer(f)

        writer.writerow(flat_hl7['field_names'])

        writer.writerow(flat_hl7['rows'])

    with open('hl7.csv', 'r') as hl7csv:
        print(hl7csv.read())