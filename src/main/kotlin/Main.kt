package tictactoe

fun poleWrite (l: MutableList<Char>) {
    println ("---------")
    println ("| ${l[0]} ${l[1]} ${l[2]} |")
    println ("| ${l[3]} ${l[4]} ${l[5]} |")
    println ("| ${l[6]} ${l[7]} ${l[8]} |")
    println ("---------")
}
fun checkMove (a:Int, b:Int):String {
    var status = "none"
    when {
        a !in (1..9) || b !in (1..9) -> status = "You should enter numbers!"
        a != 1 && a != 2 && a != 3 -> status = "Coordinates should be from 1 to 3!"
        b != 1 && b != 2 && b != 3 -> status = "Coordinates should be from 1 to 3!"
    }
    return status
}

fun coord (a:Int, b:Int):Int  {
    var cap:Int = 0
    when {
        (a == 1 && b == 1) -> cap = 0
        (a == 1 && b == 2) -> cap = 1
        (a == 1 && b == 3) -> cap = 2
        (a == 2 && b == 1) -> cap = 3
        (a == 2 && b == 2) -> cap = 4
        (a == 2 && b == 3) -> cap = 5
        (a == 3 && b == 1) -> cap = 6
        (a == 3 && b == 2) -> cap = 7
        (a == 3 && b == 3) -> cap = 8
    }
    return cap
}

fun checkPole (n: MutableList<Char>): String {
    var epsX = 0
    var epsO = 0
    var eps_ = 0
    for (i in 0..8) {
        if (n[i]=='X') epsX++
        else if (n[i]=='O') epsO++
        else eps_++
    }
    var status = "none"
// Проверка 1 - по количеству знаков:
    if ((epsX-epsO)>1 || (epsO-epsX)>1) {
        status = "Impossible"
    }
// Проверка 2 - на количество вин-линий
    if (status == "none") {
        val winLine = mutableListOf(
            mutableListOf<Char>(n[0], n[1], n[2]),
            mutableListOf<Char>(n[3], n[4], n[5]),
            mutableListOf<Char>(n[6], n[7], n[8]),
            mutableListOf<Char>(n[0], n[3], n[6]),
            mutableListOf<Char>(n[1], n[4], n[7]),
            mutableListOf<Char>(n[2], n[5], n[8]),
            mutableListOf<Char>(n[0], n[4], n[8]),
            mutableListOf<Char>(n[6], n[4], n[2])
        )
        var winX = 0
        var winO = 0
        val lineX = MutableList(3) { 'X' }
        val lineO = MutableList(3) { 'O' }
        for (n in 0..7) {
            if (winLine[n] == lineX) winX++
            else if (winLine[n] == lineO) winO++
        }
        if (winX > 1 || winO > 1 || (winX >= 1 && winO >= 1)) {
            status = "Impossible"
        }
// Проверка 3 - на наличие победителя
        if (status == "none") {
            if (winX == 1)
                status = "X wins"
            else if (winO == 1)
                status = "O wins"
        }
    }
// Проверка 4 на наличие пустых полей
    if (status == "none") {
        if (eps_ == 0)
            status = "Draw"
    }
    return status
}

fun main(args: Array<String>) {
    val pole = MutableList(9) { ' ' }
    var winStatus = "none"
    var moveStatus = "none"
    var move: String
    var moveList = mutableListOf<String>()
    var d: Int = 0
    var count: Int = 0
    poleWrite(pole)

    while (winStatus == "none") {
        moveStatus = "none"
        //poleWrite(pole)
        println("Please, enter your move")
        print('>')
        move = readln()
        if (move == "" || move.indexOf(' ') ==-1) {
            moveStatus ="You should enter numbers!"
            println (moveStatus)
        }
        else {
            moveList = move.split(" ").toMutableList()
            moveStatus = checkMove(moveList[0].toInt(), moveList[1].toInt())
            if (moveStatus != "none") {
                println(moveStatus)
            } else {
                d = coord(moveList[0].toInt(), moveList[1].toInt())
                if (pole[d] != ' ') {
                    moveStatus = "This cell is occupied!"
                    println(moveStatus)
                } else {
                    count++
                    if (count%2 !=0)
                        pole[d] ='X'
                    else
                        pole[d] ='O'
                    //println("X or O?")
                    //pole[d] = readln().single()
                    poleWrite(pole)
                }
            }
            winStatus = checkPole(pole)
            if (winStatus != "none")
                println(winStatus)
        }
    }
}