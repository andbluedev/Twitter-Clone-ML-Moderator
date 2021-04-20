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

  //Updating some value in store to re-render the component
  console.log("keycloack", keycloak.subject)
  console.log("keycloack logout", logoutUrl)
  console.log("keycloack token", keycloak.token)

  keycloak.loadUserProfile()
    .then(function (profile) {
      console.log("profile", JSON.stringify(profile, null, "  "));
    }).catch(function () {
      alert('Failed to load user profile');
    });

  store.dispatch(setAuthedUser('Welcome!'));
}

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById("root")
);
