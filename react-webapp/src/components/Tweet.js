import React, { Component, useState } from "react";
import { connect } from "react-redux";
import { formatTweet, formatDate, profanityScoreToLevelString } from "../utils/helpers";
import { Link, withRouter } from "react-router-dom";
import { Alert } from "react-bootstrap";


//importing icons from react-icons
import { TiArrowBackOutline } from "react-icons/ti";
import { TiHeartOutline } from "react-icons/ti";
import { TiHeartFullOutline, TiArrowRepeat } from "react-icons/ti";

import { handleToggleTweet } from "../actions/tweets";


import './style.css';


class Tweet extends Component {
  constructor(props) {
    super(props);
    this.state = { show: true };
  }

  setShow(value) {
    this.setState({ show: value });
  }

  toParent = (e, id) => {
    e.preventDefault();
    //todo: redirect to parent tweet
    this.props.history.push(`/tweet/${id}`);
  };


  handleLike = e => {
    e.preventDefault();

    const { dispatch, tweet, authedUser } = this.props;

    //dispatching the action creator
    dispatch(
      handleToggleTweet({
        id: tweet.id,
        hasLiked: tweet.hasLiked,
        authedUser
      })
    );
  };

  render() {
    const { tweet } = this.props;

    if (tweet === null) {
      return <p>This tweet doesn't exist</p>;
    }

    const {
      name,
      avatar,
      timestamp,
      text,
      hasLiked,
      likes,
      replies,
      id,
      parent,
      profanityScore,
      spamLabel
    } = tweet;

    const profanityMessage = profanityScoreToLevelString(profanityScore);
    const enableLink = !this.state.show || (spamLabel === "ham" && !profanityMessage)

    return (
      <Link to={enableLink ? `/tweet/${id}` : "#"} >
        <div className="tweet">
          <img src={avatar} alt={`Avatar of ${name}`} className="avatar" />

          <div className="tweet-info">
            <div>
              <span className="text-body">{name}</span>
              <div>{formatDate(timestamp)} </div>
              {parent && (
                <button
                  className="replying-to"
                  onClick={e => this.toParent(e, parent.id)}
                >
                  Replying to @{parent.author}
                </button>
              )}
              <div>
                {/* TODO: Factorize this tweet content showing.*/}
                {this.state.show && spamLabel === "spam" &&
                  <Alert variant="danger" onClose={() => this.setShow(false)} dismissible>
                    <strong>Warning ! </strong> This tweet was labelled as <strong>spam</strong> by Modbot 🤖. {profanityMessage}
                  </Alert>}
                {this.state.show && spamLabel === "ham" && profanityMessage &&
                  <Alert variant="danger" onClose={() => this.setShow(false)} dismissible>
                    <strong>Warning !</strong> {profanityMessage}.
              </Alert>}

                {this.state.show && spamLabel === "unprocessed" && <Alert variant="warning" onClose={() => this.setShow(false)} dismissible>
                  This tweet is being processed by Modbot 🤖 ...It should be available shortly to other users.
                </Alert>}

                {!this.state.show && <div>
                  <p className="text-body">{text}</p>
                </div>}

                {this.state.show && spamLabel === "ham" && !profanityMessage && <div>
                  <p className="text-body">{text}</p>
                </div>}
              </div>
              <div></div>
            </div>

            <div className="tweet-icons">
              <TiArrowBackOutline className="tweet-icon" />
              {/* show number only if it's not zero */}
              <span>{replies !== 0 && replies} </span>
              <button className="heart-button" onClick={this.handleLike}>
                {hasLiked === true ? (
                  <TiHeartFullOutline color="#e0245e" className="tweet-icon" />
                ) : (
                  <TiHeartOutline className="tweet-icon" />
                )}
              </button>
              <span>{likes !== 0 && likes} </span>
            </div>
          </div>
        </div>
      </Link>
    );
  }
}

//id comes from the props passed by a parent component
function mapStateToProps({ authedUser, users, tweets }, { id }) {
  const tweet = tweets[id]; //getting the specific tweet by its id
  const parentTweet = tweet ? tweets[tweet.replyingTo] : null; //check if the specific tweet is a reply to another one. If so, get information about that parent tweet

  return {
    authedUser,
    tweet: tweet //making sure a tweet exists
      ? formatTweet(tweet, users[tweet.author], authedUser, parentTweet)
      : null
  };
}

//using withRouter because this component is not being rendered by react router, so to have access to history props, we need to use withRouter
export default withRouter(connect(mapStateToProps)(Tweet));
