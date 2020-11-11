import React, { useState, useEffect } from 'react';

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
    <ul>
      {entries.map(entry => <li key={entry.id}>{entry.description}</li>)}
    </ul>
  );
}

export default LogEntries;
