package scoreboard.api.dao;

import scoreboard.api.db.ApiDataSource;
import scoreboard.api.domain.ScoreboardDto;

import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardDaoImpl {

    @Inject
    ApiDataSource apiDataSource;

    public List<ScoreboardDto> getScoreboardList() {
        System.out.println("getScoreboardList called in dao");
        List<ScoreboardDto> scoreboardDtos = new ArrayList<>();
        String sql = "SELECT id, player_name, game_name, score FROM scoreboard.player_scoreboard order by score desc";
        try (Connection con = apiDataSource.getDbConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                scoreboardDtos.add(new ScoreboardDto(String.valueOf(rs.getInt("id")), rs.getString("player_name"),
                        rs.getString("game_name"), rs.getString("score")));
            }

        } catch (SQLException ex) {
            System.out.println("getScoreboardList Called ex dao : " + ex);
            throw new WebApplicationException("Unable to getScoreboardList: " + ex);
        }
        return scoreboardDtos;
    }

    public int addScoreboardDtls(ScoreboardDto scoreboardDto) {
        System.out.println("addScoreboardDtls called in dao");
        ResultSet rs;
        int generatedId = 0;
        String sql = "INSERT INTO scoreboard.player_scoreboard(id, player_name, game_name, score) VALUES(DEFAULT, ?, ?, ?);";
        try (Connection connection = apiDataSource.getDbConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, scoreboardDto.playerName);
            statement.setString(2, scoreboardDto.gameName);
            statement.setString(3, scoreboardDto.score);
            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("addScoreboardDtls Called ex dao : " + ex);
            throw new WebApplicationException("Unable to addScoreboardDtls: " + ex);
        }
        return generatedId;
    }
}
