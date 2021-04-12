CREATE TYPE tweet_label AS ENUM ('ham', 'spam', 'unprocessed');

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE table users (
	id uuid DEFAULT uuid_generate_v4(),
	username VARCHAR(100) not null,
    avatar_url VARCHAR,
	inserted_at TIMESTAMP default CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);


CREATE TABLE tweets (
	id uuid DEFAULT uuid_generate_v4(),
	category tweet_label default null,
	profanity_index float default null,
	text VARCHAR(280),
	
	author_id uui,
	inserted_at TIMESTAMP default CURRENT_TIMESTAMP,
    processed_at TIMESTAMP default null,
    replying_to uuid,
	
	PRIMARY KEY (id),
	CONSTRAINT fk_author
     	FOREIGN KEY(author_id) 
	    	REFERENCES users(id)
	    	ON DELETE SET null,
	    	
	CONSTRAINT fk_tweet
     	FOREIGN KEY(replying_to) 
	    	REFERENCES tweets(id)
	    	ON DELETE SET NULL
		
);


CREATE TABLE likes (
	tweet_id uuid,
	user_id uuid,
	
	CONSTRAINT fk_tweet
     	FOREIGN KEY(tweet_id) 
	    	REFERENCES tweets(id)
	    	ON DELETE SET NULL,
	    	
	CONSTRAINT fk_user
     	FOREIGN KEY(user_id) 
	    	REFERENCES users(id)
	    	ON DELETE SET NULL
);
