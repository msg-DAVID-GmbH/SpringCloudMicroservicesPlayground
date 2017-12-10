package mafoe.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mafoe.config.SpringContext;
import mafoe.dto.BookDto;
import mafoe.service.BookService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Java FX Controller for showing a table of authors and books.
 */
public class ShowBooksController implements Initializable {

    @FXML
    private TableView<BookRow> table;
    @FXML
    public TableColumn<BookRow, String> colAuthor;
    @FXML
    public TableColumn<BookRow, String> colTitle;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        BookService bookService = SpringContext.getContext().getBean(BookService.class);
        List<BookDto> allBooks = bookService.loadAllBooks();

        table.setItems(FXCollections.observableArrayList(allBooks.stream()
                .map(dto -> new BookRow(dto.getAuthor(), dto.getTitle()))
                .collect(Collectors.toList())));
    }

}
