Playlist Roulette

A music guessing game that connects to your Spotify account and challenges you
to identify which playlist contains a given song.

Slideshow link: 
https://docs.google.com/presentation/d/1phuDOx2s3_phgZc3lrrBf9oXjBdIf8znUGMHYRoq99k/edit?usp=sharing

Features

Spotify Integration: Connect to your Spotify account to access your personal playlists
Game Mode: Play a solo game with customizable number of rounds
Playlist Discovery: View all your Spotify playlists within the app
Game Mechanics: Guess which playlist a randomly selected song belongs to
Score Tracking: View your performance at the end of each game
Offline Support: Fallback to local playlist data when offline

How to Play

Sign in with provided credentials:
Username: admin
Password: admin

Connect your Spotify account via Settings
Start a new game and select number of rounds (5, 8, or 10)
For each round, a song will be displayed with its album art
Select the playlist you think the song belongs to
After each guess, see if you were correct and view the right answer
At the end, review your performance and play again!

Architecture
MVVM architecture:
View Layer: Jetpack Compose UI components
ViewModel: Manages UI state and business logic
Repository: Handles data operations from API and local sources
Navigation: Jetpack Navigation Component with Compose

Technologies

Kotlin
Jetpack Compose
ViewModel & StateFlow
Retrofit for API communication
Coroutines for asynchronous operations
Glide for image loading
Custom Tabs for authentication

API Handling
The application handles multiple API scenarios:

Successfully fetching playlists from Spotify API
API call failures with fallback to local data
Proper error handling with user feedback

Setup Instructions

Open the project in Android Studio
Build with Gradle and run the application
Use the provided credentials to log in
Connect your Spotify account