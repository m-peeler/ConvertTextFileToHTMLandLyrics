package file_converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SongFromTextFile {
	
	private Song song;
	private String textPath;
	
	private static final String PATH = "";
	

	public SongFromTextFile() throws IOException {
		this(requestTextfilePath());
	}
	
	public static String requestTextfilePath() {
		System.out.print("INPUT a text filename and hit enter:");
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String textfilePath = PATH + sc.nextLine();
		if (textfilePath.indexOf(".txt") < 0) {
			textfilePath = textfilePath + ".txt";
		}
		return textfilePath;
	}
	
	public SongFromTextFile(String textfilePath) throws IOException {
		textPath = textfilePath;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(textPath));
			setSong(bufferedReader);
			bufferedReader.close();
		} catch (Exception e) {
			// text file has some problems - punt to caller
			if (e instanceof TextInputException) {
				throw e;
			} else {
				e.printStackTrace();
			}
		}
	}
	
	private void setSong(BufferedReader songText) throws IOException {
		song = new SongImplementation();
		setSongDefaultValues();
		setSongElements(songText);
		setSongStanzas(songText);
	}	
	
	private void setSongDefaultValues() {
		song.setTitle("??");
		song.setCapo("0");
		song.setTune("normal");
		song.setTempo("111");
		song.setTimeSignature("4/4");
	}
	
	private void setSongElements(BufferedReader songText) throws IOException {
		String aline = songText.readLine();

		while (aline.length() > 1) { // while line of file is not the blank
			int firstColon = aline.indexOf(":");
			if (firstColon > 0) {
				setSongElementByName(aline.substring(0, firstColon), 
						aline.substring(firstColon + 1));
			}
			aline = songText.readLine();
		}
	}
	
	private void setSongElementByName(String element, String value) {
		switch (element) {
		case "title":
			song.setTitle(value);
			break;
		case "asPerformedBy":
			song.setAsPerformedBy(value);
			break;
		case "authors":
			song.setAuthors(value);
			break;
		case "tune":
			song.setTune(value);
			break;
		case "order":
			List<String> order = Arrays.asList(value.split(","));
			song.addOrder(order);
			break;
		case "chordedIn":
			song.setChordedIn(value);
			break;
		case "media":
			song.setMedia(value);
			break;
		case "capo": 
			song.setCapo(value);
			break;
		case "ccli":
			song.setCCLI(value);
			break;
		case "tempo":
			song.setTempo(value);
			break;
		case "timesig":
			song.setTimeSignature(value);
			break;
		case "Keyword":
			song.setKeyword(value);
			break;
		case "event":
			song.setEvent(value);
			break;
		default:
			throw new ElementUnexpectedException(element);
		}
	}
	
	private void setSongStanzas(BufferedReader bufferedReader) throws IOException {

		String aline;
		aline = bufferedReader.readLine();
		if (!aline.equals("SONG")) {
			System.out.println("Stanzas should be preceded by SONG keyword"); // echo error message
			throw new TextInputException("Stanzas should be preceded by SONG keyword");
		}
		
		aline = bufferedReader.readLine(); 

		while (aline != null && aline.length() > 0) {
			aline = cleanStanzaName(aline);
			song.addStanza(aline, buildCurrentStanza(bufferedReader, aline));
			aline = bufferedReader.readLine(); // get next stanza
		}
	}

	private String cleanStanzaName(String stanzaName) throws TextInputException {
		if (stanzaName.length() > 8 || stanzaName.indexOf('[') < 0) {
			String errorStatement = "\nUnexpected (long?) stanza name[" + stanzaName
					+ "]\nMake sure all chord lines have something-even blanks \n";
			System.out.println(errorStatement); // echo error message
			throw new TextInputException(errorStatement);
		}
		return stanzaName.replace("[", "")
							 .replace("]", "")
							 .trim();
	}

	private Stanza buildCurrentStanza(BufferedReader bufferedReader,
			String stanzaName) throws IOException, TextInputException {

		List<StanzaLine> stanza = new ArrayList<>();
		String chord = bufferedReader.readLine(); // check for more...

		while (chord != null && chord.length() > 0) {
			if (chord.indexOf('[') >= 0 || Pattern.matches("[H-Z]", chord) == true) {
				String errorMessage = "Stanza:" + stanzaName + "...Chords contain bad characters @" + chord;
				throw new TextInputException(errorMessage);
			}

			String lyric = bufferedReader.readLine();		
			stanza.add(new StanzaLine(chord, lyric));
			chord = bufferedReader.readLine(); // read next
		}
		
		return new Stanza(stanzaName, stanza);
	}
		
	public Song getSong() {
		return song;
	}
	
}
