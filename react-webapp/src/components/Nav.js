import React from "react";
import { NavLink } from "react-router-dom";

import logo from '../img//touitter-logo.jpeg';

export default function Nav() {
  return (
    <nav className="nav">
      <ul>
        <li>
          <NavLink to="/" exact activeClassName="active">
            Home
          </NavLink>
        </li>
        <li>
          <NavLink to="/new" activeClassName="active">
            New Tweet
          </NavLink>
        </li>

      </ul>

      <div>
        <img src={logo} className="logo" alt="touitter-logo" />
      </div>
    </nav>
  );
}
