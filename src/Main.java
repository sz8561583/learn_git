import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player p1 = new Player();
        Player p2 = new Player();
        p1.turn = true;
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();//读取多余的换行符
        Player ATK = new Player();
        Player DEF = new Player();
        while (n-- > 0) {
            if (p1.turn && !p2.turn) {
                ATK = p1;
                DEF = p2;
            } else if (!p1.turn && p2.turn) {
                ATK = p2;
                DEF = p1;
            }
            String turn = sc.nextLine(); //一个回合的操作
            String[] op = turn.split(" ");

            if (op[0].equals("summon")) {
                summon(ATK, Integer.parseInt(op[1]), Integer.parseInt(op[2]), Integer.parseInt(op[3]));
            } else if (op[0].equals("attack")) {
                attack(ATK, DEF, Integer.parseInt(op[1]), Integer.parseInt(op[2]));

            } else if (op[0].equals("end")) {
                ATK.turn = false;
                DEF.turn = true;
            }
        }

        whoIsWinner(p1, p2);
        displayInfo(p1);
        displayInfo(p2);

    }

    public static void summon(Player player, int POS, int ATK, int HP) {
        Hero Minion = new Hero(ATK, HP);
        player.heroes.add(POS, Minion);
    }

    public static void attack(Player ATK, Player DEF, int ATK_POS, int DEF_POS) {
        ATK.heroes.get(ATK_POS).HP -= DEF.heroes.get(DEF_POS).ATK;
        DEF.heroes.get(DEF_POS).HP -= ATK.heroes.get(ATK_POS).ATK;
        //死亡判定
        if (ATK.heroes.get(ATK_POS).HP <= 0)
            ATK.heroes.remove(ATK_POS);
        if (DEF.heroes.get(DEF_POS).HP <= 0)
            DEF.heroes.remove(DEF_POS);
    }

    private static void whoIsWinner(Player p1, Player p2) {
        if (p1.heroes.get(0).HP <= 0)
            System.out.println(1);
        else if (p2.heroes.get(0).HP <= 0)
            System.out.println(-1);
        else
            System.out.println(0);
    }

    private static void displayInfo(Player player) {
        System.out.println(player.heroes.get(0).HP); //英雄的血量
        System.out.print(player.heroes.size() - 1 + " "); //存活随从的个数
        for (int i = 1; i < player.heroes.size(); i++) {
            System.out.print(player.heroes.get(i).HP); //随从的血量
            if (i != player.heroes.size() - 1)
                System.out.print(" ");
            else
                System.out.println();
        }
    }

    static class Hero {
        int HP;     //生命值
        int ATK;    //攻击力

        Hero(int ATK, int HP) {
            this.ATK = ATK;
            this.HP = HP;
        }
    }

    static class Player {
        boolean turn = false;
        ArrayList<Hero> heroes = new ArrayList<>();

        Player() {
            //初始英雄的血量为30，攻击力为0
            heroes.add(new Hero(0, 30));
        }
    }
}

