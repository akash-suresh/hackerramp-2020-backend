package com.myntra.critico.repository;

import com.myntra.critico.model.LeaderBoardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Akash Suresh on 7/16/20
 */

@Repository
public interface LeaderBoardRepository extends JpaRepository<LeaderBoardEntry, Long> {
}
