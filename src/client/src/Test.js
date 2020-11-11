import React, { useState } from 'react';

function Test({ headingText }) {
  const [count, setCount] = useState(0);

  const handleClick = () => {
    setCount(count + 1);
  };

  return (
    <>
      <h1>{headingText}</h1>
      <button type="button" onClick={handleClick}>Click Me</button>
      <p>{count}</p>
    </>
  );
}

export default Test;
