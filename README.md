# Ask It

Ask It is an Android application designed to facilitate the sharing and answering of questions among users. This project uses Firestore as its backend to store user information and questions.

## Features

- **User Authentication**: Users can sign in using their email and password.
- **Question Posting**: Users can post new questions and answer existing ones.
- **Search Functionality**: Users can search for questions based on keywords.
- **Swipe-to-Refresh**: Users can refresh the list of questions using swipe-to-refresh.
- **Firestore Integration**: The app uses Firestore to store and retrieve data in real-time.

## Working

### Sign In

The sign-in screen allows users to log in using their email and password. Ensure you have registered users in your Firestore database under the "Users" collection.

### Dashboard

The dashboard screen displays a list of questions fetched from the Firestore database. Users can refresh the list using swipe-to-refresh, search for specific questions, and navigate to the question posting screen.

### Question Posting

Users can post new questions from the question posting screen. These questions will be added to the Firestore database and will appear in the dashboard screen.

---
