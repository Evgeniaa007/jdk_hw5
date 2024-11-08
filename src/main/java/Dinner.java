import java.util.concurrent.CountDownLatch;

public class Dinner extends Thread{

    private final int PHILOSOPHER_CNT = 5;
    private Philosopher[] philosophers;
    private Fork[] forks;
    private CountDownLatch finishMeal;

    public Dinner() {
        philosophers = new Philosopher[PHILOSOPHER_CNT];
        forks = new Fork[PHILOSOPHER_CNT];
        finishMeal = new CountDownLatch(PHILOSOPHER_CNT);
        for(int i = 0; i < PHILOSOPHER_CNT; i++){
            forks[i] = new Fork();
        }
        for(int i = 0; i < PHILOSOPHER_CNT; i++){
            if(i == PHILOSOPHER_CNT-1){
                philosophers[i] = new Philosopher(i+1, forks[0], forks[i], finishMeal);
            }else{
                philosophers[i] = new Philosopher(i+1, forks[i], forks[i+1], finishMeal);
            }

        }
    }

    @Override
    public void run() {
        try {
            dinnerTime();
            finishMeal.await();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("Спасибо за обед. Можно расходиться.");
    }

    public synchronized boolean takeForks(Fork leftFork, Fork rightFork){ //TODO
        if(!leftFork.isInHand() && !rightFork.isInHand()){
            leftFork.setInHand(true);
            rightFork.setInHand(true);
            return true;
        }
        return false;
    }

    public void putForks(Fork leftFork, Fork rightFork){
        leftFork.setInHand(false);
        rightFork.setInHand(false);
    }

    private void dinnerTime(){
        for(Philosopher philosopher : philosophers){
            philosopher.start();
        }
    }

}
