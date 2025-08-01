# STRIDE

## Overview
Stride is a feature-rich Android application designed to track and manage daily step counts and workshop attendance. Built using Jetpack Compose, it offers a modern and intuitive user interface while leveraging advanced Android libraries for seamless functionality. The app is tailored for fitness enthusiasts and event organizers, providing step tracking, attendance management, and insightful statistics.

## 📱 Screenshots

<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/7ecfd4c6-ef12-4cd0-91b7-3800126f68b7" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/ed6841e5-74f3-4352-a8a5-9388e0ada616" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/249afadd-038b-4671-8990-4870309da9f2" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/8fd6312c-04c8-4a88-94bc-265a89fd5033" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/e01fdb4a-71d2-495f-a116-7431ab91af2e" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/6b235db0-30b0-4304-ae2f-feb8f5e8dc85" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/aeaf1b73-3e61-4b44-b273-821a8530a57a" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/642cfac1-71b3-4c4d-9fec-fc8dd6380405" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/8b5ca589-09d0-40ef-a08b-e2b60412db00" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/46d0dd7f-b251-4162-9ba6-d856def6299b" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/1b8c5b87-01dc-4e39-8657-f22020f90373" />&nbsp;
<img width="720" height="1646" alt="Image" src="https://github.com/user-attachments/assets/91ffec52-f887-4f55-8261-283a470d0fcc" />&nbsp;
<img width="720" height="1748" alt="Image" src="https://github.com/user-attachments/assets/4a61518a-4efc-4726-b289-c171fa3d674a" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/91def949-1619-4ad6-b50a-2d6a2a475013" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/24efdfe1-b614-4f15-96fb-b31b1f74ab70" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/f6dd32b6-8ebe-454e-8625-2a5cfa401ae3" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/b187689d-fba1-482c-8997-20f1780d4bab" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/eadd4e5a-92ba-432d-9ea6-937472461688" />&nbsp;
<img width="720" height="1778" alt="Image" src="https://github.com/user-attachments/assets/cb4b0ed9-ca5f-41bc-8a69-d8213f8b819a" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/d7c744f6-ea20-453e-9a68-1ddef253530b" />&nbsp;
<img width="720" height="2538" alt="Image" src="https://github.com/user-attachments/assets/05ecf32f-e905-473e-9a8b-e7f08af79eb5" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/fcffac93-9cf3-49be-8beb-6bf020aff30b" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/c8971954-40ee-4f9a-9c1c-7340e1db9233" />&nbsp;
<img width="720" height="1836" alt="Image" src="https://github.com/user-attachments/assets/ebd8656f-0e7d-49fb-a6da-1e9047a9dbad" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/37b88f45-38a4-4a96-af62-6398eb8c6c06" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/7eaa7bbc-285f-432b-9e3d-5e2c56dd1752" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/cc4f9217-55c6-4489-9d98-e723e2737aca" />&nbsp;
<img width="720" height="2772" alt="Image" src="https://github.com/user-attachments/assets/1d041574-6628-463f-a828-557d31aa4efe" />&nbsp;
<img width="720" height="1600" alt="Image" src="https://github.com/user-attachments/assets/d6aea6be-4723-4541-88ad-decb2a820ed6" />&nbsp;


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

