package com.shabdhasethu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shabdhasethu.entity.TranslationHistory;
import com.shabdhasethu.entity.User;

public interface TranslationHistoryRepository extends JpaRepository<TranslationHistory, Long> {

    List<TranslationHistory> findByUserOrderByTranslatedAtDesc(User user);

    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("DELETE FROM TranslationHistory t WHERE t.user = :user")
    int deleteByUser(User user);

}