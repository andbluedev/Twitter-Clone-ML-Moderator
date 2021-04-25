import {get as axiosGet, post as axiosPost } from 'axios';

import {
    _getUsers,
    _getTweets,
    _saveLikeToggle,
    _saveTweet,
} from './_DATA.js'


export function getInitialLocalData() {
    return Promise.all([
        getUsers(),
        getTweets(),
    ]).then(([users, tweets]) => ({
        users,
        tweets,
    }))
}

export function getInitialData() {
    return Promise.all([
        getUsers(),
        getTweets(),
    ]).then(([users, tweets]) => ({
        users,
        tweets,
    }))
}


function get(path) {
    let url = process.env.REACT_APP_API_URI + path;
    return axiosGet(url, { headers: getHeaders() });
}

function post(path, payload) {
    let url = process.env.REACT_APP_API_URI + path;
    return axiosPost(url, payload, { headers: getHeaders() });
}

function getUsers() {
    return get('/user/all')
        .then((res) => {
            const users = res.data.reduce((map, obj) => {
                map[obj.id] = {...obj, name: obj.id };
                return map;
            }, {});
            return users;
        })
        .catch((err) => {
            console.error(err);
        })
}

function getTweets() {
    return get('/tweet/all')
        .then((res) => {
            const tweets = res.data.reduce((map, obj) => {
                map[obj.id] = {...obj, likes: [] };
                return map;
            }, {});
            return tweets;
        })
        .catch((err) => {
            console.error(err);
        })
}


export function saveLikeToggle(info) {
    // not saving likes in browser memory
    // return _saveLikeToggle(info)
}

export function saveTweet(info) {
    let path = "/tweet/new";

    if (info.replyingTo) {
        path = `/tweet/${info.replyingTo}/reply`;
    }
    return post(path, { text: info.text, author: info.author })
        .then((res) => {
            return res.data;
        })
        .catch((err) => {
            console.error(err);
        });
}


function getHeaders() {
    const token = sessionStorage.getItem("kctoken");
    return { Authorization: `Bearer ${token}` }

}