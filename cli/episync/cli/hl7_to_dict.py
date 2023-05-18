"""Converting HL7 messages to dictionaries
# http://www.prschmid.com/2016/11/converting-adt-hl7-message-to-json.html

Example Usage:
    import pprint
    from hl7apy.parser import parse_message
    # Taken from http://hl7apy.org/tutorial/index.html#elements-manipulation
    s = "MSH|^~\&|GHH_ADT||||20080115153000||ADT^A01^ADT_A01|0123456789|P|2.5||||AL ... "
    pprint.pprint(hl7_str_to_dict(s))

"""

import codecs
from sys import stdin
from io import TextIOWrapper, BufferedReader, BytesIO
from pprint import pprint
from hl7apy.parser import parse_message
from hl7apy.exceptions import ParserError


def hl7_str_to_dict(s, use_long_name=True):
    """Convert an HL7 string to a dictionary
    :param s: The input HL7 string
    :param use_long_name: Whether or not to user the long names
                          (e.g. "patient_name" instead of "pid_5")
    :returns: A dictionary representation of the HL7 message
    """
    #s = s.replace("\n", "\r")
    try:
        m = parse_message(s)
        return hl7_message_to_dict(m, use_long_name=use_long_name)
    except ParserError:
        return dict() 


def hl7_message_to_dict(m, use_long_name=True):
    """Convert an HL7 message to a dictionary
    :param m: The HL7 message as returned by :func:`hl7apy.parser.parse_message`
    :param use_long_name: Whether or not to user the long names
                          (e.g. "patient_name" instead of "pid_5")
    :returns: A dictionary representation of the HL7 message
    """
    if m.children:
        d = {}
        for c in m.children:
            name = str(c.name).lower()
            if use_long_name:
                name = str(c.long_name).lower() if c.long_name else name
            dictified = hl7_message_to_dict(c, use_long_name=use_long_name)
            if name in d:
                if not isinstance(d[name], list):
                    d[name] = [d[name]]
                d[name].append(dictified)
            else:
                d[name] = dictified
        return d
    else:
        return m.to_er7() 
