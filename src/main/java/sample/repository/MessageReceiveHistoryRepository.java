package sample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sample.model.MessageReceiveHistory;

@Repository
public interface MessageReceiveHistoryRepository extends JpaRepository<MessageReceiveHistory, Long> {
}
