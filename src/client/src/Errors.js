
export default function Errors({ errors }) {
  if (errors.length === 0) {
    return null;
  }

  return (
    <ul>
      {errors.map(error => (
        <li key={error}>{error}</li>
      ))}
    </ul>
  );
}
