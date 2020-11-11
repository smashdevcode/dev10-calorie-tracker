import React, { useState } from 'react';

function Test({ headingText }) {
  const [count, setCount] = useState(0);

  const handleClick = () => {
    setCount(count + 1);
  };

  return (
    <div>
      <h2>{headingText}</h2>
      <button type="button" onClick={handleClick}>Click Me</button>
      <p>{count}</p>
    </div>
  );
}

export default Test;
