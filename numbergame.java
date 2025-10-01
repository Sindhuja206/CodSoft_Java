import java.util.*;
class Numgame2{
    public static boolean result(int ran_num){
        Scanner s=new Scanner(System.in);
        int chance=6;
        while(chance!=0){
            System.out.println("Guess a number from 1 to 100:");
            int num=s.nextInt();
            if(num==ran_num){
                System.out.println("You guessed it right! ^_^");
                return true;}
            else if(num<ran_num){
                System.out.println("Too low!");
            }
            else{
                System.out.println("To High!");
            }
            chance-=1;
        }
        System.out.println("Failed :( please try again");
        System.out.println("Guessed number is "+ran_num);
        return false;
    }
    public static void main(String args[]){
        Random rand=new Random();
        int score=0;
        int choice=1;
        Scanner s=new Scanner(System.in);
        while(choice!=0){
            System.out.println("Your Score:"+score);
        int ran_num=rand.nextInt(100);
        if(result(ran_num))score+=1;
        System.out.println("Do you want to continue?(0-No):");
        choice=s.nextInt();
    }}
}