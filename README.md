# Java_MED
# Alternative Correct Words and Minimum Edit Distance (MED)
This Java program demonstrates two functionalities:

1. Finding alternative correct words for a given input word.
2. Calculating the Minimum Edit Distance (MED) between two words.

# Part 1: Find Alternative Correct Words

The program prompts the user to input a word and then finds alternative correct words from a vocabulary file (vocabulary_tr.txt). It uses the Minimum Edit Distance algorithm to measure the similarity between the input word and words in the vocabulary.

## How to Use Part 1:
1. Run the program.
2. Enter a word when prompted.
3. The program will display alternative correct words and their proximity to the input word.

# Part 2: Minimum Edit Distance (MED) between Two Words
The program prompts the user to input two words and calculates the Minimum Edit Distance (MED) between them. It uses dynamic programming to construct a table and then backtracks to find the sequence of operations (insertion, deletion, substitution) to transform one word into the other.

## How to Use Part 2:
1. Run the program.
2. Enter the first word when prompted.
3. Enter the second word when prompted.
4. The program will display the Minimum Edit Distance and the sequence of operations.
## Files:
- Main.java: The main Java program file.
- vocabulary_tr.txt: The vocabulary file used to find alternative correct words.
## Running Time:
The program provides running times for both Part 1 and Part 2. The total running time is also displayed.

## Notes:
Ensure that the vocabulary file (vocabulary_tr.txt) is present in the same directory as the Java program.
