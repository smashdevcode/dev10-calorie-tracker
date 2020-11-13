import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect
} from 'react-router-dom';

import Test from './Test';
import LogEntries from './LogEntries';
import Profile from './Profile';
import NotFound from './NotFound';
import LogEntry from './LogEntry';
import Login from './Login';
import Register from './Register';
import NavBar from './NavBar';

import { ProvideAuth, useAuth } from './auth.js';

function PrivateRoute({ children, ...rest }) {
  const auth = useAuth();
  return (
    <Route
      {...rest}
      render={({ location }) =>
        auth.user ? (
          children
        ) : (
          <Redirect
            to={{
              pathname: '/login',
              state: { from: location }
            }}
          />
        )
      }
    />
  );
}

function App() {
  return (
    <ProvideAuth>
      <Router>
        <div>
          <h1>Calorie Tracker</h1>

          <NavBar />

          <hr />

          <Switch>
            <Route exact path="/">
              <Test headingText="Home" />
            </Route>
            <Route path="/logentries">
              <LogEntries />
            </Route>
            <PrivateRoute path="/logentry/:id">
              <LogEntry />
            </PrivateRoute>
            <PrivateRoute path="/profile">
              <Profile />
            </PrivateRoute>
            <Route path="/login">
              <Login />
            </Route>
            <Route path="/register">
              <Register />
            </Route>
            <Route path="*">
              <NotFound />
            </Route>
          </Switch>
        </div>
      </Router>
    </ProvideAuth>
  );
}

export default App;
