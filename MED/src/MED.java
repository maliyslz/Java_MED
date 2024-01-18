import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class MED {
	private static final int MAX_ALTERNATIVE_WORDS = 5;
	private static final int NUM_OF_TRY_PART1=5;
	private static final int NUM_OF_TRY_PART2=2;
	

	public static void main(String[] args) {
		try {
            // Part 1: User interface for finding alternative correct words
            System.out.println("Part 1: Find Alternative Correct Words\n");
			double timePart1=0;
            Scanner scanner = new Scanner(System.in,"CP857");
			for(int i=0;i<NUM_OF_TRY_PART1;i++){
				System.out.print("Enter a word: ");
				String inputWord = scanner.nextLine();
				long startTime = System.currentTimeMillis();

            	HashMap<String, Integer> alternatives = alternativeWords(inputWord);
				printAlternativeWords(inputWord, alternatives);
				
				long endTime = System.currentTimeMillis();
				long elapsedTime=endTime-startTime;
				double elapsedTimeSeconds=(double)elapsedTime/1000;
				timePart1+=elapsedTimeSeconds;
				System.out.println("\nRunning time = "+elapsedTimeSeconds+" seconds\n");

			}
            System.out.println("Running time for Part 1 = "+timePart1+" seconds\n");

			double timePart2=0;

            // Part 2: User interface for finding MED value between two words
            System.out.println("\nPart 2: Minimum Edit Distance (MED) between two words");
            
			for(int i=0;i<NUM_OF_TRY_PART2;i++){
				System.out.print("Enter the first word: ");
            	String word1 = scanner.nextLine();
            	System.out.print("Enter the second word: ");
            	String word2 = scanner.nextLine();
				long startTime = System.currentTimeMillis();
				int medValue = calculateMED(word1, word2, true);
				System.out.println("\nMinimum Edit Distance between '" + word1 + "' and '" + word2 + "': " + medValue);
				long endTime = System.currentTimeMillis();
				long elapsedTime=endTime-startTime;
				double elapsedTimeSeconds=(double)elapsedTime/1000;
				System.out.println("Running time = "+elapsedTimeSeconds+" seconds\n");
				timePart2+=elapsedTimeSeconds;
			}
            System.out.println("Running time for Part 2 = "+timePart2+" seconds\n");
			System.out.println("Total running time = "+(timePart1+timePart2)+" seconds\n");

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
    	}
	}

	private static void printAlternativeWords(String inputWord, Map<String, Integer> alternatives) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(alternatives.entrySet());
        Collections.sort(list, Map.Entry.comparingByValue());
        System.out.println("\nAlternative correct words for '" + inputWord + "':");
        int count = 0;
        for (Map.Entry<String, Integer> entry : list) {
            if (count != MAX_ALTERNATIVE_WORDS) {
				if(entry.getValue()==0) continue;
                System.out.println(" word: '" + entry.getKey() + "'  proximity: " + entry.getValue());
                count++;
            } else {
                break;
            }
        }
    }
	private static HashMap<String,Integer> alternativeWords(String word) 
		throws IOException {
		HashMap<String, Integer> alternatives = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("vocabulary_tr.txt"), Charset.forName("ISO-8859-9")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                alternatives.put(line, calculateMED(word, line, false));
            }
        }catch (IOException e) {
			e.printStackTrace();
		}
        return alternatives;
    }
	public static Stack<String> processCalculator(int[][] table,String word1,String word2){
		Stack<String>finalStack=new Stack<>();
		
		int row=word1.length();
		int column=word2.length();
		while (!(row == 0 || column == 0)) {
            if (table[row][column] == minimum(table, row, column, false)) {
                row --;
                column --;
            } else if (1 == minimum(table, row, column, true)) {
                finalStack.push("Substitution " + word1.charAt(row - 1) + " with " + word2.charAt(column - 1));
                row --;
                column --;
            } else if (2 == minimum(table, row, column, true)) {
                finalStack.push("Insertion " + word2.charAt(column - 1));
                column --;
            } else {
                finalStack.push("Deletion " + word1.charAt(row - 1));
                row --;
            }
        }
		
	
		if (!(row == 0 && column == 0)) {
            if (row == 1 && column == 1) {
                finalStack.push("Substitution " + word1.charAt(0) + " with " + word2.charAt(0));
            } else if (row == 0 && column == 1) {
                finalStack.push("Insertion " + word2.charAt(column - 1));
            } else {
                finalStack.push("Deletion " + word1.charAt(row - 1));
            }
        }
		
		return finalStack;
	}
	public static int calculateMED(String word1,String word2,boolean isPrint){
		int row=word1.length()+1;
		int column=word2.length()+1;
		int[][] table=new int[row][column];
		for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i == 0) {
                    table[i][j] = j;
                } else if (j == 0) {
                    table[i][j] = i;
                } else if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    table[i][j] = table[i - 1][j - 1];
                } else {
                    table[i][j] = 1 + minimum(table, i, j,false)  ;
                }
            }
        }
		if(isPrint){
			printTable(table, word1,word2);
			Stack<String> process=processCalculator(table,word1,word2);
			while(!process.empty()){
				System.out.println(process.pop());
			}
		}
		return table[row-1][column-1];
	}
	public static int minimum(int[][] table,int a,int b,boolean isProcess){
		int intNum1 = table[a-1][b-1];//substitution
        int intNum2 = table[a][b-1];//deletion
        int intNum3 = table[a-1][b];//insertion
        int min = Math.min(Math.min(intNum1, intNum2), intNum3);

        

		if (!isProcess){
            return min;                 
        }
            
        if (intNum1 == min) {
            return 1;
        } else if (intNum2 == min) {
            return 2;
        } else {
            return 3;
        }
		

	}
	public static void printTable(int[][] table, String word1, String word2) {
        int row = word1.length() + 2;
        int column = word2.length() + 2;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("      Empty");
                    j ++;
                } else if (i == 0) {
                    System.out.print("  " + word2.charAt(j - 2) + "   ");
                } else if (i == 1 && j == 0) {
                    System.out.print("Empty");
                } else if (j == 0 && i >= 2) {
                    System.out.print("  " + word1.charAt(i - 2) + "  ");
                } else
                    System.out.print("  " + table[i - 1][j - 1] + "   ");
            }
            System.out.println();
        }
    }

}
