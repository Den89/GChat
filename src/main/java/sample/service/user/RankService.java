package sample.service.user;

import sample.model.Rank;

import java.util.Optional;

public interface RankService {
    Optional<Rank> getRank(String userName, String hash);
}
