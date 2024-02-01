package com.mytutorials.spring.multipleds;

import com.mytutorials.spring.multipleds.post.Post;
import com.mytutorials.spring.multipleds.post.PostService;
import com.mytutorials.spring.multipleds.subcriber.Subscriber;
import com.mytutorials.spring.multipleds.subcriber.SubscriberService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class MultipleDsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultipleDsApplication.class, args);
    }

    @Bean
    JdbcClient subscriberJdbcClient(@Qualifier("subscriberDataSource") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }

    @Bean
    JdbcClient blogJdbcClient(@Qualifier("blogDataSource") DataSource dataSource) {
        return JdbcClient.create(dataSource);
    }

    @Bean
    CommandLineRunner commandLineRunner(PostService postService, SubscriberService subscriberService) {
        return args -> {
            List<Post> posts = postService.findAll();
            System.out.println("posts = " + posts);


            List<Subscriber> subscribers = subscriberService.findAll();
            System.out.println("subscribers = " + subscribers);
        };
    }

    @Bean
    CommandLineRunner dsCommandLineRunner(@Qualifier("blogDataSource") DataSource blogDataSource,
                                          @Qualifier("subscriberDataSource") DataSource subscriberDataSource) {
        return args -> {
            System.out.println(blogDataSource.getConnection().getMetaData().getURL()); // jdbc:h2:mem:blog
            System.out.println(subscriberDataSource.getConnection().getMetaData().getURL()); // jdbc:h2:mem:blog
        };
    }

}
