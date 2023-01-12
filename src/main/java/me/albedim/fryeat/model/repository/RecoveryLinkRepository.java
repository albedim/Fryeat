package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.RecoveryLink;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 12/01/23
 * Created at: 17:14
 * Version: 1.0.0
 * Description: This is the class for the recovery link repository
 */

public interface RecoveryLinkRepository extends CrudRepository<RecoveryLink, Long>
{
    @Override
    <S extends RecoveryLink> S save(S entity);

    @Query(value = "SELECT * FROM recovery_links WHERE link = :link", nativeQuery = true)
    RecoveryLink get(@Param("link") String link);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM recovery_links WHERE user_id = :userId", nativeQuery = true)
    void delete(@Param("userId") Long userId);

}
