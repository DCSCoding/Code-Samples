# Simple connect four game in python. 

def checkWin(x, y, board):
    player = board[y][x];
    if (board[y][x] == "#"):
        return
    consec = 0;

    for i in range(y - 4, y + 4):
        if (i <= 5 and i >= 0):
            if (board[i][x] == player):
                consec += 1;
            else:
                consec = 0;
        if (consec == 4):
            print(player + " wins!");

    for i in range(x - 4, x + 4):
        if (i <= 6 and i >= 0):
            if (board[y][i] == player):
                consec += 1;
            else:
                consec = 0;
        if (consec == 4):
            print(player + " wins!");

    for i in range(-3, 3):
        if ((y + i <= 5 and y + i >= 0) and (x + i <= 6 and x + i >= 0)):
            if (board[y + i][x + i] == player):
                consec += 1;
            else:
                consec = 0;
        if (consec == 4):
            print(player + " wins!");

    for i in range(-3, 3):
        if ((y + i <= 5 and y + i >= 0) and (x - i <= 6 and x - i >= 0)):
            if (board[y + i][x - i] == player):
                consec += 1;
            else:
                consec = 0;
        if (consec == 4):
            print(player + " wins!");


def makeBoard():
    board = [[]];
    for m in range(6):
        board.append([])
        for k in range(7):
            board[m].append("#");

    return board


def dropPiece(x, y, board, color):
    if (board[y][x] == "#"):
        if (y > 0):
            if (board[y - 1][x] != "#"):
                board[y][x] = color;
                return board, True;
            else:
                print("Could not place chip at this location.")
                return board, False;
        else:
            board[y][x] = color;
            return board, True;
    else:
        print("Could not place chip at this location.")
        return board, False;

    return board, True;


board = makeBoard();
turn = "O";
win = False;

while (not win):
    print("It's " + turn + " 's turn.")
    x = input('Enter x: ')
    while (int(x) - 1 > 6 or int(x) - 1 < 0):
        x = input('Invalid x, try again: ')
    y = input('Enter y: ')
    while (int(y) - 1 > 5 or int(y) - 1 < 0):
        y = input('Invalid y, try again: ')
    state = dropPiece(int(x) - 1, int(y) - 1, board, turn)
    board = state[0]
    if (state[1] == True):
        if (turn == "X"):
            turn = "O"
        else:
            turn = "X"
    else:
        print("Please try again.")
    for row in reversed(board):
        for column in row:
            print(column, end='')
        print("")

    checkWin(int(x) - 1, int(y) - 1, board)



