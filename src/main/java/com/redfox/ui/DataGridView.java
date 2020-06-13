package com.redfox.ui;

import com.redfox.domain.Book;
import com.redfox.domain.Genre;
import com.redfox.services.SearchService;
import javax.enterprise.context.SessionScoped;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Named
@SessionScoped
public class DataGridView implements Serializable {

    private List<Book> allBooks;

    private List<Book> books;

    private Book selectedBook;

    private String searchCriteria;

    private String titleCriteria;

    private String authorsCriteria;

    private String isbnCriteria;

    private List<Genre> genres;

    private List<String> selectedGenresIds;

    private Boolean extendedSearch;

    private BigDecimal existingMinPrice;

    private BigDecimal existingMaxPrice;

    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private String sortMode;

    @Inject
    private SearchService searchService;

    @PostConstruct
    public void init() {
        allBooks = searchService.fetchAllBooks();
        books = allBooks;
        extendedSearch = false;
        genres = searchService.fetchAllRelevantGenres();
        existingMinPrice = allBooks.stream()
                .map(Book::getPrice)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        minPrice = existingMinPrice;
        existingMaxPrice = allBooks.stream()
                .map(Book::getPrice)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        maxPrice = existingMaxPrice;
    }

    public void findBooksByCriteria() {
        if (extendedSearch) {
            if ((titleCriteria != null && !titleCriteria.isEmpty())
                    || (authorsCriteria != null && !authorsCriteria.isEmpty())
                    || (isbnCriteria != null && !isbnCriteria.isEmpty())
                    || (selectedGenresIds != null && !selectedGenresIds.isEmpty())
                    || !minPrice.equals(existingMinPrice)
                    || !maxPrice.equals(existingMaxPrice)) {
                books = searchService.findByExtendedCriteria(titleCriteria, authorsCriteria, isbnCriteria, selectedGenresIds, minPrice, maxPrice);
            } else {
                books = allBooks;
            }
        } else {
            if (searchCriteria != null && !searchCriteria.isEmpty()) {
                books = searchService.findByCriteria(searchCriteria);
            } else {
                books = allBooks;
            }
        }

    }

    public void clearForm() {
        books = allBooks;
        searchCriteria = "";
        titleCriteria = "";
        authorsCriteria = "";
        isbnCriteria = "";
        selectedGenresIds = new ArrayList<>();
        minPrice = existingMinPrice;
        maxPrice = existingMaxPrice;
    }

    public void sortBooks() {
        if (sortMode != null && !sortMode.isEmpty()) {
            switch (sortMode) {
                case "title" : books.sort(Comparator.comparing(Book::getTitle));
                    break;
                case "ascending price" : books.sort(Comparator.comparing(Book::getPrice));
                    break;
                case "descending price" : books.sort(Comparator.comparing(Book::getPrice).reversed());
                    break;
            }
        }
    }

    public void isExtendedSearch(Boolean b) {
        extendedSearch = b;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<Book> getAllBooks() {
        return allBooks;
    }

    public void setAllBooks(List<Book> allBooks) {
        this.allBooks = allBooks;
    }

    public Boolean getExtendedSearch() {
        return extendedSearch;
    }

    public void setExtendedSearch(Boolean extendedSearch) {
        this.extendedSearch = extendedSearch;
    }

    public String getTitleCriteria() {
        return titleCriteria;
    }

    public void setTitleCriteria(String titleCriteria) {
        this.titleCriteria = titleCriteria;
    }

    public String getAuthorsCriteria() {
        return authorsCriteria;
    }

    public void setAuthorsCriteria(String authorsCriteria) {
        this.authorsCriteria = authorsCriteria;
    }

    public String getIsbnCriteria() {
        return isbnCriteria;
    }

    public void setIsbnCriteria(String isbnCriteria) {
        this.isbnCriteria = isbnCriteria;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<String> getSelectedGenresIds() {
        return selectedGenresIds;
    }

    public void setSelectedGenresIds(List<String> selectedGenresIds) {
        this.selectedGenresIds = selectedGenresIds;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getExistingMinPrice() {
        return existingMinPrice;
    }

    public void setExistingMinPrice(BigDecimal existingMinPrice) {
        this.existingMinPrice = existingMinPrice;
    }

    public BigDecimal getExistingMaxPrice() {
        return existingMaxPrice;
    }

    public void setExistingMaxPrice(BigDecimal existingMaxPrice) {
        this.existingMaxPrice = existingMaxPrice;
    }

    public String getSortMode() {
        return sortMode;
    }

    public void setSortMode(String sortMode) {
        this.sortMode = sortMode;
    }
}
