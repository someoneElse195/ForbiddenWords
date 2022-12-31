package net.juneclaire.forbiddenwords;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class Words {
    
    public ArrayList<String> arrayBuilder() throws FileNotFoundException, URISyntaxException {

        File file = new File(App.jarPath() + "\\ForbiddenWords\\words.txt");
        ArrayList<String> wordList = new ArrayList<String>();
        Scanner scnrbld = new Scanner(file);

        while (scnrbld.hasNextLine()) {
            wordList.add(scnrbld.next());
        }

        scnrbld.close();
        return wordList;
    }

    public String chooseWord() throws IOException, URISyntaxException {
        
        new File(App.jarPath() + "\\ForbiddenWords").mkdirs();
        File file = new File(App.jarPath() + "\\ForbiddenWords\\progressed.txt");

        if(file.createNewFile()) {
            FileWriter writer = new FileWriter(file, true);
            writer.write("0");       
            writer.close();
            Scanner scnrchse = new Scanner(file);
        
            ArrayList<String> wordList = arrayBuilder();
            String word = wordList.get(scnrchse.nextInt());
            scnrchse.close();
            return word;
        } else {
            Scanner scnrchse = new Scanner(file);
        
            ArrayList<String> wordList = arrayBuilder();
            String word = wordList.get(scnrchse.nextInt());
            scnrchse.close();
            return word;
        }

    }


}
