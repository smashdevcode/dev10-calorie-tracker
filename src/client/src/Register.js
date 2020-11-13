import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';

import { useAuth } from './auth.js';
import Errors from './Errors';

export default function Register() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState([]);

  const history = useHistory();
  const auth = useAuth();

  const handleSubmit = async (event) => {
    event.preventDefault();

    // POST http://localhost:8080/create_account HTTP/1.1
    // Content-Type: application/json
    
    // {
    //     "username": "smashdev5",
    //     "password": "Asdff88f67!"
    // }
    
    const response = await fetch('http://localhost:8080/create_account', {
      method: 'POST',
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        username,
        password
      })
    });

    if (response.status === 201) {
      try {
        await auth.authenticate(username, password);
        history.push('/');  
      } catch (err) {
        setErrors([err.message]);
      }
    } else if (response.status === 400) {
      const errors = await response.json();
      setErrors(errors);
    } else {
      setErrors(['Unknown error.']);
    }    
  };

  return (
    <div>
      <h2>Register</h2>
      <Errors errors={errors} />
      <form onSubmit={handleSubmit}>
        <div>
          <label>Username:</label>
          <input type="text" onChange={(event) => setUsername(event.target.value)} />
        </div>
        <div>
          <label>Password:</label>
          <input type="password" onChange={(event) => setPassword(event.target.value)} />
        </div>
        <div>
          <button type="submit">Register</button>
          <Link to="/login">I already have an account</Link>
        </div>
      </form>
    </div>
  );
}
