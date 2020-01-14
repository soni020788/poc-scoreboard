package scoreboard.api.service;

import scoreboard.api.dao.ScoreboardDaoImpl;
import scoreboard.api.domain.ScoreboardDto;

import javax.inject.Inject;
import java.util.List;

public class ScoreboardService {

    @Inject
    ScoreboardDaoImpl scoreboardDao;

    public List<ScoreboardDto> getScoreboardDetails() {
        return scoreboardDao.getScoreboardList();
    }

    public int addScoreboardDetails(ScoreboardDto scoreboardDto) {
        return scoreboardDao.addScoreboardDtls(scoreboardDto);
    }
}
