import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link
} from 'react-router-dom';

import Test from './Test';
import LogEntries from './LogEntries';
import Profile from './Profile';
import NotFound from './NotFound';
import LogEntry from './LogEntry';

function App() {
  return (
    <Router>
      <div>
        <h1>Calorie Tracker</h1>

        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/logentries">Log Entries</Link>
          </li>
          <li>
            <Link to="/profile">Profile</Link>
          </li>
        </ul>

        <hr />

        <Switch>
          <Route exact path="/">
            <Test headingText="Home" />
          </Route>
          <Route path="/logentries">
            <LogEntries />
          </Route>
          <Route path="/logentry/:id">
            <LogEntry />
          </Route>
          <Route path="/profile">
            <Profile />
          </Route>
          <Route path="*">
            <NotFound />
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
