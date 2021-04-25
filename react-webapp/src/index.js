import React from "react";
import ReactDOM from "react-dom";

import { createStore } from "redux";
import { Provider } from "react-redux";

import "./index.css";

import App from "./components/App";
import keycloak from "./keycloak";
import reducer from "./reducers"; //importing the default export from index inside reducers folder
import middleware from "./middleware";


import "bootstrap/dist/css/bootstrap.min.css";

import { setAuthedUser } from "./actions/authedUser";

const store = createStore(reducer, middleware);



keycloak.init({
  onLoad: 'check-sso',
  checkLoginIframeInterval: 1,
})
  .then(authenticated => {
    if (authenticated) {
      loadData();
      setInterval(() => {
        keycloak.updateToken(10).error(() => keycloak.logout());
        loadData();
      }, 10000);
    } else {
      keycloak.login();
    }
  }).catch(error => console.error("Keycloak init error", error));


const loadData = () => {
  const logoutUrl = keycloak.createLogoutUrl();

  sessionStorage.setItem('kctoken', keycloak.token);
  sessionStorage.setItem('kcLogoutUrl', logoutUrl);

  keycloak.loadUserProfile()
    .then(function (profile) {
      store.dispatch(setAuthedUser(profile));
    }).catch(function () {
      console.error('Failed to load keycloak user profile');
    });

}

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);
