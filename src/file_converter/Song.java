package file_converter;
import java.util.List;

public interface Song  {
		
	public void setPrologue(String prologue);
	
	public String getPrologue();
	
	public void setTitle(String title);
	
	public String getTitle();
	
	public boolean addAuthor(String newAuthor);
	
	public List<String> getAuthors();
	
	public void setTune(String tune);
	
	public String getTune();
	
	public void setAsPerformedBy(String asPerformedBy);
	
	public String getAsPerformedBy();
	
	public void addStanza(String newStanza);
	
	public List<String> getStanzas();
	
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
	
	public void setOrder(String order);
	
	public String getOrder();

}
