# Blue Cheese Style (BCS) guide

This document should help clear up some confusion about styles.

## In general

Adelost, and Blue Cheese in general, follows a modified version of the K&R style guide. This is modified specifically for Java programs.

### Braces

* Opening braces **ALWAYS** occur on the same line as the `if`, `else`, `switch`, `try`, `catch`, and `for` statements that they match; the class, interface, and function definitions that they match; and there should be one space between the end of the beginning of the statement (ex. the end of the condition in `if` statements).

    * If a the beginning of a statement spans multiple lines, the brace is placed at the end of the last line

    * There should be **NO** characters, even whitespace, that occur after an opening brace on the same line

    * Example:

        ```
        if (1 == 1) {
        ```

* Closing braces are placed on their own line unless there is another statement that depends on the previous. Some examples are `try`-`catch`-`finally` and `if`-`else if`-`else`. In this case, there is to be one space between the closing brace and the beginning of the next statement

    * Example:

        ```
        }
        if (2 == 2) {
            function();
        } else {
            otherfunction();
        }
        ```

### Indentation

* Code is to be indented with four spaces, **NOT** tabs

* Any code within braces is to be indented, except `case` labels

* Conditions and argument lists, if spanning multiple lines, should be indented with spaces such that the beginning of the next line of lines up with the previous

    * Example:

        ```
        function(arg1, arg2, arg3
                 arg4);
        ```

    * ***NOTE THAT UNLIKE IN THE EXAMPLE, THIS SHOULD ONLY HAPPEN IF THE LINE EXCEEDS 80 CHARACTERS AND DOING THIS IMPROVES READABILITY***

* The previous indentation rules apply to same-type multi-declarations too, except that they should always span multiple lines

    * Example:

        ```
        int a,
            b,
            c;
        ```

* End-braces are to be placed at the same indentation level as the beginning of the statement

### Naming

* All names, except class/interface names, are camelCase

* Class/interface names are to be capitalized LikeThis

* Package names should not be capitalized, ever

* Function and method names should always be descriptive and well-documented

* Class properties and other class-level variables, private or public, should also be descriptive

* Arguments and other Varibles in functions are to be as short as possible while still somewhat conveying their meaning

* Temporary variables, if their meaning is obvious from code regardless of name, should use the highest available name from the below list:

    1. `i`

    2. `j`

    3. `k`

    4. `a`

    5. `b`

    6. `c`

    7. `w`

    8. `x`

    9. `y`

    10. `z`

    * If more are required, use the first available letter in the alphabet

### Comments

* Single line comments placed after a single line of source should be C++-style, and there should be one space between the semicolon and the first `/`, and one space between the first letter of the comment and the second `/`

* Multiline comments should always be C-style, but none of the regular rules apply

    * A single space is to be placed on each side of the `/*` and `*/`

* Comments that appear inside a line of code are discouraged but allowed, and should be C-style

* Commented-out code should always be commented out with C-style comments, no non-code is to be placed in commented-out code

* Javadoc comments should have an extra `*` after the first, and an extra `*` on each line, aligned with the opening `*`

    * C-style comment indentation rules to not apply to javadoc comments, there should be one space between the `*` at the beginning of each line of comment and the contents of that line

* In C-style multiline comments:

    * The opening `/*` (`/**` in the case of javadoc) and the closing `*/` should each be on their own line

    * The contents of the comment are to be indented with four spaces (except code, which is to be indented as if the comments did not exist)

    * Under no circumstances should contents of the comment appear in a column before the opening `/`

    * The beginning `/*` and ending `*/` should be aligned, except in javadoc, where there is one more space before the ending `*/`

* Examples

    * Javadoc comment:

        ```
        /**
         * Documentation!
         * Yay!
         */
        ```

    * C-style explanation of code:

        ```
        /*
            This code is special!
            Here-s why!
        */
        ```

    * Single-line comment:

        ```
        code(); // This does something
        ```

    * inside-code comment:

        ```
        int /* a number */ a;
        ```

### Operatiors

* Spaces should always be placed between binary operators and their operands

* Unary operators should always be placed directly next to their operands

* `Where possible, `a++` and `a--` should be used in place of `++a` and `--a`

    * `a++` and `a--` should never appear in even remotely complex statements

### Miscellaneous

* The ternary operator (`?:`) should have spaces on each side of the `?` and `:`

    * The ternary operator should be used if and only if it improves readability

    * Nested ternary operators should be enclosed in parentheses

* Lists (comma seperated expressions as appearing in array literals, multi-declarations, and arguments) should have a single space placed to the right of each comma

* Parentheses in expressions are to be used only when they are not already defined by order of operations or are required by this document

    * These parentheses are to be spaced at the programmer's discretion, and are to be as readable as possible

* Long expressions can span multiple lines and follow the same rules as function arguments

* Language syntax (with the exception of function calls) that requires the use of parenthesis or other symbol, but allows variable spacing should have spaces on the "outside" of the symbols, for example:

    ```
    // lambda
    (int a, int b) -> {
        return a + b;
    }
    // if statement
    if (a + b == 2) {
        return a + b;
    }
    ```

### Technical

* Line endings are Unix-style (`\n`)

* Documents are encoded in UTF-8

* No characters outside of the Basic Multilingual Plane may be used, even in comments

## Breaking the rules

Violations of these rules are permitted if fixing them would require large amounts of work.

## Automation

Here's a script to fix some of the less-complicated errors. Feel free to contribute!

```
for i in "$(find . -name *.java )";
do sed \
-e "s/if(/if (/g" \
-e "s/for(/for (/g" \
-e "s/while(/while (/g" \
-e "s/){/) {/g" \
-e "s/\( *\)}\n\1else/\1} else/g" \
-e "s/}else/} else/g" \
-e "s/}catch/} catch/g" \
-e "s/\( *\)}\n\1catch/\1} catch/g" \
-e "s/\( *\)}\n\1finally/\1} finally/g" \
-e "s/do{/do {/g" \
-e "s/}while/} while/g" \
-e "s/\t/    /g" \
-i $i;
dos2unix $i; 
done
```
