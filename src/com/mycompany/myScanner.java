package com.mycompany;

import java.util.ArrayList;
import java.util.List;


public class myScanner {
    FileHandler myFile = new FileHandler();
    List<String> dataInput = new ArrayList<String>();
    List<String> dataOutput = new ArrayList<String>();

    public void start(String inputPath,String outputPath){
        dataInput = myFile.readFile(inputPath);
        identifyTokens(); 
        writeInFile(outputPath);

    }
    
    public void writeInFile(String outputpath){
        for (int i = 0; i < dataOutput.size(); i++) {
            myFile.writeFile(dataOutput.get(i),outputpath);
        }
    }
    //states:  1 = start, 2 = id, 2* = reserved, 3 = num, 4 = comment, 5 = symbol
    public void identifyTokens(){
        char c;
        String is = "";
        String record;
        int num = dataInput.size();
        int i = 0;
        while(i < num){
            int j = 0;
            String line = dataInput.get(i);
            int counter = dataInput.get(i).length();
            String token = "";
            while(j < counter) {
                c = line.charAt(j);
                String st = Character.toString(c);
                
                if (";".equals(st)){
                    record = token + " \t " + is + "\n" + ";" + " \t " + "special character" + "\n" ;
                    dataOutput.add(record);
                    token = "";
                }
                else if (" ".equals(st)){                                                     
                    record = token + " \t " + is + "\n";
                    dataOutput.add(record);
                    token = "";
                }

                else if (Character.isAlphabetic(c)){
                    token += st;
                    if("if".equals(token) || "then".equals(token) || "else".equals(token) || "end".equals(token) || "repeat".equals(token) || "until".equals(token) || "read".equals(token) || "write".equals(token))
                        is = "reserved word";
                    else 
                        is = "identifier";                           
                }
                else if (Character.isDigit(c)){
                    is = "number";
                    token += st;
                }
                else if ("{".equals(st)){						
                    is = "comment";
                    while(c != '}'){
                        c = line.charAt(j++);
                        st = Character.toString(c);
                        token += st;
                    }
                }
                else if ("/".equals(st) || "*".equals(st) || "+".equals(st) || "-".equals(st)|| "=".equals(st)|| "<".equals(st)|| "(".equals(st)|| ")".equals(st)|| ":".equals(st)){
                    is = "special character";
                    token += st;
                }

                j++;           
            }
            i++;
        }
    }
}

   
    
