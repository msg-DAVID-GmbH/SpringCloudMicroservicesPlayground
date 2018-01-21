package mafoe.repository.struct;

/**
 * POJO Model for Freemarker representing a book.
 */
public class FreemarkerBook {

    private String author;
    private String title;

    public FreemarkerBook(String author, String title) {
        this.author = author;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }
}
