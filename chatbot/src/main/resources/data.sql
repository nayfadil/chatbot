CREATE TABLE IF NOT EXISTS FAQ (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    answer VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS FALLBACK_RESPONSE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    response VARCHAR(255) NOT NULL
);

INSERT INTO FAQ (question, answer) VALUES ('Hello', 'Hi there!');
INSERT INTO FAQ (question, answer) VALUES ('What is your name?', 'I am Yu' );
INSERT INTO FAQ (question, answer) VALUES ('Who are you ?', 'I am your virtual assistant.');
INSERT INTO FAQ (question, answer) VALUES ('Can you help me ?', 'What can I do for you ?');

INSERT INTO FALLBACK_RESPONSE (response) VALUES ('I am sorry, I did not understand that.');