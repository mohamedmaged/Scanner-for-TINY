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
        boolean commemt_flag=true;
        char c;
        String record;
        int num = dataInput.size();
        int i = 0;
        String token=""; String is="";
        while(i < num){
            int j = 0;
            String line = dataInput.get(i);
            int counter = dataInput.get(i).length();

            if(commemt_flag) {
                 token = "";
                 is = "";
            }
            while(j < counter) {
                c = line.charAt(j);
                String st = Character.toString(c);
                
                if (" ".equals(st) && commemt_flag){
                     record = token + "\t"+"\t"+"\t" + is;
                    dataOutput.append(record + System.getProperty("line.separator"));
                    token = "";
                    is="";
                }

                else if (";".equals(st) && commemt_flag){
                    record = token + "\t"+"\t"+"\t" + is;
                    String endLine = ";" + "\t"+"\t"+"\t" + "special character";
                    j++;
                    dataOutput.append(record + System.getProperty("line.separator"));
                    dataOutput.append(endLine + System.getProperty("line.separator"));
                    token = "";
                    is="";
                }
                else if (Character.isAlphabetic(c) && commemt_flag){
                    token += st;
                    if("if".equals(token) || "then".equals(token) || "else".equals(token) || "end".equals(token) || "repeat".equals(token) || "until".equals(token) || "read".equals(token) || "write".equals(token))
                            is = "reserved word";
                    else {
                        if(is.equals(""))
                            is = "identifier";
                        if(is.equals("number"))
                            is="warning wrong identifier" ;

                    }

                }
                else if (Character.isDigit(c) &&commemt_flag){
                    if(is.equals(""))
                        is = "number";
                    token += st;
                }
                else if ("{".equals(st) | ! commemt_flag){
                    is = "comment";
                    while(c != '}'){
                        if(j < counter )
                        c = line.charAt(j++);
                        else {
                            commemt_flag = false;
                            break;
                        }
                        st = Character.toString(c);
                        token += st;
                        commemt_flag=true;
                    }
                    j--;
                }
                else if (("/".equals(st) || "*".equals(st) || "+".equals(st) || "-".equals(st)|| "=".equals(st)|| "<".equals(st)|| "(".equals(st)|| ")".equals(st)|| ":".equals(st)) && commemt_flag ){
                    is = "special character";
                    token += st;
                }

                j++;
                if((j == counter && !(";".equals(token)))&& commemt_flag ){
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

   
    
