package models;

import java.util.Objects;

public class Article {
    private String content;
    private String writtenBy;
    private int rating;
    private int id;
    private int restaurantId; //will be used to connect Department to Article (one-to-many)

    public Article(String content, String writtenBy, int rating, int restaurantId) {
        this.content = content;
        this.writtenBy = writtenBy;
        this.rating = rating;
        this.restaurantId = restaurantId;
    }

    public String getContent() {
        return content;
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public int getRating() {
        return rating;
    }

    public int getId() {
        return id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return rating == article.rating &&
                id == article.id &&
                restaurantId == article.restaurantId &&
                Objects.equals(content, article.content) &&
                Objects.equals(writtenBy, article.writtenBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, writtenBy, rating, id, restaurantId);
    }
}
