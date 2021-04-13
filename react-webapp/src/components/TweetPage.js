import React, { Component } from "react";
import { connect } from "react-redux";
import { Container, Row, Col } from "react-bootstrap";

import Tweet from "./Tweet";
import NewTweet from "./NewTweet";

class TweetPage extends Component {
  render() {
    const { id, replies } = this.props;

    return (
      <Container>
        <Tweet id={id} />
        {/* passing the parent tweet id */}
        <Row><Col><NewTweet id={id} /></Col></Row>

        {replies.length !== 0 && <h3 className="center">Replies</h3>}
        <ul>
          {replies.map(replyId => (
            <Row><Col><li key={replyId}>
              <Tweet id={replyId} />
            </li></Col></Row>
          ))}
        </ul>
      </Container>
    );
  }
}

function mapStateToProps({ authedUser, tweets, users }, props) {
  const { id } = props.match.params;

  return {
    id,
    replies: !tweets[id]
      ? [] //if doesn't exist a tweet with this id, the reply will be an empty array
      : tweets[id].replies.sort(
        (a, b) => tweets[b].timestamp - tweets[a].timestamp
      )
  };
}

export default connect(mapStateToProps)(TweetPage);
