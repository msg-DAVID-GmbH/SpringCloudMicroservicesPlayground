package mafoe.controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * A row in the book table.
 */
public class BookRow {

    private SimpleStringProperty author;
    private SimpleStringProperty title;


    BookRow(String author, String title) {
        this.author = new SimpleStringProperty(author);
        this.title = new SimpleStringProperty(title);
    }

    public SimpleStringProperty authorProperty() {
        return author;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }
}
