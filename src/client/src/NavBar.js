import React, { useContext } from 'react';
import { Link } from 'react-router-dom';

import AuthContext from './AuthContext';

export default function NavBar() {
  const auth = useContext(AuthContext);

  return (
    <div>
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
        {!auth.user && (
          <>
            <li>
              <Link to="/login">Login</Link>
            </li>
            <li>
              <Link to="/register">Register</Link>
            </li>
          </>
        )}
      </ul>
      {auth.user && (
        <div>
          <p>
            Hello {auth.user.username}!
            [Is User: {auth.user.hasRole('ROLE_USER') ? 'Yes' : 'No'},
            Is Admin: {auth.user.hasRole('ROLE_ADMIN') ? 'Yes' : 'No'}]
          </p>
          <button onClick={() => auth.logout()}>Logout</button>
        </div>
      )}
    </div>
  );
}
