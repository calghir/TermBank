/*
    File: DataBank.java

    This is the model class
 */
package termbank.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataBank {

    /* String properties that will be observed by TableView */
    private StringProperty category = new SimpleStringProperty("");
    private StringProperty term = new SimpleStringProperty("");
    private StringProperty definition = new SimpleStringProperty("");


    public DataBank(String category, String term, String definition) {
        setCategory(category);
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

    public void setCategory(String value) {
        categoryProperty().set(value);
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        if(category == null)
            category = new SimpleStringProperty(this, "category");
        return category;
    }

}
