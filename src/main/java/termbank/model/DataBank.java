/*
    File: DataBank.java

    This is the model class
 */
package termbank.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataBank {

    /* String properties that will be observed by TableView */
    private StringProperty group = new SimpleStringProperty("");     // A group associated with term & group
    private StringProperty term = new SimpleStringProperty("");      // A term that the user will enter
    private StringProperty definition = new SimpleStringProperty("");// A definition that pairs with the term


    public DataBank(String group, String term, String definition) {
        setGroup(group);
        setTerm(term);
        setDefinition(definition);

    }

    public DataBank() {
        this(null, null, null);
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

    public String getGroup() {
        return group.get();
    }

    public StringProperty groupProperty() {
        if(group == null)
            group = new SimpleStringProperty(this, "group");
        return group;
    }

}
