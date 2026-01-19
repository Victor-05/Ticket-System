package command;

public class Comment {
    private String author;
    private String content;
    private String createdAt;
    public Comment(final String author,
                   final String content,
                   final String createdAt) {
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
    }
}
