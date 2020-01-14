import React from "react";
import {Route, Switch, BrowserRouter as Router} from 'react-router-dom'
import NetworkErrorPage from "../ErrorPage/NetworkErrorPage";
import NotFoundPage from "../ErrorPage/NotFoundPage";
import './App.css';
import './ScoreboardUI.css'
import ScoreboardPage from "../ScoreboardPage/ScroreboardPage";

class App extends React.Component {
    render() {
        return (
            <div>
                <Router>
                    <Switch>
                        <Route default path="/scoreboard" component={ScoreboardPage}/>
                        <Route path="/networkError" component={NetworkErrorPage}/>
                        <Route path="/*" component={NotFoundPage}/>
                    </Switch>
                </Router>
            </div>
        )
    }
}

export {App}