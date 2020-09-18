/*
    File: DataBank.java

    This is the model class
 */
package termbank.model;

public class DataBank {

    /* String properties that will be observed by TableView */
    private String category;
    private String term;
    private String definition;

    public DataBank(String category, String term, String definition) {
       this.category = category;
       this.term = term;
       this.definition = definition;
    }

    public DataBank() {
        this(null, null, null);
    }

    /* Setter methods: */
    public void setCategory(String category) {
        this.category = category;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    /* Getter methods */
    public String getCategory() {
        return this.category;
    }

    public String getTerm() {
        return this.term;
    }

    public String getDefinition() {
        return this.definition;
    }
}
