package sudocu;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sudo implements ActionListener{

   static int[][] matrix=new int[9][9];/*declaring the static two dimensional array to represent the lay out board, it is static so that
   any change made into it would remains same for the next change*/ 

   JFrame f = new JFrame();//creating new frame
   JButton solve = new JButton("Solve");//buttons solve and reset. 
   JButton reset = new JButton("Reset");
/*solve button will solve the sudocu whereever the user stops inputting values and reset button will reset all the fields to 0 */
   int fields_x,fields_y;
   int x = 35;//dimensions of the fields
   int y= 35;
   JTextField[][] fields = new JTextField[9][9]; {//creating fields
       for (fields_y=0; fields_y < 9; fields_y++) {
           x=35;
           for (fields_x =0; fields_x < 9; fields_x++) {

               fields[fields_x][fields_y] = new JTextField();
               fields[fields_x][fields_y].setBounds(x, y, 32, 32);
               f.getContentPane().add(fields[fields_x][fields_y]);
               fields[fields_x][fields_y].setHorizontalAlignment(JTextField.CENTER);
               x = x+35;
           }
           y = y+35;
       }
   }

   Sudo(){/*The default constructor for the class sudo. in this constructor, All the formatting of the GUI and the its connection to
        to the main code is included*/ 

       solve.setBounds(110,370,70, 30);
       reset.setBounds(210,370,70, 30);
       f.add(solve);
       f.add(reset);

       solve.addActionListener(this);
       reset.addActionListener(this);
       f.setSize(400,480);
       f.setTitle("Sudoku Solver!");
       f.setLayout(null);
       f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       f.setVisible(true);
		for (fields_y =0; fields_y < 9; fields_y++) {
           for (fields_x =0; fields_x < 9; fields_x++) {
				fields[fields_x][fields_y].setText("0");//setting defauit values 0 to the fields 
			}
		}
	}
	


   public void actionPerformed(ActionEvent e) {/*Overridden function from an interface ActionListner to handle the events
       make dicisions over the events */

       String s;
       for (fields_y =0; fields_y < 9; fields_y++) {
           for (fields_x =0; fields_x < 9; fields_x++) {
               s = fields[fields_x][fields_y].getText();//getting all the inputs inputted by the user and storing them into matrix array
               matrix[fields_x][fields_y]=Integer.parseInt(s);
           }
       }
		if(e.getSource()== solve){/*If the user has clicked 'solve' button, then firstly it gives the call to 'checkInput()' function
       to check the correctness of the inputs then it will give a call to "solve()" function to check the rules of sudocu and solve 
       the all fields if both the functions return true, the it will fill the 'fields' array with the returned matrix array */
			if (checkInput(matrix)){
				if(solve(matrix, 9)){
					for (fields_y =0; fields_y < 9; fields_y++) {
						for (fields_x =0; fields_x < 9; fields_x++) {
							fields[fields_x][fields_y].setText(""+matrix[fields_x][fields_y]);
						}
					}
				}
           }
			else {/*If both the functions return false, it will show the Error message in the GUI */
			JOptionPane.showMessageDialog(f, "The input given is not proper. Please try again.",
				"Error", JOptionPane.ERROR_MESSAGE); 
			}
       }
		
       if(e.getSource()==reset){/*If the user has clicked 'reset button, then it will give a call to reset()' function to reset all the 
       fields to value '0' */
           if(reset()){
               for (fields_y =0; fields_y < 9; fields_y++) {
                   for (fields_x =0; fields_x < 9; fields_x++) {
                       fields[fields_x][fields_y].setText("0");
                   }
               }
           }
       }
   }

   public static void main(String[] args) {
       new Sudo();/*Main method of a class Sudo which is calling its default constructor Sudo() */
   }


// Logic

   public boolean solve(int [][]board, int n) {/*Defining solve()function. This function will return the boolean according to the 
       situations. This is also a recursive function to backtrack if the solution is not fullfilling the constrains. it will 
       firsty check whether all the fields of the 9x9 matrix is filled with numbers, if yes, it will rreturn boolean as true becoz it
       shows the sudocu is fully solved! if not, then it will continue to execute further. */

       int i,j=0,value;
       int rowInd = -1 , colInd = -1;
       for(i=0; i<n; i++){
           for(j=0; j<n; j++){
               if( board[i][j]==0 ){
                   rowInd=i;
                   colInd=j;
                   break;
               }
           }
           if(rowInd != -1){
               break;
           }
       }
       if( i==n && j==n ){//checking if all the field are filled with nubers or not!
           return true;
       }
       else{           /*If all the fields are not filled with numbers, it means the sudocu needs to be solved again.
           It will firstly call trueVal() function to check whethe the values in the fields sre fulfilling the conditions or not
           the recursive call to solve() providing base condition untill the solve() function returns true. */
           for( value=1; value<10; value++ ){
               if(trueVal(board,value,rowInd,colInd)){
                   board[rowInd][colInd] = value;
                   if(!solve(board, n)){
                       board[rowInd][colInd]=0;
                   }
                   else{
                       return true;
                   }
               }
           }  
           return false;
       }
   }

   public boolean reset(){/*Defining reset() function. it is boolean returrning function returns true after filling all the fields
       '0' */
       int i,j;
       for(i=0; i<9; i++){
           for(j=0; j<9; j++){
               matrix[i][j]=0;
           }
       }
       return true;
   }
/*defining trueval() function. This is the function to check the correctness of the values in the matrix array. it firstly checks
in the row that if there is any value is repeting or not, after that it checks in the columns whether any value is repeting and after
-words it will check in 3x3 matrices for the repeting value. if anywhere value is getting repeted, it returnes false*/

   public boolean trueVal(int [][]board, int value, int rowInd, int colInd) {

       for(int i=0; i<9; i++){
           if(board[rowInd][i] == value){
               return false;
           }
       }

       for(int j=0; j<9; j++){
           if(board[j][colInd] == value){
               return false;
           }
       }

       int baseRow = rowInd - ( rowInd % 3 );
       int baseCol = colInd - ( colInd % 3 );//formulae to get the index positions of starting and ending position of 3x3 matrices
       for(int i=baseRow; i<baseRow+3; i++){
           for(int j=baseCol; j<baseCol+3; j++){
               if(board[i][j] == value){
                   return false;
               }
           }
       }
       return true;//if no value is repeting, it will return true
   }
	public boolean checkInput(int[][]x) {/*Defining checkInput() function, This function checks the inputs from the users. the input 
      from the user must be wiihtin 1-9, shuold not be repeted whithin any row and column or in any 3x3 matrix.     */
		int count=0;
		int i=0;
		int j=0;
		int value;
		int a;
		for (i=0;i<9;i++){//checking in full 9x9 matrix if all values are zero
			for (j=0; j<9; j++){
				if (x[i][j] ==0){
					count++;
				}
				if ((x[i][j] > 9)||(x[i][j]<0)){
					return false;
				}
			}		
		}
		if (count==81){//if all are zero, will return the boolean as false
			return false;
		}
		for (i=0;i<9;i++){
			for (j=0; j<9; j++){//checking repetation of the values in row, column, and 3x3 matrix
				if (x[i][j]!=0){ 
					value = x[i][j];  
					for(a=0; a<i; a++){
						if (x[a][j] == value){
							return false; 
						} 
					}
					for(a=i+1; a<9; a++){
						if (x[a][j] == value){
							return false; 
						} 
					}
					for(a=0; a<j; a++){
						if (x[i][a] == value){
							return false; 
						}
					}
					for(a=j+1; a<9; a++){
						if (x[i][a] == value){
							return false;
						}
					}
					int baseRow = i - ( i % 3 );//checking repetation of numbers  in 3x3 matrices
					int baseCol = j - ( j % 3 );	
					for(int c=baseRow; c<baseRow+3; c++){
						for(int b=baseCol; b<baseCol+3; b++){
							if ((c!=i) && (b!=j)){
								if (x[c][b]==value){
									return false; 
								}
							}
						}
					}
				}
			}
		}
		return true; //If no any rules of sudocu is being violated by the player, this function will return true. 
	}			
}
//This project is developed by Omkar Ankush Saraf. If you get stuck anywhere while understanding the workflow in the code,
//feel free to ask anything at "omkarsaraf18@gmail.com"! And if you find it helpful, you can buy me a coffee!!