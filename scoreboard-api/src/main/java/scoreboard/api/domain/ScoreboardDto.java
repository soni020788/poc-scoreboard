package scoreboard.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreboardDto {
    public final String id;
    public final String playerName;
    public final String gameName;
    public final String score;

    @JsonCreator
    public ScoreboardDto(@JsonProperty("id") final String id,
                         @JsonProperty("playerName") final String playerName,
                         @JsonProperty("gameName") final String gameName,
                         @JsonProperty("score") final String score) {
        this.id = id;
        this.playerName = playerName;
        this.gameName = gameName;
        this.score = score;
    }
}
