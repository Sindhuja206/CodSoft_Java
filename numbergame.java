import java.util.*;
class Numgame2{
    public static boolean result(int ran_num){
        Scanner s=new Scanner(System.in);
        int chance=5;
        while(chance!=0){
            System.out.println("Guess a number from 1 to 100:");
            int num=s.nextInt();
            if(num==ran_num)
                return true;
            else if(num<ran_num){
                System.out.println("Too low!");
            }
            else{
                System.out.println("To High");
            }
            chance-=1;
        }
        System.out.println("Failed:( please try again");
        System.out.println("Guessed number is "+ran_num);
        return false;
    }
    public static void main(String args[]){
        Random rand=new Random();
        int ran_num=rand.nextInt(100);
        result(ran_num);
    }
}