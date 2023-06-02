# Android and Desktop App for MarieTeam

## 📱 Android App

<strong>An android project developed in Java with Retrofit and Room (distant and local database sync).</strong>
<details>
  <summary><strong>➡️ Android app preview</strong></summary>
  <br/>
  <img align="left" src="https://imgur.com/BfFPfRr.jpeg" width="280" />
  <img src="https://imgur.com/XnO9Cqn.jpeg" width="280" />
</details>

### Features 🚀

- 🌐 **Connectivity**: The Android application is designed to connect to a remote database using Retrofit. This enables seamless communication with the server to fetch data and perform necessary operations.

- 🔄 **Data Synchronization**: The application synchronizes data between the local and remote databases. Once the user modifies the state of passenger boat crossings (e.g., in progress, completed), the changes are propagated to the local database. If an internet connection is available, the application syncs the updated data with the remote database.

- 📊 **Database Management**: The application utilizes Room, an Android library for local database management. It efficiently stores the retrieved data from the remote database, providing offline access and faster performance.

- ✏️ **State Modification**: The Android app allows users to modify the state of boat crossings. This feature enables updating the status of crossings to reflect their progress or completion, ensuring accurate tracking and management.

- 📶 **Network Connectivity Handling**: The application is equipped with network connectivity handling to ensure seamless data transmission. It detects the availability of an internet connection and adjusts the synchronization process accordingly, ensuring a smooth user experience.

- 🧩 **User Interface**: The app incorporates a user-friendly interface, offering intuitive navigation and interactions. The design follows the principles of modern Android development, leveraging Java as the programming language.

### Prerequisites 🛠️

- Android Studio (minSdk : 24)
- Basic knowledge of Android development using Java

## How to Run the Project ▶️

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/dylanlmr/MarieTeam.git
2. Navigate to the location where you cloned the repository and go to "MarieTeam/Appli_Mobile/API/"
3. Copy the "marieteam" folder to your local web server and start it.
4. Import the database situated in "MarieTeam/Database/marieteam.sql" to your PhpMyAdmin with the name "marieteam".
5. Open Android Studio and select "Open a Project".
6. Navigate to "MarieTeam/Appli_Mobile/MarieTeam".
7. Wait for Gradle to sync the project and download the necessary dependencies.
8. Rebuild the project.
9. Click the "Run" button in Android Studio to launch the application on an emulator or a connected device.

## 🖥️ Desktop App

<strong>A desktop project developed in Java using JDBC.</strong>
<details>
  <br/>
  <summary><strong>➡️ Desktop app preview</strong></summary>
  <img src="https://imgur.com/r99LVvW.jpeg" width="600" />
  <br/><br/>
  <img src="https://imgur.com/qCv8bmV.jpeg" width="600" />
  <br/><br/>
  <p>
    Exemple of selected boats, exported as PDF files :
  </p>
  <img src="https://imgur.com/Pzo8JF5.jpeg" width="600" />
</details>

### Features 🚀

- 🌐 **Database Connectivity**: The Java desktop application connects to a remote database using JDBC (Java Database Connectivity). This enables seamless communication with the database server, allowing the application to perform CRUD operations (Create, Read, Update, Delete) on passenger boats' data.

- 📝 **Boat Management**: The application provides functionalities to add, modify, and delete passenger boats. Users can enter information such as the boat's name, image, speed, and equipment details. These details are stored in the remote database, ensuring persistent data storage.

- 📂 **PDF Export**: The application includes a feature to export selected passenger boats to PDF format. Users can choose specific boats, and the application generates a PDF document containing their relevant information, such as name, image, speed, and equipment. This allows users to conveniently share or print boat details.

- 🧩 **User Interface**: The desktop application features a user-friendly interface, providing a seamless experience for managing passenger boats. Users can easily navigate through different functionalities, add or modify boat details, export data to PDF, and perform other operations efficiently.

### Prerequisites 🛠️

- IDE like IntelliJ
- Basic knowledge of object oriented development using Java

## How to Run the Project ▶️

1. Clone this repository to your local machine:

   ```shell
   git clone https://github.com/dylanlmr/MarieTeam.git
2. Navigate to the location where you cloned the repository and go to "MarieTeam/Appli_Client_Lourd/"
3. Start your local web server.
4. Import the database situated in "MarieTeam/Database/marieteam.sql" to your PhpMyAdmin with the name "marieteam".
5. Open your IDE and select "Open a Project".
6. Navigate to the location where you cloned the repository and go to "MarieTeam/Appli_Client_Lourd/".
7. Wait for Maven to sync the project and download the necessary dependencies.
8. Rebuild the project.
9. Click the "Run" button to launch the application.

## Contributions 🤝

Contributions to this project are welcome! If you would like to improve this calculator or add new features, feel free to open a pull request. Please provide a clear description of the proposed changes.

## Authors ✨

[@XKGD](https://github.com/XKGD)

## License 📄

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.
