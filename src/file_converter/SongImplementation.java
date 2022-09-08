package file_converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SongImplementation implements Song {

	private String title;
	private String authors;
	private String tune;
	private String performedBy;
	private Map<String, Stanza> stanzas;
	private List<String> order;
	private String media;
	private String chord;
	private String timeSignature;
	private String capo;
	private String tempo;
	private String keyword;
	private String event;
	private String variant;
	private String ccli;
	
	private static final String DEFAULT = "Unknown";
	
	public SongImplementation() {
		stanzas = new HashMap<>();
		order = new ArrayList<>();
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getTitle() {
		return defaultResponseIfNull(title);
	}

	@Override
	public void setAuthors(String authors) {
		this.authors = authors;
	}

	@Override
	public String getAuthors() {
		return defaultResponseIfNull(authors);
	}

	@Override
	public void setTune(String tune) {
		this.tune = tune;
	}

	@Override
	public String getTune() {
		return defaultResponseIfNull(tune);
	}

	@Override
	public void setAsPerformedBy(String asPerformedBy) {
		this.performedBy = asPerformedBy;
	}

	@Override
	public String getAsPerformedBy() {
		return defaultResponseIfNull(performedBy);
	}

	@Override
	public Map<String, Stanza> clearStanzas() {
		Map<String, Stanza> oldStanzas = stanzas;
		stanzas.clear();
		return oldStanzas;
	}

	@Override
	public Stanza removeStanza(String stanzaName) {
		return stanzas.remove(stanzaName);
	}

	@Override
	public void addStanza(String stanzaName, Stanza newStanza) {
		stanzas.put(stanzaName, newStanza);
	}

	@Override
	public Stanza getStanza(String stanzaName) {
		return stanzas.get(stanzaName);
	}
	
	@Override
	public List<String> clearOrder() {
		List<String> oldOrder = order;
		order.clear();
		return oldOrder;
	}

	@Override
	public void addOrder(List<String> stanzas) {
		order.addAll(stanzas);
	}

	@Override
	public void addOrder(String stanza) {
		// TODO Auto-generated method stub
		order.add(stanza);
	}

	@Override
	public List<String> getOrder() {
		return order;
	}

	@Override
	public void setMedia(String media) {
		this.media = defaultResponseIfNull(media);
	}

	@Override
	public String getMedia() {
		return defaultResponseIfNull(media);
	}

	@Override
	public void setChordedIn(String chordedIn) {
		this.chord = chordedIn;
	}

	@Override
	public String getChordedIn() {
		return defaultResponseIfNull(chord);
	}

	@Override
	public void setTimeSignature(String timeSig) {
		this.timeSignature = timeSig;
	}

	@Override
	public String getTimeSignature() {
		return defaultResponseIfNull(timeSignature);
	}

	@Override
	public void setCapo(String capo) {
		this.capo = capo;
	}

	@Override
	public String getCapo() {
		return defaultResponseIfNull(capo);
	}

	@Override
	public void setTempo(String tempo) {
		this.tempo = tempo;
	}

	@Override
	public String getTempo() {
		return defaultResponseIfNull(tempo);
	}

	@Override
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String getKeyword() {
		return defaultResponseIfNull(keyword);
	}

	@Override
	public void setEvent(String event) {
		this.event = event;
	}

	@Override
	public String getEvent() {
		return defaultResponseIfNull(event);
	}

	@Override
	public void setVariant(String variant) {
		this.variant = variant;
	}

	@Override
	public String getVariant() {
		return defaultResponseIfNull(variant);
	}

	@Override
	public void setCCLI(String ccli) {
		this.ccli = ccli;
	}

	@Override
	public String getCCLI() {
		return defaultResponseIfNull(ccli);
	}
	
	@Override
	public String defaultResponseIfNull(String text) {
		return text != null ? text : DEFAULT;
	}
	
	@Override
	public String getDefaultResponse() {
		return DEFAULT;
	}

}
