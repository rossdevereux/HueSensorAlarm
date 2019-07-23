'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {events: []};
    }

    componentDidMount() {
        client({method: 'GET', path: '/api/events'}).done(response => {
            this.setState({events: response.entity._embedded.events});
        });
    }

    render() {
        return (
            <EventList events={this.state.events}/>
        )
    }
}

class EventList extends React.Component{
    render() {
        var events = this.props.events.map(event =>
            <Event key={event._links.self.href} event={event}/>
        );
        return (
            <table>
                <tbody>
                <tr>
                    <th>Time</th>
                    <th>Type</th>
                    <th>Location</th>
                    <th>Notes</th>
                </tr>
                {events}
                </tbody>
            </table>
        )
    }
}

class Event extends React.Component{
    render() {
        return (
            <tr>
                <td>{this.props.event.createdAt}</td>
                <td>{this.props.event.eventType}</td>
                <td>{this.props.event.location}</td>
                <td>{this.props.event.notes}</td>
            </tr>
        )
    }
}

ReactDOM.render(
    <App />, document.getElementById('react')
);