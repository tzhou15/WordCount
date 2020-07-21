import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class WordCountJava {
public static void main(String[] args) throws IOException{
	FileInputStream fin = new FileInputStream("demo1.txt"); 	
	Scanner fileInput = new Scanner(fin);
	List<String> words = new ArrayList<String>(); 	
	List<Integer> counts = new ArrayList<Integer>();
	while(fileInput.hasNext()) { 		
		String nextWord = fileInput.next(); 		
		String newWord = nextWord.toLowerCase().replaceAll("[^a-zA-Z]", "");
		if(words.contains(newWord)) {      
			int index = words.indexOf(newWord);
			counts.set(index, counts.get(index) + 1);	
		} else {
			words.add(newWord);
			counts.add(1);
		}
	}
	fileInput.close(); 	  //Close
	fin.close();
	for(int i = 0; i < words.size(); i++) {         	//Print out the results
		System.out.println(words.get(i) + " occurred " + counts.get(i) + " times ");
	}
}
}
