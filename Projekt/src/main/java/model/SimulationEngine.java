package model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimulationEngine {
    private final List<Simulation> simulations;
    private List<Thread> threadList = new ArrayList<>();

    private ExecutorService executorService = Executors.newFixedThreadPool(4);

    public SimulationEngine(List<Simulation> simulations) {
        this.simulations = simulations;
    }

    /**
     * Synchronously runs all simulations.
     * <p>
     * Firstly the constructors of simulations are being done and then method run is starting,
     * but the next simulation waits until the end of run method in the previous simulation.
     */
    public void runSync() {
        for (Simulation simulation: simulations) {
            simulation.run();
        }
    }

    /**
     * Asynchronously runs all simulations using threads which results in interleaving these threads.
     * <p>
     * Firstly the constructors of each simulation are being done and then the thrads
     * interleave which results in running method for one simulation, then for another simulation
     * and another and another...
     * <p>
     * With awaitSimulationEnd() method it works exactly like runSync() method.
     */

    public void runAsync() {
        for (Simulation simulation: simulations) {
//            try {
//                Thread thread = new Thread(simulation);
//                synchronized (this) {
//                    thread.start();
//                    awaitSimulationsEnd(thread);
//                }
//                awaitSimulationEnd(thread);
//            } catch (InterruptedException exception) {
//                System.out.println("Interrupted thread work.");
//            }
            threadList.add(new Thread(simulation));
        }
        for (Thread thread: threadList) {
            thread.start();
        }
        try {
            awaitSimulationsEnd();
        }
        catch (InterruptedException exception) {
            System.out.println("Thread interrupt exception occurred.");
        }
    }

    public void runAsyncWithThreadPool() {
        for (Simulation simulation: simulations) {
            executorService.submit(simulation);
        }
    }

    /**
     * Method which blocks start of thread until all other working threads will be finished.
     * @throws InterruptedException exception.
     */
    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread thread: threadList) {
            thread.join();
        }
        executorService.shutdown();
        Thread.sleep(10000);
    }
}