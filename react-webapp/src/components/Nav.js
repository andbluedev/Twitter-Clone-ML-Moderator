import React from "react";
import { Navbar } from "react-bootstrap";
import { NavLink } from "react-router-dom";

import logo from '../img/touitter-logo.jpeg';

export default function Nav() {
  return (
    <Navbar className="justify-content-between">
      <div className="nav-links">
        <NavLink to="/" exact activeClassName="active" className="nav-link">
          Home
          </NavLink>
        <NavLink to="/new" activeClassName="active" className="nav-link">
          New Tweet
          </NavLink>
      </div>
      <div className="logo">
        <Navbar.Brand>
          <NavLink to="/">
            <img
              alt="touitter-logo"
              src={logo}
              width="200"
              height="80"
              className="d-inline-block align-top"
            />
          </NavLink>
        </Navbar.Brand>
      </div>
    </Navbar>
  );
}
