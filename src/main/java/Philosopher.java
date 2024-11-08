import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread{

    private int id;
    private Fork leftFork;
    private Fork rightFork;
    private int numberOfMeals;
    Random random = new Random();
    Dinner dinner;
    CountDownLatch finishMeal;

    public Philosopher(int id, Fork leftFork, Fork rightFork, CountDownLatch finishMeal) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.finishMeal = finishMeal;
    }

    public int getID() {
        return id;
    }

    @Override
    public void run() {
        try{
            while (numberOfMeals < 3){
                eat();
                think();
            }
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("Философ " + id + "  закончил обед.");
        finishMeal.countDown();

    }

    private void think() throws InterruptedException {
        Thread.sleep(random.nextInt(500, 2000));
    }

    private void eat() throws InterruptedException {
        dinner = new Dinner();
        if (dinner.takeForks(leftFork, rightFork)){
            System.out.println("Философ " + id + " взял вилки и приступил к трапезе.");
            Thread.sleep(random.nextInt(2000, 4000));
            dinner.putForks(leftFork, rightFork);
            System.out.println("Философ " + id + "  вкусил пищи мирской, чтобы вкусить пищи духовной. Время размышлений.");
            numberOfMeals++;
        }
    }



}
