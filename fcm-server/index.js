const admin = require('firebase-admin');
const serviceAccount = require('./GOOGLE_APPLICATION_CREDENTIALS.json');
const express = require('express');
const app = express();
const bodyParser = require('body-parser');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

app.use(bodyParser.json());

app.post('/sendNotification', (req, res) => {
    const { title, description, date } = req.body;

    console.log('Received notification request:', { title, description, date });

    const message = {
        notification: {
            title: title,
            body: description + " on:" + date 

        },
        data: {
            title: title,
            body: description + " on:" + date 
        },
        topic: 'all_users' // Send to all instances subscribed to 'all_users'
    };

    admin.messaging().send(message)
        .then((response) => {
            console.log('Successfully sent message:', response);
            res.status(200).send('Notification sent');
        })
        .catch((error) => {
            console.log('Error sending message:', error);
            res.status(500).send('Failed to send notification');
        });
});

app.listen(3000, () => {
    console.log('Server is running on port 3000');
});
