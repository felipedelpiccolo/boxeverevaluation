package jobs;

import play.jobs.Job;

public class MainJob extends Job<Long>{
	
	@Override
	public void doJob() throws InterruptedException {
		play.Logger.info("Starting to process job");
		Thread.currentThread().sleep(4000);
		play.Logger.info("Finished proccessing job");
	}

	@Override
	public Long doJobWithResult() throws Exception {
		long startTime = System.currentTimeMillis();

		this.doJob();
		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		
		return elapsedTime;
	}
	
	
	
}
