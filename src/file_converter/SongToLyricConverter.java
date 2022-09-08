package file_converter;

import java.io.IOException;

public class SongToLyricConverter {
	
	private Song currentSong;
	private String lyricText;
		
	public SongToLyricConverter(Song curSong) {
		currentSong = curSong;
	}

	public void saveLyricsConversion() throws IOException {
		saveLyricsConversion(Utilities.HTML_OUTFILE_DIR + currentSong.getTitle() + ".txt");
	}
	
	public void saveLyricsConversion(String location) throws IOException {
		Utilities.saveToLocation(getLyricsText(), location);
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
