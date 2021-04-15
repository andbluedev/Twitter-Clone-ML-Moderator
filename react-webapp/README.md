# Touitter Web APP

Repository copied from [https://github.com/psatler/twitter-clone-react](https://github.com/psatler/twitter-clone-react).

The frontend uses npm and docker:

* [installing npm](https://www.npmjs.com/get-npm) (version used: 7.0.14)
* [installing docker](https://docs.docker.com/get-docker/) (version used: 20.10.5)

## How to run for local dev

Install and run the java backend, instructions [here](../spring-microservice)...

Do the following:

```bash
git clone
npm install
npm start
```

## Build the docker container

The application in production is run using a docker container.

### Building the docker container

Install the npm dependencies:

```bash
npm install
```

Make a production build of the React Webapp and configure the endpoints to hit `/api` when making calls to the java backend (can be changed also to something else like `http://localhost:8000`).

```bash
REACT_APP_API_URI=/api npm run build
```

Build the doker image and tag it as `touitter-webapp:v1`.

```bash
docker build -t touitter-webapp:v1 .
```

### Running the docker container

Running the docker container and exposing the webapp to port 3000 of the host (to access it on [localhost:3000](localhost:3000)).

```bash
docker run -p 3000:80 touitter-webapp:v1 .
```

## Project Notes

## Redux Store architecture

The architecture of the redux store is as follows:

```js
{
  tweets: {
    tweetId: { tweetId, authorId, timestamp, text, likes, replies, replyingTo, profanityScore, spamLabel },
    tweetId: { tweetId, authorId, timestamp, text, likes, replies, replyingTo, profanityScore, spamLabel}
  },
  users: {
    userId: {userId, userName, avatar, tweets array},
    userId: {userId, userName, avatar, tweets array}
  },
  authedUser: userId
}
```
