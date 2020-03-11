import React from 'react';
import ReactDOM from 'react-dom';
import Favicon from 'react-favicon';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App';
import { createStore, applyMiddleware, compose } from 'redux';
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';

import * as serviceWorker from './serviceWorker';
import favicon from './assets/images/favicon.ico';
import reducer from './store/reducers/bitcoinPriceTracker'; 

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
const store = createStore(reducer, composeEnhancers(applyMiddleware(thunk)));

ReactDOM.render((
    <Provider store={store}>
        <Favicon url={favicon} />
        <App/>
    </Provider>
    ), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
