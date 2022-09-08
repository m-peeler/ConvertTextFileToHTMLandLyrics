package file_converter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class SongToLyricConverter {
	
	private Song currentSong;
	private String lyricText;
	
	private static final String HTML_OUTFILE_DIR = "HTMLs/";
	
	public SongToLyricConverter(Song curSong) {
		currentSong = curSong;
	}

	public void saveLyricsConversion() throws IOException {
		saveLyricsConversion(HTML_OUTFILE_DIR + currentSong.getTitle() + ".txt");
	}
	
	public void saveLyricsConversion(String location) throws IOException {
		saveToLocation(getLyricsText(), location);
	}
	
	private static void saveToLocation(String contents, String location) throws IOException {
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(location), "UTF-8"));
			out.write(contents);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public String getLyricsText() {
		if (this.lyricText == null) {
			lyricText = buildLyrics();
		}
		return lyricText;
	}
	
	private String buildLyrics() {
		
		StringBuilder lyrics = new StringBuilder();
		lyrics.append("SONG:" + currentSong.getTitle() + "  (2021-07-05)\n");
		lyrics.append("-" + currentSong.getAuthors());
		
		for (String stanza : currentSong.getOrder()) {
			Stanza curStanza = currentSong.getStanza(stanza);
			for (StanzaLine line : curStanza.getStanzaLines()) {
				lyrics.append(line.getLyricsLine() + "\n");
			}
			lyrics.append("\n");
		}
		lyrics.append("\n");
		
		return lyrics.toString();
	}

}
