package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Bookmark;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.repositories.BookmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Autowired
    public BookmarkServiceImpl(BookmarkRepository bookmarkRepository){
        this.bookmarkRepository = bookmarkRepository;
    }

    @Override
    public void saveBookmark(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    @Override
    public void deleteBookmark(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }

    @Override
    public List<Bookmark> fetchAllBookmarksFromUser(User user) {
        return bookmarkRepository.findAllByUser(user);
    }

    @Override
    public Bookmark findByPost(Post post) {
        return bookmarkRepository.findByPost(post);
    }

    @Override
    public Bookmark findByPostAndUser(Post post, User user) {
        return bookmarkRepository.findByPostAndUser(post, user);
    }

    @Override
    public void deleteAllByUser(User user) {
        bookmarkRepository.deleteAllByUser(user);
    }

    @Override
    public void deleteAllByPost(Post post) {
        bookmarkRepository.deleteAllByPost(post);
    }
}
