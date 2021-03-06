# skolem

FIXME: My attempt to solve the Skolem Problem using Clojure, a Lisp dialect that runs on the JVM.

## Overview
The Skolem problem is a problem in computer science that has been open since the 60s. It asks the
question:
> Given any linear recurrence relation (with certain initial conditions), does it contain a zero?

When I created the program, I wrote a few functions to generate a linear recurrence relation and find the zeroes in that sequence.
The two important functions of the program are:
- lrr: Generates a linear recurrence relation given certain initial conditions
- skolemization: Finds the index of a zero in a linear recurrence relation.

The format of the linear recurrence relation equation in the program is
> a_n = c_0*a_n-k + c_1*a_n-(k+1) + ... + c_(n-1)*a_0

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    Before using this program, I assume the following:
    - You have an installation of Java on your machine
    - It's location is on your PATH.
    - You have taken (or are taking) calculus
    
    To run the program:
    $ java -jar skolem-0.1.0-standalone.jar [args]
    
    Once the program is running, you will be asked for the following:
    - The sequence's initial terms
    - k
    - Are all coefficients 1?
    - if yes to the question, the coefficients of the equation in order
    - The number of terms you'd like to generate (n)
    
    You will then get n terms of the sequence according to the conditions you inputted.
    Below that line displays the terms in the sequence that are zero  (a_n, n = index of zero).
    Then it will prompt you to state if you would like to run the program again.

## Examples
####Sample program run:

#####Fibonacci Sequence: a_n = a_n-1 + a_n-2, a_0 = 1, a_1 = 1
Enter initial terms: 1, 1 <br />
Enter k: 1 <br />
Are all coefficients 1? (y/n): y <br />
How many terms would you like? 10

Sequence: 1, 1, 2, 3, 5, 8, 13, 21, 34, 55<br />
Zeroes: 

You wanna run it again? (y/n): y

#####Pell Numbers: a_n = 2*a_n-1 + a_n-2, a_0 = 0, a_1 = 1
Enter initial terms: 0, 1 <br />
Enter k: 1 <br />
Are all coefficients 1? (y/n): n <br />
Enter coefficients in order: 2, 1 <br />
How many terms would you like? 10

Sequence: 0, 1, 2, 5, 12, 29, 70, 169, 408, 985<br />
Zeroes: a_0

You wanna run it again? (y/n): y

#####Perrin Sequence: a_n = a_n-2 + a_n-3, a_0 = 3, a_1 = 0, a_2 = 2
Enter initial terms: 3, 0, 2<br />
Enter k: 2<br />
Are all coefficients 1? (y/n): y <br />
How many terms would you like? 15

Sequence: 3, 0, 2, 3, 2, 5, 5, 7, 10, 12, 17, 22, 29, 39, 51 <br />
Zeroes: a_1

You wanna run it again? (y/n): y

#####My own random sequence: a_n = 3*a_n-1 - 3*a_n-2, a_0 = a_1 = 0, a_2 = -1, a_3 = 0, a_4 = 1
Enter initial terms: 0, 0, -1, 0, 1 <br />
Enter k: 1 <br />
Are all coefficients 1? (y/n): n <br />
Enter coefficients in order: 3, -3<br />
How many terms would you like? 20

Sequence: 0, 0, -1, 0, 1, 3, 6, 9, 9, 0, -27, 81, -162, -243, -243, 0, 729, 2187, 4374, 6561<br />
Zeroes: a_0, a_1, a_3, a_9, a_15

You wanna run it again? (y/n): n

## License

Copyright © 2016 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
