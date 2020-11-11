import { Link, useParams, useHistory } from 'react-router-dom';

function LogEntry() {
  const { id } = useParams();
  const history = useHistory();

  const handleClick = () => {
    history.push('/logentries');
  };

  return (
    <div>
      <h2>Log Entry</h2>
      <p>Log Entry ID: {id}</p>
      <Link to="/logentries">Back to List</Link>
      <button onClick={handleClick}>Back to List</button>
    </div>
  );
}

export default LogEntry;
