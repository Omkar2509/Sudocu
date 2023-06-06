# Sudocu
Welcome to sudocu game ! It consists of a 9x9 textbox grid. The value for a textbox is set if it adheres to the rules of sudoku,
otherwise, the code backtracks and checks for other possible values.
The GUI consists of a 9x9 grid, a solve button, and a reset button.
On clicking solve, the solver first checks if the input is true or false (number of inputs range between 1 and 81,
each input should be in the range of integers between 1-9, there should be no repetition of values in a row, 
column or a submatrix (3x3 grid)). If the given user input is correct, the solver proceeds to solve the puzzle. 
For solving, each textbox that is empty is checked for all possible values (range: 1 to 9).
 If there exists a duplicate value in a particular row, column, or matrix, the code flags the value and backtracks. 
 This process takes place until all the 81 textboxes are filled with their correct values. 
If the user input is not correct, a dialogue box pops up with an error message. 
User input is wrong
