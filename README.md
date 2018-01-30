# Adelost

This is the main repository for Team 1086's 2018 code for our robot - Adelost.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy our code to the robot.

### Prerequisites

You need to have completed the setup outlined in https://wpilib.screenstepslive.com/s/currentCS/m/getting_started. This includes Eclipse set up with the WPILib FRC Java plugins, the NI suite, CTRE TalonSRX Libraries, and the Kauai Labs NavX Library. 

You also need to have a working understanding of Git and GitHub. Please refer to https://try.github.io/levels/1/challenges/1 and other tutorials online to obtain a basic understanding of Git.

### Installing

To get a working copy of the code onto your local machine, follow these instructions:

First, create a fork of this repository to your own profile. Click on the fork button on the top right of this screen.

Next, open up your Eclipse and create a new Robot project. Name this project Adelost, and make it a Timed Robot. If prompted, your team number is 1086.

Once the project is created, delete the Robot.java file. 

Open up the command prompt, and navigate to the folder you just created. You can change directories by using

```
cd folder_name
```

Once you're in the correct folder, initialize it as a git repository, and set your GitHub repository as its origin. The link can be found by going to your personal repository, clicking on the clone button, and copying the link.

```
git init
git remote add origin -link-
```
Next, connect your repository to the Blue Cheese repository.

```
git remote add upstream https://github.com/BlueCheese1086/Adelost.git
git pull upstream prototype
```

## Workflow
Whenever you want to start working on code, make sure you are up to date with any code that other members have written.

```
git pull upstream prototype
```

Next, write your code. When you are done, follow these steps:

```
git add *.java
git commit -m "Insert a descriptive message here. Should describe what you did."
git push origin master
```

Note how we said `git add *.java` and not add `git add *.*`. We only want our Java file here.

Finally, go to your personal repository and create a pull request into Blue Cheese prototype branch. Kevin/Jack will review it and merge into the Blue Cheese code.

## Deployment

To deploy code using the standard WPILib setup, right click on your project, click on Run As, then Deploy Java. 
