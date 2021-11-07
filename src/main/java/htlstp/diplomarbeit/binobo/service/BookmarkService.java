package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Bookmark;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;

import java.util.List;

public interface BookmarkService {
    void saveBookmark(Bookmark bookmark);
    void deleteBookmark(Bookmark bookmark);
    List<Bookmark> fetchAllBookmarksFromUser(User user);
    Bookmark findByPost(Post post);
    Bookmark findByPostAndUser(Post post, User user);
    void deleteAllByUser(User user);
    void deleteAllByPost(Post post);
}
