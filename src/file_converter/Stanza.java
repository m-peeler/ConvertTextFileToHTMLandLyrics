package file_converter;

import java.util.List;

public class Stanza {
	
	private List<StanzaLine> stanzaLines;
	private String stanzaName;
	
	public Stanza(String stanzaName, List<StanzaLine> stanzaLines) {
		this.stanzaLines = stanzaLines;
		this.stanzaName = stanzaName;
	}
	
	public List<StanzaLine> getStanzaLines() {
		return stanzaLines;
	}
	
	public String getStanzaName() {
		return stanzaName;
	}
}
