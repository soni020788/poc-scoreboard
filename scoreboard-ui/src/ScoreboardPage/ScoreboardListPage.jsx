import React from "react";
import {scoreboardService} from "../_services/scoreboard.service";
import SortableTbl from 'react-sort-search-table';

let col = [
    "playerName",
    "gameName",
    "score"
];
let tHead = [
    "Player Name",
    "Game Name",
    "score"
];

export default class ScoreboardListPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            playerName: '',
            gameName: '',
            score: 1,
            submit: false,
            loading: false,
            error: '',
            scoreboardDetails: []
        };
        this.handleChange = this.handleChange.bind(this);
        this.handelSubmit = this.handelSubmit.bind(this);
    }

    componentDidMount() {
        this.getScoreboardDetails();
    }

    getScoreboardDetails() {
        scoreboardService.getScoreboardDetails().then(response => {
            this.setState({
                scoreboardDetails: response.data
            })
        }).catch(function (error) {
            console.log('getScoreboardDetails Error' + error)
        })
    }

    handleChange(e) {
        const {name, value} = e.target;
        this.setState({
            [name]: value,
            loading: false,
            error: ''
        });
    }

    handelSubmit(event) {
        event.preventDefault();
        this.setState({submit: true});
        const {playerName, gameName, score} = this.state;
        if (!(playerName && gameName && score)) {
            return;
        }
        this.setState({loading: true});
        scoreboardService.addScoreboardDetails(playerName, gameName, score).then(response => {
            this.getScoreboardDetails();
        }, error => this.setState({error: error, loading: false})).catch(function (error) {
            console.log('Error in addScoreboardDetails: ' + error);
        })
    }


    render() {
        const {playerName, gameName, score, submit, loading, error} = this.state;
        return (
            <div className="App">
                <div>
                    <h3>
                        SCOREBOARD LIST
                    </h3>
                    <SortableTbl tblData={this.state.scoreboardDetails}
                                 tHead={tHead}
                                 dKey={col}
                                 search={true}
                                 defaultCSS={true}
                    />
                </div>
                <h3>
                    Add scoreboard details
                </h3>
                <h6>
                    Add scoreboard details Page
                </h6>
                <div className={'form-group' + (submit && !playerName ? 'has-error' : '')}>
                    <label className="label-td" htmlFor="playerName">Player Name</label>
                    <input placeholder="Player Name" type="text" name="playerName" value={playerName} required={true}
                           minLength={3} maxLength={255}
                           onChange={this.handleChange}/>
                    {submit && !playerName && <div className="text-danger">Player Name is required!</div>}
                </div>
                <div className={'form-group' + (submit && !gameName ? 'has-error' : '')}>
                    <label className="label-td" htmlFor="gameName">Game Name</label>
                    <input placeholder="Game Name" type="text" name="gameName" value={gameName} required={true}
                           minLength={3} maxLength={150}
                           onChange={this.handleChange}/>
                    {submit && !gameName && <div className="text-danger">Game Name is required!</div>}
                </div>
                <div className={'form-group' + (submit && !score ? 'has-error' : '')}>
                    <label className="label-amount" htmlFor="score">Score </label>
                    <input placeholder="Score" type="number" name="score" value={score}
                           required={true}
                           minLength={1}
                           onChange={this.handleChange}/>
                    {submit && !score &&
                    <div className="text-danger">Score is required, Please enter valid Score!</div>}
                </div>
                <div>
                    <button className="btn btn-primary" onClick={this.handelSubmit} disabled={loading}>Submit
                    </button>
                </div>
                {error && <div className={'bg-danger'}>{error}</div>}
                <h6>Developed by @HL
                </h6>
            </div>
        )
    }
}