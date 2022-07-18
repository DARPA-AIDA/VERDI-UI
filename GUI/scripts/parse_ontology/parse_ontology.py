import json
import sys
from typing import List

import openpyxl


class ListSpec:
    def __init__(self, start:int, step:int, max:int):
        self.start = start
        self.step = step
        self.max = max

    def get_list(self, sheet, row) -> List:
        ret = []
        for i in range(0, self.max):
            value = val(sheet, chr(self.start + i * self.step), row)
            if bool(value) and value != 'n/a':
                ret.append(value)
            else:
                return ret
        return ret

type_spec = ListSpec(ord('B'), 2, 3)
arg_spec = ListSpec(ord('J'), 3, 5)

def val(sheet, col: str, row: int) -> str:
    value = sheet[col + str(row)].value
    return value if value is None else value.strip()

def add_sheet(sheet, data: dict):
    row = 2
    while val(sheet,'A', row) is not None:
        name = '.'.join(type_spec.get_list(sheet, row))
        data[name] = {
            'template': val(sheet, 'I', row), 
            'args': arg_spec.get_list(sheet, row)
        }
        row += 1

def get_new_event_strings(data: dict):
    ret = {}
    for v in data.values():
        template = v['template']
        if template not in ret:
            strings = template.replace('>', '').split()
            i = 0
            prefix = []
            parts = { 'order': [] }
            last_index = len(strings) - 1
            while i < len(strings):
                value = strings[i]
            
                if '<arg' not in value:
                    prefix.append(value)
                else:
                    id = value.split('<arg')[1][0]
                    part = {}
                    if id == '1':
                        part['substitution'] = 'Something'
                        part['suffix'] = ' ' 
                    else: 
                        if id == '2':
                            parts['order'].append(' '.join(prefix))
                            part['prefix'] = ' '
                            prefix = []
                        else:
                            if prefix:
                                part['prefix'] = ' ' + ' '.join(prefix) + ' '
                                prefix = []
                        if i + 2 < last_index and '<arg' not in strings[i + 2] or i + 1 == last_index:
                            part['suffix'] = ' ' + strings[i + 1]
                            i = i + 1
                    parts['order'].append(int(id))
                    parts[id] = part
                
                i = i + 1
            ret[template] = parts
    return ret


def main(xlsxfile, output_filename):
    # open input
    wb = openpyxl.load_workbook(xlsxfile)

    # process events and relations into single dictionary
    data = {}
    add_sheet(wb['events'], data)
    new_strings = get_new_event_strings(data)
    add_sheet(wb['relations'], data)
    # for key, value in data.items():
    #     print(key + ': ' + str(value))
    output = {
        'original': data,
        'augmented': new_strings
    }
    
    # output .json file with utility function to get string from type and map (role->name)
    with open(output_filename, 'w') as out:
        json.dump(output, out, indent=4)
    
if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2] if len(sys.argv) > 2 else 'templates.json')
