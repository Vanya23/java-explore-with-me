package ru.practicum.ewm.controller.pub.comments;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.controller.pub.comments.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {


    void deleteCommentByUser_Id(Long userId);

    void deleteCommentByEvent_Id(Long eventId);

    Page<Comment> findByUserInAndEvent_IdIn(List<Long> users, List<Long> events, Pageable pageable);
}