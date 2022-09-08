
package file_converter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

/**
 * Takes a {@link Song} and converts into a file with HTML and / or Lyrics, as needed.
 * Will generate HTML and / or Lyrics on the basis of the {@link Song}'s state when
 * the {@code get} or {@code save} methods are called, then save those strings internally.
 * A persistent {@link SongLyricAndHTMLConverter} should not be relied upon if {@link Song}
 * is being regularly modified.
 * 
 * @author Michael Peeler and Jake Shore
 *
 */
public class SongLyricAndHTMLConverter { 
	
	private Song currentSong;
	private String htmlText;
	private String lyricText;

	private static final String[] CHORDS = { "A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#" };
	private static final String HTML_OUTFILE_DIR = "HTMLs/";
	
	public SongLyricAndHTMLConverter(Song curSong) throws IOException{
		currentSong = curSong;
	}
	
	public void saveHTMLConversion() throws IOException {
		saveHTMLConversion(HTML_OUTFILE_DIR + currentSong.getTitle() + ".html");		
	}

	public void saveHTMLConversion(String location) throws IOException {
		saveToLocation(getHTMLText(), location);		
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
	
	public String getHTMLText() {
		if (this.htmlText == null) {
			htmlText = buildHTML();
		}
		return htmlText;
	}
	
	private String buildHTML() {
		StringBuilder html = new StringBuilder();
		html.append(buildHTMLHeader());
		html.append(buildHTMLStanzas());
		html.append(buildHTMLOutro());
		return html.toString();
	}

	private String buildHTMLHeader() {
		StringBuilder header = new StringBuilder();
		header.append(buildHTMLHead());
		header.append(buildHTMLTranspositionMenu());	
		header.append(buildHTMLTitleAndMetadata());
		return header.toString();
	}
	
	private String buildHTMLHead() {
		return "<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>" + currentSong.getTitle()
			+ "</title> <link rel=\"stylesheet\" href=\"..\\SongSupport\\view.css\">"
			+ "<script src=\"..\\SongSupport\\scroll.js\"></script>"
			+ "<meta name=\"keywords\" content=\""+currentSong.getKeyword()+"\">"
			+ "<meta name=\"events\" content=\""+currentSong.getEvent()+"\">"
			+"</head>\n";
	}
	

	private String buildHTMLTranspositionMenu() {
		int delayTime = calculateScrollDelay(currentSong.getTempo());

		// add content to include a "transposition" pull-down menu
		return "<body>"
				+ "<select id=\"xpose\" onchange=\"transpose()\"><option value=\"-5\">-5</option><option value=\"-4\">-4</option>"
				+ "<option value=\"-3\">-3</option><option value=\"-2\">-2</option><option value=\"-1\">-1</option>"
				+ "<option value=\"0\" selected>0</option><option value=\"1\">1</option><option value=\"2\">2</option>"
				+ "<option value=\"3\">3</option><option value=\"4\">4</option><option value=\"5\">5</option>"
				+ "<option value=\"6\">6</option></select> "
				+ "<script>SCROLL_INTERVAL=" + delayTime + ";</script>";
	}
	
	private int calculateScrollDelay(String tempo) {
		int parsedTempo;
		try {
			parsedTempo = Integer.parseInt(tempo);
		} catch (NumberFormatException e) {
			return -1;
		}
		//non-linear approximation of scroll delay time needed
		//   tempo      60, 70, 80, 90,100,110,120,130
		int[] delays = {300,250,210,160,110, 90, 70, 50};
		int tempoIndex = (parsedTempo-60) / 10;   //130=7  60=0
		if (tempoIndex < 0) tempoIndex = 0;
		if (tempoIndex > delays.length-1) tempoIndex = delays.length - 1;
		int delayTime = delays[tempoIndex];
		return delayTime;
	}

	private String buildHTMLTitleAndMetadata() {
		String variant = "master";   //TODO: replace with actual variant data

		StringBuilder titleAndMetadata = new StringBuilder();
		
		titleAndMetadata.append("\n<div class=\"title\">" + currentSong.getTitle() + "<span></span><div class=\"authors\">"
				+ currentSong.getAuthors() + "</div></div>" + "<div class=\"info\">" + "Key: " + currentSong.getChordedIn()
				+ "&nbsp; &nbsp; Capo: " + currentSong.getCapo() + "&nbsp; &nbsp; Tempo:" + currentSong.getTempo()
				+ "&nbsp;&nbsp;" + currentSong.getTimeSignature() + "&nbsp;&nbsp;&nbsp;<i>(Variant: " + variant);
		
		if (currentSong.getTune() != currentSong.getDefaultResponse() && !currentSong.getTune().equals("normal")) {
			titleAndMetadata.append("nbsp;" + currentSong.getTune());
		}

		titleAndMetadata.append(")</i>"); // finish metadata line

		if (currentSong.getMedia() != currentSong.getDefaultResponse() && currentSong.getMedia().length() > 2) {
			titleAndMetadata.append("<br><a href=\"" + currentSong.getMedia() + "\" target=\"_blank\">media link</a>");
		}

		titleAndMetadata.append("</div>");
		return titleAndMetadata.toString();
	}

	private String buildHTMLStanzas () {
		StringBuilder htmlStanzas = new StringBuilder();
		
		htmlStanzas.append("\n<div class='stanzas'>");
		for (String stanzaName : currentSong.getOrder()) {
			htmlStanzas.append(buildHTMLSingleStanza(stanzaName));
		}
		htmlStanzas.append("</div>"); 
		return htmlStanzas.toString();
	}

	private String buildHTMLSingleStanza(String stanzaName) {
		StringBuilder htmlStanza = new StringBuilder();
		Stanza currentStanza = currentSong.getStanza(stanzaName);
		
		htmlStanza.append("\n<div class='stanza'>");

		
		if (currentStanza != null) {
			htmlStanza.append(buildHTMLExistingStanza(currentStanza));
		}
		else {
			htmlStanza.append(buildHTLMissingStanza(stanzaName));
		}
		
		htmlStanza.append("</div>\n\n");
		return htmlStanza.toString();
	}

	private String buildHTMLExistingStanza(Stanza currentStanza) {
		StringBuilder htmlExistingStanza = new StringBuilder();
		List<StanzaLine> stanzaLines = currentStanza.getStanzaLines();
		
		htmlExistingStanza.append("<div class='stanzaName'>"  
				+ currentStanza.getStanzaName() + "</div>\n");

		for (StanzaLine line : stanzaLines) {
			htmlExistingStanza.append( buildHTMLStanzaLine(line.getChordsLine(), line.getLyricsLine()));
		}
		return htmlExistingStanza.toString();
	}
	
	// processes one line of stanza into HTML
	private String buildHTMLStanzaLine(String chords, String lyrics) {
		
		String tempChords = buildHTMLTransposableChord(chords);

		/// Allow for HTML transpose as needed
		String chordline = "\n<div class=\"song_chords\">" + tempChords.replaceAll(" ", "&nbsp;") + "</div>";
		String lyricline = "\n<div class=\"song_lyrics\">" + lyrics.replace("’", "'") + "</div>";

		return chordline + lyricline;
	}

	private String buildHTMLTransposableChord(String chords) {
		StringBuilder tempChords = new StringBuilder();

		for (int index = 0; index < chords.length(); index++) {
			if (chords.charAt(index) != ' ') 
			{
				int offset = 1;
				while (index + offset < chords.length() && chords.charAt(index + offset) != ' ') {
					offset++;
				}
				String curChord = chords.substring(index, index + offset);
				tempChords.append(buildHTMLChordTag(curChord));
				// Prevents us from skipping a character / leaving bounds
				index += offset - 1;
			} else {
				tempChords.append(chords.charAt(index));
			}
		}
		
		return tempChords.toString();
	}
	
	private String buildHTMLChordTag(String chord) {
		String baseChord = chord.substring(0, 1);
		String mod = chord.substring(1); // modifier portion if exists
		
		if (chord.length() > 1 && chord.charAt(1) == '#') { // include sharp symbol in base chord
			baseChord = chord.substring(0, 2);
			mod = chord.substring(2);
		}
		// HTML span needs this base chord as c0, c1, c2,...etc so find index in chords
		// above
		int chordIndex = 0;
		while (chordIndex < 12 && !CHORDS[chordIndex].equals(baseChord)) {
			chordIndex++;
		}
		
		return "<span class=\"c" + chordIndex + "\">" + baseChord + "</span>" + mod;
	}

	private String buildHTLMissingStanza(String stanzaName) {
		return "<h1>?? Missing "  + stanzaName + "</h1>\n";
	}
	
	private String buildHTMLOutro() {
		StringBuilder outro = new StringBuilder();
		String title = currentSong.getTitle();
		String ccli = currentSong.getCCLI();
		
		if (ccli != currentSong.getDefaultResponse()) {
			outro.append("<br><br>ccli:" + ccli + " " + title);
		} else {
			System.out.println ("WARNING: No CCLI value for song:" + title);
		}
		return outro.append("</body></html>").toString();
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

	public static void main(String[] args) throws IOException {	
		
		SongFromTextFile songMaker = new SongFromTextFile();
		SongLyricAndHTMLConverter toHTML = new SongLyricAndHTMLConverter(songMaker.getSong());
		toHTML.saveHTMLConversion();
		toHTML.saveLyricsConversion();		
		
		System.out.println("Conversion complete\n\n");
	}

}