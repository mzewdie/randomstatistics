package com.mul.randomstatistics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Random;

public class RecordRandomize {
	
	private int sizeDrawingNumbers; //numbers to be drawn 0 to sizeDrawingNumbers-1, default 100
	private int randomizeSize; //They are drawn randomiseSize times, default 500
	Hashtable<Integer, Integer> recordRandomiseHashtables = new Hashtable<Integer, Integer>();
	
	



	public RecordRandomize(int sizeDrawingNumbers, int randomizeSize) {
		super();
		this.sizeDrawingNumbers = sizeDrawingNumbers;
		this.randomizeSize = randomizeSize;
	}

	

	public int getSizeDrawingNumbers() {
		return sizeDrawingNumbers;
	}



	public int getRandomizeSize() {
		return randomizeSize;
	}



	public Hashtable<Integer, Integer> getRecordRandomiseHashtables() {
		return recordRandomiseHashtables;
	}



	/**
	 * initialises the hashtable. At the begining have all entries 0 values
	 */
	public void initHashTable() {
		for(int i=0; i<sizeDrawingNumbers;i++) {
			this.recordRandomiseHashtables.put(new Integer(i), new Integer(0));
		}
	}
	
	
	
	public static void main(String[] args) {

		 String sizeDrawingNumbersStr=args[0];
		 String randomizeSizeStr = args[1];
		 int sizeDrawingNumbers=100; //default
		 int randomizeSize=500;
		 if(!sizeDrawingNumbersStr.equals("")) {
			 try {
				 int sizeDrawingNumbersCmd=Integer.parseInt(sizeDrawingNumbersStr);
				 sizeDrawingNumbers=sizeDrawingNumbersCmd;
			 }
			 catch (NumberFormatException exc) {
				 exc.printStackTrace();
			 }
		 }
		 
		 if(!randomizeSizeStr.equals("")) {
			 try {
				 int randomizeSizeCmd=Integer.parseInt(randomizeSizeStr);
				 randomizeSize=randomizeSizeCmd;
			 } catch (NumberFormatException exc) {
				 exc.printStackTrace();
			 }
		 }
         RecordRandomize recordRandomize = new RecordRandomize(sizeDrawingNumbers,randomizeSize);
         recordRandomize.initHashTable();
         
         Random random = new Random();
         //now randmize and record the result in the hashtable
         for(int i=0; i<recordRandomize.getRandomizeSize();i++) {
        	 int drawnNumber = random.nextInt(recordRandomize.getSizeDrawingNumbers());
        	 //get the last values saved for this drawn number
        	 int lastValueOfDrawnNumber = recordRandomize.getRecordRandomiseHashtables().get(new Integer(drawnNumber));
        	 //increment its outcome
        	 lastValueOfDrawnNumber+=1;
        	 //write the result to the table
        	 recordRandomize.getRecordRandomiseHashtables().put(new Integer(drawnNumber), lastValueOfDrawnNumber);
         }
         
         
         //display the result and write the result to the file
         Calendar currentDateCal=Calendar.getInstance(Locale.GERMANY);
         //String currentTimeStr =""+currentDateCal.get(Calendar.YEAR)+currentDateCal.get(Calendar.MONTH)+currentDateCal.get(Calendar.DAY_OF_MONTH)+"_"+
         //                     currentDateCal.get(Calendar.HOUR)+currentDateCal.get(Calendar.MINUTE)+currentDateCal.get(Calendar.SECOND);
         
         
         
         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
         String dateStr= simpleDateFormat.format(currentDateCal.getTime());
         //System.out.println("current date str: "+dateStr);
         String fileName = "DrawnNumbers_"+dateStr+".csv";
       
         
         boolean writeToFile=true;
         //FileOutputStream fileOutputStream=null;
         
         BufferedWriter bufferedWriter=null;
         File file = new File(fileName);
         if(!file.exists()) {
         
          try {
        	    file.createNewFile();
				//fileOutputStream= new FileOutputStream(file);
        	    bufferedWriter = new BufferedWriter(new PrintWriter(file));
				System.out.println("Results for the drawn numbers:");
				for(int i =0; i<recordRandomize.getRecordRandomiseHashtables().size();i++)  {
		        	 System.out.println(i+ " wurde "+ recordRandomize.getRecordRandomiseHashtables().get(new Integer(i))+" gezogen");
		             if(writeToFile) {
		            	 String lineStr=i+";"+recordRandomize.getRecordRandomiseHashtables().get(new Integer(i))+"\n";
		            	 bufferedWriter.append(lineStr, 0, lineStr.length());
		             }
		         
		        
		         }
				
				bufferedWriter.flush();
		        bufferedWriter.close();
				
			} catch (IOException e) {
				writeToFile=false;
				e.printStackTrace();
			}
         }
         
         
         if(writeToFile==false) {
        	 System.out.println ("Didn't write to file. See the command line for error!");
         } 
        
         
         

	}

}
