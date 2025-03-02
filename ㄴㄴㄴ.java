import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class LoadBalancer {
    private static final double CPU_THRESHOLD = 0.70;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        
        // 새로운 작업 추가 예시
        for (int i = 0; i < 20; i++) {
            executor.execute(new Task());
            monitorCpuUsageAndAdjustTasks(executor);
        }

        executor.shutdown();
    }

    private static void monitorCpuUsageAndAdjustTasks(ThreadPoolExecutor executor) {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        double cpuLoad = osBean.getSystemLoadAverage() / osBean.getAvailableProcessors();

        if (cpuLoad > CPU_THRESHOLD) {
            System.out.println("CPU usage exceeded threshold! Redistributing tasks...");
            // 작업 분산 로직 추가
            // 예시: 새로운 서버에 작업 추가
            // For this example, we're just printing a message
        }
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000); // Simulate work
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("Task completed");
        }
    }
}
