
import java.util.ArrayList;
import java.util.List;

abstract class Character {
    protected String name;
    protected int hp;
    protected int baseDamage;

    public Character(String name, int hp, int baseDamage) {
        this.name = name;
        this.hp = hp;
        this.baseDamage = baseDamage;
    }

    public abstract void attack();
}

class Warrior extends Character {
    public Warrior(String name, int hp, int baseDamage) {
        super(name, hp, baseDamage);
    }

    @Override
    public void attack() {
        System.out.println(name + " (Warrior) attacks with a sword!");
    }
}

class Archer extends Character {
    public Archer(String name, int hp, int baseDamage) {
        super(name, hp, baseDamage);
    }

    @Override
    public void attack() {
        System.out.println(name + " (Archer) shoots an arrow!");
    }
}

class Mage extends Character {
    public Mage(String name, int hp, int baseDamage) {
        super(name, hp, baseDamage);
    }

    @Override
    public void attack() {
        System.out.println(name + " (Mage) casts a fireball!");
    }
}

class ArcaneArcher extends Character {
    public ArcaneArcher(String name, int hp, int baseDamage) {
        super(name, hp, baseDamage);
    }

    @Override
    public void attack() {
        System.out.println(name + " (ArcaneArcher) shoots an arrow!");
        System.out.println(name + " also casts a magic spell!");
    }
}

public class Main {
    public static void main(String[] args) {
        List<Character> characters = new ArrayList<>();
        characters.add(new Warrior("Arthur", 100, 15));
        characters.add(new Archer("Legolas", 80, 12));
        characters.add(new Mage("Gandalf", 70, 20));
        characters.add(new ArcaneArcher("Sylvanas", 90, 18));

        for (Character c : characters) {
            c.attack();
        }
    }
}
