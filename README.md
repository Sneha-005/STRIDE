# STRIDE

## Overview
Stride is a feature-rich Android application designed to track and manage daily step counts and workshop attendance. Built using Jetpack Compose, it offers a modern and intuitive user interface while leveraging advanced Android libraries for seamless functionality. The app is tailored for fitness enthusiasts and event organizers, providing step tracking, attendance management, and insightful statistics.

## Features
- **Step Tracking**: Tracks daily steps using Android's sensor framework and provides insights into step count trends.
- **Daily Reset**: Automatically resets step counts at a scheduled time using `AlarmManager`.
- **Dependency Injection**: Utilizes Dagger Hilt for efficient dependency management and code reusability.
- **Data Persistence**: Implements Room Database and DataStore for secure and efficient data storage.
- **Permissions Handling**: Manages runtime permissions with Accompanist Permissions library.
- **Drag And Drop**: Allow user to move a block on the screen.
- **Exercise Recommendation**: Recommends exercise according to the user requirement(Loss Weight, Muscle Gain, Yoga and Meditation)
- **Bottom Sheet Navigation**: User can navigate to all exercises and workout plans.

## Architecture
The app employs a modular architecture that integrates various modern Android development components and best practices to ensure scalability, maintainability, and high performance.

**Components**

1. **User Interface**
- *RecyclerView*: Developed 10+ dynamic and efficient lists using RecyclerView, significantly enhancing UI responsiveness and user experience.
- *Fragments & Activities*: Structured UI components to manage different sections of the app seamlessly.

2. **Data Management**
- *Retrofit*: Integrated APIs with Retrofit for seamless data exchange between the app and backend services.
- *Glide*: Utilized Glide to efficiently process and load images from the server.
- *DataStore*: Implemented a secure OTP-based Two-Factor Authentication system with access and refresh token management using DataStore.
- *Room Db*: 

3. **Authentication & Security**
- *Authentication (OTP)*: Ensured secure user authentication with OTP-based verification.
- *Token Management*: Managed access and refresh tokens securely using DataStore.
- *Oauth2*: Integrated OAuth2 for seamless authentication.  

4. **Asynchronous Operations**
- *Kotlin Coroutines*: Optimized asynchronous operations by incorporating Kotlin Coroutines for managing background tasks, ensuring smooth and responsive user experiences.

5**State Management**
- *ViewModel*: Utilized ViewModel to manage data for RecyclerView adapters, enabling efficient handling of dynamic lists and minimizing redundant API calls.

## Technologies Used

- **Kotlin**: Programming language
- **Compose**: UI Toolkit
- **Room DB**: Local database storage
- **Retrofit**: HTTP client for API calls
- **Coroutines**: Asynchronous programming
- **Navigation Component**: Handling navigation within the app
- **Nested Navigation**: Managing multiple levels of navigation
- **LiveData**: Data lifecycle-aware components

## Samples
<img src="app/src/main/assets/Screenshot 2025-04-16 092211.png">
<img src="app/src/main/assets/Screenshot 2025-04-16 092300.png">
<img src="app/src/main/assets/Screenshot 2025-04-16 092331.png">
<img src="app/src/main/assets/Screenshot 2025-04-16 092345.png">
<img src="app/src/main/assets/Screenshot 2025-04-16 092406.png">
<img src="app/src/main/assets/Screenshot 2025-04-16 092436.png">
<img src="app/src/main/assets/Screenshot 2025-04-16 092448.png">
<img src="app/src/main/assets/Screenshot 2025-04-16 092505.png">

## Dependencies
- **Jetpack Compose**: A modern UI toolkit for building native Android UIs declaratively.
- **Dagger Hilt**: A dependency injection library for Android that simplifies dependency management and promotes code reusability.
- **Alarm Manager**: A system service for scheduling and executing tasks at specific times.
- **Room Database**: A persistence library for Android that provides an abstraction layer over SQLite.

## Getting Started
To utilize the Workshop Attendee Scanner application, follow these steps:
1. Clone the repository: `git clone https://github.com/Sneha-005/STRIDE.git`
2. Open the project in Android Studio.
3. Build and run the application on an Android device or emulator.
4. Scan attendee barcodes to register attendance, and utilize the additional features as needed.

## Contributing
Contributions to the Workshop Attendee Scanner project are encouraged and welcomed! To contribute:
1. Fork the repository.
2. Create a new branch for your feature or bug fix: `git checkout -b feature-name`.
3. Make your changes and commit them: `git commit -m 'Add new feature'`.
4. Push to the branch: `git push origin feature-name`.
5. Create a pull request with a detailed description of your changes.

## Contact
For inquiries or feedback about Stride, please contact the project maintainer.

---

