package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadKEELFile {

	public static PatternSet readFile(String fileName)throws IOException{
		ArrayList<String> variables = new ArrayList<String>();
		ArrayList<Double> variables_min = new ArrayList<Double>();
		ArrayList<Double> variables_max = new ArrayList<Double>();
		
		ArrayList<String> classes = new ArrayList<String>();
		ArrayList<TrainPattern> p = new ArrayList<TrainPattern>();
		
		BufferedReader br;
		File file = new File(fileName);
		br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		
		Pattern pattern = null;
		Matcher matcher = null;
		boolean endHead = false;
		String [] st;
		while(line!=null && !endHead){
			
			pattern = Pattern.compile("[@[\\w]]+"); // busca cualquier secuencia de letras
			matcher = pattern.matcher(line);
			matcher.find();
			String key = matcher.group();
			
			if("@attribute".equals(key)){
				matcher.find();
				if(matcher.group().equalsIgnoreCase("class")){
					matcher.usePattern(Pattern.compile("[\\w[-]]+"));
					while(matcher.find())
						classes.add((String)matcher.group());//matcher.group());
				} else{
					variables.add(matcher.group());
					matcher.usePattern(Pattern.compile("\\-?\\d+\\.?\\d*"));
					matcher.find();
					variables_min.add(Double.parseDouble(matcher.group()));
					matcher.find();
					variables_max.add(Double.parseDouble(matcher.group()));
				}
				
			}else if("@data".equals(key)){
				endHead = true;
			}
			line = br.readLine();
		}

		
		// procesar las lineas y normalizarlas
		int i = 0;
		TrainPattern patternSet;
		while(line!=null){
			i= 0;
			st = line.split(",");
			patternSet = new TrainPattern();
			while(i <= (variables.size()-1)){
				patternSet.putInput(variables.get(i), MathOperation.NormalizeDouble(variables_min.get(i), 
						variables_max.get(i), Double.parseDouble(st[i])));
				i++;
			}
			patternSet.setOutput(st[i].trim());
			p.add(patternSet);
			line = br.readLine();
		}
		
		br.close();
		
		PatternSet toReturn = new PatternSet();
		toReturn.setPattern(p.toArray(new TrainPattern[1]));
		toReturn.setPossibleOutputs(classes.toArray(new String[1]));
		toReturn.setInputNames(variables.toArray(new String[1]));
				
		return toReturn;
	}
	
}
