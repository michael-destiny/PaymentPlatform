package cn.berserker.utils;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import android.content.Context;

public class WorkerThread extends Thread {
	final Object obj = new Object();
	AtomicBoolean aLive = new AtomicBoolean(false);
	ArrayList<Worker> workList = new ArrayList<Worker>();
	
	public WorkerThread() {
		aLive.set(true);
	}
	
	@Override
	public void run() {
		while(aLive.get()) {
			Worker elf = null;
			synchronized(obj) {
				if(workList.size() > 0) {
					for(int i = workList.size() - 1 ; i>=0 ; i--) {
						elf = workList.get(i);
						if(elf.getStatus() == Worker.IN_START) {
							break;
						}
						else if(elf.getStatus() == Worker.IN_FINISH) {
							workList.remove(elf);
						}
					}
				}
			}
			if(null != elf && elf.getStatus() == Worker.IN_START) {
				new Thread(elf).start();
				synchronized(obj) {
					workList.remove(elf);
					workList.add(0, elf);
				}
			}
			try {
				Thread.sleep(3 * 1000);
			} catch (java.lang.InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void destroy() {
		aLive.set(false);
	}

	public void addElement(Worker element) {
		synchronized(obj) {
			workList.add(element);
		}
		element.setHost(this);
	}

	public static abstract class Worker implements Runnable {
		private static final int IN_START = 0;
		private static final int IN_PROGRESS = 1;
		private static final int IN_FINISH = 2;

		//public AtomicBoolean hostStatus = new AtomicBoolean(false);
		AtomicInteger workStatus = new AtomicInteger(IN_START);
		WorkerThread mHost = null;
		Context context = null;

		public Worker() {
			//hostStatus.set(true);
		}

		public void setContext(Context ctx) {
			context = ctx;
		}

		public Context getContext() {
			return context;
		}

		public void setHost(WorkerThread m) {
			mHost = m;
		}

		public void onPause() {

		}

		protected void pause() {
			//hostStatus.set(false);
		}

		public boolean isAlive() {
			//return hostStatus.get();
			if(null != mHost) {
				return mHost.aLive.get();
			}
			return false;
		}

		public abstract void onRun();

		public void run() {
			try {
				if(isAlive()) {
					workStatus.set(IN_PROGRESS);
					onRun();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			workStatus.set(IN_FINISH);
			context = null;
			mHost = null;
		}

		protected int getStatus() {
			return workStatus.get();
		}
	}

}
