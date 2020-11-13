import React, { useState } from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect
} from 'react-router-dom';
import jwt_decode from 'jwt-decode';

import Test from './Test';
import LogEntries from './LogEntries';
import Profile from './Profile';
import NotFound from './NotFound';
import LogEntry from './LogEntry';
import Login from './Login';
import Register from './Register';
import NavBar from './NavBar';
import AuthContext from './AuthContext';

function App() {
  const [user, setUser] = useState(null);

  const login = (token) => {
    // {
    //   "iss": "calorie-tracker",
    //   "sub": "smashdev5",
    //   "appUserId": 9,
    //   "authorities": "ROLE_USER",
    //   "exp": 1605235902
    // }

    const { appUserId, sub: username, authorities } = jwt_decode(token);

    // Split the authorities into an array of roles.
    const roles = authorities.split(',');

    const user = {
      appUserId,
      username,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };

    console.log(user);

    setUser(user);

    return user;
  };

  const logout = () => {
    setUser(null);
  };

  const authenticate = async (username, password) => {
    const response = await fetch('http://localhost:8080/authenticate', {
      method: 'POST',
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        username,
        password
      })
    });

    if (response.status === 200) {
      const { jwt_token } = await response.json();

      // {
      //   "jwt_token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjYWxvcmllLXRyYWNrZXIiLCJzdWIiOiJzbWFzaGRldjUiLCJhdXRob3JpdGllcyI6IlJPTEVfVVNFUiIsImV4cCI6MTYwNTIzNDczNH0.nwWJtPYhD1WlZA9mGo4n5U0UQ3rEW_kulilO2dEg7jo"
      // }

      return login(jwt_token);
    } else if (response.status === 403) {
      throw new Error('Login failed.');
    } else {
      throw new Error('Unknown error.');
    }
  };

  // Create the auth object
  const auth = {
    user,
    login,
    logout,
    authenticate
  };

  const PrivateRoute = ({ children, ...rest }) => {
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
  
  return (
    <AuthContext.Provider value={auth}>
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
            <Route path="/logentry/:id">
              {auth.user ? (
                <LogEntry />
              ) : (
                <Redirect to="/login" />  
              )}
            </Route>
            <Route path="/profile">
              {auth.user ? (
                <Profile />
              ) : (
                <Redirect to="/login" />  
              )}
            </Route>
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
    </AuthContext.Provider>
  );
}

export default App;
