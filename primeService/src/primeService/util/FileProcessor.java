package primeService.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileProcessor {
    static FileWriter writer;
    List<String[]> fileData=new ArrayList<>();
    String errorFilePath;
    /**
     * @param  filepath  It is an input file path
     * @return List<String[]> returning all the data in the courseInfo input file
     */
    public List<String[]> readFile(String filepath){
        try {
            Scanner sc = new Scanner(new File(filepath));
            while (sc.hasNextLine()) {
                String s=sc.nextLine();
                if(s.endsWith(";")){
                    s=s.replace(";","");
                }
                String[] split=s.split(":");
                fileData.add(split);
            }
            sc.close();
        }catch(FileNotFoundException e) {
            System.err.println("Please provide the correct input file");
            e.printStackTrace();
            System.exit(0);
        }
        return fileData;
    }
    /**
     * @param  filepath  It is an  input file path
     * @return Scanner it is an object of scanner class.
     */
    public Scanner getFileScanner(String filepath){
        Scanner sc=null;
        try {
            sc = new Scanner(new File(filepath));
        }catch(FileNotFoundException e){
            System.err.println("Please provide the correct input file");
            e.printStackTrace();
            System.exit(0);
        }
        return sc;
    }
    /**
     * @param  Scanner  it is an object of scanner class.
     * @return String[] returning string array
     */
    public String[] readFileLine(Scanner sc){
        String s=sc.nextLine();
        String[] line=s.split(" ");
        return line;
    }
    /**
     * @param  filepath  It is an output file path
     * @param  List<String>  It is an output file path
     * @throws IOException
     */
    public void createWriter() throws IOException{
        writer= new FileWriter(errorFilePath);
    }
    public void writeError(String mesString){

      
                try {
                    writer.write(mesString+ "\n");
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
           
    }
    /**
     *
     */
    public void closeWriter(){
        try{
            writer.close();
        }  catch(IOException e){
            System.err.println("Runtime exception occurs while closing the writer output file");
            e.printStackTrace();
            System.exit(0);
        }
    }

}

