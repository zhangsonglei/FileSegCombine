import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: FileSegmentCombine
 * Description: to segment file into certain size files or combine files into one file
 * Company: HUST
 * @author Sonly
 * Date: 2017年6月10日
 */
public class FileSegmentCombine {
	/**
	 * Method: segFile
	 * Description: segment file by certain size
	 * @param read_file
	 * @param seg_path
	 * @throws IOException
	 */
	public static void segFile(String read_file, String seg_path, String encoding) throws IOException {
		File file = new File(read_file);
		
		if(file.isFile() && file.exists()) {
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), encoding);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			
			int max_lines = 1000;	//segment file by 1000 lines
			int line_count = 0;		//line counter
			
			List<String> lines = new ArrayList<String>();
			String line = new String();
			while((line = reader.readLine()) != null) {
				lines.add(line);
				line_count++;
				
				if((line_count % max_lines) == 0) {//time to segment
					String write_file = seg_path+String.valueOf(line_count/max_lines)+".txt";
					File file2 = new File(write_file);
					OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file2));
					BufferedWriter writer = new BufferedWriter(outputStreamWriter);
					
					//write to a new file
					for(String str : lines)
						writer.write(str+"\n");
					
					writer.close();
					lines = new ArrayList<String>();
				}							
			}
			
			if(lines.size() > 0) {
				String write_file = seg_path+String.valueOf(line_count/max_lines + 1)+".txt";
				File file2 = new File(write_file);
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file2));
				BufferedWriter writer = new BufferedWriter(outputStreamWriter);
				
				//write to a new file
				for(String str : lines)
					writer.write(str+"\n");
				
				writer.close();
			}
			
			reader.close();
		}else {
			System.err.println("File:\""+read_file+"\" read failed!");
		}
	}
	
	/**
	 * Method: combineFile
	 * Description: combine files to one file
	 * @param comb_file
	 * @param read_path
	 * @throws IOException
	 */
	public static void combineFile(String comb_file, String read_path, String encoding) throws IOException {
		File file = new File(comb_file);
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file));
		BufferedWriter writer = new BufferedWriter(outputStreamWriter);
	
		File files = new File(read_path);
		String[] list = files.list(); 
		for(String file_name : list){
			File f = new File(read_path+file_name);
			InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(f), encoding);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			
			String line = "";
			while((line = reader.readLine()) != null) {// write along with read
				writer.write(line+"\n");
			}
			reader.close();
		}
		
		writer.close();
	}
	
	/**
	 * Method: main
	 * Description: the entrance of program
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String readFile = "E:\\bigfile\\input.txt";
		String filePath = "E:\\bigfile\\segfiles\\";
		String writeFile = "E:\\bigfile\\output.txt";
		String encoding = "utf-8";
		segFile(readFile, filePath, encoding);
		combineFile(writeFile,filePath, encoding);
	}
}
