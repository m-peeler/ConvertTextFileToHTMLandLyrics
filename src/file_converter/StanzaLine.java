package file_converter;

public class StanzaLine {
	
	private String chordsLine;
	private String lyricsLine;
	
	public StanzaLine(String chordsLine, String lyricsLine) {
		this.chordsLine = chordsLine;
		this.lyricsLine = lyricsLine;
	}
	
	public String getChordsLine() {
		return chordsLine;
	}
	
	public String getLyricsLine() {
		return lyricsLine;
	}
}
