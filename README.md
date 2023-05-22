<h2 align="center">
    <img src="https://i.ibb.co/DM8pSyX/image-1.png" />
    LaunchDarkly + Spring Boot + Angular + React

<font size="3">(Because more is more!)</font>
</h2>

# What is this?
This is a simple Product Ordering application that demonstrates the use of LaunchDarkly feature flags both on the server side and in the browser. 

The server is implemented as a Spring Boot application. There are two UIs available, one built with Angular and one with React. 

Both UIs have identical functionality. 

## Server
The server is a Spring Boot application that exposes a REST API for fetching Products from an in-memory database. Products are returned via GET requests to `http://localhost:8080/api/product/<product id>` where the `<product id>` is a number 1 through 5.

Products returned from the API have a purchase price. The price of the returned Product can be discounted on the server by changing the value of the `discount-pricing` flag in LaunchDarkly.

## UI
The UI displays a simple product order form with a "Place Order" button. The product displayed is randomly determined by the UI and fetched from the server. 

The "Place Order" button is enabled/disabled based on the value of the `enable-purchasing` flag in LaunchDarkly.

# LaunchDarkly setup
The application requires two feature flags set up in LaunchDarkly:

| Flag Key    | Tier | Type  | Variations |
| ----------- | ----- | ----- | ---------- |
| enable-purchasing | Browser | Boolean | true, false |
| discount-pricing | Server | Number | 0.25, 0.5, 0.75, 1 |

The following attributes are available for configuring targeting rules on either flag:

| Attribute | Description | Value |
| --------|----------|-------| 
| key | The user key | `abc123` | 
| email | The user's email address | `abc123@gmail.com` | 
| plan | The user's loyalty level | `gold`|   
| type | The type of product being purchased | `furniture`, `electronics`, `clothing`, `food`, `housewares` | 
| price | The original price of product being purchased | A numeric value |

> NOTE: Product attribute values are hard-coded on the server tier. User attributes are set in the UI. All attributes can be modified as needed.

# Run the application
Here's how to set up and run the Spring Boot server app and the two UIs.

## Spring Boot

1. Update the `ld.sdkkey` property in the `application.properties` file located in `src/main/resources`:
> `application.properties`
``` bash
ld.sdkkey = <Your SDK Key from LaunchDarkly Account settings> 
```

2. Start the application from the repo root:
```bash
% ./mvnw spring-boot:run
```

3. Go to http://localhost:8080/api/product/1 to make sure the Spring Boot application is up and serving Product JSON.

## Angular

1. Install the Angular app dependencies:
```bash
% cd angular
% npm install
```

2. Update the `ldClientID` value in the `environment.ts` file located in `angular/src/environment`:
>`environment.ts`
``` js
export const environment = {
    production: false,
    ldClientID: '<Your ClientSideID from LaunchDarkly Account settings>'
  };
```

3. Start the Angular app:
```bash
% cd angular
% npm start
```

Browse to http://localhost:4200 to view the Angular Product Purchase Form.

## React
1. Install the React app dependencies:
``` bash
% cd react
% npm install
```
2. Update the `REACT_APP_LD_CLIENT_SIDE_ID` value in the `.env.local` file located in the `react` directory:
``` bash
REACT_APP_LD_CLIENT_SIDE_ID=<Your ClientSideID from LaunchDarkly Account settings>
```

3. Start the React UI:
```bash
% cd react
% npm start
```

Browse to http://localhost:3000 to view the React Product Purchase Form.

# Toggling
Try adding targeting rules to the `discount-pricing` flag to manipulate the Product's price. For example, set a rule to reduce the price by 50% for all Products priced over $100.

You can also add rules to the `enable-purchasing` flag to govern the state of the "Place Order" button. For example, set a rule that disables purchasing of all `food` products priced under $50.

Combine multiple attributes and conditions on one or both flags for even more toggling fun!