import React from "react";
export default class NetworkErrorPAge extends React.Component {
    render() {
        return (
            <div>
                <h1>Oops! Seems Network error when to fetch resource..</h1>
                <p>
                    It looks like REST API service is down!.
                </p>
            </div>
        )
    }
}