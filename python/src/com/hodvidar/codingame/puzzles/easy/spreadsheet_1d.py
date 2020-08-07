import sys
import math

# By Hodvidar
# https://www.codingame.com/ide/puzzle/1d-spreadsheet
VALUE = "VALUE"
ADD = "ADD"
SUB = "SUB"
MULT = "MULT"
EMPTY = "EMPTY"
DOLLAR = "$"
SEPARATOR = "#"
UNDERSCORE = "_"


def is_reference(content):
    return DOLLAR in str(content)


def is_null(arg):
    return UNDERSCORE == arg


def cast_arg(arg):
    if is_null(arg):
        return 0
    if is_reference(arg):
        return arg
    return int(arg)


def extract_reference_value(arg):
    return int(arg[1:])


def handle_reference_value(list, arg_i, i):
    #print("handle_reference_value arg_i="+str(arg_i)+" i="+str(i), file=sys.stderr, flush=True)
    reference_value = arg_i
    operation, arg_1, arg_2 = reference_value.split(SEPARATOR)
    old_arg_1 = arg_1
    old_arg_2 = arg_2
    if is_reference(arg_1):
        arg_1 = extract_reference_value(arg_1)
        if is_reference(list[arg_1]):
            arg_1 = handle_reference_value(list, list[arg_1], arg_1)
        else:
            arg_1 = list[arg_1]

    if old_arg_1 == old_arg_2:
        return get_value_from_args(operation, arg_1, arg_1)

    if is_reference(arg_2):
        arg_2 = extract_reference_value(arg_2)
        if is_reference(list[arg_2]):
            arg_2 = handle_reference_value(list, list[arg_2], arg_2)
        else:
            arg_2 = list[arg_2]

    return get_value_from_args(operation, arg_1, arg_2)


def get_value_from_args(ope, arg_1, arg_2):
    if is_reference(arg_1) or is_reference(arg_2):
        return operation+SEPARATOR+str(arg_1)+SEPARATOR+str(arg_2)
    arg_1 = cast_arg(arg_1)
    arg_2 = cast_arg(arg_2)
    if VALUE == ope:
        return arg_1
    if ADD == ope:
        return arg_1 + arg_2
    if SUB == ope:
        return arg_1 - arg_2
    if MULT == ope:
        return arg_1 * arg_2
    raise ValueError("Unknown operation")


print("Debug messages, let's start...", file=sys.stderr, flush=True)
n = int(input())
myList = [None] * n

for i in range(n):
    operation, arg_1, arg_2 = input().split()
    arg_1 = cast_arg(arg_1)
    arg_2 = cast_arg(arg_2)
    arg_1_str = str(arg_1)
    arg_2_str = str(arg_2)
    #print("i="+str(i)+" ope="+operation+" arg_1="+arg_1_str+" arg_2="+arg_2_str, file=sys.stderr, flush=True)
    myList[i] = get_value_from_args(operation, arg_1, arg_2)
    #print("myList="+str(myList), file=sys.stderr, flush=True)

for i in range(n):
    if is_reference(myList[i]):
        myList[i] = handle_reference_value(myList, myList[i], i)

for i in range(n):
    #print("Debug messages...", file=sys.stderr, flush=True)
    # Write an answer using print
    # To debug: print("Debug messages...", file=sys.stderr, flush=True)

    print(myList[i])
