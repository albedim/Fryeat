package me.albedim.fryeat.model.repository;

import me.albedim.fryeat.model.entity.Poll;
import org.springframework.data.repository.CrudRepository;

/**
 * @author: albedim <dimaio.albe@gmail.com>
 * Created on: 09/01/23
 * Created at: 19:38
 * Version: 1.0.0
 * Description: This is the class for the poll repository
 */

public interface PollRepository extends CrudRepository<Poll, Long>
{
    @Override
    <S extends Poll> S save(S entity);
}
