import React, { Component } from 'react';

const ENDPOINT = "http://localhost:8080/spam-protection-team";
const STATE_OPEN = "OPEN";
const STATE_BLOCKED = "BLOCKED";
const STATE_RESOLVED = "RESOLVED";

class SpamReports extends Component {
  constructor(props) {
    super(props);

    this.state = {
      reports: [],
      isLoading: false,
      error: null,
    }

    this.load = function() {
      fetch(ENDPOINT + "/reports/?state=" + STATE_OPEN + "&state=" + STATE_BLOCKED, {
        method: 'GET',
        headers: {'Accept': 'application/json'}})
        .then(response => {
          if (response.ok) {
            return response.json();
          } else {
            console.log(response);
            throw new Error('Something went wrong ...');
          }
        })
        .then(data => this.setState({ reports: data.elements, isLoading: false }))
        .catch(error => this.setState({ error, isLoading: false }));
    }

    this.update = function(id, state) {
      fetch(ENDPOINT + "/reports/:" + id, {
        method: 'PUT',
        headers: {'Content-type': 'application/json'},
        body: JSON.stringify({
          'ticketState' : state
        })})
        .then(response => {
          if (response.ok) {
            this.load();
          }
        })
    }
    
    this.reset = function() {
        fetch(ENDPOINT + "/reports", {
          method: 'PUT',
          headers: {'Content-type': 'application/json'},
          body: JSON.stringify({
            'ticketState' : STATE_OPEN
          })})
          .then(response => {
            if (response.ok) {
              this.load();
            }
          })
      }
  }

  componentDidMount() {
    this.setState({ isLoading: true });
    this.load();
  }

  render() {
    const { reports, isLoading, error } = this.state;

    if (error) {
      return <p>{error.message}</p>;
    }

    if (isLoading) {
      return <p>Loading ...</p>;
    }

    return (
      <div>
        <h2>{"Spam Protection Team - React Version"}</h2>
        <h2><button onClick={() => this.reset()}>Reset</button></h2>
        <table border="1"><tbody>
          {reports.map(report =>
            <tr key={report.id} bgcolor={report.state === STATE_BLOCKED ? "#EEEEEE" : "#FFFFFF"}>
              <td width="300"><a href={ENDPOINT + "/reports/:" + report.id}>{report.id}</a></td>
              <td width="200">{report.payload.reportType}</td>
              <td width="200">{report.payload.message ? report.payload.message : ""}</td>
              <td width="100">{report.state}</td>
              <td width="220">{report.created}</td>
              <td><button style={{width:'100px'}} onClick={() => this.update(report.id, STATE_BLOCKED)}>{report.state === STATE_BLOCKED ? "Unblock" : "Block"}</button></td>
              <td><button style={{width:'100px'}} onClick={() => this.update(report.id, STATE_RESOLVED)}>{"Resolve"}</button></td>
            </tr>
          )}
        </tbody></table>
      </div>
    );
  }
}

export default SpamReports;
