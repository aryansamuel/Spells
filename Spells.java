//-----------------------------------------------------------------------------------------------------------------
// The following program is a simple grammar and spelling game called Spells. It starts off with giving
// the player a sentence with multiple misspelt words. The user has to identify which of these words are
// missspelt, and then find the correct replacement from the options given for each misspelt word. 
// The answer should make grammatical sense.
//
// Created by Aryan Samuel.
//-----------------------------------------------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Spells {
	
	public static void main (String[] args) throws IOException {

// Variable declarations --------------------------------------------------------------------------------------
		
		Scanner infile_dict = null, infile_level = null, input = new Scanner(System.in);
		
		int d=0, ans=0, opt=0; // d=# of dictionary words
		
		ArrayList<String> dict = new ArrayList<String>(), level = new ArrayList<String>(), 
			level_key = new ArrayList<String>(), w_spell = new ArrayList<String>(),
			r_spell = new ArrayList<String>();

		// Input check and open files
		if ( (args.length < 2) || (args.length > 2) ) {
			System.err.println("Usage: SplChk infile_dict infile_level");
			System.exit(1);
		}
		infile_dict = new Scanner(new File(args[0]));
		infile_level = new Scanner(new File(args[1]));

		// Initialize dictionary and level arrays
		while (infile_dict.hasNextLine()) { // List of dictionary words.
			dict.add(infile_dict.nextLine());
			d++;
		}
		
		level = new ArrayList<String>(Arrays.asList(infile_level.nextLine().split("[^\\w']+"))); // level words list
		infile_level.nextLine();
		level_key = new ArrayList<String>(Arrays.asList(infile_level.nextLine().split("[^\\w']+"))); // level's answer key list
		
		// Intro prompts
		System.out.println("\nWelcome to Spells! \n\nFigure out the misspelt word in the sentence, " + 
		"then replace it with \nthe grammatically correct answer. \nJust type in the option number you think " +
		"is correct, then press the Enter key. \nHit Enter when ready!");
		System.in.read();
		
		
// Spells game algorithm -------------------------------------------------------------------------------------
		
		// Create list of misspelt words
		for (int i=0; i<level_key.size(); i++) { 
			if (!(level.get(i).equals(level_key.get(i))) ) {
				w_spell.add(level.get(i));
			}
		}
		
		// Main loop
		for (int i=0; i<w_spell.size(); i++) {
			printArray(level);
			
			// Get correct misspelt word from user
			System.out.print("\n\nType in the number of the word you think is misspelt: ");
			int opt_num = 0;
			boolean loop1 = false;
			while (loop1 == false) { // Check if user entered correct misspelt word option
				opt = input.nextInt();
				if (opt > 0 && opt <= level.size()) { // Bounary check
					boolean eq = false;
					for (int j=0; j<w_spell.size(); j++) {
						if ((level.get(opt-1)).equals(w_spell.get(j))) {
							eq = true;
							break;
						}
					}
					if (eq == true) {
						System.out.println("Correct! Which word should replace it?");
						loop1 = true;
					} else System.out.print("That word is correct. Try again: ");
				} else {
					System.out.print("Please enter a valid option: ");
				}
			}
			
			// Find all possible solutions to misspelt word
			for (int k=0; k<d; k++) {
				if (level.get(opt-1).length() == dict.get(k).length()) { // Words searched should be same length
					// Alphabetically sort both words and see if they're the same
					if ( sorted(level.get(opt-1).toLowerCase()).equals(sorted(dict.get(k))) ) { 
						opt_num++;
						r_spell.add(dict.get(k)); // List of misspelt wordse
						System.out.println(opt_num + ")" + dict.get(k) + " ");
					}
				}
			}
			
			// Get correct answer from user
			System.out.print("\nAnswer: ");
			boolean loop2 = false;
			while (loop2 == false) { // Check if user entered correct answer
				ans = input.nextInt();
				if (ans > 0 && ans <= r_spell.size()) { // Boundary check
					boolean eq = false;
					if ((r_spell.get(ans-1)).equals(level_key.get(opt-1).toLowerCase())) {
						System.out.println("Thats right!\n");
						if (i < w_spell.size()-1) System.out.println("Now try another misspelt word...\n");
						loop2 = true;
					} else System.out.print("That's incorrect. Try again: ");
				} else {
					System.out.print("Please enter a valide option: ");
				}
			}
			
			// Reset sentence with correct answer and reset right spellings list
			level.set(opt-1, r_spell.get(ans-1));
			r_spell.clear();
		}
		
		// Final prompts
		System.out.println("You managed to correct the entire sentence! Great job!");
		System.out.println("Here's the correct sentence: \n");
		for (int i=0; i<level_key.size(); i++) {
			System.out.print(level_key.get(i) + " ");
		}
		System.out.println();
	
	}


// Helper Functions ------------------------------------------------------------------------------------------
	
	// search()
	// Binary search that searches through a list to find a match.
	public static int search (ArrayList<String> A, String S) {
		int min = 0, max = A.size()-1, mid;
		while (min <= max) {
			mid = (min+max)/2;
			if (A.get(mid).compareTo(S) > 0) {
				max = mid - 1;
			} else if (A.get(mid).compareTo(S) < 0) {
				min = mid +1;
			} else return mid;
		}
		return -1;
	}
	
	// sorted()
	// Sorts a strings elements in alphabetical order
	public static String sorted (String s) {
		char[] c = s.toCharArray();
		Arrays.sort(c);
		return new String(c);
	}
	
	// printArray()
	// Prints out the items in an ArrayList
	public static void printArray (ArrayList<String> A) {
		for (int i=0; i<A.size(); i++) {
			System.out.print(A.get(i) + "(" + (i+1) + ")" + " ");
		}
		return;
	}

}