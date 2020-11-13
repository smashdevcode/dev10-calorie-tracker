import React, { useState, useContext, createContext } from 'react';
import jwt_decode from 'jwt-decode';

const authContext = createContext();

// Provider component that wraps your app and makes auth object ...
// ... available to any child component that calls useAuth().
export function ProvideAuth({ children }) {
  const auth = useProvideAuth();
  return <authContext.Provider value={auth}>{children}</authContext.Provider>;
}

// Hook for child components to get the auth object ...
// ... and re-render when it changes.
export const useAuth = () => {
  return useContext(authContext);
};

// Provider hook that creates auth object and handles state
function useProvideAuth() {
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

  // Return the user object and auth methods
  return {
    user,
    login,
    logout,
    authenticate
  };
}
