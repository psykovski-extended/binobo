package htlstp.diplomarbeit.binobo.service;

import htlstp.diplomarbeit.binobo.model.Comment;
import htlstp.diplomarbeit.binobo.model.Post;
import htlstp.diplomarbeit.binobo.model.User;
import htlstp.diplomarbeit.binobo.model.Vote;
import htlstp.diplomarbeit.binobo.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Transactional
public class VoteServiceImpl implements VoteService{

    private final VoteRepository voteRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository){
        this.voteRepository = voteRepository;
    }

    @Override
    public List<Vote> findAllByUser(User user) {
        return voteRepository.findAllByUser(user);
    }

    @Override
    public List<Vote> findAllByPost(Post post) {
        return voteRepository.findAllByPost(post);
    }

    @Override
    public List<Vote> findAllByComment(Comment comment) {
        return voteRepository.findAllByComment(comment);
    }

    @Override
    public void deleteAllByPost(Post post) {
        voteRepository.deleteAllByPost(post);
    }

    @Override
    public void deleteAllByUser(User user) {
        voteRepository.deleteAllByUser(user);
    }

    @Override
    public void deleteAllByComment(Comment comment) {
        voteRepository.deleteAllByComment(comment);
    }

    @Override
    public int countAllByPost(Post post) {
        return voteRepository.countAllByPost(post);
    }

    @Override
    public int countAllByUser(User user) {
        return voteRepository.countAllByUser(user);
    }

    @Override
    public int countAllByComment(Comment comment) {
        return voteRepository.countAllByComment(comment);
    }

    @Override
    public int getVoteCountByPost(Post post) {
        Iterable<Vote> votes = findAllByPost(post);
        AtomicInteger voteCount = new AtomicInteger();
        votes.forEach(vote -> voteCount.addAndGet((vote.getIsUseful() ? 1 : -1)));
        return voteCount.get();
    }

    @Override
    public int getVoteCountByComment(Comment comment) {
        Iterable<Vote> votes = findAllByComment(comment);
        AtomicInteger voteCount = new AtomicInteger();
        votes.forEach(vote -> voteCount.addAndGet((vote.getIsUseful() ? 1 : -1)));
        return voteCount.get();
    }

    @Override
    public List<Integer> getAllVoteCountByPosts(List<Post> posts) {
        List<Integer> voteCounts = new ArrayList<>();
        posts.forEach(post -> voteCounts.add(getVoteCountByPost(post)));
        return voteCounts;
    }

    @Override
    public List<Integer> getAllVoteCountByComments(List<Comment> comments) {
        List<Integer> voteCounts = new ArrayList<>();
        comments.forEach(comment -> voteCounts.add(getVoteCountByComment(comment)));
        return voteCounts;
    }

    @Override
    public Vote findByUserAndPost(User user, Post post) {
        return voteRepository.findByUserAndPost(user, post);
    }

    @Override
    public Vote findByUserAndComment(User user, Comment comment) {
        return voteRepository.findByUserAndComment(user, comment);
    }

    @Override
    public void save(Vote vote) {
        voteRepository.save(vote);
    }

    @Override
    public void delete(Vote vote) {
        voteRepository.delete(vote);
    }

    @Override
    public void saveAll(List<Vote> votes) {
        voteRepository.saveAll(votes);
    }
}
