package htlstp.diplomarbeit.binobo.repositories;

import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import htlstp.diplomarbeit.binobo.model.Bookmark;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByUser(User user);
    Bookmark findByPost(Post post);
    Bookmark findByPostAndUser(Post post, User user);
    void deleteAllByPost(Post post);
    void deleteAllByUser(User user);
}
