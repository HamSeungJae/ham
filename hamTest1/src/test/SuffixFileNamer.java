package test;

import java.io.File;

public class SuffixFileNamer {
	
	String directory = "G:/사진/2019/애들베트남/곱추";
	String newDir = "G:/사진/2019/03_베트남";
	String suffix = "gop";
	
	void run() {
		
		File f;
		File oriFile;
		File newFile;
		
		String fileName;
		String fileExt;
		String newFileName;
		
		int dotIdx;
		
		try {
			f = new File(directory);

			for (String s : f.list()) {

				// Start Add Suffix
				dotIdx = s.lastIndexOf(".");
				fileName = s.substring(0, dotIdx);
				fileExt = s.substring(dotIdx);
				
				newFileName = fileName.concat("_").concat(suffix).concat(fileExt);
				
				oriFile = new File(directory.concat(File.separator).concat(s));
				newFile = new File(newDir.concat(File.separator).concat(newFileName));
				
				if(!newFile.exists()) {
					System.out.println("NOT EXISTS FILE!\t\t:\t\t"+newFileName);
					
					oriFile.renameTo(newFile);
				}else {
					System.err.println("EXISTS FILE!\t\t:\t\t"+newFileName);
				}
			}

		}catch(Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}finally {
		}
	}
	
	public static void main(String[] args) {
		SuffixFileNamer sfn = new SuffixFileNamer();
		
		sfn.run();
	}
}
