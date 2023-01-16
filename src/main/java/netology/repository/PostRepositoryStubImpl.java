package netology.repository;


import netology.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {

    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong count = new AtomicLong(1);

    @Override
    public List<Post> all() {
        List<Post> postList = new ArrayList<>();

        for (long k :
                posts.keySet()) {
            postList.add(posts.get(k));
        }

        return postList;
    }

    @Override
    public Optional<Post> getById(long id) {
        System.out.println("ID " + id);
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public Post save(Post post) {
        long id;
        if (post.getId() == 0) {
            id = count.getAndIncrement();
            post.setId(id);
            posts.put(id, post);
        } else {
            if (post.getId() > count.get()) {
                //todo отпарвить сообщение клиенту, что пост сохранен с другим id
                id = count.getAndIncrement();
                post.setId(id);
            } else {
                id = post.getId();
            }
            posts.put(id, post);
        }
        return post;
    }

    @Override
    public void removeById(long id) {
        Post post = posts.get(id);
        if (post != null) {
            posts.remove(id);
        }
    }
}
