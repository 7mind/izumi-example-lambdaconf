# Wiring problem definition 

This repository contains a wireframe of a modular application which needs to be *wired*.

There are many different ways to solve the problem, you may want to check out a full [solution](https://github.com/7mind/distage-example) based on [distage](https://izumi.7mind.io/latest/release/doc/distage/)

Feel free to share your thoughts and ideas with us in our Telegram chat: https://t.me/izumi_en  

## Requirements

We need to implement a simple "leaderboard" service with REST-like API, allowing a user to:

- Register their profile for ranking
- Read the content of their profile
- Submit their scores
- Get a ranked list of all the users

The solution must meet the following requirements:

1. The application must support two kinds data storages: PostgreSQL and in-memory(*) one. 
2. The user must be able to select which storage implementations to use with a command-line argument.
3. The application must be functional when in-memory is selected and no PostgreSQL instance available.
4. The application must be able to self-provision its environment(*) when a Docker daemon is running on the local host. 
5. The user must be able to opt-in for self-provisioning.    
6. All the tests of the application's business logic must be execited twice: with both PostgreSQL and in-memory storage implementation.
7. The environment necessary for PostgreSQL tests must be unconditionally provisioned when a Docker daemon is running on the local host. 
   In case the daemon is no available the tests depending on PostgreSQL must be skipped.
8. All the PostgreSQL-depending tests should share just one instance of PostgreSQL docker container. Such a container should be created before all the tests and removed after the test suite exits. 

(*) This is required for integration testing purposes.