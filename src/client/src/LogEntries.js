import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

function LogEntries() {
  const [entries, setEntries] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const result = await fetch('http://localhost:8080/logentries');
      const data = await result.json();
      setEntries(data);  
    };
    fetchData();
  }, []);

  return (
    <div>
      <h2>Log Entries</h2>
      <ul>
        {entries.map(entry => (
          <li key={entry.id}>{entry.description} <Link to={`/logentry/${entry.id}`}>Edit</Link></li>
        ))}
      </ul>
    </div>
  );
}

export default LogEntries;
