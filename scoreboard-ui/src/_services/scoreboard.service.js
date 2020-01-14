import axios from 'axios'
import {apiUrl} from "../_helpers/api.url";
import {Redirect} from 'react-router-dom';
import React from "react";

export const scoreboardService = {
    getScoreboardDetails,
    addScoreboardDetails
};

const request = axios.create({
    withCredentials: true,
    baseURL: apiUrl.getBaseApiUrl()
});

function getScoreboardDetails() {
    return request.get('/rest/scoreboard/details').then().catch(function (error) {
        console.log('getScoreboardDetails error: ' + error);
    })
}

function addScoreboardDetails(playerName, gameName, score) {
    return request.post('/rest/scoreboard/details',{
        playerName: playerName,
        gameName: gameName,
        score: score
    }).then().catch(function (error) {
        if (error.response.status === 400) {
            return Promise.reject(error.response.data);
        } else {
            return Promise.reject(<Redirect to='/networkError'/>);
        }
    })
}