import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class SaveableDictionary {

    private HashMap<String, String> englishToFinnish;           //Stores English words
    private HashMap<String, String> finnishToEnglish;           //Stores Finnish words
    private File file;
    private PrintWriter writer;

    public SaveableDictionary() {
        this.englishToFinnish = new HashMap<>();
        this.finnishToEnglish = new HashMap<>();
    }
    public SaveableDictionary(String file){
        this();
        this.file = new File(file);
    }
    

    public void add(String words, String translation) {
        if (!this.englishToFinnish.containsKey(translation)) {
            this.englishToFinnish.put(translation, words);          
        }
        if (!this.finnishToEnglish.containsKey(words)){
            this.finnishToEnglish.put(words, translation);
        }
    }

    public String translate(String word) {
        if (this.englishToFinnish.containsKey(word)){
            return this.englishToFinnish.get(word);
        }
        if (this.finnishToEnglish.containsKey(word)){
            return this.finnishToEnglish.get(word);
        }
        return null;
    }

    public void delete(String word) {
        if (this.englishToFinnish.containsKey(word)){
            String translated = this.englishToFinnish.get(word);
            this.englishToFinnish.remove(word);
            this.finnishToEnglish.remove(translated);
        }else if (this.finnishToEnglish.containsKey(word)){
            String translated = this.finnishToEnglish.get(word);
            this.finnishToEnglish.remove(word);
            this.englishToFinnish.remove(translated);
        }
    }
    public boolean load(){
        try{
           Scanner fileScanner = new Scanner(this.file);
           while (fileScanner.hasNextLine()){
               String line = fileScanner.nextLine();
               String[] pieces = line.split(":");                       //Splitting line by ":" as we are saving translation in word:translation format
               this.englishToFinnish.put(pieces[1], pieces[0]);         
               this.finnishToEnglish.put(pieces[0], pieces[1]);
           }
           return true;
        }catch(Exception e){
            return false;
        }
    }
    public boolean save(){
        try{
            PrintWriter writer = new PrintWriter(this.file);
            for (String key:this.englishToFinnish.keySet()){                  //Saving the words
                writer.append(key+":"+this.englishToFinnish.get(key)+"\n");
            }
            writer.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
