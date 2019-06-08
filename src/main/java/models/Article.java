package models;

import java.util.Objects;

public class Article {
    private String content;
    private int id;
    private int departmentId;

    public Article(String content,int departmentId) {
        this.content = content;
        this.departmentId = departmentId;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return getId() == article.getId() &&
                getDepartmentId() == article.getDepartmentId() &&
                getContent().equals(article.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getContent(), getId(), getDepartmentId());
    }
}
