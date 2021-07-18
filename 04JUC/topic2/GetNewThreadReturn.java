package com.cy.venus.calculate;

import lombok.SneakyThrows;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GetNewThreadReturn {

	public static void main(String[] args) throws Exception {
		GetNewThreadReturn g = new GetNewThreadReturn();
		// 方法1：主线程sleep
		g.execute1();
		// 方法2：FutureTask
		g.execute2();
		// 方法3：阻塞当前线程
		g.execute3();
		// 方法4：线程池Future
		g.execute4();
		// 方法5：CountDownLatch
		g.execute5();
		// 方法6：CyclicBarrier
		g.execute6();
		// 方法7：synchronized
		g.execute7();
		// 方法8：主线程循环等待
		g.execute8();
		// 方法9：Lock
		g.execute9();
	}

	private void execute1() throws Exception {
		Item item = new Item();
		Thread thread = new Thread(new MyRunnable(item));
		thread.start();
		Thread.sleep(3000L);
		System.out.println("方法1得到非0值" + item.getValue());
	}

	private void execute2() throws Exception {
		Item item = new Item();
		FutureTask futureTask = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						new MyRunnable(item).run();
						return item.getValue();
					}
				}
		);
		futureTask.run();
		System.out.println("方法2得到非0值" + futureTask.get());
	}

	private void execute3() throws Exception {
		Item item = new Item();
		Thread thread = new Thread(new MyRunnable(item));
		thread.start();
		thread.join();
		System.out.println("方法3得到非0值" + item.getValue());
	}

	private void execute4() throws Exception {
		Item item = new Item();
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		Future future = executorService.submit(new MyRunnable(item));
		future.get();
		System.out.println("方法4得到非0值" + item.getValue());
	}

	private void execute5() throws Exception {
		Item item = new Item();
		CountDownLatch countDownLatch = new CountDownLatch(1);
		Thread thread = new Thread(new Runnable() {
			@SneakyThrows
			@Override
			public void run() {
				Thread.sleep(1000L);
				item.setValue("1");
				countDownLatch.countDown();
			}
		});
		thread.start();
		countDownLatch.await();
		System.out.println("方法5得到非0值" + item.getValue());
	}

	private void execute6() throws Exception {
		Item item = new Item();
		CyclicBarrier cyclicBarrier = new CyclicBarrier(1, new Runnable() {
			@Override
			public void run() {
				System.out.println("方法6得到非0值" + item.getValue());
			}
		});
		Thread thread = new Thread(new Runnable() {
			@SneakyThrows
			@Override
			public void run() {
				Thread.sleep(1000L);
				item.setValue("1");
				cyclicBarrier.await();
			}
		});
		thread.start();
	}

	private void execute7() throws Exception {
		Item item = new Item();
		Thread thread = new Thread(new Runnable() {
			@SneakyThrows
			@Override
			public void run() {
				synchronized (item) {
					Thread.sleep(1000L);
					item.setValue("1");
				}
			}
		});
		thread.start();
		Thread.sleep(100L);
		synchronized (item) {
			System.out.println("方法7得到非0值" + item.getValue());
		}
	}

	private void execute8() throws Exception {
		Item item = new Item();
		Thread thread = new Thread(new MyRunnable(item));
		thread.start();
		while (true) {
			Thread.sleep(100L);
			if (!item.getValue().equals("0")) {
				System.out.println("方法8得到非0值" + item.getValue());
				break;
			}
		}
	}

	private void execute9() throws Exception {
		Lock lock = new ReentrantLock(true);
		Item item = new Item();
		Thread thread = new Thread(new Runnable() {
			@SneakyThrows
			@Override
			public void run() {
				lock.lock();
				Thread.sleep(1000L);
				item.setValue("1");
				lock.unlock();
			}
		});
		thread.start();
		Thread.sleep(100L);
		while (true) {
			if (lock.tryLock()) {
				System.out.println("方法9得到非0值" + item.getValue());
				break;
			}
		}
	}

	private class MyRunnable implements Runnable {

		private Item item;

		private Item getItem() {
			return this.item;
		}

		MyRunnable(Item item) {
			this.item = item;
		}

		@SneakyThrows
		@Override
		public void run() {
			Thread.sleep(1000L);
			item.setValue("1");
		}
	}

	private class Item {
		private String value = "0";

		private String getValue() {
			return this.value;
		}

		private void setValue(String value) {
			this.value = value;
		}
	}
}
