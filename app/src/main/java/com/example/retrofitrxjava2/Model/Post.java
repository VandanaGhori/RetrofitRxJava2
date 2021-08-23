package com.example.retrofitrxjava2.Model;

public class Post
{
    public int userId;
    public int id;
    public String title;
    public String body;

    // Empty Constructor
    public Post() {}

    // Parameterized Constructor
    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }
}
