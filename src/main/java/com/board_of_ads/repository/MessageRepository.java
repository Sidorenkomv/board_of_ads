package com.board_of_ads.repository;

import com.board_of_ads.models.Message;
import com.board_of_ads.models.dto.MessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
//    List<MessageDto> getAllByAuthor_Id(Long id);
//    List<MessageDto> getMessagesByAuthor_Id(Long id);
//    List<MessageDto> getMessageById(Long id);
    @Query("select new com.board_of_ads.models.dto.MessageDto(p.id, p.text, p.author, p.posting) from Message p where p.author.id = :user_id")
    List<MessageDto> findAllUserMessages(@Param("user_id") Long id);
}