# Hello World program in Python
print "Hello World!\n"

def gcd(a, b):
    """Calculate the Greatest Common Divisor of a and b.

        Unless b==0, the result will have the same sign as b (so that when
        b is divided by it, the result comes out positive).
        """
    while b:
        a, b = b, a % b
    return a
    
def simplify_fraction(numer, denom):

    if denom == 0:
        return "Division by 0 - result undefined"

    # Remove greatest common divisor:
    common_divisor = gcd(numer, denom)
    (reduced_num, reduced_den) = (numer / common_divisor, denom / common_divisor)
    # Note that reduced_den > 0 as documented in the gcd function.

    if reduced_den == 1:
        return "%d/%d is simplified to %d" % (numer, denom, reduced_num)
    elif common_divisor == 1:
        return "%d/%d is already at its most simplified state" % (numer, denom)
    else:
        return "%d/%d is simplified to %d/%d" % (numer, denom, reduced_num, reduced_den)
    
num = 63 * (1985**18) * 65027135
print num

deno = 64 * (2016**18) * 65028096
print deno

fract = num/float(deno)
print fract

num2 = 744755736701
deno2 = 1000000000000

print simplify_fraction(744755736701, 1000000000000)
