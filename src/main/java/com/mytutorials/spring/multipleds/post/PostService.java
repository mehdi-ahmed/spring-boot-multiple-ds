package com.mytutorials.spring.multipleds.post;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final JdbcClient jdbcClient;

    public PostService(@Qualifier("blogJdbcClient") JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Post> findAll() {

        return jdbcClient.sql("SELECT id,title,slug,date,time_to_read,tags FROM post")
                .query(Post.class)
                .list();
    }

    public Optional<Post> findById(String id) {

        return jdbcClient.sql("SELECT id,title,slug,date,time_to_read,tags FROM post WHERE id = :id")
                .param("id", id)
                .query(Post.class)
                .optional();
    }

    public int create(Post post) {
        return jdbcClient.sql("INSERT INTO post(id,title,slug,date,time_to_read,tags) VALUES(?,?,?,?,?,?)")
                .params(List.of(post.id(), post.title(), post.slug(), post.date(), post.timeToRead(), post.tags()))
                .update();
    }

    public int update(Post post, String id) {
        return jdbcClient.sql("UPDATE post set title = ?, slug = ?, date = ?, time_to_read = ?, tags = ? WHERE id = ?")
                .params(List.of(post.title(), post.slug(), post.date(), post.timeToRead(), post.tags(), id))
                .update();
    }

    public int delete(String id) {
        return jdbcClient.sql("DELETE FROM post WHERE id = :id")
                .update();
    }
}
