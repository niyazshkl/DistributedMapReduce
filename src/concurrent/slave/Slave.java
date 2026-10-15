package concurrent.slave;

public interface Slave extends Runnable{
    void notifySlave();
}
