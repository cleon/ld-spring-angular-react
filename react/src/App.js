import { useState } from 'react';
import { useFlags } from 'launchdarkly-react-client-sdk';
import './App.css';

export default function App(props) {
  const { enablePurchasing } = useFlags();
  const [context] = useState(props.context);

  const placeOrderClicked = () => {
    alert('Thank you for your purchase.');
  };

  const Product = (props) => (
    <div className="product">
      <div className="productImage">
        <img src={props.img} />
        <div className="productDetails">
          <h3 className="productName">{props.name}</h3>
          <h2 className="productPrice">${props.price.toFixed(2)}</h2>
        </div>
      </div>
    </div>
  );

  const Checkout = (props) => (
    <div className="orderFormBox">
      <div className="orderForm">
        <h3 className="orderFormTitle">React Order Form</h3>
        <Input label="User's Email" type="text" name="email" value={props.email} />
        <Input label="User's Plan" type="text" name="plan" value={props.plan} />
        <button className="orderButton" type="button" disabled={!enablePurchasing} onClick={placeOrderClicked}>Place Order</button>
      </div>
    </div>
  );

  const Input = (props) => (
    <div className="input">
      <label>{props.label}</label>
      <div className="orderInput">
        <input readOnly={true} type={props.type} name={props.name} value={props.value} onChange={() => { }} />
      </div>
    </div>
  );

  return (
    <div className="main">
      <div className="row">
        <div className="column">
          <Product name={context.name} price={context.price} img={context.url} />
        </div>
        <div className="column">
          <Checkout email={context.email} plan={context.plan}/>
        </div>
      </div>
    </div>
  );
}