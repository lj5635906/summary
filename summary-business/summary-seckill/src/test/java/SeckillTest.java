//import com.summary.client.seckill.param.SeckillActionParam;
//
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * 秒杀接口测试
// *
// * @author jie.luo
// * @since 2024/6/4
// */
//public class SeckillTest {
//    public static void main(String[] args) throws InterruptedException {
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        Long seckillId = 1798021082926678016L;
//
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(50, 100, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20), new ThreadPoolExecutor.CallerRunsPolicy());
//        for (long i = 0; i < 500; i++) {
//            long customerId = i + 1;
//            executor.execute(new Runnable() {
//                @Override
//                public void run() {
//                    SeckillActionParam param = new SeckillActionParam();
//                    param.setSeckillId(seckillId);
//                    param.setNum(1);
//                    param.setCustomerId(customerId);
//
//                    String str = restTemplate.postForObject("http://localhost:10500/seckill/action", param, String.class);
//
//                    System.out.println(str);
//                }
//            });
//        }
//
//        Thread.sleep(1000 * 60);
//
//        executor.shutdown();
//        boolean flag = true;
//
//        try {
//            do {
//                flag = !executor.awaitTermination(1, TimeUnit.SECONDS);
//                System.out.println(flag);
//            } while (flag);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("线程池关闭");
//        System.out.println(Thread.currentThread().getId());
//    }
//
//}
