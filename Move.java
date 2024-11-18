public class Move {

    Position position;
    Disc disc;

    public Move(Position position, Disc disc){
     this.position=position;
     this.disc=disc;
    }

    public Position position(){

        return this.position;
    }

    public Disc disc(){

        return this.disc;
    }
}
