export function formatDate(timestamp) {
    const d = new Date(timestamp)
    const time = d.toLocaleTimeString('en-US')
    return time.substr(0, 5) + time.slice(-2) + ' | ' + d.toLocaleDateString()
}

export function formatTweet(tweet, author, authedUser, parentTweet) {
    const { id, likes, replies, text, timestamp, label, profanity_score } = tweet
    const { name, avatarURL } = author

    return {
        name,
        id,
        timestamp,
        text,
        avatar: avatarURL,
        likes: likes.length,
        replies: replies.length,
        hasLiked: likes.includes(authedUser),
        parent: !parentTweet ? null : {
            author: parentTweet.author,
            id: parentTweet.id,
        },
        isProcessed: label !== "unprocessed",
        spamLabel: label,
        profanityScore: profanity_score,
    }
}


export function profanityScoreToLevelString(score) {
    let scale = "";

    if (score < 0.3) {
        return ""
    }

    switch (score) {
        case score < 0.5:
            scale = "moderately";
            break;
        case score < 0.75:
            scale = "highly";
            break;
        default:
            scale = "extremely";
    }
    return `This tweet can contain ${scale} coarsed language. Viewer's discretion is advised.`;
}