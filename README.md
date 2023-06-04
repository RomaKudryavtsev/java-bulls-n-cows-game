# java-bulls-n-cows-game

         (__) 
         /oo| 
        (_\"_)*+++++++++*" 
         /   --+++++++ 
         #!  +    ++++++ 
           \\ _    +++++
           |\\_\\   +   +
           |  \\ \\      
        (__/  (__/

Bulls 'N Cows CLI Game is a command-line interface version of the popular game "Bulls and Cows." The game itself and its rules are accessible within the application.
## Features

- Play the classic Bulls and Cows game through a user-friendly command-line interface.
- Guess the hidden code by strategically deducing the correct sequence of numbers.
- Receive feedback on each guess in the form of bulls (correct number in the correct position) and cows (correct number in the wrong position).
- Play multiple rounds to improve your logic and deduction skills.

## Logging and File Storage

The game logs all your plays, including the guesses and the corresponding bulls and cows, using ObjectStreams. This allows you to review your gameplay later and keep track of your progress.

The game saves logs with a specific format (date, time, and moves) in a default directory called "logs." If the player specifies a different directory name, files from the old directory are copied to the new one and the old directory is deleted. The name of the log directory is stored in a separate file called "dir_logs_name.txt." This allows the application to remember the last location of the logs. By default, logs are saved in the "logs" directory.

## Getting Started

To play the Bulls and Cows CLI Game, simply run the application and follow the on-screen instructions to guess the code. The rules and instructions for the game will be displayed within the app itself.