/*
 * File: DataBank.java
 * 
 * This is the model class
 */

package termbank.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class DataBank {

	/* String properties that will be observed by TableView */
	private StringProperty term = new SimpleStringProperty("");
	private StringProperty definition = new SimpleStringProperty("");
	private StringProperty group = new SimpleStringProperty("");
	
	public DataBank(String term, String definition, String group) {
		setTerm(term);
		setDefinition(definition);
		setGroup(group);
	}
	
	public DataBank() {
		
	}
	
	public void setTerm(String value) {
		termProperty().set(value);
	}
	
	public String getTerm() {
		return term.get();
	}
	
	public StringProperty termProperty() {
		if(term == null) 
			term = new SimpleStringProperty(this, "term");
		return term;
	}
	
	public void setDefinition(String value) {
		definitionProperty().set(value);
	}
	
	public String getDefinition() {
		return definition.get();
	}
	
	public StringProperty definitionProperty() {
		if(definition == null)
			definition = new SimpleStringProperty(this, "definition");
		return definition;
	}
	
	public void setGroup(String value) {
		groupProperty().set(value);
	}
	
	public String getgroup() {
		return group.get();
	}
	
	public StringProperty groupProperty() {
		if(group == null)
			group = new SimpleStringProperty(this, "group");
		return group;
	}
	
}



















