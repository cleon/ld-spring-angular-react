import React from 'react';
import ReactDOM from 'react-dom/client';
import { asyncWithLDProvider } from 'launchdarkly-react-client-sdk';
import App from './App';

(async () => {
  let context = {
    kind: 'user',
    key: 'abc123',
    email: 'abc123@gmail.com',
    plan: 'gold'
  };

  const randomProductId = [1, 2, 3, 4, 5][Math.floor(Math.random() * 5)];
  const url = `http://localhost:8080/api/product/${randomProductId}?userKey=${context.key}&plan=${context.plan}&email=${context.email}`;
  const response = await fetch(url);
  const product = await response.json();
  
  context.name = product.name;
  context.url = product.url;
  context.price = product.price;
  context.type = product.type;

  const LDProvider = await asyncWithLDProvider({
    clientSideID: process.env.REACT_APP_LD_CLIENT_SIDE_ID,
    context: context,
    flags: {
      'enable-purchasing': false
    }
  });

  const root = ReactDOM.createRoot(document.getElementById('root'));
  root.render(
    <React.StrictMode>
      <LDProvider>
        <App context={context} />
      </LDProvider>
    </React.StrictMode>
  );
})();