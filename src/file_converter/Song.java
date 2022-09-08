package file_converter;
import java.util.List;
import java.util.Map;

public interface Song  {
	
	public void setTitle(String title);
	
	public String getTitle();
	
	public void setAuthors(String authors);
	
	public String getAuthors();
		
	public void setTune(String tune);
	
	public String getTune();
	
	public void setAsPerformedBy(String asPerformedBy);
	
	public String getAsPerformedBy();
	
	public Map<String, Stanza> clearStanzas();
	
	public Stanza removeStanza(String stanzaName);
	
	public void addStanza(String stanzaName, Stanza newStanza);
	
	public Stanza getStanza(String stanzaName);
	
	public void setMedia(String media);
	
	public String getMedia();
	
	public void setChordedIn(String chordedIn);
	
	public String getChordedIn();
	
	public void setTimeSignature(String timeSig);
	
	public String getTimeSignature();
	
	public void setCapo(String capo);
	
	public String getCapo();
	
	public void setTempo(String tempo);
	
	public String getTempo();
	
	public void setKeyword(String keyword);
	
	public String getKeyword();
	
	public void setEvent(String event);
	
	public String getEvent();
	
	public void setVariant(String variant);
	
	public String getVariant();
	
	public void setCCLI(String CCLI);
	
	public String getCCLI();
	
	public List<String> clearOrder();
	
	public void addOrder(List<String> stanzas);
	
	public void addOrder(String stanza);
	
	public List<String> getOrder();
	
	public String defaultResponseIfNull(String text);
	
	public String getDefaultResponse();

}
