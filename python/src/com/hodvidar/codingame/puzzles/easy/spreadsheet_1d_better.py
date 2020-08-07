import sys

# By LaurentBouvier
# https://www.codingame.com/ide/puzzle/1d-spreadsheet
n=int(input())
xcell=[input().split()for i in range(n)]
xval=["?" for i in range(n)]
print(xcell, file=sys.stderr)


def compute_arg(val):
    if val[0]=="$":
        return compute(int(val[1:]))
    else:
        return int(val)

def compute(i):
    if xval[i]!="?": return xval[i]
    op,a1,a2=xcell[i]

    if op=="VALUE":
        res=compute_arg(a1)
    elif op=="ADD":
        res=compute_arg(a1)+compute_arg(a2)
    elif op=="SUB":
        res=compute_arg(a1)-compute_arg(a2)
    elif op=="MULT":
        res=compute_arg(a1)*compute_arg(a2)
    else:
        assert False, (op,a1,a2)
    xval[i]=res
    return res

for i in range(n):
    print(compute(i))