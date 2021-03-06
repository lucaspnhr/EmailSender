package com.github.manojesus.messagesender.repository;

import com.github.manojesus.messagesender.model.UnreadEmailStats;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnreadEmailStatsRepository extends CassandraRepository<UnreadEmailStats, String> {
    List<UnreadEmailStats> findAllByUserId(String userId);

    @Query("update unread_emails_stats set unread = unread + 1 where user_id = ?0 and label_name = ?1")
    void incrementCounter(String userId, String labelName);
    @Query("update unread_emails_stats set unread = unread - 1 where user_id = ?0 and label_name = ?1")
    void decrementCounter(String userId, String labelName);

}
