package com.mycompany;

import java.util.ArrayList;
import java.util.List;


public class myScanner {
    FileHandler myFile = new FileHandler();
    List<String> dataInput = new ArrayList<String>();
    StringBuilder dataOutput = new StringBuilder();
    public void start(String inputPath,String outputPath){
        dataInput = myFile.readFile(inputPath);
        identifyTokens(outputPath); 
    }
    

    //states:  1 = start, 2 = id, 2* = reserved, 3 = num, 4 = comment, 5 = symbol
    public void identifyTokens(String outputPath){
        char c;
        String record;
        int num = dataInput.size();
        int i = 0;
        while(i < num){
            int j = 0;
            String line = dataInput.get(i);
            int counter = dataInput.get(i).length();
            String token = "";
            String is = "";

            while(j < counter) {
                c = line.charAt(j);
                String st = Character.toString(c);
                
                if (" ".equals(st)){                                                     
                     record = token + "\t"+"\t"+"\t" + is;
                    dataOutput.append(record + System.getProperty("line.separator"));
                    token = "";
                }
                else if ("".equals(st)){
                     record = token + "\t"+"\t"+"\t" + is;
                    dataOutput.append(record + System.getProperty("line.separator"));
                    token = "";
                }
                else if (";".equals(st)){
                    record = token + "\t"+"\t"+"\t" + is;
                    String endLine = ";" + "\t"+"\t"+"\t" + "special character";
                    j++;
                    dataOutput.append(record + System.getProperty("line.separator"));
                    dataOutput.append(endLine + System.getProperty("line.separator"));
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
                    j--;
                }
                else if ("/".equals(st) || "*".equals(st) || "+".equals(st) || "-".equals(st)|| "=".equals(st)|| "<".equals(st)|| "(".equals(st)|| ")".equals(st)|| ":".equals(st)){
                    is = "special character";
                    token += st;
                }

                j++;
                if(j == counter && !(";".equals(token))){
                    if(!is.equals("comment"))
                    record = token + "\t"+"\t"+"\t" + is;
                    else
                        record = token + "\t" + is;
                    dataOutput.append(record + System.getProperty("line.separator"));
                    token = "";
                }
            }
            i++;
            myFile.writeFile(dataOutput.toString(),outputPath);
        }
    }
}

   
    
