Liam Kinney, kinne351

Assumptions:
    - That the user will correctly follow the formats given in the instructions.
    - That the user will not try to place a flag on a square that already has one.
    
Additional Features:
    - The format was changed for whether a user wants to place a flag.
    - If a user wants to place a flag, they just follow with an 'F', (or 'f')
      such as, [X] [Y] [F], and a flag will be placed on that {x, y} coordinate.
      If the user does not want to input a flag, they just type [X] [Y] without anything after.

    - Implemented exception handling in case user accidentally breaks the format rules or does not follow them.

    - Since the game is actually not winnable when following the doc, (impossible to reveal information other than just zeroes)
      the ability to reveal other information was implemented. Just like in the real minesweeper, if the user guesses a
      square that is not a mine or a zero, then that squares information is revealed. (e.g. if user guesses a square
      containing a "1" then "1" will be set to revealed, same as 1 - 8).

    - If the user hits a mine and loses, then the location of all mines are revealed, just like in the actual game.


Bugs/Glitches:
    - There could still be some incorrect format inputs that was not accounted for.

Outside sources:
    - Minesweeper

I certify that the information contained in this README file is complete and accurate.
I have both read and followed the course policies in the ‘Academic Integrity - Course Policy’
section of the course syllabus

- Liam Kinney