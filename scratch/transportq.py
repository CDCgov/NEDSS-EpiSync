import csv
from hl7_to_dict import hl7_str_to_dict
from jsonflat import JsonFlat

with open('hl72.txt', 'r') as file:
    hl7_msg = file.read()
    hl7_dict = hl7_str_to_dict(hl7_msg, use_long_name=True)

    flat_hl7 = JsonFlat().flatten([hl7_dict])

    with open('hl7.csv', 'w', encoding='UTF8') as f:
        writer = csv.writer(f)

        writer.writerow(flat_hl7['field_names'])

        rows = flat_hl7['rows']

        for row in rows:
            writer.writerow(row.values())

    with open('hl7.csv', 'r') as hl7csv:
        print(hl7csv.read())
