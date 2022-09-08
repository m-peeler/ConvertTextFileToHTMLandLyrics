package file_converter;

import java.io.IOException;

public class Main {
	public static void main(String[] args) throws IOException {	
		
		Song song = new SongFromTextFile().getSong();
		
		SongToHTMLConverter toHTML = new SongToHTMLConverter(song);
		toHTML.saveHTMLConversion();
		
		SongToLyricConverter toLyric = new SongToLyricConverter(song);
		toLyric.saveLyricsConversion();		
		
		System.out.println("Conversion complete\n\n");
	}
}
